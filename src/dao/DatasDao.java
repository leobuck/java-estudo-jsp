package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bean.DatasBean;
import connection.SingleConnection;

public class DatasDao {

	private Connection connection;
	
	public DatasDao() {
		connection = SingleConnection.getConnection();
	}
	
	public void gravar(DatasBean datasBean) {
		try {
			String sql = "insert into datas (datafinal) values (?)";
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, datasBean.getDataFinal());
			preparedStatement.execute();
			
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
}
