public class TaskList {
    private Task[] tasks;
    private int taskCount;

    public TaskList() {
        this.tasks = new Task[100];
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

        StringBuilder result = new StringBuilder();
        result.append(indent).append("Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            result.append("\n").append(indent).append(i + 1).append(".").append(tasks[i]);
        }
        return result.toString();
    }
}