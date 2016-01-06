package Model.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
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
