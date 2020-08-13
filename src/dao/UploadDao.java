package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.UploadBean;
import connection.SingleConnection;

public class UploadDao {

	private Connection connection;
	
	public UploadDao() {
		connection = SingleConnection.getConnection();
	}

	public void inserir(UploadBean uploadBean) {
		try {
			String sql = "insert into upload(arquivo, tipo_arquivo) values(?, ?)";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, uploadBean.getArquivo());
			statement.setString(2, uploadBean.getTipoArquivo());
			statement.execute();
			
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}	
	}
	
	public List<UploadBean> listar() throws SQLException {
		List<UploadBean> lista = new ArrayList<UploadBean>();
		
		String sql = "select * from upload order by id";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		
		while (resultSet.next()) {
			UploadBean uploadBean = new UploadBean();
			uploadBean.setId(resultSet.getLong("id"));
			uploadBean.setArquivo(resultSet.getString("arquivo"));
			uploadBean.setTipoArquivo(resultSet.getString("tipo_arquivo"));
			
			lista.add(uploadBean);
		}
		
		return lista;
	}

	public UploadBean consultar(Long id) throws SQLException {
		UploadBean uploadBean = new UploadBean();
		
		String sql = "select * from upload where id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, id);
		ResultSet resultSet = statement.executeQuery();
		
		while (resultSet.next()) {
			uploadBean.setId(resultSet.getLong("id"));
			uploadBean.setArquivo(resultSet.getString("arquivo"));
			uploadBean.setTipoArquivo(resultSet.getString("tipo_arquivo"));
		}
		
		return uploadBean;
	}
	
}
