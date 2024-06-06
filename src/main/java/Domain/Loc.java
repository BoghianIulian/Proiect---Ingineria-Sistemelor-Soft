package Domain;

public class Loc extends Entity {


    private float pret;

    private boolean stare;

    @Override
    public String toString() {
        return "Loc{" +
                "pret=" + pret +
                ", stare=" + stare +
                '}';
    }

    public Loc(String rand_nr, float pret , boolean stare) {

        super(rand_nr);
        this.pret = pret;
        this.stare=stare;
    }

    public int getRand() {
        String[] tok = getId().split(",");
        return Integer.parseInt(tok[0]);
    }

    public int getNumar() {
        String[] tok = getId().split(",");
        return Integer.parseInt(tok[1]);
    }

    public float getPret() {
        return pret;
    }

    public boolean isStare() {
        return stare;
    }

    public boolean isValid(){
        if(this.stare)
            return true;
        return false;
    }

    public void setStare(boolean stare) {
        this.stare = stare;
    }
}
