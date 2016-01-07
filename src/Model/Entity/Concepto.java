package Model.Entity;
/*
 * Jose Eduardo Hernandez Tapia 
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.LinkedList;


public class Concepto {

	private Integer idConcepto;
	private Model.Entity.Partida partida;
	private String nombre;
	private Integer idPartida;
	public Concepto()
	{
		this.idConcepto = -1;
		this.nombre = null;
		this.partida = null;
	}
	public Concepto(String nombre)
	{
		this.idConcepto = -1;
		this.nombre = nombre;
	}
	public Concepto(Integer idConcepto,Integer idPartida, String nombre)
	{
		this.idConcepto = idConcepto;
		this.idPartida = idPartida;
		//this.partida = Partida.find(idPartida);
		this.nombre = nombre;
	}
	
	public Integer getIdConcepto() {
		return idConcepto;
	}

	public void setIdConcepto(Integer idConcepto) {
		this.idConcepto = idConcepto;
	}

	public Model.Entity.Partida getPartida() {
		this.partida = Partida.find(this.idPartida); 
		return partida;
	}

	public void setPartida(Model.Entity.Partida partida) {
		this.partida = partida;
	}
	public Integer getIdPartida()
	{
		
		return this.idPartida;
	}
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	/**
	 * Obtiene un concepto de la nbase de datos
	 * @param idConcepto identificador de la fila
	 * @return	el concepto si lo encontro o nulo en caso contrario
	 */
	public static Concepto find(Integer idConcepto)
	{
		Concepto c = null;
		BDConexion bd = new BDConexion();
		ResultSet rs = bd.obtieneRegistro("concepto", "idconcepto",idConcepto.toString());
		try {
			if(rs.next())
				c = new Concepto(rs.getInt("idconcepto"),rs.getInt("idPartida"),rs.getString("nombre"));
			
		} catch (SQLException e) {
			c = null;
			e.printStackTrace();
		}
		bd.cerrar();
		return c;
	}
	/**
	 * Devuelve todos los conceptos en la base de datos
	 * @return lista con los conceptos
	 */
	public static LinkedList<Concepto> findAll()
	{
		LinkedList<Concepto> llconceptos = new LinkedList<Concepto>();
		BDConexion bd = new BDConexion();
		String[] campos = {"idconcepto","idpartida","nombre"};
		ResultSet rs = bd.consulta("concepto", campos);
		try {
			while(rs.next())
			{
				llconceptos.add(new Concepto(rs.getInt("idconcepto"), rs.getInt("idpartida"),rs.getString("nombre")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		bd.cerrar();
		return llconceptos;
	}
	/**
	 * Guarda el conceptto actual
	 * @return verdadero si se inserto o actualizo falso en caso contrario
	 */
	public boolean save() {
		boolean state = false;
		BDConexion bd = new BDConexion();
		
		Hashtable<String, String> contenedor = new Hashtable<String, String>();
		contenedor.put("idpartida",partida.getIdPartida().toString());
		contenedor.put("nombre", "'" + nombre + "'");
		if(idConcepto == -1)
		{
			
			if((idConcepto = bd.inserta("concepto", contenedor)) > 0)
				state = true;
		}
		else
		{
			if(bd.actualiza("concepto", contenedor, "idconcepto", idConcepto.toString()) > 0)
				state = true;
		}
		return state;
	}
	/**
	 * Elimina el concepto actual de la base de datos
	 * @return verdadero si se elimino correctamente ffalso en caso contrario
	 */
	public boolean destroy() {
		boolean state = false;
		BDConexion bd = new BDConexion();
		if(bd.eliminar("concepto", "idconcepto", idConcepto.toString()) > 0)
			state = true;
		return state;
	}
	/**
	 * 
	 */
	public String toString() {
		
		return "idConcepto: " + idConcepto + "\nidPartida: " + partida.getIdPartida() + "\nnombre: " + nombre ;
	}


}

