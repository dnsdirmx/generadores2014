package Views;
/*
 * Jose Eduardo Hernandez Tapia 
 */
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.EventObject;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import Excel.Generarexcel;
import Excel.Genrargeneral;
import Manejodetablas2.ControlTableGenerador;
import Manejodetablas2.ModeloTablaGenerador;
import Manejotablas.MyTableModel;
import MetodosRemotos.Metodos;
import Model.Entity.Aspecto;
import Model.Entity.Concepto;
import Model.Entity.Consultor;
import Model.Entity.Estimacion;
import Model.Entity.Frente;
import Model.Entity.Partida;
import ObjetosSerializables.Plantilla;
import ObjetosSerializables.Rgenerador;
import Options.ComponentsUser;
import PDF.Generarpdf;
import Tablaconceptos.ModeloConceptoSeleccion;
import TablasPartidas.ControPartida;

import com.toedter.calendar.JDateChooser;

import controllers.AcerosController;
import controllers.PresupuestoController;

public class AcerosView extends JInternalFrame {
	/**
     * elementos para la creacion de la ventana 
     */
	private static final long serialVersionUID = 1L;

	private JDesktopPane escritorio;
	private JPanel Pparitdas;
	private JPanel Pconcepto;
	@SuppressWarnings("unused")
	private int indi;
	private JTable generadores;
	private JLabel efecha;
	private JButton plantilla;
	private JLabel ope;
	private JComboBox<String> Coperaciones;
	private JPanel Poperaciones;
	private JScrollPane SPartidas;
	private JComboBox<String> Cfrente;
	private JLabel Tfrente;
	private JComboBox<String> consultor;
	private JLabel jLabel1;
	private JComboBox<String> Cproyecto;
	private JLabel Econsultor;
	private JPanel Pcosultor;
	private JScrollPane Pgenerador;
	private JTable tablap;
	private JDateChooser finicial;

	private LinkedList<Model.Entity.Partida> Lpartidas = new LinkedList<Model.Entity.Partida>();
	private LinkedList<Model.Entity.Proyecto> ListaProyectos = new LinkedList<Model.Entity.Proyecto>();
	private LinkedList<Model.Entity.Consultor> ListaConsultores = new LinkedList<Model.Entity.Consultor>();
	private LinkedList<Frente> Listafrentes = new LinkedList<Frente>();
	private LinkedList<Model.Entity.Concepto> Lconceptos = new LinkedList<Model.Entity.Concepto>();
	private LinkedList<Model.Entity.Aspecto> Laspectos = new LinkedList<Model.Entity.Aspecto>();
	private LinkedList<Estimacion> Lestimacion = new LinkedList<Estimacion>();

	private Metodos cone;
	private ModeloTablaGenerador modelo2 = new ModeloTablaGenerador();
	private ControlTableGenerador control = new ControlTableGenerador(modelo2);
	private Model.Entity.Proyecto pro = new Model.Entity.Proyecto();
	private Frente fre = new Frente();
	private Model.Entity.Concepto concep = new Model.Entity.Concepto();
	private Aspecto asp2 = new Aspecto();
	private Model.Entity.Partida par = new Model.Entity.Partida();
	@SuppressWarnings("unused")
	private boolean ban = false, ban2 = false, entrouno = false;
	private int idpartida = 0;
	private int idestimacion = 0;
	private int idfrente = -1;
	private int idconsultor = -1;
	private int idproyecto = -1;
	private int idconcepto;
	private int repeticion = -1;
	private JPopupMenu pMenu;
	private JMenuItem agregar, eliminar;
	private int indice;
	private String des;
	private Lplano pla;
	@SuppressWarnings("unused")
	private Genrargeneral general;
	private Estimaciones esti;
	private GuardarEstimacion gestimacion;
	private Elegirplantilla vpla;
	//private ModeloPartida modelop = new ModeloPartida();
	private ControPartida controlp = new ControPartida();
	private String fechaestimacion = "";
	private AcerosController controlador;

	/**
	 * constructor de la clase  
	 * 
	 * @param escritorio2 -- JDesktopPane de la aplicaci�n principal
	 * @param conexion -- conexi�n a la base de datos 
	 */
	public AcerosView(JDesktopPane escritorio2, Metodos conexion, AcerosController controlador) {
		super("Control de aceros", false, true, false, true);
		// Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.controlador = controlador;
		initGUI(escritorio2, conexion);
	}

	/**
	 * inicio de la GUI
	 * 
	 * @param escritorio2 -- JDesktopPane de la aplicaci�n principal
	 * @param conexion -- conexi�n a la base de datos
	 */
	private void initGUI(final JDesktopPane escritorio2, Metodos conexion) {
		try {
			this.setPreferredSize(new java.awt.Dimension(227, 190));
			this.setBounds(0, 0, 227, 190);
			this.setPreferredSize(new java.awt.Dimension(1275, 663));
			this.setBounds(0, 0, 1275, 663);
			setVisible(true);

			this.cone = conexion;
			escritorio = new JDesktopPane();
			llenarParitdas();
			getContentPane().add(escritorio, BorderLayout.CENTER);

			Pparitdas = new JPanel();
			escritorio.add(Pparitdas, JLayeredPane.DEFAULT_LAYER);
			Pparitdas.setBounds(17, 64, 260, 275);
			Pparitdas.setBorder(new LineBorder(new java.awt.Color(0, 0, 0), 1, false));
			Pparitdas.setLayout(null);

			SPartidas = new JScrollPane();
			Pparitdas.add(SPartidas);
			SPartidas.setPreferredSize(new java.awt.Dimension(244, 222));
			SPartidas.setBounds(8, 6, 244, 222);

			tablap = new JTable(controlp.getModel());
			SPartidas.setViewportView(tablap);
			TableColumn col = tablap.getColumn("Partidas Prepuestales");
			col.setPreferredWidth(265);
			tablap.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			tablap.setEnabled(false);
			
			plantilla = new JButton();
			plantilla.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			Pparitdas.add(plantilla);
			plantilla.setText("Crear elemento");
			plantilla.setBounds(73, 234, 105, 28);
			Pconcepto = new JPanel();
			escritorio.add(Pconcepto, JLayeredPane.DEFAULT_LAYER);
			Pconcepto.setBounds(282, 66, 972, 553);
			Pconcepto.setBorder(new LineBorder(new java.awt.Color(0, 0, 0), 1, false));

			Pcosultor = new JPanel();
			escritorio.add(Pcosultor, JLayeredPane.DEFAULT_LAYER);
			Pcosultor.setBounds(284, 5, 970, 59);
			Pcosultor.setBorder(new LineBorder(new java.awt.Color(0, 0, 0), 1, false));
			Pcosultor.setLayout(null);

			jLabel1 = new JLabel();
			Pcosultor.add(jLabel1);
			jLabel1.setText("Consultor:");
			jLabel1.setBounds(220, 7, 90, 16);

			consultor = new JComboBox<String>();
			Pcosultor.add(consultor);
			llenarconsultor();
			llenarLFrentes();
			llenarLconceptos();
			llenarLAcpestosdos();
			consultor.setBounds(214, 27, 248, 25);
			consultor.addActionListener(new ActionListener() {
				/**
				 * clic en el nombre de un consultor,
				 * s�lo habilita el combo box de proyectos
				 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
				 */
				public void actionPerformed(ActionEvent e) {
					if (consultor.getSelectedIndex() > -1) {
						Model.Entity.Consultor con = (Model.Entity.Consultor) ListaConsultores.get(consultor.getSelectedIndex());
						idconsultor = con.getIdConsultor();
						Cproyecto.setEnabled(true);
					}
				}
			});

			consultor.setEnabled(false);

			Econsultor = new JLabel();
			Pcosultor.add(Econsultor);
			Econsultor.setText("Proyecto:");
			Econsultor.setName("Eproyecto");
			Econsultor.setBounds(484, 7, 80, 16);

			Cproyecto = new JComboBox<String>();
			Pcosultor.add(Cproyecto);
			llenarproyectos();
			//JOptionPane.showMessageDialog(null, "lista" + ListaProyectos.size() );
			Cproyecto.setBounds(478, 27, 271, 25);
			Cproyecto.addActionListener(new ActionListener() {
				/**
				 * clic en el nombre de un proyecto, 
				 * solo habilita el combo box de frentes
				 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
				 */
				public void actionPerformed(ActionEvent e) {
					if (Cproyecto.getSelectedIndex() > -1) {
						Model.Entity.Proyecto pro = new Model.Entity.Proyecto();
						//JOptionPane.showMessageDialog(null, "valor" + Cproyecto.getSelectedIndex() + " lista: " + ListaProyectos.size());
						pro = (Model.Entity.Proyecto) ListaProyectos.get(Cproyecto.getSelectedIndex());
						idproyecto = pro.getIdproyecto();
						llenarfrentes(idproyecto);
						Cfrente.setEnabled(true);
					}
				}
			});
			Cproyecto.setEnabled(false);

			Tfrente = new JLabel();
			Pcosultor.add(Tfrente);
			Tfrente.setText("Frentes:");
			Tfrente.setBounds(766, 7, 45, 16);

			Cfrente = new JComboBox<String>();
			Pcosultor.add(Cfrente);
			Cfrente.setEnabled(false);
			Cfrente.setBounds(766, 26, 190, 26);

			efecha = new JLabel();
			Pcosultor.add(efecha);
			efecha.setText("Fecha: ");
			efecha.setBounds(19, 7, 40, 16);

			ImageIcon Imagenfecha = new ImageIcon("C:/Users/pabluchis/workspace/Generadores/src/iconos/reloj.png");
			finicial = new JDateChooser();
			finicial.setIcon(Imagenfecha);
			Pcosultor.add(finicial);
			finicial.setBounds(16, 24, 180, 30);
			Calendar c1 = new GregorianCalendar();
			String cm = "";
			int dia = c1.get(Calendar.DATE);
			int mes = c1.get(Calendar.MONTH) + 1;
			if (mes < 10) {
				cm = "0" + String.valueOf(mes);
			} else {
				cm = String.valueOf(mes);
			}
			int a = c1.get(Calendar.YEAR);
			String fe = String.valueOf(dia) + "-" + cm + "-" + String.valueOf(a);
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Date uno = sdf.parse(fe);
			finicial.setDate(uno);

			Cfrente.addActionListener(new ActionListener() {
				/**
				 * clic en el nombre de un frente,
				 * crea una estimacion con la informacions seleccionada con anterioridad (consultor,proyecto)
				 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
				 */
				public void actionPerformed(ActionEvent evt) {
					int anio, mes, dia;  // , existe = 0;
					String ca = "", cm = "", cd = "";
					int c = -1;
					String fe = "";
					if (Cfrente.getSelectedIndex() > 0) {
						Frente fre = new Frente();
						for (int i = 0; i < Listafrentes.size(); i++) {
							fre = (Frente) Listafrentes.get(i);
							if (fre.getIndentificador().equals(String.valueOf(Cfrente.getSelectedItem())) == true) {
								c = fre.getIdfrente();
							}
						}
						// ****************************************************************************************************************************************
						if (finicial.getDate() != null) {
							anio = finicial.getCalendar().get(Calendar.YEAR);
							mes = finicial.getCalendar().get(Calendar.MONTH) + 1;
							dia = finicial.getCalendar().get(Calendar.DAY_OF_MONTH);
							ca = String.valueOf(anio);
							if (mes < 10) {
								cm = "0" + String.valueOf(mes);
							} else {
								cm = String.valueOf(mes);
							}
							if (dia < 10) {
								cd = "0" + String.valueOf(dia);
							} else {
								cd = String.valueOf(dia);
							}
							fe = ca + "-" + cm + "-" + cd;
							fechaestimacion = fe;
						} else {
							JOptionPane.showMessageDialog(null, "No has seleccionado fecha inicial");
						}
						// ***************************************************************************************************************************************
						idfrente = c;
						if (c > 0) {
							tablap.setEnabled(true);
						}
						// *************************************************************************************************************************************************************************************************************************
						if (c > 0 && Coperaciones.getSelectedIndex() == 0) {
							Estimacion.sacarPreinicial(String.valueOf(c), "I");
							Lestimacion = Estimacion.sacarPreinicial(String.valueOf(c), "I"); //cone.sacarPreinicial(String.valueOf(c), "I");
							if (Lestimacion.size() == 0) {
								int res = JOptionPane.showConfirmDialog(null, "Deseas utilizar una plantilla", "Confirmacion", JOptionPane.YES_NO_OPTION);
								if (res == JOptionPane.NO_OPTION) {
									Estimacion estimacion = new Estimacion();
									estimacion.setIdfrente(idfrente);
									estimacion.setIdConsultor(idconsultor);
									estimacion.setPorcentaje((float) 0.0);
									estimacion.setFecha(fe);
									estimacion.setTipo("I");
									estimacion.setNestimacion(0);
									estimacion.save();
									idestimacion = estimacion.getIdestimacion();
									//idestimacion = cone.insertarestimacion(String.valueOf(idfrente), String.valueOf(idconsultor), "0.0", fe, "I", "0");
									if (idestimacion > 0) {
										JOptionPane.showMessageDialog(null, "Estimacion creada");
									}
								} else {
									if (res == JOptionPane.YES_OPTION) {
										idestimacion = -1;
										LinkedList<Plantilla> pla = new LinkedList<Plantilla>();
										pla = cone.consultarPlantillas();
										if (pla.size() > 0) {
											try {
												Estimacion estimacion = new Estimacion();
												estimacion.setIdfrente(idfrente);
												estimacion.setIdConsultor(idconsultor);
												estimacion.setPorcentaje((float) 0.0);
												estimacion.setFecha(fe);
												estimacion.setTipo("I");
												estimacion.setNestimacion(0);
												estimacion.save();
												idestimacion = estimacion.getIdestimacion();
												//idestimacion = cone.insertarestimacion(String.valueOf(idfrente), String.valueOf(idconsultor), "0.0", fe, "I", "0");
												vpla = new Elegirplantilla(pla, cone, idestimacion, fechaestimacion);
												escritorio2.add(vpla);
												vpla.setSelected(true);
											} catch (Exception e) {
												e.printStackTrace();
												idestimacion = -1;
											}
										} else {
											Estimacion estimacion = new Estimacion();
											estimacion.setIdfrente(idfrente);
											estimacion.setIdConsultor(idconsultor);
											estimacion.setPorcentaje((float) 0.0);
											estimacion.setFecha(fe);
											estimacion.setTipo("I");
											estimacion.setNestimacion(0);
											estimacion.save();
											idestimacion = estimacion.getIdestimacion();
											//idestimacion = cone.insertarestimacion(String.valueOf(idfrente), String.valueOf(idconsultor), "0.0", fe, "I", "0");
										}
										// if(idestimacion>0){JOptionPane.showMessageDialog(null,"No hay plantillas desponibles,estimacion creada");
										// }
									} else {
										idestimacion = -1;
									}
								}
								if (idestimacion > -1) {
									consultor.setEnabled(false);
									Cproyecto.setEnabled(false);
									Cfrente.setEnabled(false);
								}
							} else {
								int res = JOptionPane.showConfirmDialog(null, "Este frente tiene: " + Lestimacion.size() + " estimaciones iniciales deseas crear una nueva", "Confirmar", JOptionPane.YES_NO_OPTION);
								if (res == JOptionPane.YES_OPTION) {
									int r = JOptionPane.showConfirmDialog(null, "Deseas utilizar una plantilla", "Confirmacion", JOptionPane.YES_NO_OPTION);
									if (r == JOptionPane.NO_OPTION) {
										Estimacion estimacion = new Estimacion();
										estimacion.setIdfrente(idfrente);
										estimacion.setIdConsultor(idconsultor);
										estimacion.setPorcentaje((float) 0.0);
										estimacion.setFecha(fe);
										estimacion.setTipo("I");
										estimacion.setNestimacion(0);
										estimacion.save();
										idestimacion = estimacion.getIdestimacion();
										//idestimacion = cone.insertarestimacion(String.valueOf(idfrente), String.valueOf(idconsultor), "0.0", fe, "I", "0");
									} else {
										if (r == JOptionPane.YES_OPTION) {
											idestimacion = -1;
											LinkedList<Plantilla> pla = new LinkedList<Plantilla>();
											pla = cone.consultarPlantillas();
											if (pla.size() > 0) {
												try {
													Estimacion estimacion = new Estimacion();
													estimacion.setIdfrente(idfrente);
													estimacion.setIdConsultor(idconsultor);
													estimacion.setPorcentaje((float) 0.0);
													estimacion.setFecha(fe);
													estimacion.setTipo("I");
													estimacion.setNestimacion(0);
													estimacion.save();
													idestimacion = estimacion.getIdestimacion();
													//idestimacion = cone.insertarestimacion(String.valueOf(idfrente), String.valueOf(idconsultor), "0.0", fe, "I", "0");
													vpla = new Elegirplantilla(pla, cone, idestimacion, fechaestimacion);
													escritorio2.add(vpla);
													vpla.setSelected(true);
												} catch (Exception e) {
													e.printStackTrace();
													idestimacion = -1;
												}
											} else {
												Estimacion estimacion = new Estimacion();
												estimacion.setIdfrente(idfrente);
												estimacion.setIdConsultor(idconsultor);
												estimacion.setPorcentaje((float) 0.0);
												estimacion.setFecha(fe);
												estimacion.setTipo("I");
												estimacion.setNestimacion(0);
												estimacion.save();
												idestimacion = estimacion.getIdestimacion();
												//idestimacion = cone.insertarestimacion(String.valueOf(idfrente), String.valueOf(idconsultor), "0.0", fe, "I", "0");
											}
											// if(idestimacion>0){JOptionPane.showMessageDialog(null,"No hay plantillas desponibles,estimacion creada");}
										} else {
											idestimacion = -1;
										}
									}
									if (idestimacion > -1) {
										consultor.setEnabled(false);
										Cproyecto.setEnabled(false);
										Cfrente.setEnabled(false);
									}

								}

							}

						}
						// ******************************************************************************************************************************************************************************************************************************************************
						if (c > 0 && Coperaciones.getSelectedIndex() == 1) {
							
							Lestimacion = Estimacion.sacarPreinicial(String.valueOf(c), "I");// cone.sacarPreinicial(String.valueOf(c), "I");
							if (Lestimacion.size() == 0) {
								JOptionPane.showMessageDialog(null, "Este frente no tiene ninguna estimacinin inicial", "verifica", JOptionPane.WARNING_MESSAGE);
							} else {
								try {
									esti = new Estimaciones(Lestimacion, cone, "modificar");
									escritorio2.add(esti);
									esti.setSelected(true);
								} catch (Exception e) {
									e.printStackTrace();
								}
								Cproyecto.setEnabled(false);
								Cfrente.setEnabled(false);
							}
						}
						// ***********************************************************************************************************************************************************************************************************************************************************
						if (c > 0 && Coperaciones.getSelectedIndex() == 2) {
							tablap.setEnabled(false);
							
							Lestimacion = Estimacion.sacarPreinicial(String.valueOf(c), "I"); //cone.sacarPreinicial(String.valueOf(c), "I");

							if (Lestimacion.size() == 0) {
								JOptionPane.showMessageDialog(null, "Este frente no tiene ninguna estimacinin inicial", "verifica", JOptionPane.WARNING_MESSAGE);
							} else {
								try {
									esti = new Estimaciones(Lestimacion, cone, "eliminar");
									escritorio2.add(esti);
									esti.setSelected(true);
								} catch (Exception e) {
									e.printStackTrace();
								}
								Cproyecto.setEnabled(false);
								Cfrente.setEnabled(false);
							}
						}

						// ***********************************************************************************************************************************************************************************************************************************************************

					}
				}
			});

			Poperaciones = new JPanel();
			escritorio.add(Poperaciones, JLayeredPane.DEFAULT_LAYER);
			Poperaciones.setBounds(17, 4, 260, 58);
			Poperaciones.setBorder(new LineBorder(new java.awt.Color(0, 0, 0), 1, false));
			Poperaciones.setLayout(null);

			Coperaciones = new JComboBox<String>();
			Coperaciones.addItem("Agregar");
			Coperaciones.addItem("Modificar");
			Coperaciones.addItem("Eliminar");
			Coperaciones.setSelectedIndex(-1);
			Coperaciones.addActionListener(new ActionListener() {
				/**
				 * validaci�n para las opciones permitidas al usuario
				 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
				 */
				public void actionPerformed(ActionEvent e) {
					if (Coperaciones.getSelectedIndex() == 0) {
						consultor.setEnabled(true);
						Cproyecto.setEnabled(false);
						Cfrente.setEnabled(false);
						tablap.setEnabled(false);

						Limpiar();
					}
					if (Coperaciones.getSelectedIndex() == 1) {
						consultor.setEnabled(false);
						Cproyecto.setEnabled(true);
						Cfrente.setEnabled(false);
						tablap.setEnabled(false);
						Limpiar();
						plantilla.setEnabled(true);

					}
					if (Coperaciones.getSelectedIndex() == 2) {
						consultor.setEnabled(false);
						Cproyecto.setEnabled(true);
						Cfrente.setEnabled(false);
						tablap.setEnabled(false);
						Limpiar();
					}
				}
			});
			Poperaciones.add(Coperaciones);
			Coperaciones.setBounds(9, 23, 239, 26);

			ope = new JLabel();
			Poperaciones.add(ope);
			ope.setText("Acci\u00F3n:");
			ope.setBounds(16, 4, 53, 16);

			pMenu = new JPopupMenu();
			agregar = new JMenuItem("Agregar");
			agregar.addActionListener(new ActionListener() {
				/**
				 * clic en agregar un aspecto a la estimacion inicial 
				 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
				 */
				public void actionPerformed(ActionEvent e) {
					int i = 0;
					if (indice > -1) {
						// *****************************************************************************************
						//se agrega una fila con la informacion del aspecto seleccionado 
						Rgenerador asp = new Rgenerador();
						Rgenerador a = new Rgenerador();
						for (i = 0; i < Laspectos.size(); i++) {
							asp2 = (Aspecto) Laspectos.get(i);
							if (asp2.getDescripcion().equals(des) == true) {
								asp.setIdaspecto(String.valueOf(asp2.getIdAspecto()));
								//asp.setClave(asp2.getClave());
								asp.setClavePublica(asp2.getClave());
								asp.setDescripcion("");
								asp.setUnidad(asp2.getUnidad());
								asp.setX(" ");
								asp.setY(" ");
								asp.setZ(" ");
								asp.setAlto("0");
								asp.setLargo("0");
								asp.setAncho("0");
								asp.setCantidad("0");
								asp.setPiezas("0");
								asp.setCosto(asp2.getCosto().toString());
								asp.setImporte("0");
								asp.setFecha(fechaestimacion);
								for (int j = 0; j < control.getLista().size(); j++) {
									a = (Rgenerador) control.getLista().get(j);
									if (a.getClave().equals(asp2.getClave())) {
										repeticion++;
									}
								}
								asp.setRepeticion(repeticion);
								control.Insertaabajo(asp, indice);
								i = Laspectos.size() + 5;
								generadores.changeSelection(indice + 1, 2, false, false);
							}
						}
					}
				}
			});

			eliminar = new JMenuItem("Eliminar");
			eliminar.addActionListener(new ActionListener() {
				/**
				 * clic en eliminar el aspecto incluido en la estimaci�n
				 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
				 */
				public void actionPerformed(ActionEvent e) {
					if (indice > -1) {
						// se elimina el aspecto del formato de n�meros generadores del sistema
						Rgenerador asp = new Rgenerador();
						asp = (Rgenerador) control.getLista().get(indice);
						if (cone.eleminarunaspecto(asp.getIdaspecto(), String.valueOf(idestimacion), String.valueOf(asp.getRepeticion())) == true && asp != null) {
							control.borraFila(indice, 1);
						} else {
							JOptionPane.showMessageDialog(null, "Error al eliminar aspecto", "Error", JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			});

			pMenu.add(agregar);
			pMenu.add(eliminar);
			

			ImageIcon Imagenplano = new ImageIcon(getClass().getResource("/iconos/abrir.png"));

			ComboBoxModel<String> CopcionesModel = new DefaultComboBoxModel<String>(new String[] { "Exporta por patida", "Exportar todo el frente", "Exportar partida a PDF" });
			Pconcepto.setLayout(null);
			
						Pgenerador = new JScrollPane();
						Pgenerador.setBounds(0, 12, 1216, 260);
						Pconcepto.add(Pgenerador);
						Pgenerador.setPreferredSize(new java.awt.Dimension(1216, 239));
						
									generadores = new JTable(modelo2);
									Pgenerador.setViewportView(generadores);
									generadores.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
									
												// Restricci�n de la informaci�n de entrada
												TableColumn tabColClave = generadores.getColumn("Clave");
												
															TableColumn tc3 = generadores.getColumn("Descripci�n");
															
																		TableColumn tc4 = generadores.getColumn("Unidad");
																		TableColumn tabColX = generadores.getColumn("       X");
																		
																					TableColumn tabColY = generadores.getColumn("       Y");
																					
																								TableColumn tabColZ = generadores.getColumn("       Z");
																								TableColumn tabColAlto = generadores.getColumn("    Alto");
																								
																											TableColumn tabColLargo = generadores.getColumn("    Largo");
																											
																														TableColumn tabColAncho = generadores.getColumn("    Ancho");
																														
																																	TableColumn tabColCantidad = generadores.getColumn("Cantidad");
																																	
																																				TableColumn tabColPiezas = generadores.getColumn("Piezas");
																																				
																																							TableColumn tabColCosto = generadores.getColumn("Costo");
																																							
																																										TableColumn tabColImporte = generadores.getColumn("Importe");
																																										
																																													generadores.setRowHeight(60);
																																													generadores.addMouseListener(new java.awt.event.MouseAdapter() {
																																														/**
																																														 * clic en la tabla de n�meros generadores del sistema, obtiene la descripci�n del aspecto seleccionado para su futuro uso en el sistema
																																														 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
																																														 */
																																														public void mouseClicked(java.awt.event.MouseEvent evt) {
																																															if (evt.isMetaDown()) {
																																																pMenu.show(evt.getComponent(), evt.getX(), evt.getY());
																																															} else {
																																																if (evt.isAltDown() == false) {
																																																	indice = generadores.getSelectionModel().getLeadSelectionIndex();
																																																	if (indice > -1) {
																																																		Rgenerador asp = new Rgenerador();
																																																		asp = (Rgenerador) control.getLista().get(indice);
																																																		if (asp.getDescripcion().equals("") == false) {
																																																			des = asp.getDescripcion();
																																																		}
																																																		repeticion = asp.getRepeticion();
																																																	}
																																																}
																																															}
																																														}
																																													});
																																													
																																																generadores.addKeyListener(new KeyListener() {
																																																	public void keyPressed(KeyEvent e) {
																																																	}
																																													
																																																	public void keyReleased(KeyEvent e) {
																																																	}
																																													
																																																	/**
																																																	 * validaci�n del texto de entrada por parte del usuario en la tabla de n�meros generadores
																																																	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
																																																	 */
																																																	public void keyTyped(KeyEvent e) {
																																																		char letra = e.getKeyChar();
																																																		if ((letra < '0' || letra > '9') && (letra != '.') && (letra != KeyEvent.VK_BACK_SPACE)) {
																																																			e.consume();
																																																		}
																																																	}
																																																});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * m�todo para recuperar los proyectos de la base de datos y mostrarlos en pantalla
	 */
	private void llenarproyectos() {
		ListaProyectos = Model.Entity.Proyecto.findAll();
		Cproyecto.removeAllItems();
		//ListaProyectos =  models.Proyecto.findAll();//cone.GetProyectos();
		for (int i = 0; i < ListaProyectos.size(); i++) {
			pro = (Model.Entity.Proyecto) ListaProyectos.get(i);
			Cproyecto.addItem(pro.getProyecto());
		}
		if (Cproyecto.getItemCount() > 0) {
			Cproyecto.setSelectedIndex(0);
		}
		
	}

	/**
	 * m�todo para recuperar los consultores de la base de datos y mostrarlos en pantalla
	 */
	private void llenarconsultor() {
		consultor.removeAllItems();
		ListaConsultores = Model.Entity.Consultor.findAll();//cone.GetConsultores();
		for(Model.Entity.Consultor c : ListaConsultores){
			consultor.addItem(c.getNombre() + "   " + c.getPaterno() + "  " + c.getMaterno());
		}
		if (consultor.getItemCount() > 0) {
			consultor.setSelectedIndex(0);
		}
	}

	/**
	 * m�todo para recuperar los frentes de la base de datos 
	 */
	private void llenarLFrentes() {
		Listafrentes = Frente.findAll();//cone.Frentes();
	}

	/**
	 * m�todo para mostrar los frentes en pantalla dependiendo del proyecto seleccionado
	 * @param indice -- identificador del proyecto seleccionado
	 */
	private void llenarfrentes(int indice) {
		Cfrente.removeAllItems();
		Cfrente.addItem("        ");
		for (int i = 0; i < Listafrentes.size(); i++) {
			fre = (Frente) Listafrentes.get(i);
			if (fre.getIdproyecto() == indice) {
				Cfrente.addItem(fre.getIndentificador());
			}
			Cfrente.setSelectedIndex(-1);

		}
		if (Cfrente.getItemCount() > 0) {
			Cfrente.setSelectedIndex(0);
		}

	}

	/**
	 * m�todo para recuperar los conceptos de la base de datos
	 */
	private void llenarLconceptos() {
		Lconceptos = Model.Entity.Concepto.findAll();//cone.GetConceptos();
	}

	/**
	 * m�todo para mostrar los conceptos en pantalla dependiendo de la partida seleccionada
	 * @param indice -- indentificador de partida seleccionada
	 */
	

	/**
	 * m�todo para recuperar los Aspectos de la base de datos 
	 */
	private void llenarLAcpestosdos() {
		Laspectos = Model.Entity.Aspecto.findAll();//cone.GetAspectos();
	}

	/**
	 * m�todo para verificar la informacion de los aspectos seleccionados en la estimacion
	 * @return si existe algun problema con la informacion del aspecto ? false : todo ok true
	 */
	private boolean verificarDatos() {
		boolean ban = false;
		int i;
		Rgenerador ge = new Rgenerador();
		for (i = 0; i < control.getLista().size(); i++) {
			ge = (Rgenerador) control.getLista().get(i);
			if (ge.getX().equals(" ") == true || ge.getY().equals(" ") == true || ge.getZ().equals(" ") == true) {
				ban = true;
				JOptionPane.showMessageDialog(null, "El aspecto: " + ge.getClave() + "  contiene valores no validos ", "verifica", JOptionPane.WARNING_MESSAGE);
				i = control.getLista().size() + 4;
			} else {
				if (ge.getUnidad().equals("m2") == true) {
					if (ge.getLargo().equals("0") == true || ge.getAncho().equals("0") == true || ge.getPiezas().equals("0") == true) {
						ban = true;
						JOptionPane.showMessageDialog(null, "El aspecto: " + ge.getClave() + "  contiene valores no validos ", "verifica", JOptionPane.WARNING_MESSAGE);
						i = control.getLista().size() + 4;
					}
				}
				if (ge.getUnidad().equals("ml") == true) {
					if (ge.getLargo().equals("0") == true || ge.getPiezas().equals("0") == true) {
						ban = true;
						JOptionPane.showMessageDialog(null, "El aspecto: " + ge.getClave() + "  contiene valores no validos ", "verifica", JOptionPane.WARNING_MESSAGE);
						i = control.getLista().size() + 4;
					}
				}
				if (ge.getUnidad().equals("m3") == true) {
					if (ge.getAlto().equals("0") == true || ge.getLargo().equals("0") == true || ge.getAncho().equals("0") == true || ge.getPiezas().equals("0") == true) {
						ban = true;
						JOptionPane.showMessageDialog(null, "El aspecto: " + ge.getClave() + "  contiene valores no validos ", "verifica", JOptionPane.WARNING_MESSAGE);
						i = control.getLista().size() + 4;
					}
				}
				if (ge.getUnidad().equals("pieza") == true) {
					if (ge.getPiezas().equals("0") == true) {
						ban = true;
						JOptionPane.showMessageDialog(null, "El aspecto: " + ge.getClave() + "  contiene valores no validos ", "verifica", JOptionPane.WARNING_MESSAGE);
						i = control.getLista().size() + 4;
					}
				}
				if (ge.getUnidad().equals("L") == true) {
					if (ge.getPiezas().equals("0") == true) {
						ban = true;
						JOptionPane.showMessageDialog(null, "El aspecto: " + ge.getClave() + "  contiene valores no validos ", "verifica", JOptionPane.WARNING_MESSAGE);
						i = control.getLista().size() + 4;
					}
				}
				if (ge.getUnidad().equals("kg") == true) {
					if (ge.getPiezas().equals("0") == true) {
						ban = true;
						JOptionPane.showMessageDialog(null, "El aspecto: " + ge.getClave() + "  contiene valores no validos ", "verifica", JOptionPane.WARNING_MESSAGE);
						i = control.getLista().size() + 4;
					}
				}
			}
		}
		return ban;
	}

	/**
	 * m�todo para desplegar los aspectos en pantalla
	 * @param indice -- indice del concepto seleccionado
	 */
	private void llenarAspectos(int indice) {
		//controlador.llenarAspectos(Laspectos,asp2,Ddescripcion,TDescripcion,control,indice);
	}

	/**
	 * m�todo para limpiar el modelo de la tabla del generador en pantalla
	 */
	public void Limpiar() {
		int tama = control.Tama() - 1;
		if (entrouno = true) {
			if (tama > -1) {
				while (tama >= 0) {
					control.borraFila(tama, 1);
					tama--;
				}
			}
		} else {
			entrouno = true;
		}

	}

	/**
	 * m�todo para mostrar las partidas en pantalla
	 */
	public void llenarParitdas() {
		Lpartidas = Model.Entity.Partida.findAll();//cone.getPartidas();
		for (int i = 0; i < Lpartidas.size(); i++) {
			par = (Partida) Lpartidas.get(i);
			controlp.anhadeFila(par.getNombre());
		}
	}

	// *****************************************************************************************************************************
	/**
	 *Clase para asiginar un componente JTextArea en la columna de descripcion del aspecto (visualmente)
	 * @author Pablo Rivera
	 */
	class CustomRenderer implements TableCellRenderer {

		JTextArea textArea;

		/**
		 * constructor con un JTextArea
		 */
		public CustomRenderer() {
			textArea = new JTextArea();
			textArea.setLineWrap(true);
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			textArea.setText((String) value);
			return textArea;
		}
	}

	/**
	 *Clase para asignar el editor de un componente JTextField en la columna de descripcion del aspecto
	 * @author Pablo Rivera
	 */
	class CustomEditor implements TableCellEditor {

		JTextArea textArea;

		/**
		 * constructor con un JtextArea
		 */
		public CustomEditor() {
			textArea = new JTextArea();
			textArea.setLineWrap(true);
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			textArea.setText((String) value);
			return textArea;
		}

		public void addCellEditorListener(CellEditorListener l) {
		}

		public void cancelCellEditing() {
		}

		public Object getCellEditorValue() {
			return textArea.getText();
		}
		public boolean isCellEditable(EventObject anEvent) {
			return true;
		}

		public void removeCellEditorListener(CellEditorListener l) {
		}

		public boolean shouldSelectCell(EventObject anEvent) {
			return true;
		}
		public boolean stopCellEditing() {
			return true;
		}

	}
}