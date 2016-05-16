package Views;
import  java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import MetodosRemotos.Metodos;
import Model.Entity.Tipo;
import Options.ComponentsUser;
import TablaTipo.ControlTipo;
/**
 * clase para administrar los tipos de proyectos de la base de datos
 * @author Pablo Rivera
 * @colaboraci�n luiiis lazaro
 *
 */
public class Tipoproyecto extends javax.swing.JInternalFrame {

	/**
	 * escritorio --- panel principal de la aplicaci�n 
	 * pcontenedor --- panel para la informaci�n de los tipos de proyectos
	 * agregar --- bot�n para agregar un nuevo tipo 
	 * ttipos --- Tabla para visualizar la informaci�n de los tipos de proyectos
	 * eliminar -- bot�n para eliminar un tipo 
	 * scontenedor --- scroll para la tabla de tipos de proyecto
	 * modelo --- modelo de la tabla para los tipos
	 * control --- controlo de la tabla para manipular el modelo y su informaci�n 
	 * modificar --- bot�n para actualizar la informaci�n de de un tipo 
	 * ltipos --- lista de tipos de proyectos existentes en la base de datos
	 * ti --- auxiliar para manipular los tipos en la lista
	 * indice --- auxiliar para obtener el Id del tipo seleccionado
	 * tipo --- combo box //sin viisualizaci�n 
	 * 
	 */

	private static final long serialVersionUID = 1L;
	private JDesktopPane escritorio;
	private JPanel Pcontenedor;
	private JButton agregar;
	private JTable Ttipos;
	private JButton eliminar;
	private JScrollPane Scontenedor;
	private ControlTipo control = new ControlTipo();
	private JButton modificar;
	private LinkedList < Tipo > Ltipos = new LinkedList < Tipo >( );
	private Tipo ti;
	private int indice = -1;
	private JComboBox < String > tipo;
	
	/**
	 * constructor de la clase 
	 * @param cone --- m�todos para la manipulaci�n de la base de datos 
	 * @param tipo --- combo box que no se visualiza 
	 */
	public Tipoproyecto ( Metodos cone , JComboBox < String > tipo ) {
		super( "Editar tipos de proyectos", false, true, false, true );
		initGUI( cone, tipo );
	}

	/**
	 * inicializaci�n de los componentes gr�ficos
	 * @param cone --- m�todos para la manipulaci�n de la base de datos 
	 * @param tipo --- combo box que no se visualiza
	 */
	private void initGUI ( final Metodos cone , JComboBox < String > tipo ) {
		try {
			setPreferredSize( new Dimension( 400, 300 ) );
			setBounds( new Rectangle( 300, 70, 400, 300 ) );
			setVisible( true );

			escritorio = new JDesktopPane( );
			getContentPane( ).add( escritorio, BorderLayout.CENTER );

			Pcontenedor = new JPanel( );
			escritorio.add( Pcontenedor, JLayeredPane.DEFAULT_LAYER );
			Pcontenedor.setBounds( 0, 0, 398, 275 );
			Pcontenedor.setLayout( null );

			Scontenedor = new JScrollPane( );
			Pcontenedor.add( Scontenedor );
			Scontenedor.setBounds( 3, 4, 393, 221 );
			Scontenedor.setBorder( new LineBorder( new java.awt.Color( 0, 0, 0 ), 1, false ) );

			Ttipos = new JTable( control.getModeloTipo() );
			Scontenedor.setViewportView( Ttipos );
			Ltipos = Tipo.findAll();
			Ttipos.addMouseListener( new java.awt.event.MouseAdapter( ) {
				/**
				 * listener para obtener el ID del tipo seleccionado en la tabla 
				 * (non-Javadoc)
				 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
				 */
				public void mouseClicked ( java.awt.event.MouseEvent e ) {
					if ( e.isMetaDown( ) ) {

					} else {
						if ( e.isAltDown( ) == false ) {
							indice = Ttipos.getSelectionModel( ).getLeadSelectionIndex( );
						}
					}
				}
			} );

			this.tipo = tipo;
			llenartipos( ); // ##################################33

			ImageIcon Imagenagregar = new ImageIcon( getClass( ).getResource( "/iconos/agregar.png" ) );
			agregar = new JButton( );
			agregar.setIcon( Imagenagregar );
			Pcontenedor.add( agregar );
			agregar.setText( "Agregar" );
			agregar.setBounds( 56, 236, 102, 30 );
			agregar.addActionListener( new ActionListener( ) {

				/**Clic sobre Botón Agregar
				 * (non-Javadoc)
				 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
				 */
				public void actionPerformed ( ActionEvent e ) {
					JTextField txt = ComponentsUser.getDataTxt( 45, 1 );
					int result = JOptionPane.showConfirmDialog( null, txt, "Ingrese Nombre del Nuevo Tipo", JOptionPane.OK_CANCEL_OPTION );
					switch ( result ) {
						case 0 :
							try {
								String newtype = txt.getText( ).trim( );
								if(newtype.length() == 0)
								{
									JOptionPane.showMessageDialog( null, "No se ha Ingresado Ningún Dato" );
									return;
								}
								System.out.println("Nuevo tipo : " + newtype);
								Tipo tipo = new Tipo();
								tipo.setTipo(newtype);
								
								if(Tipo.find(newtype) != null)
								{
									JOptionPane.showMessageDialog( null, "El Tipo Ya Existe", "Atenci�n", JOptionPane.WARNING_MESSAGE );
								}
								else
								{
									tipo.save();
									control.anhadeFila( newtype );
									Ltipos = Tipo.findAll();
									llenarTipos();
								}
								
							} catch ( Exception e1 ) {
								JOptionPane.showMessageDialog( null, "Servidor fuera de servicio (Error insertar tipo de proyecto) ", "Atención", JOptionPane.ERROR_MESSAGE );
							}
							
							break;

						default :
							JOptionPane.showMessageDialog( null, "Operaci�n Cancelada por el Usuario" );
							break;
					}
				}
			} );

			ImageIcon Imageneliminar = new ImageIcon( getClass( ).getResource( "/iconos/eliminar.png" ) );
			eliminar = new JButton( );
			Pcontenedor.add( eliminar );
			eliminar.setIcon( Imageneliminar );
			eliminar.setText( "Eliminar" );
			eliminar.setBounds( 283, 236, 103, 30 );
			eliminar.addActionListener( new ActionListener( ) {
				/**
				 * opci�n para eliminar un tipo de proyecto
				 * (non-Javadoc)
				 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
				 */
				public void actionPerformed ( ActionEvent e ) {
					if ( indice > 0 ) {
						int res = JOptionPane.showConfirmDialog( null, "Deseas eliminar �ste tipo", "confirmar", JOptionPane.YES_NO_OPTION );
						if ( res == JOptionPane.YES_OPTION ) {
							Tipo t = new Tipo( );
							t = ( Tipo ) Ltipos.get( indice );
							if ( t.delete()) {
								control.borraFila( indice );
								Ltipos = Tipo.findAll();
								llenarTipos( );
							} else {
								JOptionPane.showMessageDialog( null, "Error al eliminar tipo proyecto", "Error", JOptionPane.WARNING_MESSAGE );
							}
						}
					}
				}
			} );

			ImageIcon Imagenmodificar = new ImageIcon( getClass( ).getResource( "/iconos/modificar.png" ) );
			modificar = new JButton( );
			Pcontenedor.add( modificar );
			modificar.setIcon( Imagenmodificar );
			modificar.setText( "Modifcar" );
			modificar.setBounds( 169, 237, 103, 30 );
			modificar.addActionListener( new ActionListener( ) {
				/**
				 * opci�n para actualizar el tipo de proyecto
				 * (non-Javadoc)
				 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
				 */
				public void actionPerformed ( ActionEvent e ) {
					if ( indice >= 0 ) {
						JTextField txt = ComponentsUser.getDataTxt( 45, 1 );// Creaci�n de la caja de texto
						int result = JOptionPane.showConfirmDialog( null, txt, "Ingrese el Nuevo Nombre del Tipo", JOptionPane.OK_CANCEL_OPTION );
						switch ( result ) {
							case 0 :
								try {
									String newtype = txt.getText( ).trim( );
									if ( Boolean.valueOf( cone.existetipo( newtype ) ).equals( false ) ) {
										Tipo t = new Tipo( );
										t = ( Tipo ) Ltipos.get( indice );
										t.setTipo(newtype);
										if (t.save()) {
											Limpiar();
											Ltipos = Tipo.findAll();
											llenartipos( );
											llenarTipos( );
										}
									} else {
										JOptionPane.showMessageDialog( null, "Servidor fuera de servicio (Error insertar tipo de proyecto) ", "Atención", JOptionPane.ERROR_MESSAGE );
									}
								} catch ( Exception e2 ) {
									JOptionPane.showMessageDialog( null, "Servidor fuera de servicio (Error modificar tipo de proyecto) ", "Error", JOptionPane.ERROR_MESSAGE );
								}
								break;
							default :
								JOptionPane.showMessageDialog( null, "Operaci�n Cancelada por el Usuario" );
								break;
						}
					}
				}
			} );

		} catch ( Exception e ) {
			e.printStackTrace( );
		}
	}

	/**
	 * m�todo para llenar la tabla de tipos con la informaci�n de la base de datos
	 */
	public void llenartipos ( ) {
		ti = new Tipo( );
		for ( int i = 0 ; i < Ltipos.size( ) ; i++ ) {
			ti = ( Tipo ) Ltipos.get( i );
			control.anhadeFila( ti.getTipo( ) );
		}
	}

	/**
	 * M�todo para limpiar la informaci�n de la tabla de tipos 
	 */
	public void Limpiar ( ) {
		int tama = control.Tama( ) - 1;
		if ( tama > -1 ) {
			while ( tama >= 0 ) {
				control.borraFila( tama );
				tama--;
			}
		}
	}

	/**
	 * m�todo para llenar el Combobox de tipos de proyectos
	 * 
	 */
	public void llenarTipos ( ) {
		tipo.removeAllItems( );
		for ( int i = 0 ; i < Ltipos.size( ) ; i++ ) {
			ti = new Tipo( );
			ti = ( Tipo ) Ltipos.get( i );
			tipo.addItem( ti.getTipo( ) );
		}
		if ( tipo.getItemCount( ) > 0 ) {
			tipo.setSelectedIndex(0);
		}
	}
}