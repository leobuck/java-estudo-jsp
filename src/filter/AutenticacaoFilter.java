package filter;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bean.UserBean;
import connection.SingleConnection;
import connection.SingleConnection2;
import connection.SingleConnectionMySql;

@WebFilter(urlPatterns = {"/pages/*"})
public class AutenticacaoFilter implements Filter {

	private Connection connection;
	private Connection connection2;
	private Connection connectionMySql;
	
    public AutenticacaoFilter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, 
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		
		String url = req.getServletPath();
		
		UserBean userLogado = (UserBean) session.getAttribute("usuario");
		
		if (userLogado == null && !url.equalsIgnoreCase("/pages/autenticacaoServlet")) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/login.jsp?url=" + url);
			dispatcher.forward(request, response);
			return;
		}
		
		System.out.println("interceptando...");
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		//connection = SingleConnection.getConnection();
		//connection2 = SingleConnection2.getConnection();
		//connectionMySql = SingleConnectionMySql.getConnection();
	}

}
