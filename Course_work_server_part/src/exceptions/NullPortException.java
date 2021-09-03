package exceptions;

public class NullPortException extends IllegalArgumentException
{
    public static final String DEFAULT_MESSAGE = "Введите порт";
    public NullPortException()
    {
        super(DEFAULT_MESSAGE);
    }
    public NullPortException(String message)
    {
        super(message);
    }
}
