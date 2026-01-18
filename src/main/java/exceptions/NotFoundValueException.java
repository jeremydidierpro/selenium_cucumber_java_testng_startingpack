package exceptions;

public class NotFoundValueException extends RuntimeException{
    public NotFoundValueException(String msg){
        super(msg);
    }
}
