package application.Teacher;

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

public class TeacherLoginController{
	
	@FXML
	private TextField textId;
	
	@FXML
	private PasswordField textPassword;
	
    Stage dialogStage = new Stage();
    Scene scene;
    
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
 
    public TeacherLoginController() {
        connection = DatabaseConnector.connectdb();
    }
    
    
    public void authUser(ActionEvent event) {
        String id = textId.getText().toString();
        String pass = textPassword.getText().toString();
        
        String sql = "SELECT * FROM admin WHERE id = ? and pass = ?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, pass);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()){
                infoBox("Please enter correct ID and Password",  "Failed");
            }
            else{
            
            	UserSession.getInstace(id);
                infoBox("Login Successfull","Success" );

    			Node node = (Node)event.getSource();
    			dialogStage = (Stage) node.getScene().getWindow();
    			dialogStage.close();
    	        scene = new Scene(FXMLLoader.load(getClass().getResource("TeacherDashboard.fxml")));
    	        dialogStage.setScene(scene);
    	        dialogStage.show();
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