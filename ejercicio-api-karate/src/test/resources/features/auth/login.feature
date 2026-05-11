Feature: Autenticacion - FakeStore API
  Como usuario del sistema
  Quiero autenticarme en el endpoint de login
  Para obtener un token de acceso valido

  Background:
    * url baseUrl

  Scenario: Login exitoso con credenciales validas
    Given path '/auth/login'
    And header Content-Type = 'application/json'
    And request { username: 'johnd', password: 'm38rmF$' }
    When method POST
    Then status 201
    And match response == { token: '#notnull' }
    And match response.token == '#string'

  Scenario Outline: Login exitoso con multiples usuarios desde CSV - <username>
    Given path '/auth/login'
    And header Content-Type = 'application/json'
    And request { username: '<username>', password: '<password>' }
    When method POST
    Then status 201
    And match response.token != null

    Examples:
      | read('classpath:data/users.csv') |

  Scenario: Login con credenciales invalidas retorna error
    Given path '/auth/login'
    And header Content-Type = 'application/json'
    And request { username: 'usuario_invalido', password: 'clave_incorrecta' }
    When method POST
    And print 'Status recibido con credenciales invalidas:', responseStatus
    And match responseStatus != 201

  Scenario: Validar estructura completa del token de respuesta
    Given path '/auth/login'
    And header Content-Type = 'application/json'
    And request { username: 'johnd', password: 'm38rmF$' }
    When method POST
    Then status 201
    And def token = response.token
    And match token == '#notnull'
    And assert token.length > 0
    And print 'Token obtenido:', token
