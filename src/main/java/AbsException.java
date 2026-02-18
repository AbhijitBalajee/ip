/**
 * Represents exceptions specific to the Abs chatbot application.
 */
public class AbsException extends Exception {
    /**
     * Constructs an AbsException with the specified error message.
     *
     * @param message Error message to display to the user
     */
    public AbsException(String message) {
        super(message);
    }
}