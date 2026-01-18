package exceptions;

public class NotFoundPathException extends RuntimeException{
    public NotFoundPathException(String msg){
        super(msg);
    }
}
