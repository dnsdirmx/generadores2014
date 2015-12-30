package Manejodetablas2;

import java.util.LinkedList;

import ObjetosSerializables.Rgenerador;

/**
 * 
 * @author Pablo Rivera
 *
 */
public class ControlTableGenerador {

	private ModeloTablaGenerador modelo = new ModeloTablaGenerador();

	/**
	 * Constructor de la clase. asignaci�n del modelo de datos.
	 * @param modelo
	 */
	public ControlTableGenerador(ModeloTablaGenerador modelo) {
		this.modelo = modelo;
	}

	/**
	 * Agregar nueva fila al modelo de datos
	 * @param asp
	 */
	public void anhadeFila(Rgenerador asp) {
		modelo.anhadeAspecto(asp);
	}

	/**
	 * Agregar una nueva fila al modelo de datos en el indice especificado como parametro
	 * @param asp --- nuevo aspecto agregado 
	 * @param indice --- posici�n el modelo a ingresar.
	 */
	public void Insertaabajo(Rgenerador asp, int indice) {
		modelo.anhadeAspectoAbajo(asp, indice);
	}

	/**
	 * borrar filas del modelo de datos
	 * @param indice --- posici�n del modelo de datos a eliminar 
	 * @param veces
	 */
	public void borraFila(int indice, int veces) {
		if (modelo.getRowCount() > 0)
			modelo.borraAspecto(indice, veces);
	}

	/**
	 * M�todo para obtener la propiedad editable de la celda del modelo de la tabla 
	 * @param ban
	 */
	public void Editable(boolean ban) {
		modelo.Editable(ban);
	}

	/**
	 * obtener la lista de elementos del modelo de datos
	 * @return lista de datos del modelo
	 */
	public LinkedList<Rgenerador> getLista() {
		return modelo.getListaDatos();
	}

	/**
	 * M�todo para obtener la propiedad editable de la celda del modelo de la tabla, apartir de la posici�n fila y columna del modelo de datos
	 * @param col --- posici�n de la columna 
	 * @param fila --- posici�n de la fila
	 * @return es editable ? true: false 
	 */
	public boolean Editable(int col, int fila) {
		return modelo.isCellEditable(col, fila);
	}

	/**
	 * Obtener el n�mero de elementos dentro del modelo de datos
	 * @return n�mero de elementos
	 */
	public int Tama() {
		return modelo.getRowCount();
	}
}
