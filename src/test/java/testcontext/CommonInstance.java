package testcontext;

import assertions.ScenarioSoftAssertions;
import org.openqa.selenium.WebDriver;

public class CommonInstance {
    private final ScenarioSoftAssertions soflty = new ScenarioSoftAssertions();

    public ScenarioSoftAssertions softly(){
        return soflty;
    }
}


