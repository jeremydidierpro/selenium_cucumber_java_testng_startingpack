package assertions;


import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.WebElement;

public class ScenarioSoftAssertions extends SoftAssertions {

    public WebElementAssert assertThat(WebElement element) {
        return proxy(WebElementAssert.class, WebElement.class, element);
    }

}
