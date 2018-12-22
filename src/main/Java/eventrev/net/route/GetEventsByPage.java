package eventrev.net.route;

import com.google.gson.Gson;
import eventrev.net.model.Event;
import eventrev.net.model.ResponseData;
import eventrev.net.model.ResponseType;
import eventrev.net.service.EventService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/geteventsbypage/*")
public class GetEventsByPage extends HttpServlet {

    private EventService eventService = new EventService();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] path = req.getRequestURI().split("/");
        if(path.length == 3 && path[2].matches("\\d+")) {
            Integer page = Integer.parseInt(path[2]);
            List<Event> events = eventService.findByPage(page);
            if(events.size() == 0){
                String json = new Gson().toJson(ResponseData.type(ResponseType.PAGE_NOT_FOUND));
                resp.getWriter().write(json);
            } else {
                for(int i = 0;i < events.size();i++){
                    events.get(i).setUsers(null);
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
