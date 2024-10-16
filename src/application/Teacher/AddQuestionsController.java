package application.Teacher;

import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import application.DatabaseConnector;

public class AddQuestionsController{
	
	@FXML
	private TextField questionText;
	@FXML
	private TextField oneText;
	@FXML
	private TextField twoText;
	@FXML
	private TextField threeText;
	@FXML
	private TextField fourText;
	@FXML
	private TextField trueText;
	
	@FXML 
	private Label infoLabel;
	
    Stage dialogStage = new Stage();
    Scene scene;
	
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
	
	
	
	@FXML
	private void addQuestion(ActionEvent event) {
		
		if(questionText.getText() == null || questionText.getText().trim().isEmpty()) {
			infoBox("please Enter Question","Fail");
		}
		else if(oneText.getText() == null || oneText.getText().trim().isEmpty()) {
			infoBox("please Enter option 1","Fail");
		}
		else if(twoText.getText() == null || twoText.getText().trim().isEmpty()) {
			infoBox("please Enter option 2","Fail");
		}
		else if(threeText.getText() == null || threeText.getText().trim().isEmpty()) {
			infoBox("please Enter option 3","Fail");
		}
		else if(fourText.getText() == null || fourText.getText().trim().isEmpty()) {
			infoBox("please Enter option 4","Fail");
		}
		
		else if(trueText.getText() == null || trueText.getText().trim().isEmpty()) {
			infoBox("please Enter true option","Fail");
		}
		else if(!Pattern.matches("[1-4]",trueText.getText().toString())){  
			infoBox("Option number shoud be an integer between 1 to 4","fail");
		}
		else {
			try {
				String sql = "insert into questions(question,ans1,ans2,ans3,ans4,trueans) values(?,?,?,?,?,?)";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, questionText.getText().toString());
				preparedStatement.setString(2, oneText.getText().toString());
				preparedStatement.setString(3, twoText.getText().toString());
				preparedStatement.setString(4, threeText.getText().toString());
				preparedStatement.setString(5, fourText.getText().toString());
				preparedStatement.setInt(6, Integer.parseInt(trueText.getText().toString()));
			
				preparedStatement.executeUpdate();

				Node node = (Node)event.getSource();
				infoBox("Question Added Successfully.","Success");
				dialogStage = (Stage) node.getScene().getWindow();
				dialogStage.close();
				scene = new Scene(FXMLLoader.load(getClass().getResource("AddQuestions.fxml")));
				dialogStage.setScene(scene);
				dialogStage.show();
			}
			catch(Exception e) {
				e.printStackTrace();
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
	
	
	public AddQuestionsController() {
		connection = DatabaseConnector.connectdb();
	}
	
	@FXML
	private void initialize() {

	}
	
	
	public static void infoBox(String msg,String title) {
        Alert alert= new Alert(AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
	}
}