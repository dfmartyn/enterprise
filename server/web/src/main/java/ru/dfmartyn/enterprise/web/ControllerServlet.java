package ru.dfmartyn.enterprise.web;

import ru.dfmartyn.enterprise.ejb.LocalInterface;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Dmitriy Martynov
 */
@WebServlet(name = "controller", urlPatterns = "/hello")
public class ControllerServlet extends HttpServlet {

    private long id = System.currentTimeMillis();

    @EJB
    private LocalInterface stateless;

    public ControllerServlet() {
        System.out.println("Controller constructor. Id = " + id);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.getWriter().write("Controller id = " + id);
        response.getWriter().write("\n");
        response.getWriter().write("Bean = " + stateless.toString());
        response.getWriter().write("\n");
        response.getWriter().write("Session Id = " + request.getSession().getId());
        response.getWriter().write("\n");
        response.getWriter().write("Result = " + stateless.helloWorld());
    }
}
