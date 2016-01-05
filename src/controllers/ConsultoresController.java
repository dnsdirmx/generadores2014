package controllers;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import Views.ConsultoresView;
import models.ConsultorModel;

public class ConsultoresController {
	private ConsultoresView vista;
	private ConsultorModel consultor;
	private Hashtable<Integer,ConsultorModel> consultores;
	
	public ConsultoresController() {
		vista = new ConsultoresView(this);
	}
	public ConsultoresView getVista() {
		return vista;
	}
	public List<ConsultorModel> getConsultores()
	{
		List<ConsultorModel> nombres = new ArrayList<ConsultorModel>();
		Hashtable<Integer,ConsultorModel> tb =  ConsultorModel.findAll();
		Set<Entry<Integer, ConsultorModel>> entradas = tb.entrySet();
		Iterator<Entry<Integer, ConsultorModel>> it = entradas.iterator();
		while(it.hasNext())
		{
			ConsultorModel cm = it.next().getValue();
			nombres.add(cm);
		}	
		return nombres;	
	}
	public boolean update(ConsultorModel consultor,String nombre, String paterno, String materno, String login, char[] cs, int tipousu) {
		consultor.setNombre(nombre);
		consultor.setPaterno(paterno);
		consultor.setMaterno(materno);
		consultor.setLogin(login);
		consultor.setPass(new String(cs));
		if(tipousu == 0)
			consultor.setTipousu("A");
		else
			consultor.setTipousu("U");
		return consultor.save();
	}
	public boolean create(String nombre, String paterno, String materno, String login, char[] cs, int tipousu) {
		ConsultorModel consultor = new ConsultorModel();
		consultor.setNombre(nombre);
		consultor.setPaterno(paterno);
		consultor.setMaterno(materno);
		consultor.setLogin(login);
		consultor.setPass(new String(cs));
		if(tipousu == 0)
			consultor.setTipousu("A");
		else
			consultor.setTipousu("U");
		return consultor.save();
	}
	public boolean destroy(ConsultorModel consultor) {
		return consultor.delete();
	}
}
