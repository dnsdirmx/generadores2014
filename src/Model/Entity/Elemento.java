package Model.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.LinkedList;

import javax.swing.JOptionPane;

public class Elemento {
	private Integer idElemento;
	private String nombre;
	
	public Elemento(Integer idElemento, String nombre)
	{
		this.idElemento = idElemento;
		this.nombre = nombre;
	}
	public Elemento(String nombre)
	{
		this.idElemento = -1;
		this.nombre = nombre;
	}
	public Elemento() {
		this.idElemento = -1;
		this.nombre = null;
	}
	public Integer getIdElemento() {
		return idElemento;
	}
	public void setIdElemento(Integer idPartida) {
		this.idElemento = idPartida;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public static Elemento find(Integer idPartida)
	{
		BDConexion bd = new BDConexion();
		Elemento p = null;
		ResultSet rs = bd.obtieneRegistro("elemento", "idelemento",idPartida.toString());
		try {
			if(rs != null)
				if(rs.next())
				{
					p = new Elemento(rs.getInt("idelemento"), rs.getString("nombre"));
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
	public static LinkedList<Elemento> findAll()
	{
		LinkedList<Elemento> llelementos = new LinkedList<Elemento>();
		BDConexion bd = new BDConexion();
		String[] campos = {"idelemento","nombre"};
		ResultSet rs = bd.consulta("elemento", campos);
		try {
			while(rs.next())
			{
				llelementos.add(new Elemento(rs.getInt("idelemento"),rs.getString("nombre")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bd.cerrar();
		return llelementos;
	}
	public boolean save() {
		boolean state = false;
		BDConexion bd = new BDConexion();
		Hashtable<String, String> contenedor = new Hashtable<String,String>();
		contenedor.put("nombre", "'" + nombre + "'");
		System.out.println("+++++++ : " + idElemento);
		if(this.idElemento == -1)
		{
			if(( idElemento = bd.inserta("elemento", contenedor)) > 0)
				state = true;
		}
		else
		{
			if(bd.actualiza("partida", contenedor, "idelemento", this.idElemento.toString()) > 0)
				state = true;
		}
		return state;
	}
	public boolean destroy() {
		boolean state = false;
		BDConexion bd = new BDConexion();
		bd.eliminar("elemento", "idelemento", idElemento.toString());
		return state;
	}

	public String toString() {
		return "idElemento: " + idElemento + "\nnombre: " + nombre;
	}

}
