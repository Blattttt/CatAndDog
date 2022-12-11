package sample;

public class BDanimals {
    private Integer IdAnimal;
    private String Name;
    private String NameOwner;
    private String BirthDate;
    private String Type;
    private String Breed;
    private String Gender;

    public BDanimals(int IdAnimal, String Name, String NameOwner, String BirthDate, String Type, String Breed, String Gender) {
        this.IdAnimal = IdAnimal;
        this.Name = Name;
        this.NameOwner = NameOwner;
        this.BirthDate = BirthDate;
        this.Type = Type;
        this.Breed = Breed;
        this.Gender = Gender;}

    public BDanimals() {}

    public Integer getPrCode(){ return IdAnimal; }

    public Integer getIdAnimal() {
        return IdAnimal;
    }

    public void setIdAnimal(Integer IdAnimal) {
        this.IdAnimal = IdAnimal;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getNameOwner() {
        return NameOwner;
    }

    public void setNameOwner(String NameOwner) {
        this.NameOwner = NameOwner;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String BirthDate) {
        this.BirthDate = BirthDate;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getBreed() {
        return Breed;
    }

    public void setBreed(String Breed) {
        this.Breed = Breed;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

}
