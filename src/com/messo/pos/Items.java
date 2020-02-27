package com.messo.pos;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Items {
	
	private IntegerProperty id;
	private StringProperty name;
	private DoubleProperty price;
	private StringProperty category;
	
	public Items(){}
	
	public Items(int id, String name, Double price, String category){
		this.id = new SimpleIntegerProperty(id);
		this.name = new SimpleStringProperty(name);
		this.price = new SimpleDoubleProperty(price);
		this.category = new SimpleStringProperty(category);
	}
	
	public int getId() {
		return id.get();
	}
	public void setId(int id) {
		this.id = new SimpleIntegerProperty(id);
	}
	public IntegerProperty getIdProperty(){
		return this.id;
	}
	public String getName() {
		return this.name.get();
	}
	public void setName(String name) {
		this.name = new SimpleStringProperty(name);
	}
	public StringProperty getNameProperty(){
		return this.name;
	}
	public Double getPrice() {
		return price.get();
	}
	public void setPrice(Double price) {
		this.price = new SimpleDoubleProperty(price);
	}
	public DoubleProperty getPriceProperty(){
		return this.price;
	}
	public String getCategory() {
		return category.get();
	}
	public void setCategory(String category) {
		this.category = new SimpleStringProperty(category);
	}
	public StringProperty getCategoryProperty(){
		return this.category;
	}

}
