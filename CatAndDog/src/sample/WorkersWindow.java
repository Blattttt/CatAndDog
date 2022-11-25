package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;

public class WorkersWindow {
    public int n;
    @FXML private Button exitButton;
    @FXML private Button createButton;
    @FXML private TextField idField;
    @FXML private TextField passwordField;
    @FXML private TextField fioField;
    @FXML private TextField sexField;
    @FXML private DatePicker birthDateField;
    @FXML private TextField jobField;
    @FXML private TextField registrationField;
    @FXML private Label exceptionLabel;
    @FXML private Button refreshButton;
    @FXML private TableView<BDworkers> workersListTable;
    @FXML private TableColumn<BDworkers, String> ID;
    @FXML private TableColumn<BDworkers, String> Password;
    @FXML private TableColumn<BDworkers, String> FIO;
    @FXML private TableColumn<BDworkers, String> Gender;
    @FXML private TableColumn<BDworkers, String> BirthDate;
    @FXML private TableColumn<BDworkers, String> Job;
    @FXML private TableColumn<BDworkers, String> Registration;
    private final ObservableList<BDworkers> workersListData = FXCollections.observableArrayList();
    private BDworkers selectedTable1 = new BDworkers();
    @FXML private Button removeButton;
    @FXML private Button cancelButton;
    @FXML private Button removeButton2;
    @FXML private Label labelMessage;
    @FXML private void initialize() {

        ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        Password.setCellValueFactory(new PropertyValueFactory<>("Password"));
        FIO.setCellValueFactory(new PropertyValueFactory<>("FIO"));
        Gender.setCellValueFactory(new PropertyValueFactory<>("Gender"));
        BirthDate.setCellValueFactory(new PropertyValueFactory<>("BirthDate"));
        Job.setCellValueFactory(new PropertyValueFactory<>("Job"));
        Registration.setCellValueFactory(new PropertyValueFactory<>("Registration"));

        FXMLLoader loader = new FXMLLoader();
        tablerefresh();

        refreshButton.setOnAction(actionEvent -> tablerefresh());

        try { Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/veterinary_clinic",
                    "root", "1234")) { Statement statement2 = conn.createStatement();
                ResultSet resultSet2 = statement2.executeQuery("SELECT * FROM workers");
                n = 1001;
                while (resultSet2.next()) {
                    if(resultSet2.getInt("id_workers") > 0){
                        n++;
                        continue; }}}}
        catch (Exception e) { System.out.println("Ошибка в БД"); }
        idField.setText(n + "");

        exceptionLabel.setVisible(false);
        passwordField.textProperty().addListener((observableValue, s, t1) -> exceptionLabel.setVisible(false));
        fioField.textProperty().addListener((observableValue, s, t1) -> exceptionLabel.setVisible(false));
        sexField.textProperty().addListener((observableValue, s, t1) -> exceptionLabel.setVisible(false));
        jobField.textProperty().addListener((observableValue, s, t1) -> exceptionLabel.setVisible(false));
        registrationField.textProperty().addListener((observableValue, s, t1) -> exceptionLabel.setVisible(false));

        createButton.setOnAction(event -> {
            java.sql.Date birthDate = java.sql.Date.valueOf(birthDateField.getValue());
            if (!(passwordField.getText().isEmpty() || fioField.getText().isEmpty()
                    || sexField.getText().isEmpty() || jobField.getText().isEmpty()
                    || registrationField.getText().isEmpty() || fioField.getText().matches("^[0-9]+$")
                    || sexField.getText().matches("^[0-9]+$") || passwordField.getText().matches("^[A-Za-z ]+$"))) {
                try { Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                    try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/veterinary_clinic",
                            "root", "1234")){ PreparedStatement statement = conn.prepareStatement
                                ("INSERT into workers(id_workers,password,full_name,gender,birth_date,job," +
                                        "registration) VALUES(?,?,?,?,?,?,?)");
                        statement.setInt(1, n);
                        statement.setInt(2, Integer.parseInt(passwordField.getText()));
                        statement.setString(3, fioField.getText());
                        statement.setString(4, sexField.getText());
                        statement.setDate(5, birthDate);
                        statement.setString(6, jobField.getText());
                        statement.setString(7, registrationField.getText());
                        statement.executeUpdate(); }
                    loader.setLocation(getClass().getResource("buhWindow.fxml"));
                    exitButton.getScene().getWindow().hide();
                    Parent root = null;
                    try { root = loader.load(); }
                    catch (IOException e) { e.printStackTrace(); }
                    Stage stage = new Stage();
                    assert root != null;
                    stage.setScene(new Scene(root));
                    stage.show(); }
                catch (Exception e) { e.printStackTrace(); }}
            else exceptionLabel.setVisible(true); });

        exitButton.setOnAction(event -> {
            loader.setLocation(getClass().getResource("buhWindow.fxml"));
            exitButton.getScene().getWindow().hide();
            Parent root = null;
            try { root = loader.load(); }
            catch (IOException e) { e.printStackTrace(); }
            Stage stage = new Stage();
            assert root != null;
            stage.setScene(new Scene(root));
            stage.show(); });

        removeButton.setOnAction(event -> {
            selectedTable1 = workersListTable.getSelectionModel().getSelectedItem();
            selectedTable1.getPrCode();
            removeButton.setVisible(false);
            removeButton2.setVisible(true);
            cancelButton.setVisible(true);
            labelMessage.setVisible(true);});

        removeButton2.setOnAction(event -> {
            deleteTableWorker(selectedTable1.getPrCode());
            workersListTable.getItems().remove(selectedTable1);
            removeButton.setVisible(true);
            removeButton2.setVisible(false);
            cancelButton.setVisible(false);
            labelMessage.setVisible(false);
            loader.setLocation(getClass().getResource("buhWindow.fxml"));
            exitButton.getScene().getWindow().hide();
            Parent root = null;
            try { root = loader.load(); }
            catch (IOException e) { e.printStackTrace(); }
            Stage stage = new Stage();
            assert root != null;
            stage.setScene(new Scene(root));
            stage.show(); });

        cancelButton.setOnAction(event -> {
            removeButton.setVisible(true);
            removeButton2.setVisible(false);
            cancelButton.setVisible(false);
            labelMessage.setVisible(false);});
    }

    public static void deleteTableWorker(int Code1) {
        String querry = "DELETE FROM workers WHERE id_workers = "+ Code1;
        try {Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/veterinary_clinic",
                "root", "1234");
            PreparedStatement preparedStatement = conn.prepareStatement(querry);
            preparedStatement.executeUpdate(); }
        catch (SQLException throwables) { throwables.printStackTrace(); }}

    private void tablerefresh(){
        workersListData.clear();
        try { Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/veterinary_clinic",
                    "root", "1234")) { Statement statement2 = conn.createStatement();
                ResultSet resultSet2 = statement2.executeQuery("SELECT * FROM workers");
                while(resultSet2.next()){ workersListData.add(new BDworkers(
                            resultSet2.getInt("id_workers"),
                            resultSet2.getString("password"),
                            resultSet2.getString("full_name"),
                            resultSet2.getString("gender"),
                            resultSet2.getString("birth_date"),
                            resultSet2.getString("job"),
                            resultSet2.getString("registration")));}}
            if (!workersListData.isEmpty()){ workersListTable.setItems(workersListData); }}
        catch (Exception e){ System.out.println("Ошибка в БД"); }}
}