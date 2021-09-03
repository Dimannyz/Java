package exceptions;

public class DateNotFoundException extends IllegalArgumentException
{
    public static final String DEFAULT_MESSAGE = "Элемент не найден.";
    public DateNotFoundException() { super(DEFAULT_MESSAGE); }
    public DateNotFoundException(String message)
    {
        super(message);
    }
}
