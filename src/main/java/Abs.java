import java.nio.file.Paths;

/**
 * Main chatbot application that manages a task list.
 * Coordinates between Ui, Storage, TaskList, and Parser.
 */
public class Abs {
    private static final String DATA_FILE_PATH = Paths.get("data", "abs.txt").toString();

    private Storage storage;
    private TaskList taskList;
    private Ui ui;
    private String userName;

    /**
     * Constructs an Abs chatbot instance.
     * Initializes storage, task list, and UI.
     */
    public Abs() {
        ui = new Ui();
        storage = new Storage(DATA_FILE_PATH);
        try {
            taskList = new TaskList(storage);
            taskList.loadTasks();
        } catch (StorageException e) {
            ui.showLoadingError();
            taskList = new TaskList(storage);
        }
    }

    /**
     * Main entry point for the application.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        new Abs().run();
    }

    /**
     * Runs the main chatbot loop.
     */
    public void run() {
        ui.showWelcome();
        userName = ui.getUserName();
        ui.showGreeting(userName);

        boolean isExit = false;
        while (!isExit) {
            String input = ui.readCommand();

            if (input.isEmpty()) {
                ui.showLine();
                ui.showEmptyCommandMessage(userName);
                ui.showLine();
                continue;
            }

            ui.showLine();
            try {
                String command = Parser.getCommand(input);
                isExit = executeCommand(command, input);
            } catch (AbsException e) {
                ui.showError(e.getMessage());
            }
            ui.showLine();
        }

        ui.close();
    }

    /**
     * Executes a command based on the command type.
     *
     * @param command Command type
     * @param input Full user input
     * @return true if command is "bye", false otherwise
     * @throws AbsException If command execution fails
     */
    private boolean executeCommand(String command, String input) throws AbsException {
        switch (command) {
        case "bye":
            ui.showGoodbye(userName);
            return true;

        case "list":
            handleList();
            break;

        case "mark":
            handleMark(input);
            break;

        case "unmark":
            handleUnmark(input);
            break;

        case "delete":
            handleDelete(input);
            break;

        case "todo":
            handleTodo(input);
            break;

        case "deadline":
            handleDeadline(input);
            break;

        case "event":
            handleEvent(input);
            break;

        case "find":
            handleFind(input);
            break;

        default:
            throw new AbsException("I don't understand that " + userName + "! Please try again!");
        }

        return false;
    }

    /**
     * Handles the list command.
     */
    private void handleList() {
        ui.showTaskList(taskList.listTasks("    "));
    }

    /**
     * Handles the mark command.
     *
     * @param input Full user input
     * @throws AbsException If marking fails
     */
    private void handleMark(String input) throws AbsException {
        if (taskList.isEmpty()) {
            throw new AbsException("There is nothing on the list to mark " + userName + "!");
        }

        int taskNumber = Parser.parseTaskNumber(input, "mark");
        if (taskNumber < 1 || taskNumber > taskList.getTaskCount()) {
            throw new AbsException("Oops " + userName + "! Task number " + taskNumber
                    + " doesn't exist in your list! You have " + taskList.getTaskCount() + " tasks.");
        }

        Task task = taskList.getTask(taskNumber - 1);
        taskList.markTaskAsDone(task);
        ui.showTaskMarkedAsDone(task);
    }

    /**
     * Handles the unmark command.
     *
     * @param input Full user input
     * @throws AbsException If unmarking fails
     */
    private void handleUnmark(String input) throws AbsException {
        if (taskList.isEmpty()) {
            throw new AbsException("There is nothing on the list to unmark " + userName + "!");
        }

        int taskNumber = Parser.parseTaskNumber(input, "unmark");
        if (taskNumber < 1 || taskNumber > taskList.getTaskCount()) {
            throw new AbsException("Oops " + userName + "! Task number " + taskNumber
                    + " doesn't exist in your list! You have " + taskList.getTaskCount() + " tasks.");
        }

        Task task = taskList.getTask(taskNumber - 1);
        taskList.markTaskAsNotDone(task);
        ui.showTaskMarkedAsNotDone(task);
    }

    /**
     * Handles the delete command.
     *
     * @param input Full user input
     * @throws AbsException If deletion fails
     */
    private void handleDelete(String input) throws AbsException {
        if (taskList.isEmpty()) {
            throw new AbsException("There is nothing on the list to delete " + userName + "!");
        }

        int taskNumber = Parser.parseTaskNumber(input, "delete");
        if (taskNumber < 1 || taskNumber > taskList.getTaskCount()) {
            throw new AbsException("Oops " + userName + "! Task number " + taskNumber
                    + " doesn't exist in your list! You have " + taskList.getTaskCount() + " tasks.");
        }

        Task deletedTask = taskList.deleteTask(taskNumber - 1);
        ui.showTaskDeleted(deletedTask, taskList.getTaskCount());
    }

    /**
     * Handles the todo command.
     *
     * @param input Full user input
     * @throws AbsException If todo creation fails
     */
    private void handleTodo(String input) throws AbsException {
        String description = Parser.parseTodo(input);
        Task newTask = new Todo(description);
        taskList.addTask(newTask);
        ui.showTaskAdded(newTask, taskList.getTaskCount());
    }

    /**
     * Handles the deadline command.
     *
     * @param input Full user input
     * @throws AbsException If deadline creation fails
     */
    private void handleDeadline(String input) throws AbsException {
        String[] parts = Parser.parseDeadline(input);
        Task newTask = new Deadline(parts[0], parts[1]);
        taskList.addTask(newTask);
        ui.showTaskAdded(newTask, taskList.getTaskCount());
    }

    /**
     * Handles the event command.
     *
     * @param input Full user input
     * @throws AbsException If event creation fails
     */
    private void handleEvent(String input) throws AbsException {
        String[] parts = Parser.parseEvent(input);
        Task newTask = new Event(parts[0], parts[1], parts[2]);
        taskList.addTask(newTask);
        ui.showTaskAdded(newTask, taskList.getTaskCount());
    }

    /**
     * Handles the find command.
     *
     * @param input Full user input
     * @throws AbsException If find fails
     */
    private void handleFind(String input) throws AbsException {
        String keyword = Parser.parseFindKeyword(input);
        ui.showFindResults(taskList.findTasks(keyword, "    "));
    }
}