package kr.hogink.mbti.MBTILovers.web.member;

import kr.hogink.mbti.MBTILovers.web.login.LoginType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping(value = "/members/new")
    public String createFrom(HttpSession session) {
        return "members/createMemberForm";
    }


    @PostMapping("/members/new")
    public String create(MemberForm form, HttpSession session) {
        String newUserUid = (String) session.getAttribute(LoginType.NEW_USER_UID_SESSION);
        Member member = new Member();
        member.setUid(newUserUid);
        member.setName(form.getName());
        member.setGender(form.getGender());
        member.setAge(form.getAge());
        member.setMbti(form.getMbti());
        member.setStateMessage(form.getStateMessage());
        member.setProfileImage(form.getProfileImage());

//        System.out.println("UID: "+newUserUid);
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping(value = "/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }


    @GetMapping(value = "/members/edit")
    public String editFrom() {
        return "members/editMemberForm";
    }


    @PostMapping("/members/edit")
    public String edit(MemberForm form) {
//        Member member = memberService.findOneByUid((long) 1).get();//임시
//        member.setName(form.getName());
//        member.setGender(form.getGender());
//        member.setAge(form.getAge());
//        member.setMbti(form.getMbti());
//        member.setStateMessage(form.getStateMessage());
//        member.setProfileImage(form.getProfileImage());
////        memberService.modifyUser(member);
        return "redirect:/";
    }


}
