package assertions;

import org.assertj.core.api.AbstractAssert;
import org.openqa.selenium.WebElement;

import java.util.Objects;


public class WebElementAssert extends AbstractAssert<WebElementAssert, WebElement> {



    protected WebElementAssert(WebElement webElement) {
        super(webElement,WebElementAssert.class );
    }
    public static WebElementAssert assertThat(WebElement element){
        return new WebElementAssert(element);
    }
    public WebElementAssert isDisplayed(){
        isNotNull();
        if(!actual.isDisplayed()){
            failWithMessage("Expected the element to be displayed. But it was not...");
        }
        return this;
    }
    public WebElementAssert isEnabled(){
        isNotNull();
        if(!actual.isEnabled()){
            failWithMessage("Expected the element to be enabled. But it was not...");
        }
        return this;
    }
    public WebElementAssert isButton(){
        isNotNull();
        boolean isButton = actual.getTagName().equalsIgnoreCase("button") || actual.getAttribute("type").equalsIgnoreCase("button");
        if(!isButton){
            failWithMessage("Expected the element to be a button. But it was not...");
        }
        return this;
    }
    public WebElementAssert hasAttributeValue(String attr, String value){
        isNotNull();
        if(!actual.getAttribute(attr).trim().equalsIgnoreCase(value)){
            failWithMessage("Expected the element to have attribute <%s> with value <%s>. But the actual value was <%s>",attr, value, actual.getAttribute(attr));
        }
        return this;
    }
    public WebElementAssert hasTextEqualTo(String value){
        isNotNull();
        String text = actual.getText().trim();
        if(!text.equalsIgnoreCase(value)){
            failWithMessage("Expected the element to have text equals to <%s>. But the actual value was <%s>", value, actual.getText());
        }
        return this;
    }
    public WebElementAssert hasTitleEqualTo(String value){
        isNotNull();
        String title = actual.getAttribute("title").trim();
        if(!title.equalsIgnoreCase(value)  && !title.equalsIgnoreCase(value+"\\n" + "Click to follow link")){
            failWithMessage("Expected the element to have attribute 'title' with value <%s>. But the actual value was <%s>", value, actual.getAttribute("title"));
        }
        return this;
    }
    public WebElementAssert hasValueEqualTo(String value){
        isNotNull();
        if(!actual.getAttribute("value").trim().equalsIgnoreCase(value)){
            failWithMessage("Expected the element to have attribute 'value' with value <%s>. But the actual value was <%s>", value, actual.getAttribute("value"));
        }
        return this;
    }
    public WebElementAssert isReadableOnly(){
        isNotNull();
        if(Objects.isNull(actual.getAttribute("readonly"))){
            failWithMessage("Expected the element to be readable only. But was not...");
        }
        return this;
    }
    public WebElementAssert isEditable(){
        isNotNull();
        if(Objects.nonNull(actual.getAttribute("readonly"))){
            failWithMessage("Expected the element to be editable. But was not...");
        }
        return this;
    }
}