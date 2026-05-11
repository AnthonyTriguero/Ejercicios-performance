package questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import ui.LoginPage;

public class IsErrorDisplayed implements Question<Boolean> {

    @Override
    public Boolean answeredBy(Actor actor) {
        try {
            return LoginPage.ERROR_MESSAGE.resolveFor(actor).isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    public static IsErrorDisplayed visible() {
        return new IsErrorDisplayed();
    }
}
