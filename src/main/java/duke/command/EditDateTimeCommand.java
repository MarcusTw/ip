package duke.command;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import duke.backend.Storage;
import duke.backend.TaskList;
import duke.exception.DukeInvalidIndexException;
import duke.exception.DukeInvalidUpdateException;
import duke.ui.Ui;

/**
 * Represents an updating task command.
 */
public class EditDateTimeCommand implements Command {
    private final String description;
    private final int index;
    private final LocalDate date;
    private final LocalTime time;

    /**
     * Class constructor.
     *
     * @param description The description of task.
     * @param date The updated date.
     * @param time The update time.
     */
    public EditDateTimeCommand(String description, LocalDate date, LocalTime time) {
        this.description = description;
        this.index = 0;
        this.date = date;
        this.time = time;
    }

    /**
     * Class constructor.
     *
     * @param index The index of the task.
     * @param date The updated date.
     * @param time The updated time.
     */
    public EditDateTimeCommand(int index, LocalDate date, LocalTime time) {
        this.description = null;
        this.index = index;
        this.date = date;
        this.time = time;
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
     * Performs the action to be taken.
     *
     * @param tasks   The TaskList to add the task to.
     * @param ui      The Ui to show responses or error messages.
     * @param storage The Storage to save the TaskList.
     * @return True if Duke should continue running.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            if (index <= 0) {
                String result = tasks.editDateTime(description, date, time);
                storage.save(tasks);
                return result;
            } else if (description == null) {
                String result = tasks.editDateTime(index - 1, date, time);
                storage.save(tasks);
                return result;
            }
            return tasks.editDateTime(description, date, time);
        } catch (DukeInvalidUpdateException | IOException | DukeInvalidIndexException e) {
            return ui.showError(e);
        }
    }
}
