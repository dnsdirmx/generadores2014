package controllers;

import java.awt.Component;
import java.util.LinkedList;

import javax.swing.JDesktopPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import Manejodetablas2.ControlTableGenerador;
import Manejotablas.MyTableModel;
import MetodosRemotos.Metodos;
import Model.Entity.Aspecto;
import ObjetosSerializables.Rgenerador;
import Views.Presupuesto;

public class PresupuestoController {
	private Presupuesto vista;
	public PresupuestoController(JDesktopPane escritorio2, Metodos conexion)
	{
		vista = new Presupuesto(escritorio2, conexion, this);
	}
	public void obtenerAspectos(ControlTableGenerador control, LinkedList<Rgenerador> lseleccion) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Llena los aspectos del la vista
	 * @param Laspectos
	 * @param asp2
	 * @param Ddescripcion
	 * @param TDescripcion
	 * @param control
	 * @param indice
	 */
	public void llenarAspectos(LinkedList<Aspecto> Laspectos, Aspecto asp2,
			JScrollPane Ddescripcion, JTable TDescripcion,
			ControlTableGenerador control, int indice) {
		Laspectos = Aspecto.findAll();
		Rgenerador seleccionado = new Rgenerador();
		int posi = 0;
		Object[][] elementos;
		int filas = 0, columnas = 2;
		for (int i = 0; i < Laspectos.size(); i++) {
			asp2 = (Aspecto) Laspectos.get(i);
			if (asp2.getIdConcepto() == indice)
				filas++;
		}
		elementos = new Object[filas][columnas];
		for (int i = 0; i < elementos.length; i++) {
			elementos[i][0] = new Boolean(false);
		}
		TDescripcion.removeAll();

		for (int i = 0; i < Laspectos.size(); i++) {
			asp2 = (Aspecto) Laspectos.get(i);
			if (asp2.getIdConcepto() == indice) {
				elementos[posi][1] = asp2.getDescripcion();
				if (control.Tama() > 0) {
					for (int j = 0; j < control.getLista().size(); j++) {
						seleccionado = (Rgenerador) control.getLista().get(j);
						if (seleccionado.getClave().equals(asp2.getClave())) {
							elementos[posi][0] = new Boolean(true);
						}
					}
				}
				posi++;
			}
		}
		AbstractTableModel dos = new MyTableModel(elementos);
		TDescripcion.removeAll();
		Ddescripcion.setViewportView(TDescripcion);
		TDescripcion.setModel(dos);
	}

	public Component getVista() {
		return vista;
	}

}
