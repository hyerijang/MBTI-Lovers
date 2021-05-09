package kr.hogink.mbti.MBTILovers.web;

import kr.hogink.mbti.MBTILovers.web.member.Member;
import kr.hogink.mbti.MBTILovers.web.member.MemberService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

import static kr.hogink.mbti.MBTILovers.web.login.LoginType.USER_UID_COOKIE;

@Log4j2
@Controller
public class MapController {

    private MemberService memberService;

    public MapController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/matching")
    public String matching(Model model) {
        List<Member> members = memberService.findNearMembers("33","36");
        model.addAttribute("members", members);
        return "matching";
    }

    @PostMapping("/members/position")
    public String setPosition(@CookieValue(name = USER_UID_COOKIE) String cookieUid, String positionX, String positionY) {

        log.info("[UserPosition] x:" + positionX + " y:" + positionY);

        Optional<Member> user = memberService.findOneByUid(cookieUid);
        if (user.isPresent()) {
            Member temp = user.get();
            temp.setPositionX(positionX);
            temp.setPositionY(positionY);
            memberService.edit(temp);
            log.info(positionX +" "+ positionY);
            log.info(cookieUid+"님의 현재 위치를 저장하였습니다.");
        }

        return "redirect:/";
    }

}
