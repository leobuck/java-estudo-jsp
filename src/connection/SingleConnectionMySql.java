package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnectionMySql {

	private static String banco = "jdbc:mysql://localhost:3306/teste?autoReconnect=true";
	private static String user = "root";
	private static String password = "";
	private static Connection connection = null;
	
	static {
		conectar();
	}
	
	public SingleConnectionMySql() {
		conectar();
	}
	
	private static void conectar() {
		try {
			
			if (connection == null) {
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection(banco, user, password);
				connection.setAutoCommit(false);				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao conectar com banco de dados");
		}
	}
	
	public static Connection getConnection() {
		return connection;
	}
	
}
