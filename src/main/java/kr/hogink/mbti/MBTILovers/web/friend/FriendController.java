package kr.hogink.mbti.MBTILovers.web.friend;

import kr.hogink.mbti.MBTILovers.web.member.Member;
import kr.hogink.mbti.MBTILovers.web.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static kr.hogink.mbti.MBTILovers.web.friend.Friend.RelationType.FRIEND;
import static kr.hogink.mbti.MBTILovers.web.login.LoginType.USER_UID_COOKIE;

@Log4j2
@Controller
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;
    private final MemberService memberService;

    @GetMapping(value = "/friends")
    public String list(Model model, @CookieValue(name = USER_UID_COOKIE) String cookieUid) {
        List<Friend> friends = friendService.findAllByUid(cookieUid);
        if (!friends.isEmpty()) {
            Collections.sort(friends, FriendController::compare);
        }
        model.addAttribute("friends", friends);

        return "friend/friendsList";
    }

    // 친구 수락
    @PostMapping("/friends/acceptRequest")
    public String Accept(FriendDTO friendDTO, @CookieValue(name = USER_UID_COOKIE) String cookieUid) {

        log.info("친구추가");
        log.info("나 : " + cookieUid);
        log.info("상대방 : " + friendDTO.getFid());
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

    private static int compare(Friend a, Friend b) {
        //오름차순 정렬
        String nameA = a.getFriendMember().getName();
        String nameB = b.getFriendMember().getName();
        return nameA.compareTo(nameB);

    }

}
