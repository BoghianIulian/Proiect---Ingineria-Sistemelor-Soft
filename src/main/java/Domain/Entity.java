package Domain;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    private static final long serialVersionUID = 1000L;

    private final String id;

    public Entity(String id){
        this.id = id;
    }

    public String getId()
    {
        return id;
    }

}
