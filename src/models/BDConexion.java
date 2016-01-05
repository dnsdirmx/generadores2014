package models;

import java.sql.*;

public class BDConexion {
	private Connection conexion;
	private String host;
	private String bdnombre;
	private String usuario;
	private String pass;

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }
    public void cerrar()
    {
    	if(conexion == null)
    		return;
    	try {
			conexion.close();
			System.out.println(this.getClass().getSimpleName() + ": Se ha cerrado la conexion");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    public BDConexion() {
    	host	 = "jdbc:mysql://localhost/";
    	bdnombre = "generadores";
    	usuario  = "root";
    	pass 	 = "12345";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String BaseDeDatos = host + bdnombre +"?user=" + usuario + "&password=" + pass;
            setConexion(DriverManager.getConnection(BaseDeDatos));
            if (conexion != null) {
                System.out.println(this.getClass().getSimpleName()  + ": Conexion exitosa!");
            } else {
                System.out.println(this.getClass().getSimpleName() + ": Conexion fallida!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet consultar(String sql) {
        ResultSet resultado;
        try {
            Statement sentencia = getConexion().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            resultado = sentencia.executeQuery(sql);
            System.out.println(this.getClass().getSimpleName() + ": Consulta : " + sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return resultado;
    }

    public boolean ejecutar(String sql) {
        try {
            Statement sentencia = getConexion().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            System.out.println("Ejecuta : " + sql);
            sentencia.executeUpdate(sql);
            sentencia.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}