package kr.hogink.mbti.MBTILovers.web.friend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static kr.hogink.mbti.MBTILovers.web.login.LoginController.USER_COOKIE;

@Controller
public class FriendController {

    @Autowired
    FriendService friendService;

    @GetMapping(value = "/friends")
    public String list(Model model, @CookieValue(name = USER_COOKIE) String cookieUid) {
        List<Friend> friends = friendService.findAllByUid(cookieUid);
        model.addAttribute("friends", friends);
        return "members/friendsList";
    }
}
