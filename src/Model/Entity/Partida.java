/**
 * 
 */
package Model.Entity;
/*
 * Jose Eduardo Hernandez Tapia 
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.LinkedList;

import javax.swing.JOptionPane;

public class Partida{

	private Integer idPartida;
	private String nombre;
	
	public Partida(Integer idPartida, String nombre)
	{
		this.idPartida = idPartida;
		this.nombre = nombre;
	}
	public Partida(String nombre)
	{
		this.idPartida = -1;
		this.nombre = nombre;
	}
	public Partida() {
		this.idPartida = -1;
		this.nombre = null;
	}
	public Integer getIdPartida() {
		return idPartida;
	}
	public void setIdPartida(Integer idPartida) {
		this.idPartida = idPartida;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	/**
	 * Obtiene lapartida especificada
	 * @param idPartida identificador de la partida
	 * @return partida o null
	 */
	public static Partida find(Integer idPartida)
	{
		BDConexion bd = new BDConexion();
		Partida p = null;
		ResultSet rs = bd.obtieneRegistro("partida", "idpartida",idPartida.toString());
		try {
			if(rs != null)
				if(rs.next())
				{
					p = new Partida(rs.getInt("idpartida"), rs.getString("nombre"));
				}
				else
					JOptionPane.showMessageDialog(null, "Op ");
		} catch (SQLException e) {
			p = null;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bd.cerrar();
		return p;
	}
	/**
	 * Obtiene todas las partidas de la base de datos
	 * @return lista con las partidas
	 */
	public static LinkedList<Partida> findAll()
	{
		LinkedList<Partida> llpartidas = new LinkedList<Partida>();
		BDConexion bd = new BDConexion();
		String[] campos = {"idpartida","nombre"};
		ResultSet rs = bd.consulta("partida", campos);
		try {
			while(rs.next())
			{
				llpartidas.add(new Partida(rs.getInt("idpartida"),rs.getString("nombre")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bd.cerrar();
		return llpartidas;
	}
	/**
	 * Inserta o actualiza la partida
	 * @return verdadero si se guardo falso en caso contrario
	 */
	public boolean save() {
		boolean state = false;
		BDConexion bd = new BDConexion();
		Hashtable<String, String> contenedor = new Hashtable<String,String>();
		contenedor.put("nombre", "'" + nombre + "'");
		System.out.println("+++++++ : " + idPartida);
		if(this.idPartida == -1)
		{
			if(( idPartida = bd.inserta("partida", contenedor)) > 0)
				state = true;
		}
		else
		{
			if(bd.actualiza("partida", contenedor, "idpartida", this.idPartida.toString()) > 0)
				state = true;
		}
		return state;
	}
	/**
	 * elimina la partida de la base de datos 
	 * @return verdadero si se elimino correctamente
	 */
	public boolean destroy() {
		boolean state = false;
		BDConexion bd = new BDConexion();
		bd.eliminar("partida", "idpartida", idPartida.toString());
		return state;
	}

	public String toString() {
		return "idPartida: " + idPartida + "\nnombre: " + nombre;
	}

}
