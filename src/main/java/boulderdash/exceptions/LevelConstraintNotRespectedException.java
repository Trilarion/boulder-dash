package boulderdash.exceptions;

/**
 * Raises an 'LevelConstraintNotRespectedException' exception.
 * Given the exception message.
 */
public class LevelConstraintNotRespectedException extends Exception {
    /**
     * @param message Exception backtrace message
     */
    public LevelConstraintNotRespectedException(String message) {
        super(message);
    }
}
