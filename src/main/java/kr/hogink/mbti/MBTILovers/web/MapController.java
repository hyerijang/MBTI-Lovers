package kr.hogink.mbti.MBTILovers.web;

import kr.hogink.mbti.MBTILovers.web.member.Member;
import kr.hogink.mbti.MBTILovers.web.member.MemberService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Log4j2
@Controller
public class MapController {

    private MemberService memberService;

    public MapController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/matching")
    public String matching(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);

        return "matching";
    }



}
