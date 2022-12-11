package sample;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;

public class AnimalWindow {
    public int n;
    @FXML private Button exitButton;
    @FXML private Button createButton;
    @FXML private TextField nameField;
    @FXML private TextField typeField;
    @FXML private TextField breedField;
    @FXML private TextField genderField;
    @FXML private TextField FIOownerField;
    @FXML private DatePicker birthDateField;
    @FXML private Label titleLabel;
    @FXML private Label exceptionLabel;
    @FXML private void initialize() {
        FXMLLoader loader = new FXMLLoader();

        try { Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/veterinary_clinic",
                    "root", "1234")) {
                Statement statement2 = conn.createStatement();
                ResultSet resultSet2 = statement2.executeQuery("SELECT * FROM animal");
                n = 1;
                while (resultSet2.next()) {
                    if(resultSet2.getInt("id_animal") > 0){
                        n++;
                        continue;}}}}
        catch (Exception e) { System.out.println("Ошибка в БД"); }
        titleLabel.setText("Добавление животного № " + n);

        exceptionLabel.setVisible(false);
        nameField.textProperty().addListener((observableValue, s, t1) -> exceptionLabel.setVisible(false));
        typeField.textProperty().addListener((observableValue, s, t1) -> exceptionLabel.setVisible(false));
        breedField.textProperty().addListener((observableValue, s, t1) -> exceptionLabel.setVisible(false));
        genderField.textProperty().addListener((observableValue, s, t1) -> exceptionLabel.setVisible(false));
        FIOownerField.textProperty().addListener((observableValue, s, t1) -> exceptionLabel.setVisible(false));

        createButton.setOnAction(event -> {
            java.sql.Date birthDate = java.sql.Date.valueOf(birthDateField.getValue());
            if (!(nameField.getText().isEmpty() || typeField.getText().isEmpty()
                    || breedField.getText().isEmpty() || genderField.getText().isEmpty()
                    || FIOownerField.getText().isEmpty() || nameField.getText().matches("^[0-9]+$")
                    || typeField.getText().matches("^[0-9]+$") || breedField.getText().matches("^[0-9]+$")
                    || genderField.getText().matches("^[0-9]+$") || FIOownerField.getText().matches("^[0-9]+$"))) {
                try { Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                    try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/veterinary_clinic",
                            "root", "1234")){ PreparedStatement statement = conn.prepareStatement
                                ("INSERT into animal(id_animal,name,owner_full_name,birth_date,type,breed,gender) VALUES(?,?,?,?,?,?,?)");
                        statement.setInt(1, n);
                        statement.setString(2, nameField.getText());
                        statement.setString(3, FIOownerField.getText());
                        statement.setDate(4, birthDate);
                        statement.setString(5, typeField.getText());
                        statement.setString(6, breedField.getText());
                        statement.setString(7, genderField.getText());
                        statement.executeUpdate();
                        loader.setLocation(getClass().getResource("buhNewContract.fxml"));
                        exitButton.getScene().getWindow().hide();
                        Parent root = null;
                        try { root = loader.load(); }
                        catch (IOException e) {e.printStackTrace();}
                        Stage stage = new Stage();
                        assert root != null;
                        stage.setScene(new Scene(root));
                        stage.show();}}
                catch (Exception e) { e.printStackTrace();}}
            else exceptionLabel.setVisible(true);});

        exitButton.setOnAction(event -> {
            loader.setLocation(getClass().getResource("buhNewContract.fxml"));
            exitButton.getScene().getWindow().hide();
            Parent root = null;
            try { root = loader.load(); }
            catch (IOException e) { e.printStackTrace(); }
            Stage stage = new Stage();
            assert root != null;
            stage.setScene(new Scene(root));
            stage.show();});
    }}