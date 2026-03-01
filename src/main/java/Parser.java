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
        return words[0].toLowerCase();
    }

    /**
     * Extracts the description from a todo command.
     *
     * @param input Full user input
     * @return Task description
     * @throws AbsException If description is missing
     */
    public static String parseTodo(String input) throws AbsException {
        int todoLength = 5; // "todo ".length()
        if (input.length() <= todoLength) {
            throw new AbsException("Did you forget? Remember to put an activity after todo!");
        }

        String description = input.substring(todoLength).trim();
        if (description.isEmpty()) {
            throw new AbsException("Did you forget? Remember to put an activity after todo!");
        }

        return description;
    }

    /**
     * Parses a deadline command and returns [description, deadline].
     *
     * @param input Full user input
     * @return Array with [description, deadline]
     * @throws AbsException If format is invalid
     */
    public static String[] parseDeadline(String input) throws AbsException {
        int deadlineLength = 9; // "deadline ".length()
        if (input.length() <= deadlineLength) {
            throw new AbsException("Did you forget? Remember to put an activity after deadline!");
        }

        String taskDetails = input.substring(deadlineLength).trim();
        if (taskDetails.isEmpty()) {
            throw new AbsException("Did you forget? Remember to put an activity after deadline!");
        }

        if (!taskDetails.contains(" /by ")) {
            throw new AbsException("Don't forget to use /by to specify the deadline time!\n"
                    + "    Format: deadline <description> /by <time>");
        }

        String[] parts = taskDetails.split(" /by ", 2);
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new AbsException("I need both the activity and the deadline time!\n"
                    + "    Format: deadline <description> /by <time>");
        }

        return new String[]{parts[0].trim(), parts[1].trim()};
    }

    /**
     * Parses an event command and returns [description, start, end].
     *
     * @param input Full user input
     * @return Array with [description, start, end]
     * @throws AbsException If format is invalid
     */
    public static String[] parseEvent(String input) throws AbsException {
        int eventLength = 6; // "event ".length()
        if (input.length() <= eventLength) {
            throw new AbsException("Did you forget? Remember to put an activity after event!");
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
     * @param input Full user input
     * @param commandName Name of the command (for error messages)
     * @return Task number (1-indexed)
     * @throws AbsException If number is missing or invalid
     */
    public static int parseTaskNumber(String input, String commandName) throws AbsException {
        // Extract everything after the command
        String afterCommand = "";

        // Remove the command from the beginning (case-insensitive)
        if (input.toLowerCase().startsWith(commandName)) {
            afterCommand = input.substring(commandName.length()).trim();
        }

        // If nothing after command
        if (afterCommand.isEmpty()) {
            throw new AbsException("Which task? Remember to put a number after " + commandName + "!");
        }

        // Try to parse as number
        try {
            // Handle case where there might be extra text after the number
            // e.g., "mark 1 hello" should just get the "1"
            String[] parts = afterCommand.split("\\s+");
            return Integer.parseInt(parts[0]);
        } catch (NumberFormatException e) {
            throw new AbsException("'" + afterCommand + "' is not a valid number! Please give me a task number.");
        }
    }

    /**
     * Parses a find keyword.
     *
     * @param input Full user input
     * @return Search keyword
     * @throws AbsException If keyword is missing
     */
    public static String parseFindKeyword(String input) throws AbsException {
        String[] words = input.split("\\s+", 2);

        if (words.length < 2 || words[1].trim().isEmpty()) {
            throw new AbsException("What should I find? Please provide a keyword!");
        }

        return words[1].trim();
    }
}