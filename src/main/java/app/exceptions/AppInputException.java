package app.exceptions;

public class AppInputException extends Exception{
    public String field;
    public AppInputException(String s)
    {
        super(s);
    }
    public AppInputException(String s, String field)
    {
        super(s);
        this.field = field;
    }
}
