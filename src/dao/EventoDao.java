package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.EventoBean;
import connection.SingleConnection;

public class EventoDao {

	private Connection connection;
	
	public EventoDao() {
		connection = SingleConnection.getConnection();
	}
	
	public List<EventoBean> listar() throws SQLException {
		List<EventoBean> lista = new ArrayList<EventoBean>();
		
		String sql = "select * from evento order by id";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		
		while (resultSet.next()) {
			EventoBean eventoBean = new EventoBean();
			eventoBean.setId(resultSet.getLong("id"));
			eventoBean.setDataEvento(resultSet.getString("data_evento"));
			eventoBean.setDescricao(resultSet.getString("descricao"));
			
			lista.add(eventoBean);
		}
		
		return lista;
	}
	
}
