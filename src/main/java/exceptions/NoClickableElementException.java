package exceptions;

public class NoClickableElementException extends RuntimeException{
    public NoClickableElementException(String msg){
        super(msg);
    }
}
