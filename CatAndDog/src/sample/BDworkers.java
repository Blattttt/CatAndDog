package sample;

public class BDworkers {
    private Integer ID;
    private String Password;
    private String FIO;
    private String Gender;
    private String BirthDate;
    private String Job;
    private String Registration;

    public BDworkers(int ID, String Password, String FIO, String Gender, String BirthDate, String Job, String Registration) {
        this.ID = ID;
        this.Password = Password;
        this.FIO = FIO;
        this.Gender = Gender;
        this.BirthDate = BirthDate;
        this.Job = Job;
        this.Registration = Registration; }

    public BDworkers() {}

    public Integer getPrCode(){ return ID; }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String BirthDate) {
        this.BirthDate = BirthDate;
    }

    public String getJob() {
        return Job;
    }

    public void setJob(String Job) {
        this.Job = Job;
    }

    public String getRegistration() {
        return Registration;
    }

    public void setRegistration(String Registration) {
        this.Registration = Registration;
    }
}
