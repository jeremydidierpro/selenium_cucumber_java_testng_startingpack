package functions.miscellaneous;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class Screenshots {
    private final TakesScreenshot screenshot;

    public Screenshots(WebDriver driver){
        this.screenshot = (TakesScreenshot)driver;
    }


    public void takeAScreen()  {
        File scr = screenshot.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scr,new File("./screenshots/test.jpg" ));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
