package controllers;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import Model.Entity.Consultor;

import java.util.Set;

import Views.ConsultoresView;

public class ConsultoresController {
	private ConsultoresView vista;
	private Consultor consultor;
	
	public ConsultoresController() {
		vista = new ConsultoresView(this);
	}
	public ConsultoresView getVista() {
		return vista;
	}
	public List<Consultor> getConsultores()
	{
		List<Consultor> nombres = new ArrayList<Consultor>();
		LinkedList<Consultor> tb =  Consultor.findAll();
		for(Consultor c : tb)
		{
			nombres.add(c);
		}
		return nombres;	
	}
	public boolean update(Consultor consultor,String nombre, String paterno, String materno, String login, char[] cs, int tipousu) {
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
		Consultor consultor = new Consultor();
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
	public boolean destroy(Consultor consultor) {
		return consultor.delete();
	}
}
