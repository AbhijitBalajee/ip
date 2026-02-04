import java.util.Scanner;

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

    private static final int MARK_PREFIX_LENGTH = 5;
    private static final int UNMARK_PREFIX_LENGTH = 7;
    private static final int TODO_PREFIX_LENGTH = 5;
    private static final int DEADLINE_PREFIX_LENGTH = 9;
    private static final int EVENT_PREFIX_LENGTH = 6;

    private TaskList taskList;
    private Scanner scanner;
    private String userName;

    public Abs() {
        this.taskList = new TaskList();
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        Abs chatBot = new Abs();
        chatBot.run();
    }

    public void run() {
        greetUser();
        getUserName();
        handleUserCommands();
        sayGoodbye();
    }

    private void greetUser() {
        printSeparator();
        System.out.println(LOGO);
        System.out.println(INDENT + "Hellooo! I'm Abs!");
        System.out.println(INDENT + "What is your name?");
        printSeparator();
    }

    private void getUserName() {
        userName = scanner.nextLine();
        printSeparator();
        System.out.println(INDENT + "Hi " + userName + "! Hope you are doing great!");
        System.out.println(INDENT + "What can I do for you?");
        printSeparator();
    }

    private void handleUserCommands() {
        String input = scanner.nextLine();

        while (!input.equals(COMMAND_BYE)) {
            printSeparator();
            processCommand(input);
            printSeparator();
            input = scanner.nextLine();
        }
    }

    private void processCommand(String input) {
        if (input.equals(COMMAND_LIST)) {
            handleListCommand();

        } else if (input.startsWith(COMMAND_PREFIX_MARK)) {
            handleMarkCommand(input);
        } else if (input.startsWith(COMMAND_PREFIX_UNMARK)) {
            handleUnmarkCommand(input);

        } else if (input.startsWith(COMMAND_PREFIX_TODO)) {
            handleTodoCommand(input);
        } else if (input.startsWith(COMMAND_PREFIX_DEADLINE)) {
            handleDeadlineCommand(input);
        } else if (input.startsWith(COMMAND_PREFIX_EVENT)) {
            handleEventCommand(input);

        } else {
            handleUnknownCommand();
        }
    }

    private void handleListCommand() {
        System.out.println(taskList.listTasks(INDENT));
    }

    private void handleMarkCommand(String input) {
        int taskNumber = parseTaskNumber(input, MARK_PREFIX_LENGTH);
        Task task = taskList.getTask(taskNumber - 1);
        task.markAsDone();
        printTaskMarkedAsDone(task);
    }

    private void handleUnmarkCommand(String input) {
        int taskNumber = parseTaskNumber(input, UNMARK_PREFIX_LENGTH);
        Task task = taskList.getTask(taskNumber - 1);
        task.markAsNotDone();
        printTaskMarkedAsNotDone(task);
    }

    private void handleTodoCommand(String input) {
        String description = input.substring(TODO_PREFIX_LENGTH);
        Task newTask = new Todo(description);
        addTaskAndConfirm(newTask);
    }

    private void handleDeadlineCommand(String input) {
        String taskDetails = input.substring(DEADLINE_PREFIX_LENGTH);
        String[] parts = taskDetails.split(" /by ");
        String description = parts[0];
        String deadline = parts[1];

        Task newTask = new Deadline(description, deadline);
        addTaskAndConfirm(newTask);
    }

    private void handleEventCommand(String input) {
        String taskDetails = input.substring(EVENT_PREFIX_LENGTH);
        String[] eventParts = parseEventDetails(taskDetails);

        Task newTask = new Event(eventParts[0], eventParts[1], eventParts[2]);
        addTaskAndConfirm(newTask);
    }

    private void handleUnknownCommand() {
        System.out.println(INDENT + "I don't understand that command!");
    }

    private String[] parseEventDetails(String taskDetails) {
        String[] parts = taskDetails.split(" /from ");
        String description = parts[0];
        String[] timeParts = parts[1].split(" /to ");
        String startTime = timeParts[0];
        String endTime = timeParts[1];

        return new String[]{description, startTime, endTime};
    }

    private void addTaskAndConfirm(Task task) {
        taskList.addTask(task);
        System.out.println(INDENT + "Got it. I've added this task:");
        System.out.println(INDENT + "  " + task);
        System.out.println(INDENT + "Now you have " + taskList.getTaskCount() + " tasks in the list.");
    }

    private void printTaskMarkedAsDone(Task task) {
        System.out.println(INDENT + "Nice! I've marked this task as done:");
        System.out.println(INDENT + "  " + task);
    }

    private void printTaskMarkedAsNotDone(Task task) {
        System.out.println(INDENT + "OK, I've marked this task as not done yet:");
        System.out.println(INDENT + "  " + task);
    }

    private int parseTaskNumber(String input, int prefixLength) {
        return Integer.parseInt(input.substring(prefixLength));
    }

    private void sayGoodbye() {
        printSeparator();
        System.out.println(INDENT + "Bye " + userName + "!");
        System.out.println(INDENT + "Hope to see you again realll soonnn!");
        printSeparator();
    }

    private void printSeparator() {
        System.out.println(SEPARATOR);
    }
}