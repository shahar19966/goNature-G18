package entity;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Subscriber extends Person {
	private SimpleStringProperty subscriberNumber;
	private SimpleIntegerProperty subscriberFamilyMembers;
	private SimpleStringProperty subscriberCardDetails;
	private SimpleStringProperty phone;
	private SimpleBooleanProperty isGuide;
	public Subscriber(String subscriberNumber, String id, String firstName, String lastName, String phone, String mail,
			int subscriberFamilyMembers, String subscriberCardDetails,boolean isGuide) {
		super(id, firstName, lastName, mail);
		this.subscriberCardDetails = new SimpleStringProperty(subscriberCardDetails);
		this.subscriberFamilyMembers = new SimpleIntegerProperty(subscriberFamilyMembers);
		this.subscriberNumber = new SimpleStringProperty(subscriberNumber);
		this.isGuide=new SimpleBooleanProperty(isGuide);
	}

	public String toString() {
		return "Subscriber" + " " + subscriberNumber.getValue() + " " + super.getID() + " " + getFirstName() + " "
				+ getLastName() + " " + phone.getValue() + " " + getEmail() + " " + subscriberFamilyMembers.getValue() + " "
				+ subscriberCardDetails.getValue();
	}

	public String getSubscriberNumber() {
		return subscriberNumber.getValue();
	}

	public SimpleStringProperty getSubscriberNumberProperty() {
		return subscriberNumber;
	}

	public int getSubscriberFamilyMembers() {
		return subscriberFamilyMembers.getValue();
	}
	public SimpleIntegerProperty getSubscriberFamilyMembersProperty() {
		return subscriberFamilyMembers;
	}
	public String getSubscriberCardDetails() {
		return subscriberCardDetails.getValue();
	}

	public SimpleStringProperty getSubscriberCardDetailsProperty() {
		return subscriberCardDetails;
	}
	public boolean getIsGuide() {
		return isGuide.getValue();
	}

	public SimpleBooleanProperty getIsGuideProperty() {
		return isGuide;
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
		Subscriber other = (Subscriber) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.getValue().equals(other.id.getValue()))
			return false;
		return true;
	}

}
