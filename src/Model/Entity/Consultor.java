package Model.Entity;
/*
 * Jose Eduardo Hernandez Tapia 
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Vector;


public class Consultor {
	private Integer idConsultor;
	private String paterno;
	private String materno;
	private String nombre;
	private String login;
	private String pass;
	private String tipousu;
	
	public Consultor()
	{
		idConsultor = -1;
	}
	public Integer getIdConsultor() {
		return idConsultor;
	}
	public void setIdConsultor(Integer idConsultor) {
		this.idConsultor = idConsultor;
	}
	public String getPaterno() {
		return paterno;
	}
	public void setPaterno(String paterno) {
		this.paterno = paterno;
	}
	public String getMaterno() {
		return materno;
	}
	public void setMaterno(String materno) {
		this.materno = materno;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getTipousu() {
		return tipousu;
	}
	public void setTipousu(String tipousu) {
		this.tipousu = tipousu;
	}
	
	/**
	 * inserta o actualiza el concepto en la base de datos
	 * @return verdadero si se actualizo o inserto correctamente
	 */
	public boolean save()
	{
		boolean estado = false;
		String sql = "";
		if(this.idConsultor == -1)
		{
			sql = "INSERT INTO `generadores`.`consultor` (`paterno`,`materno`,`nombre`,`login`,`pass`,`tipousu`) "
						+ "VALUES ('" + this.paterno + "','"+ this.materno +"','" + this.nombre + "','" + this.login + "','" + this.pass + "','" + this.tipousu + "')";
		}
		else
		{
			sql = "UPDATE `generadores`.`consultor` "
					+ "SET `paterno` = '"+ this.paterno+"', `materno` = '" + this.materno + "', `nombre` = '" + this.nombre + "', `login` = '" + this.login + "', `pass` = '"+ this.pass+"', `tipousu` = '" + this.tipousu + "' "
					+ "WHERE `idConsultor` = " + this.idConsultor;
		}
		BDConexion bd = new BDConexion();
		estado = bd.ejecutar(sql);
		if(this.idConsultor == -1 && estado)
		{
			sql = " SELECT LAST_INSERT_ID()";
			ResultSet rs = bd.consultar(sql);
			
			try {
				rs.next();
				this.idConsultor = rs.getInt(0);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		bd.cerrar();
		return estado;
	}
	/**
	 * elimina el consultor actual de la base de datos
	 * @return verdadero si se elimino correctamente falso en caso contrario
	 */
	public boolean delete()
	{
		boolean estado = false;
		String sql = "DELETE FROM `generadores`.`consultor` WHERE `idConsultor` = " + this.idConsultor; 
		BDConexion bd = new BDConexion();
		estado = bd.ejecutar(sql);
		bd.cerrar();
		return estado;
	}
	/**
	 * devuelve todos los consultores de la base de datos
	 * @return lista con los consulotes
	 */
	public static LinkedList<Consultor> findAll() {
		LinkedList<Consultor> llConsultores = new LinkedList<Consultor>();
		BDConexion bd = new BDConexion();
		String[] campos = {"*"}; 
		ResultSet rs =  bd.consulta("consultor", campos);
		
		try {
			while(rs.next())
			{
				Consultor consultor = new Consultor();
				consultor.setIdConsultor(rs.getInt("idConsultor"));
				consultor.setPaterno(rs.getString("paterno"));
				consultor.setMaterno(rs.getString("materno"));
				consultor.setNombre(rs.getString("nombre"));
				consultor.setLogin(rs.getString("login"));
				consultor.setPass(rs.getString("pass"));
				consultor.setTipousu(rs.getString("tipousu"));
				llConsultores.add(consultor);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		bd.cerrar();
		return llConsultores;
	}
	/**
	 * Devuleve todos los consultores en un vector
	 * @return vector de consultores
	 */
	public static Vector<Consultor> getConsultores()
	{
		Consultor consultor;
		ResultSet rs = null;
		Vector<Consultor> Lconsultores = new Vector<Consultor>();
		String sql = " SELECT c.idConsultor, c.paterno, c.materno, c.nombre, c.login, c.pass, c.tipousu " + " FROM consultor AS c " + " ORDER BY c.nombre ASC, c.paterno ASC, c.nombre ASC ";
		try {
			BDConexion bd = new BDConexion();
			bd.consultar(sql);
			while (rs.next() == true) {
				consultor = new Consultor();
				consultor.setIdConsultor(rs.getInt("idConsultor"));
				consultor.setPaterno(rs.getString("paterno"));
				consultor.setMaterno(rs.getString("materno"));
				consultor.setNombre(rs.getString("nombre"));
				consultor.setLogin(rs.getString("login"));
				consultor.setPass(rs.getString("pass"));
				consultor.setTipousu(rs.getString("tipousu"));
				Lconsultores.add(consultor);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return Lconsultores;
	}
	//TODO vaciar 
	public String toString()
	{
		return nombre + " " + paterno + " " + materno;
	}
}
