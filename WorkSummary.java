package application;

import java.sql.Date;

public class WorkSummary {
    private int id;
    private int userId;
    private Date workDate;
    private String summary;

    public WorkSummary(int id, int userId, Date workDate, String summary) {
        this.id = id;
        this.userId = userId;
        this.workDate = workDate;
        this.summary = summary;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public String getSummary() {
        return summary;
    }
}
