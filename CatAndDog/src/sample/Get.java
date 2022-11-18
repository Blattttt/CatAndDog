package sample;

public class Get {
    private static String userName;
    private static int userNumber;

    public static String getUserName(){
        return userName;
    }

    public static void setUserName(String user){
        userName = user;
    }

    public static int getUserNumber(){
        return userNumber;
    }

    public static void setUserNumber(int user_number){
        userNumber = user_number;
    }

}