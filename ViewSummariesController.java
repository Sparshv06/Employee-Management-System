package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ViewSummariesController {

    @FXML
    private TableView<WorkSummary> workTable;

    @FXML
    private TableColumn<WorkSummary, Date> workDateColumn;

    @FXML
    private TableColumn<WorkSummary, String> summaryColumn;

    @FXML
    private TableColumn<WorkSummary, Void> deleteColumn;

    private User user;

    public void setUser(User user) {
        this.user = user;
        loadWorkSummaries();
    }

    @FXML
    private void initialize() {
        workDateColumn.setCellValueFactory(new PropertyValueFactory<>("workDate"));
        summaryColumn.setCellValueFactory(new PropertyValueFactory<>("summary"));

        addDeleteButtonToTable();
    }

    private void loadWorkSummaries() {
        ObservableList<WorkSummary> workSummaries = FXCollections.observableArrayList();
        String query = "SELECT * FROM work_summary WHERE user_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, user.getId());
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

    private void addDeleteButtonToTable() {
        Callback<TableColumn<WorkSummary, Void>, TableCell<WorkSummary, Void>> cellFactory = new Callback<TableColumn<WorkSummary, Void>, TableCell<WorkSummary, Void>>() {
            @Override
            public TableCell<WorkSummary, Void> call(final TableColumn<WorkSummary, Void> param) {
                final TableCell<WorkSummary, Void> cell = new TableCell<WorkSummary, Void>() {

                    private final Button btn = new Button("Delete");

                    {
                        btn.setOnAction((event) -> {
                            WorkSummary summary = getTableView().getItems().get(getIndex());
                            deleteSummary(summary);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        deleteColumn.setCellFactory(cellFactory);
    }

    private void deleteSummary(WorkSummary summary) {
        String query = "DELETE FROM work_summary WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, summary.getId());
            stmt.executeUpdate();
            loadWorkSummaries();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackButtonAction() {
        Stage stage = (Stage) workTable.getScene().getWindow();
        stage.close();
    }
}
