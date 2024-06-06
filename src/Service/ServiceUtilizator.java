package Service;

import Domain.Utilizator;
import Repository.*;

import java.util.Collection;

public class ServiceUtilizator {
    private IRepository<Utilizator> repoUtil;

    public ServiceUtilizator(){
        this.repoUtil = new SQLUtilizatorRepo("C:\\Users\\Iulian\\Documents\\GitHub\\IssApp\\src\\sala.db");
    }

    public void addUtilizator(String cnp,String name,boolean isValid)throws DuplicateObjectException
    {
     Utilizator l = new Utilizator(cnp,name,isValid);
        repoUtil.addElem(l);
    }
    public void removeUtilizator(String username)throws RepositoryException
    {
        repoUtil.removeElem(username);
    }

    public Collection<Utilizator> getAllT() throws RepositoryException
    {
        return repoUtil.getAll();
    }

    public Utilizator getById(String id) throws RepositoryException
    {
        return repoUtil.getById(id);
    }




}
