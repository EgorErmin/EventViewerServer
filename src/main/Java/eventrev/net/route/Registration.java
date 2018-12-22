package eventrev.net.route;

import com.google.gson.Gson;
import eventrev.net.model.ResponseData;
import eventrev.net.model.ResponseType;
import eventrev.net.model.Role;
import eventrev.net.model.User;
import eventrev.net.service.UserService;
import eventrev.net.service.ValidationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/registration")
public class Registration extends HttpServlet {

    ValidationService validationService = new ValidationService();
    UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User userByEmail = userService.findByEmail(email);
        if(userByEmail == null){
            ResponseData validation = validationService.register(email, password);

            if(validation == null){
                String firstName = req.getParameter("firstname");
                String lastName = req.getParameter("lastname");

                User user = new User();
                user.setEmail(email);
                user.setPassword(password);
                user.setFirstname(firstName);
                user.setLastname(lastName);
                user.setRole(Role.USER);

                userService.add(user);

                String json = new Gson().toJson(ResponseData.type(ResponseType.SUCCESS_REGISTRATION));
                resp.getWriter().write(json);
            } else {
                String json = new Gson().toJson(validation);
                resp.getWriter().write(json);
            }
        } else {
            String json = new Gson().toJson(ResponseData.type(ResponseType.EMAIL_REGISTERED));
            resp.getWriter().write(json);
        }

    }
}
