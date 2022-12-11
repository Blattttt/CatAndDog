package sample;

public class BDcontract {
    private Integer IdContract;
    private String DateContract;
    private String Exam;
    private String IdAnimal;
    private String Passport;

    public BDcontract(int IdContract, String DateContract, String Exam, String IdAnimal, String Passport) {
        this.IdContract = IdContract;
        this.DateContract = DateContract;
        this.Exam = Exam;
        this.IdAnimal = IdAnimal;
        this.Passport = Passport;}

    public BDcontract() {}

    public Integer getPrCode(){ return IdContract; }

    public Integer getIdContract() {
        return IdContract;
    }

    public void setIdContract(int IdContract) {
        this.IdContract = IdContract;
    }

    public String getDateContract() {
        return DateContract;
    }

    public void setDateContract(String DateContract) {
        this.DateContract = DateContract;
    }

    public String getExam() {
        return Exam;
    }

    public void setExam(String Exam) {
        this.Exam = Exam;
    }

    public String getIdAnimal() {
        return IdAnimal;
    }

    public void setIdAnimal(String IdAnimal) {
        this.IdAnimal = IdAnimal;
    }

    public String getPassport() {
        return Passport;
    }

    public void setPassport(String Passport) {
        this.Passport = Passport;
    }
}
