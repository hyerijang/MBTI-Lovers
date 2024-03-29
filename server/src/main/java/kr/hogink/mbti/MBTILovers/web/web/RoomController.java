package kr.hogink.mbti.MBTILovers.web.web;

import kr.hogink.mbti.MBTILovers.web.domain.chat.room.Room;
import kr.hogink.mbti.MBTILovers.web.domain.friend.Friend;
import kr.hogink.mbti.MBTILovers.web.domain.friend.FriendService;
import kr.hogink.mbti.MBTILovers.web.domain.member.Member;
import kr.hogink.mbti.MBTILovers.web.login.LoginType;
import kr.hogink.mbti.MBTILovers.web.service.MemberService;
import kr.hogink.mbti.MBTILovers.web.service.chat.room.RoomService;
import kr.hogink.mbti.MBTILovers.web.web.dto.chat.RoomDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static kr.hogink.mbti.MBTILovers.web.login.LoginType.USER_UID_COOKIE;

@Controller
@SessionAttributes(LoginType.USER_MEMBER_SESSION)
@Log4j2
@RequiredArgsConstructor
public class RoomController {


    private final FriendService friendService;
    private final RoomService roomService;
    private final MemberService memberService;


    private static int compare(Friend a, Friend b) {
        Room roomA = a.getRoom();
        Room roomB = b.getRoom();

        if (roomA != null && roomB != null) {
            Timestamp aTime = a.getRoom().getLastSentTimeAt();
            Timestamp bTime = b.getRoom().getLastSentTimeAt();
            if (aTime == null)
                aTime = new Timestamp(System.currentTimeMillis());
            if (bTime == null)
                bTime = new Timestamp(System.currentTimeMillis());
            return bTime.compareTo(aTime);
        }


        log.warn("room 정보가 없습니다.");
        return 0;
    }


    // 모든 채팅방 목록 반환
    @GetMapping(value = "/chatList")

    public String list(Model model, @CookieValue(name = USER_UID_COOKIE) String cookieUid) {
        List<Friend> roomMappingList = friendService.getListAllFriend(cookieUid);

        //최신순으로 정렬
        if (!roomMappingList.isEmpty()) {
            //리스트에서 제외
            roomMappingList.removeIf(friend -> {
                //보낸 메세지가 없는 경우 삭제
                if (friend.getRoom() == null) {
                    //room이 생성되지 않은 경우 삭제
                    return true;
                } else return friend.getRoom().getLastSentTimeAt() == null;
            });
            roomMappingList.sort(RoomController::compare);

        }

        model.addAttribute("rooms", roomMappingList);
        return "chat/chatList";
    }


    // 채팅방 생성
    @PostMapping("/room")
    public String createRoom(RoomDto roomDTO, @CookieValue(name = USER_UID_COOKIE) String cookieUid) {

        Friend friend = friendService.getFriendInfo(cookieUid, roomDTO.getFid()).get();
        if (friend != null) {
            //방생성 및 친구객체에 방정보 저장
            if (friend.getRoom() == null) {
                Room room = new Room();
                roomService.createChatRoom(room);
                friend.setRoom(room);
                friendService.saveFriend(friend);
            } else
                log.info("이미 생성된 방이 존재합니다.");
            return "redirect:/chat/enter/" + friend.getRoom().getRid();
        }

        log.error("채팅방 생성 실패");
        return "redirect:/";
    }


    //채팅방 입장화면 {rid}를 통해 입장
    @GetMapping(value = "/chat/enter/{rid}")
    public String createFrom(@ModelAttribute(LoginType.USER_MEMBER_SESSION) Member user,
                             Model model,
                             @PathVariable Long rid) {
        //세션 정보로 sender 설정
        Room room = roomService.findRoomByRid(rid);

        //room 정보
        model.addAttribute("rid", room.getRid());
        model.addAttribute("sender", user.getName());
        model.addAttribute("senderUid", user.getUid());

        //친구 정보
        Optional<Friend> friend = friendService.getFriendName(user.getUid(), room);

        log.info(room.getRid() + "번 채팅방에 " + user.getUid() + "님 입장");

        String fid = null;
        if (friend.isPresent()) {
            fid = friend.get().getFid();
            Optional<Member> fMember = memberService.findOneByUid(fid);
            if (fMember.isPresent()) {
                model.addAttribute("fid", fid);
                model.addAttribute("fname", fMember.get().getName());
                model.addAttribute("f_profileImage", fMember.get().getProfileImage());
                model.addAttribute("f_relation", friend.get().getRelation());
                if (friend.get().getRelation() == Friend.RelationType.BLOCKED)
                    log.info("상대 유저로부터 차단되었습니다.");
                else if (friend.get().getRelation() == Friend.RelationType.BLOCK)
                    log.info("내가 차단한 유저입니다.");
            }
        }

        return "chat/roomdetail";

    }

    private String getFriendName(String uid, Room room) {

        Optional<Friend> f = friendService.getFriendName(uid, room);
        if (f.isPresent()) {
            String friendUid = f.get().getFid();
            return memberService.findOneByUid(friendUid).get().getName();
        } else {
            throw new IllegalStateException("친구 정보가 없습니다.");
        }

    }


}