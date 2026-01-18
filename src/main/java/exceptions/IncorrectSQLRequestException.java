package exceptions;

public class IncorrectSQLRequestException extends RuntimeException{
    public IncorrectSQLRequestException(String msg){
        super(msg);
    }
}
