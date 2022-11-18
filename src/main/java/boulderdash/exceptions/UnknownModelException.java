package boulderdash.exceptions;

/**
 * UnknownModelException
 * <p>
 * Raises an 'UnknownSpriteException' exception.
 * Given the exception message.
 */
public class UnknownModelException extends Exception {
    /**
     * @param message Exception backtrace message
     */
    public UnknownModelException(String message) {
        super(message);
    }
}
