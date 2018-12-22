package eventrev.net.patterns;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Manager {
    private Manager(){
        throw new AssertionError();
    }

    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("eventrev");

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
