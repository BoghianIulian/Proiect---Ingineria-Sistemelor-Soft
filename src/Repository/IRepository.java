package Repository;





import Domain.Entity;

import java.util.Collection;

public interface IRepository<T extends Entity> extends Iterable<T> {

    public int getSize();
    public T getAt(int poz);

    public void addElem(T o) throws DuplicateObjectException;

    public void removeElem(String id) throws RepositoryException;

    public void updateElem(T elem1 , T elem2) throws RepositoryException;

    public boolean findById(String id);

    public T getById(String id) throws RepositoryException;

    public Collection<T> getAll() throws RepositoryException;
}
