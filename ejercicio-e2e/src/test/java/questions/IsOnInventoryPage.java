package questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.page.TheWebPage;

public class IsOnInventoryPage implements Question<Boolean> {

    @Override
    public Boolean answeredBy(Actor actor) {
        String url = TheWebPage.currentUrl().answeredBy(actor);
        return url != null && url.contains("inventory");
    }

    public static IsOnInventoryPage displayed() {
        return new IsOnInventoryPage();
    }
}
