package application.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.stream.IntStream;

import application.DatabaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;





public class QuizViewController {
	

	private static QuizViewController instance ;
	
	@FXML 
	private Label textQuestion;	
	@FXML 
	private RadioButton rba;
	@FXML 
	private RadioButton rbb;
	@FXML
	private RadioButton rbc;
	@FXML 
	private RadioButton rbd;

	@FXML 
	private ToggleGroup rbGroup;
	
	@FXML 
	private Button bNext;
	@FXML
	private Button bBack;
	
	@FXML
	private HBox timerBox;

	private int[] scorePerQuestion;
	private RadioButton[] selectedButton;
	private int index,score,count,nmark,mpques,ttime;
	
    Stage dialogStage = new Stage();
    Scene scene;
	
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
	
    
	
public QuizViewController() {
	rbGroup = new ToggleGroup();
	connection = DatabaseConnector.connectdb();
	instance = this;
}
	

@FXML
private void initialize() 
{
	rba.setToggleGroup(rbGroup); rbb.setToggleGroup(rbGroup);
	rbc.setToggleGroup(rbGroup); rbd.setToggleGroup(rbGroup);
		
	try {
		String sql = "select * from quizdetails";
		preparedStatement = connection.prepareStatement(sql);
		resultSet = preparedStatement.executeQuery();

		resultSet.absolute(1);
		this.ttime = resultSet.getInt(1);
		this.mpques = resultSet.getInt(2);
		this.nmark = -1* resultSet.getInt(3);
	}
	catch(Exception e) {
		e.printStackTrace();
    }
	
	//set timer
	CountDown counter1 = new CountDown(this.ttime);
	timerBox.getChildren().add(counter1.setCountdown());
		
	//fetch total no of questions from database
	count = 0;
	try {
		String sql = "select * from questions";
		preparedStatement = connection.prepareStatement(sql);
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()){
				count++;
		}
		
		scorePerQuestion = new int[count];
		selectedButton = new RadioButton[count];
		
	}
	catch(Exception e) {
		e.printStackTrace();
    }
		
		
	index = 0;                 
	score = 0;                      //initially score is zero
	showQuiz(null);
}


public void showQuiz(ActionEvent event)
{		
	try {				
		if(index < count){
					
			//for first question disable back button 
			if(index == 0)
				bBack.setDisable(true);
			else
				bBack.setDisable(false);
					
			//for last question set next as submit
			if(index == count - 1) 
				bNext.setText("Submit");
			else 
				bNext.setText("Next");
					
			//fetch and display question	
			resultSet.absolute(index+1);
			this.textQuestion.setText((index+1) + ". " + resultSet.getString(2));
			this.rba.setText(resultSet.getString(3));
			this.rba.setSelected(false);
			this.rbb.setText(resultSet.getString(4));
			this.rbb.setSelected(false);
			this.rbc.setText(resultSet.getString(5));
			this.rbc.setSelected(false);
			this.rbd.setText(resultSet.getString(6));
			this.rbd.setSelected(false);
					
			//if user already picked an option
			if(selectedButton[index] != null) {
				selectedButton[index].setSelected(true);	
			}	
		}
		else {
			//confirm before submit
			Alert alert = new Alert(AlertType.CONFIRMATION, "Do you want to submit ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
			alert.setTitle("Confirm");
			alert.setHeaderText(null);
			alert.showAndWait();
			
			if (alert.getResult() == ButtonType.YES) {		
				score =  IntStream.of(scorePerQuestion).sum();
	
				UserSession instance = UserSession.getInstace("");
				String userId = instance.getUserName();
				String sql = "insert into result values (?,?)";
				try {
					preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setString(1, userId);
					preparedStatement.setString(2, Integer.toString(score));
					preparedStatement.executeUpdate();

			            
					Node node = (Node)event.getSource();
					dialogStage = (Stage) node.getScene().getWindow();
					dialogStage.close();
					scene = new Scene(FXMLLoader.load(getClass().getResource("ResultView.fxml")));
					dialogStage.setScene(scene);
					dialogStage.show();
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
			else {
				index--;
			}
		}
	}
	catch(Exception e){
		e.printStackTrace();
	}
}
	

//go to next question	
@FXML 
private void goNext(ActionEvent event)
{	
	index++;
	showQuiz(event);
}


//go to previous question
@FXML 
private void goBack(ActionEvent event) 
{
	index--;
	showQuiz(event);	
}
	

//save the radio button information when clicked	
@FXML
private void rbClicked(){
	RadioButton sRButton = (RadioButton) rbGroup.getSelectedToggle();

	if(selectedButton[index] == sRButton)
	{
		sRButton.setSelected(false);
		selectedButton[index] = null;
		scorePerQuestion[index] = 0;
	}
	else 
	{		
		if(sRButton == rba) 
			selectedButton[index] = rba;
		else if(sRButton == rbb) 
			selectedButton[index] = rbb;
		else if(sRButton == rbc) 
			selectedButton[index] = rbc;
		else if(sRButton == rbd) 
			selectedButton[index] = rbd;
		
		try 
		{
			if((resultSet.getInt(7) == 1) && sRButton == rba) {
				scorePerQuestion[index] = this.mpques;
			}
			else if((resultSet.getInt(7) == 2) && sRButton == rbb) {
				scorePerQuestion[index] = this.mpques;
			}
			else if((resultSet.getInt(7) == 3) && sRButton == rbc) {
				scorePerQuestion[index] = this.mpques;
			}
			else if((resultSet.getInt(7) == 4) && sRButton == rbd) {
				scorePerQuestion[index] = this.mpques;
			}
			else {
				scorePerQuestion[index] = this.nmark;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
	
//return instance
public static QuizViewController getInstance() {
	return instance;
}

//to automatically submit after time out	
public void submit() {
		score =  IntStream.of(scorePerQuestion).sum();		
		UserSession instance = UserSession.getInstace("");
		String userId = instance.getUserName();
		String sql = "insert into result values (?,?)";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, userId);
			preparedStatement.setString(2, Integer.toString(score));
			preparedStatement.executeUpdate();
		    
			Stage cstage = instance.getStage();
			cstage.close();
			scene = new Scene(FXMLLoader.load(getClass().getResource("ResultView.fxml")));
			dialogStage.setScene(scene);
			dialogStage.show();
			}
		catch(Exception e){
			e.printStackTrace();
		}
}

	
	
public static void infoBox(String infoMessage,String title){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
	
}