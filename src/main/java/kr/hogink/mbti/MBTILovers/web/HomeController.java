package kr.hogink.mbti.MBTILovers.web;

import kr.hogink.mbti.MBTILovers.web.login.LoginType;
import kr.hogink.mbti.MBTILovers.web.member.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();

        if (session.getAttribute(LoginType.USER_MEMBER_SESSION) != null) {
            Member member = (Member) session.getAttribute(LoginType.USER_MEMBER_SESSION);

            if (member != null) {
                model.addAttribute("uid", member.getUid());
                model.addAttribute("user", member);
            }
        }
        return "home";
    }

    //파일 업로드 테스트
    @GetMapping("/file")
    public String file() {
        return "fileUploadTest";
    }

    @GetMapping("/matching")
    public String matching() {
        return "matching";
    }
}
