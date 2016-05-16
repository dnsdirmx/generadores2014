package TablaFrentes;

import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import MetodosRemotos.Metodos;
import Model.Entity.Frente;

// import java.rmi.RemoteException;


import java.util.LinkedList;

/**
 * Clase ModeloFrente Funciona para declarar el modelo de la tabla de frentes
 */
public class Modelofrente implements TableModel {

	// Lista de datos contenidos en el modelo de frentes
	private LinkedList<Model.Entity.Frente> datos = new LinkedList<Model.Entity.Frente>();
	private LinkedList<TableModelListener> listeners = new LinkedList<TableModelListener>();
	// M�todos para la manipulaci�n a la base de datos
	private Metodos cone;

	/**
	 * Asignaci�n de la conexi�n
	 * 
	 * @param con
	 *            Metodos --- para la manipulaci�n de la base de datos
	 */
	public void conexion(Metodos con) {
		this.cone = con;
	}

	/**
	 * N�mero de columnas existentes en el modelo de datos
	 */
	public int getColumnCount() {
		return 2;
	}

	/**
	 * N�mero de elementos en la lista de datos
	 */
	public int getRowCount() {
		return datos.size();
	}

	/**
	 * Obtiene el valor de una celda de la tabla
	 * 
	 * @param rowIndex
	 *            int --- �ndice de la fila
	 * @param columnIndex
	 *            int --- �ndice de la columna
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		Model.Entity.Frente aux = (Model.Entity.Frente) datos.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return aux.getIndentificador();
		case 1:
			return aux.getUbicacion();

		default:
			return null;
		}
	}

	/**
	 * Elimina una fila del modelo de la tabla
	 * 
	 * @param fila
	 *            int --- �ndice de la fila a eliminar
	 */
	public void borrarfrente(int indice) {
		datos.remove(indice);

		TableModelEvent evento = new TableModelEvent(this, indice, indice, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
		avisaSuscriptores(evento);
	}

	/**
	 * Agregar una fila al modelo de la tabla
	 * 
	 * @param tipo
	 *            String --- Nombre del nuevo Tipo de Proyecto
	 */
	public void anhadeFrente(Model.Entity.Frente fre) {
		datos.add(fre);
		TableModelEvent evento;
		evento = new TableModelEvent(this, this.getRowCount() - 1, this.getRowCount() - 1, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT);
		avisaSuscriptores(evento);
	}

	/**
	 * Agregar Listener al modelo
	 */
	public void addTableModelListener(TableModelListener l) {
		listeners.add(l);
	}

	/**
	 * Obtener el tipo de clase de la coluna elegida
	 */
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return String.class;
		case 1:
			return String.class;

		default:
			return Object.class;
		}
	}

	/**
	 * Obtener Nombres de las Columnas del Modelo
	 */
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return "Indentificador";
		case 1:
			return "Ubicaci�n";
		default:
			return null;
		}
	}

	/**
	 * Permitir Edici�n
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	/**
	 * Eliminar un elemento de la lista de Listeners del modelo
	 */
	public void removeTableModelListener(TableModelListener l) {
		listeners.remove(l);
	}

	/**
	 * Fijar un valor a una celda de la tabla en el modelo
	 */
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Model.Entity.Frente aux;
		String tempIdentificadorUbicacion;
		aux = (Model.Entity.Frente) datos.get(rowIndex);
		switch (columnIndex) {
			case 0:
				tempIdentificadorUbicacion= aux.getIndentificador();
				aux.setIdentificador((String) aValue);
				if (!aux.getIndentificador().equals("")) {
					Frente frente = Frente.findById(aux.getIdfrente());
					frente.setIdentificador(aux.getIndentificador());
					frente.setUbicacion(aux.getUbicacion());
					frente.save();
					//cone.ModificarFrente(aux.getIndentificador(), aux.getUbicacion(), String.valueOf(aux.getIdfrente()));
				}else{
					JOptionPane.showMessageDialog(null, "No se permite este campo vac�o...");
					setValueAt(tempIdentificadorUbicacion, rowIndex, columnIndex);
				}
				break;
			case 1:
				tempIdentificadorUbicacion=aux.getUbicacion();
				aux.setUbicacion((String) aValue);
				if (! aux.getUbicacion().equals("")) {
					Frente frente = Frente.findById(aux.getIdfrente());
					frente.setIdentificador(aux.getIndentificador());
					frente.setUbicacion(aux.getUbicacion());
					frente.save();
					//cone.ModificarFrente(aux.getIndentificador(), aux.getUbicacion(), String.valueOf(aux.getIdfrente()));
				}else{
					JOptionPane.showMessageDialog(null, "No se permite este campo vac�o...");
					setValueAt(tempIdentificadorUbicacion, rowIndex, columnIndex);
				}
			default:
				break;
		}
		TableModelEvent evento = new TableModelEvent(this, rowIndex, rowIndex, columnIndex);
		avisaSuscriptores(evento);
	}

	/**
	 * Avisa cual es la fila y columna que cambi�
	 * 
	 * @param evento
	 */
	private void avisaSuscriptores(TableModelEvent evento) {
		int i;
		for (i = 0; i < listeners.size(); i++)
			((TableModelListener) listeners.get(i)).tableChanged(evento);
	}

	/**
	 * Obtener los Datos del modelo de la tabla
	 * 
	 * @return lista de elementos
	 */
	public LinkedList<Model.Entity.Frente> getListaDatos() {
		return this.datos;
	}

	/**
	 * Limpiar la lista de los elementos del modelo de la tabla
	 */
	public void Limpiar() {
		this.datos = new LinkedList<Model.Entity.Frente>();
	}

}