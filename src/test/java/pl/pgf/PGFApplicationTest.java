package pl.pgf;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import pl.pgf.PGFApplication;
import pl.pgf.model.City;
import pl.pgf.model.Customer;
import pl.pgf.model.HelperFormObj;
import pl.pgf.services.CityService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PGFApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PGFApplicationTest {

	private CityService cityService = null;

	@Test
	public void testController() {

		HelperFormObj helperobj = new HelperFormObj(new City("Lodz"), new Customer("Marek"));

		String name = helperobj.getCustomer().getName();

		assertEquals("Marek", name);

	}

	@Test(expected = NullPointerException.class)
	public void testNullName() {

		HelperFormObj helperobj = new HelperFormObj();

		helperobj.getCustomer().getName();

	}

	@Test
	public void testSaveCustomer() {

		cityService = Mockito.mock(CityService.class);
		Customer customer = new Customer("Marek");
		City city = new City("Lodz");
		List<Customer> list = new ArrayList<Customer>();
		city.setListOfCustomer(list);
		customer.setCity(city);

		city.getListOfCustomer().add(customer);

		Mockito.doNothing().when(cityService).saveCity(city);

	}
}
