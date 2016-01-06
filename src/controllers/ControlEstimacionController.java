package controllers;

import java.util.LinkedList;
import java.util.Vector;

import javax.swing.JDesktopPane;
import javax.swing.JRadioButton;
import Manejodetablas2.ControlTableGenerador;
import MetodosRemotos.Metodos;
import Model.Entity.Consultor;
import ObjetosSerializables.Rgenerador;
import Views.ControlEstimaciones;
import Views.Estimaciones;

public class ControlEstimacionController {
	private ControlEstimaciones vista;
	private Metodos cone;
	
	public ControlEstimacionController(Metodos cone , JDesktopPane escritorio2)
	{
		this.cone = cone;
		this.vista = new ControlEstimaciones(cone,escritorio2,this);
	}
	public void dispose()
	{
		this.vista.dispose();
	}
	public boolean isClosed()
	{
		return this.vista.isClosed();
	}
	public ControlEstimaciones getVista()
	{
		return this.vista;
	}
	
	public void llenarTodos(LinkedList < Rgenerador > LSeleccion,JRadioButton  modificar,JRadioButton  agregar,Estimaciones esti, int idestimacion, String nombre, ControlTableGenerador control3)
	{
		Rgenerador ob = new Rgenerador( );
		LSeleccion = new LinkedList < Rgenerador >( );
		if ( modificar.isSelected( ) ) {
			LSeleccion = cone.reportegeneral( String.valueOf( esti.Nestimacion( ) ) );
		}
		if ( agregar.isSelected( ) ) {
			LSeleccion = cone.reportegeneral( String.valueOf( idestimacion ) );
		}
		for ( int i = 0 ; i < LSeleccion.size( ) ; i++ ) {
			ob = ( Rgenerador ) LSeleccion.get( i );
			if ( ob.getPartida( ).equals( nombre ) == true ) {
				Rgenerador meter = new Rgenerador( );
				meter.setIdaspecto( String.valueOf( ob.getIdaspecto( ) ) );
				meter.setClave( ob.getClave( ) );
				meter.setDescripcion( ob.getDescripcion( ) );
				meter.setUnidad( ob.getUnidad( ) );
				meter.setX( ob.getX( ) );
				meter.setY( ob.getY( ) );
				meter.setZ( ob.getZ( ) );
				meter.setAlto( ob.getAlto( ) );
				meter.setLargo( ob.getLargo( ) );
				meter.setAncho( ob.getAncho( ) );
				meter.setCantidad( "0" );
				meter.setPiezas( ob.getCantidad( ) );
				meter.setCosto( String.valueOf( ob.getCosto( ) ) );
				meter.setImporte( ob.getImporte( ) );
				meter.setRepeticion( ob.getRepeticion( ) );
				meter.setFecha( ob.getFecha( ) );
				control3.anhadeFila( meter );
			}
		}
	}
	public Vector<Consultor> getConsultores() {
		Vector<Consultor> consultores = new Vector<Consultor>();
		LinkedList<Consultor> tmConsultores = Consultor.findAll();
		for(Consultor c : tmConsultores)
		{
			consultores.add(c);
		}
		return consultores;
	}
}
