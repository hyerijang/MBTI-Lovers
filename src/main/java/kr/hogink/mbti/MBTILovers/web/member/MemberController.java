package kr.hogink.mbti.MBTILovers.web.member;

import kr.hogink.mbti.MBTILovers.web.login.LoginType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Log4j2
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
        Member member = new Member();
        String uid = (String) session.getAttribute(LoginType.NEW_USER_UID_SESSION);
        member.setUid(uid);
        member.setName(form.getName());
        member.setGender(form.getGender());
        member.setAge(form.getAge());
        member.setMbti(form.getMbti());
        member.setStateMessage(form.getStateMessage());
        member.setProfileImage(uid+form.getProfileImage());

        log.info("파일 이름 : " + uid+form.getProfileImage());
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
    public String editFrom(HttpSession session, Model model) {
        Member member = (Member) session.getAttribute(LoginType.USER_MEMBER_SESSION);
        model.addAttribute("user", member);
        return "members/editMemberForm";
    }


    @PostMapping("/members/edit")
    public String edit(MemberForm form, HttpSession session, HttpServletRequest request) {
        Member member = new Member();
        String uid = (String) session.getAttribute(LoginType.NEW_USER_UID_SESSION);
        if (uid == null) {
            log.info("회원정보 수정");
            Cookie loginCookie = WebUtils.getCookie(request, LoginType.USER_UID_COOKIE);
            uid = loginCookie.getValue();
        }

        member.setUid(uid);
        member.setName(form.getName());
        member.setGender(form.getGender());
        member.setAge(form.getAge());
        member.setMbti(form.getMbti());
        member.setStateMessage(form.getStateMessage());
        member.setProfileImage(uid+form.getProfileImage());

        memberService.edit(member);
        //세션정보 갱신
        session.setAttribute(LoginType.USER_MEMBER_SESSION, memberService.findOneByUid(uid).get());
        return "redirect:/";
    }


}
