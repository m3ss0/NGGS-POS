package com.messo.pos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class Comanda {
	
	private ObservableMap<Integer, ComandaEntry> lista = FXCollections.observableHashMap();
	private Double totale;
	
	public void add(Integer id, ComandaEntry comanda){
		if (lista.get(id) != null) lista.remove(id);
		if (comanda.getQuantity() != 0)lista.put(id, comanda);
	}
	
	public ObservableMap<Integer, ComandaEntry> getLista() {
		return lista;
	}

	public Double getTotale() {
		totale = lista.values().stream().mapToDouble(e -> e.getPrice()*e.getQuantity()).sum();
		return totale;
	}

}
