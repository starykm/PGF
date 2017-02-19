package pl.pgf.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pl.pgf.dao.CityDao;
import pl.pgf.model.City;
import pl.pgf.model.Customer;
import pl.pgf.model.HelperFormObj;
import pl.pgf.services.CityService;

@Controller
public class AddPageController {

	@Autowired
	private CityService cityService;

	@Autowired
	CityDao cityDao;

	@GetMapping("/addPage")
	public String showEditPage(Model model, final RedirectAttributes redirectAttributes) {

		addArgToModel(model);

		if (!WelcomePageController.loaded) {

			return "redirect:index";

		}
		return "addPage";
	}

	@RequestMapping(value = "/addPage", method = RequestMethod.POST)
	public String save(final RedirectAttributes redirectAttributes, @ModelAttribute HelperFormObj item,
			@RequestParam(value = "action", required = true) String action) {

		if (action.equals("save")) {

			String nameCustomer = item.getCustomer().getName();
			String nameCity = item.getCity().getName();

			if (nameCustomer.equals("")) {

				redirectAttributes.addFlashAttribute("warning", "Nothing saved because Customer  not written");

				return "redirect:addPage";
			} else if (nameCity.equals("")) {
				redirectAttributes.addFlashAttribute("warning", "Nothing saved because City not written");

				return "redirect:addPage";
			}
			Customer customer = new Customer(item.getCustomer().getName().toUpperCase());
			City city = cityDao.findByName(nameCity);

			try {
				city.getListOfCustomer().add(customer);
			} catch (NullPointerException e) {
				city = new City(item.getCity().getName());
				List<Customer> list = new ArrayList<Customer>();
				city.setListOfCustomer(list);
			}
			customer.setCity(city);
			city.getListOfCustomer().add(customer);

			cityService.saveCity(city);

			redirectAttributes.addFlashAttribute("warning",
					customer.getName() + " successfully added" + " to City " + city.getName());
			redirectAttributes.addFlashAttribute("name", city.getName());

		} else if (action.equals("edit page >")) {

			return "redirect:editPage";
		} else if (action.equals("main page >")) {

			return "redirect:mainPage";
		}

		return "redirect:addPage";
	}

	private void addArgToModel(Model model) {

		model.addAttribute("helperObj", new HelperFormObj());
	}
}
