import AdministrarPlantillas.View.VAdministrarPlantillas;
import Consultores.View.VReasignacionConsultor;
import EstimacionAceros.ViewEstimacionAceros;
import ImportarExportar.View.VExportarConceptos;
import ImportarExportar.View.VImportarConceptos;
import MetodosRemotos.Metodos;
import Views.BaseDeDatosView;
import Views.ConsultoresView;
import controllers.ConsultoresController;
import controllers.ControlEstimacionController;
import controllers.PresupuestoController;
import controllers.ProyectosController;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Clase de Men� Principal para la ejecuci�n del Programa SYSGerencial
 * @author Rivera Pablo
 * @colaboraci�n luiiis lazaro
 */
public class Principal extends JFrame {

	/**
	 * Clase Principal para el manejo de los men�s para el usuario
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * creacion de escritorio para las ventanas mdi
	 */
	private JDesktopPane escritorio2;
	private JMenuBar Menu;

	/**
	 * creacion de los men�s para las opciones del usuario que ingrese al sistema
	 * rControlar --- menu para realizar el seguimiento de las estimaciones creadas
	 * rProyectos --- menu para registrar un nuevo proyecto
	 * rCambiar --- menu para cambiar de usuario
	 * rConsultor -- menu para registrar consultores
	 * rCatalogo --- menu para administrar el cat�logo
	 * rPresupuestar --- menu para realizar la estimaci�n inicial del proyecto
	 * inicio --- variable para llamar al Internal Frame de inicio de sesi�n
	 * pre --- variable para llamar al Internal Frame de estimaci�n inicial
	 * cat --- variable para llamar al Internal Frame de cat�logo de conceptos
	 * me2 --- variable para llamar al Internal Frame de  control de estimaci�n
	 * proyec --- variable para llamar al Internal Frame de administraci�n de proyectos
	 * consultores --- variable para llamar al Internal Frame de administraci�n de consultores
	 */
	private JMenuItem rControlar;
	private JMenuItem rProyectos;
	private JMenuItem rCambiar;
	private JMenu mCuenta;
	private JMenuItem rConsultores;
	private JMenu mConsultores;
	private JMenu mProyectos;
	private JLabel texto;
	private JMenuItem rCatalogo;
	private JMenu mCatalogo;
	private JMenuItem rPresupuestar;
	private JMenu mControlar;
	private JMenu mPresupuestar;
	private Logeo inicio;
	private Npresupuesto pre;
	private Mcatalago cat;
	private Meterdos me2;
	private Proyectos proyec;
	private Consultores consultores;

	/**
	 * Nuevas Funciones del sistema;
	 */
	
	private VAdministrarPlantillas vAdminPlantillas;
	private ViewEstimacionAceros vEstimacionAceros;
	private VExportarConceptos vExportarCatalogo;
	private VImportarConceptos vImportarConceptos;
	private VReasignacionConsultor vReasignacionProyectos;
	private JMenuItem mntmRegistroDeConsultoress;
	private JMenuItem mntmPresupuestoIniciall;
	
	/**
	 * Contructor de la clase principal
	 * 
	 * @param conexion
	 *            para las operaciones a la base de datos (centralizada, mejora
	 *            para el cte-servidor)
	 */
	public Principal(Metodos conexion) {
		super();
		// Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		initGUI(conexion);
	}

	/**
	 * CloseOptions Procedimiento que se encarga de cerrar las opciones que se
	 * encuentren abiertas por el usuario en el sistema.
	 */
	private void closeOptions() {
		try { consultores.dispose(); } catch (Exception e) {}
		try {cat.dispose();} catch (Exception e) {}
		try {proyec.dispose();} catch (Exception e) {}
		try {pre.dispose();} catch (Exception e) {}
		try {me2.dispose();} catch (Exception e) {}
		
		try { vAdminPlantillas.dispose(); } catch (Exception e) {}
		try { vEstimacionAceros.dispose();} catch (Exception e) {}
		try { vExportarCatalogo.dispose();} catch (Exception e) {}
		try { vImportarConceptos.dispose();} catch (Exception e) {}
		try { vReasignacionProyectos.dispose();} catch (Exception e) {}
		
	}

	/**
	 * procedimiento para la creaci�n de la ventana principal men�
	 * 
	 * @param conexion
	 *            para las operaciones a la base de datos (centralizada, mejora
	 *            para el cte-servidor)
	 * 
	 */
	private void initGUI(final Metodos conexion) {
		try {
			this.setTitle("Sistema Generadores de obra");
			Menu = new JMenuBar();
			setJMenuBar(Menu);
			this.addWindowListener(new WindowAdapter() {
				/**
				 * finalizar aplicaci�n 
				 * (non-Javadoc)
				 * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
				 */
				public void windowClosing(WindowEvent e) {
					conexion.cerrar();
					System.exit(0);
				}
			});

			mConsultores = new JMenu();
			Menu.add(mConsultores);
			mConsultores.setText("Consultores");
			mConsultores.setEnabled(false);
			{
				mntmRegistroDeConsultoress = new JMenuItem("Registro de Consultoress");
				mntmRegistroDeConsultoress.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						escritorio2.add(new ConsultoresController().getVista());
					}
				});
				mConsultores.add(mntmRegistroDeConsultoress);
			}

			rConsultores = new JMenuItem();
			mConsultores.add(rConsultores);
			rConsultores.setText("Registro de Consultores");
			rConsultores.addActionListener(new ActionListener() {
				/**
				 * visualizaci�n de la ventana de Consultores (usuarios)
				 * 
				 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
				 */
				public void actionPerformed(ActionEvent e) {
					if (consultores == null) {
						consultores = new Consultores(conexion);
						escritorio2.add(consultores);
						
					} else {
						if (consultores.isClosed()) {
							consultores = new Consultores(conexion);
							escritorio2.add(consultores);
						}
					}
				}
			});

			mCatalogo = new JMenu();
			Menu.add(mCatalogo);
			mCatalogo.setText("Cat�logos");
			mCatalogo.setEnabled(false);
			rCatalogo = new JMenuItem();
			mCatalogo.add(rCatalogo);
			rCatalogo.setText("Edici\u00F3n de Cat\u00E1logo");
			rCatalogo.addActionListener(new ActionListener() {

				/**
				 * Visualizaci�n de la ventana de cat�logos para los
				 * 
				 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
				 */
				public void actionPerformed(ActionEvent e) {
					if (cat == null) {
						cat = new Mcatalago(conexion);
						escritorio2.add(cat);
					} else {
						if (cat.isClosed()) {
							cat = new Mcatalago(conexion);
							escritorio2.add(cat);
						}
					}
				}
			});

			mProyectos = new JMenu();
			Menu.add(mProyectos);
			mProyectos.setText("Proyectos");
			mProyectos.setEnabled(false);
			
			JMenuItem mntmRegistroDeProyectoss = new JMenuItem("Registro de Proyectoss");
			mntmRegistroDeProyectoss.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					escritorio2.add(new ProyectosController(escritorio2,conexion).getVista());
				}
			});
			mProyectos.add(mntmRegistroDeProyectoss);

			rProyectos = new JMenuItem();
			mProyectos.add(rProyectos);
			rProyectos.setText("Registro de Proyectos");
			rProyectos.addActionListener(new ActionListener() {

				/**
				 * Visualizaci�n de la ventana de Registro de Nuevos Proyectos
				 * 
				 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
				 */
				public void actionPerformed(ActionEvent e) {
					if (proyec == null) {
						proyec = new Proyectos(escritorio2, conexion);
						escritorio2.add(proyec);
					} else {
						if (proyec.isClosed()) {
							proyec = new Proyectos(escritorio2, conexion);
							escritorio2.add(proyec);
						}
					}
				}
			});

			mPresupuestar = new JMenu();
			Menu.add(mPresupuestar);
			mPresupuestar.setText("Presupuestar");
			mPresupuestar.setEnabled(false);
			
			mntmPresupuestoIniciall = new JMenuItem("Presupuesto Iniciall");
			mntmPresupuestoIniciall.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					escritorio2.add(new PresupuestoController(escritorio2, conexion).getVista());
				}
			});
			mPresupuestar.add(mntmPresupuestoIniciall);
			rPresupuestar = new JMenuItem();
			mPresupuestar.add(rPresupuestar);
			rPresupuestar.setText("Presupuesto Inicial");
			rPresupuestar.addActionListener(new ActionListener() {

				/**
				 * Visualizaci�n de la ventana de presupuesto inicial
				 * 
				 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
				 */
				public void actionPerformed(ActionEvent e) {
					if (pre == null) {
						pre = new Npresupuesto(escritorio2, conexion);
						escritorio2.add(pre);
					} else {
						if (pre.isClosed()) {
							pre = new Npresupuesto(escritorio2, conexion);
							escritorio2.add(pre);
						}
					}
				}
			});

			mControlar = new JMenu();
			Menu.add(mControlar);
			mControlar.setText("Controlar");
			mControlar.setEnabled(false);

			mCuenta = new JMenu();
			Menu.add(mCuenta);
			mCuenta.setText("Cuenta");

			rCambiar = new JMenuItem();
			mCuenta.add(rCambiar);
			rCambiar.setText("Cambiar de Usuario");
			
			JMenuItem rImagenes = new JMenuItem("Personalizar Imagen");
			mCuenta.add(rImagenes);
			rCambiar.addActionListener(new ActionListener() {

				/**
				 * Visualizaci�n para la ventana de cerrar sesi�n.
				 * 
				 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
				 */
				public void actionPerformed(ActionEvent e) {
					closeOptions();
					mConsultores.setEnabled(false);
					mCatalogo.setEnabled(false);
					mProyectos.setEnabled(false);
					mPresupuestar.setEnabled(false);
					mControlar.setEnabled(false);
					if (inicio.isClosed() == true) {
						inicio = new Logeo(conexion, mConsultores, mCatalogo, mProyectos, mPresupuestar, mControlar);
						escritorio2.add(inicio);
					}
				}
			});
			
			JMenuItem mntmControlDeEstimacioness = new JMenuItem("Control de Estimacioness");
			mntmControlDeEstimacioness.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					escritorio2.add(new ControlEstimacionController(conexion,escritorio2).getVista());
				}
			});
			mControlar.add(mntmControlDeEstimacioness);

			rControlar = new JMenuItem();
			mControlar.add(rControlar);
			rControlar.setText("Control de Estimaciones");
			rControlar.addActionListener(new ActionListener() {
				/**
				 * Visualizaci�n de ventana de control de estimaciones
				 * 
				 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
				 */
				public void actionPerformed(ActionEvent e) {
					if (me2 == null) {
						me2 = new Meterdos(conexion, escritorio2);
						escritorio2.add(me2);
					} else {
						if (me2.isClosed()) {
							me2 = new Meterdos(conexion, escritorio2);
							escritorio2.add(me2);
						}
					}
				}
			});

			/**
			 * al cargar todos los elementos del men� prinicipal, se agrega una
			 * ventana para el inicio de sesi�n de los usuarios
			 */
			inicio = new Logeo(conexion, mConsultores, mCatalogo, mProyectos, mPresupuestar, mControlar);
			
			JMenuItem mntmImportarCatlogoDe = new JMenuItem("Importar Cat\u00E1logo de Conceptos");
			mntmImportarCatlogoDe.addActionListener(new ActionListener() {
				/**
				 * (non-Javadoc)
				 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
				 */
				public void actionPerformed(ActionEvent arg0) {
					if (vImportarConceptos== null) {
						vImportarConceptos= new VImportarConceptos();
						escritorio2.add(vImportarConceptos);
					} else {
						if (vImportarConceptos.isClosed()) {
							vImportarConceptos = new VImportarConceptos();
							escritorio2.add(vImportarConceptos);
						}
					}
				}
			});
			mCatalogo.add(mntmImportarCatlogoDe);
			
			JMenuItem mntmExportarCatlogoDe = new JMenuItem("Exportar Cat\u00E1logo de Conceptos");
			mntmExportarCatlogoDe.addActionListener(new ActionListener() {
				/**
				 * (non-Javadoc)
				 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
				 */
				public void actionPerformed(ActionEvent arg0) {
					if (vExportarCatalogo== null) {
						vExportarCatalogo= new VExportarConceptos();
						escritorio2.add(vExportarCatalogo);
						try {
							escritorio2.getDesktopManager().activateFrame(vExportarCatalogo);
							vExportarCatalogo.setSelected(true);
						} catch (PropertyVetoException e) {
							e.printStackTrace();
						}
					} else {
						if (vExportarCatalogo.isClosed()) {
							vExportarCatalogo = new VExportarConceptos();
							escritorio2.add(vExportarCatalogo);
						}
					}
				}
			});
			mCatalogo.add(mntmExportarCatlogoDe);
			
			JMenuItem mntmAdministrarCatlogoDe = new JMenuItem("Administrar Cat\u00E1logo de Plantillas");
			mntmAdministrarCatlogoDe.addActionListener(new ActionListener() {
				/**
				 * (non-Javadoc)
				 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
				 */
				public void actionPerformed(ActionEvent arg0) {
					if (vAdminPlantillas== null) {
						vAdminPlantillas= new VAdministrarPlantillas();
						escritorio2.add(vAdminPlantillas);
					} else {
						if (vAdminPlantillas.isClosed()) {
							vAdminPlantillas = new VAdministrarPlantillas();
							escritorio2.add(vAdminPlantillas);
						}
					}
				}
			});
			mCatalogo.add(mntmAdministrarCatlogoDe);
			
			JMenuItem mntmControlDeEstimaciones = new JMenuItem("Control de Estimaciones de Aceros");
			mControlar.add(mntmControlDeEstimaciones);
			
			JMenuItem mntmPresupuestoInicialAceros = new JMenuItem("Presupuesto Inicial Aceros");
			mntmPresupuestoInicialAceros.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (vEstimacionAceros == null) {
						vEstimacionAceros = new ViewEstimacionAceros (conexion);
						escritorio2.add(vEstimacionAceros );
					} else {
						if (vEstimacionAceros .isClosed()) {
							vEstimacionAceros  = new ViewEstimacionAceros(conexion);
							escritorio2.add(vEstimacionAceros );
						}
					}
				}
			});
			mPresupuestar.add(mntmPresupuestoInicialAceros);
			
			JMenuItem mntmReasignacionesDeProyectos = new JMenuItem("Reasignaci�n de Consultores");
			mntmReasignacionesDeProyectos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (vReasignacionProyectos== null) {
						vReasignacionProyectos= new VReasignacionConsultor();
						escritorio2.add(vReasignacionProyectos);
					} else {
						if (vReasignacionProyectos.isClosed()) {
							vReasignacionProyectos = new VReasignacionConsultor();
							escritorio2.add(vReasignacionProyectos);
						}
					}
				}
			});
			mConsultores.add(mntmReasignacionesDeProyectos);
			
			JMenu mnConfiguracin = new JMenu("Configuración");
			Menu.add(mnConfiguracin);
			
			JMenuItem mntmBaseDeDatos = new JMenuItem("Base de datos");
			mntmBaseDeDatos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					escritorio2.add(new BaseDeDatosView());
				}
			});
			mnConfiguracin.add(mntmBaseDeDatos);

			escritorio2 = new JDesktopPane();
			getContentPane().add(escritorio2, BorderLayout.CENTER);
			escritorio2.setPreferredSize(new java.awt.Dimension(492, 395));
			escritorio2.setBackground(new java.awt.Color(255, 255, 255));
			texto = new JLabel();
			escritorio2.add(inicio);
			inicio.setBounds(191, 110, 577, 333);
			escritorio2.add(texto, JLayeredPane.DEFAULT_LAYER);
			texto.setText("Sistemas y Soluciones");
			texto.setBounds(400, 476, 508, 80);
			texto.setFont(new java.awt.Font("SansSerif", 2, 48));
			this.setSize(844, 654);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}