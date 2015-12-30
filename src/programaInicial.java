/**
 * Clase para la ejecuci�n del programa
 * 
 * @author Pablo Rivera
 * @version 1.0
 * @description proyecto de vinculaci�n Arquitectos
 * @access {@docRoot}
 * @colaboraci�n luiiis lazaro
 */

public class programaInicial {
	/**
	 * Inicio del programa crea la interfaz para concectarse con el servidor
	 */
	public static void main(String[] args) {
		InterfazServidor conenctarBD = new InterfazServidor();
		conenctarBD.setVisible(true);
	}
}