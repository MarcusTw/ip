package duke.exception;

/**
 * Represents the exception thrown when task to be edited is not found.
 */
public class DukeInvalidUpdateException extends DukeException {
    /**
     * Class constructor.
     */
    public DukeInvalidUpdateException() {
        super("Error updating your task. Please check the format.");
    }
}
