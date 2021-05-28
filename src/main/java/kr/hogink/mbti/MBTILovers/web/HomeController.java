package kr.hogink.mbti.MBTILovers.web;

import kr.hogink.mbti.MBTILovers.web.friend.Friend;
import kr.hogink.mbti.MBTILovers.web.friend.FriendController;
import kr.hogink.mbti.MBTILovers.web.friend.FriendService;
import kr.hogink.mbti.MBTILovers.web.login.LoginType;
import kr.hogink.mbti.MBTILovers.web.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

@Controller
@SessionAttributes(LoginType.USER_MEMBER_SESSION)
@Log4j2
@RequiredArgsConstructor
public class HomeController {

    private final FriendService friendService;

    @GetMapping("/")
    public String home(Model model, @ModelAttribute(LoginType.USER_MEMBER_SESSION) Member member) {

        List<Friend> friends = friendService.getListRelationFriend(member.getUid());
        if (!friends.isEmpty()) {
            Collections.sort(friends, FriendController::compare);
        }
        model.addAttribute("friends", friends);

        return "friend/friendsList";
    }

    @GetMapping("/mypage")
    public String myPage(Model model, @ModelAttribute(LoginType.USER_MEMBER_SESSION) Member member) {
        if (member != null) {
            model.addAttribute("uid", member.getUid());
            model.addAttribute("user", member);
        }
        return "home";
    }

    @GetMapping("/favicon.ico")
    public String favicon() {
        log.warn("/favicon.ico 로 접근");
        return "redirect:/";
    }


}
