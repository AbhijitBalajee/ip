import java.util.ArrayList;

/**
 * Manages a collection of tasks using an ArrayList.
 * Notifies storage when tasks are modified.
 */
public class TaskList {
    /** List storing all tasks */
    private ArrayList<Task> tasks;
    /** Storage handler for saving tasks */
    private Storage storage;

    /**
     * Constructs an empty task list.
     *
     * @param storage Storage handler for saving tasks
     */
    public TaskList(Storage storage) {
        this.tasks = new ArrayList<>();
        this.storage = storage;
    }

    /**
     * Adds a task to the list and saves to storage.
     *
     * @param task Task to be added
     */
    public void addTask(Task task) {
        tasks.add(task);
        saveToStorage();
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
    }

    /**
     * Returns a formatted string of all tasks in the list.
     *
     * @param indent Indentation string to use for formatting
     * @return Formatted string containing all tasks or empty message
     */
    public String listTasks(String indent, String userName) {
        if (isEmpty()) {
            return indent + "Your list is empty, " + userName + "! Nothing to do yet.";
        }
        StringBuilder result = new StringBuilder();
        result.append(indent).append("Here are the tasks in your list, ").append(userName).append(":");
        for (int i = 0; i < tasks.size(); i++) {
            result.append("\n").append(indent).append(i + 1).append(".").append(tasks.get(i));
        }
        return result.toString();
    }

    /**
     * Finds and returns all tasks containing the keyword (case-insensitive).
     *
     * @param keyword Keyword to search for
     * @param indent  Indentation string for formatting
     * @return Formatted string with matching tasks
     */
    public String findTasks(String keyword, String indent, String userName) {
        StringBuilder result = new StringBuilder();
        result.append(indent).append("Here are the matching tasks I found for you, ").append(userName).append(":");

        int matchCount = 0;
        String lowerKeyword = keyword.toLowerCase();
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(lowerKeyword)) {
                matchCount++;
                result.append("\n").append(indent).append(matchCount).append(".").append(task);
            }
        }
        if (matchCount == 0) {
            return indent + "I couldn't find any tasks matching '" + keyword + "', " + userName + ".";
        }
        return result.toString();
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
    }
}