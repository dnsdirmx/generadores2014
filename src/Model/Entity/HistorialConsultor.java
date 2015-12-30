package Model.Entity;

import java.sql.Timestamp;

import ObjetosSerializables.Consultor;
import ObjetosSerializables.Proyecto;

/**
 * Clase para la representaci�n de historiales de consultor
 * @author Luis Angel Hern�ndez L�zaro
 *
 */
public class HistorialConsultor {
	
	/**
	 * elementos de para crear el historial
	 */
	private int idHistorial;
	private Proyecto proyecto;
	private Consultor idConsultorAnterior;
	private Consultor idConsultorNuevo;
	private Timestamp fechaRegistro;
	
	/**
	 * recuperar la fecha de creaci�n del historial
	 * @return fechaRegistro -- fecha de registro del historial 
	 */
	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}
	
	/**
	 * asignaci�n de la fecha de registro
	 * @param fechaRegistro - fecha de registro del historial
	 */
	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	
	/**
	 * recuperaci�n del identificador del historial
	 * @return the idHistorial
	 */
	public int getIdHistorial() {
		return idHistorial;
	}
	
	/**
	 * asignaci�n del historial del consultor
	 * @param idHistorial the idHistorial to set
	 */
	public void setIdHistorial(int idHistorial) {
		this.idHistorial = idHistorial;
	}
	
	/**
	 * recuperaci�n del proyecto del historial
	 * @return the proyecto
	 */
	public Proyecto getProyecto() {
		return proyecto;
	}
	
	/**
	 * asignaci�n del proyecto del historial
	 * @param proyecto 
	 */
	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}
	
	/**
	 * recuperaci�n del identificador del consultor anterior
	 * @return the idConsultorAnterior
	 */
	public Consultor getIdConsultorAnterior() {
		return idConsultorAnterior;
	}
	
	/**
	 * asignaci�n del identificador del consultor anterior
	 * @param idConsultorAnterior the idConsultorAnterior to set
	 */
	public void setIdConsultorAnterior(Consultor idConsultorAnterior) {
		this.idConsultorAnterior = idConsultorAnterior;
	}
	
	/**
	 * recuperaci�n del nuevo consultor
	 * @return the idConsultorNUevo
	 */
	public Consultor getIdConsultorNuevo() {
		return idConsultorNuevo;
	}
	
	/**
	 * asignaci�n del nuevo consultor
	 * @param idConsultorNUevo the idConsultorNUevo to set
	 */
	public void setIdConsultorNuevo(Consultor idConsultorNUevo) {
		this.idConsultorNuevo = idConsultorNUevo;
	}
}
