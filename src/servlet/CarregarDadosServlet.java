package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.UserBean;
import dao.UserDao;

@WebServlet("/pages/carregarDadosDataTable")
public class CarregarDadosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private UserDao userDao = new UserDao();
	
    public CarregarDadosServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		try {
			List<UserBean> usuarios = userDao.listar();
			
			if (!usuarios.isEmpty()) {
				String data = "";
				int total = usuarios.size();
				int index = 1;
				
				for (UserBean usuario : usuarios) {
					data += "[\"" + usuario.getId() + "\",\"" + usuario.getLogin() + "\",\"" + usuario.getSenha() + "\"]";
					
					if (index < total) {
						data += ",";
					}
					
					index++;
				}
				
				String json = "{\"draw\":1,\"recordsTotal\":" + usuarios.size() + ",\"recordsFiltered\":" + usuarios.size() + ",\"data\":[ " + data + " ]}";
				
				response.setStatus(200);
				response.getWriter().write(json);
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus(500);
			response.getWriter().write(e.getMessage());
		}
	}

	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
	}

}
