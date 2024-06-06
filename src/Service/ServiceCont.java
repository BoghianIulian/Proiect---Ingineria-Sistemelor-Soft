package Service;

import Domain.Cont;
import Repository.DuplicateObjectException;
import Repository.IRepository;
import Repository.RepositoryException;
import Repository.SQLContRepo;

import java.util.Collection;

public class ServiceCont {
    private IRepository<Cont> repoCont;

    public ServiceCont(){
        this.repoCont = new SQLContRepo("C:\\Users\\Iulian\\Documents\\GitHub\\IssApp\\src\\sala.db");
    }

    public void addCont(String username , String parola , String cnp)throws DuplicateObjectException
    {
        Cont c = new Cont(username, parola, cnp);
        repoCont.addElem(c);
    }
    public void removeCont(String username)throws RepositoryException
    {
        repoCont.removeElem(username);
    }

    public void updateCont(String user1 , String user2 , String parola)throws RepositoryException
    {
        Cont c_vechi = repoCont.getById(user1);
        Cont c2 = new Cont(user2 , parola, c_vechi.getCnp());
        repoCont.updateElem(c_vechi,c2);


    }
    public Collection<Cont> getAllT() throws RepositoryException
    {
        return repoCont.getAll();
    }

    public Cont getById(String id) throws RepositoryException
    {
        return repoCont.getById(id);
    }

    public Cont login(Cont cont)throws RepositoryException
    {
        Cont c = repoCont.getById(cont.getId());
        if(c == null) {
            throw new RepositoryException("User" + cont.getId() + "is not register in system");
        }
        return c;
    }
}
