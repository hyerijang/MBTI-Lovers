package kr.hogink.mbti.MBTILovers.web.chat;

import kr.hogink.mbti.MBTILovers.web.login.LoginController;
import kr.hogink.mbti.MBTILovers.web.member.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class RoomController {

    final static RoomRepository ROOM_REPOSITORY = new RoomRepository();

    //채팅홈
    @GetMapping(value = "/chatHome")
    public String chatHome(Model model) {
        return "chatHome";
    }


    // 모든 채팅방 목록 반환
    @GetMapping(value = "/chatList")
    public String list(Model model) {
        createRoom("room1");
        createRoom("room2");
        List<Room> rooms = ROOM_REPOSITORY.findAllRoom();
        model.addAttribute("rooms", rooms);
        return "chat/chatList";
    }


    // 채팅방 생성
    @PostMapping("/room")
    @ResponseBody
    public Room createRoom(@RequestParam String name) {
        Room room = new Room();
        room.setName(name);
        return ROOM_REPOSITORY.createChatRoom(room);
    }

    
    //채팅방 입장화면 {rid}를 통해 입장
    @GetMapping(value = "/chat/enter/{rid}")
    public String createFrom(HttpServletRequest request, Model model, @PathVariable String rid) {
        //세션 정보로 sender 설정
        HttpSession session = request.getSession();
        Member user = (Member)session.getAttribute(LoginController.USER);
        String sender =  user.getName();
        model.addAttribute("sender",sender);

        //Room 정보로 rid, name 설정
        Room room = ROOM_REPOSITORY.findRoomByRid(rid);
        model.addAttribute("rid",room.getRid().trim());
        model.addAttribute("name",room.getName());
        //채팅방 입장
        return "chat/roomdetail";
    }


}