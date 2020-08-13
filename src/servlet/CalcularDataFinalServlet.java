package servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.DatasBean;
import dao.DatasDao;

@WebServlet("/pages/calcularDataFinal")
public class CalcularDataFinalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private DatasDao datasDao = new DatasDao();
	
    public CalcularDataFinalServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		int horaDia = 8;
		Date dataCalculada = null;
		Double totalDeDias = 0.0;
		
		String data = request.getParameter("data");
		int tempo = Integer.parseInt(request.getParameter("tempo"));
		
		try {
			if (tempo <= horaDia) {
				Date dateInformada = new SimpleDateFormat("dd/MM/yyyy").parse(data);
				Calendar calendar = Calendar.getInstance();
				
				calendar.setTime(dateInformada);
				calendar.add(Calendar.DATE, 1);
				
				dataCalculada = calendar.getTime();
				totalDeDias = 1.0;
			
			} else {				
				totalDeDias = (double) (tempo / horaDia);
				
				if (totalDeDias <= 1) {
					dataCalculada = new SimpleDateFormat("dd/MM/yyyy").parse(data);
					
				} else {
					Date dateInformada = new SimpleDateFormat("dd/MM/yyyy").parse(data);
					Calendar calendar = Calendar.getInstance();
					
					calendar.setTime(dateInformada);
					calendar.add(Calendar.DATE, totalDeDias.intValue());
					
					dataCalculada = calendar.getTime();
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		DatasBean datasBean = new DatasBean();
		datasBean.setDataFinal(new SimpleDateFormat("dd/MM/yyyy").format(dataCalculada));
		
		datasDao.gravar(datasBean);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/datas.jsp");
		request.setAttribute("dataFinal", datasBean.getDataFinal());
		request.setAttribute("dias", totalDeDias);
		dispatcher.forward(request, response);
	}

}
