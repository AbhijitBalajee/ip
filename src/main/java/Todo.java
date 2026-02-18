/**
 * Represents a simple task without any time constraints.
 */
public class Todo extends Task {

    /**
     * Constructs a Todo task.
     *
     * @param description Task description
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns string representation of the todo task.
     *
     * @return Formatted string with [T] prefix
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}