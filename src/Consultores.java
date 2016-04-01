import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Font;

import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import MetodosRemotos.Metodos;
import Model.Entity.Consultor;
import Options.LenString;
import Options.OptionsText;

/**
 * Clase para la actualizaciones de los consultores en la base de datos
 * (usuarios)
 * @author Pablo Rivera
 * @colaboraci�n luiiis Lazaro
 * @access {@docRoot}
 */
public class Consultores extends javax.swing.JInternalFrame {

	/**
	 * escritorio --- panel de la aplicaci�n (men� principal)
	 * paneluno --- panel del JInternalFrame
	 * agregar --- bot�n para agregar nuevo usuario (Administrador o consultor)
	 * materno --- campo txt para el apellido materno
	 * ematern --- etiqueta del campo txt de apellido materno
	 * tipo --- comboboc para asignar el tipo de usuario
	 * aceptar --- bot�n para confirmar operaci�n seleccionada
	 * password2 --- confirmaci�n de la contrase�a elegida
	 * epassword2 --- etiqueta para la confirmacion de la contrase�a elegida
	 * password --- contrase�a elegida del usuarios (Administrador o consultor)
	 * epassword --- etiqueta para la contrase�a
	 * login --- campo txt para asignar nickname (alias) para el sistema
	 * elogin --- etiqueta para nickname
	 * nombre --- campo txt para escribir el nombre del usuario
	 * enombre --- etiqueta para el nombre 
	 * paterno --- campo txt para escribir el apellido paterno del usuario
	 * epaterno --- etiqueta para el apellido paterno
	 * pdatos --- panel para contener la informaci�n del usuario
	 * eusuario --- etiqueta para el tipo de usuario
	 * Cusuarios --- combobox para seleccionar el tipo de usuario elegido
	 * eliminar --- bot�n para eliminar un usuario
	 * modificar --- bot�n para modificar la informaci�n de un usuario
	 * panelopc --- panel para contener los RadioButtons de opciones disponibles
	 * Lconsultores --- vector para manipular la informaci�n de la base de datos de los usuarios (administradores y consultores)
	 * idconsultor --- auxiliar para obtener el id del consultor seleccionado por el Cbox
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JDesktopPane escritorio;
	private JPanel Paneluno;
	private JRadioButton agregar;
	private JTextField materno;
	private JLabel ematerno;
	private JComboBox < String > tipo;
	private JLabel etipo;
	private JButton aceptar;
	private JPasswordField password2;
	private JLabel epassword2;
	private JPasswordField password;
	private JLabel epassword;
	private JTextField login;
	private JLabel elogin;
	private JTextField nombre;
	private JLabel Enombre;
	private JTextField paterno;
	private JLabel epaterno;
	private JPanel Pdatos;
	private JLabel Eusuarios;
	private JComboBox < String > Cusuarios;
	private JRadioButton eliminar;
	private JRadioButton modificar;
	private JPanel panelopc;
	private Vector<Model.Entity.Consultor> Lconsultores = new Vector < Model.Entity.Consultor>( );
	private int idconsultor = -1;

	/**
	 * contructor de la clase inicia componentes gr�ficos
	 * 
	 * @param cone
	 *            para las operaciones a la base de datos
	 */
	public Consultores ( Metodos cone ) {
		super( "Registro de consultores", false, true, false, true );
		initGUI( cone );
	}

	/**
	 * Inicializaci�n de componentes gr�ficos para la ventana de consultores
	 * 
	 * @param cone
	 *            para las operaciones a la base de datos
	 *            JComboBox.enabled= true/false -->
	 *            JcomboBox.setenable(false/true)
	 */
	private void initGUI ( final Metodos cone ) {
		try {
			this.setPreferredSize( new java.awt.Dimension( 484, 420 ) );
			this.setBounds( 0, 0, 374, 420 );
			setVisible( true );

			escritorio = new JDesktopPane( );
			getContentPane( ).add( escritorio, BorderLayout.CENTER );

			Paneluno = new JPanel( );
			escritorio.add( Paneluno, JLayeredPane.DEFAULT_LAYER );
			Paneluno.setBounds( 0, 0, 362, 395 );
			Paneluno.setLayout( null );

			panelopc = new JPanel( );
			Paneluno.add( panelopc );
			panelopc.setBounds( 6, 6, 345, 71 );
			panelopc.setBorder( new LineBorder( new java.awt.Color( 0, 0, 0 ), 1, false ) );
			panelopc.setLayout( null );

			agregar = new JRadioButton( );
			agregar.setSelected(true);
			agregar.setFont( new Font( "Tahoma", Font.PLAIN, 12 ) );
			panelopc.add( agregar );
			agregar.setText( "Agregar" );
			agregar.setBounds( 17, 11, 73, 20 );
			agregar.addActionListener( new ActionListener( ) {
				/**
				 * Opci�n para Agregar un nuevo Usuario
				 * (non-Javadoc)
				 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
				 * 
				 */
				public void actionPerformed ( ActionEvent e ) {
					modificar.setSelected( false );
					eliminar.setSelected( false );
					Cusuarios.setEnabled( false );
					Limpiar( );
					paterno.requestFocusInWindow( );
				}
			} );

			modificar = new JRadioButton( );
			modificar.setFont( new Font( "Tahoma", Font.PLAIN, 12 ) );
			panelopc.add( modificar );
			modificar.setText( "Modificar" );
			modificar.setBounds( 126, 11, 80, 20 );
			modificar.addActionListener( new ActionListener( ) {

				/**
				 * Opci�n para modificar/actualizar la informaci�n de un consultor 
				 * (non-Javadoc)
				 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
				 */
				public void actionPerformed ( ActionEvent e ) {
					agregar.setSelected( false );
					eliminar.setSelected( false );
					Lconsultores = cone.GetConsultores( );
					llenarConsultores( );
					Cusuarios.setEnabled( true );
					Limpiar( );
					llenarTXT( );
				}
			} );

			eliminar = new JRadioButton( );
			eliminar.setFont( new Font( "Tahoma", Font.PLAIN, 12 ) );
			panelopc.add( eliminar );
			eliminar.setText( "Eliminar" );
			eliminar.setBounds( 230, 11, 78, 20 );
			eliminar.addActionListener( new ActionListener( ) {

				/** Opci�n para Eliminar un consultor del sistema
				 * (non-Javadoc)
				 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
				 */
				public void actionPerformed ( ActionEvent e ) {
					agregar.setSelected( false );
					modificar.setSelected( false );
					Lconsultores = cone.GetConsultores( );
					llenarConsultores( );
					Cusuarios.setEnabled( true );
					Limpiar( );
					llenarTXT( );
				}
			} );

			ButtonGroup groupRadiosBtn = new ButtonGroup( );
			groupRadiosBtn.add( agregar );
			groupRadiosBtn.add( modificar );
			groupRadiosBtn.add( eliminar );

			Cusuarios = new JComboBox < String >( );
			panelopc.add( Cusuarios );
			Cusuarios.setBounds( 76, 37, 232, 26 );
			Cusuarios.setEnabled( false );
			Cusuarios.addActionListener( new ActionListener( ) {
				/**
				 * Secci�n encargada de llenar los campos de texto dependiendo de que usuario esta 
				 * seleccionado en el combo box
				 * (non-Javadoc)
				 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
				 */
				public void actionPerformed ( ActionEvent e ) {
					llenarTXT( );
				}
			} );

			Eusuarios = new JLabel( );
			panelopc.add( Eusuarios );
			Eusuarios.setText( "Usuarios:" );
			Eusuarios.setBounds( 7, 43, 60, 16 );

			Pdatos = new JPanel( );
			Paneluno.add( Pdatos );
			Pdatos.setBounds( 6, 83, 345, 305 );
			Pdatos.setLayout( null );
			Pdatos.setBorder( new LineBorder( new java.awt.Color( 0, 0, 0 ), 1, false ) );

			epaterno = new JLabel( );
			Pdatos.add( epaterno );
			epaterno.setText( "Apellido Paterno:" );
			epaterno.setBounds( 10, 11, 107, 28 );

			paterno = new JTextField( );
			Pdatos.add( paterno );
			paterno.setBounds( 121, 11, 191, 28 );
			paterno.setBorder( new LineBorder( new java.awt.Color( 0, 0, 0 ), 1, false ) );
			paterno.addKeyListener( new KeyListener( ) {

				/**
				 * Listener para escribir s�lo caracteres permitidos en el JtextField
				 * invoca otro m�todo statico que se encarga de revisar los caracteres. 
				 * @Override(non-Javadoc)
				 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
				 * validaci�n de texto de entrada
				 */
				public void keyTyped ( KeyEvent e ) {
					OptionsText.onlyCharsSpace( e.getKeyChar( ), e );
				}
				public void keyPressed ( KeyEvent e ) {}
				public void keyReleased ( KeyEvent e ) {}
			} );

			ematerno = new JLabel( );
			Pdatos.add( ematerno );
			ematerno.setText( "Apellido Materno:" );
			ematerno.setBounds( 10, 51, 117, 28 );

			materno = new JTextField( );
			Pdatos.add( materno );
			materno.setBounds( 121, 51, 191, 29 );
			materno.setBorder( new LineBorder( new java.awt.Color( 0, 0, 0 ), 1, false ) );
			materno.addKeyListener( new KeyListener( ) {

				/**
				 * Listener para escribir s�lo caracteres permitidos en el JtextField
				 * invoca otro m�todo statico que se encarga de revisar los caracteres. 
				 * @Override(non-Javadoc)
				 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
				 * validaci�n del texto de entrada
				 */
				public void keyTyped ( KeyEvent e ) {
					OptionsText.onlyCharsSpace( e.getKeyChar( ), e );
				}

				public void keyPressed ( KeyEvent e ) {}

				public void keyReleased ( KeyEvent e ) {}
			} );

			Enombre = new JLabel( );
			Pdatos.add( Enombre );
			Enombre.setText( "Nombre(s):" );
			Enombre.setBounds( 47, 85, 70, 28 );

			nombre = new JTextField( );
			Pdatos.add( nombre );
			nombre.setBounds( 121, 86, 191, 27 );
			nombre.setBorder( new LineBorder( new java.awt.Color( 0, 0, 0 ), 1, false ) );
			nombre.addKeyListener( new KeyListener( ) {

				/**
				 * Listener para escribir s�lo caracteres permitidos en el JtextField
				 * invoca otro m�todo statico que se encarga de revisar los caracteres. 
				 * @Override(non-Javadoc)
				 * @see	java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
				 * validaci�n del texto de entrada
				 */
				public void keyTyped ( KeyEvent e ) {
					OptionsText.onlyCharsSpace( e.getKeyChar( ), e );
				}

				public void keyPressed ( KeyEvent e ) {}
				public void keyReleased ( KeyEvent e ) {}
			} );

			elogin = new JLabel( );
			Pdatos.add( elogin );
			elogin.setText( "Login:" );
			elogin.setBounds( 69, 123, 37, 23 );

			login = new JTextField( );
			Pdatos.add( login );
			login.setBounds( 121, 122, 191, 25 );
			login.setBorder( new LineBorder( new java.awt.Color( 0, 0, 0 ), 1, false ) );
			login.addKeyListener( new KeyListener( ) {

				/**
				 * Listener para escribir s�lo caracteres permitidos en el JtextField
				 * invoca otro m�todo statico que se encarga de revisar los caracteres. 
				 * @Override (non-Javadoc)
				 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
				 * validacion de texto de entrada
				 */
				public void keyTyped ( KeyEvent e ) {
					OptionsText.onlyCharsNumbers( e.getKeyChar( ), e );
				}

				public void keyPressed ( KeyEvent e ) {}

				public void keyReleased ( KeyEvent e ) {}
			} );

			epassword = new JLabel( );
			Pdatos.add( epassword );
			epassword.setText( "Contrase�a:" );
			epassword.setBounds( 41, 152, 77, 25 );

			password = new JPasswordField( );
			Pdatos.add( password );
			password.setBounds( 121, 152, 191, 25 );
			password.setBorder( new LineBorder( new java.awt.Color( 0, 0, 0 ), 1, false ) );

			password.addKeyListener( new KeyListener( ) {

				/**
				 * Listener para escribir s�lo caracteres permitidos en el JtextField
				 * invoca otro m�todo statico que se encarga de revisar los caracteres.
				 * (non-Javadoc)
				 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
				 * validaci�n de texto de entrada
				 */
				public void keyTyped ( KeyEvent e ) {
					OptionsText.onlyCharsNumbers( e.getKeyChar( ), e );
				}

				public void keyReleased ( KeyEvent e ) {}

				public void keyPressed ( KeyEvent arg0 ) {}
			} );

			epassword2 = new JLabel( );
			Pdatos.add( epassword2 );
			epassword2.setText( "Repita Contrase�a:" );
			epassword2.setBounds( 6, 189, 121, 22 );

			password2 = new JPasswordField( );
			Pdatos.add( password2 );
			password2.setBounds( 131, 188, 181, 25 );
			password2.setBorder( new LineBorder( new java.awt.Color( 0, 0, 0 ), 1, false ) );
			password2.addKeyListener( new KeyListener( ) {

				/**
				 * Listener para escribir s�lo caracteres permitidos en el JtextField
				 * invoca otro m�todo statico que se encarga de revisar los caracteres.
				 * (non-Javadoc)
				 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
				 * validaci�n de texto de entrada
				 */
				public void keyTyped ( KeyEvent e ) {
					OptionsText.onlyCharsNumbers( e.getKeyChar( ), e );
				}

				public void keyReleased ( KeyEvent e ) {}

				public void keyPressed ( KeyEvent e ) {}
			} );

			/**
			 * Bloqueo CopyPaste en campos de usuario, y contrase�as
			 */
			OptionsText.disablePasteAndCopyAction( ( JComponent ) login );
			OptionsText.disablePasteAndCopyAction( ( JComponent ) password );
			OptionsText.disablePasteAndCopyAction( ( JComponent ) password2 );

			paterno.setDocument( new LenString( 20 ) );
			materno.setDocument( new LenString( 20 ) );
			nombre.setDocument( new LenString( 20 ) );
			login.setDocument( new LenString( 20 ) );
			password.setDocument( new LenString( 20 ) );
			password2.setDocument( new LenString( 20 ) );

			ImageIcon Imagenprocesar = new ImageIcon( getClass( ).getResource( "iconos/proceso.png" ) );
			aceptar = new JButton( );
			Pdatos.add( aceptar );
			aceptar.setIcon( Imagenprocesar );
			aceptar.setText( "Realizar Operaci�n" );
			aceptar.setBounds( 41, 258, 271, 38 );
			aceptar.setFont( new java.awt.Font( "Segoe UI", 1, 12 ) );
			aceptar.addActionListener( new ActionListener( ) {

				/**
				 * Validaci�n para comparaci�n de contrase�as
				 * y para la informaci�n del usuario nuevo
				 * (non-Javadoc)
				 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
				 */
				public void actionPerformed ( ActionEvent e ) {
					String tu = new String( "" );
					String pass1 = new String( password.getPassword( ) );
					String pass2 = new String( password2.getPassword( ) );

					/**
					 * Secci�n para agregar el usuario
					 */
					if ( agregar.isSelected( ) ) {
						if ( vacio( ) == false ) {
							// verificaci�n del tipo de usuario
							if ( pass1.toString( ).equals( pass2.toString( ) ) == true ) {
								if ( tipo.getSelectedIndex( ) == 0 ) {
									tu = "A";
								} else {
									tu = "U";
								}
								try {
									// verficaci�n si ya existe el usuario
									if ( cone.existeUsuario( paterno.getText( ), materno.getText( ), nombre.getText( ) ) == false ) {
										if ( cone.agregarUsuario( paterno.getText( ), materno.getText( ), nombre.getText( ), login.getText( ), password.getPassword( ), tu ) ) {
											JOptionPane.showMessageDialog( null, "La inserci�n se realiz� con �xito" );
											Limpiar( );
										}
									} else {
										JOptionPane.showMessageDialog( null, "El usuario que Ingresaste ya Existe", "Error", JOptionPane.WARNING_MESSAGE );
									}
								} catch ( HeadlessException e1 ) {
									JOptionPane.showMessageDialog( null, "Servidor Fuera de Servicio (Error al insertar usuario) ", "Error", JOptionPane.ERROR_MESSAGE );
								}
							} else {
								JOptionPane.showMessageDialog( null, "Las Contrase�as no Coincide" );
							}
						}
					} else if ( modificar.isSelected( ) ) {
						/**
						 * Secci�n para modificar la informaci�n
						 * de los datos del usuario
						 */
						if ( vacio( ) == false ) {
							if ( pass1.toString( ).equals( pass2.toString( ) ) == true ) {
								int res = JOptionPane.showConfirmDialog( null, "Deseas modificar el usuario", "Confirmaci�n", JOptionPane.YES_NO_OPTION );
								if ( res == JOptionPane.YES_OPTION ) {
									if ( tipo.getSelectedIndex( ) == 0 ) {
										tu = "A";
									} else {
										tu = "U";
									}
									try {
										if ( cone.modificarUsuario( paterno.getText( ), materno.getText( ), nombre.getText( ), login.getText( ), password.getPassword( ), tu, String.valueOf( idconsultor ) ) ) {
											JOptionPane.showMessageDialog( null, "La Modificaci�n se realiz� con �xito" );
											Lconsultores = cone.GetConsultores( );
											llenarConsultores( );
											Limpiar( );
										}
									} catch ( HeadlessException e1 ) {
										JOptionPane.showMessageDialog( null, "Servidor fuera de servicio (Error al Modificar usuario) ", "Error", JOptionPane.ERROR_MESSAGE );
									}
								}
							} else {
								JOptionPane.showMessageDialog( null, "password no coincide", "verifica", JOptionPane.WARNING_MESSAGE );
							}
						}
					} else if ( eliminar.isSelected( ) ) {
						/**
						 * Secci�n para eliminar informaci�n
						 * de los usuarios
						 */
						if ( vacio( ) == false ) {
							if ( pass1.toString( ).equals( pass2.toString( ) ) == true ) {
								int res = JOptionPane.showConfirmDialog( null, "Deseas eliminar el usuario", "Confirmaci�n", JOptionPane.YES_NO_OPTION );
								if ( res == JOptionPane.YES_OPTION ) {
									try {
										if ( cone.eliminarUsuario( String.valueOf( idconsultor ) ) == true ) {
											JOptionPane.showMessageDialog( null, "La eliminaci�n se realiz� con �xito" );
											Lconsultores = cone.GetConsultores( );
											llenarConsultores( );
											Limpiar( );
										} else {
											JOptionPane.showMessageDialog( null, "No se puede eliminar el consultor ", "Problema", JOptionPane.WARNING_MESSAGE );
										}
									} catch ( HeadlessException e1 ) {
										JOptionPane.showMessageDialog( null, "Error del servidor: (No se puede eliminar el consultor) ", "Error", JOptionPane.ERROR_MESSAGE );
									}// fin de try
								}// fin de option_yes
							}// fin de comparacion de cadenas
						}// fin de vacio en la comparcion con falso
					}// fin de eliminar
					else {
						JOptionPane.showMessageDialog( null, "Selecciona una Opci�n ha Realizar" );
					}

				}// fin de action performed
			} );// fin de action listener

			etipo = new JLabel( );
			Pdatos.add( etipo );
			etipo.setText( "Tipo Usuario:" );
			etipo.setBounds( 38, 223, 89, 25 );

			ComboBoxModel < String > tipoModel = new DefaultComboBoxModel < String >( new String [ ] {
			        "Administrador", "Empleado"
			} );

			tipo = new JComboBox < String >( );
			Pdatos.add( tipo );
			tipo.setModel( tipoModel );
			tipo.setBounds( 121, 220, 191, 27 );

		} catch ( Exception e ) {
			e.printStackTrace( );
		}
	}// fin de inicio de ventana

	/**
	 * Funci�n Vacio() ->
	 * Verifica si uno o mas campos esta vacio o no
	 * JpasswordField..gettext Error ->> JpasswordField.getPassword
	 * 
	 * @return true si alguno esta vacio / false si todos tienen contenido
	 */
	public boolean vacio ( ) {
		String strPaterno = paterno.getText( ).trim( );
		String strMaterno = materno.getText( ).trim( );
		String strNombre = nombre.getText( ).trim( );
		String strLogin = login.getText( ).trim( );
		String strPass = new String( password.getPassword( ) ).trim( );
		String strPassConfirm = new String( password2.getPassword( ) ).trim( );
		if ( strPaterno.isEmpty( ) || strMaterno.isEmpty( ) || strNombre.isEmpty( ) || strLogin.isEmpty( ) || strPass.isEmpty( ) || strPassConfirm.isEmpty( ) ) {
			JOptionPane.showMessageDialog( null, "Uno o varios campos est�n vac�os", "Verifica", JOptionPane.WARNING_MESSAGE );
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Procedimiento llenarConsultores() ->
	 * LLena la lista de contultores que se muestra en el ComboBox de la GUI
	 * con el Formato Nombre(s), Apellido Paterno, Apellido Materno
	 */
	public void llenarConsultores ( ) {
		Cusuarios.removeAllItems( );
		Consultor con = new Consultor( );
		for ( int i = 0 ; i < Lconsultores.size( ) ; i++ ) {
			con = ( Consultor ) Lconsultores.get( i );
			Cusuarios.addItem( con.getNombre( ) + " " + con.getPaterno( ) + " " + con.getMaterno( ) );
		}
		if ( Cusuarios.getItemCount( ) > 0 ) {
			Cusuarios.setSelectedItem( 0 );
		}
	}

	/**
	 * Procedimiento Limpiar() ->
	 * Limpia los campos de la GUI
	 */
	public void Limpiar ( ) {
		paterno.setText( "" );
		materno.setText( "" );
		nombre.setText( "" );
		login.setText( "" );
		password.setText( "" );
		password2.setText( "" );
	}

	/**
	 * Procedimiento llenarTXT ->
	 * LLena los campos JtextField con la Informaci�n del usuario seleccionado
	 * en el ComboBox
	 * Si encuentra un usuario de tipo Administrador cambia el tipo de
	 * selecci�n en el comboBox
	 */
	public void llenarTXT ( ) {
		if ( ( Lconsultores != null ) && ( Cusuarios.getSelectedIndex( ) > -1 ) ) {
			Consultor con = new Consultor( );
			con = ( Consultor ) Lconsultores.get( Cusuarios.getSelectedIndex( ) );
			paterno.setText( con.getPaterno( ) );
			materno.setText( con.getMaterno( ) );
			nombre.setText( con.getNombre( ) );
			login.setText( con.getLogin( ) );
			if ( con.getTipousu( ).equals( "A" ) == true ) {
				tipo.setSelectedIndex( 0 );
			} else {
				tipo.setSelectedIndex( 1 );
			}
			password.setText( con.getPass( ).toString( ) );
			password2.setText( con.getPass( ).toString( ) );
			idconsultor = con.getIdConsultor( );
		}
	}
}
