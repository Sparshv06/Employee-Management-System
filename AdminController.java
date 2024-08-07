package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminController {

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private ComboBox<String> operatorComboBox;

    @FXML
    private void initialize() {
        loadOperators();
    }

    private void loadOperators() {
        ObservableList<String> operators = FXCollections.observableArrayList();
        String query = "SELECT username FROM users WHERE role = 'operator'";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                operators.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        operatorComboBox.setItems(operators);
    }

    @FXML
    private void handleOkButtonAction() {
        String operator = operatorComboBox.getValue();
        if (operator == null || startDatePicker.getValue() == null || endDatePicker.getValue() == null) {
            System.err.println("Please select an operator and dates.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/admin_report.fxml"));
            Parent root = loader.load();

            AdminReportController controller = loader.getController();
            controller.setUserAndDates(operator, startDatePicker.getValue(), endDatePicker.getValue());

            Stage stage = new Stage();
            stage.setTitle("Work Summaries Report");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
