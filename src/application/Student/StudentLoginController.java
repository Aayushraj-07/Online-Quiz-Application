package application.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import application.DatabaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class StudentLoginController{
	
	@FXML
	private TextField textId;
	
	@FXML
	private PasswordField textPassword;
	
    Stage dialogStage = new Stage();
    Scene scene;
    
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
 
    public StudentLoginController() {
        connection = DatabaseConnector.connectdb();
    }
    
    public void authUser(ActionEvent event) {
        String id = textId.getText().toString();
        String pass = textPassword.getText().toString();
        
        String sql = "SELECT * FROM users WHERE id = ? and pass = ?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, pass);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()){
                infoBox("Please enter correct ID and Password",  "Failed");
            }
            else{
            
            	UserSession instance = UserSession.getInstace(id);
            	String userId = instance.getUserName();
                infoBox("Login Successfull","Success" );
              
                //check if user already taken quiz or not
    			
    			sql = "SELECT * FROM users where id = ?";
    			preparedStatement = connection.prepareStatement(sql);
    			preparedStatement.setString(1,userId );
    			resultSet = preparedStatement.executeQuery();
    			resultSet.absolute(1);
    			if(resultSet.getInt(3) == 1) {
    				infoBox("You alredy taken the quiz", "Attention");
    				Node node = (Node)event.getSource();
    				dialogStage = (Stage) node.getScene().getWindow();
    				dialogStage.close();
    	        	scene = new Scene(FXMLLoader.load(getClass().getResource("StudentLogin.fxml")));
    	        	dialogStage.setScene(scene);
    	        	dialogStage.show();
    			}
    			else {
    				sql = "update users set attempt = ? where id = ?";
    				preparedStatement = connection.prepareStatement(sql);
    				preparedStatement.setInt(1,0);
    				preparedStatement.setString(2, userId);
    				preparedStatement.executeUpdate();
    				Node node = (Node)event.getSource();
    				dialogStage = (Stage) node.getScene().getWindow();
    				dialogStage.close();
    				scene = new Scene(FXMLLoader.load(getClass().getResource("StartQuiz.fxml")));
    				dialogStage.setScene(scene);
    				instance.setStage(dialogStage);
    				dialogStage.show();
    			}
    		}
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void infoBox(String infoMessage, String title){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}