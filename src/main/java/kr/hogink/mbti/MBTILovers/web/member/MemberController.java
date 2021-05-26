package kr.hogink.mbti.MBTILovers.web.member;

import kr.hogink.mbti.MBTILovers.web.friend.Friend;
import kr.hogink.mbti.MBTILovers.web.friend.FriendService;
import kr.hogink.mbti.MBTILovers.web.login.LoginType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

import static kr.hogink.mbti.MBTILovers.web.login.LoginType.USER_UID_COOKIE;

@Controller
@Log4j2
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final FriendService friendService;

    @GetMapping(value = "/members/new")
    public String createFrom(Model model, HttpSession session) {
        String uid = (String) session.getAttribute(LoginType.NEW_USER_UID_SESSION);
        model.addAttribute("uid", uid);
        return "members/createMemberForm";
    }


    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setUid(form.getUid());
        member.setName(form.getName());
        member.setGender(form.getGender());
        member.setAge(form.getAge());
        member.setMbti(form.getMbti());
        member.setStateMessage(form.getStateMessage());
        member.setProfileImage(form.getProfileImage());

        log.info(member.getStateMessage());
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

        String uid = (String) session.getAttribute(LoginType.NEW_USER_UID_SESSION);
        if (uid == null) {
            Cookie loginCookie = WebUtils.getCookie(request, LoginType.USER_UID_COOKIE);
            uid = loginCookie.getValue();
        }

        Member member = memberService.findOneByUid(uid).get();

        member.setName(form.getName());
        member.setGender(form.getGender());
        member.setAge(form.getAge());
        member.setMbti(form.getMbti());
        member.setStateMessage(form.getStateMessage());
        member.setProfileImage(form.getProfileImage());

        memberService.edit(member);
        //세션정보 갱신
        session.removeAttribute(LoginType.USER_MEMBER_SESSION);
        session.setAttribute(LoginType.USER_MEMBER_SESSION, member);
        return "redirect:/";
    }

    @GetMapping(value = "/members/read/{fid}")
    public String readFrom(Model model, @PathVariable String fid, @CookieValue(name = USER_UID_COOKIE) String cookieUid) {
        Optional<Member> member = memberService.findOneByUid(fid);
        if (member.isPresent()) {
            model.addAttribute("uid", cookieUid);
            model.addAttribute("member", member.get());

            Friend friend = friendService.getFriend(cookieUid, fid);
            if (friend != null) {
                model.addAttribute("relation",friend.getRelation());
            }
            return "members/readMemberForm.html";
        }
        log.warn("존재하지 않는 유저입니다.");
        return "redirect:/";
    }




}
