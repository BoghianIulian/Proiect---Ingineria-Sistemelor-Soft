package Domain;

import java.util.ArrayList;

public class Rezervare extends Entity{

    private String id;

    private ArrayList<Loc> lista;

    public Rezervare(String id, ArrayList<Loc> lista) {
        super(id);
        this.lista=lista;
    }

    public ArrayList<Loc> getLista() {
        return lista;
    }

    public float getPretTotal() {

        float sum =0;
        for(Loc l :lista)
        {
            sum+=l.getPret();
        }
        return sum;
    }
}
