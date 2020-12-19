package entity;

public class Subscriber extends Person {
	private String subscriberNumber;
	private int subscriberFamilyMembers;
	private String subscriberCardDetails;
	private String phone;
	private boolean isGuide;
	public Subscriber(String subscriberNumber, String id, String firstName, String lastName, String phone, String mail,
			int subscriberFamilyMembers, String subscriberCardDetails,boolean isGuide) {
		super(id, firstName, lastName, mail);
		this.phone=phone;
		this.subscriberCardDetails = subscriberCardDetails;
		this.subscriberFamilyMembers =subscriberFamilyMembers;
		this.subscriberNumber = subscriberNumber;
		this.isGuide=isGuide;
	}

	public String toString() {
		return "Subscriber" + " " + subscriberNumber + " " + super.getID() + " " + getFirstName() + " "
				+ getLastName() + " " + phone + " " + getEmail() + " " + subscriberFamilyMembers + " "
				+ subscriberCardDetails;
	}

	public String getSubscriberNumber() {
		return subscriberNumber;
	}


	public int getSubscriberFamilyMembers() {
		return subscriberFamilyMembers;
	}

	public String getSubscriberCardDetails() {
		return subscriberCardDetails;
	}
	public String getPhone() {
		return phone;
	}


	public boolean getIsGuide() {
		return isGuide;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
