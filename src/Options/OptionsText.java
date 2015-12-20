package Options;

import java.awt.Event;
import java.awt.event.KeyEvent;

import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

/**
 * Clase para desactivar el CTRL+V CTRL+C en los componenetes de JTextField
 * 
 * @author luiiis lazaro
 * @category Complementos
 */
public class OptionsText {

	/**
	 * Deshabilitaci�n de CTRL+C CTRL+V
	 * 
	 * @param txt
	 *            JComponent --- Componente para deshabilitar CTRL
	 */
	public static void disablePasteAndCopyAction(JComponent txt) {
		InputMap map = txt.getInputMap();
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, Event.SHIFT_MASK), "null");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK), "null");
	}

	/**
	 * Restricci�n de caracteres en los componentes 'A-Za-z'
	 * 
	 * @param caracter
	 *            char --- Caracter ingresado
	 * @param e
	 *            KeyEvent --- Evento del teclado
	 */
	public static void onlyChars(char caracter, KeyEvent e) {
		if (((caracter < 'a') || (caracter > 'z')) && (caracter < 'A' || caracter > 'Z') && (caracter != KeyEvent.VK_BACK_SPACE) && (caracter != '�' && caracter != '�' && caracter != '�' && caracter != '�' && caracter != '�' && caracter != '!' && caracter != '"' && caracter != '#' && caracter != '$' && caracter != '%' && caracter != '&' && caracter != '/' && caracter != '(' && caracter != ')' && caracter != '=' && caracter != '?' && caracter != '�' && caracter != '�' && caracter != '+' && caracter != '*' && caracter != '{' && caracter != '}' && caracter != '[' && caracter != ']' && caracter != ',' && caracter != ';' && caracter != '.' && caracter != ':' && caracter != '-' && caracter != '_')) {
			e.consume();
		}
	}

	/**
	 * Restricci�n de caracteres en los componentes 'A-Za-z '
	 * 
	 * @param caracter
	 *            char --- Caracter ingresado
	 * @param e
	 *            KeyEvent --- Evento del teclado
	 */
	public static void onlyCharsSpace(char caracter, KeyEvent e) {
		if (((caracter < 'a') || (caracter > 'z')) && (caracter < 'A' || caracter > 'Z') && (caracter != '�' && caracter != '�' && caracter != '�' && caracter != '�' && caracter != '�' && caracter != ' ' && caracter != '!' && caracter != '"' && caracter != '#' && caracter != '$' && caracter != '%' && caracter != '&' && caracter != '/' && caracter != '(' && caracter != ')' && caracter != '=' && caracter != '?' && caracter != '�' && caracter != '�' && caracter != '+' && caracter != '*' && caracter != '{' && caracter != '}' && caracter != '[' && caracter != ']' && caracter != ',' && caracter != ';' && caracter != '.' && caracter != ':' && caracter != '-' && caracter != '_')) {
			e.consume();
		}
	}

	/**
	 * Restricci�n de caracteres en los componentes 'A-Za-z 0-9'
	 * 
	 * @param caracter
	 *            char --- Caracter ingresado
	 * @param e
	 *            KeyEvent --- Evento del teclado
	 */
	public static void onlyCharsNumbers(char caracter, KeyEvent e) {
		if (((caracter < 'a') || (caracter > 'z')) && (caracter < 'A' || caracter > 'Z') && (caracter != ' ') && (caracter != '�' && caracter != 'e' && caracter != '�' && caracter != '�' && caracter != '�' && caracter != '!' && caracter != '"' && caracter != '#' && caracter != '$' && caracter != '%' && caracter != '&' && caracter != '/' && caracter != '(' && caracter != ')' && caracter != '=' && caracter != '?' && caracter != '�' && caracter != '�' && caracter != '+' && caracter != '*' && caracter != '{' && caracter != '}' && caracter != '[' && caracter != ']' && caracter != ',' && caracter != ';' && caracter != '.' && caracter != ':' && caracter != '-' && caracter != '_') && (caracter < '0' || caracter > '9')) {
			e.consume();
		}
	}

	/**
	 * Restricci�n de caracteres en los componentes '0-9.'
	 * 
	 * @param caracter
	 *            char --- Caracter ingresado
	 * @param e
	 *            KeyEvent --- Evento del teclado
	 */
	public static void onlyNumbersDouble(char caracter, KeyEvent e) {
		if ((caracter < '0' || caracter > '9') && (caracter != '.')) {
			e.consume();
		}
	}

	/**
	 * M�todo para escribir solo n�meros enteros 
	 * @param caracter
	 * @param e
	 */
	public static void onlyINT(char caracter, KeyEvent e) {
		if ((caracter < '0' || caracter > '9')) {
			e.consume();
		}
	}

	/**
	 * Escapar caracteres especiales para mysql
	 * 
	 * @param text
	 *            String --- B�squeda de caracteres a escapar
	 * @return text String --- caracteres escapados
	 */
	public static String replaceString(String text) {
		return text.replace("\\", "\\\\").replace("\"", "\\\"").replace("'", "\\'");
	}
}
