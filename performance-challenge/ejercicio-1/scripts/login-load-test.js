import http from 'k6/http';
import { check } from 'k6';
import { SharedArray } from 'k6/data';
import { Counter, Rate } from 'k6/metrics';
import { htmlReport } from 'https://raw.githubusercontent.com/benc-uk/k6-reporter/main/dist/bundle.js';
import { textSummary } from 'https://jslib.k6.io/k6-summary/0.0.1/index.js';

// ─── Carga de datos desde CSV ────────────────────────────────────────────────
const users = new SharedArray('users', function () {
    const csv = open('../data/users.csv');
    return csv
        .split('\n')
        .slice(1)                        // omitir encabezado
        .filter((line) => line.trim())
        .map((line) => {
            const [username, password] = line.split(',');
            return { username: username.trim(), password: password.trim() };
        });
});

// ─── Métricas personalizadas ──────────────────────────────────────────────────
const loginErrors = new Counter('login_errors');
const loginErrorRate = new Rate('login_error_rate');

// ─── Configuración del escenario ──────────────────────────────────────────────
export const options = {
    scenarios: {
        login_load: {
            executor: 'ramping-arrival-rate',
            startRate: 0,
            timeUnit: '1s',
            preAllocatedVUs: 40,
            maxVUs: 100,
            stages: [
                { duration: '1m', target: 5  },  // calentamiento
                { duration: '2m', target: 20 },  // rampa hasta 20 TPS
                { duration: '3m', target: 20 },  // carga sostenida a 20 TPS
                { duration: '1m', target: 0  },  // rampa de bajada
            ],
        },
    },
    thresholds: {
        // Tiempo de respuesta: p(95) debe ser < 1500 ms
        'http_req_duration': ['p(95)<1500'],
        // Tasa de error: debe ser < 3 %
        'http_req_failed': ['rate<0.03'],
        'login_error_rate': ['rate<0.03'],
    },
};

const BASE_URL = 'https://fakestoreapi.com';

// ─── Función principal ────────────────────────────────────────────────────────
export default function () {
    // Rotación circular entre usuarios del CSV
    const user = users[__VU % users.length];

    const payload = JSON.stringify({
        username: user.username,
        password: user.password,
    });

    const params = {
        headers: { 'Content-Type': 'application/json' },
        timeout: '60s',
    };

    const res = http.post(`${BASE_URL}/auth/login`, payload, params);

    const ok = check(res, {
        'status 200 o 201':      (r) => r.status === 200 || r.status === 201,
        'tiene token en body':   (r) => {
            try { return JSON.parse(r.body).token !== undefined; }
            catch (_) { return false; }
        },
        'tiempo < 1500 ms':      (r) => r.timings.duration < 1500,
    });

    if (!ok || (res.status !== 200 && res.status !== 201)) {
        loginErrors.add(1);
        loginErrorRate.add(1);
    } else {
        loginErrorRate.add(0);
    }
}

// ─── Generación de reportes ───────────────────────────────────────────────────
export function handleSummary(data) {
    return {
        '../reports/summary.html':
            htmlReport(data),
        '../reports/textSummary.txt':
            textSummary(data, { indent: ' ', enableColors: false }),
        stdout:
            textSummary(data, { indent: ' ', enableColors: true }),
    };
}
