package application.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.DatabaseConnector;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

public class Question {
	private int qid,trueans;
	private String quest,ans1,ans2,ans3,ans4;
	Button edit;
	
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
	
	public Question(int qid, int trueans, String quest, String ans1, String ans2, String ans3, String ans4,Button edit) {
		this.qid = qid;
		this.trueans = trueans;
		this.quest = quest;
		this.ans1 = ans1;
		this.ans2 = ans2;
		this.ans3 = ans3;
		this.ans4 = ans4;
		this.edit = edit;

		
		Connection conn = DatabaseConnector.connectdb();

		edit.setOnAction(e ->{
			ObservableList<Question> temp = EditQuestionsController.questionTable2.getSelectionModel().getSelectedItems();
			for(Question question :temp) {
				if(question.getEdit() == edit) {
				//System.out.println("yesh " + question.getQid());
				try {
					preparedStatement = conn.prepareStatement("update questions set question = ?,ans1= ?,ans2=?,ans3=?,ans4=?,trueans=? where queid = ?");
					preparedStatement.setString(1, question.getQuest());
					preparedStatement.setString(2, question.getAns1());
					preparedStatement.setString(3, question.getAns2());
					preparedStatement.setString(4, question.getAns3());
					preparedStatement.setString(5, question.getAns4());
					preparedStatement.setInt(6, question.getTrueans());
					preparedStatement.setInt(7, question.getQid());
					preparedStatement.executeUpdate();
					infoBox("Updated Successfully.","success");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					infoBox("Something is not okay.","fail");
					e1.printStackTrace();
				}
	
				}
			}
		});
	}
	
	public int getQid() {
		return qid;
	}

	public int getTrueans() {
		return trueans;
	}

	public void setQid(int qid) {
		this.qid = qid;
	}

	public void setTrueans(int trueans) {
		this.trueans = trueans;
	}

	public void setQuest(String quest) {
		this.quest = quest;
	}

	public void setAns1(String ans1) {
		this.ans1 = ans1;
	}

	public void setAns2(String ans2) {
		this.ans2 = ans2;
	}

	public void setAns3(String ans3) {
		this.ans3 = ans3;
	}

	public void setAns4(String ans4) {
		this.ans4 = ans4;
	}
	
	public void setEdit(Button edit) {
		this.edit = edit;
	}

	public String getQuest() {
		return quest;
	}

	public String getAns1() {
		return ans1;
	}

	public String getAns2() {
		return ans2;
	}


	public String getAns3() {
		return ans3;
	}


	public String getAns4() {
		return ans4;
	}

	public Button getEdit() {
		return edit;
	}
	
	public static void infoBox(String infoMessage,String title){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
