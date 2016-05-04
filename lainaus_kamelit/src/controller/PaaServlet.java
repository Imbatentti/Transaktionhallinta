package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.LainausDAO;


@WebServlet("/PaaServlet")
public class PaaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public PaaServlet() {
        super(); 
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("action")!= null && request.getParameter("action").equals("kaikki")) {					
			try {
				request.setAttribute("lainauslista", LainausDAO.haeKaikkiLainaukset());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			request.getRequestDispatcher("KaikkiLainaukset.jsp").forward(request, response);
			
			
		/*}else if(request.getParameter("action")!= null && request.getParameter("action").equals("yksi")) {
			
			String numero = request.getParameter("numero"); // tällä se numero sinne daoo?
			
			try {
				request.setAttribute("lainaus", LainausDAO.haeYksiLainaus());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.getRequestDispatcher("YksiLainaus.jsp").forward(request, response);
			
			*/
		}else {
			try {
				request.setAttribute("numerot", LainausDAO.haeNumerot());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.getRequestDispatcher("etusivu.jsp").forward(request, response);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
