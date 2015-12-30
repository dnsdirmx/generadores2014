package Consultores.Controller;

import java.sql.SQLException;
import java.util.LinkedList;

import Model.Entity.HistorialConsultor;
import Model.EntityCRUD.ConsultorCRUD;
import Model.EntityCRUD.HistorialConsultorCRUD;
import Model.EntityCRUD.ProyectoCRUD;
import ObjetosSerializables.Consultor;
import ObjetosSerializables.Proyecto;

/**
 * Clase para realizar las llamadas al modelo de la reasignaci�n para los consultores 
 * @author Luis Angel Hern�ndez L�zaro
 *
 */
public class CReasignacion {
	
	/**
	 * Guardar una reasignaci�n en la base de datos
	 * @param historial -  objeto a guardar
	 * @return int = id_asginaci�n agregada
	 * @throws SQLException
	 */
	public static int save (HistorialConsultor historial) throws SQLException{
		return HistorialConsultorCRUD.save(historial);
	}
	
	/**
	 * Recuperaci�n de las reasignaciones de un consultor en especifico por medio de su identificador 
	 * @param idConsultor
	 * @return lista de los historiales del consultor seleccionado
	 * @throws SQLException
	 */
	public static LinkedList<HistorialConsultor> findHistorialConsultor (int idConsultor) throws SQLException{
		return HistorialConsultorCRUD.findHistorialConsultor(idConsultor);
	}
	
	/**
	 * Recuperaci�n de los consultores de la base de datos
	 * @return lista de consultores en la base de datos
	 * @throws SQLException
	 */
	public static LinkedList<Consultor> findConsultors() throws SQLException{
		return ConsultorCRUD.findConsultors();
	}
	
	/**
	 * Recuperaci�n de los proyectos en la base de datos
	 * @return lista de proyectos en la base de datos
	 * @throws SQLException
	 */
	public static LinkedList<Proyecto> findProyectos () throws SQLException{
		return ProyectoCRUD.findProyectos();
	}
	
	/**
	 * recuperaci�n de los proyectos donde colabora un consultor, aplicando un filtro por el identificador del consultor
	 * @param idConsultor -- identificador del consultor para obtener los proyectos donde ha realizado estimaciones
	 * @return lista de proyectos
	 * @throws SQLException
	 */
	public static LinkedList<Proyecto> findProyecto (int idConsultor) throws SQLException{
		return HistorialConsultorCRUD.findProyecto(idConsultor);
	}
	
	/**
	 * recuperaci�n de los consultores que colaboran en un proyecto, aplicando un filtro por el identificador de proyecto
	 * @param idProyecto -- identificador del proyecto para obtener los consultores que han realizado estimaciones
	 * @return lista de consultores
	 * @throws SQLException
	 */
	public static LinkedList<Consultor> findConsultor (int idProyecto) throws SQLException{
		return HistorialConsultorCRUD.findConsultor(idProyecto);
	}

}
