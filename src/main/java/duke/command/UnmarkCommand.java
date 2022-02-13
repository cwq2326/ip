package duke.command;

import java.io.IOException;

import duke.Storage;
import duke.exception.BingChillingException;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.MessageUi;

/**
 * Represents commands which unmark a task in the task list. A MarkCommand
 * object corresponds a valid Ekud command, which can then be executed.
 */
public class UnmarkCommand implements Command {

    private String fullCommand;
    private String[] splitFullCommand;
    private int position;

    /**
     * Constructor for this class.
     * @param fullCommand User's input.
     */
    public UnmarkCommand(String fullCommand) {
        this.fullCommand = fullCommand;
        this.splitFullCommand = this.fullCommand.split(" ", 2);
        this.position = Integer.parseInt(splitFullCommand[1]);
    }

    /**
     * Executes a valid Ekud command that marks a task in the task list.
     * The position of which the task is supplied by the user, of which
     * if the postion is < 1 or more than the number of tasks in the task list,
     * a BingChillingException is thrown.
     *
     * @param tasks   Task object.
     * @param storage Storage object.
     * @param ui      Ui object.
     * @throws BingChillingException If position < 1 or more than number of tasks in task list.
     * @throws IOException   If directory or file cannot be found.
     */
    @Override
    public String execute(TaskList tasks, Storage storage,
                        MessageUi ui) throws BingChillingException {
        if (position < 1 || position > tasks.getTaskSize()) {
            throw new BingChillingException("Task do not exist!");
        } else {
            Task task = tasks.getTask(position);
            if (!task.isDone()) {
                throw new BingChillingException(
                        "duke.task.Task is already marked as not done!");
            } else {
                task.unmark();
                storage.setInFile(position, task.taskDescriptionForFile());
                return ui.showUnMarkMessage(task);
            }
        }
    }
}
