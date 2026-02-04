import java.util.Scanner;

public class Abs {
    public static void main(String[] args) {
        String indent = "    ";
        String separator = indent + "-------------------------------------------";
        String logo = indent + "  █████╗ ██████╗ ███████╗\n"
                + indent + " ██╔══██╗██╔══██╗██╔════╝\n"
                + indent + " ███████║██████╔╝███████╗\n"
                + indent + " ██╔══██║██╔══██╗╚════██║\n"
                + indent + " ██║  ██║██████╔╝███████║\n"
                + indent + " ╚═╝  ╚═╝╚═════╝ ╚══════╝\n";

        TaskList taskList = new TaskList();  // Using TaskList class now

        System.out.println(separator);
        System.out.println(logo);
        System.out.println(indent + "Hellooo! I'm Abs!");
        System.out.println(indent + "What is your name?");
        System.out.println(separator);

        Scanner scanner = new Scanner(System.in);
        String userName = scanner.nextLine();

        System.out.println(separator);
        System.out.println(indent + "Hi " + userName + "! Hope you are doing great!");
        System.out.println(indent + "What can I do for you?");
        System.out.println(separator);

        String input = scanner.nextLine();

        while (!input.equals("bye")) {
            System.out.println(separator);

            if (input.equals("list")) {
                System.out.println(taskList.listTasks(indent));  // Simplified!
            } else if (input.startsWith("mark ")) {
                int taskNumber = Integer.parseInt(input.substring(5));
                taskList.getTask(taskNumber - 1).markAsDone();
                System.out.println(indent + "Nice! I've marked this task as done:");
                System.out.println(indent + "  " + taskList.getTask(taskNumber - 1));
            } else if (input.startsWith("unmark ")) {
                int taskNumber = Integer.parseInt(input.substring(7));
                taskList.getTask(taskNumber - 1).markAsNotDone();
                System.out.println(indent + "OK, I've marked this task as not done yet:");
                System.out.println(indent + "  " + taskList.getTask(taskNumber - 1));
            } else if (input.startsWith("todo ")) {
                String description = input.substring(5);
                Task newTask = new Todo(description);
                taskList.addTask(newTask);
                System.out.println(indent + "Got it. I've added this task:");
                System.out.println(indent + "  " + newTask);
                System.out.println(indent + "Now you have " + taskList.getTaskCount() + " tasks in the list.");
            } else if (input.startsWith("deadline ")) {
                String[] parts = input.substring(9).split(" /by ");
                String description = parts[0];
                String by = parts[1];
                Task newTask = new Deadline(description, by);
                taskList.addTask(newTask);
                System.out.println(indent + "Got it. I've added this task:");
                System.out.println(indent + "  " + newTask);
                System.out.println(indent + "Now you have " + taskList.getTaskCount() + " tasks in the list.");
            } else if (input.startsWith("event ")) {
                String[] parts = input.substring(6).split(" /from | /to ");
                String description = parts[0];
                String from = parts[1];
                String to = parts[2];
                Task newTask = new Event(description, from, to);
                taskList.addTask(newTask);
                System.out.println(indent + "Got it. I've added this task:");
                System.out.println(indent + "  " + newTask);
                System.out.println(indent + "Now you have " + taskList.getTaskCount() + " tasks in the list.");
            } else {
                System.out.println(indent + "I don't understand that command!");
            }

            System.out.println(separator);
            input = scanner.nextLine();
        }

        System.out.println(separator);
        System.out.println(indent + "Bye " + userName + "!");
        System.out.println(indent + "Hope to see you again realll soonnn!");
        System.out.println(separator);
    }
}