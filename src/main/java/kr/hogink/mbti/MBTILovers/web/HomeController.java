package kr.hogink.mbti.MBTILovers.web;

import kr.hogink.mbti.MBTILovers.web.login.LoginController;
import kr.hogink.mbti.MBTILovers.web.member.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();

        if (session.getAttribute(LoginController.USER_SESSION) != null) {
            Member member = (Member) session.getAttribute(LoginController.USER_SESSION);

            if (member!= null)
                model.addAttribute("uid", member.getUid());
        }
        return "home";
    }
}
