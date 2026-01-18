package exceptions;

public class IncorrectAPIRequestException extends RuntimeException{
    public IncorrectAPIRequestException(String msg){
        super(msg);
    }
}
