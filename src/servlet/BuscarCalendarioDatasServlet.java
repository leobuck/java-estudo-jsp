package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.EventoBean;
import dao.EventoDao;

@WebServlet("/pages/buscarCalendarioDatas")
public class BuscarCalendarioDatasServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private EventoDao eventoDao = new EventoDao();
	
    public BuscarCalendarioDatasServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
	
		try {
			List<EventoBean> eventos = eventoDao.listar();
			
			if (eventos.size() > 0) {
				int totalEventos = eventos.size();
				int index = 1;
				String datas = "[";
			
				for (EventoBean evento : eventos) {
					datas += "{ \"title\": \"" + evento.getDescricao() + "\", \"start\": \"" + evento.getDataEvento() + "\" }";
					
					if (index < totalEventos) {
						datas += ",";
					}
					
					index++;
				}
				
				datas += "]";
				
				response.setStatus(200);
				response.getWriter().write(datas);
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
