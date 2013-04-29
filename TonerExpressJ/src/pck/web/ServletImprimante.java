package pck.web;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pck.dao.DAO_TonerExpress;

/**
 * Servlet implementation class ServletImprimante
 */
@WebServlet("/ServletImprimante")
public class ServletImprimante extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletImprimante() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*PrintWriter out;
		response.setContentType("text/html");
		out=response.getWriter();
		out.println("<h1>Liste des imprimantes<br/></h1>");
		for (Imprimante imp : DAO_TonerExpress.getLesImprimantes())
			out.println(imp.toString()+"<br/>");
		out.flush();*/

		ServletConfig config=getServletConfig();
		String actionListerLesImprimantes = config.getInitParameter("listerLesImprimantes");
		request.setAttribute("lesImprimantes",DAO_TonerExpress.getLesImprimantes());
		getServletContext().getRequestDispatcher(actionListerLesImprimantes).forward(request,response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
