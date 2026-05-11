Feature: Productos - FakeStore API
  Como usuario autenticado
  Quiero consultar el catalogo de productos
  Para visualizar los items disponibles en la tienda

  Background:
    * url baseUrl

  Scenario: Obtener todos los productos
    Given path '/products'
    When method GET
    Then status 200
    And match response == '#[]'
    And match each response == { id: '#number', title: '#string', price: '#number', description: '#string', category: '#string', image: '#string', rating: '#object' }
    And print 'Total de productos:', response.length

  Scenario: Obtener un producto por ID
    Given path '/products/1'
    When method GET
    Then status 200
    And match response.id == 1
    And match response.title == '#notnull'
    And match response.price == '#number'
    And match response.category == '#notnull'

  Scenario Outline: Obtener productos por categoria - <category>
    Given url baseUrl + '/products/category/' + '<category>'
    When method GET
    Then status 200
    And match response == '#[]'
    And print 'Productos en categoria <category>:', response.length

    Examples:
      | category         |
      | electronics      |
      | jewelery         |
      | men%27s%20clothing |
      | women%27s%20clothing |

  Scenario: Obtener productos con limite de resultados
    Given path '/products'
    And param limit = 5
    When method GET
    Then status 200
    And match response == '#[5]'

  Scenario: Obtener productos ordenados descendente
    Given path '/products'
    And param sort = 'desc'
    When method GET
    Then status 200
    And match response == '#[]'
    And assert response[0].id > response[1].id
