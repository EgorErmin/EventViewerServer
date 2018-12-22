package eventrev.net.route;

import com.google.gson.Gson;
import eventrev.net.model.Event;
import eventrev.net.model.ResponseData;
import eventrev.net.model.ResponseType;
import eventrev.net.model.User;
import eventrev.net.service.UserService;
import netscape.javascript.JSObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/authorization")
public class Authorization extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User user = userService.findByEmail(email);
        if(user != null && user.passwordEquals(password)){
            req.getSession().setAttribute("user", user);
            User obj = user;
            obj.setEvents(null);
            obj.setPassword(null);
            String json = new Gson().toJson(obj);
            resp.getWriter().write(json);
        } else {
            resp.setStatus(401);
            String json = new Gson().toJson(ResponseData.type(ResponseType.INCORRECT_USER_DATA));
            resp.getWriter().write(json);
        }
    }
}
