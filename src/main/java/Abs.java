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
    private static final String COMMAND_PREFIX_MARK = "mark ";
    private static final String COMMAND_PREFIX_UNMARK = "unmark ";
    private static final String COMMAND_PREFIX_TODO = "todo ";
    private static final String COMMAND_PREFIX_DEADLINE = "deadline ";
    private static final String COMMAND_PREFIX_EVENT = "event ";
    private static final String COMMAND_PREFIX_DELETE = "delete ";
    private static final String COMMAND_PREFIX_DELETE = "delete ";
    private static final String COMMAND_PREFIX_FIND = "find ";
    private static final String DATA_FILE_PATH = Paths.get("data", "abs.txt").toString();


    private static final int MARK_PREFIX_LENGTH = 5;
    private static final int UNMARK_PREFIX_LENGTH = 7;
    private static final int TODO_PREFIX_LENGTH = 5;
    private static final int DEADLINE_PREFIX_LENGTH = 9;
    private static final int EVENT_PREFIX_LENGTH = 6;
    private static final int DELETE_PREFIX_LENGTH = 7;
    private static final int DELETE_PREFIX_LENGTH = 7;
    private static final int FIND_PREFIX_LENGTH = 5;

    private static final String DATA_FILE_PATH = "./data/abs.txt";

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
        userName = scanner.nextLine();
        printSeparator();
        System.out.println(INDENT + "Hi " + userName + "! Hope you are doing great!");
        System.out.println(INDENT + "What can I do for you?");
        printSeparator();
    }

    /**
     * Processes user commands in a loop until "bye" is entered.
     */
    private void handleUserCommands() {
        String input = scanner.nextLine();

        while (!input.equals(COMMAND_BYE)) {
            printSeparator();
            try {
                processCommand(input);
            } catch (AbsException e) {
                System.out.println(INDENT + e.getMessage());
            }
            printSeparator();
            input = scanner.nextLine();
        }
    }

    /**
     * Processes a single user command.
     *
     * @param input User command string
     * @throws AbsException If command is invalid or has missing parameters
     */
    private void processCommand(String input) throws AbsException {
        if (input.equals(COMMAND_LIST)) {
            handleListCommand();

        } else if (input.startsWith(COMMAND_PREFIX_MARK)) {
            handleMarkCommand(input);
        } else if (input.startsWith(COMMAND_PREFIX_UNMARK)) {
            handleUnmarkCommand(input);
        } else if (input.startsWith(COMMAND_PREFIX_DELETE)) {
            handleDeleteCommand(input);
        } else if (input.startsWith(COMMAND_PREFIX_TODO)) {
            handleTodoCommand(input);
        } else if (input.startsWith(COMMAND_PREFIX_DEADLINE)) {
            handleDeadlineCommand(input);
        } else if (input.startsWith(COMMAND_PREFIX_EVENT)) {
            handleEventCommand(input);

        } else if (input.equals("todo") || input.equals("deadline") || input.equals("event")
                || input.equals("mark") || input.equals("unmark")) {
            handleCommandWithoutArguments(input);

        } else {
            throw new AbsException("I don't understand that " + userName + "! Please try again!");
        }
    }

    /**
     * Handles commands that were entered without required arguments.
     *
     * @param input The command without arguments
     * @throws AbsException With a helpful error message for the specific command
     */
    private void handleCommandWithoutArguments(String input) throws AbsException {
        if (input.equals("todo")) {
            throw new AbsException("Did you forget " + userName + "? Remember to put an activity after todo!");
        } else if (input.equals("deadline")) {
            throw new AbsException("Did you forget " + userName + "? Remember to put an activity after deadline!");
        } else if (input.equals("event")) {
            throw new AbsException("Did you forget " + userName + "? Remember to put an activity after event!");
        } else if (input.equals("mark")) {
            if (taskList.isEmpty()) {
                throw new AbsException("There is nothing on the list to mark " + userName + "!");
            }
            throw new AbsException("Which task " + userName + "? Remember to put a number after mark!");
        } else if (input.equals("unmark")) {
            if (taskList.isEmpty()) {
                throw new AbsException("There is nothing on the list to unmark " + userName + "!");
            }
            throw new AbsException("Which task " + userName + "? Remember to put a number after unmark!");
        } else if (input.equals("delete")) {
            if (taskList.isEmpty()) {
                throw new AbsException("There is nothing on the list to delete " + userName + "!");
            }
            throw new AbsException("Which task " + userName + "? Remember to put a number after delete!");
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
     * @param input User command containing task number to mark
     * @throws AbsException If task number is invalid or list is empty
     */
    private void handleMarkCommand(String input) throws AbsException {
        if (taskList.isEmpty()) {
            throw new AbsException("There is nothing on the list to mark " + userName + "!");
        }

        try {
            int taskNumber = parseTaskNumber(input, MARK_PREFIX_LENGTH);
            if (taskNumber < 1 || taskNumber > taskList.getTaskCount()) {
                throw new AbsException("Oops " + userName + "! Task number " + taskNumber
                        + " doesn't exist in your list!");
            }
            Task task = taskList.getTask(taskNumber - 1);
            taskList.markTaskAsDone(task);  // Changed this line
            printTaskMarkedAsDone(task);
        } catch (NumberFormatException e) {
            throw new AbsException("Hey " + userName + "! Please give me a valid task number to mark!");
        } catch (StringIndexOutOfBoundsException e) {
            throw new AbsException("Which task " + userName + "? Remember to put a number after mark!");
        }
    }

    /**
     * Handles the unmark command to mark a task as not done.
     *
     * @param input User command containing task number to unmark
     * @throws AbsException If task number is invalid or list is empty
     */
    private void handleUnmarkCommand(String input) throws AbsException {
        if (taskList.isEmpty()) {
            throw new AbsException("There is nothing on the list to unmark " + userName + "!");
        }

        try {
            int taskNumber = parseTaskNumber(input, UNMARK_PREFIX_LENGTH);
            if (taskNumber < 1 || taskNumber > taskList.getTaskCount()) {
                throw new AbsException("Oops " + userName + "! Task number " + taskNumber
                        + " doesn't exist in your list!");
            }
            Task task = taskList.getTask(taskNumber - 1);
            taskList.markTaskAsNotDone(task);  // Changed this line
            printTaskMarkedAsNotDone(task);
        } catch (NumberFormatException e) {
            throw new AbsException("Hey " + userName + "! Please give me a valid task number to unmark!");
        } catch (StringIndexOutOfBoundsException e) {
            throw new AbsException("Which task " + userName + "? Remember to put a number after unmark!");
        }
    }

    /**
     * Handles the delete command to remove a task from the list.
     *
     * @param input User command containing task number to delete
     * @throws AbsException If task number is invalid or list is empty
     */
    private void handleDeleteCommand(String input) throws AbsException {
        if (taskList.isEmpty()) {
            throw new AbsException("There is nothing on the list to delete " + userName + "!");
        }

        try {
            int taskNumber = parseTaskNumber(input, DELETE_PREFIX_LENGTH);
            if (taskNumber < 1 || taskNumber > taskList.getTaskCount()) {
                throw new AbsException("Oops " + userName + "! Task number " + taskNumber
                        + " doesn't exist in your list!");
            }
            Task deletedTask = taskList.deleteTask(taskNumber - 1);
            printTaskDeleted(deletedTask);
        } catch (NumberFormatException e) {
            throw new AbsException("Hey " + userName + "! Please give me a valid task number to delete!");
        } catch (StringIndexOutOfBoundsException e) {
            throw new AbsException("Which task " + userName + "? Remember to put a number after delete!");
        }
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
        String taskDetails = input.substring(DEADLINE_PREFIX_LENGTH).trim();
        if (taskDetails.isEmpty()) {
            throw new AbsException("Did you forget " + userName + "? Remember to put an activity after deadline!");
        }

        if (!taskDetails.contains(" /by ")) {
            throw new AbsException("Hey " + userName + "! Don't forget to use /by to specify the deadline time!");
        }

        String[] parts = taskDetails.split(" /by ");
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new AbsException(userName + ", I need both the activity and the deadline time!");
        }

        String description = parts[0];
        String deadline = parts[1];

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
        String taskDetails = input.substring(EVENT_PREFIX_LENGTH).trim();
        if (taskDetails.isEmpty()) {
            throw new AbsException("Did you forget " + userName + "? Remember to put an activity after event!");
        }

        if (!taskDetails.contains(" /from ") || !taskDetails.contains(" /to ")) {
            throw new AbsException("Hey " + userName
                    + "! Don't forget to use /from and /to to specify the event timing!");
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
        String[] parts = taskDetails.split(" /from ");
        if (parts.length < 2) {
            throw new AbsException(userName + ", I can't find the /from in your event command!");
        }

        String description = parts[0];
        String[] timeParts = parts[1].split(" /to ");

        if (timeParts.length < 2) {
            throw new AbsException(userName + ", I can't find the /to in your event command!");
        }

        String startTime = timeParts[0];
        String endTime = timeParts[1];

        if (description.trim().isEmpty() || startTime.trim().isEmpty() || endTime.trim().isEmpty()) {
            throw new AbsException(userName + ", I need the activity, start time, and end time for the event!");
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
     * Parses the task number from a user command.
     *
     * @param input User command string
     * @param prefixLength Length of the command prefix to skip
     * @return Task number entered by user
     * @throws NumberFormatException If the task number is not a valid integer
     */
    private int parseTaskNumber(String input, int prefixLength) {
        return Integer.parseInt(input.substring(prefixLength));
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