package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class AdminReportController {

    @FXML
    private TableView<WorkSummary> workTable;

    @FXML
    private TableColumn<WorkSummary, Date> workDateColumn;

    @FXML
    private TableColumn<WorkSummary, String> summaryColumn;

    private String operator;
    private LocalDate startDate;
    private LocalDate endDate;

    public void setUserAndDates(String operator, LocalDate startDate, LocalDate endDate) {
        this.operator = operator;
        this.startDate = startDate;
        this.endDate = endDate;
        loadWorkSummaries();
    }

    @FXML
    private void initialize() {
        workDateColumn.setCellValueFactory(new PropertyValueFactory<>("workDate"));
        summaryColumn.setCellValueFactory(new PropertyValueFactory<>("summary"));
    }

    private void loadWorkSummaries() {
        ObservableList<WorkSummary> workSummaries = FXCollections.observableArrayList();
        String query = "SELECT * FROM work_summary WHERE user_id = (SELECT id FROM users WHERE username = ?) AND work_date BETWEEN ? AND ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, operator);
            stmt.setDate(2, Date.valueOf(startDate));
            stmt.setDate(3, Date.valueOf(endDate));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                WorkSummary summary = new WorkSummary(rs.getInt("id"), rs.getInt("user_id"),
                                                      rs.getDate("work_date"), rs.getString("summary"));
                workSummaries.add(summary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        workTable.setItems(workSummaries);
    }

    @FXML
    private void handleBackButtonAction() {
        Stage stage = (Stage) workTable.getScene().getWindow();
        stage.close();
    }
}
