package runner;

import com.intuit.karate.junit5.Karate;

class TestRunner {

    @Karate.Test
    Karate testAll() {
        return Karate.run("classpath:features")
                     .outputCucumberJson(true)
                     .relativeTo(getClass());
    }

    @Karate.Test
    Karate testAuth() {
        return Karate.run("classpath:features/auth")
                     .outputCucumberJson(true)
                     .relativeTo(getClass());
    }

    @Karate.Test
    Karate testProducts() {
        return Karate.run("classpath:features/products")
                     .outputCucumberJson(true)
                     .relativeTo(getClass());
    }
}
