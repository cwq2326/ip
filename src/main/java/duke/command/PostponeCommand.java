package duke.command;

import duke.Storage;
import duke.exception.DukeException;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.MessageUi;

import java.time.LocalDate;

public class PostponeCommand implements Command {

    private String fullCommand;
    private String[] splicedFullCommand;
    private int position;
    private LocalDate localDate;

    public PostponeCommand(String fullCommand) {
        this.fullCommand = fullCommand;
        this.splicedFullCommand = fullCommand.split(" ");
        this.position = Integer.parseInt(splicedFullCommand[1]);
        this.localDate = LocalDate.parse(splicedFullCommand[2], Task.getInputDateFormat());
    }

    @Override
    public String execute(TaskList tasks, Storage storage, MessageUi ui) throws DukeException {
        System.out.println("postpone execute");
        if (position < 1 || position > tasks.getTaskSize()) {
            System.out.println("postpone OOB");
            throw new DukeException("Task do not exist!");
        } else {
            Task task = tasks.getTask(position);
            assert task.getType().equals("deadline") || task.getType().equals("event");
            task.setDate(localDate);
            storage.setInFile(position, task.taskDescriptionForFile());
            return ui.showPostponeMessage(task);
        }
    }
}