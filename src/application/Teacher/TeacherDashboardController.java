package application.Teacher;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TeacherDashboardController{
	
    Stage dialogStage = new Stage();
    Scene scene;
	
	
    public void addQuestions(ActionEvent event) {

        
		try {
			Node node = (Node)event.getSource();
			dialogStage = (Stage) node.getScene().getWindow();
			dialogStage.close();
        	scene = new Scene(FXMLLoader.load(getClass().getResource("AddQuestions.fxml")));
        	dialogStage.setScene(scene);
        	dialogStage.show();
		}
        catch(Exception e){
            e.printStackTrace();
        }
    }

    
    @FXML
    private void editQuestions(ActionEvent event) {
		try {
			Node node = (Node)event.getSource();
			dialogStage = (Stage) node.getScene().getWindow();
			dialogStage.close();
        	scene = new Scene(FXMLLoader.load(getClass().getResource("EditQuestions.fxml")));
        	dialogStage.setScene(scene);
        	dialogStage.show();
		}
        catch(Exception e){
            e.printStackTrace();  
        }
    }

    @FXML
    private void addStudents(ActionEvent event) {
		try {
			Node node = (Node)event.getSource();
			dialogStage = (Stage) node.getScene().getWindow();
			dialogStage.close();
        	scene = new Scene(FXMLLoader.load(getClass().getResource("AddStudents.fxml")));
        	dialogStage.setScene(scene);
        	dialogStage.show();
		}
        catch(Exception e){
            e.printStackTrace();  
        }
    }
    
    @FXML
    private void removeStudents(ActionEvent event) {
		try {
			Node node = (Node)event.getSource();
			dialogStage = (Stage) node.getScene().getWindow();
			dialogStage.close();
        	scene = new Scene(FXMLLoader.load(getClass().getResource("RemoveStudents.fxml")));
        	dialogStage.setScene(scene);
        	dialogStage.show();
		}
        catch(Exception e){
            e.printStackTrace();  
        }
    }
    
    @FXML
    private void sendMail(ActionEvent event) {
		try {
			Node node = (Node)event.getSource();
			dialogStage = (Stage) node.getScene().getWindow();
			dialogStage.close();
        	scene = new Scene(FXMLLoader.load(getClass().getResource("Sendmail.fxml")));
        	dialogStage.setScene(scene);
        	dialogStage.show();
		}
        catch(Exception e){
            e.printStackTrace();  
        }
    }
    
    @FXML
    private void sendResult(ActionEvent event) {
		try {
			Node node = (Node)event.getSource();
			dialogStage = (Stage) node.getScene().getWindow();
			dialogStage.close();
        	scene = new Scene(FXMLLoader.load(getClass().getResource("SendResult.fxml")));
        	dialogStage.setScene(scene);
        	dialogStage.show();
		}
        catch(Exception e){
            e.printStackTrace();  
        }
    }
    
    @FXML
    private void setDetails(ActionEvent event) {
		try {
			Node node = (Node)event.getSource();
			dialogStage = (Stage) node.getScene().getWindow();
			dialogStage.close();
        	scene = new Scene(FXMLLoader.load(getClass().getResource("QuizDetails.fxml")));
        	dialogStage.setScene(scene);
        	dialogStage.show();
		}
        catch(Exception e){
            e.printStackTrace();  
        }
    }
    
}