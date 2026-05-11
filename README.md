# QA Automation Challenge

Solución al challenge de automatización QA que cubre pruebas de rendimiento, API y E2E.

## Estructura del Proyecto

```
.
├── performance-challenge/
│   ├── ejercicio-1/            # K6 - Script de carga + datos
│   └── ejercicio-2/            # Informe de resultados (InformeResultados.docx)
├── ejercicio-api-karate/       # Karate Framework - Pruebas de API
└── ejercicio-e2e/              # SerenityBDD + ScreenPlay - Pruebas E2E
```

---

## Requisitos Previos

| Herramienta | Versión | Descarga |
|-------------|---------|---------|
| JDK | 17 | https://adoptium.net |
| Chrome | 114+ | https://www.google.com/chrome |
| ChromeDriver | Igual a Chrome | https://googlechromelabs.github.io/chrome-for-testing |
| K6 | Última estable | https://k6.io/docs/get-started/installation |

> Gradle no requiere instalación — cada proyecto incluye el Gradle Wrapper (`gradlew` / `gradlew.bat`).

---

## Clonar el repositorio

```bash
git clone https://github.com/AnthonyTriguero/Ejercicios-performance.git
cd Ejercicios-performance
```

---

## Ejercicio 1 — Performance (K6)

**Objetivo:** Prueba de carga sobre `POST https://fakestoreapi.com/auth/login` apuntando a 20 TPS sostenidos.

### Ejecución

```bash
cd performance-challenge/ejercicio-1
k6 run scripts/login-load-test.js
```

### Umbrales configurados

| Métrica | Umbral |
|---------|--------|
| Percentil 95 de respuesta | < 1500 ms |
| Tasa de errores | < 3% |
| TPS objetivo | 20 |

### Resultados

El informe de análisis con gráficas y conclusiones está en:
```
performance-challenge/ejercicio-2/InformeResultados.docx
```

---

## Ejercicio 2 — API (Karate Framework)

**Objetivo:** Validar los endpoints de autenticación y productos de FakeStore API.

**API base:** `https://fakestoreapi.com`

### Cobertura de pruebas

| Feature | Escenarios |
|---------|-----------|
| `auth/login.feature` | Login exitoso, múltiples usuarios desde CSV, credenciales inválidas, validación de token |
| `products/products.feature` | Obtener todos, por ID, por categoría (Outline), con límite, ordenado descendente |

### Ejecución

**Windows:**
```bash
cd ejercicio-api-karate
.\gradlew.bat test
```

**Linux/Mac:**
```bash
cd ejercicio-api-karate
./gradlew test
```

### Reportes generados

```
build/reports/tests/test/index.html   # Reporte HTML Karate/JUnit
target/cucumber-reports/              # JSON Cucumber
```

---

## Ejercicio 3 — E2E (SerenityBDD + ScreenPlay)

**Objetivo:** Pruebas end-to-end sobre [SauceDemo](https://www.saucedemo.com) usando el patrón ScreenPlay.

### Cobertura de pruebas

| Escenario | Datos | Resultado esperado |
|-----------|-------|-------------------|
| Login exitoso — `standard_user` | CSV | Redirige a inventario |
| Login exitoso — `performance_glitch_user` | CSV | Redirige a inventario |
| Login fallido — `locked_out_user` | Hardcoded | Muestra mensaje de error |
| Login con credenciales vacías | Hardcoded | Muestra mensaje de error |

### Arquitectura ScreenPlay

```
src/test/java/
├── ui/          # Targets (LoginPage, InventoryPage)
├── tasks/       # Tareas (Login, Logout)
├── questions/   # Questions (IsOnInventoryPage, IsErrorDisplayed)
├── stepdefs/    # Step Definitions Cucumber
├── runner/      # CucumberTestRunner (JUnit 4)
└── support/     # CustomChromeDriverSource
```

### Ejecución

**Windows:**
```bash
cd ejercicio-e2e
.\gradlew.bat clean test -Dwebdriver.chrome.driver=C:\ruta\chromedriver.exe
```

**Linux/Mac:**
```bash
cd ejercicio-e2e
./gradlew clean test -Dwebdriver.chrome.driver=/ruta/chromedriver
```

> Si ChromeDriver está en el PATH del sistema, el parámetro `-Dwebdriver.chrome.driver` puede omitirse.

### Reportes generados automáticamente

```
target/site/serenity/index.html       # Reporte Serenity HTML completo
target/cucumber-reports/Cucumber.html # Reporte Cucumber HTML
target/cucumber-reports/Cucumber.json # Reporte Cucumber JSON
```

---

## Datos de prueba

Todos los ejercicios usan datos parametrizados desde archivos CSV:

| Proyecto | Archivo |
|---------|---------|
| K6 Performance | `performance-challenge/ejercicio-1/data/users.csv` |
| API Karate | `ejercicio-api-karate/src/test/resources/data/users.csv` |
| E2E SerenityBDD | `ejercicio-e2e/src/test/resources/data/users.csv` |
