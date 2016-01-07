package Model.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
/*
 * Jose Eduardo Hernandez Tapia 
 */
public class Aspecto{
	private Integer idAspecto;
	private Concepto concepto;
	private Integer idConcepto;
	private String clave;
	private String unidad;
	private String tipo;
	private Double costo;
	private String descripcion;
	private String clave_privada;
	private String descripcion_completa;

	
	

	
	
	/**
	 * @return the idAspecto
	 */
	public Integer getIdAspecto() {
		return idAspecto;
	}






	/**
	 * @param idAspecto the idAspecto to set
	 */
	public void setIdAspecto(Integer idAspecto) {
		this.idAspecto = idAspecto;
	}






	/**
	 * @return the concepto
	 */
	public Concepto getConcepto() {
		return concepto;
	}






	/**
	 * @param concepto the concepto to set
	 */
	public void setConcepto(Concepto concepto) {
		this.concepto = concepto;
	}






	/**
	 * @return the idConcepto
	 */
	public Integer getIdConcepto() {
		return idConcepto;
	}






	/**
	 * @param idConcepto the idConcepto to set
	 */
	public void setIdConcepto(Integer idConcepto) {
		this.idConcepto = idConcepto;
	}






	/**
	 * @return the clave
	 */
	public String getClave() {
		return clave;
	}






	/**
	 * @param clave the clave to set
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}






	/**
	 * @return the unidad
	 */
	public String getUnidad() {
		return unidad;
	}






	/**
	 * @param unidad the unidad to set
	 */
	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}






	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}






	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}






	/**
	 * @return the costo
	 */
	public Double getCosto() {
		return costo;
	}






	/**
	 * @param costo the costo to set
	 */
	public void setCosto(Double costo) {
		this.costo = costo;
	}






	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}






	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}






	/**
	 * @return the clave_privada
	 */
	public String getClave_privada() {
		return clave_privada;
	}






	/**
	 * @param clave_privada the clave_privada to set
	 */
	public void setClave_privada(String clave_privada) {
		this.clave_privada = clave_privada;
	}






	/**
	 * @return the descripcion_completa
	 */
	public String getDescripcion_completa() {
		return descripcion_completa;
	}






	/**
	 * @param descripcion_completa the descripcion_completa to set
	 */
	public void setDescripcion_completa(String descripcion_completa) {
		this.descripcion_completa = descripcion_completa;
	}






	/**
	 * Devuelve todos los aspectos en la base de datos
	 * @return Lista con los aspectos
	 */
	public static LinkedList<Aspecto> findAll() {
		LinkedList<Aspecto> llAspectos = new LinkedList<Aspecto>();
		BDConexion bd = new BDConexion();
		String [] campos = {"*"};
		ResultSet rs = bd.consulta("aspecto", campos);
		
		try {
			while(rs.next())
			{
				Aspecto aspecto = new Aspecto();
				aspecto.setIdAspecto(rs.getInt("idaspecto"));
				aspecto.setIdConcepto(rs.getInt("idConcepto"));
				aspecto.setClave(rs.getString("clave"));
				aspecto.setUnidad(rs.getString("unidad"));
				aspecto.setTipo(rs.getString("tipo"));
				aspecto.setCosto(rs.getDouble("costo"));
				aspecto.setDescripcion(rs.getString("descripcion"));
				aspecto.setClave_privada(rs.getString("clave_publica"));
				aspecto.setDescripcion_completa(rs.getString("descripcion_completa"));
				llAspectos.add(aspecto);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bd.cerrar();
		return llAspectos;
	}

	

}
