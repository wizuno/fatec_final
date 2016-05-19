package br.sceweb.servico;

import java.sql.DriverManager;

import org.apache.log4j.Logger;

import com.mysql.jdbc.Connection;

public class FabricaDeConexoes {
	Logger logger = Logger.getLogger(FabricaDeConexoes.class);
	public Connection getConnection(){
		String url = "jdbc:mysql://localhost/sceweb";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return (Connection) DriverManager.getConnection(url,"root","");
			}
		catch (Exception e){
			logger.info("SQLException na classe FabricaDeConexoes causa: " + e.getMessage());
			throw new RuntimeException(e); 
		}
	}
}
