import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Data {
    private String filePath;
    private List<Task> tasksList;

    Data(String filePath) throws DukeInvalidData {
        this.filePath = filePath;
        try {
            this.tasksList = read(filePath);
        } catch (DukeInvalidData dukeInvalidData) {
            System.out.println(dukeInvalidData.getMessage());
            throw new DukeInvalidData("Invalid Data provided");
        }
    }

    public List<Task> getTasksList() {
        return tasksList;
    }

    public void add(Task task) {
        this.tasksList.add(task);
        try {
            this.save();
        } catch (IOException e) {
            System.out.println("Saving error! Data not saved.");
        }
    }

    public void delete(int index) {
        this.tasksList.remove(index);
        try {
            this.save();
        } catch (IOException e) {
            System.out.println("Saving error! Data not saved.");
        }
    }

    public void markDone(int index) {
        Task t = tasksList.get(index);
        t.markAsDone();
        try {
            this.save();
        } catch (IOException e) {
            System.out.println("Saving error! Data not saved.");
        }
    }

    static List<Task> read(String filePath) throws DukeInvalidData {
        File file = new File(filePath);
        List<Task> list = new ArrayList<>();
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {
                String next = sc.nextLine();
                String[] tokens = next.split(" \\| ");
                if (tokens.length < 3) {
                    throw new DukeInvalidData("Oops data is invalid");
                }
                String taskType = tokens[0];
                String isDone = tokens[1];
                String description = tokens[2];
                Task task;
                if (taskType.equals("T")) {
                    task = new Todo(description);
                } else if (tokens.length != 4) {
                    throw new DukeInvalidData("Oops data is invalid");
                } else if (taskType.equals("D")) {
                    task = new Deadline(description, tokens[3]);
                } else if (taskType.equals("E")) {
                    task = new Event(description, tokens[3]);
                } else {
                    throw new DukeInvalidData("Oops data is invalid");
                }

                if (isDone.equals("1")) {
                    task.markAsDone();
                    list.add(task);
                } else if (isDone.equals("0")) {
                    list.add(task);
                } else {
                    throw new DukeInvalidData("Oops data is invalid");
                }
            }
        } catch (FileNotFoundException e) {
            FileWriter writer;
            try {
                writer = new FileWriter(filePath);
                writer.write("");
            } catch (IOException ioException) {
                System.out.println(ioException.getMessage());
                list = new ArrayList<>();
            }
        }
        return list;
    }

    private void save() throws IOException {
        File file = new File(filePath);
        FileWriter writer = new FileWriter(file);
        for (int i = 0; i < tasksList.size(); i++) {
            Task task = tasksList.get(i);
            TaskType type = task.taskType;
            String s = "";
            switch (type) {
                case TODO:
                    s = String.format("T | %d | %s", task.isDone ? 1 : 0, task.description);
                    break;
                case DEADLINE:
                    Deadline deadline = (Deadline) task;
                    s = String.format("D | %d | %s | %s", deadline.isDone ? 1 : 0,
                            deadline.description, deadline.by);
                    break;
                case EVENT:
                    Event event = (Event) task;
                    s = String.format("E | %d | %s | %s", event.isDone ? 1 : 0,
                            event.description, event.at);
                    break;
                default:
                    break;
            }

            if (i != tasksList.size() - 1) {
                s += "\n";
            }
            writer.write(s);
        }
        writer.close();
    }
}
