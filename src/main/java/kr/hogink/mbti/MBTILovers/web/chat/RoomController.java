package kr.hogink.mbti.MBTILovers.web.chat;

import kr.hogink.mbti.MBTILovers.web.friend.Friend;
import kr.hogink.mbti.MBTILovers.web.friend.FriendService;
import kr.hogink.mbti.MBTILovers.web.login.LoginController;
import kr.hogink.mbti.MBTILovers.web.member.Member;
import kr.hogink.mbti.MBTILovers.web.member.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static kr.hogink.mbti.MBTILovers.web.login.LoginController.USER_COOKIE;

@Controller
public class RoomController {

    FriendService friendService;
    RoomService roomService;
    MemberService memberService;

    public RoomController(FriendService friendService, RoomService roomService, MemberService memberService) {
        this.friendService = friendService;
        this.roomService = roomService;
        this.memberService = memberService;
    }

    // 모든 채팅방 목록 반환
    @GetMapping(value = "/chatList")

    public String list(Model model) {
        List<Room> rooms = roomService.findAllRoom();
        model.addAttribute("rooms", rooms);
        return "chat/chatList";
    }


    // 채팅방 생성
    @PostMapping("/room")
    public String createRoom(RoomDTO roomDTO, @CookieValue(name = USER_COOKIE) String cookieUid) {

        Friend friend = friendService.findOneByUidAndFid(cookieUid, roomDTO.getFid()).get();
        if (friend != null) {
            if (friend.getRid() != null)
                return "redirect:/chat/enter/" + friend.getRid().toString();

            else {
                //방생성
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
        model.addAttribute("name", room.getName());
        model.addAttribute("sender", user.getName());
        model.addAttribute("senderUid", user.getUid());

        //채팅방 입장
        return "chat/roomdetail";
    }


}