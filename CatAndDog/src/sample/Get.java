package sample;

public class Get {
    private static String userName;
    private static int userNumber;
    private static String updatePrice;
    private static String updateDescription;
    private static int updateNumber;
    private static String updateContract;

    public static String getUserName(){ return userName; }

    public static void setUserName(String user){
        userName = user;
    }

    public static int getUserNumber(){
        return userNumber;
    }

    public static void setUserNumber(int user_number){
        userNumber = user_number;
    }

    public static int getNumberS(){
        return updateNumber;
    }

    public static void setNumberS(int num){
        updateNumber = num;
    }

    public static String getContractS(){
        return updateContract;
    }

    public static void setContractS(String cont){
        updateContract = cont;
    }

    public static String getPriceS(){
        return updatePrice;
    }

    public static void setPriceS(String price){
        updatePrice = price;
    }

    public static String getDescriptionS(){
        return updateDescription;
    }

    public static void setDescriptionS(String desc){
        updateDescription = desc;
    }
}