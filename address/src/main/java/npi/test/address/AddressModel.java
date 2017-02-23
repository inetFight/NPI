package npi.test.address;

public class AddressModel {

private String city;
private String street;
private String postCode;

public AddressModel(String city, String street, String postCode) {
	super();
	this.city = city;
	this.street = street;
	this.postCode = postCode;
}
public String getCity() {
	return city;
}
public void setCity(String city) {
	this.city = city;
}
public String getStreet() {
	return street;
}
public void setStreet(String street) {
	this.street = street;
}
public String getPostCode() {
	return postCode;
}
public void setPostCode(String postCode) {
	this.postCode = postCode;
}


}