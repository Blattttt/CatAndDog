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

public class VetUpdate {
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

        titleLabel.setText("Изменение услуги № " + Get.getNumberS());

        contractList.setValue(Integer.parseInt(Get.getContractS()));
        contractNum = contractList.getValue();
        priceField.setText(Get.getPriceS());
        descriptionField.setText(Get.getDescriptionS());

        contractList.setOnAction(e -> {
            contractNum = contractList.getValue();
            exceptionLabel.setVisible(false);});

        exceptionLabel.setVisible(false);
        priceField.textProperty().addListener((observableValue, s, t1) -> exceptionLabel.setVisible(false));
        descriptionField.textProperty().addListener((observableValue, s, t1) -> exceptionLabel.setVisible(false));

        createButton.setOnAction(event -> {
            if (!(priceField.getText().isEmpty() || descriptionField.getText().isEmpty()
                    || descriptionField.getText().matches("^[0-9]+$") || priceField.getText().matches("^[A-Za-z ]+$")
                    || contractList.getValue().equals("00000"))) {
                try { Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                    try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/veterinary_clinic",
                            "root", "1234")){ PreparedStatement statement = conn.prepareStatement
                            ("UPDATE service set id_workers=" + Get.getUserNumber() +
                                    " ,id_contract=" + contractNum + " ,price_rub=" + Integer.parseInt(priceField.getText()) +
                                    " ,service_description=" + "'" + descriptionField.getText() + "'" + " where id_service=" + Get.getNumberS());
                        statement.executeUpdate();
                        loader.setLocation(getClass().getResource("vetWindow.fxml"));
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
            loader.setLocation(getClass().getResource("vetWindow.fxml"));
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