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

import pl.pgf.model.City;
import pl.pgf.model.HelperFormObj;
import pl.pgf.services.CityService;

@Controller
public class MainPageController {

	@Autowired
	private CityService cityService;

	@GetMapping("/mainPage")
	public String showPage(Model model) {

		addArgToModel(model);

		if (!WelcomePageController.loaded) {

			return "redirect:index";

		}
		return "mainPage";
	}

	@RequestMapping(value = "/mainPage/customer", method = RequestMethod.GET)
	public String handleClick(@RequestParam String customerName, @ModelAttribute City item, Model model) {
		model.addAttribute("customerList", cityService.getCityData(customerName));
		model.addAttribute("name", customerName);
		return "mainPage :: customer ";

	}

	@RequestMapping(value = "/mainPage", method = RequestMethod.POST)
	public String redirect(@RequestParam(value = "action", required = true) String action) {

		if (action.equals("edit page >")) {
			return "redirect:editPage";
		}

		else if (action.equals("add page >")) {
			return "redirect:addPage";
		}
		return "redirect:mainPage";
	}

	private void addArgToModel(Model model) {

		String name = "";
		try {
			name = getCustomerList().get(0).getName();
		} catch (IndexOutOfBoundsException e) {

			name = "";
		}

		model.addAttribute("helperObj", new HelperFormObj());
		model.addAttribute("itemlist", getCustomerList());
		model.addAttribute("customerList", cityService.getCityData(name));

	}

	private List<City> getCustomerList() {

		return cityService.getCustomerData();

	}

}