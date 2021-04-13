package kr.hogink.mbti.MBTILovers.web.chat;

import kr.hogink.mbti.MBTILovers.web.friend.Friend;
import kr.hogink.mbti.MBTILovers.web.friend.FriendService;
import kr.hogink.mbti.MBTILovers.web.login.LoginController;
import kr.hogink.mbti.MBTILovers.web.member.Member;
import kr.hogink.mbti.MBTILovers.web.member.MemberService;
import lombok.extern.flogger.Flogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

import static kr.hogink.mbti.MBTILovers.web.login.LoginController.USER_COOKIE;

@Controller
public class RoomController {

    FriendService friendService;
    RoomService roomService;
    MemberService memberService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public RoomController(FriendService friendService, RoomService roomService, MemberService memberService) {
        this.friendService = friendService;
        this.roomService = roomService;
        this.memberService = memberService;
    }

    // 모든 채팅방 목록 반환
    @GetMapping(value = "/chatList")

    public String list(Model model, @CookieValue(name = USER_COOKIE) String cookieUid) {
        List<Room> rooms = roomService.findAllRoom();

        if (!rooms.isEmpty()) {
            for (Room room : rooms) {
                try {
                    room.setName(getFriendName(cookieUid, room.getRid()));
                } catch (IllegalStateException e) {
                    logger.error(e.getMessage());
                }
                model.addAttribute("rooms", rooms);
            }
        }

        return "chat/chatList";
    }


    // 채팅방 생성
    @PostMapping("/room")
    public String createRoom(RoomDTO roomDTO, @CookieValue(name = USER_COOKIE) String cookieUid) {

        Friend friend = friendService.getFriendInfo(cookieUid, roomDTO.getFid()).get();
        if (friend != null) {
            if (friend.getRid() != null)
                return "redirect:/chat/enter/" + friend.getRid().toString();

            else {
                //방생성 및 친구객체에 방정보 저장
                Room room = new Room();
                roomService.createChatRoom(room);
                friend.setRid(room.getRid());
                friendService.saveFriend(friend);
                return "redirect:/chat/enter/" + room.getRid().toString();
            }

        }

        return "채팅방 입장 실패";
    }


    //채팅방 입장화면 {rid}를 통해 입장
    @GetMapping(value = "/chat/enter/{rid}")
    public String createFrom(HttpServletRequest request, Model model, @PathVariable Long rid) {
        //세션 정보로 sender 설정
        HttpSession session = request.getSession();
        Member user = (Member) session.getAttribute(LoginController.USER_SESSION);
        Room room = roomService.findRoomByRid(rid);


        //room 정보
        model.addAttribute("rid", room.getRid());
        model.addAttribute("name", getFriendName(user.getUid(), room.getRid()));
        model.addAttribute("sender", user.getName());
        model.addAttribute("senderUid", user.getUid());

        //채팅방 입장
        return "chat/roomdetail";
    }

    private String getFriendName(String uid, Long rid) {

        Optional<Friend> f = friendService.getFriendName(uid, rid);
        if (f.isPresent()) {
            String friendUid = f.get().getFid();
            return memberService.findOneByUid(friendUid).get().getName();
        } else {
            IllegalStateException e = new IllegalStateException("친구 정보가 없습니다.");
            throw e;
        }

    }


}