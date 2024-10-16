package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainPageController{
   
	Stage dialogStage = new Stage();
    Scene scene;
	
    public void goToStudentLogin(ActionEvent event)
	{
		try {
			Node node = (Node)event.getSource();
			dialogStage = (Stage) node.getScene().getWindow();
			dialogStage.close();
        	scene = new Scene(FXMLLoader.load(getClass().getResource("StudentLogin.fxml")));
        	dialogStage.setScene(scene);
        	dialogStage.show();
		}
        catch(Exception e){
            e.printStackTrace();
        }
	}
    
    public void goToTeacherLogin(ActionEvent event)
	{
		try {
			Node node = (Node)event.getSource();
			dialogStage = (Stage) node.getScene().getWindow();
			dialogStage.close();
        	scene = new Scene(FXMLLoader.load(getClass().getResource("TeacherLogin.fxml")));
        	dialogStage.setScene(scene);
        	dialogStage.show();
		}
        catch(Exception e){
            e.printStackTrace();
        }
	}
}