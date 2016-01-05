package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

public class ConsultorModel {
	private Integer idConsultor;
	private String paterno;
	private String materno;
	private String nombre;
	private String login;
	private String pass;
	private String tipousu;
	
	public ConsultorModel()
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
		if(this.idConsultor == -1)
		{
			sql = " SELECT LAST_INSERT_ID()";
			ResultSet rs = bd.consultar(sql);
			
			try {
				rs.next();
				this.idConsultor = rs.getInt(0);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		bd.cerrar();
		return estado;
	}
	public boolean delete()
	{
		boolean estado = false;
		String sql = "DELETE FROM `generadores`.`consultor` WHERE `idConsultor` = " + this.idConsultor; 
		BDConexion bd = new BDConexion();
		estado = bd.ejecutar(sql);
		bd.cerrar();
		return estado;
	}
	public static ConsultorModel findConsultor(Integer idConsultor)
	{
		ConsultorModel consultor = null;
		return consultor;
	}
	public static Hashtable<Integer,ConsultorModel> findAll()
	{
		Hashtable<Integer,ConsultorModel> consultores = null;
		BDConexion bd = new BDConexion();
		ResultSet rs = bd.consultar("SELECT * FROM generadores.consultor");
		try {
			if (rs != null)
				consultores = new Hashtable<Integer,ConsultorModel>();
			while(rs.next())
			{
				ConsultorModel consultor = new ConsultorModel();
				consultor.setIdConsultor(rs.getInt("idConsultor"));
				consultor.setPaterno(rs.getString("paterno"));
				consultor.setMaterno(rs.getString("materno"));
				consultor.setNombre(rs.getString("nombre"));
				consultor.setLogin(rs.getString("login"));
				consultor.setPass(rs.getString("pass"));
				consultor.setTipousu(rs.getString("tipousu"));
				consultores.put(consultor.getIdConsultor(), consultor);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bd.cerrar();
		return consultores; 
	}
	public String toString()
	{
		return nombre + " " + paterno + " " + materno;
	}
}
