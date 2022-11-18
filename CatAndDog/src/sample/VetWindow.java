package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;

public class VetWindow {
    public int n;
    @FXML private Button refreshButton;
    @FXML private Button newButton;
    @FXML private Button exitButton;
    @FXML private Button removeButton;
    @FXML private Button cancelButton;
    @FXML private Button removeButton2;
    @FXML private Label labelMessage;
    @FXML private Label nameField;
    @FXML private Label scoreField;
    @FXML private TableView<BD> serviceListTable;
    @FXML private TableColumn<BD, String> Number;
    @FXML private TableColumn<BD, String> Worker;
    @FXML private TableColumn<BD, String> Contract;
    @FXML private TableColumn<BD, String> Price;
    @FXML private TableColumn<BD, String> Description;
    private final ObservableList<BD> serviceListData = FXCollections.observableArrayList();
    private BD selectedTable1 = new BD();
    @FXML private void initialize() {

        nameField.setText("Здравстуйте, " + Get.getUserName() + "!" + " Ваш ID - " + Get.getUserNumber());

        Number.setCellValueFactory(new PropertyValueFactory<>("Number"));
        Worker.setCellValueFactory(new PropertyValueFactory<>("Worker"));
        Contract.setCellValueFactory(new PropertyValueFactory<>("Contract"));
        Price.setCellValueFactory(new PropertyValueFactory<>("Price"));
        Description.setCellValueFactory(new PropertyValueFactory<>("Description"));

        FXMLLoader loader = new FXMLLoader();
        tablerefresh();

        refreshButton.setOnAction(actionEvent -> tablerefresh());

        newButton.setOnAction(event -> {
            loader.setLocation(getClass().getResource("vetNewService.fxml"));
            newButton.getScene().getWindow().hide();
            Parent root = null;
            try {root = loader.load(); }
            catch (IOException e) { e.printStackTrace();}
            Stage stage = new Stage();
            assert root != null;
            stage.setScene(new Scene(root));
            stage.show();});

        exitButton.setOnAction(event -> {
            loader.setLocation(getClass().getResource("authorizationWindow.fxml"));
            exitButton.getScene().getWindow().hide();
            Parent root = null;
            try {root = loader.load(); }
            catch (IOException e) { e.printStackTrace();}
            Stage stage = new Stage();
            assert root != null;
            stage.setScene(new Scene(root));
            stage.show();});

        removeButton.setOnAction(event -> {
            selectedTable1 = serviceListTable.getSelectionModel().getSelectedItem();
            labelMessage.setText("Вернуть строку будет невозможно");
            labelMessage.setVisible(false);
            if (selectedTable1.getWorker() == Get.getUserNumber()){
            selectedTable1.getWorker();
            removeButton.setVisible(false);
            removeButton2.setVisible(true);
            cancelButton.setVisible(true);
            labelMessage.setVisible(true);} else {
                labelMessage.setText("Вы не можете удалять чужые услуги");
                labelMessage.setVisible(true);}});

        removeButton2.setOnAction(event -> {
            deleteTableService(selectedTable1.getPrCode());
            serviceListTable.getItems().remove(selectedTable1);
            removeButton.setVisible(true);
            removeButton2.setVisible(false);
            cancelButton.setVisible(false);
            labelMessage.setVisible(false);
            tablerefresh();});

        cancelButton.setOnAction(event -> {
            removeButton.setVisible(true);
            removeButton2.setVisible(false);
            cancelButton.setVisible(false);
            labelMessage.setVisible(false);});
    }

    public static void deleteTableService(int Code1) {
        String querry = "DELETE FROM service WHERE id_service = "+ Code1;
        try {Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/veterinary_clinic",
                "root", "1234");
            PreparedStatement preparedStatement = conn.prepareStatement(querry);
            preparedStatement.executeUpdate(); }
        catch (SQLException throwables) { throwables.printStackTrace(); }}

    private void tablerefresh(){
        try { Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/veterinary_clinic",
                    "root", "1234")) { Statement statement2 = conn.createStatement();
                ResultSet resultSet2 = statement2.executeQuery("SELECT * FROM service");
                n = 0;
                while (resultSet2.next()) {
                    if(resultSet2.getInt("id_workers") == Get.getUserNumber()){
                        n++;
                        continue;}}}}
        catch (Exception e) { System.out.println("Ошибка в БД"); }
        scoreField.setText("На данный момент услуг закрепленных за вами - " + n + ".");
        serviceListData.clear();
        try { Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/veterinary_clinic",
                    "root", "1234")) { Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM service");
                while(resultSet.next()){ serviceListData.add(new BD(
                            resultSet.getInt("id_service"),
                            resultSet.getInt("id_workers"),
                            resultSet.getString("id_contract"),
                            resultSet.getString("price_rub"),
                            resultSet.getString("service_description")));}}
            if (!serviceListData.isEmpty()){ serviceListTable.setItems(serviceListData); }}
        catch (Exception e){ System.out.println("Ошибка в БД"); }}
}
