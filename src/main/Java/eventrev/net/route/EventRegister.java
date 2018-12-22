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

@WebServlet("/eventregister/*")
public class EventRegister extends HttpServlet {

    private EventService eventService = new EventService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] path = req.getRequestURI().split("/");
        if(path.length == 3 && path[2].matches("\\d+")) {
            Integer id = Integer.parseInt(path[2]);


            Event event = eventService.find(id);
            if(event != null){
                User user = ((User) req.getSession().getAttribute("user"));
                eventService.registerUserToEvent(user.getId(), event.getId());

                String json = new Gson().toJson(ResponseData.type(ResponseType.SUCCESS_REGISTER_TO_EVENT));
                resp.getWriter().write(json);
            } else {
                String json = new Gson().toJson(ResponseData.type(ResponseType.EVENT_DOES_NOT_EXISTS));
                resp.getWriter().write(json);
            }
        }

    }
}
