package Model.Entity;
/*
 * Jose Eduardo Hernandez Tapia 
 */
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;


public class Proyecto {

	private int idproyecto;
	private int idtipo;
	private String proyecto;
	private String descripcion;
	private Date inicio;
	private Date fin;
	private String comentarios;

	public Proyecto()
	{
		idproyecto = -1;
	}
	/**
	 * @return the comentarios
	 */
	public String getComentarios() {
		return comentarios;
	}

	/**
	 * @param comentarios
	 *            the comentarios to set
	 */
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	/**
	 * 
	 * @param idproyecto
	 */
	public void setIdproyecto(int idproyecto) {
		this.idproyecto = idproyecto;
	}

	/**
	 * 
	 * @return
	 */
	public int getIdproyecto() {
		return this.idproyecto;
	}

	/**
	 * 
	 * @param idtipo
	 */
	public void setIdtipo(int idtipo) {
		this.idtipo = idtipo;
	}

	/**
	 * 
	 * @return
	 */
	public int getIdtipo() {
		return this.idtipo;
	}

	/**
	 * 
	 * @param proyecto
	 */
	public void setProyecto(String proyecto) {
		this.proyecto = proyecto;
	}

	/**
	 * 
	 * @return
	 */
	public String getProyecto() {
		return this.proyecto;
	}

	/**
	 * 
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * 
	 * @return
	 */
	public String getDescripcion() {
		return this.descripcion;
	}

	/**
	 * 
	 * @param inicio
	 */
	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	/**
	 * 
	 * @return
	 */
	public Date getInicio() {
		return this.inicio;
	}

	/**
	 * 
	 * @param fin
	 */
	public void setFin(Date fin) {
		this.fin = fin;
	}

	/**
	 * 
	 * @return
	 */
	public Date getFin() {
		return this.fin;
	}
	
	public String toString() {
		return this.proyecto;
	}
	
	/**
	 * Inserta o actualiza el proyecto en la base de datos
	 * @return verdaderos si se guardo falso en caso contrario
	 */
	public boolean save()
	{
		boolean estado = false;
	
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String sinicio = sdf.format(this.inicio);
		String sfin = sdf.format(this.fin);
		String sql;
		BDConexion bd = new BDConexion();
		if(idproyecto == -1)
		{	
			sql = "INSERT INTO `generadores`.`proyecto` (`idtipo`,`inicio`,`fin`,`descripcion`,`proyecto`,`comentarios`) "
						+ "VALUES ('"+  this.idtipo +"',	'" + sinicio +"','"+ sfin +"', '"+ descripcion +"','"+ proyecto +"','"+ comentarios +"')";
			estado = bd.ejecutar(sql);
			if(estado)
			{
				sql = " SELECT LAST_INSERT_ID() as id";
				ResultSet rs = bd.consultar(sql);
				try {
					rs.next();
					this.idproyecto = rs.getInt("id");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else
		{
			
			sql = "UPDATE `generadores`.`proyecto` SET"
					+ "`idtipo` = "+ idtipo + ","
					+ "`inicio` = '"+ sinicio +"',"
					+ "`fin` = '" + sfin + "',"
					+ "`descripcion` = '" + descripcion + "',"
					+ "`proyecto` = '"+ proyecto +"',"
					+ "`comentarios` = '"+ comentarios +"' "
					+ "WHERE `idproyecto` = " + idproyecto;
			estado = bd.ejecutar(sql);
		}
		return estado;
	}
	
	/**
	 * Obtiene un proyecto buscandolo por id
	 * @param idProyecto
	 * @return si lo encontro el objeto proyecto en caso contrario nulo
	 */
	public static Proyecto findById(Integer idProyecto)
	{
		String sql = "SELECT idproyecto, idtipo, proyecto, descripcion, inicio, fin, comentarios FROM proyecto WHERE idproyecto = " + idProyecto;
		BDConexion bd = new BDConexion();
		ResultSet rs = bd.consultar(sql);
		Proyecto proyecto = null;
		try {
			rs.next();
			proyecto = new Proyecto();
			proyecto.setIdproyecto(rs.getInt("idproyecto"));
			proyecto.setIdtipo(rs.getInt("idtipo"));
			proyecto.setProyecto(rs.getString("proyecto"));
			proyecto.setDescripcion(rs.getString("descripcion"));
			proyecto.setInicio(rs.getDate("inicio"));
			proyecto.setFin(rs.getDate("fin"));
		} catch (SQLException e) {
			proyecto = null;
			e.printStackTrace();
		}
		return proyecto;
	}
	/**
	 * Obtiene un proyecto buscandolo por nombre
	 * @param nombre 
	 * @return si lo encontro el objeto proyecto en caso contrario nulo
	 */
	public static Proyecto findByNombre(String nombre)
	{
		String sql = "SELECT idproyecto, idtipo, proyecto, descripcion, inicio, fin, comentarios FROM proyecto WHERE proyecto = '" + nombre + "'";
		BDConexion bd = new BDConexion();
		ResultSet rs = bd.consultar(sql);
		Proyecto proyecto = null;
		try {
			if(rs.next())
			{
				proyecto = new Proyecto();
				proyecto.setIdproyecto(rs.getInt("idproyecto"));
				proyecto.setIdtipo(rs.getInt("idtipo"));
				proyecto.setProyecto(rs.getString("proyecto"));
				proyecto.setDescripcion(rs.getString("descripcion"));
				proyecto.setInicio(rs.getDate("inicio"));
				proyecto.setFin(rs.getDate("fin"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return proyecto;
	}
	/**
	 * Obtiene todos los proyectos de la base de datos
	 * @return lista con los proyectos
	 */
	public static LinkedList<Proyecto> findAll()
	{
		LinkedList<Proyecto> proye = new LinkedList<Proyecto>();
		BDConexion bd = new BDConexion();
	
		String sql = " SELECT idproyecto, idtipo, proyecto, descripcion, inicio, fin, comentarios FROM proyecto";
		try {
			ResultSet rs = bd.consultar(sql);
			while (rs.next()) {
				Proyecto proyecto = new Proyecto();
				proyecto.setIdproyecto(rs.getInt("idproyecto"));
				proyecto.setIdtipo(rs.getInt("idtipo"));
				proyecto.setProyecto(rs.getString("proyecto"));
				proyecto.setDescripcion(rs.getString("descripcion"));
				proyecto.setInicio(rs.getDate("inicio"));
				proyecto.setFin(rs.getDate("fin"));
				proye.add(proyecto);
			}
			bd.cerrar();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return proye;
	}
	
	/**
	 * Elimina un proyecto de la base de datos
	 * @return verdadero si lo elimina falso en caso contrario
	 */
	public boolean delete() {
		String sql = "DELETE FROM `generadores`.`proyecto` WHERE idproyecto = " + idproyecto;
		BDConexion bd = new BDConexion();
		boolean estado = bd.ejecutar(sql);
		return estado;
	}
}