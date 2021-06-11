package kr.hogink.mbti.MBTILovers.web.friend;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static kr.hogink.mbti.MBTILovers.web.friend.Friend.RelationType.*;
import static kr.hogink.mbti.MBTILovers.web.login.LoginType.USER_UID_COOKIE;

@Log4j2
@Controller
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

//    @GetMapping(value = "/")
//    public String list(Model model, @CookieValue(name = USER_UID_COOKIE) String cookieUid) {
//        List<Friend> friends = friendService.getListRelationFriend(cookieUid);
//        if (!friends.isEmpty()) {
//            Collections.sort(friends, FriendController::compare);
//        }
//        model.addAttribute("friends", friends);
//
//        return "friend/friendsList";
//    }

    //보낸 친구 신청 리스트
    @GetMapping(value = "/friends/request")
    public String requestList(Model model, @CookieValue(name = USER_UID_COOKIE) String cookieUid) {
        List<Friend> friends = friendService.getListRelationRequest(cookieUid);
        if (!friends.isEmpty()) {
            Collections.sort(friends, FriendController::compare);
        }
        model.addAttribute("friends", friends);

        return "friend/requestList";
    }

    //받은 친구 신청 리스트
    @GetMapping(value = "/friends/received")
    public String receivedList(Model model, @CookieValue(name = USER_UID_COOKIE) String cookieUid) {
        List<Friend> friends = friendService.getListRelationReceived(cookieUid);
        if (!friends.isEmpty()) {
            Collections.sort(friends, FriendController::compare);
        }
        model.addAttribute("friends", friends);

        return "friend/receivedList";
    }

    @GetMapping(value = "/friends/block")
    public String blockList(Model model, @CookieValue(name = USER_UID_COOKIE) String cookieUid) {
        List<Friend> friends = friendService.getListRelationBlock(cookieUid);
        if (!friends.isEmpty()) {
            Collections.sort(friends, FriendController::compare);
        }
        model.addAttribute("friends", friends);

        return "friend/blockList";
    }

    // 친구 수락
    @PostMapping("/friends/acceptRequest")
    public String accept(FriendDTO friendDTO, @CookieValue(name = USER_UID_COOKIE) String cookieUid) {

        log.info("친구 수락" + "나 : " + cookieUid + "상대방 : " + friendDTO.getFid());
        Friend friend = friendService.getFriend(cookieUid, friendDTO.getFid());
        //관계를 "친구"로 설정
        friend.setRelation(FRIEND);
        friendService.saveFriend(friend);

        return "redirect:/";
    }


    // 친구 신청
    @PostMapping("/friends/request")
    public String request(FriendDTO friendDTO, @CookieValue(name = USER_UID_COOKIE) String cookieUid) {

        log.info("친구 신청" + "나 : " + cookieUid + "상대방 : " + friendDTO.getFid());
        Friend friend = friendService.getFriend(cookieUid, friendDTO.getFid());
        //관계를 "친구 신청"로 설정
        friend.setRelation(REQUEST);
        friendService.saveFriend(friend);

        return "redirect:/friends/request";
    }

    // 친구 신청 취소
    @PostMapping("/friends/cancelRequest")
    public String cancel(FriendDTO friendDTO, @CookieValue(name = USER_UID_COOKIE) String cookieUid) {
        log.info("친구 신청 취소" + "나 : " + cookieUid + "상대방 : " + friendDTO.getFid());
        friendService.removeRecord(cookieUid, friendDTO.getFid());
        return "redirect:/";
    }

    // 친구 차단
    @PostMapping("/friends/block")
    public String block(FriendDTO friendDTO, @CookieValue(name = USER_UID_COOKIE) String cookieUid) {

        log.info("친구 차단" + "나 : " + cookieUid + "상대방 : " + friendDTO.getFid());
        Friend friend = friendService.getFriend(cookieUid, friendDTO.getFid());
        //관계를 "차단"으로 설정
        friend.setRelation(BLOCK);
        friendService.saveFriend(friend);

        return "redirect:/";
    }

    //차단 취소
    @PostMapping("/friends/cancelBlock")
    public String cancelBlock(FriendDTO friendDTO, @CookieValue(name = USER_UID_COOKIE) String cookieUid) {

        log.info("친구 차단 취소" + "나 : " + cookieUid + "상대방 : " + friendDTO.getFid());
        Friend friend = friendService.getFriend(cookieUid, friendDTO.getFid());
        //채팅내역 없는 경우 삭제
        if (friend.getRoom() == null) {
            friendService.removeRecord(cookieUid, friendDTO.getFid());
        }
        friend.setRelation(NONE);
        friendService.saveFriend(friend);
        return "redirect:/";
    }


    public static int compare(Friend a, Friend b) {
        //오름차순 정렬
        String nameA = a.getFriendMember().getName();
        String nameB = b.getFriendMember().getName();
        return nameA.compareTo(nameB);

    }

}
