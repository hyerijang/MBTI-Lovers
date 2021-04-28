package kr.hogink.mbti.MBTILovers.web;

import kr.hogink.mbti.MBTILovers.web.login.LoginType;
import kr.hogink.mbti.MBTILovers.web.member.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes(LoginType.USER_MEMBER_SESSION)
public class HomeController {
    @GetMapping("/")
    public String home(Model model, @ModelAttribute(LoginType.USER_MEMBER_SESSION) Member member) {
        if (member != null) {
            model.addAttribute("uid", member.getUid());
            model.addAttribute("user", member);
        }
        return "home";
    }


    @GetMapping("/matching")
    public String matching() {
        return "matching";
    }
}
