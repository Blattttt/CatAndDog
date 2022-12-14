package sample;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;

public class BuhNewService {
    public int n;
    public int contractNum;
    @FXML private Button exitButton;
    @FXML private Button createButton;
    @FXML private TextField priceField;
    @FXML private TextField descriptionField;
    @FXML private Label titleLabel;
    @FXML private Label exceptionLabel;
    @FXML private ComboBox<Integer> contractList;
    @FXML private void initialize() {
        FXMLLoader loader = new FXMLLoader();
        initContracts();

        try { Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/veterinary_clinic",
                    "root", "1234")) { Statement statement2 = conn.createStatement();
                ResultSet resultSet2 = statement2.executeQuery("SELECT * FROM service");
                n = 1;
                while (resultSet2.next()) {
                    if(resultSet2.getInt("id_service") > 0){
                        n++;
                        continue;}}} }
        catch (Exception e) { System.out.println("Ошибка в БД"); }
        titleLabel.setText("Создание услуги № " + n);

        contractList.setOnAction(e -> {
            contractNum = contractList.getValue();
            exceptionLabel.setVisible(false);});

        exceptionLabel.setVisible(false);
        priceField.textProperty().addListener((observableValue, s, t1) -> exceptionLabel.setVisible(false));
        descriptionField.textProperty().addListener((observableValue, s, t1) -> exceptionLabel.setVisible(false));

        createButton.setOnAction(event -> {
            if (!(priceField.getText().isEmpty() || descriptionField.getText().isEmpty()
                    || priceField.getText().matches("^[A-Za-z ]+$")
                    || contractList.getValue().equals("00000"))) {
                try { Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                    try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/veterinary_clinic",
                            "root", "1234")){ PreparedStatement statement = conn.prepareStatement
                                ("INSERT into service(id_service,id_workers,id_contract,price_rub," +
                                        "service_description) VALUES(?,?,?,?,?)");
                        statement.setInt(1, n);
                        statement.setInt(2, Get.getUserNumber());
                        statement.setInt(3, contractNum);
                        statement.setInt(4, Integer.parseInt(priceField.getText()));
                        statement.setString(5, descriptionField.getText());
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
    }
    private void initContracts() {
        try { Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/veterinary_clinic",
                    "root", "1234")) { Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM contract");
                while (resultSet.next()) {
                    contractList.getItems().add(resultSet.getInt("id_contract"));}}}
        catch (Exception e) { System.out.println("Ошибка в БД"); }}
}