package Views;
/*
 * Jose Eduardo Hernandez Tapia 
 */
import javax.swing.JInternalFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import Model.Entity.Consultor;
import controllers.ConsultoresController;

import javax.swing.JPasswordField;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;

public class ConsultoresView extends JInternalFrame {

	private static final long serialVersionUID = -5289839603182193539L;
	private JTextField txtPaterno;
	private JTextField txtMaterno;
	private JTextField txtNombre;
	private JTextField txtLogin;
	private JPasswordField txtPassword;
	private JPasswordField txtPasswordConfirma;
	private JComboBox<Consultor> cbUsuarios;
	private JPanel plPrincipal;
	private JPanel plOpciones;
	private JRadioButton rbAgregar;
	private JRadioButton rbModificar ;
	private JRadioButton rbEliminar;
	private JComboBox<String> cbTipoUsuario ;
	private JPanel plFormulario;
	private ButtonGroup bgOpciones;
	private JLabel lbTipoUsuario;
	private JLabel lbPasswordConfirma;
	private JLabel lbPassword;
	private JLabel lbLogin;
	private JLabel lbNombre;
	private JLabel lbAMaterno;
	private JLabel lbApaterno;
	private JButton btnAceptar;
	
	private ConsultoresController controlador;
	/**
	 * Create the frame.
	 * @param controlador 
	 */
	public ConsultoresView(ConsultoresController controlador) {
		this.controlador = controlador;
		/**crea la interfaz **/
		setIconifiable(true);
		setClosable(true);
		setTitle("Registro de consultores");
		setBounds(100, 100, 371, 421);
		setPreferredSize( new java.awt.Dimension( 484, 420 ) );
		plPrincipal = new JPanel();
		plPrincipal.setLayout(null);
		getContentPane().add(plPrincipal, BorderLayout.CENTER);
		
		plOpciones = new JPanel();
		plOpciones.setBorder(new LineBorder(new Color(0, 0, 0)));
		plOpciones.setLayout(null);
		plOpciones.setBounds(6, 6, 345, 71);
		plPrincipal.add(plOpciones);
		
		rbAgregar = new JRadioButton();
		rbAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/** cuando se selecciona agregar radiobutton**/
				seleccionaAgregar();
			}
		});
		rbAgregar.setText("Agregar");
		rbAgregar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rbAgregar.setBounds(17, 11, 73, 20);
		plOpciones.add(rbAgregar);
		
		rbModificar = new JRadioButton();
		rbModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/** cuando se selecciona modificar radiobutton**/
				seleccionaModificar();
			}
		});
		rbModificar.setText("Modificar");
		rbModificar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rbModificar.setBounds(126, 11, 80, 20);
		plOpciones.add(rbModificar);
		
		rbEliminar = new JRadioButton();
		rbEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/** cuando se selecciona eliminar radiobutton**/
				seleccionaEliminar();
			}
		});
		rbEliminar.setText("Eliminar");
		rbEliminar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rbEliminar.setBounds(230, 11, 78, 20);
		plOpciones.add(rbEliminar);
		
		cbUsuarios = new JComboBox<Consultor>();
		cbUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/** cuando se selcccioona un consultor del comobox**/
				Consultor consultor = (Consultor) cbUsuarios.getSelectedItem();
				llenaFormulario(consultor);
			}
		});
		cbUsuarios.setEnabled(false);
		cbUsuarios.setBounds(76, 37, 232, 26);
		plOpciones.add(cbUsuarios);
		
		JLabel label = new JLabel();
		label.setText("Usuarios:");
		label.setBounds(7, 43, 60, 16);
		plOpciones.add(label);
		
		plFormulario = new JPanel();
		plFormulario.setBorder(new LineBorder(new Color(0, 0, 0)));
		plFormulario.setLayout(null);
		plFormulario.setBounds(6, 83, 345, 305);
		plPrincipal.add(plFormulario);
		
		lbApaterno = new JLabel();
		lbApaterno.setText("Apellido Paterno:");
		lbApaterno.setBounds(10, 11, 107, 28);
		plFormulario.add(lbApaterno);
		
		txtPaterno = new JTextField();
		txtPaterno.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				soloLetrasEspacios(arg0);
			}
		});
		txtPaterno.setBounds(121, 11, 191, 28);
		txtPaterno.setBorder( new LineBorder( new java.awt.Color( 0, 0, 0 ), 1, false ) );
		plFormulario.add(txtPaterno);
		
		lbAMaterno = new JLabel();
		lbAMaterno.setText("Apellido Materno:");
		lbAMaterno.setBounds(10, 51, 117, 28);
		plFormulario.add(lbAMaterno);
		
		txtMaterno = new JTextField();
		txtMaterno.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				soloLetrasEspacios(e);
			}
		});
		txtMaterno.setBounds(121, 51, 191, 29);
		txtMaterno.setBorder( new LineBorder( new java.awt.Color( 0, 0, 0 ), 1, false ) );
		plFormulario.add(txtMaterno);
		
		lbNombre = new JLabel();
		lbNombre.setText("Nombre(s):");
		lbNombre.setBounds(47, 85, 70, 28);
		plFormulario.add(lbNombre);
		
		txtNombre = new JTextField();
		txtNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				soloLetrasEspacios(e);
			}
		});
		txtNombre.setBounds(121, 86, 191, 27);
		txtNombre.setBorder( new LineBorder( new java.awt.Color( 0, 0, 0 ), 1, false ) );
		plFormulario.add(txtNombre);
		
		lbLogin = new JLabel();
		lbLogin.setText("Login:");
		lbLogin.setBounds(69, 123, 37, 23);
		plFormulario.add(lbLogin);
		
		txtLogin = new JTextField();
		txtLogin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				soloLetras(e);
			}
		});
		txtLogin.setBounds(121, 122, 191, 25);
		txtLogin.setBorder( new LineBorder( new java.awt.Color( 0, 0, 0 ), 1, false ) );
		plFormulario.add(txtLogin);
		
		lbPassword = new JLabel();
		lbPassword.setText("Contrase\u00F1a:");
		lbPassword.setBounds(41, 152, 77, 25);
		plFormulario.add(lbPassword);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(121, 152, 191, 25);
		txtPassword.setBorder( new LineBorder( new java.awt.Color( 0, 0, 0 ), 1, false ) );
		plFormulario.add(txtPassword);
		
		lbPasswordConfirma = new JLabel();
		lbPasswordConfirma.setText("Repita Contrase\u00F1a:");
		lbPasswordConfirma.setBounds(6, 189, 121, 22);
		plFormulario.add(lbPasswordConfirma);
		
		txtPasswordConfirma = new JPasswordField();
		txtPasswordConfirma.setBounds(131, 188, 181, 25);
		txtPasswordConfirma.setBorder( new LineBorder( new java.awt.Color( 0, 0, 0 ), 1, false ) );
		plFormulario.add(txtPasswordConfirma);
		
		
		ImageIcon Imagenprocesar = new ImageIcon( getClass( ).getResource( "/iconos/proceso.png" ) );
		btnAceptar = new JButton();
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rbModificar.isSelected())
				{
					if(validarFormulario())
					{
						/* actualiza el consultor */
						boolean resultado = ConsultoresView.this.controlador.update((Consultor) cbUsuarios.getSelectedItem(),
								ConsultoresView.this.txtNombre.getText(),
								ConsultoresView.this.txtPaterno.getText(),
								ConsultoresView.this.txtMaterno.getText(),
								ConsultoresView.this.txtLogin.getText(),
								ConsultoresView.this.txtPassword.getPassword(),
								ConsultoresView.this.cbTipoUsuario.getSelectedIndex());
						if(resultado)
							JOptionPane.showMessageDialog(ConsultoresView.this, "Se ha actualizado el consultor");
						else
							JOptionPane.showMessageDialog(ConsultoresView.this, "No se ha podido actualizar el consultor");
						llenarConsultores();
					}
				}
				else if (rbEliminar.isSelected())
					/*eliminar el consultor*/
					if(ConsultoresView.this.controlador.destroy((Consultor) cbUsuarios.getSelectedItem()))
					{
						JOptionPane.showMessageDialog(ConsultoresView.this, "Se ha eliminado el consultor");
						cbUsuarios.removeItem(cbUsuarios.getSelectedItem());
					}
					else
						JOptionPane.showMessageDialog(ConsultoresView.this, "No se ha podido eliminar el consultor");
				else
				{
					if(validarFormulario())
					{
						/*crea el consultor*/
						boolean resultado = ConsultoresView.this.controlador.create(ConsultoresView.this.txtNombre.getText(),
																ConsultoresView.this.txtPaterno.getText(),
																ConsultoresView.this.txtMaterno.getText(),
																ConsultoresView.this.txtLogin.getText(),
																ConsultoresView.this.txtPassword.getPassword(),
																ConsultoresView.this.cbTipoUsuario.getSelectedIndex());// si es 0 es administrador si es1 es usuario
						if(resultado)
						{
							JOptionPane.showMessageDialog(ConsultoresView.this, "Se ha creado el consultor");
							llenarConsultores();
						}
						else
							JOptionPane.showMessageDialog(ConsultoresView.this, "No se ha podido crear el consultor");
					}
				}
				
			}
		});
		btnAceptar.setText("Realizar Operaci\u00F3n");
		btnAceptar.setFont(new Font("Segoe UI", Font.BOLD, 12));
		btnAceptar.setBounds(41, 258, 271, 38);
		btnAceptar.setIcon( Imagenprocesar );
		plFormulario.add(btnAceptar);
		
		lbTipoUsuario = new JLabel();
		lbTipoUsuario.setText("Tipo Usuario:");
		lbTipoUsuario.setBounds(38, 223, 89, 25);
		plFormulario.add(lbTipoUsuario);
		
		ComboBoxModel < String > tipoModel = new DefaultComboBoxModel < String >( new String [ ] {
		        "Administrador", "Empleado"
		} );
		cbTipoUsuario= new JComboBox<String>();
		cbTipoUsuario.setBounds(121, 220, 191, 27);
		cbTipoUsuario.setModel(tipoModel);
		plFormulario.add(cbTipoUsuario);
		
		rbAgregar.setSelected(true);
		bgOpciones = new ButtonGroup();
		bgOpciones.add(rbAgregar);
		bgOpciones.add(rbModificar);
		bgOpciones.add(rbEliminar);
		
		this.setVisible(true);
	}
	
/**
 * Vaalida el formulario de consultores
 * @return verdadero si es correcto
 */
	protected boolean validarFormulario() {
		
		boolean estado = true;
		if(this.txtNombre.getText().isEmpty())
			estado = false;
		if(this.txtPaterno.getText().isEmpty())
			estado = false;
		if(this.txtMaterno.getText().isEmpty())
			estado = false;
		if(this.txtLogin.getText().isEmpty())
			estado = false;
		if(this.txtPassword.getPassword().length <= 0)
			estado = false;
		if(this.txtPasswordConfirma.getPassword().length <= 0)
			estado = false;
		if(!estado)
			JOptionPane.showMessageDialog(ConsultoresView.this, "Uno o mas campos estan vacios");
		String passuno = new String(this.txtPassword.getPassword());
		String passsdos = new String(this.txtPasswordConfirma.getPassword());
		System.out.println(passuno + " "  +  passsdos);
		if(!(passuno.toString().equals( passsdos.toString())) && estado)
		{
			estado = false;
			JOptionPane.showMessageDialog(ConsultoresView.this, "El password no coincide");
		}
		return estado;
	}

/**
 * LLena el formulario con los datos del consultor especificado
 * @param consultor
 */
	protected void llenaFormulario(Consultor consultor) {
		this.txtNombre.setText(consultor.getNombre());
		this.txtPaterno.setText(consultor.getPaterno());
		this.txtMaterno.setText(consultor.getMaterno());
		this.txtLogin.setText(consultor.getLogin());
		this.txtPassword.setText(consultor.getPass());
		this.txtPasswordConfirma.setText(consultor.getPass());
		System.out.println(consultor.getTipousu() + " " + consultor.getTipousu().length());
		if(consultor.getTipousu().charAt(0) == 'A')
			this.cbTipoUsuario.setSelectedIndex(0);
		else
			this.cbTipoUsuario.setSelectedIndex(1);
	}

/**
 * activa los controles para eliminar
 */
	protected void seleccionaEliminar() {
		if(!cbUsuarios.isEnabled())
			cbUsuarios.setEnabled(true);
		llenarConsultores();
	}

/**
 * Activa los controles para modificar
 */
	protected void seleccionaModificar() {
		cbUsuarios.setEnabled(true);
		llenarConsultores();
		
	}

/**
 * Llena el combobox con los consultores de la bd
 */
	private void llenarConsultores() {
		//JOptionPane.showMessageDialog(null, "he");
		cbUsuarios.removeAll();
		//cbUsuarios.removeAllItems();
		List<Consultor> consultores = controlador.getConsultores();
		limpiarFormulario();
		
		
		for(Consultor nombre : consultores)
		{
			cbUsuarios.addItem(nombre);
		}
	}


	protected void soloLetras(KeyEvent arg0) {
		if(!Character.isLetter(arg0.getKeyChar()))
			arg0.consume();
	}	
	protected void soloLetrasEspacios(KeyEvent arg0) {
		if(		!Character.isLetter(arg0.getKeyChar()) 
				&& !Character.isSpaceChar(arg0.getKeyChar()))
			arg0.consume();
	}

	private void limpiarFormulario()
	{
		txtPaterno.setText("");
		txtMaterno.setText("");
		txtNombre.setText("");
		txtLogin.setText("");
		txtPassword.setText("");
		txtPasswordConfirma.setText("");
	}
	//private void habilitaFormulario
	
	private void seleccionaAgregar()
	{ 
		cbUsuarios.setEnabled(false);
		limpiarFormulario();
		
	}
	
	
	

}
