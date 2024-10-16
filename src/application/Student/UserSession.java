package application.Student;

import javafx.stage.Stage;

public final class UserSession {

    private static UserSession instance;

    private String userId;
    private Stage stage;
    
    private UserSession(String userId) {
        this.userId = userId;
    }

    public static UserSession getInstace(String userId) {
        if(instance == null) {
            instance = new UserSession(userId);
            
        }
        return instance;
    }

    public String getUserName() {
        return userId;
    }
    
    public void setStage(Stage stage) {
    	this.stage = stage;
    }
    
    public Stage getStage() {
    	return stage;
    }
    	
    public void cleanUserSession() {
        userId = "";

    }

}