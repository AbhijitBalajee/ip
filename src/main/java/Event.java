import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task that occurs during a specific time period.
 * Supports both datetime and natural language time specifications.
 */
public class Event extends Task {
    /** Start time as LocalDateTime (if parseable) */
    private LocalDateTime startDateTime;
    /** End time as LocalDateTime (if parseable) */
    private LocalDateTime endDateTime;
    /** Original start time string */
    private String startString;
    /** Original end time string */
    private String endString;
    /** Whether the times were successfully parsed */
    private boolean isParsedDateTime;

    /**
     * Constructs an Event task.
     * Attempts to parse times as LocalDateTime in yyyy-mm-dd HHmm format.
     *
     * @param description Task description
     * @param start Start time (either "yyyy-mm-dd HHmm" or natural language)
     * @param end End time (either "yyyy-mm-dd HHmm" or natural language)
     */
    public Event(String description, String start, String end) {
        super(description);
        this.startString = start;
        this.endString = end;

        try {
            // Try to parse as LocalDateTime (yyyy-mm-dd HHmm format)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
            this.startDateTime = LocalDateTime.parse(start, formatter);
            this.endDateTime = LocalDateTime.parse(end, formatter);
            this.isParsedDateTime = true;
        } catch (DateTimeParseException e) {
            // If parsing fails, keep as strings
            this.isParsedDateTime = false;
        }
    }

    /**
     * Constructs an Event task with pre-parsed datetimes.
     * Used when loading from storage.
     *
     * @param description Task description
     * @param startDateTime Start as LocalDateTime
     * @param endDateTime End as LocalDateTime
     */
    public Event(String description, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        super(description);
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        this.startString = startDateTime.format(formatter);
        this.endString = endDateTime.format(formatter);
        this.isParsedDateTime = true;
    }

    /**
     * Returns the start time as a string.
     *
     * @return Start time string (for storage)
     */
    public String getStartTime() {
        if (isParsedDateTime) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
            return startDateTime.format(formatter);
        }
        return startString;
    }

    /**
     * Returns the end time as a string.
     *
     * @return End time string (for storage)
     */
    public String getEndTime() {
        if (isParsedDateTime) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
            return endDateTime.format(formatter);
        }
        return endString;
    }

    /**
     * Returns the start datetime if it was successfully parsed.
     *
     * @return LocalDateTime or null if not parseable
     */
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    /**
     * Returns the end datetime if it was successfully parsed.
     *
     * @return LocalDateTime or null if not parseable
     */
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    /**
     * Returns string representation of the event task.
     * If datetime is parsed, displays in "MMM dd yyyy, h:mma" format.
     *
     * @return Formatted string with [E] prefix and event timing information
     */
    @Override
    public String toString() {
        String startDisplay;
        String endDisplay;

        if (isParsedDateTime) {
            // Format as "MMM dd yyyy, h:mma" (e.g., "Dec 02 2019, 6:00PM")
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");
            startDisplay = startDateTime.format(formatter);
            endDisplay = endDateTime.format(formatter);
        } else {
            startDisplay = startString;
            endDisplay = endString;
        }

        return "[E]" + super.toString() + " (from: " + startDisplay + " to: " + endDisplay + ")";
    }
}