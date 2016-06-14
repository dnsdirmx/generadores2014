package Model.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.LinkedList;

public class DetalleLongitud {
	private Integer id;
	private Integer idLongitud;
	private Integer idElemento;
	private Integer idFrente;
	
	private Longitud longitud;
	private Elemento elemento;
	private Frente frente;
	
	private Double seccion;
	private Double separacion;
	private Double nPiezas;
	private Double cantidad;
	private Double dLongitud;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIdLongitud() {
		return idLongitud;
	}
	public void setIdLongitud(Integer idLongitud) {
		this.idLongitud = idLongitud;
	}
	public Integer getIdElemento() {
		return idElemento;
	}
	public void setIdElemento(Integer idElemento) {
		this.idElemento = idElemento;
	}
	public Integer getIdFrente() {
		return idFrente;
	}
	public void setIdFrente(Integer idFrente) {
		this.idFrente = idFrente;
	}
	public Longitud getLongitud() {
		return longitud;
	}
	public void setLongitud(Longitud longitud) {
		this.longitud = longitud;
	}
	public Elemento getElemento() {
		return elemento;
	}
	public void setElemento(Elemento elemento) {
		this.elemento = elemento;
	}
	public Frente getFrente() {
		return frente;
	}
	public void setFrente(Frente frente) {
		this.frente = frente;
	}
	public Double getSeccion() {
		return seccion;
	}
	public void setSeccion(Double seccion) {
		this.seccion = seccion;
	}
	public Double getSeparacion() {
		return separacion;
	}
	public void setSeparacion(Double separacion) {
		this.separacion = separacion;
	}
	public Double getnPiezas() {
		return nPiezas;
	}
	public void setnPiezas(Double nPiezas) {
		this.nPiezas = nPiezas;
	}
	public Double getCantidad() {
		return cantidad;
	}
	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}
	public Double getdLongitud() {
		return dLongitud;
	}
	public void setdLongitud(Double dLongitud) {
		this.dLongitud = dLongitud;
	}
	
	public static void main(String[] args) {
		DetalleLongitud dt = new DetalleLongitud();
		dt.setIdElemento(1);
		dt.setIdLongitud(1);
		dt.setIdFrente(2);
		dt.setCantidad(100.0);
		dt.save();
	}
	
	public void save()
	{
		BDConexion bd = new BDConexion();
		
		Hashtable<String, String> contenedor = new Hashtable<String, String>();
		contenedor.put("elemento_idelemento", "" + this.idElemento + "");
		contenedor.put("longitudes_idlongitudes", "" + this.idLongitud + "");
		contenedor.put("frente_idfrente", "" + this.idFrente + "");
		contenedor.put("seccion", "\"" + this.seccion+ "\"");
		contenedor.put("separacion", "\"" + this.separacion + "\"");
		contenedor.put("piezas", "\"" + this.nPiezas + "\"");
		contenedor.put("cantidad", "\"" + this.cantidad + "\"");
		contenedor.put("longitud", "\"" + this.dLongitud + "\"");
		
		this.id = bd.inserta("detalle_longitudes", contenedor );
	}
	public static final LinkedList<DetalleLongitud> findAll()
	{
		LinkedList result = new LinkedList<DetalleLongitud>();
		BDConexion bd = new BDConexion();
		ResultSet rs = bd.consultar("SELECT * FROM detalle_longitudes");
		
		try {
			while(rs.next())
			{
				DetalleLongitud dt = new DetalleLongitud();
				dt.setId(rs.getInt("id_detalle_longitudes"));
				dt.setIdElemento(rs.getInt("elemento_idelemento"));
				dt.setIdLongitud(rs.getInt("longitudes_idlongitudes"));
				dt.setIdFrente(rs.getInt("frente_idfrente"));
				dt.setSeccion(Double.parseDouble(rs.getString("seccion")));
				dt.setSeparacion(Double.parseDouble(rs.getString("separacion")));
				dt.setSeparacion(Double.parseDouble(rs.getString("piezas")));
				dt.setCantidad(Double.parseDouble(rs.getString("cantidad")));
				dt.setdLongitud(Double.parseDouble(rs.getString("longitud")));
				result.add(dt);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
