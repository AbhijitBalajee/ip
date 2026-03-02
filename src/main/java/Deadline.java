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
    private boolean isParsedDate;

    /**
     * Constructs a Deadline task.
     * Attempts to parse the deadline as a date in yyyy-mm-dd format.
     *
     * @param description Task description
     * @param deadline Deadline (either "yyyy-mm-dd" or natural language)
     */
    public Deadline(String description, String deadline) {
        super(description);
        this.deadlineString = deadline;

        try {
            // Try to parse as LocalDate (yyyy-mm-dd format)
            this.deadlineDate = LocalDate.parse(deadline);
            this.isParsedDate = true;
        } catch (DateTimeParseException e) {
            // If parsing fails, keep as string (e.g., "next Friday", "tomorrow")
            this.isParsedDate = false;
        }
    }

    /**
     * Constructs a Deadline task with a pre-parsed date.
     * Used when loading from storage.
     *
     * @param description Task description
     * @param deadlineDate Deadline as LocalDate
     */
    public Deadline(String description, LocalDate deadlineDate) {
        super(description);
        this.deadlineDate = deadlineDate;
        this.deadlineString = deadlineDate.toString();
        this.isParsedDate = true;
    }

    /**
     * Returns the deadline time as a formatted string.
     *
     * @return Deadline time string (for storage)
     */
    public String getDeadlineTime() {
        if (isParsedDate) {
            return deadlineDate.toString(); // Returns yyyy-mm-dd format
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
     * Checks if the deadline is on a specific date.
     *
     * @param date Date to check against
     * @return true if deadline is on the specified date
     */
    public boolean isOnDate(LocalDate date) {
        return isParsedDate && deadlineDate.equals(date);
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
        if (isParsedDate) {
            // Format as "MMM dd yyyy" (e.g., "Dec 02 2019")
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
            deadlineDisplay = deadlineDate.format(formatter);
        } else {
            deadlineDisplay = deadlineString;
        }
        return "[D]" + super.toString() + " (by: " + deadlineDisplay + ")";
    }
}