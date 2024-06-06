package Service;

import Domain.Loc;
import Repository.*;

import java.util.Collection;

public class ServiceLoc {
    private IRepository<Loc> repoLoc;

    public ServiceLoc(){
        this.repoLoc = new SQLocRepo("C:\\Users\\Iulian\\Documents\\GitHub\\IssApp\\src\\sala.db");
    }

    public void addLoc(String rand_nr,float pret , boolean stare)throws DuplicateObjectException
    {
        Loc l =new Loc(rand_nr, pret, stare);
        repoLoc.addElem(l);
    }
    public void removeLoc(String rand_nr)throws RepositoryException{
        repoLoc.removeElem(rand_nr);
    }
    public void updateStare(String rand_nr)throws RepositoryException
    {
        Loc l =repoLoc.getById(rand_nr);
        if(l.isValid()){
            l.setStare(false);
        }
        else l.setStare(true);
        repoLoc.updateElem(repoLoc.getById(rand_nr),l);
    }
    public Collection<Loc> getAllT() throws RepositoryException
    {
        return repoLoc.getAll();
    }

    public Loc getById(String id) throws RepositoryException
    {
        return repoLoc.getById(id);
    }


}
