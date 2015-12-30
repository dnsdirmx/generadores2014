package Model.Entity;

import java.util.LinkedList;

import ObjetosSerializables.Concepto;

/**
 * clase para crear conceptos con un atributo extra 
 * @author Luis Angel Hern�ndez L�zaro
 *
 */
public class ConceptoSelection extends Concepto{

	//elementos de la clase ... se agrega el atributo select|
	private boolean select;
	private LinkedList<AspectoSelection> subsAspectos;

	/**
	 * m�todo para agregar un desglose a la lista de desgloses del concepto
	 * @param aspecto
	 */
	public void addSubsAspecto(AspectoSelection aspecto) {
		subsAspectos.add(aspecto);
	}
	
	/**
	 * recuperaci�n del aspecto por el indice
	 * @param index - indice (posicion) del aspecto en la lista
	 * @return
	 */
	public AspectoSelection getSubAspecto(int index) {
		return subsAspectos.get(index);
	}
	
	
	/**
	 * recuperaci�n de la lista de desgloses de conceptos
	 * @return the subsAspectos
	 */
	public LinkedList<AspectoSelection> getSubsAspectos() {
		return subsAspectos;
	}

	/**
	 * constructor
	 */
	public ConceptoSelection(boolean select, int idconcepto, int idpartida, String nombre) {
		super(idconcepto, idpartida, nombre);
		this.select = select;
		subsAspectos = new  LinkedList<AspectoSelection>();
	}
	
	/**
	 * recuperaci�n del atributo select 
	 * @return valor del select del concepto
	 */
	public boolean isSelect() {
		return select;
	}

	/**
	 * asignaci�n del atributo select del concepto
	 * @param select .valor para guardar
	 */
	public void setSelect(boolean select) {
		this.select = select;
	}
	
	//constructor
	public ConceptoSelection () {
		subsAspectos = new LinkedList<AspectoSelection>();
	}

	
}
