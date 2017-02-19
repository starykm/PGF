package pl.pgf.controller;

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
import pl.pgf.dao.CustomerDao;
import pl.pgf.model.City;
import pl.pgf.model.Customer;
import pl.pgf.model.HelperFormObj;
import pl.pgf.services.CityService;

@Controller
public class EditPageController {

	@Autowired
	private CityService cityService;
	@Autowired
	private CityDao cityDao;

	@Autowired
	private CustomerDao customerDao;

	private static boolean redirect = false;

	public static boolean loaded = false;

	@GetMapping("/editPage")
	public String showEditPage(Model model, final RedirectAttributes redirectAttributes) {

		addArgToModel(model);
		if (!WelcomePageController.loaded) {

			return "redirect:index";

		}
		return "editPage";
	}

	@RequestMapping(value = "/editPage", method = RequestMethod.POST)
	public String save(final RedirectAttributes redirectAttributes, @ModelAttribute HelperFormObj item,
			@RequestParam(value = "action", required = true) String action) {

		String name = null;
		if (action.equals("save")) {
			try {
				name = item.getCustomer().getName();
			} catch (NullPointerException e) {

				redirectAttributes.addFlashAttribute("warning", "There is no selected City to assign");

			}
			if (name != null) {
				Customer customer = new Customer(item.getCustomer().getName().toUpperCase());
				City city = cityDao.findByName(item.getCity().getName());
				if (customer.getName().equals("")) {

					redirectAttributes.addFlashAttribute("warning", "Nothing saved because Customer not written");

					return "redirect:editPage";
				}

				customer.setCity(city);

				city.getListOfCustomer().add(customer);

				cityService.saveCity(city);

				redirectAttributes.addFlashAttribute("warning",
						customer.getName() + " successfully added" + " to City " + city.getName());
				redirectAttributes.addFlashAttribute("name", city.getName());
				redirect = true;
			}
		} else if (action.equals("add page >")) {

			return "redirect:addPage";
		} else if (action.equals("main page >")) {
			return "redirect:mainPage";
		}

		return "redirect:editPage";
	}

	@RequestMapping(value = "/editPage/customer/filter", method = RequestMethod.GET)
	public String handleClick(@RequestParam String customerName, @ModelAttribute City item, Model model) {

		model.addAttribute("customerList", cityService.getCityData(customerName));
		model.addAttribute("name", customerName);
		return "editPage :: customer ";

	}

	@RequestMapping(value = "/editPage/customer", method = RequestMethod.GET)
	public String delete(@RequestParam(required = true) final String name, Model model) {

		Customer customer = customerDao.findByName(name);
		customerDao.delete(customer);
		return "redirect:/editPage";

	}

	private List<City> getCustomerList() {

		return cityService.getCustomerData();

	}

	private void addArgToModel(Model model) {
		String name = "";

		try {
			if (redirect) {
				name = (String) model.asMap().get("name");

			} else
				name = getCustomerList().get(0).getName();
		} catch (IndexOutOfBoundsException e) {

			name = "";
		}

		model.addAttribute("wybrany", name);
		model.addAttribute("helperObj", new HelperFormObj());
		model.addAttribute("itemlist", getCustomerList());
		model.addAttribute("customerList", cityService.getCityData(name));
		redirect = false;
	}

}
