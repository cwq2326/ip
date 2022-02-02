package duke.gui;

import duke.Duke;
import duke.exception.DukeException;
import duke.ui.AlertUi;
import duke.ui.MessageUi;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * Controller for duke.Gui.MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private VBox bannerPane;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;
    @FXML
    private Stage stage;
    @FXML
    private Label welcomeMessage;

    private Duke duke;

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
        welcomeMessage.setText(new MessageUi().showWelcomeMessage() + " " + duke.getFileLoadingMessage());
        welcomeMessage.setWrapText(true);
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private int handleUserInput() {
        String input = userInput.getText();
        if (input.equals("bye")) {
            Alert alert = AlertUi.makeConfirmationAlert("Exit Ekud?", "Kill me?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                AlertUi.makeInformationAlert("BYE!", new MessageUi().showExitMessage());
                System.exit(0);
            }
            userInput.clear();
            return 0;
        } else {
            if (input.equals("clear")) {
                Alert alert = AlertUi.makeConfirmationAlert("Clear task list?",
                        "Do you want Ekud to clear your task list??");
                if (alert.showAndWait().get() == ButtonType.CANCEL) {
                    userInput.clear();
                    return 0;
                }
            } else {
                try {
                    String response = duke.getResponse(input);
                    dialogContainer.getChildren().addAll(
                            DialogBox.getUserDialog(input, userImage),
                            DialogBox.getDukeDialog(response, dukeImage)
                    );
                    return 0;
                } catch (DukeException e) {
                    AlertUi.makeErrorAlert("DukeException", e.getMessage());
                    return 0;
                } finally {
                    userInput.clear();
                    return 0;
                }
            }
        }
        return 0;
    }
}