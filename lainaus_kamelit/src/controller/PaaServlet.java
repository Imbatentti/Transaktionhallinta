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

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		if (request.getParameter("action") != null && 
				request.getParameter("action").equals("Hae kaikki lainaukset")) {
			try {
				request.setAttribute("lainauslista",LainausDAO.haeKaikkiLainaukset());
			} 
			catch (SQLException e) { e.printStackTrace();
			}
			request.getRequestDispatcher("KaikkiLainaukset.jsp").forward(request, response);

		} 
		else if (request.getParameter("action") != null
				&& request.getParameter("action").equals("Hae lainaus")) {
			System.out.println("get");
			int numero = Integer.parseInt(request.getParameter("lainausnumero"));
			try {
				request.setAttribute("lainauslista",LainausDAO.haeYksiLainaus(numero));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("hae lainaus");
			request.getRequestDispatcher("YksiLainaus.jsp").forward(request,response);

		}
		else {
			try { request.setAttribute("lainausnumerot", LainausDAO.haeLainausNumerot());
			} 
			catch (SQLException e) {e.printStackTrace();
			}
			request.getRequestDispatcher("etusivu.jsp").forward(request,
					response);
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("action") != null
				&& request.getParameter("action").equals("Hae lainaus")) {
			System.out.println("post");
			int numero = Integer.parseInt(request.getParameter("lainausnumero"));
			try {
				request.setAttribute("lainaus",LainausDAO.haeYksiLainaus(numero));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("hae lainaus");
			request.getRequestDispatcher("YksiLainaus.jsp").forward(request,response);

		}
		else if (request.getParameter("action") != null && 
				request.getParameter("action").equals("Hae kaikki lainaukset")) {
			try {
				request.setAttribute("lainauslista",LainausDAO.haeKaikkiLainaukset());
			} 
			catch (SQLException e) { e.printStackTrace();
			}
			request.getRequestDispatcher("KaikkiLainaukset.jsp").forward(request, response);

		}
	}

}
