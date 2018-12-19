package cn.wenda.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.wenda.service.UserService;

@Controller
public class LoginController {

	@Autowired
	UserService userService;

	@RequestMapping("/login")
	public String login(Model model,@RequestParam(value="next",required=false) String next) {
		if(StringUtils.isNotBlank(next))
			model.addAttribute("next",next);
		return "login";
	}

	@RequestMapping(value = "/reg/", method = RequestMethod.POST)
	public String doReg(Model model, 
			@RequestParam("name") String name, 
			@RequestParam("password") String password,
			@RequestParam(value="next",required=false) String next,
			HttpServletResponse response) {
		Map<String, String> map = userService.register(name, password);
		if(map.containsKey("ticket")) {
			Cookie cookie=new Cookie("ticket", map.get("ticket"));
			cookie.setPath("/");
			response.addCookie(cookie);
			if(StringUtils.isNotBlank(next))
				return "redirect:"+next;
			return "redirect:/";
		}else{
			model.addAttribute("error",map.get("error"));
			return "login";
		}
	}
	@RequestMapping(value = "/login/", method = RequestMethod.POST)
	public String doLogin(Model model, 
			@RequestParam("name") String name, 
			@RequestParam("password") String password,
			@RequestParam(value="rememberme",defaultValue="false") boolean rememberme,
			@RequestParam(value="next",required=false) String next,
			HttpServletResponse response) {
		Map<String, String> map = userService.login(name, password);
		if(map.containsKey("ticket")) {
			Cookie cookie=new Cookie("ticket", map.get("ticket"));
			cookie.setPath("/");
			 if (rememberme) {
                 cookie.setMaxAge(3600*24*5);
             }
			response.addCookie(cookie);
			System.err.println(next);
			if(StringUtils.isNotBlank(next))
				return "redirect:"+next;
			return "redirect:/";
		}else{
			model.addAttribute("error",map.get("error"));
			return "login";
		}
	}
	@RequestMapping("/logout")
	public String logout(@CookieValue("ticket") String ticket) {
		userService.logout(ticket);
		return "redirect:/";
	}

}
