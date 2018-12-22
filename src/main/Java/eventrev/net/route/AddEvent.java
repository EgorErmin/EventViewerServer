package eventrev.net.route;

import com.google.gson.Gson;
import eventrev.net.model.*;
import eventrev.net.service.EventService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/addevent")
public class AddEvent extends HttpServlet {

    private EventService eventService = new EventService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if(user != null /*&& user.getRole() == Role.ADMIN*/){
            String name = req.getParameter("name_event");
            Date date;
            try{
                date = new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy").parse(req.getParameter("date"), new ParsePosition(0));
            } catch(Exception e){
                date = null;
            }

            String place = req.getParameter("place");
            String information = req.getParameter("information");

            Event event = new Event();
            event.setName(name);
            event.setData(date);
            event.setPlace(place);
            event.setInformation(information);

            System.out.println(event.getName());

            eventService.add(event);
            String json = new Gson().toJson(ResponseData.type(ResponseType.SUCCESS_ADD_EVENT));
            resp.getWriter().write(json);
        } else {
            String json = new Gson().toJson(ResponseData.type(ResponseType.ACCESS_DENIED));
            resp.getWriter().write(json);
        }
    }
}
