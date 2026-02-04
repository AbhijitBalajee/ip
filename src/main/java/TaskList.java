public class TaskList {
    private static final int DEFAULT_CAPACITY = 100;

    private Task[] tasks;
    private int taskCount;

    public TaskList() {
        this.tasks = new Task[DEFAULT_CAPACITY];
        this.taskCount = 0;
    }

    public void addTask(Task task) {
        tasks[taskCount] = task;
        taskCount++;
    }

    public Task getTask(int index) {
        return tasks[index];
    }

    public int getTaskCount() {
        return taskCount;
    }

    public boolean isEmpty() {
        return taskCount == 0;
    }

    public String listTasks(String indent) {
        if (isEmpty()) {
            return indent + "There are no tasks in the list!";
        }

        return buildTaskListString(indent);
    }

    private String buildTaskListString(String indent) {
        StringBuilder result = new StringBuilder();
        result.append(indent).append("Here are the tasks in your list:");

        for (int i = 0; i < taskCount; i++) {
            appendTaskToList(result, indent, i);
        }

        return result.toString();
    }

    private void appendTaskToList(StringBuilder result, String indent, int index) {
        int displayNumber = index + 1;
        result.append("\n")
                .append(indent)
                .append(displayNumber)
                .append(".")
                .append(tasks[index]);
    }
}