package Model.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;


public class Frente {

	private int idfrente;
	private int idproyecto;
	private String identificador;
	private String ubicacion;

	public Frente()
	{
		idfrente = -1;
	}
	/**
	 * 
	 * @param idfrente
	 */
	public void setIdfrente(int idfrente) {
		this.idfrente = idfrente;
	}

	/**
	 * 
	 * @return
	 */
	public int getIdfrente() {
		return this.idfrente;
	}

	/**
	 * 
	 * @param idproyecto
	 */
	public void setIdproyecto(int idproyecto) {
		this.idproyecto = idproyecto;
	}

	/**
	 * 
	 * @return
	 */
	public int getIdproyecto() {
		return this.idproyecto;
	}

	/**
	 * 
	 * @param identificador
	 */
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	/**
	 * 
	 * @return
	 */
	public String getIndentificador() {
		return this.identificador;
	}

	/**
	 * 
	 * @param ubicacion
	 */
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	/**
	 * 
	 * @return
	 */
	public String getUbicacion() {
		return this.ubicacion;
	}
	public static LinkedList<Frente> findAll()
	{
		LinkedList<Frente> Lfrentes = new LinkedList<Frente>();
		String sql = " SELECT idfrente, idproyecto, identificador, ubicacion FROM frente";
		BDConexion bd = new BDConexion();
		ResultSet rs = bd.consultar(sql);
		try {
			while (rs.next()) {	
				Frente frente = new Frente();
				frente.setIdfrente(rs.getInt("idfrente"));
				frente.setIdproyecto(rs.getInt("idproyecto"));
				frente.setIdentificador(rs.getString("identificador"));
				frente.setUbicacion(rs.getString("ubicacion"));
				Lfrentes.add(frente);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		bd.cerrar();
		return Lfrentes;
	}
	public boolean save()
	{
		boolean estado = false;
		String sql = "";
		BDConexion bd = new BDConexion();
		if(idfrente == -1)
		{
			sql = "INSERT INTO `generadores`.`frente` (`idfrente`,`idproyecto`,`identificador`,`ubicacion`) VALUES("+ this.idproyecto +",'"+ this.identificador +"','"+ this.ubicacion +"')";
			estado = bd.ejecutar(sql);
			
			sql = " SELECT LAST_INSERT_ID() as id";
			ResultSet rs = bd.consultar(sql);
			try {
				rs.next();
				this.idfrente = rs.getInt("id");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			sql = "UPDATE `generadores`.`frente` SET `idproyecto` = "+ idproyecto  + ",`identificador` = '"+ identificador +"',`ubicacion` = '"+ ubicacion +"' WHERE `idfrente` = " + idfrente;
			estado = bd.ejecutar(sql);
		}
		return estado;
	}
}