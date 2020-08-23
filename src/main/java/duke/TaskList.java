package duke;

import duke.exception.*;
import duke.task.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    static final String INDENT = "    ";

    protected List<Task> list;

    public TaskList() {
        this.list = new ArrayList<>();
    }

    public TaskList(List<Task> taskList) {
        this.list = taskList;
    }

    public Task addTask(Task task) {
        this.list.add(task);
        return task;
    }

    public Task deleteTask(int index) throws DukeInvalidIndexException {
        try {
            Task toBeDeleted = get(index);
            this.list.remove(index);
            return toBeDeleted;
        } catch (IndexOutOfBoundsException e) {
            throw new DukeInvalidIndexException();
        }
    }

    public Task markDone(int index) throws DukeInvalidIndexException {
        try {
            Task task = get(index);
            task.markAsDone();
            return task;
        } catch (IndexOutOfBoundsException e) {
            throw new DukeInvalidIndexException();
        }
    }

    public static String getListAsStringFromList(List<Task>  list, String alternative) {
        String s = "";
        for (int i = 0; i < list.size(); i ++) {
            s += (i + 1) + "." + list.get(i);
            if (i != list.size() - 1) {
                s += '\n' + INDENT;
            }
        }
        if (s.equals("")) {
            return alternative;
        } else {
            return s;
        }
    }

    public String getListAsString() {
        return getListAsStringFromList(this.list, "There is nothing in the list!");
    }

    public int getSize() {
        return list.size();
    }

    public Task get(int index) {
        return this.list.get(index);
    }

}
