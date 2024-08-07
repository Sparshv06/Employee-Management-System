package application;

public class Summary {
    private final String teamName;
    private final String userName;
    private final String userRole;
    private final String summary;

    public Summary(String teamName, String userName, String userRole, String summary) {
        this.teamName = teamName;
        this.userName = userName;
        this.userRole = userRole;
        this.summary = summary;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserRole() {
        return userRole;
    }

    public String getSummary() {
        return summary;
    }
}
