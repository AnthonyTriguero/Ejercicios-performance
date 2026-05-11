package stepdefs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import questions.IsErrorDisplayed;
import questions.IsOnInventoryPage;
import tasks.Login;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.is;

public class LoginStepDefinitions {

    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    @After
    public void tearDown() {
        OnStage.drawTheCurtain();
    }

    @Given("{string} es un usuario de la tienda")
    public void esUnUsuarioDeLaTienda(String actorName) {
        theActorCalled(actorName);
    }

    @When("{string} inicia sesion con usuario {string} y contrasena {string}")
    public void iniciaSesion(String actorName, String username, String password) {
        theActorCalled(actorName).attemptsTo(
                Login.withCredentials(username, password)
        );
    }

    @Then("{string} deberia ver la pagina de productos")
    public void deberiaVerLaPaginaDeProductos(String actorName) {
        theActorCalled(actorName).should(
                seeThat(IsOnInventoryPage.displayed(), is(true))
        );
    }

    @Then("{string} deberia ver un mensaje de error de acceso")
    public void deberiaVerMensajeDeError(String actorName) {
        theActorCalled(actorName).should(
                seeThat(IsErrorDisplayed.visible(), is(true))
        );
    }
}
