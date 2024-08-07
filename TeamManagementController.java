package application;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class TeamManagementController {

    @FXML
    private void handleProjectManagerButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/project_manager.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Project Manager");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleTeamLeadButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/team_lead.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Team Lead");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleTeamMemberButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/team_member.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Team Member");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}