package at.nicoleperak.client.controllers;

import at.nicoleperak.client.Client;
import at.nicoleperak.client.ClientException;
import at.nicoleperak.client.ServiceFunctions;
import at.nicoleperak.shared.User;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.io.IOException;

import static at.nicoleperak.client.Client.loadScene;
import static at.nicoleperak.client.FXMLLocation.SIGN_UP_SCREEN;
import static at.nicoleperak.client.Redirection.redirectToFinancialAccountsOverviewScreen;
import static at.nicoleperak.client.Validation.assertEmailIsValid;
import static at.nicoleperak.client.Validation.assertUserInputLengthIsValid;

public class WelcomeScreenController {
    private static final Jsonb jsonb = JsonbBuilder.create();

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signInButton;

    @FXML
    private Hyperlink signUpLink;

    @FXML
    private Label alertMessageLabel;

    @FXML @SuppressWarnings("unused")
    protected void onSignUpLinkClicked(ActionEvent event) {
        try {
            Scene scene = loadScene(SIGN_UP_SCREEN);
            Client.getStage().setScene(scene);
            scene.getRoot().requestFocus();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }

    @FXML @SuppressWarnings("unused")
    protected void onSignInButtonClicked(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();
        try {
            assertUserInputLengthIsValid(email, "email address", 4, 255);
            assertEmailIsValid(email);
            assertUserInputLengthIsValid(password, "password", 8, 255);
            saveUserCredentials(new User(null, null, email, password));
            User loggedInUser = loadLoggedInUser();
            saveLoggedInUser(loggedInUser);
            redirectToFinancialAccountsOverviewScreen();
        } catch (ClientException e) {
            alertMessageLabel.setTextFill(Color.RED);
            alertMessageLabel.setText(e.getMessage());
        }
    }

    private static void saveLoggedInUser(User loggedInUser) {
        Client.setLoggedInUser(loggedInUser);
    }

    private static User loadLoggedInUser() throws ClientException {
        String jsonResponse = ServiceFunctions.get("users");
        return jsonb.fromJson(jsonResponse, User.class);
    }

    private void saveUserCredentials(User user) {
        Client.setUserCredentials(user);
    }


    public void setWelcomeScreenAlertMessageLabelText(String message) {
        alertMessageLabel.setText(message);
    }
}
