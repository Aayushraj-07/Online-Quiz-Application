package application.Student;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import application.DatabaseConnector;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class ResultViewController{
	@FXML
	private ImageView resultImage;
	@FXML
	private Label scoreLabel;
	@FXML 
	private Label totalLabel;
	@FXML
	private Label tQueLabel;
	
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement1 = null;
    ResultSet resultSet = null;
    ResultSet resultSet1 = null;
    
    public ResultViewController(){
    	connection = DatabaseConnector.connectdb();
    }
    
    @FXML 
    private void initialize() {
    	showResult();
    }
    
    public void showResult() {
    	String sql = "select * from questions";
    	int count = 0;
    	int score;
    	
    	try {
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                count++;
            }
            //resultSet.close();
            UserSession instance = UserSession.getInstace("");
			String userId = instance.getUserName();
            
            sql = "Select * from result where userid = ? ";
            preparedStatement1 = connection.prepareStatement(sql);
            preparedStatement1.setString(1, userId);
            resultSet1 = preparedStatement1.executeQuery();
            resultSet1.absolute(1);
            score = resultSet1.getInt(2);
            
            //System.out.println(score);
            
            if(score == count*4) {
            	FileInputStream input = new FileInputStream("src/application/images/maxscore.gif");
            	Image image = new Image(input);
            	resultImage.setImage(image);
            	resultImage.setCache(true);
            }
            else if(score == 0) {
            	FileInputStream input = new FileInputStream("src/application/images/normalscore.gif");
                Image image = new Image(input);
                resultImage.setImage(image);
                resultImage.setCache(true);
            }
            else {
            	FileInputStream input = new FileInputStream("src/application/images/normalscore.gif");
                Image image = new Image(input);
   
                resultImage.setImage(image);
                resultImage.setCache(true);
            }
            preparedStatement = connection.prepareStatement("select * from quizdetails");
            resultSet = preparedStatement.executeQuery();
            resultSet.absolute(1);
            
            scoreLabel.setText("you scored: " + score);
            totalLabel.setText("max marks:  " + count*(resultSet.getInt("marks")));
            tQueLabel.setText("Total Questions: " + count);
    	}
        catch(Exception e){
            e.printStackTrace();
        }
    }
}