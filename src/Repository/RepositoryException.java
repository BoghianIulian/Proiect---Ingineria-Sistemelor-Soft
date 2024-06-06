package Repository;

public class RepositoryException extends Exception{
    public RepositoryException(String msg)
    {
        super(msg);

    }

    public RepositoryException(String msg ,Throwable cause){
        super(msg , cause);
    }
}
