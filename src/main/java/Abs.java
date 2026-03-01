import java.util.Scanner;
import java.nio.file.Paths;

/**
 * Main chatbot application that manages a task list.
 * Handles user commands for adding, listing, marking, and unmarking tasks.
 * Automatically saves and loads tasks from file.
 */
public class Abs {
    private static final String INDENT = "    ";
    private static final String SEPARATOR = INDENT + "-------------------------------------------";
    private static final String LOGO = INDENT + "  █████╗ ██████╗ ███████╗\n"
            + INDENT + " ██╔══██╗██╔══██╗██╔════╝\n"
            + INDENT + " ███████║██████╔╝███████╗\n"
            + INDENT + " ██╔══██║██╔══██╗╚════██║\n"
            + INDENT + " ██║  ██║██████╔╝███████║\n"
            + INDENT + " ╚═╝  ╚═╝╚═════╝ ╚══════╝\n";

    private static final String COMMAND_BYE = "bye";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_TODO = "todo";
    private static final String COMMAND_DEADLINE = "deadline";
    private static final String COMMAND_EVENT = "event";
    private static final String COMMAND_MARK = "mark";
    private static final String COMMAND_UNMARK = "unmark";
    private static final String COMMAND_DELETE = "delete";
    private static final String COMMAND_FIND = "find";

    private static final String DATA_FILE_PATH = Paths.get("data", "abs.txt").toString();

    private static final int TODO_PREFIX_LENGTH = 5;
    private static final int DEADLINE_PREFIX_LENGTH = 9;
    private static final int EVENT_PREFIX_LENGTH = 6;

    private Storage storage;
    private TaskList taskList;
    private Scanner scanner;
    private String userName;

    /**
     * Constructs a new Abs chatbot instance.
     * Initializes storage, task list, and scanner for user input.
     */
    public Abs() {
        this.storage = new Storage(DATA_FILE_PATH);
        this.taskList = new TaskList(storage);
        this.scanner = new Scanner(System.in);
    }

    /**
     * Main entry point for the application.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        Abs chatBot = new Abs();
        chatBot.run();
    }

    /**
     * Runs the main chatbot loop.
     * Loads tasks, greets user, gets their name, processes commands, and says goodbye.
     */
    public void run() {
        loadTasks();
        greetUser();
        getUserName();
        handleUserCommands();
        sayGoodbye();
    }

    /**
     * Loads tasks from storage.
     * Displays error message if loading fails.
     */
    private void loadTasks() {
        try {
            taskList.loadTasks();
        } catch (StorageException e) {
            printSeparator();
            System.out.println(INDENT + "Error loading tasks: " + e.getMessage());
            System.out.println(INDENT + "Starting with an empty task list.");
            printSeparator();
        }
    }

    /**
     * Displays the initial greeting message and logo.
     */
    private void greetUser() {
        printSeparator();
        System.out.println(LOGO);
        System.out.println(INDENT + "Hellooo! I'm Abs!");
        System.out.println(INDENT + "What is your name?");
        printSeparator();
    }

    /**
     * Gets the user's name and displays a personalized greeting.
     */
    private void getUserName() {
        userName = scanner.nextLine().trim();

        // Handle empty name
        while (userName.isEmpty()) {
            printSeparator();
            System.out.println(INDENT + "I didn't catch your name! What's your name?");
            printSeparator();
            userName = scanner.nextLine().trim();
        }

        printSeparator();
        System.out.println(INDENT + "Hi " + userName + "! Hope you are doing great!");
        System.out.println(INDENT + "What can I do for you?");
        printSeparator();
    }

    /**
     * Processes user commands in a loop until "bye" is entered.
     */
    private void handleUserCommands() {
        String input = scanner.nextLine().trim();

        while (!input.equalsIgnoreCase(COMMAND_BYE)) {
            printSeparator();

            // Handle empty input
            if (input.trim().isEmpty()) {
                System.out.println(INDENT + "Please enter a command, " + userName + "!");
                printSeparator();
                input = scanner.nextLine().trim();
                continue;
            }

            try {
                processCommand(input);
            } catch (AbsException e) {
                System.out.println(INDENT + e.getMessage());
            }
            printSeparator();
            input = scanner.nextLine().trim();
        }
    }

    /**
     * Processes a single user command.
     *
     * @param input User command string
     * @throws AbsException If command is invalid or has missing parameters
     */
    private void processCommand(String input) throws AbsException {
        // Extract command (first word, case-insensitive)
        String[] words = input.split("\\s+", 2);
        String command = words[0].toLowerCase();

        switch (command) {
        case COMMAND_LIST:
            handleListCommand();
            break;

        case COMMAND_MARK:
            handleMarkCommand(input, words);
            break;

        case COMMAND_UNMARK:
            handleUnmarkCommand(input, words);
            break;

        case COMMAND_DELETE:
            handleDeleteCommand(input, words);
            break;

        case COMMAND_TODO:
            handleTodoCommand(input);
            break;

        case COMMAND_DEADLINE:
            handleDeadlineCommand(input);
            break;

        case COMMAND_EVENT:
            handleEventCommand(input);
            break;

        case COMMAND_FIND:
            handleFindCommand(input, words);
            break;

        default:
            throw new AbsException("I don't understand that " + userName + "! Please try again!");
        }
    }

    /**
     * Handles the list command by displaying all tasks.
     */
    private void handleListCommand() {
        System.out.println(taskList.listTasks(INDENT));
    }

    /**
     * Handles the mark command to mark a task as done.
     *
     * @param input Full user command
     * @param words Command split into words
     * @throws AbsException If task number is invalid or list is empty
     */
    private void handleMarkCommand(String input, String[] words) throws AbsException {
        if (taskList.isEmpty()) {
            throw new AbsException("There is nothing on the list to mark " + userName + "!");
        }

        if (words.length < 2 || words[1].trim().isEmpty()) {
            throw new AbsException("Which task " + userName + "? Remember to put a number after mark!");
        }

        try {
            int taskNumber = Integer.parseInt(words[1].trim());
            if (taskNumber < 1 || taskNumber > taskList.getTaskCount()) {
                throw new AbsException("Oops " + userName + "! Task number " + taskNumber
                        + " doesn't exist in your list! You have " + taskList.getTaskCount() + " tasks.");
            }
            Task task = taskList.getTask(taskNumber - 1);
            taskList.markTaskAsDone(task);
            printTaskMarkedAsDone(task);
        } catch (NumberFormatException e) {
            throw new AbsException("Hey " + userName + "! '" + words[1]
                    + "' is not a valid number! Please give me a task number.");
        }
    }

    /**
     * Handles the unmark command to mark a task as not done.
     *
     * @param input Full user command
     * @param words Command split into words
     * @throws AbsException If task number is invalid or list is empty
     */
    private void handleUnmarkCommand(String input, String[] words) throws AbsException {
        if (taskList.isEmpty()) {
            throw new AbsException("There is nothing on the list to unmark " + userName + "!");
        }

        if (words.length < 2 || words[1].trim().isEmpty()) {
            throw new AbsException("Which task " + userName + "? Remember to put a number after unmark!");
        }

        try {
            int taskNumber = Integer.parseInt(words[1].trim());
            if (taskNumber < 1 || taskNumber > taskList.getTaskCount()) {
                throw new AbsException("Oops " + userName + "! Task number " + taskNumber
                        + " doesn't exist in your list! You have " + taskList.getTaskCount() + " tasks.");
            }
            Task task = taskList.getTask(taskNumber - 1);
            taskList.markTaskAsNotDone(task);
            printTaskMarkedAsNotDone(task);
        } catch (NumberFormatException e) {
            throw new AbsException("Hey " + userName + "! '" + words[1]
                    + "' is not a valid number! Please give me a task number.");
        }
    }

    /**
     * Handles the delete command to remove a task from the list.
     *
     * @param input Full user command
     * @param words Command split into words
     * @throws AbsException If task number is invalid or list is empty
     */
    private void handleDeleteCommand(String input, String[] words) throws AbsException {
        if (taskList.isEmpty()) {
            throw new AbsException("There is nothing on the list to delete " + userName + "!");
        }

        if (words.length < 2 || words[1].trim().isEmpty()) {
            throw new AbsException("Which task " + userName + "? Remember to put a number after delete!");
        }

        try {
            int taskNumber = Integer.parseInt(words[1].trim());
            if (taskNumber < 1 || taskNumber > taskList.getTaskCount()) {
                throw new AbsException("Oops " + userName + "! Task number " + taskNumber
                        + " doesn't exist in your list! You have " + taskList.getTaskCount() + " tasks.");
            }
            Task deletedTask = taskList.deleteTask(taskNumber - 1);
            printTaskDeleted(deletedTask);
        } catch (NumberFormatException e) {
            throw new AbsException("Hey " + userName + "! '" + words[1]
                    + "' is not a valid number! Please give me a task number.");
        }
    }

    /**
     * Handles the find command to search for tasks.
     *
     * @param input Full user command
     * @param words Command split into words
     * @throws AbsException If keyword is missing
     */
    private void handleFindCommand(String input, String[] words) throws AbsException {
        if (words.length < 2 || words[1].trim().isEmpty()) {
            throw new AbsException("What should I find " + userName + "? Please provide a keyword!");
        }

        String keyword = words[1].trim();
        System.out.println(taskList.findTasks(keyword, INDENT));
    }

    /**
     * Displays confirmation message for deleting a task.
     *
     * @param task Task that was deleted
     */
    private void printTaskDeleted(Task task) {
        System.out.println(INDENT + "Noted. I've removed this task:");
        System.out.println(INDENT + "  " + task);
        System.out.println(INDENT + "Now you have " + taskList.getTaskCount() + " tasks in the list.");
    }

    /**
     * Handles the todo command to add a simple task.
     *
     * @param input User command containing task description
     * @throws AbsException If description is empty
     */
    private void handleTodoCommand(String input) throws AbsException {
        if (input.length() <= TODO_PREFIX_LENGTH) {
            throw new AbsException("Did you forget " + userName + "? Remember to put an activity after todo!");
        }

        String description = input.substring(TODO_PREFIX_LENGTH).trim();
        if (description.isEmpty()) {
            throw new AbsException("Did you forget " + userName + "? Remember to put an activity after todo!");
        }

        Task newTask = new Todo(description);
        addTaskAndConfirm(newTask);
    }

    /**
     * Handles the deadline command to add a task with a deadline.
     *
     * @param input User command containing task description and deadline
     * @throws AbsException If description or deadline is missing or invalid format
     */
    private void handleDeadlineCommand(String input) throws AbsException {
        if (input.length() <= DEADLINE_PREFIX_LENGTH) {
            throw new AbsException("Did you forget " + userName + "? Remember to put an activity after deadline!");
        }

        String taskDetails = input.substring(DEADLINE_PREFIX_LENGTH).trim();
        if (taskDetails.isEmpty()) {
            throw new AbsException("Did you forget " + userName + "? Remember to put an activity after deadline!");
        }

        if (!taskDetails.contains(" /by ")) {
            throw new AbsException("Hey " + userName + "! Don't forget to use /by to specify the deadline time!\n"
                    + INDENT + "Format: deadline <description> /by <time>");
        }

        String[] parts = taskDetails.split(" /by ", 2);
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new AbsException(userName + ", I need both the activity and the deadline time!\n"
                    + INDENT + "Format: deadline <description> /by <time>");
        }

        String description = parts[0].trim();
        String deadline = parts[1].trim();

        Task newTask = new Deadline(description, deadline);
        addTaskAndConfirm(newTask);
    }

    /**
     * Handles the event command to add a task with start and end times.
     *
     * @param input User command containing task description, start time, and end time
     * @throws AbsException If any required information is missing or invalid format
     */
    private void handleEventCommand(String input) throws AbsException {
        if (input.length() <= EVENT_PREFIX_LENGTH) {
            throw new AbsException("Did you forget " + userName + "? Remember to put an activity after event!");
        }

        String taskDetails = input.substring(EVENT_PREFIX_LENGTH).trim();
        if (taskDetails.isEmpty()) {
            throw new AbsException("Did you forget " + userName + "? Remember to put an activity after event!");
        }

        if (!taskDetails.contains(" /from ") || !taskDetails.contains(" /to ")) {
            throw new AbsException("Hey " + userName
                    + "! Don't forget to use /from and /to to specify the event timing!\n"
                    + INDENT + "Format: event <description> /from <start> /to <end>");
        }

        String[] eventParts = parseEventDetails(taskDetails);

        Task newTask = new Event(eventParts[0], eventParts[1], eventParts[2]);
        addTaskAndConfirm(newTask);
    }

    /**
     * Parses event details from the task details string.
     *
     * @param taskDetails String containing description, start time, and end time
     * @return Array containing [description, startTime, endTime]
     * @throws AbsException If parsing fails or required information is missing
     */
    private String[] parseEventDetails(String taskDetails) throws AbsException {
        String[] parts = taskDetails.split(" /from ", 2);
        if (parts.length < 2) {
            throw new AbsException(userName + ", I can't find the /from in your event command!\n"
                    + INDENT + "Format: event <description> /from <start> /to <end>");
        }

        String description = parts[0].trim();
        String[] timeParts = parts[1].split(" /to ", 2);

        if (timeParts.length < 2) {
            throw new AbsException(userName + ", I can't find the /to in your event command!\n"
                    + INDENT + "Format: event <description> /from <start> /to <end>");
        }

        String startTime = timeParts[0].trim();
        String endTime = timeParts[1].trim();

        if (description.isEmpty() || startTime.isEmpty() || endTime.isEmpty()) {
            throw new AbsException(userName + ", I need the activity, start time, and end time for the event!\n"
                    + INDENT + "Format: event <description> /from <start> /to <end>");
        }

        return new String[]{description, startTime, endTime};
    }

    /**
     * Adds a task to the task list and displays confirmation message.
     *
     * @param task Task to be added
     */
    private void addTaskAndConfirm(Task task) {
        taskList.addTask(task);
        System.out.println(INDENT + "Got it. I've added this task:");
        System.out.println(INDENT + "  " + task);
        System.out.println(INDENT + "Now you have " + taskList.getTaskCount() + " tasks in the list.");
    }

    /**
     * Displays confirmation message for marking a task as done.
     *
     * @param task Task that was marked as done
     */
    private void printTaskMarkedAsDone(Task task) {
        System.out.println(INDENT + "Nice! I've marked this task as done:");
        System.out.println(INDENT + "  " + task);
    }

    /**
     * Displays confirmation message for marking a task as not done.
     *
     * @param task Task that was marked as not done
     */
    private void printTaskMarkedAsNotDone(Task task) {
        System.out.println(INDENT + "OK, I've marked this task as not done yet:");
        System.out.println(INDENT + "  " + task);
    }

    /**
     * Displays the goodbye message to the user.
     */
    private void sayGoodbye() {
        printSeparator();
        System.out.println(INDENT + "Bye " + userName + "!");
        System.out.println(INDENT + "Hope to see you again realll soonnn!");
        printSeparator();
    }

    /**
     * Prints a separator line for visual formatting.
     */
    private void printSeparator() {
        System.out.println(SEPARATOR);
    }
}