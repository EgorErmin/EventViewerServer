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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebServlet("/getuser/*")
public class GetUser extends HttpServlet {

    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] path = req.getRequestURI().split("/");
        if(path.length == 3 && path[2].matches("\\d+")) {
            Integer id = Integer.parseInt(path[2]);

            User user = userService.find(id);
            if(user == null){
                String json = new Gson().toJson(ResponseData.type(ResponseType.USER_DOES_NOT_EXISTS));
                resp.getWriter().write(json);
            } else {
                // Костыль
                List<Event> events = (new ArrayList<Event>(user.getEvents()));
                for(int i = 0;i < events.size();i++){
                    events.get(i).setUsers(null);
                }
                user.setEvents(new HashSet<>(events));
                // Костыль
                String json = new Gson().toJson(user);
                resp.getWriter().write(json);
            }
        } else {
            String json = new Gson().toJson(ResponseData.type(ResponseType.BAD_SIGNATURE));
            resp.getWriter().write(json);
        }
    }
}
