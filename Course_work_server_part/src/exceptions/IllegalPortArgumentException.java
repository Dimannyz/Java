package exceptions;

public class IllegalPortArgumentException extends IllegalArgumentException
{
    public static final String DEFAULT_MESSAGE = "Неверное значение порта. Введите значение между 0 и 65535 включительно.";
    public IllegalPortArgumentException ()
    {
        super(DEFAULT_MESSAGE);
    }
    public IllegalPortArgumentException (String message)
    {
        super(message);
    }
}
