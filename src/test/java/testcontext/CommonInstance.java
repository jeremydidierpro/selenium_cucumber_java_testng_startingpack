package testcontext;

import assertions.ScenarioSoftAssertions;

public class CommonInstance {
    private final ScenarioSoftAssertions softly = new ScenarioSoftAssertions();

    public ScenarioSoftAssertions softly(){
        return softly;
    }
}


