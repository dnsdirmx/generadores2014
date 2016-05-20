package controllers;

import javax.swing.JDesktopPane;

import MetodosRemotos.Metodos;
import Views.AcerosView;

public class AcerosController {
	AcerosView vista;
	public AcerosController(final JDesktopPane escritorio, Metodos conexion)
	{
		vista = new AcerosView(escritorio,conexion,this);
	}
	public AcerosView getVista()
	{
		return vista;
	}
}
