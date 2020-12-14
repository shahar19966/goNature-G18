package entity;

import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;

public class Visitor implements Serializable{
	private SimpleStringProperty id;

	public Visitor(SimpleStringProperty id) {
		this.id = id;
	}

	public SimpleStringProperty getId() {
		return id;
	}

	public void setId(SimpleStringProperty id) {
		this.id = id;
	}


}
