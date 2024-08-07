package application;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectManagerController {

    @FXML
    private ComboBox<String> teamsComboBox;

    @FXML
    private ComboBox<String> teamLeadComboBox;

    @FXML
    private ComboBox<String> teamMembersComboBox;

    @FXML
    private void initialize() {
        loadTeams();
    }

    private void loadTeams() {
        ObservableList<String> teams = FXCollections.observableArrayList();
        String query = "SELECT name FROM teams";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                teams.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        teamsComboBox.setItems(teams);
    }

    @FXML
    private void handleTeamSelection() {
        String selectedTeam = teamsComboBox.getValue();
        if (selectedTeam != null) {
            loadTeamLeads(selectedTeam);
            loadTeamMembers(selectedTeam);
        }
    }

    private void loadTeamLeads(String team) {
        ObservableList<String> teamLeads = FXCollections.observableArrayList();
        String query = "SELECT username FROM users WHERE role = 'team_lead' AND team = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, team);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                teamLeads.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        teamLeadComboBox.setItems(teamLeads);
    }

    private void loadTeamMembers(String team) {
        ObservableList<String> teamMembers = FXCollections.observableArrayList();
        String query = "SELECT username FROM users WHERE role = 'team_member' AND team = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, team);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                teamMembers.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        teamMembersComboBox.setItems(teamMembers);
    }

    @FXML
    private void handleViewTeamLeadSummaryButtonAction() {
        String teamLead = teamLeadComboBox.getValue();
        if (teamLead != null) {
            showWorkSummary(teamLead);
        }
    }

    @FXML
    private void handleViewTeamMemberSummaryButtonAction() {
        String teamMember = teamMembersComboBox.getValue();
        if (teamMember != null) {
            showWorkSummary(teamMember);
        }
    }

    private void showWorkSummary(String username) {
        String query = "SELECT summary, work_date FROM work_summary ws JOIN users u ON ws.user_id = u.id WHERE u.username = ?";
        StringBuilder summary = new StringBuilder();
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                summary.append("Date: ").append(rs.getDate("work_date")).append("\n");
                summary.append("Summary: ").append(rs.getString("summary")).append("\n\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        showAlert("Work Summary", summary.toString());
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
