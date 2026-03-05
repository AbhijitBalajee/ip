import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task with a deadline.
 * Supports both date-only and natural language deadlines.
 */
public class Deadline extends Task {
    /** Deadline as LocalDate (if parseable) */
    private LocalDate deadlineDate;
    /** Original deadline string (fallback for unparseable dates) */
    private String deadlineString;
    /** Whether the deadline was successfully parsed as a date */
    private boolean hasValidDate;

    /**
     * Constructs a Deadline task.
     * Attempts to parse the deadline as a date in yyyy-MM-dd format.
     *
     * @param description Task description
     * @param deadline    Deadline (either "yyyy-MM-dd" or natural language)
     */
    public Deadline(String description, String deadline) {
        super(description);
        this.deadlineString = deadline;

        try {
            this.deadlineDate = LocalDate.parse(deadline);
            this.hasValidDate = true;
        } catch (DateTimeParseException e) {
            this.hasValidDate = false;
        }
    }

    /**
     * Constructs a Deadline task with a pre-parsed date.
     * Used when loading from storage.
     *
     * @param description  Task description
     * @param deadlineDate Deadline as LocalDate
     */
    public Deadline(String description, LocalDate deadlineDate) {
        super(description);
        this.deadlineDate = deadlineDate;
        this.deadlineString = deadlineDate.toString();
        this.hasValidDate = true;
    }

    /**
     * Returns the deadline time as a formatted string for storage.
     *
     * @return Deadline time string
     */
    public String getDeadlineTime() {
        if (hasValidDate) {
            return deadlineDate.toString();
        }
        return deadlineString;
    }

    /**
     * Returns the deadline date if it was successfully parsed.
     *
     * @return LocalDate or null if not parseable
     */
    public LocalDate getDeadlineDate() {
        return deadlineDate;
    }

    /**
     * Checks whether this deadline falls on the given date.
     *
     * @param date The date to check against
     * @return true if the deadline is on the specified date
     */
    @Override
    public boolean occursOnDate(LocalDate date) {
        return hasValidDate && deadlineDate.equals(date);
    }

    /**
     * Returns string representation of the deadline task.
     * If date is parsed, displays in "MMM dd yyyy" format.
     *
     * @return Formatted string with [D] prefix and deadline information
     */
    @Override
    public String toString() {
        String deadlineDisplay;
        if (hasValidDate) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
            deadlineDisplay = deadlineDate.format(formatter);
        } else {
            deadlineDisplay = deadlineString;
        }
        return "[D]" + super.toString() + " (by: " + deadlineDisplay + ")";
    }
}