package com.messo.pos;

import javafx.beans.property.IntegerProperty;

public class ComandaEntry extends Items{

	private IntegerProperty quantity;
	
	public ComandaEntry(Items item, IntegerProperty quantity){
		super(item.getId(),item.getName(),item.getPrice(),item.getCategory());
		this.quantity = quantity;
	}

	public Integer getQuantity() {
		return quantity.get();
	}

	public void setQuantity(Integer quantity) {
		this.quantity.set(quantity);
	}
}
