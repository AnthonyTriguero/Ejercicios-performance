# QA Automation Challenge

Solución al challenge de automatización QA que cubre pruebas de rendimiento, API y E2E.

## Estructura del Proyecto

```
.
├── performance-challenge/      # Ejercicio de Performance con K6
│   ├── ejercicio-1/            # Script de carga + datos + reportes
│   └── ejercicio-2/            # Informe de resultados (InformeResultados.docx)
├── ejercicio-api-karate/       # Pruebas de API con Karate Framework
└── ejercicio-e2e/              # Pruebas E2E con SerenityBDD + ScreenPlay
```

## Requisitos Previos

| Herramienta | Versión |
|-------------|---------|
| JDK | 17 |
| Gradle | 7.6.1 (wrapper incluido) |
| Chrome | 114+ |
| ChromeDriver | Compatible con Chrome instalado |
| K6 | Última estable |

---

## Ejercicio Performance (K6)

**Objetivo:** Prueba de carga sobre el endpoint `POST /auth/login` de FakeStore API, apuntando a 20 TPS sostenidos.

### Ejecución

```bash
cd performance-challenge/ejercicio-1
k6 run scripts/login-load-test.js
```

### Umbrales configurados

- **p(95) < 1500 ms**
- **Tasa de errores < 3%**
- **TPS objetivo: 20**

### Reportes

Los reportes HTML generados por K6 quedan en `performance-challenge/ejercicio-1/reports/`.  
El informe de análisis completo se encuentra en `performance-challenge/ejercicio-2/InformeResultados.docx`.

---

## Ejercicio API — Karate Framework

**Objetivo:** Validar los endpoints de autenticación y productos de FakeStore API.

### Cobertura de pruebas

| Feature | Escenarios |
|---------|-----------|
| `auth/login.feature` | Login exitoso, Login con múltiples usuarios (CSV), Credenciales inválidas, Validación de token |
| `products/products.feature` | Obtener todos, Por ID, Por categoría (Outline), Con límite, Ordenado descendente |

### Ejecución

```bash
cd ejercicio-api-karate
./gradlew test
```

### Reportes

```
build/reports/tests/test/index.html    # Reporte JUnit/Karate
target/cucumber-reports/               # Reporte Cucumber JSON
```

---

## Ejercicio E2E — SerenityBDD + ScreenPlay

**Objetivo:** Pruebas end-to-end sobre [SauceDemo](https://www.saucedemo.com) usando el patrón ScreenPlay.

### Arquitectura ScreenPlay

```
src/test/java/
├── ui/                     # Page Objects (Targets de Serenity)
│   ├── LoginPage.java
│   └── InventoryPage.java
├── tasks/                  # Tareas ScreenPlay
│   ├── Login.java
│   └── Logout.java
├── questions/              # Questions ScreenPlay
│   ├── IsOnInventoryPage.java
│   └── IsErrorDisplayed.java
├── stepdefs/               # Step Definitions Cucumber
│   └── LoginStepDefinitions.java
├── runner/                 # Runner JUnit 4
│   └── CucumberTestRunner.java
└── support/                # Configuración custom driver
    └── CustomChromeDriverSource.java
```

### Cobertura de pruebas

| Escenario | Resultado esperado |
|-----------|-------------------|
| Login exitoso — `standard_user` | Redirige a inventario |
| Login exitoso — `performance_glitch_user` | Redirige a inventario |
| Login fallido — `locked_out_user` | Muestra mensaje de error |
| Login con credenciales vacías | Muestra mensaje de error |

Los datos de usuarios se parametrizan desde `src/test/resources/data/users.csv`.

### Ejecución

**Windows:**
```bash
cd ejercicio-e2e
.\gradlew.bat clean test -Dwebdriver.chrome.driver=<ruta>\chromedriver.exe
```

**Linux/Mac:**
```bash
cd ejercicio-e2e
./gradlew clean test -Dwebdriver.chrome.driver=<ruta>/chromedriver
```

> Si ChromeDriver está en el PATH del sistema, el parámetro `-Dwebdriver.chrome.driver` puede omitirse.

### Reportes generados automáticamente

```
target/site/serenity/index.html          # Reporte Serenity HTML
target/cucumber-reports/Cucumber.html    # Reporte Cucumber HTML
target/cucumber-reports/Cucumber.json    # Reporte Cucumber JSON
```

---

## Datos de prueba

Todos los ejercicios utilizan datos parametrizados:

| Proyecto | Archivo | Formato |
|---------|---------|---------|
| API Karate | `src/test/resources/data/users.csv` | CSV |
| E2E SerenityBDD | `src/test/resources/data/users.csv` | CSV |
| K6 Performance | `ejercicio-1/data/users.csv` | CSV |
