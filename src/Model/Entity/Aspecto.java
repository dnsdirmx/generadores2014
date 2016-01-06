package Model.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class Aspecto{
	Integer idAspecto;
	Concepto concepto;
	String clave;
	String unidad;
	String tipo;
	Double costo;
	String descripcion;
	String clave_publica;
	String descripcion_completa;
	private Integer idConcepto;
	
	public Integer getIdAspecto() {
		return idAspecto;
	}

	public void setIdAspecto(Integer idAspecto) {
		this.idAspecto = idAspecto;
	}

	public Concepto getConcepto() {
		this.concepto = Model.Entity.Concepto.find(idConcepto);
		return this.concepto;
	}

	public void setConcepto(Concepto concepto) {
		this.concepto = concepto;
	}
	public Integer getIdConcepto()
	{
		
		return this.idConcepto;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getClave_publica() {
		return clave_publica;
	}

	public void setClave_publica(String clave_publica) {
		this.clave_publica = clave_publica;
	}

	public String getDescripcion_completa() {
		return descripcion_completa;
	}
	public void setDescripcion_completa(String descripcion_completa) {
		this.descripcion_completa = descripcion_completa;
	}



	public boolean save() {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean destroy() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public static Aspecto find(Integer idAspecto)
	{
		Aspecto aspecto = null;
		
		
		
		return aspecto;
	}
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
				aspecto.setClave_publica(rs.getString("clave_publica"));
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

	public void setIdConcepto(Integer idConcepto) {
		this.idConcepto = idConcepto;
		
	}

}
