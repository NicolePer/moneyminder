package at.nicoleperak.client;

import at.nicoleperak.shared.User;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static at.nicoleperak.client.Validation.*;

public class SignUpScreenFXMLController {
    private static final Jsonb jsonb = JsonbBuilder.create();

    @FXML
    private TextField emailField;

    @FXML
    private AnchorPane goBackButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField retypePasswordField;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField usernameField;

    @FXML
    private Label warningLabel;

    @FXML
    protected void registerUser(ActionEvent event) {
        try {
            assertUserInputLengthIsValid(usernameField.getText(), "username", 1, 255);
            assertUserInputLengthIsValid(emailField.getText(), "email address", 4, 255);
            assertEmailIsValid(emailField.getText());
            assertUserInputLengthIsValid(passwordField.getText(), "password", 8, 255);
            assertPasswordsMatch(passwordField.getText(), retypePasswordField.getText());
            User newUser = new User(null, usernameField.getText(), emailField.getText(), passwordField.getText());
            ServiceFunctions.post("users", jsonb.toJson(newUser));
            redirectToWelcomeScreen("Your account has been successfully created!");
        } catch (ClientException clientException) {
            warningLabel.setText(clientException.getMessage());
        }
    }

    @FXML
    protected void showWarningIfPasswordsDiffer(KeyEvent event) {
        if (passwordsDiffer(passwordField.getText(), retypePasswordField.getText())) {
            warningLabel.setText("passwords do not match");
        } else {
            warningLabel.setText("");
        }
    }

    @FXML
    protected void onGoBackButtonClicked(MouseEvent event) {
        redirectToWelcomeScreen("");
    }




    private void redirectToWelcomeScreen(String successMessage) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/welcome-screen.fxml"));
        try {
            Parent root = loader.load();
            WelcomeScreenFXMLController controller = loader.getController();
            controller.setAlertMessageLabelText(successMessage);
            Stage stage = (Stage) goBackButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }
}

