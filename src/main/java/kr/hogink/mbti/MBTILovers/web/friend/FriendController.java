package kr.hogink.mbti.MBTILovers.web.friend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kr.hogink.mbti.MBTILovers.web.login.LoginController.USER_COOKIE;

@Controller
public class FriendController {

    FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }


    @GetMapping(value = "/friends")
    public String list(Model model, @CookieValue(name = USER_COOKIE) String cookieUid) {
        List<Friend> friends = friendService.findAllByUid(cookieUid);
        if (friends != null)
            model.addAttribute("friends", friends);
        return "members/friendsList";
    }

    // 채팅방 생성
    @PostMapping("/friends/acceptRequest")
    public String Accept(FriendDTO friendDTO, @CookieValue(name = USER_COOKIE) String cookieUid) {
        Friend friend = new Friend();
        friend.setUid(cookieUid);
        friend.setFid(friendDTO.getFid());
        friend.setRelation("친구");
        friendService.addFriend(friend);
        return "redirect:/friends";
    }
}
