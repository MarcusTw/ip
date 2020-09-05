package duke.command;

import java.io.IOException;

import duke.Storage;
import duke.TaskList;
import duke.Ui;
import duke.exception.DukeInvalidIndexException;
import duke.task.Task;

/**
 * Represents a delete task action.
 */
public class DeleteCommand implements Command {
    private final int index;

    /**
     * Class constructor with specified index.
     *
     * @param index The index of the Task to be deleted.
     */
    public DeleteCommand(int index) {
        assert(index >= 0);
        this.index = index;
    }

    /**
     * Returns false because command does not exit.
     *
     * @return false.
     */
    @Override
    public boolean shouldExit() {
        return false;
    }

    /**
     * Performs the deleting of task from task list.
     *
     * @param tasks The TaskList to delete the task from.
     * @param ui The Ui to show responses or error messages.
     * @param storage The Storage to save the TaskList.
     * @return True because Duke should continue running.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            Task task = tasks.deleteTask(index);
            storage.save(tasks);
            return ui.showDeleted(task, tasks.getSize());
        } catch (DukeInvalidIndexException e) {
            return ui.showError(e);
        } catch (IOException e) {
            return ui.showError(e);
        }
    }
}
