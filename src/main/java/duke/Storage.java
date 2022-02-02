package duke;

import duke.exception.DukeException;
import duke.task.Task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * A class that interacts with the directory of the file. A file in this context
 * refers to the text file that stores the content of the task list.
 */
public class Storage {

    private Ui ui;
    private String filePath;

    public Storage(String path) {
        this.filePath = path;
        this.ui = new Ui();
    }

    /**
     * Creates a folder in a directory and then a file in it.
     *
     * @throws IOException If directory cannot be found.
     */
    public static void createNewFolderAndTextFile() throws IOException {
        File directory = new File("data");
        directory.mkdir();
        File file = new File(directory, "ekud.txt");
        file.createNewFile();
    }

    /**
     * Appends new task to the file.
     *
     * @param textToAdd Information of the task to be added to the file.
     * @throws IOException If the directory or file cannot be found.
     */
    public void appendToFile(String textToAdd) throws IOException {
        FileWriter fw = new FileWriter(filePath, true);
        fw.write(textToAdd);
        fw.close();
    }

    /**
     * Overwrites the content of a specific task index in the file.
     * Used for command such as mark and unmark.
     *
     * @param lineNumber The index of the task to be modified.
     * @param data       The content of the task in the file.
     * @throws IOException If directory or file cannot be found.
     */
    public void setInFile(int lineNumber, String data) throws IOException {
        Path path = Paths.get(filePath);
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        lines.set(lineNumber - 1, data);
        Files.write(path, lines, StandardCharsets.UTF_8);
    }

    /**
     * Overwrites the entire content of the file.
     * Used for command such as delete.
     *
     * @param taskList The task list.
     * @throws IOException If directory or file cannot be found.
     */
    public void writeToFile(List<Task> taskList) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        for (Task task : taskList) {
            stringBuilder.append(task.taskDescriptionForFile()
                    + System.lineSeparator());
        }
        FileWriter fw = new FileWriter(filePath);
        fw.write(stringBuilder.toString());
        fw.close();
    }

    /**
     * Load the contents of the file and store it as a list of strings.
     *
     * @return List of type String.
     * @throws DukeException If there is an error reading the file.
     * @throws IOException   If there is an error reading the file.
     */
    public List<String> loadFileContents() throws DukeException, IOException {
        boolean gotError = false;
        try {
            Path path = Paths.get(filePath);
            Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException err) {
            gotError = true;
            throw new DukeException("Error loading file!");
        } finally {
            if (!gotError) {
                Path path = Paths.get(filePath);
                List<String> data = Files.readAllLines(path, StandardCharsets.UTF_8);
                return data;
            } else {
                return null;
            }
        }
    }
}