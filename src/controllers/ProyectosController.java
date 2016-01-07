package controllers;

import java.util.Date;

import javax.swing.JDesktopPane;

import MetodosRemotos.Metodos;
import Model.Entity.Proyecto;
import Views.ProyectosView;

public class ProyectosController {
	private ProyectosView vista;
	private Proyecto proyecto;
	public ProyectosController(JDesktopPane escritorio2, Metodos cone)
	{
		this.vista = new ProyectosView(this,escritorio2, cone);
		proyecto = null;
	}
	/**
	 * Obtiene la vista asociada al controlador
	 * @return verdadero o falso 
	 */
	public ProyectosView getVista() {
		return vista;
	}
	/**
	 * Inserta un nuevo proyecto
	 * @param idtipo
	 * @param date
	 * @param date2
	 * @param descripcion
	 * @param nombre
	 * @param comentarios
	 * @return verdadero si se inserto falso en caso contrario
	 */
	public boolean create(String idtipo, Date date, Date date2, String descripcion, String nombre, String comentarios) {
		proyecto = new Proyecto();
		proyecto.setIdtipo(Integer.parseInt(idtipo));
		proyecto.setFin(new java.sql.Date(date2.getTime()));
		proyecto.setInicio(new java.sql.Date(date.getTime()));
		proyecto.setDescripcion(descripcion);
		proyecto.setProyecto(nombre);
		proyecto.setComentarios(comentarios);
		return proyecto.save();
	}
	/**
	 * Obtiene el id del proyecto actual
	 * @return nulo si no hay un proyecto o el id del proyecto
	 */
	public Integer getIdProyecto() {
		if(proyecto == null)
			return null;
		if(proyecto.getIdproyecto() == -1)
			return null;
		return proyecto.getIdproyecto();
	}
	/**
	 * Elimina el proyecto especificado
	 * @param idproyecto
	 * @return verdadero o falso
	 */
	public boolean destroyProyecto(int idproyecto) {
		boolean estado = false;
		Proyecto proyecto = Proyecto.findById(idproyecto);
		if(proyecto != null)
			estado = proyecto.delete();
		return estado;
	}
	/**
	 * Actualiza el proyecto especificado 
	 * @param idtipo
	 * @param date
	 * @param date2
	 * @param descripcion
	 * @param nombre
	 * @param idproyecto
	 * @return verdaero o falso
	 */
	public boolean updateProyecto(String idtipo, Date date, Date date2, String descripcion, String nombre,int idproyecto) {
		proyecto = Proyecto.findById(idproyecto);
		if(proyecto == null)
		{
			System.out.println("Error al actualizar proyecto");
		}
		proyecto.setIdtipo(Integer.parseInt(idtipo));
		proyecto.setFin(new java.sql.Date(date2.getTime()));
		proyecto.setInicio(new java.sql.Date(date.getTime()));
		proyecto.setDescripcion(descripcion);
		proyecto.setProyecto(nombre);
		
		
		return proyecto.save();
	}
}
