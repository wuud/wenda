package cn.wenda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ErrorController {
	
	@RequestMapping(value="/errors")
	public String toerror(Model model,@RequestParam("msg")String message) {
		model.addAttribute("message",message);
		return "error";
	}

}
