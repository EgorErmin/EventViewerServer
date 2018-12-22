package eventrev.net.route;

import com.google.gson.Gson;
import eventrev.net.model.Event;
import eventrev.net.model.ResponseData;
import eventrev.net.model.ResponseType;
import eventrev.net.model.User;
import eventrev.net.service.EventService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/geteventsbyamount/*")
public class GetEventByAmount extends HttpServlet {

    private EventService eventService = new EventService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] path = req.getRequestURI().split("/");
        if(path.length == 3 && path[2].matches("\\d+")){
            Integer limit = Integer.parseInt(path[2]);

            List<Event> events = eventService.findAmount(limit);
            if(events.size() == 0){
                String json = new Gson().toJson(ResponseData.type(ResponseType.EVENT_DOES_NOT_EXISTS));
                resp.getWriter().write(json);
            } else {
                for(Event e : events){
                    for(User u : e.getUsers()){
                        u.setEvents(null);
                    }
                }
                String json = new Gson().toJson(events);
                resp.getWriter().write(json);
            }
        } else {
            String json = new Gson().toJson(ResponseData.type(ResponseType.BAD_SIGNATURE));
            resp.getWriter().write(json);
        }
    }
}
