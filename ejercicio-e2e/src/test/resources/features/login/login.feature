@login
Feature: Login - SauceDemo Store
  Como usuario de la tienda en linea
  Quiero poder iniciar sesion con mis credenciales
  Para acceder al catalogo de productos

  Scenario Outline: Login exitoso con usuario <username>
    Given "Cliente" es un usuario de la tienda
    When "Cliente" inicia sesion con usuario "<username>" y contrasena "<password>"
    Then "Cliente" deberia ver la pagina de productos

    Examples:
      | username                | password     |
      | standard_user           | secret_sauce |
      | performance_glitch_user | secret_sauce |

  Scenario: Login fallido con usuario bloqueado
    Given "Carlos" es un usuario de la tienda
    When "Carlos" inicia sesion con usuario "locked_out_user" y contrasena "secret_sauce"
    Then "Carlos" deberia ver un mensaje de error de acceso

  Scenario: Login con credenciales vacias muestra error
    Given "Ana" es un usuario de la tienda
    When "Ana" inicia sesion con usuario "" y contrasena ""
    Then "Ana" deberia ver un mensaje de error de acceso
