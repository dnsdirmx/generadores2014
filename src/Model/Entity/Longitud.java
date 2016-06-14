package Model.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.LinkedList;

public class Longitud{
	private Integer idLongitud;
	private String nombre;
	
	public Integer getIdLongitud() {
		return idLongitud;
	}
	public void setIdLongitud(Integer idLongitud) {
		this.idLongitud = idLongitud;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void save()
	{
		BDConexion bd = new BDConexion();
		Hashtable<String, String> contenedor = new Hashtable<String,String>();
		contenedor.put("nombre_longitud","\"" + this.nombre + "\"");
		this.idLongitud = bd.inserta("longitudes", contenedor);
	}
	public static final LinkedList<Longitud> findAll()
	{
		LinkedList result = new LinkedList<Longitud>();
		BDConexion bd = new BDConexion();
		String[] campos = new String[1];
		campos[1] = "*";
		ResultSet resultado = bd.consulta("longitudes", campos);
		try {
			while(resultado.next())
			{
				Longitud longitud = new Longitud();
				longitud.setIdLongitud(Integer.valueOf(resultado.getString("idlongitud")));
				longitud.setNombre(resultado.getString("nombre"));
				result.add(longitud);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	public static void main(String[] args) {
		Longitud n = new Longitud();
		n.setNombre("Holi");
		n.save();
	}

}
