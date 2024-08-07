package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TeamMemberController {

    @FXML
    private Label summaryLabel;

    public void setSummary(String summary) {
        summaryLabel.setText(summary);
    }
}
