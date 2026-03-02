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
     * @param input   Full user input
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

        case "date":
            handleDate(input);
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
        ui.showTaskList(taskList.listTasks("    ", userName));
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

        int taskNumber = Parser.parseTaskNumber(input, "mark", userName);
        if (taskNumber < 1 || taskNumber > taskList.getTaskCount()) {
            throw new AbsException("Oops " + userName + "! Task number " + taskNumber
                    + " doesn't exist in your list! You have " + taskList.getTaskCount() + " tasks.");
        }

        Task task = taskList.getTask(taskNumber - 1);
        taskList.markTaskAsDone(task);
        ui.showTaskMarkedAsDone(task, userName);
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

        int taskNumber = Parser.parseTaskNumber(input, "unmark", userName);
        if (taskNumber < 1 || taskNumber > taskList.getTaskCount()) {
            throw new AbsException("Oops " + userName + "! Task number " + taskNumber
                    + " doesn't exist in your list! You have " + taskList.getTaskCount() + " tasks.");
        }

        Task task = taskList.getTask(taskNumber - 1);
        taskList.markTaskAsNotDone(task);
        ui.showTaskMarkedAsNotDone(task, userName);

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

        int taskNumber = Parser.parseTaskNumber(input, "delete", userName);
        if (taskNumber < 1 || taskNumber > taskList.getTaskCount()) {
            throw new AbsException("Oops " + userName + "! Task number " + taskNumber
                    + " doesn't exist in your list! You have " + taskList.getTaskCount() + " tasks.");
        }

        Task deletedTask = taskList.deleteTask(taskNumber - 1);
        ui.showTaskDeleted(deletedTask, taskList.getTaskCount(), userName);
    }

    /**
     * Handles the todo command.
     *
     * @param input Full user input
     * @throws AbsException If todo creation fails
     */
    private void handleTodo(String input) throws AbsException {
        String description = Parser.parseTodo(input, userName); // Personalized Parser
        Task newTask = new Todo(description);
        taskList.addTask(newTask);
        ui.showTaskAdded(newTask, taskList.getTaskCount(), userName); // Personalized UI
    }

    /**
     * Handles the deadline command.
     *
     * @param input Full user input
     * @throws AbsException If deadline creation fails
     */
    private void handleDeadline(String input) throws AbsException {
        String[] parts = Parser.parseDeadline(input, userName);
        Task newTask = new Deadline(parts[0], parts[1]);
        taskList.addTask(newTask);
        ui.showTaskAdded(newTask, taskList.getTaskCount(), userName);
    }

    /**
     * Handles the event command.
     *
     * @param input Full user input
     * @throws AbsException If event creation fails
     */
    private void handleEvent(String input) throws AbsException {
        String[] parts = Parser.parseEvent(input, userName);
        Task newTask = new Event(parts[0], parts[1], parts[2]);
        taskList.addTask(newTask);
        ui.showTaskAdded(newTask, taskList.getTaskCount(), userName);
    }

    /**
     * Handles the find command.
     *
     * @param input Full user input
     * @throws AbsException If find fails
     */
    private void handleFind(String input) throws AbsException {
        String keyword = Parser.parseFindKeyword(input, userName);
        ui.showFindResults(taskList.findTasks(keyword, "    ", userName));
    }

    /**
     * Handles the occur command to find tasks on a specific date.
     *
     * @param input Full user input
     * @throws AbsException If date parsing fails
     */
    private void handleDate(String input) throws AbsException {
        java.time.LocalDate searchDate = Parser.parseDateCommand(input, userName);

        System.out.println("    Okay " + userName + ", here are the tasks on " +
                searchDate.format(java.time.format.DateTimeFormatter.ofPattern("MMM dd yyyy")) + ":");
        int count = 0;
        for (int i = 0; i < taskList.getTaskCount(); i++) {
            Task task = taskList.getTask(i);
            boolean isMatch = false;

            if (task instanceof Deadline) {
                isMatch = ((Deadline) task).isOnDate(searchDate);
            } else if (task instanceof Event) {
                Event e = (Event) task;
                if (e.getStartDateTime() != null && e.getEndDateTime() != null) {
                    java.time.LocalDate start = e.getStartDateTime().toLocalDate();
                    java.time.LocalDate end = e.getEndDateTime().toLocalDate();
                    isMatch = !searchDate.isBefore(start) && !searchDate.isAfter(end);
                }
            }

            if (isMatch) {
                count++;
                System.out.println("    " + count + "." + task);
            }
        }

        if (count == 0) {
            System.out.println("    Nothing scheduled for this date! Rest easy.");
        }
    }
}