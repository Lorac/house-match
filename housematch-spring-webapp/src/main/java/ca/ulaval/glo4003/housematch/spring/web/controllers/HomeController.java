package ca.ulaval.glo4003.housematch.spring.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes({"username"})
public class HomeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public final ModelAndView displayHomepage(HttpSession session) {
        return new ModelAndView("home");
    }
}
