package Repository;

public class DuplicateObjectException extends RepositoryException{
    public DuplicateObjectException(String msg)
    {
        super(msg);
    }

    public DuplicateObjectException(String msg , Throwable cause){
        super(msg , cause);
    }
}
