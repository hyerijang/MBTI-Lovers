package kr.hogink.mbti.MBTILovers.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    private final String LOGIN = "login";
    @GetMapping("/")
    public String home(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        String uid = (String)session.getAttribute(LOGIN);
        model.addAttribute("uid",uid);

        return "home";
    }
}
