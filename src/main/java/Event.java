/**
 * Represents a task that occurs during a specific time period.
 */
public class Event extends Task {
    /** Start time of the event */
    protected String startTime;
    /** End time of the event */
    protected String endTime;

    /**
     * Constructs an Event task.
     *
     * @param description Task description
     * @param startTime Event start time (e.g., "Monday 2pm")
     * @param endTime Event end time (e.g., "Monday 4pm")
     */
    public Event(String description, String startTime, String endTime) {
        super(description);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Returns the start time.
     *
     * @return Start time string
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Returns the end time.
     *
     * @return End time string
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Returns string representation of the event task.
     *
     * @return Formatted string with [E] prefix and event timing information
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + startTime + " to: " + endTime + ")";
    }
}