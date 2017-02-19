package pl.pgf.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class City {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long Id;

	private String name;

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "city")
	private List<Customer> listOfCustomer;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public List<Customer> getListOfCustomer() {
		return listOfCustomer;
	}

	public void setListOfCustomer(List<Customer> listOfCustomer) {
		this.listOfCustomer = listOfCustomer;
	}

	public City(String name) {

		super();
		this.name = name;

	}

	public City() {

	}

}
