package Model.Entity;

import java.sql.*;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JOptionPane;

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
    
    
    
    //metodos antiguos
	public int actualiza(String tabla,Hashtable<String,String> actualizados, String id, String idvalor)
	{
		int  modificados = 0;
		String cadCampos = "";
		
		boolean first = true;
		Enumeration<String> claves = actualizados.keys();
		Enumeration<String> valores = actualizados.elements();
		while (claves.hasMoreElements())
		{
			
			if(first)
			{
				cadCampos = (String) claves.nextElement() + "=" + (String) valores.nextElement(); ; 
				first = false;
			}
			else
			{
				cadCampos = cadCampos + "," + (String) claves.nextElement()+ "=" + (String) valores.nextElement(); ; 
			}
		}
		System.out.println(cadCampos);
		String sql = "UPDATE " + tabla + " SET " + cadCampos + " WHERE " + id + "=" + idvalor;
		System.out.println(sql);
		
		
		try {
			Statement st = (Statement) conexion.createStatement(); 
			modificados = st.executeUpdate(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null,"" + e.getMessage());
		}
		return modificados;
	}
	/**
	 * Elimina un registro de la tabla especificada
	 *
	 * @param tabla  Cadena con el nombre de la tabla
	 * @param id   Cadena con el nombre de la columna identificadora
	 * @param idValor  Cadena con el valor identificador de la fila que se eliminara
	 * @return      devuelve mayor que 0 si se a eliminado el registro
	 */
	public int eliminar(String tabla, String id, String idValor)
	{
		int modificado = 0;
		String sql = "DELETE FROM " + tabla + " WHERE " + id + "=" + idValor;
		System.out.println(sql);
		try {
			Statement st = (Statement) conexion.createStatement(); 
			modificado = st.executeUpdate(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null,"" + e.getMessage());
		}
		return modificado;
	}
	public ResultSet obtieneRegistro(String tabla, String id, String idValor)
	{
		ResultSet rs = null;
		String sql = "Select * from " + tabla + " where " + id + "=" + idValor;
		System.out.println(sql);
		Connection con;
		try {
			Statement st = (Statement) conexion.createStatement();
			rs = st.executeQuery(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs;
	}
	public ResultSet consulta(String tabla, String [] campos)
	{
		ResultSet rs = null;
		Statement st = null;
		String cadCampos = "";
		boolean first = true;
		for(String e : campos)
		{
			if(first)
			{
				cadCampos = e;
				first = false;
			}
			else
			{
				cadCampos = cadCampos + "," + e;
			}
		}
		try {
			String sql = "select " + cadCampos + " from " + tabla;
			System.out.println(sql);
			st = conexion.createStatement();
			rs = st.executeQuery(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	public Integer ultimoId()
	{
		String sql = "select last_insert_id()";
		Connection con;
		Integer ultimo = null;
		try {
			System.out.println(sql);
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			ultimo = rs.getInt(0);
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ultimo;
		
	}
	public int inserta(String tabla, Hashtable<String,String> contenedor)
	{
		
		int modificados = 0;
		String cadCampos = "";
		String cadValores = "";
		boolean first = true;
		Enumeration<String> claves = contenedor.keys();
		while (claves.hasMoreElements())
		{
			
			if(first)
			{
				cadCampos = (String) claves.nextElement(); 
				first = false;
			}
			else
			{
				cadCampos = cadCampos + "," + (String) claves.nextElement(); 
			}
		}
		Enumeration<String> valores = contenedor.elements();
		first = true;
		while (valores.hasMoreElements())
		{
			
			if(first)
			{
				cadValores = (String) valores.nextElement(); 
				first = false;
			}
			else
			{
				cadValores = cadValores + "," + (String) valores.nextElement(); 
			}
		}
		String sql = "INSERT INTO " + tabla + "("+ cadCampos +") values("+ cadValores +")";
		System.out.println(sql);
		try {
			Statement st = (Statement) conexion.createStatement(); 
			modificados = st.executeUpdate(sql);
			ResultSet rs = st.executeQuery("select last_insert_id()");
			while(rs.next())
			{
				modificados = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null,"" + e.getMessage());
		}
		System.out.println("Id insertado + "+ modificados);
		return modificados;
	}

}