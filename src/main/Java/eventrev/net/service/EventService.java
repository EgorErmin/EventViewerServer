package eventrev.net.service;

import eventrev.net.dao.EventDAO;
import eventrev.net.model.Event;

import java.util.List;

public class EventService {

    private EventDAO eventDAO = new EventDAO();
    private final int PAGE_LIMIT = 10;

    public Event add(Event event){
        return eventDAO.add(event);
    }

    public Event find(Integer id){
        return eventDAO.findEvent(id);
    }

    public List<Event> findAmount(int limit){
        return eventDAO.findAmount(limit);
    }

    public List<Event> findByPage(int page){
        return eventDAO.findByPage(page, PAGE_LIMIT);
    }

    public List<Event> findAll(){
        return eventDAO.findAllEvents();
    }

    public void registerUserToEvent(Integer userId, Integer eventId) {
        eventDAO.registerUserToEvent(userId, eventId);
    }
}
