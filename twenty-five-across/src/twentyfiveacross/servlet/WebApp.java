package twentyfiveacross.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import twentyfiveacross.ejbs.UserManagerRemote;

/**
 * Servlet implementation class WebApp
 */
@WebServlet(name = "WebApp", urlPatterns = { "/main" })
public class WebApp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
    UserManagerRemote userManager;

    public WebApp() {
        super();
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param req servlet request
     * @param res servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest req,
            HttpServletResponse res) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        res.setContentType("text/html");

        PrintWriter out = res.getWriter();
        HttpSession session = req.getSession();

        String cmd;

        try {
            cmd = req.getParameter("cmd");

            if (null!=cmd) {
                if ("printHeader".equals(cmd)) {
                    WebPages.printHeader(out);
                }
                else if ("printMain".equals(cmd)) {
                    if(true/*user.loggedIn*/)
                    	WebPages.printMain(out);
                    else
                    	WebPages.printLogin(out, null);
                }
                else if ("printRegister".equals(cmd)) {
                    WebPages.printRegister(out,null);
                }
                else if ("processRegister".equals(cmd)) {
                	if(userManager.createUser(req.getParameter("_U"),req.getParameter("name"),req.getParameter("_P")))
                		WebPages.printLogin(out,"Registration Succeeded! Please Log in.");
                	else
                		WebPages.printRegister(out,"Registration Failed! Please try again.");
				}
                else if ("processLogin".equals(cmd)) {
					if (userManager.login(req.getParameter("_U"),req.getParameter("_P")))
						WebPages.printMain(out);
					else
						WebPages.printLogin(out,"Login Failed! Please try again.");
				}
                else
					WebPages.printFrameset(out);
            }
            else
            {
                WebPages.printLogin(out,null);
            	return;
            }

        }
        catch(Exception e) {
            System.out.println("Error(doPost): " + e);
            e.printStackTrace();
        }
        finally {
            out.close();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
