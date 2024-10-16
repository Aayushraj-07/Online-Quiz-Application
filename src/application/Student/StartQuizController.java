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
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class StartQuizController {
    @FXML
    private Label marksLabel;

    @FXML
    private Label tmarkLabel;

    @FXML
    private Label timeLabel;

    Stage dialogStage = new Stage();
    Scene scene;
	
    
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    private int count;
	
    @FXML
    private void initialize() {
    	Connection connection = DatabaseConnector.connectdb();
    	//calculate total no of questions
    	count = 0;
    	try {
    		String sql = "select * from questions";
    		preparedStatement = connection.prepareStatement(sql);
    		resultSet = preparedStatement.executeQuery();
    		while (resultSet.next()){
    				count++;
    		}
    		preparedStatement.close();
    		resultSet.close();
			preparedStatement = connection.prepareStatement("select * from quizdetails");
			resultSet = preparedStatement.executeQuery();
			resultSet.absolute(1);
			marksLabel.setText(resultSet.getInt(2) + " marks for right answer and -" + resultSet.getInt(3) + " for wrong answer");
			tmarkLabel.setText("Total questinos : " + count);
			float tt = resultSet.getInt(1);
			tt = tt/60;
			timeLabel.setText("Total time : " + String.format("%.2f", tt) + " minutes");
    	}
    	catch(Exception e) {
    		e.printStackTrace();
        }
    	
		
    	
    }
    
    @FXML
    private void startQuiz(ActionEvent event) {
    	Connection connection = DatabaseConnector.connectdb();
		UserSession instance = UserSession.getInstace("");
		String userId = instance.getUserName();
		try {
			
			String sql = "update users set attempt = ? where id = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1,1);
			preparedStatement.setString(2, userId);
			preparedStatement.executeUpdate();
			Node node = (Node)event.getSource();
			dialogStage = (Stage) node.getScene().getWindow();
			dialogStage.close();
        	scene = new Scene(FXMLLoader.load(getClass().getResource("QuizView.fxml")));
        	dialogStage.setScene(scene);
        	dialogStage.show();
		}
        catch(Exception e){
            e.printStackTrace();
        }
    }

}
