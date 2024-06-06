package Domain;

public class Utilizator  extends Entity{


    private String name;

    private boolean isAdmin;


    public Utilizator(String cnp , String name,boolean isAdmin){
        super(cnp);
        this.name=name;
        this.isAdmin = isAdmin;
    }
    public String getName() {
        return name;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
