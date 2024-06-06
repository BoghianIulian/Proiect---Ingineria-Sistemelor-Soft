package Domain;

public class Cont extends Entity{
     private String parola;
     private String cnp;
    public Cont(String username, String parola,String cnp) {
        super(username);
        this.parola = parola;
        this.cnp=cnp;
    }

    public String getParola() {
        return parola;
    }

    public String getCnp(){return cnp;}
}
