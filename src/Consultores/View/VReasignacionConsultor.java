package Consultores.View;

import java.sql.SQLException;
import java.util.LinkedList;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;

import Consultores.Controller.CReasignacion;
import Model.Entity.HistorialConsultor;
import Model.EntityCRUD.Exceptions.ErrorConnectionDB;
import ObjetosSerializables.Consultor;
import ObjetosSerializables.Proyecto;

import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JTable;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

/**
 * Clase para la creaci�n de la ventana para reasignar consultores
 * 
 * @author Luis Angel Hern�ndez L�zaro
 *
 */
public class VReasignacionConsultor extends JInternalFrame {
	/**
	 * elementos para la creaci�n de la ventana
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(VReasignacionConsultor.class);
	private JComboBox<Consultor> cbConsultores;
	private JComboBox<Consultor> cbConsultorNuevoConsultor;
	private LinkedList<Consultor> listCbConsultors;
	private LinkedList<Proyecto> listTabProyectos;
	private JTable tabProyectos;

	private LinkedList<Proyecto> listCbProyectos;
	private LinkedList<Consultor> listTabConsultores;
	private JButton btnSaveProyecto;
	private JButton btnSaveConsultor;
	private JTable tabConsultor;

	private JComboBox<Proyecto> cbProyectos;
	private JComboBox<Consultor> cbConsultorNuevoProyecto;

	/**
	 * Create the frame.
	 */
	public VReasignacionConsultor() {
		super("Reasignaci�n de Proyectos", false, true, false, true);
		setVisible(true);
		setBounds(100, 100, 440, 538);
		getContentPane().setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 428, 501);
		getContentPane().add(tabbedPane);

		JPanel pBusquedaProyecto = new JPanel();
		tabbedPane.addTab("B�squeda por Proyecto", null, pBusquedaProyecto, null);
		pBusquedaProyecto.setLayout(null);

		btnSaveProyecto = new JButton("Guardar Cambios");
		btnSaveProyecto.addActionListener(new ActionListener() {
			/**
			 * llamada a los m�todos del controlador para guardar la
			 * reasignaci�n del consultor
			 */
			public void actionPerformed(ActionEvent e) {
				// creacion de un nuevo registro de historial
				// este objeto se llena con la informaci�n en la secci�n de
				// b�squeda por proyecto
				HistorialConsultor historial = new HistorialConsultor();
				historial.setProyecto(listCbProyectos.get(cbProyectos.getSelectedIndex()));
				historial.setIdConsultorAnterior(listTabConsultores.get(tabConsultor.getSelectedRow()));
				historial.setIdConsultorNuevo(listCbConsultors.get(cbConsultorNuevoProyecto.getSelectedIndex()));
				try {
					// llamada al controlador para guardar la informaci�n
					if (SaveReasignacion(historial) > 0) {
						showMessageSaveHistoryProyecto();
					}
				} catch (SQLException e1) {
					// si ocurre un error en la base de datos se registra en un
					// log
					showMessageErrorConnectDB();
					log.error(e1);

				}
			}
		});
		btnSaveProyecto.setBounds(200, 425, 225, 40);
		pBusquedaProyecto.add(btnSaveProyecto);

		JScrollPane scrollConsultores = new JScrollPane();
		scrollConsultores.setBounds(5, 70, 420, 305);
		pBusquedaProyecto.add(scrollConsultores);

		tabConsultor = new JTable(); // creacion del modelo de la table de
										// consultores
		tabConsultor.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Nombre del Consultor" }) {
			private static final long serialVersionUID = 1L;
			Class<?>[] columnTypes = new Class[] { String.class };

			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		tabConsultor.getColumnModel().getColumn(0).setPreferredWidth(245);
		scrollConsultores.setViewportView(tabConsultor);

		JLabel lblListaDeConsultores = new JLabel("Lista de Consultores Colaborando en este Proyecto");
		lblListaDeConsultores.setBounds(66, 43, 300, 16);
		pBusquedaProyecto.add(lblListaDeConsultores);

		cbProyectos = new JComboBox<Proyecto>();
		cbProyectos.addActionListener(new ActionListener() {
			/**
			 * m�todo para desplegar los consultores dependiendo el proyecto
			 * seleccionado
			 */
			public void actionPerformed(ActionEvent arg0) {
				fillTableModelReasignacionConsultores(listCbProyectos.get(cbProyectos.getSelectedIndex()).getIdproyecto());
			}
		});
		cbProyectos.setBounds(150, 5, 275, 30);
		pBusquedaProyecto.add(cbProyectos);

		JLabel lblNombreDelProyecto = new JLabel("Nombre del Proyecto:");
		lblNombreDelProyecto.setBounds(5, 15, 140, 14);
		pBusquedaProyecto.add(lblNombreDelProyecto);

		JLabel label_2 = new JLabel("Nuevo Consultor:");
		label_2.setBounds(30, 390, 110, 14);
		pBusquedaProyecto.add(label_2);

		cbConsultorNuevoProyecto = new JComboBox<Consultor>();
		cbConsultorNuevoProyecto.setBounds(130, 380, 295, 30);
		pBusquedaProyecto.add(cbConsultorNuevoProyecto);
		
		JButton btnShowHistoryProyecto = new JButton("Ver Historial del Proyecto");
		btnShowHistoryProyecto.setBounds(5, 425, 183, 40);
		pBusquedaProyecto.add(btnShowHistoryProyecto);

		JPanel pBusquedaConsultor = new JPanel();
		tabbedPane.addTab("B�squeda por Consultor", null, pBusquedaConsultor, null);
		pBusquedaConsultor.setLayout(null);

		btnSaveConsultor = new JButton("Guardar Cambios");
		btnSaveConsultor.addActionListener(new ActionListener() {
			/**
			 * llamada a los m�todos del controlador para guardar la
			 * reasignaci�n del consultor
			 */
			public void actionPerformed(ActionEvent e) {
				
				//VALIDAR SELECCIONES
				
				// creacion de un nuevo registro de historial
				// este objeto se llena con la informaci�n en la secci�n de
				// b�squeda por consultor
				HistorialConsultor historial = new HistorialConsultor();
				historial.setProyecto(listTabProyectos.get(tabProyectos.getSelectedRow()));
				historial.setIdConsultorAnterior(listCbConsultors.get(cbConsultores.getSelectedIndex()));
				historial.setIdConsultorNuevo(listCbConsultors.get(cbConsultorNuevoConsultor.getSelectedIndex()));
				try {
					// llamada al controlador para guardar la informaci�n
					if (SaveReasignacion(historial) > 0) {
						showMessageSaveHistoryConsultor();
					}
				} catch (SQLException e1) {
					// si ocurre un error en la base de datos se registra en un
					// log
					showMessageErrorConnectDB();
					log.error(e1);
				}
			}
		});
		btnSaveConsultor.setBounds(200, 425, 225, 40);
		pBusquedaConsultor.add(btnSaveConsultor);

		JScrollPane scrollProyectos = new JScrollPane();
		scrollProyectos.setBounds(5, 70, 420, 305);
		pBusquedaConsultor.add(scrollProyectos);

		tabProyectos = new JTable();// modelo para la tabla de proyectos
		tabProyectos.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Nombre del Proyecto" }) {
			private static final long serialVersionUID = 1L;
			Class<?>[] columnTypes = new Class[] { String.class };

			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		tabProyectos.getColumnModel().getColumn(0).setResizable(false);
		scrollProyectos.setViewportView(tabProyectos);

		JLabel lblListaDeProyectos = new JLabel("Lista de Proyectos donde Colabora");
		lblListaDeProyectos.setBounds(10, 42, 199, 16);
		pBusquedaConsultor.add(lblListaDeProyectos);

		cbConsultores = new JComboBox<Consultor>();
		cbConsultores.addActionListener(new ActionListener() {
			/**
			 * m�todo para desplegar los proyectos dependiendo del consultor que
			 * esta seleccionado
			 */
			public void actionPerformed(ActionEvent arg0) {
				fillTableModelReasignacionProyectos(listCbConsultors.get(cbConsultores.getSelectedIndex()).getIdconsultor());
			}
		});
		cbConsultores.setBounds(150, 5, 275, 30);
		pBusquedaConsultor.add(cbConsultores);

		JLabel lblNombreDelConsultor = new JLabel("Nombre del Consultor:");
		lblNombreDelConsultor.setBounds(5, 15, 140, 14);
		pBusquedaConsultor.add(lblNombreDelConsultor);

		JLabel lblNuevoconsultor = new JLabel("Nuevo Consultor:");
		lblNuevoconsultor.setBounds(30, 390, 110, 14);
		pBusquedaConsultor.add(lblNuevoconsultor);

		cbConsultorNuevoConsultor = new JComboBox<Consultor>();
		cbConsultorNuevoConsultor.setBounds(130, 380, 295, 30);
		pBusquedaConsultor.add(cbConsultorNuevoConsultor);

		displayInformation();
	}

	/**
	 * 
	 */
	public void displayInformation() {
		// obtener los proyectos de la base de datos
		try {
			listCbProyectos = CReasignacion.findProyectos();
			for (Proyecto proyecto : listCbProyectos) {
				cbProyectos.addItem(proyecto);
			}
		} catch (SQLException e) {
			showMessageErrorConnectDB();
			log.error(e);
		}

		// obtener los consultores de la base de datos
		try {
			listCbConsultors = CReasignacion.findConsultors();
			for (Consultor consultor : listCbConsultors) {
				cbConsultores.addItem(consultor);
				cbConsultorNuevoConsultor.addItem(consultor);
				cbConsultorNuevoProyecto.addItem(consultor);
			}
		} catch (SQLException e) {
			showMessageErrorConnectDB();
			log.error(e);
		}
	}

	/**
	 * 
	 * @param idConsultor
	 */
	public void fillTableModelReasignacionProyectos(int idConsultor) {
		// recuperaci�n de los proyectos dependiendo del consultor seleccionado
		try {
			listTabProyectos = CReasignacion.findProyecto(idConsultor);
		} catch (SQLException e) {
			// si ocurre un error se notifica al usuario y se guarda en la base
			// de datos
			showMessageErrorConnectDB();
			log.error(e);
		}
		((DefaultTableModel) tabProyectos.getModel()).setNumRows(0);
		((DefaultTableModel) tabProyectos.getModel()).setRowCount(0);
		tabProyectos.repaint();
		if (!listTabProyectos.isEmpty()) {
			// para cada proyecto agregar al modelo de la tabla
			for (Proyecto proyecto : listTabProyectos) {
				((DefaultTableModel) tabProyectos.getModel()).addRow(new Object[] { proyecto.toString() });
			}
			// si existen proyectos se habilita el boton para guardar la
			// reasignaci�n
			btnSaveConsultor.setEnabled(true);
		} else {
			// sino existen proyectos no se habilita el boton para guardar la
			// reasignaci�n
			btnSaveConsultor.setEnabled(false);
		}
	}

	/**
	 * m�todo para desplegar los consultores en la tabla asignada a los
	 * consultores
	 * 
	 * @param idProyecto
	 */
	public void fillTableModelReasignacionConsultores(int idProyecto) {
		// recuperacion de los consultores
		try {
			listTabConsultores = CReasignacion.findConsultor(idProyecto);
		} catch (SQLException e) {
			showMessageErrorConnectDB();
			log.error(e);
		}
		((DefaultTableModel) tabConsultor.getModel()).setNumRows(0);
		((DefaultTableModel) tabConsultor.getModel()).setRowCount(0);
		tabConsultor.repaint();
		if (!listTabConsultores.isEmpty()) {
			// para cada consultor agregar al modelo de la tabla
			for (Consultor consultor : listTabConsultores) {
				((DefaultTableModel) tabConsultor.getModel()).addRow(new Object[] { consultor.toString() });
			}
			// si existen consultores se habilita el boton para guardar la
			// reasignaci�n
			btnSaveProyecto.setEnabled(true);
		} else {
			// si no existen consultores no se habilita el boton para guardar la
			// reasignaci�n
			btnSaveProyecto.setEnabled(false);
		}
	}

	/**
	 * m�todo para guardar el historial en la base de datos
	 * 
	 * @param historial
	 *            - objeto a guardar en la base de datos
	 * @return verificaci�n de insert en la base de datos
	 * @throws SQLException
	 */
	public int SaveReasignacion(HistorialConsultor historial) throws SQLException {
		return CReasignacion.save(historial);
	}

	/**
	 * metodo para mostrar el mensaje de historial guardado en la secci�n de
	 * consultor actualiza el modelo de la tabla de la secci�n
	 */
	public void showMessageSaveHistoryConsultor() {
		JOptionPane.showMessageDialog(null, "Reasignaci�n de Proyecto registrada en el sistema");
		fillTableModelReasignacionProyectos(listCbConsultors.get(cbConsultores.getSelectedIndex()).getIdconsultor());
	}

	/**
	 * m�todo par amostrar el mensaje de historial guardado en la seccion de
	 * proyectos+ actualiza el modelo de la tabla de la secci�n
	 */
	public void showMessageSaveHistoryProyecto() {
		JOptionPane.showMessageDialog(null, "Reasignaci�n de Proyecto registrada en el sistema");
		fillTableModelReasignacionConsultores(listCbProyectos.get(cbProyectos.getSelectedIndex()).getIdproyecto());
	}

	/**
	 * m�todo para mostrar el mensaje de error de conexi�n en la base de datos
	 */
	public void showMessageErrorConnectDB() {
		ErrorConnectionDB eConnectionDB = new ErrorConnectionDB();
		log.error(eConnectionDB);
		JOptionPane.showMessageDialog(null, eConnectionDB.getMessage());
	}

}
