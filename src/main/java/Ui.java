import java.util.Scanner;

/**
 * Handles all user interface interactions.
 * Responsible for reading user input and displaying messages.
 */
public class Ui {
    private static final String INDENT = "    ";
    private static final String SEPARATOR = INDENT + "-------------------------------------------";
    private static final String LOGO = INDENT + "  █████╗ ██████╗ ███████╗\n"
            + INDENT + " ██╔══██╗██╔══██╗██╔════╝\n"
            + INDENT + " ███████║██████╔╝███████╗\n"
            + INDENT + " ██╔══██║██╔══██╗╚════██║\n"
            + INDENT + " ██║  ██║██████╔╝███████║\n"
            + INDENT + " ╚═╝  ╚═╝╚═════╝ ╚══════╝\n";

    private Scanner scanner;

    /**
     * Constructs a Ui object with a new scanner.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the welcome message and logo.
     */
    public void showWelcome() {
        showLine();
        System.out.println(LOGO);
        System.out.println(INDENT + "Hellooo! I'm Abs!");
        System.out.println(INDENT + "What is your name?");
        showLine();
    }

    /**
     * Reads and returns the user's name.
     * Keeps prompting until a non-empty name is provided.
     *
     * @return User's name
     */
    public String getUserName() {
        String userName = scanner.nextLine().trim();

        while (userName.isEmpty()) {
            showLine();
            System.out.println(INDENT + "I didn't catch your name! What's your name?");
            showLine();
            userName = scanner.nextLine().trim();
        }

        return userName;
    }

    /**
     * Displays a personalized greeting with the user's name.
     *
     * @param userName User's name
     */
    public void showGreeting(String userName) {
        showLine();
        System.out.println(INDENT + "Hi " + userName + "! Hope you are doing great!");
        System.out.println(INDENT + "What can I do for you?");
        showLine();
    }

    /**
     * Displays the goodbye message.
     *
     * @param userName User's name
     */
    public void showGoodbye(String userName) {
        showLine();
        System.out.println(INDENT + "Bye " + userName + "!");
        System.out.println(INDENT + "Hope to see you again realll soonnn!");
        showLine();
    }

    /**
     * Reads a command from the user.
     *
     * @return User's command, trimmed
     */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    /**
     * Displays an error message.
     *
     * @param message Error message to display
     */
    public void showError(String message) {
        System.out.println(INDENT + message);
    }

    /**
     * Displays a loading error message.
     */
    public void showLoadingError() {
        System.out.println(INDENT + "Error loading tasks from file.");
        System.out.println(INDENT + "Starting with an empty task list.");
    }

    /**
     * Displays a message prompting for a command.
     *
     * @param userName User's name
     */
    public void showEmptyCommandMessage(String userName) {
        System.out.println(INDENT + "Please enter a command, " + userName + "!");
    }

    /**
     * Displays the separator line.
     */
    public void showLine() {
        System.out.println(SEPARATOR);
    }

    /**
     * Displays a task that was marked as done.
     *
     * @param task Task that was marked
     */
    public void showTaskMarkedAsDone(Task task) {
        System.out.println(INDENT + "Nice! I've marked this task as done:");
        System.out.println(INDENT + "  " + task);
    }

    /**
     * Displays a task that was marked as not done.
     *
     * @param task Task that was unmarked
     */
    public void showTaskMarkedAsNotDone(Task task) {
        System.out.println(INDENT + "OK, I've marked this task as not done yet:");
        System.out.println(INDENT + "  " + task);
    }

    /**
     * Displays a task that was deleted.
     *
     * @param task Task that was deleted
     * @param taskCount Remaining number of tasks
     */
    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println(INDENT + "Noted. I've removed this task:");
        System.out.println(INDENT + "  " + task);
        System.out.println(INDENT + "Now you have " + taskCount + " tasks in the list.");
    }

    /**
     * Displays a task that was added.
     *
     * @param task Task that was added
     * @param taskCount Total number of tasks
     */
    public void showTaskAdded(Task task, int taskCount) {
        System.out.println(INDENT + "Got it. I've added this task:");
        System.out.println(INDENT + "  " + task);
        System.out.println(INDENT + "Now you have " + taskCount + " tasks in the list.");
    }

    /**
     * Displays the task list.
     *
     * @param formattedList Formatted string of tasks
     */
    public void showTaskList(String formattedList) {
        System.out.println(formattedList);
    }

    /**
     * Displays find results.
     *
     * @param formattedResults Formatted string of matching tasks
     */
    public void showFindResults(String formattedResults) {
        System.out.println(formattedResults);
    }

    /**
     * Closes the scanner.
     */
    public void close() {
        scanner.close();
    }
}