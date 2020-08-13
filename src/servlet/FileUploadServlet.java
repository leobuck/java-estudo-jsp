package servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.codec.binary.Base64;

import bean.UploadBean;
import dao.UploadDao;

@WebServlet("/pages/fileUpload")
public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UploadDao uploadDao = new UploadDao();
       
    public FileUploadServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		String acao = request.getParameter("acao");
		
		if (acao.equalsIgnoreCase("carregar")) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("upload.jsp");
			try {
				request.setAttribute("arquivos", uploadDao.listar());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dispatcher.forward(request, response);
		} else if (acao.equalsIgnoreCase("download")) {
			String id = request.getParameter("id");
			try {
				UploadBean arquivo = uploadDao.consultar(Long.parseLong(id));
				if (arquivo != null) {
					response.setHeader("Content-Disposition", "attachment;filename=arquivo_" + arquivo.getId() + "." + arquivo.getTipoArquivo());
					
					String arquivoString = arquivo.getArquivo().split(",")[1];
					
					byte[] arquivoByte = new Base64().decode(arquivoString); 
					
					InputStream is = new ByteArrayInputStream(arquivoByte);
					
					int read = 0;
					byte[] bytes = new byte[1024];
					OutputStream os = response.getOutputStream();
					
					while ((read = is.read(bytes)) != -1) {
						os.write(bytes, 0, read);
					}
					
					os.flush();
					os.close();
				}
			} catch (NumberFormatException | SQLException e) {
				e.printStackTrace();
			}
		}		
		
	}

	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		try {
			String fileUpload = request.getParameter("fileUpload");
			
			String tipoArquivo = fileUpload.split(",")[0].split(";")[0].split("/")[1];
			
			UploadBean uploadBean = new UploadBean();
			uploadBean.setArquivo(fileUpload);
			uploadBean.setTipoArquivo(tipoArquivo);
			
			uploadDao.inserir(uploadBean);
			
			response.setStatus(200);
			response.getWriter().write("Upload realizado com sucesso!");
		} catch (Exception e) {		
			e.printStackTrace();
			response.setStatus(500);
			response.getWriter().write("Erro ao realizar upload: " + e.getMessage());			
		}
		
	}

}
