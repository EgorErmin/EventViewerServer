package eventrev.net.dao;

import eventrev.net.patterns.Manager;
import eventrev.net.model.Event;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class EventDAO {
    public Event add(Event event){
        EntityManager entityManager = Manager.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(event);
        entityManager.getTransaction().commit();
        entityManager.close();
        return event;
    }


    public Event findEvent(int id) {
        EntityManager entityManager = Manager.getEntityManager();
        Event event = entityManager.find(Event.class, id);
        entityManager.close();
        return event;
    }

    public List<Event> findByPage(int page, int limit){
        EntityManager entityManager = Manager.getEntityManager();
        TypedQuery<Event> events = entityManager.createQuery("SELECT e FROM Event e ", Event.class)
                .setFirstResult((limit * page) - limit)
                .setMaxResults(limit);
        return events.getResultList();
    }

    public List<Event> findAmount(int limit){
        EntityManager entityManager = Manager.getEntityManager();
        TypedQuery<Event> events = entityManager.createQuery("SELECT e FROM Event e ", Event.class)
                .setMaxResults(limit);
        return events.getResultList();
    }


    public List<Event> findAllEvents() {
        EntityManager entityManager = Manager.getEntityManager();
        TypedQuery<Event> query = entityManager.createQuery("SELECT e FROM Event e", Event.class);
        List<Event> event = query.getResultList();
        entityManager.close();
        return event;
    }

    public void registerUserToEvent(Integer userId, Integer eventId){
        EntityManager entityManager = Manager.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("INSERT INTO user_event (user_id, event_id) VALUES (:userId, :eventId)")
                .setParameter("userId", userId)
                .setParameter("eventId", eventId)
                .executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

}
