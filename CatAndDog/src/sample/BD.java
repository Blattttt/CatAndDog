package sample;

public class BD {
    private Integer Number;
    private String Worker;
    private String Contract;
    private String Price;
    private String Description;

    public BD(int Number, String Worker, String Contract, String Price, String Description) {
        this.Number = Number;
        this.Worker = Worker;
        this.Contract = Contract;
        this.Price = Price;
        this.Description = Description;}

    public BD() {}

    public Integer getPrCode(){ return Number; }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int Number) {
        this.Number = Number;
    }

    public String getWorker() {
        return Worker;
    }

    public void setWorker(String Worker) {
        this.Worker = Worker;
    }

    public String getContract() {
        return Contract;
    }

    public void setContract(String Contract) {
        this.Contract = Contract;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String Price) {
        this.Price = Price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }
}