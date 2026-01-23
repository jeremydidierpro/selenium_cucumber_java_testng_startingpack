package exceptions;

public class InvalidApiRequestException extends RuntimeException{
    public InvalidApiRequestException(String msg){
        super(msg);
    }
}
