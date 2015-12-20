package ImportarExportar.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Model.Entity.AspectoSelection;
import Model.Entity.ConceptoSelection;
import Model.Entity.PartidaSelection;
import Model.EntityCRUD.ImportacionCRUD;
import Model.EntityCRUD.Exceptions.ForeingKeyReference;
import Model.EntityCRUD.Exceptions.ImportarExportar.IncompleteInformationDesglose;

/**
 * clase controladora para la importaci�n de conceptos
 * 
 * @author Luis Angel Hern�ndez L�zaro
 *
 */
public class CImportacionConcepos {

	private static Logger log = Logger.getLogger(CImportacionConcepos.class);

	/**
	 * m�todo para verficar la extensi�n del archivo seleccionado
	 * 
	 * @param fileInput
	 *            -- archivo seleccionado para la importaci�n de conceptos
	 * @return si la extensi�n es .xlx o .xlsx? true : false
	 */
	public static boolean checkExtensionFile(File fileInput) {
		boolean result = false;
		// se crea un split del nombre del archivo seleccionado
		String[] properties = fileInput.getName().split("\\.");
		// se verifica la extensi�n del archivo seleccionado
		if (properties[1].equals("xls") || properties[1].equals("xlsx")) {
			result = true;
		}
		return result;
	}

	/**
	 * m�todo para obtener las partidas, conceptos, y desglose de conceptos del
	 * archivo seleccionado
	 * 
	 * @param fileInput
	 *            -- archivo de importaci�n seleccionado
	 * @return lista de partidas de seleccion encontradas en el archivo
	 *         seleccionado
	 * @throws IOException
	 * @throws IncompleteInformationDesglose
	 * @throws Exception
	 */
	public static LinkedList<PartidaSelection> findPartidasFileInput(File fileInput) throws IOException, IncompleteInformationDesglose {
		LinkedList<PartidaSelection> listPartidaSelectionFileInput = new LinkedList<PartidaSelection>();
		String[] properties = fileInput.getName().split("\\.");
		// se verifica la extensi�n del archivo una vez m�s
		if (properties[1].equals("xls")) {
			// si es un archivo de excel 97-03 .xls
			listPartidaSelectionFileInput = findPartidasFileXls(fileInput);
		} else if (properties[1].equals("xlsx")) {
			// si es un archivo de excel 2007 .xlsx
			listPartidaSelectionFileInput = findPartidasFileXlsx(fileInput);
		} else {
			// en caso contrario no regresar ning�n elemento en la lista
			return listPartidaSelectionFileInput = null;
		}
		return listPartidaSelectionFileInput;
	}

	/**
	 * m�todo para encontrar las partidas en un archivo .xls
	 * 
	 * @param fileInput
	 *            -- archivo de importaci�n
	 * @return lista de partidas de seleccion encontradas
	 * @throws IOException
	 * @throws IncompleteInformationDesglose
	 * @throws Exception
	 */
	public static LinkedList<PartidaSelection> findPartidasFileXls(File fileInput) throws IOException, IncompleteInformationDesglose {
		LinkedList<PartidaSelection> listPartidaSelection = new LinkedList<PartidaSelection>();

		// creacion de los archivos para manipular el contenido del archivo
		FileInputStream fInputStream = new FileInputStream(fileInput);
		POIFSFileSystem pfFileSystem = new POIFSFileSystem(fInputStream);

		// recuperaci�n del libro de excel del archivo
		@SuppressWarnings("resource")
		HSSFWorkbook workbook = new HSSFWorkbook(pfFileSystem);

		// recuperaci�n de la primera hoja del libro de excel
		HSSFSheet sheet = workbook.getSheetAt(0);

		// recuperaci�n de la primera fila de la hoja de excel
		HSSFRow titles = sheet.getRow(0);
		// se verifica que el orden de las columnas sea el correcto
		if (titles.getCell(0).toString().equals("Clave") && titles.getCell(1).toString().equals("Descripci�n") && titles.getCell(2).toString().equals("Unidad") && titles.getCell(3).toString().equals("Costo")) {
			// si es correcto el formato se crean una partida y concepto para
			// recuperar la informaci�n
			PartidaSelection partida = new PartidaSelection();
			ConceptoSelection concepto = new ConceptoSelection();

			// se recorren las filas en la hoja del libro para obtener la
			// informaci�n
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				// se recupera la fila del recorrido
				HSSFRow row = sheet.getRow(i);
				// se realiza un try catch para contemplar columnas combinadas
				// en el nombre de las partidas
				try {
					// si la segunda columna se encuentra vacia se crea una
					// nueva partida y concepto con el nombre de la celda actual
					if (row.getCell(1).toString().isEmpty()) {
						partida = new PartidaSelection();
						concepto = new ConceptoSelection();

						partida.setNombre(row.getCell(0).toString());
						partida.setSelect(true);

						concepto.setNombre(row.getCell(0).toString());
						concepto.setSelect(true);
						// se agrega el concepto a la lista de conceptos de la
						// partida
						partida.addSubsConceptos(concepto);
						// se agrega la partida a la lista de partidas
						// encontradas
						listPartidaSelection.add(partida);

					} else {
						int index = 0;// variable para localizar la columna que
										// casue problemas
						AspectoSelection desgloseConcepto = new AspectoSelection();
						desgloseConcepto.setSelect(true);

						try {
							// si la segunda columna no esta vacia y las
							// primeras 4 columnas son diferenten de estar
							// vacias
							// se crea un nuevo desglose de concepto y se agrega
							// a la lista de aspectos del concepto actual
							if (!row.getCell(0).toString().isEmpty()) {
								index++;
								desgloseConcepto.setClave(row.getCell(0).toString());
								if (!row.getCell(1).toString().isEmpty()) {
									index++;
									desgloseConcepto.setDescripcion(row.getCell(1).toString());
									if (!row.getCell(2).toString().isEmpty()) {
										index++;
										desgloseConcepto.setUnidad(row.getCell(2).toString());
										if (!row.getCell(3).toString().isEmpty()) {
											index++;
											desgloseConcepto.setCosto(row.getCell(3).toString());
											concepto.addSubsAspecto(desgloseConcepto);
										}
									}
								}
							}
						} catch (NullPointerException e) {
							IncompleteInformationDesglose eIncompleteInformationDesglose = new IncompleteInformationDesglose("---", i + 1);
							switch (index) {
							case 0:
								eIncompleteInformationDesglose = new IncompleteInformationDesglose("Clave", i + 1);
								break;
							case 1:
								eIncompleteInformationDesglose = new IncompleteInformationDesglose("Descripci�n", i + 1);
								break;
							case 2:
								eIncompleteInformationDesglose = new IncompleteInformationDesglose("Unidad", i + 1);
								break;
							case 3:
								eIncompleteInformationDesglose = new IncompleteInformationDesglose("Clave", i + 1);
								break;
							default:
								break;
							}
							// si alg�n concepto no tiene informaci�n se crea
							// una nueva excepci�n para informar al usuario
							log.error(eIncompleteInformationDesglose);
							throw eIncompleteInformationDesglose;
						}

					}
				} catch (NullPointerException e) {
					log.info(e, e);

					// si la celda no se encuentra combinada se crea la partida
					// y el concepto con el nombre en la fila actual .. se
					// registra la excepci�n en el archivo
					partida = new PartidaSelection();
					concepto = new ConceptoSelection();

					partida.setIdpartida(i);
					partida.setNombre(row.getCell(0).toString());
					partida.setSelect(true);

					concepto.setIdpartida(i);
					concepto.setIdconcepto(i);
					concepto.setNombre(row.getCell(0).toString());
					concepto.setSelect(true);

					partida.addSubsConceptos(concepto);

					listPartidaSelection.add(partida);

					log.warn(e);

				}

			}
		}
		// se regresa la lista obtenida
		return listPartidaSelection;
	}

	/**
	 * m�todo para encotnrar las partidas en un archivo .xlsx
	 * 
	 * @param fileInput
	 *            -- archivo de importaci�n
	 * @return lista de partidas de seleccion encontradas
	 * @throws IOException
	 * @throws IncompleteInformationDesglose
	 * @throws Exception
	 */
	public static LinkedList<PartidaSelection> findPartidasFileXlsx(File fileInput) throws IOException, IncompleteInformationDesglose {
		LinkedList<PartidaSelection> listPartidaSelection = new LinkedList<PartidaSelection>();

		// creaci�n del archivo para manupular el contenido
		FileInputStream fInputStream = new FileInputStream(fileInput);
		@SuppressWarnings("resource")
		// creaci�n del libro de excel
		XSSFWorkbook workBook = new XSSFWorkbook(fInputStream);
		// recuperaci�n de la primera hoja de excel del archivo
		XSSFSheet sheet = workBook.getSheetAt(0);

		// recuperaci�n de la primera fila para ver los titulos
		XSSFRow titles = sheet.getRow(0);
		// verificaci�n de los titulos de las columnas para ver si coinciden con
		// el formato
		if (titles.getCell(0).toString().equals("Clave") && titles.getCell(1).toString().equals("Descripci�n") && titles.getCell(2).toString().equals("Unidad") && titles.getCell(3).toString().equals("Costo")) {
			// creaci�n de la partida y concepto en caso de respetar el formato
			// del contenido del archivo
			PartidaSelection partida = new PartidaSelection();
			ConceptoSelection concepto = new ConceptoSelection();

			// recorrido para cada una de las filas en el archivo
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				// recuperaci�n de la fila
				XSSFRow row = sheet.getRow(i);
				try {
					// se pregunta si la segunda fila esta vac�a
					if (row.getCell(1).toString().isEmpty()) {
						// si esta vacia se crea la partida y el concepto con el
						// contenido de la fila
						partida = new PartidaSelection();
						concepto = new ConceptoSelection();

						partida.setNombre(row.getCell(0).toString());
						partida.setSelect(true);

						concepto.setNombre(row.getCell(0).toString());
						concepto.setSelect(true);
						// se agrega el concepto a la lista de conceptos de la
						// partida
						partida.addSubsConceptos(concepto);
						// se agrega la partida a la lista de partidas
						// encontradas
						listPartidaSelection.add(partida);

					} else if ((!row.getCell(0).toString().isEmpty()) && (!row.getCell(1).toString().isEmpty()) && (!row.getCell(2).toString().isEmpty()) && (!row.getCell(3).toString().isEmpty())) {
						// si la segunda columna no esta vacia y las primeras 4
						// columnas son diferenten de estar vacias
						// se crea un nuevo desglose de concepto y se agrega a
						// la lista de aspectos del concepto actual
						AspectoSelection desgloseConcepto = new AspectoSelection();
						desgloseConcepto.setSelect(true);
						desgloseConcepto.setClave(row.getCell(0).toString());
						desgloseConcepto.setDescripcion(row.getCell(1).toString());
						desgloseConcepto.setUnidad(row.getCell(2).toString());
						desgloseConcepto.setCosto(row.getCell(3).toString());
						// se agrega el concepto a la lista de conceptos actual
						concepto.addSubsAspecto(desgloseConcepto);
					} else {
						// si las columnas no cumplen con el formato se genera
						// una excepci�n para nofiticar al usuario
						IncompleteInformationDesglose eIncompleteInformationDesglose = new IncompleteInformationDesglose("---", i + 1);
						if (row.getCell(0).toString().isEmpty()) {
							eIncompleteInformationDesglose = new IncompleteInformationDesglose("Clave", i + 1);
						} else if (row.getCell(1).toString().isEmpty()) {
							eIncompleteInformationDesglose = new IncompleteInformationDesglose("Descripci�n", i + 1);
						} else if (row.getCell(2).toString().isEmpty()) {
							eIncompleteInformationDesglose = new IncompleteInformationDesglose("Unidad", i + 1);
						} else if (row.getCell(3).toString().isEmpty()) {
							eIncompleteInformationDesglose = new IncompleteInformationDesglose("Clave", i + 1);
						}
						// si alg�n concepto no tiene informaci�n se crea una
						// nueva excepci�n para informar al usuario
						log.error(eIncompleteInformationDesglose);
						throw eIncompleteInformationDesglose;
					}
				} catch (NullPointerException e) {
					// si la celda de la partida no esta combinada se crea
					// partida y el concepto con el contenido celda
					partida = new PartidaSelection();
					concepto = new ConceptoSelection();

					partida.setIdpartida(i);
					partida.setNombre(row.getCell(0).toString());
					partida.setSelect(true);

					concepto.setIdpartida(i);
					concepto.setIdconcepto(i);
					concepto.setNombre(row.getCell(0).toString());
					concepto.setSelect(true);
					partida.addSubsConceptos(concepto);
					listPartidaSelection.add(partida);
					log.warn(e);
				}
			}
		}
		return listPartidaSelection;
	}

	/**
	 * m�todo para guardar la informaci�n en la base de datos de las partidas,
	 * conceptos y desgloses encontrados en el archivo
	 * 
	 * @param data
	 *            -- lista de partidas encontradas en el archivo
	 * @throws SQLException
	 * @throws ForeingKeyReference
	 */
	public static boolean SavePartidas(LinkedList<PartidaSelection> data) throws SQLException, ForeingKeyReference {
		return ImportacionCRUD.SavePartidas(data);
	}

}
