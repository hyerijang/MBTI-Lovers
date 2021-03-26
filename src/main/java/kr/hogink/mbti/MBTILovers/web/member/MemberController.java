package kr.hogink.mbti.MBTILovers.web.member;

import kr.hogink.mbti.MBTILovers.web.member.MBTI.EnumMbti;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping(value = "/members/new")
    public String createFrom() {
        return "members/createMemberForm";
    }


    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());
        member.setGender(form.getGender());
        member.setAge(form.getAge());
        member.setUid(form.getUid());
        member.setMbti(form.getMbti());
        member.setStateMessage(form.getStateMessage());

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping(value = "/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }


    public void getMbtiList() {
        System.out.println("MBTI 목록");
        List<EnumMbti> enumValues = Arrays.asList(EnumMbti.values());
        System.out.println(enumValues);
    }

}
