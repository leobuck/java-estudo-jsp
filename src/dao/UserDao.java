package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.UserBean;
import connection.SingleConnection;

public class UserDao {

	private Connection connection;
	
	public UserDao() {
		connection = SingleConnection.getConnection();
	}
	
	public List<UserBean> listar() throws SQLException {
		List<UserBean> lista = new ArrayList<UserBean>();
		String sql = " select * from user2 order by id ";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next()) {
			UserBean usuario = new UserBean();
			usuario.setId(resultSet.getLong("id"));
			usuario.setLogin(resultSet.getString("login"));
			usuario.setSenha(resultSet.getString("senha"));
			
			lista.add(usuario);
		}
		
		return lista;
	}
}
