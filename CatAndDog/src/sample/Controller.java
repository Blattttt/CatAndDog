package sample;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Controller {
    @FXML private TextField loginField;
    @FXML private PasswordField passwordField;
    @FXML private Button enterButton;
    @FXML private Label exceptionField;
    public String user;
    public int user_number;
    @FXML private void initialize() {
        FXMLLoader loader = new FXMLLoader();

        loginField.textProperty().addListener((observableValue, s, t1) -> exceptionField.setVisible(false));
        passwordField.textProperty().addListener((observableValue, s, t1) -> exceptionField.setVisible(false));

        enterButton.setOnAction(event -> {
            try { String id = loginField.getText();
                String password = passwordField.getText();
                Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/veterinary_clinic",
                        "root", "1234")) { System.out.println("Подключение к бд");
                    Statement statement = conn.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM workers");
                    while (resultSet.next()) {
                        if (resultSet.getString("id_workers").equals(id) &&
                                resultSet.getString("password").equals(password) &&
                                resultSet.getString("job").equals("Вет")) {
                            System.out.println("Успешный вход ветеренар");
                            user = resultSet.getString("full_name");
                            Get.setUserName(user);
                            user_number = resultSet.getInt("id_workers");
                            Get.setUserNumber(user_number);
                            enterButton.getScene().getWindow().hide();
                            loader.setLocation(getClass().getResource("vetWindow.fxml"));
                            Parent root = null;
                            try { root = loader.load(); }
                            catch (IOException e) { e.printStackTrace(); }
                            Stage stage = new Stage();
                            assert root != null;
                            stage.setScene(new Scene(root));
                            stage.show();
                            break;}
                        else if (resultSet.getString("id_workers").equals(id) &&
                                resultSet.getString("password").equals(password) &&
                                resultSet.getString("job").equals("Вет/Бух")) {
                            System.out.println("Успешный вход бухгалтер");
                            user = resultSet.getString("full_name");
                            Get.setUserName(user);
                            user_number = resultSet.getInt("id_workers");
                            Get.setUserNumber(user_number);
                            enterButton.getScene().getWindow().hide();
                            loader.setLocation(getClass().getResource("buhWindow.fxml"));
                            Parent root = null;
                            try { root = loader.load(); }
                            catch (IOException e) { e.printStackTrace(); }
                            Stage stage = new Stage();
                            assert root != null;
                            stage.setScene(new Scene(root));
                            stage.show();
                            break;}
                        else exceptionField.setVisible(true);}}}
            catch (Exception ex) { System.out.println("Ошибка доступа к БД"); }});
    }}
