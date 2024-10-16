package application.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import application.DatabaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class QuizDetailsController {
    @FXML
    private TextField nmarkText;

    @FXML
    private TextField timeText;

    @FXML
    private TextField markText;

    Stage dialogStage = new Stage();
    Scene scene;
    
    
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
	
    
    @FXML
    void saveDetails(ActionEvent event) {
    	connection = DatabaseConnector.connectdb();
		if(timeText.getText() == null || timeText.getText().trim().isEmpty()) {
			infoBox("please Enter total time","Fail");
		}
		else if(markText.getText() == null || markText.getText().trim().isEmpty()) {
			infoBox("please Enter marks per question","Fail");
		}
		else if(nmarkText.getText() == null || nmarkText.getText().trim().isEmpty()) {
			infoBox("please Enter negative marking","Fail");
		}
		else if(!Pattern.matches("[0-9]+",timeText.getText().toString())){  
			infoBox("time should be an integer","fail");
		}
		else if(!Pattern.matches("[0-9]+",markText.getText().toString())){  
			infoBox("time should be an integer","fail");
		}
		else if(!Pattern.matches("[0-9]+",nmarkText.getText().toString())){  
			infoBox("time should be an integer","fail");
		}
		else {
		Alert alert = new Alert(AlertType.CONFIRMATION, "Do you want to Save ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
		alert.setTitle("Confirm");
		alert.setHeaderText(null);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.YES) {
			try {
				connection.prepareStatement("delete from quizdetails").executeUpdate();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String sql = "insert into quizdetails values (?,?,?)";
			try {
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, Integer.parseInt(timeText.getText().toString()));
				preparedStatement.setInt(2, Integer.parseInt(markText.getText().toString()));
				preparedStatement.setInt(3, Integer.parseInt(nmarkText.getText().toString()));
				preparedStatement.executeUpdate();
				infoBox("saved.","success");
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		}
    }
    
	
	@FXML
	private void goDashboard(ActionEvent event) {
		try {
			Node node = (Node)event.getSource();
			dialogStage = (Stage) node.getScene().getWindow();
			dialogStage.close();
			scene = new Scene(FXMLLoader.load(getClass().getResource("TeacherDashboard.fxml")));
			dialogStage.setScene(scene);
			dialogStage.show();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
    
    
	public static void infoBox(String msg,String title) {
        Alert alert= new Alert(AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
	}
}
