package Model.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.LinkedList;


/**
 * 
 * @author luiiis
 *
 */
public class Tipo {

	String idtipo;
	String tipo;

	/**
	 * 
	 * @param tipo
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * 
	 * @return
	 */
	public String getTipo() {
		return this.tipo;
	}

	/**
	 * 
	 * @param idtipo
	 */
	public void setIdtipo(String idtipo) {
		this.idtipo = idtipo;
	}

	/**
	 * 
	 * @return
	 */
	public String getIdtipo() {
		return this.idtipo;
	}
	
	public boolean delete()
	{
		boolean estado = false;
		BDConexion bd = new BDConexion();
		if(bd.eliminar("tipos", "idtipo", "\"" + this.idtipo + "\"") > 0 )
			estado = true;
		else
			estado = false;
		return estado;
	}
	public boolean save()
	{
		boolean estado = false;
		BDConexion bd = new BDConexion();
		String sql = "";
		Hashtable<String,String> contenedor = new Hashtable<String,String>();
		contenedor.put("tipo", "\"" + this.tipo + "\"");
		if(this.idtipo == null)
		{
			
			if(bd.inserta("tipos", contenedor) == 1)
				estado = true;
			else
				estado = false;
		}
		else
		{
			if(bd.actualiza("tipos", contenedor, "idtipo", this.idtipo) == 1)
				estado = true;
			else
				estado = false;
		}
		return estado;
	}
	public static Tipo find(String strTipo)
	{
		Tipo tipo = null;
		String sql = "Select * from tipos where tipo = \"" + strTipo + "\"";
		BDConexion bd = new BDConexion();
		ResultSet resultado = bd.consultar(sql);
		try {
			while(resultado.next())
			{
				tipo = new Tipo();
				tipo.setIdtipo(resultado.getString(1));
				tipo.setTipo(resultado.getString(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tipo;
	}
	public static LinkedList<Tipo> findAll()
	{
		String sql = "SELECT idtipo,tipo FROM tipos";
		
		BDConexion bd = new BDConexion();
		ResultSet rs = bd.consultar(sql);
		LinkedList<Tipo> tipos = new LinkedList<Tipo>();
		try {
			while(rs.next())
			{
				Tipo tipo = new Tipo();
				tipo.setIdtipo(rs.getString("idtipo"));
				tipo.setTipo(rs.getString("tipo"));
				tipos.add(tipo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bd.cerrar();
		return tipos;
	}
}
