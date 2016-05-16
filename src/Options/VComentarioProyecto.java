package Options;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;

import MetodosRemotos.Metodos;
import Model.Entity.Proyecto;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

/**
 * clase para ver los comentarios asignados a un proyecto
 * 
 * @author luiiis lazaro
 *
 */
public class VComentarioProyecto extends JInternalFrame {

	/**
     * idproyect --- ID del proyecto para escribir comentarios
     * textArea --- �rea para escribir comentarios
     * lblnombredelproyecto --- etiqueta para el nombre del proyecto
     * btnactualizar --- bot�n para habilitar la edici�n del Textarea 
     * btnagregar --- bot�n para agregar y guardar los comentarios
     * scrollPanel --- scroll para el textArea de los comentarios
     */
	private int idProyect;
	private JTextArea textArea = new JTextArea();
	private JLabel lblNombreDelProyecto = new JLabel("Nombre del Proyecto:");
	private Metodos conn;

	private JButton btnActualizar = new JButton("Actualizar");
	private JButton btnAgregar = new JButton("Guardar y Cerrar");
	private JScrollPane scrollPane = new JScrollPane();

	/**
	 * M�t
	 * @return the conn
	 */
	public Metodos getConn() {
		return conn;
	}

	/**
	 * @param conn
	 *            the conn to set
	 */
	public void setConn(Metodos conn) {
		this.conn = conn;
	}

	/**
	 * @return the idProyect
	 */
	public int getIdProyect() {
		return idProyect;
	}

	/**
	 * @param idProyect
	 *            the idProyect to set
	 */
	public void setIdProyect(int idProyect) {
		this.idProyect = idProyect;
	}

	private static final long serialVersionUID = 1L;

	/**
	 * Create the frame.
	 */
	public VComentarioProyecto(Metodos conexion, int idProyecto) {
		setTitle("Comentarios y Observaciones acerca del Proyecto");
		setClosable(true);
		this.setIdProyect(idProyecto);
		this.setConn(conexion);

		setBounds(10, 10, 450, 331);
		getContentPane().setLayout(null);

		scrollPane.setBounds(10, 36, 414, 223);
		getContentPane().add(scrollPane);

		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);

		btnAgregar.addActionListener(new ActionListener() {
			/**
			 * Secci�n para actualizar los comentarios del proyecto
			 */
			public void actionPerformed(ActionEvent arg0) {
				textArea.setEditable(false);
				Proyecto p = Proyecto.findById(getIdProyect());
				p.setComentarios(textArea.getText());
				p.save();
				
				//getConn().updateCommentsProj(getIdProyect(), textArea.getText());
				dispose();
			}
		});
		btnAgregar.setBounds(222, 270, 202, 23);
		getContentPane().add(btnAgregar);

		btnActualizar.addActionListener(new ActionListener() {
			/**
			 * Secci�n para activar la edici�n de los comentarios
			 */
			public void actionPerformed(ActionEvent arg0) {
				textArea.setEditable(true);
				textArea.requestFocusInWindow();
			}
		});
		btnActualizar.setBounds(10, 270, 129, 23);
		getContentPane().add(btnActualizar);

		lblNombreDelProyecto.setBounds(135, 10, 158, 14);
		getContentPane().add(lblNombreDelProyecto);

		JLabel lblNameproj = new JLabel("");
		lblNameproj.setBounds(178, 10, 55, 16);
		getContentPane().add(lblNameproj);

		JLabel lblNameproject = new JLabel("Nombre del Proyecto:");
		lblNameproject.setBounds(10, 8, 129, 16);
		getContentPane().add(lblNameproject);
		System.out.println("[XX] SACANDO EL TEXTO : " + Proyecto.findById(idProyecto).getComentarios());
		textArea.setText(Proyecto.findById(idProyecto).getComentarios());
		FillCommentsProj();
	}

	/**
	 * Muestra en el �rea de texto los comentarios del proyecto
	 */
	public void FillCommentsProj() {
		//Proyecto proj = conn.getCommentsProj(this.idProyect);
		Proyecto proj = Proyecto.findById(this.idProyect);
		textArea.setText(proj.getComentarios());
		lblNombreDelProyecto.setText(proj.getProyecto());
	}
}
