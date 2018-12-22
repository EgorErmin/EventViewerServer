package eventrev.net.route;

import com.google.gson.Gson;
import eventrev.net.model.ResponseData;
import eventrev.net.model.Event;
import eventrev.net.model.ResponseType;
import eventrev.net.service.EventService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/getevent/*")
public class GetEvent extends HttpServlet {
    private EventService eventService = new EventService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] path = req.getRequestURI().split("/");
        if(path.length == 3 && path[2].matches("\\d+")){
            Integer id = Integer.parseInt(path[2]);
            Event event = eventService.find(id);
            if(event != null){
                event.setUsers(null);

                String json = new Gson().toJson(event);
                resp.getWriter().write(json);
            } else {
                String json = new Gson().toJson(ResponseData.type(ResponseType.EVENT_DOES_NOT_EXISTS));
                resp.getWriter().write(json);
            }
        } else {
            String json = new Gson().toJson(ResponseData.type(ResponseType.BAD_SIGNATURE));
            resp.getWriter().write(json);
        }
    }
}
