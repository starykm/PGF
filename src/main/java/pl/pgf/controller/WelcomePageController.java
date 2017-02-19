package pl.pgf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.pgf.services.CityService;

@Controller
public class WelcomePageController {
	@Autowired
	CityService cityService;

	public static boolean loaded = false;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	String showWelcomePage(Model model) {

		if (!loaded) {
			cityService.setFakeDataCity();
			loaded = true;
		}

		return "index";
	}

	@RequestMapping(value = "index", method = RequestMethod.GET)
	String showWelcomePageIndex(Model model) {

		if (!loaded) {
			cityService.setFakeDataCity();
			loaded = true;
		}

		return "index";
	}

}
