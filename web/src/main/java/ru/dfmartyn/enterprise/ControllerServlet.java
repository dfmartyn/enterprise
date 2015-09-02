package ru.dfmartyn.enterprise;

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
@WebServlet(name = "ControllerServlet", urlPatterns = "hello")
public class ControllerServlet extends HttpServlet {

    @EJB
    private LocalInterface stateless;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().write("Bean = " + stateless.toString());
        response.getWriter().write("\n");
        response.getWriter().write("Session Id = " + request.getSession().getId());
        response.getWriter().write("\n");
        response.getWriter().write("Result = " + stateless.helloWorld());
    }
}
