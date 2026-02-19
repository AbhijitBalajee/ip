/**
 * Represents exceptions related to storage operations.
 */
public class StorageException extends Exception {
    /**
     * Constructs a StorageException with the specified error message.
     *
     * @param message Error message describing the storage issue
     */
    public StorageException(String message) {
        super(message);
    }
}