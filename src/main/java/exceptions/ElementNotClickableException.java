package exceptions;

public class ElementNotClickableException extends RuntimeException{
    public ElementNotClickableException(String msg){
        super(msg);
    }
}
