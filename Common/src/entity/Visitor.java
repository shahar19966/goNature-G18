package entity;

import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;

public class Visitor implements Serializable{
	private SimpleStringProperty id;

	public Visitor(String id) {
		this.id=new SimpleStringProperty(id);
	}

	public SimpleStringProperty getId() {
		return id;
	}

	public void setId(SimpleStringProperty id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.getValue().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Visitor other = (Visitor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.getValue().equals(other.id.getValue()))
			return false;
		return true;
	}
	
}
