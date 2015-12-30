package Model.Entity;

/**
 * Clase para representar la unidad de medida
 * 
 * @author luiiis
 *
 */
public class Unidad implements java.io.Serializable {

	/**
	 * elementos de la undiad
	 */
	private static final long serialVersionUID = 1L;
	private Integer idUnidad;
	private String nombre;
	private Boolean largo;
	private Boolean ancho;
	private Boolean alto;

	// constructor
	public Unidad() {
	}

	// constructor
	public Unidad(String nombre, Boolean largo) {
		this.nombre = nombre.replace("\\", "\\\\").replace("\"", "\\\"").replace("'", "\\'");
		this.largo = largo;
	}

	// constructor
	public Unidad(String nombre, Boolean largo, Boolean ancho, Boolean alto) {
		this.nombre = nombre.replace("\\", "\\\\").replace("\"", "\\\"").replace("'", "\\'");
		this.largo = largo;
		this.ancho = ancho;
		this.alto = alto;
	}

	// recuperacion del id de la unidad
	public Integer getIdUnidad() {
		return this.idUnidad;
	}

	// asignaci�n del id de la unidad
	public void setIdUnidad(Integer idUnidad) {
		this.idUnidad = idUnidad;
	}

	// recuperaci�n del nombre de la unidad
	public String getNombre() {
		return this.nombre;
	}

	// asignaci�n del nombre de la unidad
	public void setNombre(String nombre) {
		this.nombre = nombre.replace("\\", "\\\\").replace("\"", "\\\"").replace("'", "\\'") ;
	}

	// recuperaci�n de la propiedad largo de la unidad
	public Boolean isLargo() {
		return this.largo;
	}

	// asignaci�n del atributo largo de la unidad
	public void setLargo(Boolean largo) {
		this.largo = largo;
	}

	// recuperaci�n del atributo ancho de la unidad
	public Boolean isAncho() {
		return this.ancho;
	}

	// asignaci�n del atributo ancho de la unidad
	public void setAncho(Boolean ancho) {
		this.ancho = ancho;
	}

	// recuperaci�n del atributo alto de la unidad
	public Boolean isAlto() {
		return this.alto;
	}

	// asignaci�n del alto de la unidad
	public void setAlto(Boolean alto) {
		this.alto = alto;
	}

}
