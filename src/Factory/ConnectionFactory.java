package Factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;
import javax.swing.JOptionPane;

import com.mchange.v2.c3p0.*;

public class ConnectionFactory {

//private DataSource datasource;
	


	public ConnectionFactory() {
		
		/*var pooledDataSource = new ComboPooledDataSource();
		pooledDataSource.setJdbcUrl("jdbc:mysql://localhost/hotel?useTimeZone=true&serverTimeZone=UTC");
		pooledDataSource.setUser("root");
		pooledDataSource.setPassword("jmtommmm");
		pooledDataSource.setMaxPoolSize(10);
		
		
		this.datasource = pooledDataSource;*/
	
		
		
	}
	
	public Connection recuperarConexion() {
		
	try {
			
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/hotel?useTimeZone=true&serverTimeZone=UTC",
					"root", "jmtommmm");
			
			return conexion;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	
	
	}
	
	

	
}
