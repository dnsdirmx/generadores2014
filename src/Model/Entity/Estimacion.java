package Model.Entity;
/*
 * Jose Eduardo Hernandez Tapia 
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class Estimacion {

	Integer idestimacion;
	Integer idfrente;
	Integer idConsultor;
	Float pocentaje;
	String fecha;
	String tipo;
	Integer nestimacion;

	public Estimacion()
	{
		idestimacion = -1;
	}
	/**
	 * 
	 * @param idestimacion
	 */
	public void setIdestimacion(Integer idestimacion) {
		this.idestimacion = idestimacion;
	}

	/**
	 * 
	 * @return
	 */
	public Integer getIdestimacion() {
		return this.idestimacion;
	}

	/**
	 * 
	 * @param idfrente
	 */
	public void setIdfrente(Integer idfrente) {
		this.idfrente = idfrente;
	}

	/**
	 * 
	 * @return
	 */
	public Integer getIdfrente() {
		return this.idfrente;
	}

	/**
	 * 
	 * @param idConsultor
	 */
	public void setIdConsultor(Integer idConsultor) {
		this.idConsultor = idConsultor;
	}

	/**
	 * 
	 * @return
	 */
	public Integer getIdconsultor() {
		return this.idConsultor;
	}

	/**
	 * 
	 * @param porcentaje
	 */
	public void setPorcentaje(Float porcentaje) {
		this.pocentaje = porcentaje;
	}

	/**
	 * 
	 * @return
	 */
	public Float getPorcentaje() {
		return this.pocentaje;
	}

	/**
	 * 
	 * @param fecha
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * 
	 * @return
	 */
	public String getFecha() {
		return this.fecha;
	}

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
	 * @param nestimacion
	 */
	public void setNestimacion(Integer nestimacion) {
		this.nestimacion = nestimacion;
	}

	/**
	 * 
	 * @return
	 */
	public Integer getNestimacion() {
		return this.nestimacion;
	}
	/**
	 * Inserta o actualiza la estimacion en la base de datos
	 * @return	verdadero si se guardo falso en caso contrario
	 */
	public boolean save()
	{
		boolean estado = false;
		String sql;
		BDConexion bd = new BDConexion();
		if(idestimacion == -1)
		{
			sql = "INSERT INTO `generadores`.`control_estimacion` (`idfrente`,`idConsultor`,`pocentaje`,`fecha`,`tipo`,`nestimacion`) VALUES "
					+ "("+ this.idfrente +", "+ this.idConsultor +","+ this.pocentaje +",'"+ fecha +"','" + tipo + "'," + this.nestimacion + ")";
			estado = bd.ejecutar(sql);
			if(estado)
			{
				sql = " SELECT LAST_INSERT_ID() as id";
				ResultSet rs = bd.consultar(sql);
				try {
					rs.next();
					this.idestimacion = rs.getInt("id");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
		}
		else
		{
			sql = "UPDATE `generadores`.`control_estimacion` "
					+ "SET "
					+ "`idfrente` = " + this.idfrente + ", "
					+ "`idConsultor` = " + this.idConsultor + ","
					+ "`pocentaje` = " + this.pocentaje + ","
					+ "`fecha` = '" + fecha + "', "
					+ "`tipo` = '" + tipo + "', "
					+ "`nestimacion` = " + this.nestimacion + " "
					+ "WHERE `idestimacion` = " + this.idestimacion;
			estado = bd.ejecutar(sql);
		}
		return estado;
	}
	/**
	 * Obtiene el prsupuesto inicial de la estimacion
	 * @param idfrente frent del que se obtendra el presupuesto
	 * @param tipo 
	 * @return la estimacion inicial
	 */
	public static LinkedList<Estimacion> sacarPreinicial(String idfrente, String tipo)
	{
		LinkedList<Estimacion> list = new LinkedList<Estimacion>();
		ResultSet resul;
		Estimacion es;
		String sql = "SELECT idestimacion, idfrente, idconsultor, pocentaje, fecha, tipo, nestimacion " + " FROM control_estimacion " + " WHERE idfrente = " + idfrente + " AND tipo = '" + tipo + "';";
		BDConexion bd = new BDConexion();
		
		try {
			resul = bd.consultar(sql);
			while (resul.next()) {
				es = new Estimacion();
				es.setIdestimacion(resul.getInt("idestimacion"));
				es.setIdfrente(resul.getInt("idfrente"));
				es.setIdConsultor(resul.getInt("idconsultor"));
				es.setPorcentaje(resul.getFloat("pocentaje"));
				es.setFecha(String.valueOf(resul.getString("fecha")));
				es.setTipo(String.valueOf(resul.getString("tipo")));
				es.setNestimacion(Integer.parseInt(resul.getString("nestimacion")));
				list.add(es);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}
	/**
	 * Elimina la estimacion
	 * @return verdadero si se elimina falso en caso contrario
	 */
	public boolean delete()
	{
		String sql = "DELETE FROM `generadores`.`control_estimacion` WHERE  idestimacion = " + this.idestimacion;
		BDConexion bd = new BDConexion();
		return bd.ejecutar(sql);
	}
	/**
	 * Elimina el aspecto de la estimacion
	 * @param idaspecto	aspecto a eliminar
	 * @param idestimacion estimacion asociada
	 * @return verdadero si se elimino
	 */
	public static boolean eliminaAspecto(Integer idaspecto, Integer idestimacion)
	{
		String sql = "DELETE FROM `generadores`.`esti_aspec` WHERE idaspecto = " + idaspecto + " and idestimacion = " + idestimacion;
		BDConexion bd = new BDConexion();
		return bd.ejecutar(sql);
	}
	/**
	 * Método que inserta los conceptos que estanran contenidos en la estimación
	 * incial
	 * 
	 * @param idaspecto
	 * @param idestimacion
	 * @param cantidad
	 * @param importe
	 * @param ubicacionx
	 * @param ubicaciony
	 * @param ubicacionz
	 * @param alto
	 * @param largo
	 * @param ancho
	 * @param costo
	 * @param idpartida
	 * @param repeticion
	 * @param fechaestimacion
	 * @return insert con éxito ? true : false
	 */
	public static boolean InsertaAspecto(String idaspecto, String idestimacion, String piezas, String importe, String x,
			String y, String z, String alto, String largo, String ancho, String costo, String idpartida, String repeticion,
			String fechaestimacion) {
			if(Estimacion.existeAspecto(idaspecto, idestimacion, repeticion))
				return true;
			String sql = "INSERT INTO `generadores`.`esti_aspec` "
					+ "(`idaspecto`,`idestimacion`,`cantidad`,`importe`,`ubicacionX`,`ubicacionY`,`ubicacionZ`,`alto`,`largo`,`Ancho`,`costo`,`idpartida`,`repeticion`,`fechae`)"
					+ "VALUES ("+ idaspecto+","
							+ " " + idestimacion + ","
							+ " " + piezas + "," + importe + "," + x +"," + y + "," + z + "," + alto + "," + largo + "," + ancho + "," + costo + "," + idpartida + "," + repeticion + ",'" + fechaestimacion + "')";
			
			BDConexion bd = new BDConexion();
			boolean estado = bd.ejecutar(sql);
			System.out.println("Estado de la estimacion " + estado);
			bd.cerrar();
		return estado;
	}
	/**
	 * Verifica la existencia de la estimacion 
	 * @param idaspecto 
	 * @param idestimacion
	 * @param repeticion
	 * @return verdadero si existe falso en caso contrario
	 */
	public static boolean existeAspecto(String idaspecto, String idestimacion, String repeticion )
	{
		boolean estado = false;
		BDConexion bd = new BDConexion();
		String sql = "select * from esti_aspec where idaspecto = " + idaspecto + " and idestimacion = " + idestimacion + " and repeticion = " + repeticion ;
		ResultSet rs = bd.consultar(sql);
		try {
			if(rs.next())
				estado = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bd.cerrar();
		return estado;
	}
}
