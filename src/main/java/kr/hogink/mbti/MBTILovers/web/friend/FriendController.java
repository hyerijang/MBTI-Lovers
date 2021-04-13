package kr.hogink.mbti.MBTILovers.web.friend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static kr.hogink.mbti.MBTILovers.web.friend.Friend.RelationType.FRIEND;
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

    // 친구 수락
    @PostMapping("/friends/acceptRequest")
    public String Accept(FriendDTO friendDTO, @CookieValue(name = USER_COOKIE) String cookieUid) {
        Friend friend = getFriend(cookieUid, friendDTO.getFid());
        friendService.saveFriend(friend);
        return "redirect:/friends";
    }

    public Friend getFriend(String uid, String fid) {

        Optional<Friend> f = friendService.getFriendInfo(uid, fid);

        //이미 존재하는 친구
        if (f.isPresent())
            return f.get();

        Friend friend = new Friend();
        friend.setUid(uid);
        friend.setFid(fid);
        friend.setRelation(FRIEND);
        return friend;
    }
}
