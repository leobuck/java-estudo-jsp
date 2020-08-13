package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.ProjetoBean;
import bean.ProjetoSeriesBean;
import connection.SingleConnection;

public class GanttViewDao {

	private Connection connection;
	
	public GanttViewDao() {
		connection = SingleConnection.getConnection();
	}
	
	public List<ProjetoBean> listar() throws SQLException {
		List<ProjetoBean> projetoLista = new ArrayList<ProjetoBean>();
		
		String sql = "select * from projeto order by id";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		
		while (resultSet.next()) {
			ProjetoBean projetoBean = new ProjetoBean();
			projetoBean.setId(resultSet.getLong("id"));
			projetoBean.setNome(resultSet.getString("nome"));
			
			String sqlSeries = "select * from projeto_series where projeto_id = ? order by id";
			PreparedStatement statementSeries = connection.prepareStatement(sqlSeries);
			statementSeries.setLong(1, projetoBean.getId());
			ResultSet resultSetSeries = statementSeries.executeQuery();
			
			List<ProjetoSeriesBean> projetoSeriesLista = new ArrayList<ProjetoSeriesBean>(); 
			while(resultSetSeries.next()) {
				ProjetoSeriesBean projetoSeriesBean = new ProjetoSeriesBean();
				projetoSeriesBean.setId(resultSetSeries.getLong("id"));
				projetoSeriesBean.setNome(resultSetSeries.getString("nome"));
				projetoSeriesBean.setDataInicial(resultSetSeries.getString("data_inicial"));
				projetoSeriesBean.setDataFinal(resultSetSeries.getString("data_final"));
				projetoSeriesBean.setProjetoId(resultSetSeries.getLong("projeto_id"));
				
				projetoSeriesLista.add(projetoSeriesBean);
			}
			
			projetoBean.setSeries(projetoSeriesLista);
			projetoLista.add(projetoBean);
		}
		
		return projetoLista;
	}
	
	public void atualizar(ProjetoSeriesBean seriesBean) {
		String sql = " update projeto_series set data_inicial = ?, data_final = ? where id = ? and projeto_id = ? ";
		
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, seriesBean.getDataInicial());
			statement.setString(2, seriesBean.getDataFinal());
			statement.setLong(3, seriesBean.getId());
			statement.setLong(4, seriesBean.getProjetoId());
			statement.executeUpdate();
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
