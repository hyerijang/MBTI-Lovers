package kr.hogink.mbti.MBTILovers.web.chatRoom;

import kr.hogink.mbti.MBTILovers.web.member.Member;
import kr.hogink.mbti.MBTILovers.web.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final MemberService memberService;

    @Autowired
    public ChatRoomController(MemberService memberService, ChatRoomService chatRoomService ) {
        this.chatRoomService = chatRoomService;
        this.memberService = memberService;
    }

    @GetMapping(value = "/chatRoom")
    public String list(Model model) {
        List<ChatRoom> rooms = chatRoomService.findMembers();
        model.addAttribute("members", rooms);
        return "chatRoom";
    }


    @PostMapping("/chatRoom")
    public String create(ChatRoomForm form) {
        ChatRoom chatRoom = new ChatRoom();
        Member member1 = new Member();
        Member member2 = new Member();
        //임시
        member1 = memberService.findOneById((long)1).get();
        member2 = memberService.findOneById((long)2).get();
        //Member 지정할 것
        chatRoom.setMember1(member1);
        chatRoom.setMember2(member2);

        chatRoomService.make(chatRoom);
        return "chatRoom";
    }

}