package application.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.DatabaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class RemoveStudentsController {
	@FXML
	private TextField removeText;
	
    Stage dialogStage = new Stage();
    Scene scene;
	
    Connection connection = DatabaseConnector.connectdb();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
    	    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
    	        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
    	        return matcher.find();
    }
    
    @FXML
    private void removeEmail(ActionEvent event) {
    	if(removeText.getText() == null || removeText.getText().trim().isEmpty()) {
			infoBox("please Enter Email","Fail");
		}
		else if(!validate(removeText.getText().toString())){  
			infoBox("Email is not correct","fail");
		}
		else {
			
			String sql = "delete from emails where emailid = ?";
			try {
				String sql1 = "select * from emails where emailid = ?";
				preparedStatement = connection.prepareStatement(sql1);
				preparedStatement.setString(1, removeText.getText().toString());
				resultSet = preparedStatement.executeQuery();
				if(!resultSet.next()) {
					infoBox("Email not present.","Fail");
				}
				else {
					preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setString(1, removeText.getText().toString());
					preparedStatement.executeUpdate();
					infoBox("Removed Successfully.","Success");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
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
    
	public static void infoBox(String msg,String title) {
        Alert alert= new Alert(AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
	}
    	

}
