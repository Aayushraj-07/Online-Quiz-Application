package application.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.DatabaseConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;


public class EditQuestionsController{
	
	@FXML
	private TextField removeText;
	@FXML
	private TextField editText;
	@FXML
	private TableView<Question> questionTable;
	@FXML
	private TableColumn<Question, Integer>  qid;
	@FXML
	private TableColumn<Question, String>  que;
	@FXML
	private TableColumn<Question, String>  ans1;
	@FXML
	private TableColumn<Question, String>  ans2;
	@FXML
	private TableColumn<Question, String>  ans3;
	@FXML
	private TableColumn<Question, String>  ans4;
	@FXML
	private TableColumn<Question, Integer>  trueans;
	@FXML
	private TableColumn<Question, Button> edit_col;
	
	
	public static ObservableList<Question> obList = FXCollections.observableArrayList();
	public static TableView<Question> questionTable2 ;
	
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
	
	
	@FXML
	private void initialize() {
		//obList2 = obList;
		questionTable2 = questionTable;
		try {
			
			populateQuestions();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void populateQuestions() {
		
		
		que.setCellValueFactory(new PropertyValueFactory<>("quest"));
		qid.setCellValueFactory(new PropertyValueFactory<>("qid"));
		ans1.setCellValueFactory(new PropertyValueFactory<>("ans1"));
		ans2.setCellValueFactory(new PropertyValueFactory<>("ans2"));
		ans3.setCellValueFactory(new PropertyValueFactory<>("ans3"));
		ans4.setCellValueFactory(new PropertyValueFactory<>("ans4"));
		trueans.setCellValueFactory(new PropertyValueFactory<>("trueans"));
		edit_col.setCellValueFactory(new PropertyValueFactory<>("edit"));
		
		
		Connection conn = DatabaseConnector.connectdb();
		
		try {
			ResultSet res = conn.createStatement().executeQuery("select * from questions");
			
			while(res.next()) {
				obList.add(new Question(res.getInt("queid"),res.getInt("trueans"),res.getString("question"),res.getString("ans1"),res.getString("ans2"),res.getString("ans3"),res.getString("ans4"),new Button("update")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		questionTable.setItems(obList);
		editTable();
		
	}
	
	private void editTable() {
		que.setCellFactory(TextFieldTableCell.forTableColumn());
		que.setOnEditCommit(e ->{
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setQuest(e.getNewValue());});
	
		ans1.setCellFactory(TextFieldTableCell.forTableColumn());
		ans1.setOnEditCommit(e ->{
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setAns1(e.getNewValue());});
		ans2.setCellFactory(TextFieldTableCell.forTableColumn());
		ans2.setOnEditCommit(e ->{
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setAns2(e.getNewValue());});
		ans3.setCellFactory(TextFieldTableCell.forTableColumn());
		ans3.setOnEditCommit(e ->{
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setAns3(e.getNewValue());});
		ans4.setCellFactory(TextFieldTableCell.forTableColumn());
		ans4.setOnEditCommit(e ->{
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setAns4(e.getNewValue());});
		trueans.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		trueans.setOnEditCommit(e ->{
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setTrueans(e.getNewValue());});
		
		questionTable.setEditable(true);
	}
	
	


	@FXML
	private void removeQuestion(ActionEvent event) {
		int qid = Integer.parseInt(removeText.getText().toString());
		Connection conn = DatabaseConnector.connectdb();
		try {
			preparedStatement = conn.prepareStatement("delete from questions where queid = ?");
			preparedStatement.setInt(1, qid);
			preparedStatement.executeUpdate();
			infoBox("Question removed Successfully.","Success");
			questionTable.getItems().clear();
			populateQuestions();
		}
		catch(Exception e) {
			infoBox("Something Wrong Happened. You Should check Qid.","failed");
			e.printStackTrace();
		}
	}
	
	@FXML
	private void goToDashboard(ActionEvent event) {
		try {
			Stage dialogStage;
			Node node = (Node)event.getSource();
			dialogStage = (Stage) node.getScene().getWindow();
			dialogStage.close();
			Scene scene = new Scene(FXMLLoader.load(getClass().getResource("TeacherDashboard.fxml")));
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