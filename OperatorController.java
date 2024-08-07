package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class OperatorController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private DatePicker workDatePicker;

    @FXML
    private TextArea summaryTextArea;

    @FXML
    private Button addButton;

    @FXML
    private Button viewButton;

    private User user;

    public void setUser(User user) {
        this.user = user;
        welcomeLabel.setText("Welcome, " + user.getUsername() + " (Operator)");
    }

    @FXML
    private void handleAddButtonAction() {
        if (workDatePicker.getValue() == null || summaryTextArea.getText().isEmpty()) {
            System.err.println("Work date or summary cannot be empty.");
            return;
        }

        Date workDate = Date.valueOf(workDatePicker.getValue());
        String summary = summaryTextArea.getText();

        String query = "INSERT INTO work_summary (user_id, work_date, summary) VALUES (?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, user.getId());
            stmt.setDate(2, workDate);
            stmt.setString(3, summary);
            stmt.executeUpdate();

            // Show success alert
            showAlert("Summary Saved", "Summary saved successfully!");

            // Clear input fields
            workDatePicker.setValue(null);
            summaryTextArea.clear();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleViewButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view_summaries.fxml"));
            Parent root = loader.load();

            ViewSummariesController controller = loader.getController();
            controller.setUser(user);

            Stage stage = new Stage();
            stage.setTitle("Work Summaries");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
