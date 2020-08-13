package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.UserBean;

@WebServlet("/pages/autenticacaoServlet")
public class AutenticacaoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AutenticacaoServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		if (Boolean.parseBoolean(request.getParameter("sair"))) {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();
			session.invalidate();
			response.sendRedirect("../index.jsp");
		}
		
	}

	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		String url = request.getParameter("url");
		
		if (login.equalsIgnoreCase("admin") && senha.equalsIgnoreCase("admin")) {
			
			UserBean userBean = new UserBean();
			userBean.setLogin(login);
			userBean.setSenha(senha);
			
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();
			session.setAttribute("usuario", userBean);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/login.jsp");
			dispatcher.forward(request, response);
		}
	}

}
