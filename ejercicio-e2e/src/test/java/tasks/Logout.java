package tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.thucydides.core.annotations.Step;
import ui.InventoryPage;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class Logout implements Task {

    @Step("{0} cierra sesion")
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(InventoryPage.MENU_BUTTON),
                Click.on(InventoryPage.LOGOUT_LINK)
        );
    }

    public static Logout fromTheApplication() {
        return instrumented(Logout.class);
    }
}
