import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles loading and saving of tasks to/from a file.
 */
public class Storage {
    /** File path for storing tasks */
    private final String filePath;

    /**
     * Constructs a Storage object with the specified file path.
     *
     * @param filePath Path to the data file (e.g., "./data/tasks.txt")
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the file.
     * Creates the file and directory if they don't exist.
     *
     * @return ArrayList of tasks loaded from file
     * @throws StorageException If file reading fails or data is corrupted
     */
    public ArrayList<Task> load() throws StorageException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        // Create directory and file if they don't exist
        if (!file.exists()) {
            createFileAndDirectory(file);
            return tasks; // Return empty list for new file
        }

        // Read and parse the file
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i).trim();
                if (line.isEmpty()) {
                    continue; // Skip empty lines
                }

                try {
                    Task task = parseTaskFromLine(line);
                    tasks.add(task);
                } catch (StorageException e) {
                    // Log corrupted line but continue loading other tasks
                    System.out.println("Warning: Corrupted data at line " + (i + 1) + ": " + line);
                }
            }
        } catch (IOException e) {
            throw new StorageException("Error reading from file: " + e.getMessage());
        }

        return tasks;
    }

    /**
     * Saves all tasks to the file.
     *
     * @param tasks ArrayList of tasks to save
     * @throws StorageException If file writing fails
     */
    public void save(ArrayList<Task> tasks) throws StorageException {
        File file = new File(filePath);

        // Ensure directory exists
        if (!file.getParentFile().exists()) {
            createFileAndDirectory(file);
        }

        try (FileWriter writer = new FileWriter(file)) {
            for (Task task : tasks) {
                writer.write(formatTaskForFile(task) + "\n");
            }
        } catch (IOException e) {
            throw new StorageException("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Creates the file and its parent directory if they don't exist.
     *
     * @param file File to create
     * @throws StorageException If directory or file creation fails
     */
    private void createFileAndDirectory(File file) throws StorageException {
        try {
            File directory = file.getParentFile();
            if (directory != null && !directory.exists()) {
                if (!directory.mkdirs()) {
                    throw new StorageException("Failed to create directory: " + directory.getPath());
                }
            }
            if (!file.exists() && !file.createNewFile()) {
                throw new StorageException("Failed to create file: " + file.getPath());
            }
        } catch (IOException e) {
            throw new StorageException("Error creating file: " + e.getMessage());
        }
    }

    /**
     * Parses a task from a line in the file.
     * Format: TYPE | DONE | DESCRIPTION | [EXTRA_INFO]
     *
     * @param line Line from the file
     * @return Parsed Task object
     * @throws StorageException If line format is invalid
     */
    private Task parseTaskFromLine(String line) throws StorageException {
        String[] parts = line.split(" \\| ");

        if (parts.length < 3) {
            throw new StorageException("Invalid task format: " + line);
        }

        String type = parts[0].trim();
        boolean isDone = parts[1].trim().equals("1");
        String description = parts[2].trim();

        Task task;

        try {
            switch (type) {
            case "T":
                if (parts.length != 3) {
                    throw new StorageException("Invalid Todo format: " + line);
                }
                task = new Todo(description);
                break;

            case "D":
                if (parts.length != 4) {
                    throw new StorageException("Invalid Deadline format: " + line);
                }
                String by = parts[3].trim();
                task = new Deadline(description, by);
                break;

            case "E":
                if (parts.length != 5) {
                    throw new StorageException("Invalid Event format: " + line);
                }
                String from = parts[3].trim();
                String to = parts[4].trim();
                task = new Event(description, from, to);
                break;

            default:
                throw new StorageException("Unknown task type: " + type);
            }

            if (isDone) {
                task.markAsDone();
            }

            return task;

        } catch (ArrayIndexOutOfBoundsException e) {
            throw new StorageException("Corrupted task data: " + line);
        }
    }

    /**
     * Formats a task for saving to file.
     * Format: TYPE | DONE | DESCRIPTION | [EXTRA_INFO]
     *
     * @param task Task to format
     * @return Formatted string for file
     */
    private String formatTaskForFile(Task task) {
        String isDone = task.getStatusIcon().equals("X") ? "1" : "0";
        String description = task.getDescription();

        if (task instanceof Todo) {
            return "T | " + isDone + " | " + description;

        } else if (task instanceof Deadline) {
            Deadline deadline = (Deadline) task;
            return "D | " + isDone + " | " + description + " | " + deadline.getDeadlineTime();

        } else if (task instanceof Event) {
            Event event = (Event) task;
            return "E | " + isDone + " | " + description + " | "
                    + event.getStartTime() + " | " + event.getEndTime();
        }

        return "";
    }
}