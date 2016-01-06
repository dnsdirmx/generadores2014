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
	public ProyectosView getVista() {
		return vista;
	}
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
	public Integer getIdProyecto() {
		if(proyecto == null)
			return null;
		if(proyecto.getIdproyecto() == -1)
			return null;
		return proyecto.getIdproyecto();
	}
}