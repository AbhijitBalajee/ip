/**
 * Represents a generic task with a description and completion status.
 */
public class Task {
    /** Description of the task */
    protected String description;
    /** Whether the task has been completed */
    protected boolean isDone;

    /**
     * Constructs a task with the given description.
     * Task starts as not done.
     *
     * @param description Task description
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon for display.
     *
     * @return "X" if task is done, " " otherwise
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Returns the task description.
     *
     * @return Task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns string representation of the task.
     *
     * @return Formatted string with status icon and description
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}