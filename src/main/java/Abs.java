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

        System.out.println(separator);
        System.out.println(logo);
        System.out.println(indent + "Hellooo! I'm Abs!");
        System.out.println(indent + "What can I do for you?");
        System.out.println(separator);

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        while (!input.equals("bye")) {
            System.out.println(separator);
            System.out.println(indent + input);
            System.out.println(separator);
            input = scanner.nextLine();
        }

        System.out.println(separator);
        System.out.println(indent + "Byeee. Hope to see you again realll soonnn!");
        System.out.println(separator);
    }
}
