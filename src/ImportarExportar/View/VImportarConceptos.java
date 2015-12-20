package ImportarExportar.View;

import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ImportarExportar.Controller.CImportacionConcepos;
import Model.Entity.AspectoSelection;
import Model.Entity.ConceptoSelection;
import Model.Entity.PartidaSelection;
import Model.EntityCRUD.Exceptions.DuplicateKey;
import Model.EntityCRUD.Exceptions.ErrorConnectionDB;
import Model.EntityCRUD.Exceptions.ForeingKeyReference;
import Model.EntityCRUD.Exceptions.ImportarExportar.ErrorCreateSavePoint;
import Model.EntityCRUD.Exceptions.ImportarExportar.IncompleteInformationDesglose;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import java.awt.event.MouseEvent;

/**
 * Clase para visualizar la Ventana de Importaciones de Conceptos.
 * 
 * @author Luis Angel Herández Lázaro
 *
 */
public class VImportarConceptos extends JInternalFrame {

	/**
	 * Variables de componentes dentro de la Ventana de Importaciones
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(VImportarConceptos.class);

	private JLabel lblSeleccionarArchivo;
	private JLabel lblPartidasPresupestales;
	private JLabel lblConceptosEncontrados;
	private JLabel lblDesgloseDeConceptos;

	private JButton btnSearchFile;
	private JButton btnImportarConceptosA;

	private JScrollPane scrollPartidas;
	private JScrollPane scrollConceptos;
	private JScrollPane scrollAspectos;

	private JTable tabPartidas;
	private TableModelPartidaSelection modelPartida;
	private JTable tabConceptos;
	private TableModelConceptoSelection modelConcepto;
	private JTable tabAspectos;
	private TableModelDesgloseAspectoSelection modelAspecto;

	private LinkedList<PartidaSelection> listPartidasInput;

	private JTextField txtRute;

	/**
	 * Create the frame.
	 */
	public VImportarConceptos() {
		super("Importación de Catálogo de Conceptos", false, true, false, true);
		setVisible(true);
		setBounds(100, 100, 870, 559);
		getContentPane().setLayout(null);

		/**
		 * Sección de etiquetas
		 */
		lblSeleccionarArchivo = new JLabel("Seleccionar archivo para importar conceptos:");
		lblSeleccionarArchivo.setBounds(41, 12, 247, 14);
		getContentPane().add(lblSeleccionarArchivo);

		lblPartidasPresupestales = new JLabel("Partidas presupestales encontradas");
		lblPartidasPresupestales.setBounds(330, 11, 209, 16);
		getContentPane().add(lblPartidasPresupestales);

		lblConceptosEncontrados = new JLabel("Conceptos encontrados");
		lblConceptosEncontrados.setBounds(594, 11, 140, 16);
		getContentPane().add(lblConceptosEncontrados);

		lblDesgloseDeConceptos = new JLabel("Desglose de conceptos encontrados");
		lblDesgloseDeConceptos.setBounds(277, 212, 209, 16);
		getContentPane().add(lblDesgloseDeConceptos);

		txtRute = new JTextField();
		txtRute.setBounds(10, 92, 280, 25);
		getContentPane().add(txtRute);

		/**
		 * Sección de Botones
		 */
		btnSearchFile = new JButton("Seleccionar Archivo...");
		btnSearchFile.addActionListener(new ActionListener() {
			/**
			 * método para seleccionar el archivo de importaciones de excel
			 */
			public void actionPerformed(ActionEvent arg0) {
				File fileInput = getSelectedFile();
				txtRute.setText("");
				try {
					txtRute.setText(fileInput.getAbsolutePath().toString());
				} catch (Exception e) {
					log.error(e);
				}
				// verificación de la extensión del archivo de importaciones que
				// sea un .xls o .xlsx
				if (CImportacionConcepos.checkExtensionFile(fileInput)) {
					listPartidasInput = new LinkedList<PartidaSelection>();
					try {
						// recuperación de la información del archivo (partidas
						// , conceptos y desgloses
						listPartidasInput = CImportacionConcepos.findPartidasFileInput(fileInput);
						FillTableModelPartidas();
					}catch (IncompleteInformationDesglose eDesglose) {
						JOptionPane.showMessageDialog(null, eDesglose.getMessage());
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "Ha ocurrido un error inesperado al recuperar la información del archivo");
						log.error(e);
					}
				} else {
					showMessageErrorExtensionFile();
				}

			}
		});
		btnSearchFile.setBounds(10, 38, 280, 35);
		getContentPane().add(btnSearchFile);

		btnImportarConceptosA = new JButton("Importar conceptos a la base de datos");
		btnImportarConceptosA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SaveInformation();
			}
		});
		btnImportarConceptosA.setBounds(10, 121, 280, 35);
		getContentPane().add(btnImportarConceptosA);

		/**
		 * Sección de Tablas, acompañadas por su Scroll
		 */
		scrollPartidas = new JScrollPane();
		scrollPartidas.setBounds(313, 30, 247, 169);
		getContentPane().add(scrollPartidas);

		modelPartida = new TableModelPartidaSelection();
		tabPartidas = new JTable(modelPartida);
		tabPartidas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (tabPartidas.getColumnModel().getSelectionModel().getLeadSelectionIndex() == 0) {
					listPartidasInput.get(tabPartidas.getSelectionModel().getLeadSelectionIndex()).setSelect((boolean) tabPartidas.getValueAt(tabPartidas.getSelectionModel().getLeadSelectionIndex(), 0));
					setValueConceptos(tabPartidas.getSelectionModel().getLeadSelectionIndex());
				}
				fillTableModelConcepto(tabPartidas.getSelectionModel().getLeadSelectionIndex());
			}
		});

		scrollPartidas.setViewportView(tabPartidas);

		scrollConceptos = new JScrollPane();
		scrollConceptos.setBounds(572, 30, 247, 169);
		getContentPane().add(scrollConceptos);

		modelConcepto = new TableModelConceptoSelection();
		tabConceptos = new JTable(modelConcepto);
		tabConceptos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (tabConceptos.getColumnModel().getSelectionModel().getLeadSelectionIndex() == 0) {
					listPartidasInput.get(tabPartidas.getSelectionModel().getLeadSelectionIndex()).getSubConcepto(tabConceptos.getSelectionModel().getLeadSelectionIndex()).setSelect((boolean) tabConceptos.getValueAt(tabConceptos.getSelectionModel().getLeadSelectionIndex(), 0));
					setValueAspectos(listPartidasInput.get(tabPartidas.getSelectionModel().getLeadSelectionIndex()).getSubConcepto(tabConceptos.getSelectionModel().getLeadSelectionIndex()));
					tabPartidas.setValueAt(true, tabPartidas.getSelectionModel().getLeadSelectionIndex(), 0);
					listPartidasInput.get(tabPartidas.getSelectionModel().getLeadSelectionIndex()).setSelect(true);
				}
				fillTableModelAspectos(tabPartidas.getSelectionModel().getLeadSelectionIndex(), tabConceptos.getSelectionModel().getLeadSelectionIndex());
			}
		});
		scrollConceptos.setViewportView(tabConceptos);

		scrollAspectos = new JScrollPane();
		scrollAspectos.setBounds(10, 230, 842, 287);
		getContentPane().add(scrollAspectos);

		modelAspecto = new TableModelDesgloseAspectoSelection();
		tabAspectos = new JTable(modelAspecto);
		scrollAspectos.setViewportView(tabAspectos);

		JLabel lblNamefile = new JLabel("Ubicaci\u00F3n del Archivo:");
		lblNamefile.setBounds(10, 75, 129, 14);
		getContentPane().add(lblNamefile);

		JButton btnVerformato = new JButton("Ver Ejemplo Estructura del Archivo");
		btnVerformato.setBounds(10, 166, 280, 35);
		getContentPane().add(btnVerformato);
		
		tabPartidas.removeColumn(tabPartidas.getColumnModel().getColumn(0));
		tabConceptos.removeColumn(tabConceptos.getColumnModel().getColumn(0));
		tabAspectos.removeColumn(tabAspectos.getColumnModel().getColumn(0));

	}

	public File getSelectedFile() {
		// operaciones para abrir archivo con jchoose file
		JFileChooser fileInput = new JFileChooser();

		FileFilter filterXls = new FileNameExtensionFilter("Libro de Excel 97-2003", "xls");
		FileFilter filterXlsx = new FileNameExtensionFilter("Libro de Excel", "xlsx");

		fileInput.addChoosableFileFilter(filterXls);
		fileInput.addChoosableFileFilter(filterXlsx);

		fileInput.setFileFilter(filterXls);
		fileInput.setDialogTitle("Seleccionar Archivo de Catálogo de Conceptos");
		fileInput.setAcceptAllFileFilterUsed(false);
		fileInput.setFileSelectionMode(JFileChooser.FILES_ONLY);

		int returnValue = fileInput.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			return fileInput.getSelectedFile();
		}
		return null;
	}

	public void FillTableModelPartidas() {
		modelPartida.clear();
		for (PartidaSelection partidaSelection : listPartidasInput) {
			modelPartida.addPartida(partidaSelection);
		}
		tabPartidas.changeSelection(0, 1, false, false);
		fillTableModelConcepto(tabPartidas.getSelectionModel().getLeadSelectionIndex());
	}

	public void fillTableModelConcepto(int index) {
		// llenar la tabla con la informaicon de la lista
		modelConcepto.clear();
		tabConceptos.repaint();
		for (ConceptoSelection conceptoSelection : listPartidasInput.get(index).getSubsConceptos()) {
			modelConcepto.addConcepto(conceptoSelection);
		}
		tabConceptos.changeSelection(0, 1, false, false);
		fillTableModelAspectos(index, tabConceptos.getSelectionModel().getLeadSelectionIndex());

	}

	public void fillTableModelAspectos(int indexPartida, int indexConcepto) {
		// llenar la tabla con la informacion de la lista
		modelAspecto.clear();
		tabAspectos.repaint();
		for (AspectoSelection aspectoSelection : listPartidasInput.get(indexPartida).getSubConcepto(indexConcepto).getSubsAspectos()) {
			modelAspecto.addAspecto(aspectoSelection);
		}
		tabAspectos.changeSelection(0, 1, false, false);
	}

	public void setValueConceptos(int index) {
		for (ConceptoSelection conceptoSelection : listPartidasInput.get(index).getSubsConceptos()) {
			conceptoSelection.setSelect((boolean) tabPartidas.getValueAt(tabPartidas.getSelectionModel().getLeadSelectionIndex(), 0));
			setValueAspectos(conceptoSelection);
		}
	}

	public void setValueAspectos(ConceptoSelection index) {
		for (AspectoSelection aspectoSelection : index.getSubsAspectos()) {
			aspectoSelection.setSelect((boolean) tabConceptos.getValueAt(tabConceptos.getSelectionModel().getLeadSelectionIndex(), 0));
		}
	}

	public void SaveInformation() {
		try {
			if (CImportacionConcepos.SavePartidas(listPartidasInput)) {
				JOptionPane.showMessageDialog(null, "Conceptos Importados con éxtio");
			}
		} catch (ErrorCreateSavePoint eSavePoint) {
			JOptionPane.showMessageDialog(null, eSavePoint.getMessage());
			log.error(eSavePoint, eSavePoint);

		} catch (DuplicateKey eDuplicateKey) {
			JOptionPane.showMessageDialog(null, eDuplicateKey.getMessage());
		} catch (ForeingKeyReference e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (MySQLIntegrityConstraintViolationException eIntegrity) {
			if (eIntegrity.toString().contains("for key 'clave'")) {
				DuplicateKey eDuplicateKey = new DuplicateKey("Clave", "!!!");
				JOptionPane.showMessageDialog(null, "El Uno de los aspectos en el archivo ya se encuentra registrado en la base de datos");
				log.error(eDuplicateKey, eIntegrity);
			}
		} catch (SQLException e) {
			ErrorConnectionDB eConnectionDB = new ErrorConnectionDB();
			log.error(eConnectionDB,e);
			JOptionPane.showMessageDialog(null, eConnectionDB.getMessage());
		}
	}

	public void showMessageErrorExtensionFile() {
		JOptionPane.showMessageDialog(null, "El archivo seleccionado no es un archivo con la extensión \".xls\" o \".xlsx\" ... seleccione un archivo que coincida con estas extensiones de archivo");
	}

}