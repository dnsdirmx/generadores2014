package Model.EntityCRUD;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import Model.Config.SQL;
import ObjetosSerializables.Consultor;

/**
 * clase para las operaciones a la base de datos de los consultores
 * @author Luis Angel Hern�ndez L�zaro
 *
 */
public class ConsultorCRUD {
	
	/**
	 * m�todo para recuperar la informaci�nd e todos los consultores en la base de datos....
	 * s�lo se recuperan id, nombre, ap paterno, ap materno ... por que s�lo son necesarios en las nuevas actualizaciones, si m�s adelante se agregan los dem�s atributos del consultor no existe problema.
	 * @return
	 * @throws SQLException
	 */
	public static LinkedList<Consultor> findConsultors () throws SQLException{
		LinkedList<Consultor> listconConsultors = new LinkedList<Consultor>();
		String query="select idConsultor, paterno, materno, nombre from consultor;";
		ResultSet result = SQL.query(query);
		while(result!= null && result.next()) {
			Consultor consultor = new Consultor();
			consultor.setIdconsultor(result.getInt("idConsultor"));
			consultor.setNombre(result.getString("nombre"));
			consultor.setPaterno(result.getString("paterno"));
			consultor.setMaterno(result.getString("materno"));
			listconConsultors.add(consultor);
		}
		SQL.close();
		return listconConsultors;
	}

}
