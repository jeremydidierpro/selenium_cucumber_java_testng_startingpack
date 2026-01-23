package exceptions;

public class InvalidSqlQueryException extends RuntimeException{
    public InvalidSqlQueryException(String msg){
        super(msg);
    }
}
