/**
 * Manages a collection of tasks with a fixed maximum capacity.
 */
public class TaskList {
    /** Default maximum number of tasks that can be stored */
    private static final int DEFAULT_CAPACITY = 100;

    /** Array storing the tasks */
    private Task[] tasks;
    /** Current number of tasks in the list */
    private int taskCount;

    /**
     * Constructs an empty task list with default capacity.
     */
    public TaskList() {
        this.tasks = new Task[DEFAULT_CAPACITY];
        this.taskCount = 0;
    }

    /**
     * Adds a task to the list.
     *
     * @param task Task to be added
     */
    public void addTask(Task task) {
        tasks[taskCount] = task;
        taskCount++;
    }

    /**
     * Retrieves a task at the specified index.
     *
     * @param index Index of the task (0-based)
     * @return Task at the specified index
     */
    public Task getTask(int index) {
        return tasks[index];
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return Number of tasks currently stored
     */
    public int getTaskCount() {
        return taskCount;
    }

    /**
     * Checks if the task list is empty.
     *
     * @return true if list contains no tasks, false otherwise
     */
    public boolean isEmpty() {
        return taskCount == 0;
    }

    /**
     * Returns a formatted string of all tasks in the list.
     *
     * @param indent Indentation string to use for formatting
     * @return Formatted string containing all tasks or empty message
     */
    public String listTasks(String indent) {
        if (isEmpty()) {
            return indent + "There are no tasks in the list!";
        }

        return buildTaskListString(indent);
    }

    /**
     * Builds the formatted task list string.
     *
     * @param indent Indentation string for formatting
     * @return Formatted string with all tasks numbered
     */
    private String buildTaskListString(String indent) {
        StringBuilder result = new StringBuilder();
        result.append(indent).append("Here are the tasks in your list:");

        for (int i = 0; i < taskCount; i++) {
            appendTaskToList(result, indent, i);
        }

        return result.toString();
    }

    /**
     * Appends a single task to the string builder.
     *
     * @param result StringBuilder to append to
     * @param indent Indentation string
     * @param index Index of the task to append (0-based)
     */
    private void appendTaskToList(StringBuilder result, String indent, int index) {
        int displayNumber = index + 1;
        result.append("\n")
                .append(indent)
                .append(displayNumber)
                .append(".")
                .append(tasks[index]);
    }
}