package servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.UserBean;
import dao.UserDao;
import services.RelatorioService;

@WebServlet("/pages/DownloadFileServlet")
public class DownloadFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private RelatorioService relatorioService = new RelatorioService();
	
	private UserDao userDao = new UserDao();
	
    public DownloadFileServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		ServletContext context = request.getServletContext();
		
		String tipoExportar = request.getParameter("tipoExportar");
		
		try {
			List<UserBean> usuarios = userDao.listar();
						
			try {
				String fileUrl = relatorioService.gerarRelatorio(usuarios, new HashMap<String, Object>(), "rel_usuarios", "rel_usuarios", 
						context, tipoExportar);
				
				File downloadFile = new File(fileUrl);
				FileInputStream inputStream = new FileInputStream(downloadFile);
				
				String mimeType = context.getMimeType(fileUrl);
				if (mimeType == null) {
					mimeType = "application/octet-stream";
				}
				
				response.setContentType(mimeType);
				response.setContentLength((int) downloadFile.length());
				
				String headerKey = "Content-Disposition";
				String headerValue = String.format("attachment;filename=\"%s\"", downloadFile.getName());
				
				response.setHeader(headerKey, headerValue);
				
				OutputStream outputStream = response.getOutputStream();
				byte[] buffer = new byte[4096];
				int byteReader = -1;
				
				while ((byteReader = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, byteReader);
				}
				
				inputStream.close();
				outputStream.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
	}

}
