package eventrev.net.route;

import com.google.gson.Gson;
import eventrev.net.model.ResponseData;
import eventrev.net.model.ResponseType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/*")
public class NotFound extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = new Gson().toJson(ResponseData.type(ResponseType.NOT_FOUND));
        resp.getWriter().write(json);
    }
}
