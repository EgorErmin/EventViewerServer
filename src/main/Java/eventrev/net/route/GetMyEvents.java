package eventrev.net.route;

import com.google.gson.Gson;
import eventrev.net.model.Event;
import eventrev.net.model.ResponseData;
import eventrev.net.model.ResponseType;
import eventrev.net.model.User;
import eventrev.net.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@WebServlet("/getmyevents")
public class GetMyEvents extends HttpServlet {

    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer userId = ((User) req.getSession().getAttribute("user")).getId();
        User user = userService.find(userId);
        if(user != null){
            Set<Event> events = user.getEvents();
            for(Event e : events){
                for(User u : e.getUsers()){
                    u.setEvents(null);
                }
            }

            String json = new Gson().toJson(events);
            resp.getWriter().write(json);
        }
    }
}
