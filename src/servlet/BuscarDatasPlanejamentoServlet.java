package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import bean.ProjetoBean;
import bean.ProjetoSeriesBean;
import dao.GanttViewDao;

@WebServlet("/pages/buscarDatasPlanejamento")
public class BuscarDatasPlanejamentoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private GanttViewDao ganttViewDao = new GanttViewDao();
       
    public BuscarDatasPlanejamentoServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		try {
			List<ProjetoBean> projetos = ganttViewDao.listar();
			
			if (projetos.size() > 0) {
				String ganttJson = new Gson().toJson(projetos);
				
				response.setStatus(200);
				response.getWriter().write(ganttJson);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus(500);
			response.getWriter().write(e.getMessage());
		}
		
		
	}

	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		ProjetoSeriesBean seriesBean = new ProjetoSeriesBean();
		seriesBean.setId(Long.parseLong(request.getParameter("serie")));
		seriesBean.setProjetoId(Long.parseLong(request.getParameter("projeto")));
		seriesBean.setDataInicial(request.getParameter("start"));
		seriesBean.setDataFinal(request.getParameter("end"));
		
		ganttViewDao.atualizar(seriesBean);
		
		response.setStatus(200);
		response.getWriter().write("Dados atualizados com sucesso!");
	}

}
