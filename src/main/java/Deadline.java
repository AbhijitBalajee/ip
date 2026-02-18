/**
 * Represents a task with a deadline.
 */
public class Deadline extends Task {
    /** Deadline time as a string */
    protected String deadlineTime;

    /**
     * Constructs a Deadline task.
     *
     * @param description Task description
     * @param deadlineTime Deadline time (e.g., "Sunday", "2026-02-15 18:00")
     */
    public Deadline(String description, String deadlineTime) {
        super(description);
        this.deadlineTime = deadlineTime;
    }

    /**
     * Returns string representation of the deadline task.
     *
     * @return Formatted string with [D] prefix and deadline information
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + deadlineTime + ")";
    }
}