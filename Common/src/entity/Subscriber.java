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

}
