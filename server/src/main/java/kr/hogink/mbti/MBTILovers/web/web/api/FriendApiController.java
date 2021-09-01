package kr.hogink.mbti.MBTILovers.web.web.api;

import kr.hogink.mbti.MBTILovers.web.domain.friend.Friend;
import kr.hogink.mbti.MBTILovers.web.domain.friend.FriendService;
import kr.hogink.mbti.MBTILovers.web.web.dto.FriendListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static kr.hogink.mbti.MBTILovers.web.domain.friend.Friend.RelationType.*;

@Log4j2
@RestController
@RequiredArgsConstructor
public class FriendApiController {

    private final FriendService friendService;

    @GetMapping(value = "/api/friends/{uid}/friendList")
    public List<FriendListDto> list(@PathVariable String uid) {
        List<Friend> friends = friendService.getListAsc(uid, FRIEND);
        return friends.stream().map(FriendListDto::new).collect(Collectors.toList());
    }

    @GetMapping(value = "/api/friends/{uid}/requestList")
    public List<FriendListDto> requestList(@PathVariable String uid) {
        //신청 목록(REQUEST)
        List<Friend> friends = friendService.getListAsc(uid, REQUEST);
        return friends.stream().map(FriendListDto::new).collect(Collectors.toList());
    }

    @GetMapping(value = "/api/friends/{uid}/receivedList")
    public List<FriendListDto> receivedList(@PathVariable String uid) {
        //받은 신청 목록
        List<Friend> friends = friendService.getListAsc(uid, RECEIVED_REQUEST);
        return friends.stream().map(FriendListDto::new).collect(Collectors.toList());
    }

    @GetMapping(value = "/api/friends/{uid}/blockList")
    public List<FriendListDto> blockList(@PathVariable String uid) {
        //차단한 유저 목록
        List<Friend> friends = friendService.getListAsc(uid, BLOCK);
        return friends.stream().map(FriendListDto::new).collect(Collectors.toList());
    }


//    // 친구 수락
//    @PostMapping("/friends/acceptRequest")
//    public String accept(FriendDto friendDTO, @CookieValue(name = USER_UID_COOKIE) String cookieUid) {
//
//        log.info("친구 수락" + "나 : " + cookieUid + "상대방 : " + friendDTO.getFid());
//        Friend friend = friendService.getFriend(cookieUid, friendDTO.getFid());
//        //관계를 "친구"로 설정
//        friend.setRelation(FRIEND);
//        friendService.saveFriend(friend);
//
//        return "redirect:/";
//    }
//
//
//    // 친구 신청
//    @PostMapping("/friends/request")
//    public String request(FriendDto friendDTO, @CookieValue(name = USER_UID_COOKIE) String cookieUid) {
//
//        log.info("친구 신청" + "나 : " + cookieUid + "상대방 : " + friendDTO.getFid());
//        Friend friend = friendService.getFriend(cookieUid, friendDTO.getFid());
//        //관계를 "친구 신청"로 설정
//        friend.setRelation(REQUEST);
//        friendService.saveFriend(friend);
//
//        return "redirect:/friends/request";
//    }
//
//    // 친구 신청 취소
//    @PostMapping("/friends/cancelRequest")
//    public String cancel(FriendDto friendDTO, @CookieValue(name = USER_UID_COOKIE) String cookieUid) {
//        log.info("친구 신청 취소" + "나 : " + cookieUid + "상대방 : " + friendDTO.getFid());
//        friendService.removeRecord(cookieUid, friendDTO.getFid());
//        return "redirect:/";
//    }
//
//    // 친구 차단
//    @PostMapping("/friends/block")
//    public String block(FriendDto friendDTO, @CookieValue(name = USER_UID_COOKIE) String cookieUid) {
//
//        log.info("친구 차단" + "나 : " + cookieUid + "상대방 : " + friendDTO.getFid());
//        Friend friend = friendService.getFriend(cookieUid, friendDTO.getFid());
//        //관계를 "차단"으로 설정
//        friend.setRelation(BLOCK);
//        friendService.saveFriend(friend);
//
//        return "redirect:/";
//    }
//
//    //차단 취소
//    @PostMapping("/friends/cancelBlock")
//    public String cancelBlock(FriendDto friendDTO, @CookieValue(name = USER_UID_COOKIE) String cookieUid) {
//
//        log.info("친구 차단 취소 " + "나 : " + cookieUid + "상대방 : " + friendDTO.getFid());
//        Friend friend = friendService.getFriend(cookieUid, friendDTO.getFid());
//        //채팅내역 없는 경우 삭제
//        if (friend.getRoom() == null) {
//            friendService.removeRecord(cookieUid, friendDTO.getFid());
//        }
//        friend.setRelation(NONE);
//        friendService.saveFriend(friend);
//        return "redirect:/";
//    }


}
