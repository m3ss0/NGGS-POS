package com.messo.pos.db;

import com.messo.pos.ComandaEntry;
import com.messo.pos.Items;

import javafx.beans.property.IntegerProperty;

public class Orders extends ComandaEntry{

	public Orders(Items item, IntegerProperty quantity, Double priceTotal, String data) {
		super(item, quantity);
		this.priceTotal = priceTotal;
		this.data = data;
	}

	private Double priceTotal;
	private String data;
	

	public Double getPriceTotal() {
		return priceTotal;
	}

	public void setPriceTotal(Double priceTotal) {
		this.priceTotal = priceTotal;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}


}
