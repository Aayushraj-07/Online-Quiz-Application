package application.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import application.DatabaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage; 


public class SendResultController {
	@FXML
	private TextField subjectText;
	@FXML
	private TextField bodyText;
	@FXML
	private Button sendButton;
	@FXML
	private Button dashButton;
	
	
    Stage dialogStage = new Stage();
    Scene scene;
	
    Connection connection = DatabaseConnector.connectdb();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
	
	
	@FXML
	private void sendEmail() {
		sendButton.setDisable(true);
		dashButton.setDisable(true);
		if(subjectText.getText() == null || subjectText.getText().trim().isEmpty()) {
			infoBox("please Enter subject","Fail");
		}
		else if(bodyText.getText() == null || bodyText.getText().trim().isEmpty()) {
			infoBox("please Enter message body","Fail");
		}
		else {

			try {
				//sendButton.setDisable(true);
				//dashButton.setDisable(true);
				preparedStatement = connection.prepareStatement("select * from result");
				resultSet = preparedStatement.executeQuery();
				//System.out.println("Hello");
				while(resultSet.next()) {
				        try{

				      
				            String host ="smtp.gmail.com" ;
				            String user = "yourmail";
				            String pass = "password";
				            String to = resultSet.getString("userid");
				            String from = "yourmail";
				            String subject = subjectText.getText().toString();
				            String messageText = bodyText.getText().toString() + "\n You scored : " + resultSet.getString("score");
				            boolean sessionDebug = false;

				            Properties props = System.getProperties();

				            props.put("mail.smtp.starttls.enable", "true");
				            props.put("mail.smtp.host", host);
				            props.put("mail.smtp.port", "587");
				            props.put("mail.smtp.auth", "true");
				            props.put("mail.smtp.starttls.required", "true");

				            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
				            Session mailSession = Session.getDefaultInstance(props, null);
				            mailSession.setDebug(sessionDebug);
				            Message msg = new MimeMessage(mailSession);
				            msg.setFrom(new InternetAddress(from));
				            InternetAddress[] address = {new InternetAddress(to)};
				            msg.setRecipients(Message.RecipientType.TO, address);
				            msg.setSubject(subject); msg.setSentDate(new Date());
				            msg.setText(messageText);

				           Transport transport=mailSession.getTransport("smtp");
				           transport.connect(host, user, pass);
				           transport.sendMessage(msg, msg.getAllRecipients());
				           transport.close();
				           //System.out.println("message send successfully");
				        }catch(Exception ex)
				        {
				            System.out.println(ex);
				        }
					}
				
				preparedStatement  = connection.prepareStatement("update emails set sent = 1");
				preparedStatement.executeUpdate();
		        infoBox("mails sent","success");
		   		sendButton.setDisable(false);
		   		dashButton.setDisable(false);
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
