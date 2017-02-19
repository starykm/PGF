package pl.pgf.model;

public class HelperFormObj {

	private City city;

	private Customer customer;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public HelperFormObj() {

	}

	public HelperFormObj(City city, Customer customer) {

		super();
		this.city = city;
		this.customer = customer;

	}
}
