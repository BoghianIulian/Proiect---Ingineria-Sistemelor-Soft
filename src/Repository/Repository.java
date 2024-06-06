package Repository;

import Domain.Entity;

import java.util.ArrayList;
import java.util.Collection;

public class Repository <T extends Entity> extends AbstractRepository<T>{

    @Override
    public int getSize(){return elems.size();}

    @Override
    public T getAt(int poz){
        return elems.get(poz);
    }

    @Override
    public boolean findById(String id){
        for(T elem:elems)
            if(elem.getId().equals(id))
                return true;
        return false;
    }

    @Override
    public T getById(String id) throws RepositoryException{
        for(T elem:elems)
            if(elem.getId().equals(id))
                return elem;
        throw new RepositoryException("Nu exista element cu acest id");
    }

    @Override
    public void addElem(T elem) throws DuplicateObjectException {
        if(findById(elem.getId()))
            throw new DuplicateObjectException("Exista deja un element cu acest id");
        else elems.add(elem);
    }

    @Override
    public void removeElem(String id) throws RepositoryException {
        if(!findById(id)) {
            throw new RepositoryException("Nu exista un element cu acest id");
        }
        else
          elems.remove(getById(id));
    }
    @Override
    public void updateElem(T elem1 , T elem2) throws RepositoryException
    {
        if (!findById(elem1.getId()))
            throw new RepositoryException("Nu exista un elem cu acest id");
        else {
            removeElem(elem1.getId());
            addElem(elem2);
        }
    }

    @Override
    public Collection<T> getAll() throws RepositoryException
    {
        if(this.elems.isEmpty())
            throw new RepositoryException("lista este goala");
        return new ArrayList(this.elems);
    }
}
