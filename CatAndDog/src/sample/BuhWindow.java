package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;

public class BuhWindow {
    public int n;
    @FXML private Button updateButton;
    @FXML private Button refreshButton;
    @FXML private Button newButton;
    @FXML private Button exitButton;
    @FXML private Button newWorkerButton;
    @FXML private Button newContractButton;
    @FXML private Label nameField;
    @FXML private Label scoreField;
    @FXML private TableView<BD> serviceListTable;
    @FXML private TableColumn<BD, String> Number;
    @FXML private TableColumn<BD, String> Worker;
    @FXML private TableColumn<BD, String> Contract;
    @FXML private TableColumn<BD, String> Price;
    @FXML private TableColumn<BD, String> Description;
    private final ObservableList<BD> serviceListData = FXCollections.observableArrayList();
    @FXML private TableView<BDcontract> contractsListTable;
    @FXML private TableColumn<BDcontract, String> IdContract;
    @FXML private TableColumn<BDcontract, String> DateContract;
    @FXML private TableColumn<BDcontract, String> Exam;
    @FXML private TableColumn<BDcontract, String> IdAnimal;
    @FXML private TableColumn<BDcontract, String> Passport;
    private final ObservableList<BDcontract> contractsListData = FXCollections.observableArrayList();
    private BD selectedTable1 = new BD();
    @FXML private Button removeServiceButton;
    @FXML private Button cancelServiceButton;
    @FXML private Button removeServiceButton2;
    @FXML private Label labelServiceMessage;
    private BDcontract selectedTable2 = new BDcontract();
    @FXML private Button removeContractButton;
    @FXML private Button cancelContractButton;
    @FXML private Button removeContractButton2;
    @FXML private Label labelContractMessage;

    @FXML private void initialize() {

        nameField.setText("Здравстуйте, " + Get.getUserName() + "!" + " Ваш ID - " + Get.getUserNumber());

        Number.setCellValueFactory(new PropertyValueFactory<>("Number"));
        Worker.setCellValueFactory(new PropertyValueFactory<>("Worker"));
        Contract.setCellValueFactory(new PropertyValueFactory<>("Contract"));
        Price.setCellValueFactory(new PropertyValueFactory<>("Price"));
        Description.setCellValueFactory(new PropertyValueFactory<>("Description"));

        IdContract.setCellValueFactory(new PropertyValueFactory<>("IdContract"));
        DateContract.setCellValueFactory(new PropertyValueFactory<>("DateContract"));
        Exam.setCellValueFactory(new PropertyValueFactory<>("Exam"));
        IdAnimal.setCellValueFactory(new PropertyValueFactory<>("IdAnimal"));
        Passport.setCellValueFactory(new PropertyValueFactory<>("Passport"));

        FXMLLoader loader = new FXMLLoader();
        tablerefresh();
        tablerefresh2();

        refreshButton.setOnAction(actionEvent -> { tablerefresh();
            tablerefresh2();});

        newButton.setOnAction(event -> {
            loader.setLocation(getClass().getResource("buhNewService.fxml"));
            newButton.getScene().getWindow().hide();
            Parent root = null;
            try {root = loader.load(); }
            catch (IOException e) { e.printStackTrace();}
            Stage stage = new Stage();
            assert root != null;
            stage.setScene(new Scene(root));
            stage.show();});

        newContractButton.setOnAction(event -> {
            loader.setLocation(getClass().getResource("BuhNewContract.fxml"));
            newButton.getScene().getWindow().hide();
            Parent root = null;
            try {root = loader.load(); }
            catch (IOException e) { e.printStackTrace();}
            Stage stage = new Stage();
            assert root != null;
            stage.setScene(new Scene(root));
            stage.show();});

        newWorkerButton.setOnAction(event -> {
            loader.setLocation(getClass().getResource("workersWindow.fxml"));
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

        removeServiceButton.setOnAction(event -> {
            selectedTable1 = serviceListTable.getSelectionModel().getSelectedItem();
            selectedTable1.getPrCode();
            removeServiceButton.setVisible(false);
            removeServiceButton2.setVisible(true);
            cancelServiceButton.setVisible(true);
            labelServiceMessage.setVisible(true);});

        removeServiceButton2.setOnAction(event -> {
            deleteTableService(selectedTable1.getPrCode());
            serviceListTable.getItems().remove(selectedTable1);
            removeServiceButton.setVisible(true);
            removeServiceButton2.setVisible(false);
            cancelServiceButton.setVisible(false);
            labelServiceMessage.setVisible(false);
            tablerefresh();});

        cancelServiceButton.setOnAction(event -> {
            removeServiceButton.setVisible(true);
            removeServiceButton2.setVisible(false);
            cancelServiceButton.setVisible(false);
            labelServiceMessage.setVisible(false);});

        updateButton.setOnAction(event -> {
            selectedTable1 = serviceListTable.getSelectionModel().getSelectedItem();
            if(!selectedTable1.getContract().isEmpty()){
                Get.setNumberS(selectedTable1.getPrCode());
                Get.setContractS(selectedTable1.getContract());
                Get.setPriceS(selectedTable1.getPrice());
                Get.setDescriptionS(selectedTable1.getDescription());
                loader.setLocation(getClass().getResource("buhServiceUpdate.fxml"));
                exitButton.getScene().getWindow().hide();
                Parent root = null;
                try {root = loader.load(); }
                catch (IOException e) { e.printStackTrace();}
                Stage stage = new Stage();
                assert root != null;
                stage.setScene(new Scene(root));
                stage.show();}});

        removeContractButton.setOnAction(event -> {
            selectedTable2 = contractsListTable.getSelectionModel().getSelectedItem();
            selectedTable2.getPrCode();
            removeContractButton.setVisible(false);
            removeContractButton2.setVisible(true);
            cancelContractButton.setVisible(true);
            labelContractMessage.setVisible(true);});

        removeContractButton2.setOnAction(event -> {
            deleteTableContract(selectedTable2.getPrCode());
            contractsListTable.getItems().remove(selectedTable2);
            removeContractButton.setVisible(true);
            removeContractButton2.setVisible(false);
            cancelContractButton.setVisible(false);
            labelContractMessage.setVisible(false);
            tablerefresh();});

        cancelContractButton.setOnAction(event -> {
            removeContractButton.setVisible(true);
            removeContractButton2.setVisible(false);
            cancelContractButton.setVisible(false);
            labelContractMessage.setVisible(false);});
    }

    public static void deleteTableService(int Code1) {
        String querry = "DELETE FROM service WHERE id_service = "+ Code1;
        try {Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/veterinary_clinic",
                "root", "1234");
            PreparedStatement preparedStatement = conn.prepareStatement(querry);
            preparedStatement.executeUpdate(); }
        catch (SQLException throwables) { throwables.printStackTrace(); }}

    public static void deleteTableContract(int Code1) {
        String querry = "DELETE FROM contract WHERE id_contract = "+ Code1;
        try {Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/veterinary_clinic",
                "root", "1234");
            PreparedStatement preparedStatement = conn.prepareStatement(querry);
            preparedStatement.executeUpdate(); }
        catch (SQLException throwables) { throwables.printStackTrace(); }}

    private void tablerefresh(){
        try { Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/veterinary_clinic",
                    "root", "1234")) { Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM service");
                n = 0;
                while (resultSet.next()) {
                    if(resultSet.getInt("id_workers") == Get.getUserNumber()){
                        n++;
                        continue;}}}}
        catch (Exception e) { System.out.println("Ошибка в БД"); }
        scoreField.setText("На данный момент услуг закрепленных за вами - " + n + ".");
        serviceListData.clear();
        try { Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/veterinary_clinic",
                    "root", "1234")) { Statement statement1 = conn.createStatement();
                ResultSet resultSet1 = statement1.executeQuery("SELECT * FROM service");
                while(resultSet1.next()){ serviceListData.add(new BD(
                            resultSet1.getInt("id_service"),
                            resultSet1.getInt("id_workers"),
                            resultSet1.getString("id_contract"),
                            resultSet1.getString("price_rub"),
                            resultSet1.getString("service_description")));}}
            if (!serviceListData.isEmpty()){ serviceListTable.setItems(serviceListData); }}
        catch (Exception e){ System.out.println("Ошибка в БД"); }}

    private void tablerefresh2(){
        contractsListData.clear();
        try { Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/veterinary_clinic",
                    "root", "1234")) {
                Statement statement2 = conn.createStatement();
                ResultSet resultSet2 = statement2.executeQuery("SELECT * FROM contract");
                while(resultSet2.next()){ contractsListData.add(new BDcontract(
                            resultSet2.getInt("id_contract"),
                            resultSet2.getString("date_contract"),
                            resultSet2.getString("date_appointment"),
                            resultSet2.getString("id_animal"),
                            resultSet2.getString("owner_phone")));}}
            if (!contractsListData.isEmpty()){ contractsListTable.setItems(contractsListData); }}
        catch (Exception e){ System.out.println("Ошибка в БД"); }}
}
