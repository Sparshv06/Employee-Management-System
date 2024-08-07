package application;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private Button loginButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button teamManagementButton;

    @FXML
    private void initialize() {
        // Populate ComboBox items
        roleComboBox.setItems(FXCollections.observableArrayList("admin", "operator"));
    }

    @FXML
    private void handleLoginButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String role = roleComboBox.getValue();

        User currentUser = Database.authenticate(username, password, role);
        if (currentUser != null) {
            try {
                FXMLLoader loader;
                Parent root;

                if ("admin".equals(role)) {
                    loader = new FXMLLoader(getClass().getResource("/application/admin.fxml"));
                    root = loader.load();
                } else {
                    loader = new FXMLLoader(getClass().getResource("/application/operator.fxml"));
                    root = loader.load();
                    OperatorController controller = loader.getController();
                    controller.setUser(currentUser);
                }

                Stage stage = (Stage) loginButton.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Invalid username, password, or role.");
        }
    }

    @FXML
    private void handleCancelButtonAction() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleTeamManagementButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/team_management.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Team Management");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
