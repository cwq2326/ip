package duke.gui;

import duke.Duke;
import duke.exception.DukeException;
import duke.ui.AlertUi;
import duke.ui.MessageUi;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.awt.*;


/**
 * Controller for duke.Gui.MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;
    @FXML
    private Stage stage;
    @FXML
    private Label welcomeMessage;
    @FXML
    private Label taskMessage;

    private Duke duke;
    private MessageUi messageUi = new MessageUi();

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/Ekud.png"));

    public MainWindow() {
    }

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    public void setDuke(Duke d) {
        duke = d;
        welcomeMessage.setText(new MessageUi().showWelcomeMessage());
        welcomeMessage.setWrapText(true);
        taskMessage.setText(duke.getFileLoadingMessage());
        taskMessage.setWrapText(true);
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        try {
            String response = duke.getResponse(input);
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getDukeDialog(response, dukeImage)
            );
            return;
        } catch (DukeException e) {
            AlertUi.makeErrorAlert("DukeException", e.getMessage());
        } finally {
            userInput.clear();
            return;
        }
    }
}



