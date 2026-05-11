function fn() {
    var env = karate.env;
    karate.log('karate.env =', env);

    var config = {
        baseUrl: 'https://fakestoreapi.com'
    };

    return config;
}
