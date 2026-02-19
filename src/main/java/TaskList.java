import java.util.ArrayList;

/**
 * Manages a collection of tasks using an ArrayList.
 * Notifies storage when tasks are modified.
 * Manages a collection of tasks using an ArrayList.
 */
public class TaskList {
    /** List storing all tasks */
    private ArrayList<Task> tasks;
    /** Storage handler for saving tasks */
    private Storage storage;
    /** List storing all tasks */
    private ArrayList<Task> tasks;

    /**
     * Constructs an empty task list.
     *
     * @param storage Storage handler for saving tasks
     * Constructs an empty task list.
     */
    public TaskList(Storage storage) {
        this.tasks = new ArrayList<>();
        this.storage = storage;
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a task to the list and saves to storage.
     *
     * @param task Task to be added
     */
    public void addTask(Task task) {
        tasks.add(task);
        saveToStorage();
        tasks.add(task);
    }

    /**
     * Retrieves a task at the specified index.
     *
     * @param index Index of the task (0-based)
     * @return Task at the specified index
     */
    public Task getTask(int index) {
        return tasks.get(index);
    }

    /**
     * Deletes a task at the specified index and saves to storage.
     *
     * @param index Index of the task to delete (0-based)
     * @return The deleted task
     */
    public Task deleteTask(int index) {
        Task deletedTask = tasks.remove(index);
        saveToStorage();
        return deletedTask;
    }

    /**
     * Marks a task and saves to storage.
     *
     * @param task Task to mark as done
     */
    public void markTaskAsDone(Task task) {
        task.markAsDone();
        saveToStorage();
    }

    /**
     * Unmarks a task and saves to storage.
     *
     * @param task Task to mark as not done
     */
    public void markTaskAsNotDone(Task task) {
        task.markAsNotDone();
        saveToStorage();
        return tasks.get(index);
    }

    /**
     * Deletes a task at the specified index.
     *
     * @param index Index of the task to delete (0-based)
     * @return The deleted task
     */
    public Task deleteTask(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return Number of tasks currently stored
     */
    public int getTaskCount() {
        return tasks.size();
    }

    /**
     * Checks if the task list is empty.
     *
     * @return true if list contains no tasks, false otherwise
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Loads tasks from storage into the list.
     *
     * @throws StorageException If loading fails
     */
    public void loadTasks() throws StorageException {
        this.tasks = storage.load();
        return tasks.isEmpty();
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
     * Finds and returns all tasks containing the keyword.
     *
     * @param keyword Keyword to search for
     * @param indent Indentation string for formatting
     * @return Formatted string with matching tasks
     */
    public String findTasks(String keyword, String indent) {
        StringBuilder result = new StringBuilder();
        result.append(indent).append("Here are the matching tasks in your list:");

        int matchCount = 0;
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getDescription().contains(keyword)) {
                result.append("\n")
                        .append(indent)
                        .append(++matchCount)
                        .append(".")
                        .append(tasks.get(i));
            }
        }

        if (matchCount == 0) {
            return indent + "No matching tasks found!";
        }

        return result.toString();
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

        for (int i = 0; i < tasks.size(); i++) {
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
                .append(tasks.get(index));
    }

    /**
     * Saves the current task list to storage.
     * Prints error message if save fails.
     */
    private void saveToStorage() {
        try {
            storage.save(tasks);
        } catch (StorageException e) {
            System.out.println("    Error saving tasks: " + e.getMessage());
        }
                .append(tasks.get(index));
    }
}