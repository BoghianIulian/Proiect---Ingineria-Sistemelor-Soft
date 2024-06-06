package Service;

import Domain.Loc;
import Domain.Rezervare;
import Repository.*;

import java.util.ArrayList;
import java.util.Collection;

public class ServiceRezervare {
    private IRepository<Rezervare> repoRez;

    public ServiceRezervare(){
        this.repoRez = new SQLRezervareRepo("C:\\Users\\Iulian\\Documents\\GitHub\\IssApp\\src\\sala.db");
    }

    public void addRez(String id, ArrayList<Loc> lista)throws DuplicateObjectException {
        Rezervare r = new Rezervare(id, lista);
        repoRez.addElem(r);
    }

    public void removeRez(String id)throws RepositoryException{
        repoRez.removeElem(id);
    }

    public void updateRez(String id,ArrayList<Loc> rez_nou)throws RepositoryException
    {
        Rezervare r_vechi = repoRez.getById(id);
        Rezervare r_nou = new Rezervare(id,rez_nou);
        repoRez.updateElem(r_vechi,r_nou);
    }

    public Collection<Rezervare> getAllT() throws RepositoryException
    {
        return repoRez.getAll();
    }

    public Rezervare getById(String id) throws RepositoryException
    {
        return repoRez.getById(id);
    }
}
