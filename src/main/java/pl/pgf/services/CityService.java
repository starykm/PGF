package pl.pgf.services;

import static java.util.Comparator.comparing;
import static java.util.Comparator.naturalOrder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.pgf.dao.CityDao;
import pl.pgf.dao.CustomerDao;
import pl.pgf.model.City;
import pl.pgf.model.Customer;

@Component
@Transactional
public class CityService {

	@Autowired
	private CityDao cityDao;

	@Autowired
	private CustomerDao customerDao;

	Comparator<Customer> comparator = comparing(Customer::getName, naturalOrder());

	public List<Customer> getCityData() {

		List<Customer> cityList = new ArrayList<Customer>();
		cityList = customerDao.findAll();

		// cityList=cityList.stream().sorted(comparator).collect(Collectors.toList());
		java.util.Collections.sort(cityList, comparator);

		return cityList;

	}

	public List<City> getCustomerData() {

		List<City> list = new ArrayList<City>();
		list = cityDao.findAll();

		List<City> itemCustomerList = list.stream().collect(Collectors.toList());

		return itemCustomerList;

	}

	public List<Customer> getCityData(String name) {

		List<Customer> itemCityList = new ArrayList<Customer>();

		City city = cityDao.findByName(name);
		if (city != null) {

			itemCityList = city.getListOfCustomer().stream().collect(Collectors.toList());
			Collections.sort(itemCityList, comparator);
		}
		return itemCityList;

	}

	public void setFakeDataCity() {

		List<Customer> itemList = new ArrayList<Customer>();

		itemList.add(new Customer("AUDI"));
		itemList.add(new Customer("BMW"));
		itemList.add(new Customer("Vw"));
		itemList.add(new Customer("SAAB"));

		setFakeDataCustomer(itemList);

		for (Customer item : itemList) {

			customerDao.save(item);

		}

	}

	private void setFakeDataCustomer(List<Customer> setList) {

		List<City> itemList = new ArrayList<City>();
		List<Customer> itemcusList = new ArrayList<Customer>();

		itemcusList.add(new Customer("VOLVO"));
		itemcusList.add(new Customer("TOYOTA"));
		itemcusList.add(new Customer("MERCEDES"));

		City city = new City("Lodz");
		City city2 = new City("Warszawa");
		City city3 = new City("Poznan");
		City city4 = new City("Wroclaw");

		city.setListOfCustomer(setList);
		for (Customer c : setList) {
			c.setCity(city);
		}
		city2.setListOfCustomer(itemcusList);
		for (Customer c : itemcusList) {
			c.setCity(city2);
		}

		itemList.add(city);
		itemList.add(city2);
		itemList.add(city3);
		itemList.add(city4);
		for (City item : itemList) {

			cityDao.save(item);
		}

	}

	public void saveCustomer(Customer customer) {

		customerDao.save(customer);
	}

	public void saveCity(City city) {

		cityDao.save(city);
	}

}
