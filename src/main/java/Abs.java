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

        Task[] tasks = new Task[100];
        int taskCount = 0;

        System.out.println(separator);
        System.out.println(logo);
        System.out.println(indent + "Hellooo! I'm Abs!");
        System.out.println(indent + "What is your name?");
        System.out.println(separator);

        Scanner scanner = new Scanner(System.in);
        String userName = scanner.nextLine();  // Get the user's name

        System.out.println(separator);
        System.out.println(indent + "Hi " + userName + "! Hope you are doing great!");
        System.out.println(indent + "What can I do for you?");
        System.out.println(separator);

        String input = scanner.nextLine();

        while (!input.equals("bye")) {
            System.out.println(separator);

            if (input.equals("list")) {
                System.out.println(indent + "Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(indent + (i + 1) + "." + tasks[i]);
                }
            } else if (input.startsWith("mark ")) {
                int taskNumber = Integer.parseInt(input.substring(5));
                tasks[taskNumber - 1].markAsDone();
                System.out.println(indent + "Nice! I've marked this task as done:");
                System.out.println(indent + "  " + tasks[taskNumber - 1]);
            } else if (input.startsWith("unmark ")) {
                int taskNumber = Integer.parseInt(input.substring(7));
                tasks[taskNumber - 1].markAsNotDone();
                System.out.println(indent + "OK, I've marked this task as not done yet:");
                System.out.println(indent + "  " + tasks[taskNumber - 1]);
            } else {
                Task newTask = new Task(input);
                tasks[taskCount] = newTask;
                taskCount++;
                System.out.println(indent + "added: " + input);
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