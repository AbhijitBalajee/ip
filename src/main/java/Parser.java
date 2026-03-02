/**
 * Parses user input into command type and arguments.
 */
public class Parser {

    /**
     * Parses the user input and returns the command type.
     *
     * @param input Full user input
     * @return Command type in lowercase
     */
    public static String getCommand(String input) {
        String[] words = input.split("\\s+", 2);
        return words[0].toLowerCase().replaceAll("\\d+.*", "");
    }

    /**
     * Extracts the description from a todo command.
     *
     * @param input Full user input
     * @return Task description
     * @throws AbsException If description is missing
     */
    public static String parseTodo(String input, String userName) throws AbsException {
        int todoLength = 5;
        if (input.length() <= todoLength || input.substring(todoLength).trim().isEmpty()) {
            throw new AbsException("Did you forget, " + userName + "? Remember to put an activity after todo!");
        }
        return input.substring(todoLength).trim();
    }

    /**
     * Parses a deadline command and returns [description, deadline].
     *
     * @param input Full user input
     * @return Array with [description, deadline]
     * @throws AbsException If format is invalid
     */
    public static String[] parseDeadline(String input, String userName) throws AbsException {
        int deadlineLength = 9;
        if (input.length() <= deadlineLength || input.substring(deadlineLength).trim().isEmpty()) {
            throw new AbsException("I need an activity, " + userName + "! Try: deadline <task> /by <time>");
        }
        String taskDetails = input.substring(deadlineLength).trim();
        if (!taskDetails.contains(" /by ")) {
            throw new AbsException("Don't forget the /by, " + userName + "! I need to know when it's due.");
        }
        String[] parts = taskDetails.split(" /by ", 2);
        return new String[]{parts[0].trim(), parts[1].trim()};
    }

    /**
     * Parses an event command and returns [description, start, end].
     *
     * @param input Full user input
     * @return Array with [description, start, end]
     * @throws AbsException If format is invalid
     */
    public static String[] parseEvent(String input, String userName) throws AbsException {
        int eventLength = 6;
        if (input.length() <= eventLength || input.substring(eventLength).trim().isEmpty()) {
            throw new AbsException("Did you forget, " + userName + "? Remember to put an activity after event!");
        }

        String taskDetails = input.substring(eventLength).trim();
        if (taskDetails.isEmpty()) {
            throw new AbsException("Did you forget? Remember to put an activity after event!");
        }

        if (!taskDetails.contains(" /from ") || !taskDetails.contains(" /to ")) {
            throw new AbsException("Don't forget to use /from and /to to specify the event timing!\n"
                    + "    Format: event <description> /from <start> /to <end>");
        }

        String[] parts = taskDetails.split(" /from ", 2);
        if (parts.length < 2) {
            throw new AbsException("I can't find the /from in your event command!\n"
                    + "    Format: event <description> /from <start> /to <end>");
        }

        String description = parts[0].trim();
        String[] timeParts = parts[1].split(" /to ", 2);

        if (timeParts.length < 2) {
            throw new AbsException("I can't find the /to in your event command!\n"
                    + "    Format: event <description> /from <start> /to <end>");
        }

        String startTime = timeParts[0].trim();
        String endTime = timeParts[1].trim();

        if (description.isEmpty() || startTime.isEmpty() || endTime.isEmpty()) {
            throw new AbsException("I need the activity, start time, and end time for the event!\n"
                    + "    Format: event <description> /from <start> /to <end>");
        }

        return new String[]{description, startTime, endTime};
    }

    /**
     * Parses a task number from commands like mark, unmark, delete.
     * Handles both "mark 1" and "mark1" formats.
     *
     * @param input       Full user input
     * @param commandName Name of the command (for error messages)
     * @return Task number (1-indexed)
     * @throws AbsException If number is missing or invalid
     */
    public static int parseTaskNumber(String input, String commandName, String userName) throws AbsException {
        String afterCommand = "";
        if (input.toLowerCase().startsWith(commandName)) {
            afterCommand = input.substring(commandName.length()).trim();
        }

        if (afterCommand.isEmpty()) {
            throw new AbsException("Which task, " + userName + "? Remember to put a number after " + commandName + "!");
        }

        try {
            String[] parts = afterCommand.split("\\s+");
            return Integer.parseInt(parts[0]);
        } catch (NumberFormatException e) {
            throw new AbsException("Sorry " + userName + ", '" + afterCommand + "' is not a valid number!");
        }
    }

    /**
     * Parses a find keyword.
     *
     * @param input Full user input
     * @return Search keyword
     * @throws AbsException If keyword is missing
     */
    public static String parseFindKeyword(String input, String userName) throws AbsException {
        String[] words = input.split("\\s+", 2);
        if (words.length < 2 || words[1].trim().isEmpty()) {
            throw new AbsException("What should I find, " + userName + "? Please provide a keyword!");
        }
        return words[1].trim();
    }

    /**
     * Parses a date string for searching tasks on a specific date.
     *
     * @param input Full user input (e.g., "occur 2019-12-02")
     * @param userName User's name for personalized error messages
     * @return LocalDate object
     * @throws AbsException If date format is invalid or missing
     */
    public static java.time.LocalDate parseDateCommand(String input, String userName) throws AbsException {
        String[] words = input.split("\\s+", 2);
        if (words.length < 2) {
            throw new AbsException("Tell me the date, " + userName + "! Use the format: yyyy-mm-dd");
        }
        try {
            return java.time.LocalDate.parse(words[1].trim());
        } catch (java.time.format.DateTimeParseException e) {
            throw new AbsException("I don't recognize that date, " + userName + "! Try yyyy-mm-dd (e.g., 2026-12-01).");
        }
    }
}