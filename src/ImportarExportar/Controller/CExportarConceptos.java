package ImportarExportar.Controller;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.LinkedList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import Model.Entity.AspectoSelection;
import Model.Entity.ConceptoSelection;
import Model.Entity.PartidaSelection;
import Model.EntityCRUD.AspectoCRUD;
import Model.EntityCRUD.ConceptoCRUD;
import Model.EntityCRUD.PartidaCRUD;
import ObjetosSerializables.Aspecto;
import ObjetosSerializables.Concepto;
import ObjetosSerializables.Partida;

/**
 * clase controladora para exportar el cat�logo de conceptos
 * 
 * @author Luis Angel Hern�ndez Lazaro
 *
 */
public class CExportarConceptos {

	/**
	 * m�todo para recuperar las partidas de la base de datos
	 * 
	 * @return lista de partidas
	 * @throws SQLException
	 */
	public static LinkedList<Partida> findPartidas() throws SQLException {
		return PartidaCRUD.findPartidas();
	}

	/**
	 * m�todo para recuperar los conceptos de la base de datos
	 * 
	 * @return lista de conceptos
	 * @throws SQLException
	 */
	public static LinkedList<Concepto> findConceptos() throws SQLException {
		return ConceptoCRUD.findConceptos();
	}

	/**
	 * m�todo para recuperar los desgloses de concepto (aspecto) de la base de
	 * datos
	 * 
	 * @return lista de desglose de conceptos
	 * @throws SQLException
	 */
	public static LinkedList<Aspecto> findAspectos() throws SQLException {
		return AspectoCRUD.findAspectos();
	}

	/**
	 * m�todo para vincular cada uno de los desgloses de concepto con su
	 * concepto correspondiente y cada uno de los conceptos con su partida
	 * correspondiente
	 * 
	 * @return lista de partidas con sus conceptos y sus desgloses vinculados
	 *         correctamente
	 * @throws SQLException
	 */
	public static LinkedList<PartidaSelection> findPartidaSelection() throws SQLException {
		LinkedList<Partida> list = new LinkedList<Partida>();
		LinkedList<PartidaSelection> listSelect = new LinkedList<PartidaSelection>();

		// recuperaci�n de las partidas en la base de datos
		list = PartidaCRUD.findPartidas();
		// para cada partida se agrega a la lista de partidas de seleccion
		// las partidas de seleccion heredan de partida , y se agrega un
		// atributo que es select (para saber si esta marcada o no en pantalla)
		for (Partida partida : list) {
			listSelect.add(new PartidaSelection(true, partida.getIdpartida(), partida.getNombre()));
		}

		// obtener la lista de aspectos y conceptos de la base de datos ya en
		// formato aspecto de seleccion
		// concepto de seleccion hereda de concepto y se agrega un atributo
		// select
		LinkedList<ConceptoSelection> listconcepto = findConceptoSelection();
		// aspecto de seleccion hereda de aspecto y se agrega un atributo select
		LinkedList<AspectoSelection> listAspecto = findAspectoSelection();
		// llenar relacion entre conceptos y aspectos
		for (ConceptoSelection conceptoSelection : listconcepto) {
			for (AspectoSelection aspectoSelection : listAspecto) {
				// si los identificadores de concepto coinciden entre aspecto y
				// concepto, se agrega el aspecto a la lista de aspectos
				// vinculados al concepto
				if (conceptoSelection.getIdconcepto() == aspectoSelection.getIdconcepto()) {
					conceptoSelection.addSubsAspecto(aspectoSelection);
				}
			}
		}

		// lenar relacion entre partidas y conceptos
		for (PartidaSelection partida : listSelect) {
			for (ConceptoSelection concepto : listconcepto) {
				// si los identificadores de partidas coinciden entre partida y
				// concepto, se agrega el concepto a la lista de conceptos
				// vinculados a la partida
				if (concepto.getIdpartida() == partida.getIdpartida()) {
					partida.addSubsConceptos(concepto);
				}
			}
		}
		return listSelect;
	}

	/**
	 * m�todo para obtener los conceptos en objetos de tipo concepto de
	 * seleccion
	 * 
	 * @return lista de conceptos de seleccion
	 * @throws SQLException
	 */
	public static LinkedList<ConceptoSelection> findConceptoSelection() throws SQLException {
		LinkedList<Concepto> list = new LinkedList<Concepto>();
		LinkedList<ConceptoSelection> listSelect = new LinkedList<ConceptoSelection>();
		// recuperaci�n de los conceptos
		list = ConceptoCRUD.findConceptos();
		for (Concepto concepto : list) {
			// creacion del objeto concepto de seleccion
			listSelect.add(new ConceptoSelection(true, concepto.getIdconcepto(), concepto.getIdpartida(), concepto.getNombre()));
		}
		return listSelect;
	}

	/**
	 * m�todo para obtener los desgloses de concepto en objetos de tipo
	 * seleccion
	 * 
	 * @return lista de desglose de concepto de seleccion
	 * @throws SQLException
	 */
	public static LinkedList<AspectoSelection> findAspectoSelection() throws SQLException {
		LinkedList<Aspecto> list = new LinkedList<Aspecto>();
		LinkedList<AspectoSelection> listSelect = new LinkedList<AspectoSelection>();
		// recuperaci�n de los desgloses de concepto
		list = AspectoCRUD.findAspectos();
		for (Aspecto aspecto : list) {
			// creacion del objeto de seleccion
			listSelect.add(new AspectoSelection(true, aspecto.getIdaspecto(), aspecto.getIdconcepto(), aspecto.getClave(), aspecto.getUnidad(), aspecto.getTipo(), aspecto.getCosto(), aspecto.getDescripcion(), aspecto.getClave_privada(), aspecto.getDescripcion_Completa()));
		}
		return listSelect;
	}

	/**
	 * m�todo para validar la lista de partidas de seleccion
	 * 
	 * @param data
	 *            lista de partidas
	 * @return si ning�n desglose de concepto esta seleccionado? false , si por
	 *         lo menos existe un desglose de concepto seleccionado ? true
	 */
	public static boolean validateList(LinkedList<PartidaSelection> data) {
		// se recorre para cada partida en la lista
		for (PartidaSelection partidaSelection : data) {
			// se verifica si esta seleccionada la partida
			if (partidaSelection.isSelect()) {
				// para cada concepto en la lista
				for (ConceptoSelection conceptoSelection : partidaSelection.getSubsConceptos()) {
					// se verifica si esta seleccionado el concepto
					if (conceptoSelection.isSelect()) {
						// para cada desglose de concepto en la lista
						for (AspectoSelection aspectoSelection : conceptoSelection.getSubsAspectos()) {
							// se verifica si esta seleccionado el desglose
							if (aspectoSelection.isSelect()) {
								// cuando se encuentre el primero se retorna
								// true
								return true;
							}
						}
					}
				}
			}
		}
		// si ningun desglose esta seleccionado se envia false
		return false;
	}

	/**
	 * m�todo para guardar el archivo de exportaci�n, se guarda con la extensi�n
	 * .xls
	 * 
	 * @param rute
	 *            -- directorio para guardar el archivo
	 * @param data
	 *            -- lista de partidas para escribir en el archivo
	 * @throws IOException
	 */
	public static void saveFileExcel(String rute, LinkedList<PartidaSelection> data) throws IOException {
		// titulos de las colunas del Archivo
		final String[] columnTitle = { "Clave P�blica", "Descripci�n", "Unidad", "Costo" };
		// creaci�n del archivo
		FileOutputStream newFile = new FileOutputStream(rute);
		@SuppressWarnings("resource")
		// creaci�n del libro
		HSSFWorkbook book = new HSSFWorkbook();
		// creaci�n de la hoja
		HSSFSheet sheet = book.createSheet("Cat�logo de Conceptos");
		// creaci�n de la fila
		HSSFRow row = sheet.createRow(0);
		// creaci�n de la celda
		HSSFCell cell = row.createCell(0);
		HSSFRichTextString text;
		// creaci�n de los titulos para las columnas
		row = sheet.createRow(0);
		for (int i = 0; i < columnTitle.length; i++) {
			cell = row.createCell(i);
			text = new HSSFRichTextString(columnTitle[i]);
			cell.setCellValue(text);
			sheet.autoSizeColumn((short) i);
		}

		int rowIndex = 1;
		// apartir de la fila 3 se escriben los elementos de la lista
		// para cada partida en la lista
		for (PartidaSelection partidaSelection : data) {
			// se verifica que este seleccionada la partida
			if (partidaSelection.isSelect()) {
				// si esta seleccionada se agrega una fila para el nombre
				HSSFRow rowPartida = sheet.createRow(rowIndex);
				text = new HSSFRichTextString(partidaSelection.getNombre());
				rowPartida.createCell(0).setCellValue(text);
				rowIndex++;
				// para cada concepto en la lista de conceptos de la partida
				for (ConceptoSelection conceptoSeleccion : partidaSelection.getSubsConceptos()) {
					// se verifica si esta seleccionado el concepto
					if (conceptoSeleccion.isSelect()) {
						// si esta seleccionado se agrega una fila para el
						// nombre
						HSSFRow rowConcepto = sheet.createRow(rowIndex);
						rowConcepto.createCell(1);
						text = new HSSFRichTextString(conceptoSeleccion.getNombre());
						rowConcepto.createCell(0).setCellValue(text);
						rowIndex++;
						// para cada desglose de concepto en la lista de
						// desgloses de concepto en el concepto
						for (AspectoSelection aspectoSeleccion : conceptoSeleccion.getSubsAspectos()) {
							// se verifica si esta seleccionado
							if (aspectoSeleccion.isSelect()) {
								// {"Clave P�blica",
								// "Descripci�n","Unidad","Costo"};
								// si esta seleccionado se agrega una fila con
								// la informaci�n del desglose de concepto
								HSSFRow rowAspecto = sheet.createRow(rowIndex);

								text = new HSSFRichTextString(aspectoSeleccion.getClave());
								rowAspecto.createCell(0).setCellValue(text);

								text = new HSSFRichTextString(aspectoSeleccion.getDescripcion());
								rowAspecto.createCell(1).setCellValue(text);

								text = new HSSFRichTextString(aspectoSeleccion.getUnidad());
								rowAspecto.createCell(2).setCellValue(text);

								text = new HSSFRichTextString(aspectoSeleccion.getCosto());
								rowAspecto.createCell(3).setCellValue(text);

								rowIndex++;
							}
						}
					}
				}
			}
		}
		// cuando se termina de recorrer la lista se escribe el archivo en el
		// libro y se muestra en pantalla
		book.write(newFile);
		newFile.close();
		// muestra el archivo en pantalla
		showFile(rute);
	}

	/**
	 * m�todo para guardar el archivo de exportaci�n, se guarda con la extensi�n
	 * .pdf
	 * 
	 * @param rute
	 *            -- directorio para guardar el archivo
	 * @param data
	 *            -- lista de partidas para escribir en el archivo
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static void saveFilePDF(String rute, LinkedList<PartidaSelection> data) throws DocumentException, IOException {

		// creaci�n del archivo
		OutputStream newFile = new FileOutputStream(rute);
		// craci�n del documento por medio de itext
		Document document = new Document(PageSize.LETTER.rotate());
		PdfWriter.getInstance(document, newFile);

		document.open();
		// encabezado del archivo
		Paragraph paragraph = new Paragraph("Expotaci�n de Cat�logo de Conceptos  SYSGerencial");
		paragraph.setLeading(25);
		paragraph.setSpacingAfter(25);
		paragraph.setSpacingBefore(25);
		paragraph.setAlignment(Element.ALIGN_CENTER);
		paragraph.setIndentationLeft(25);
		paragraph.setIndentationRight(25);

		document.add(paragraph);
		// creaci�n de la tabla para escribir la informaci�n de la partida
		PdfPTable table = createTablePDF(data);
		// se agrega la tabla al archivo
		document.add(table);
		// se cierra el archivo
		document.close();
		// se muestra el archivo en pantalla
		showFile(rute);
	}

	/**
	 * m�todo para crear una tabla con el contenido de las partidas
	 * seleccionadas para la exportaci�n en PDF
	 * 
	 * @param data
	 *            -- lista de partidas a exportar
	 * @return
	 * @throws DocumentException
	 */
	public static PdfPTable createTablePDF(LinkedList<PartidaSelection> data) throws DocumentException {
		// se definer una tabla con 6 columnas
		PdfPTable table = new PdfPTable(6);
		table.setWidthPercentage(500 / 5.23f);
		table.setWidths(new float[] { 15f, 15f, 40f, 10f, 10f, 40f });

		PdfPCell cell;
		// creaci�n de los titulos para las columnas
		cell = new PdfPCell(new Phrase("Clave P�blica"));
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Clave Privada"));
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Descripci�n"));
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Unidad"));
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Costo"));
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Descripci�n Completa"));
		table.addCell(cell);

		// para cada partida en la lista
		for (PartidaSelection partidaSelection : data) {
			// se verifica que este seleccionada la partida
			if (partidaSelection.isSelect()) {
				// si esta seleccionada se agrega una fila para el nombre
				cell = new PdfPCell(new Phrase(partidaSelection.getNombre()));
				cell.setColspan(6);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				// para cada concepto en la lista de conceptos de la partida
				for (ConceptoSelection conceptoSelection : partidaSelection.getSubsConceptos()) {
					// se verifica si esta seleccionado el concepto
					if (conceptoSelection.isSelect()) {
						// si esta seleccionado se agrega una fila para el
						// nombre
						cell = new PdfPCell(new Phrase(""));
						table.addCell(cell);
						cell = new PdfPCell(new Phrase(conceptoSelection.getNombre()));
						cell.setColspan(5);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell);
						// para cada desglose de concepto en la lista de
						// desgloses de concepto en el concepto
						for (AspectoSelection aspectoSelection : conceptoSelection.getSubsAspectos()) {
							// se verifica si esta seleccionado
							if (aspectoSelection.isSelect()) {
								// si esta seleccionado se agrega una fila con
								// la informaci�n del desglose de concepto
								cell = new PdfPCell(new Phrase(aspectoSelection.getClave()));
								table.addCell(cell);

								cell = new PdfPCell(new Phrase(aspectoSelection.getClave_privada()));
								table.addCell(cell);

								cell = new PdfPCell(new Phrase(aspectoSelection.getDescripcion()));
								table.addCell(cell);

								cell = new PdfPCell(new Phrase(aspectoSelection.getUnidad()));
								table.addCell(cell);

								cell = new PdfPCell(new Phrase(aspectoSelection.getCosto()));
								table.addCell(cell);

								cell = new PdfPCell(new Phrase(aspectoSelection.getDescripcion_Completa()));
								table.addCell(cell);
							}
						}
					}
				}
			}
		}
		// se regresa la tabla con la informaci�n de las partidas y conceptos
		// seleccionados
		return table;
	}

	/**
	 * m�todo para mostrar el archivo creado en pantalla
	 * 
	 * @param rute
	 * @throws IOException
	 */
	public static void showFile(String rute) throws IOException {
		File file = new File(rute);
		Desktop.getDesktop().open(file);
	}

}
