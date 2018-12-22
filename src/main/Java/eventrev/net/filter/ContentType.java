package eventrev.net.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


@WebFilter("*")
public class ContentType implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;


        System.out.println(String.format("[%s] %s %s",
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()),
                httpRequest.getMethod(),
                httpRequest.getRequestURI())
        );



        servletResponse.setContentType("application/json");
        servletResponse.setCharacterEncoding("UTF-8");
        if(httpRequest.getSession().getAttribute("user") == null && httpRequest.getRequestURI().equals("/registration")){
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else if(httpRequest.getSession().getAttribute("user") == null && !httpRequest.getRequestURI().equals("/authorization")){
            ((HttpServletResponse) servletResponse).sendRedirect("/authorization");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
