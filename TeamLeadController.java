package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeamLeadController {

    @FXML
    private ComboBox<String> teamMembersComboBox;

    @FXML
    private void initialize() {
        loadTeamMembers();
    }

    private void loadTeamMembers() {
        ObservableList<String> teamMembers = FXCollections.observableArrayList();
        String query = "SELECT username FROM users WHERE role = 'team_member'";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                teamMembers.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        teamMembersComboBox.setItems(teamMembers);
    }

    @FXML
    private void handleViewTeamMemberButtonAction() {
        String teamMember = teamMembersComboBox.getValue();
        if (teamMember != null) {
            showAlert("Team Member", "Team Member: " + teamMember);
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
