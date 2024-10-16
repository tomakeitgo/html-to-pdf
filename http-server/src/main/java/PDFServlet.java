import com.tomakeitgo.html_to_pdf.Converter;
import com.tomakeitgo.html_to_pdf.Zippy;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet(name = "Test", urlPatterns = {"*"})
public class PDFServlet extends HttpServlet {

    public PDFServlet() {
        System.out.println("constructed!!");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("Hello!!!");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        new Converter().convert(
                new Zippy(req.getInputStream().readAllBytes()),
                resp.getOutputStream()
        );
    }
}
