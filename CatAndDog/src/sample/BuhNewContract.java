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

public class BuhNewContract {
    public int n;
    @FXML private Button exitButton;
    @FXML private Button createButton;
    @FXML private Button refreshButton;
    @FXML private Button addAnimalButton;
    @FXML private TextField contractidField;
    @FXML private DatePicker dateContractField;
    @FXML private DatePicker dateAppointmentField;
    @FXML private TextField animalidField;
    @FXML private TextField passportField;
    @FXML private Label titleLabel;
    @FXML private Label exceptionLabel;
    @FXML private TableView<BDanimals> animalsListTable;
    @FXML private TableColumn<BDanimals, String> IdAnimal;
    @FXML private TableColumn<BDanimals, String> Name;
    @FXML private TableColumn<BDanimals, String> NameOwner;
    @FXML private TableColumn<BDanimals, String> BirthDate;
    @FXML private TableColumn<BDanimals, String> Type;
    @FXML private TableColumn<BDanimals, String> Breed;
    @FXML private TableColumn<BDanimals, String> Gender;
    private final ObservableList<BDanimals> animalsListData = FXCollections.observableArrayList();
    private BDanimals selectedTable1 = new BDanimals();
    @FXML private Button btnRemove;
    @FXML private Button cancelButton;
    @FXML private Button removeButton2;
    @FXML private Label labelMessage;
    @FXML private void initialize() {

        IdAnimal.setCellValueFactory(new PropertyValueFactory<>("IdAnimal"));
        Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        NameOwner.setCellValueFactory(new PropertyValueFactory<>("NameOwner"));
        BirthDate.setCellValueFactory(new PropertyValueFactory<>("BirthDate"));
        Type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        Breed.setCellValueFactory(new PropertyValueFactory<>("Breed"));
        Gender.setCellValueFactory(new PropertyValueFactory<>("Gender"));
        FXMLLoader loader = new FXMLLoader();
        tablerefresh();

        refreshButton.setOnAction(actionEvent -> tablerefresh());

        try { Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/veterinary_clinic",
                    "root", "1234")) {
                Statement statement2 = conn.createStatement();
                ResultSet resultSet2 = statement2.executeQuery("SELECT * FROM contract");
                n = 1;
                while (resultSet2.next()) {
                    if(resultSet2.getInt("id_contract") > 0){
                        n++;
                        continue;}}} }
        catch (Exception e) { System.out.println("Ошибка в БД"); }
        titleLabel.setText("Создание контракта № " + n);

        try { Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/veterinary_clinic",
                    "root", "1234")) {
                Statement statement2 = conn.createStatement();
                ResultSet resultSet2 = statement2.executeQuery("SELECT * FROM contract");
                n = 10001;
                while (resultSet2.next()) {
                    if(resultSet2.getInt("id_contract") > 0){
                        n++;
                        continue; }}} }
        catch (Exception e) { System.out.println("Ошибка в БД"); }
        contractidField.setText(n + "");

        exceptionLabel.setVisible(false);
        animalidField.textProperty().addListener((observableValue, s, t1) -> exceptionLabel.setVisible(false));
        passportField.textProperty().addListener((observableValue, s, t1) -> exceptionLabel.setVisible(false));

        createButton.setOnAction(event -> {
            java.sql.Date dateContract = java.sql.Date.valueOf(dateContractField.getValue());
            java.sql.Date dateAppointment = java.sql.Date.valueOf(dateAppointmentField.getValue());
            if (!(animalidField.getText().isEmpty() || passportField.getText().isEmpty()
                    || animalidField.getText().matches("^[A-Za-z ]+$") || passportField.getText().matches("^[A-Za-z ]+$"))) {
                try { Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                    try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/veterinary_clinic",
                            "root", "1234")){ PreparedStatement statement = conn.prepareStatement
                                ("INSERT into contract(id_contract,date_contract,date_appointment,id_animal,owner_passport) VALUES(?,?,?,?,?)");
                        statement.setInt(1, n);
                        statement.setDate(2, dateContract);
                        statement.setDate(3, dateAppointment);
                        statement.setInt(4, Integer.parseInt(animalidField.getText()));
                        statement.setInt(5, Integer.parseInt(passportField.getText()));
                        statement.executeUpdate();
                        loader.setLocation(getClass().getResource("buhWindow.fxml"));
                        exitButton.getScene().getWindow().hide();
                        Parent root = null;
                        try { root = loader.load(); }
                        catch (IOException e) { e.printStackTrace(); }
                        Stage stage = new Stage();
                        assert root != null;
                        stage.setScene(new Scene(root));
                        stage.show(); }}
                catch (Exception e) { e.printStackTrace(); }}
            else exceptionLabel.setVisible(true);});

        addAnimalButton.setOnAction(event -> {
            loader.setLocation(getClass().getResource("animalWindow.fxml"));
            exitButton.getScene().getWindow().hide();
            Parent root = null;
            try { root = loader.load(); }
            catch (IOException e) { e.printStackTrace(); }
            Stage stage = new Stage();
            assert root != null;
            stage.setScene(new Scene(root));
            stage.show();});

        exitButton.setOnAction(event -> {
            loader.setLocation(getClass().getResource("buhWindow.fxml"));
            exitButton.getScene().getWindow().hide();
            Parent root = null;
            try { root = loader.load(); }
            catch (IOException e) { e.printStackTrace(); }
            Stage stage = new Stage();
            assert root != null;
            stage.setScene(new Scene(root));
            stage.show();});

        btnRemove.setOnAction(event -> {
            selectedTable1 = animalsListTable.getSelectionModel().getSelectedItem();
            selectedTable1.getPrCode();
            btnRemove.setVisible(false);
            removeButton2.setVisible(true);
            cancelButton.setVisible(true);
            labelMessage.setVisible(true);});

        removeButton2.setOnAction(event -> {
            deleteTable(selectedTable1.getPrCode());
            animalsListTable.getItems().remove(selectedTable1);
            btnRemove.setVisible(true);
            removeButton2.setVisible(false);
            cancelButton.setVisible(false);
            labelMessage.setVisible(false);
            tablerefresh();});

        cancelButton.setOnAction(event -> {
            btnRemove.setVisible(true);
            removeButton2.setVisible(false);
            cancelButton.setVisible(false);
            labelMessage.setVisible(false);});
    }

    public static void deleteTable(int Code1) {
        String querry = "DELETE FROM animal WHERE id_animal = "+ Code1;
        try {Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/veterinary_clinic",
                "root", "1234");
            PreparedStatement preparedStatement = conn.prepareStatement(querry);
            preparedStatement.executeUpdate();}
        catch (SQLException throwables) { throwables.printStackTrace(); }}

    private void tablerefresh(){
        animalsListData.clear();
        try { Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/veterinary_clinic",
                    "root", "1234")) { Statement statement2 = conn.createStatement();
                ResultSet resultSet2 = statement2.executeQuery("SELECT * FROM animal");
                while(resultSet2.next()){ animalsListData.add(new BDanimals(
                            resultSet2.getInt("id_animal"),
                            resultSet2.getString("name"),
                            resultSet2.getString("owner_full_name"),
                            resultSet2.getString("birth_date"),
                            resultSet2.getString("type"),
                            resultSet2.getString("breed"),
                            resultSet2.getString("gender")));}}
            if (!animalsListData.isEmpty()){ animalsListTable.setItems(animalsListData); }}
        catch (Exception e){ System.out.println("Ошибка в БД"); }}
}