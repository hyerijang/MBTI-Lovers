package kr.hogink.mbti.MBTILovers.web;


import kr.hogink.mbti.MBTILovers.web.member.Member;
import kr.hogink.mbti.MBTILovers.web.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static kr.hogink.mbti.MBTILovers.web.login.LoginType.USER_UID_COOKIE;

@Log4j2
@Controller
@RequiredArgsConstructor
public class MapController {

    private final MemberService memberService;

    private final int NUM_NEAR_USER = 10;
    
    @GetMapping("/matching")
    public String matching(Model model, @CookieValue(name = USER_UID_COOKIE) String cookieUid) {
        Optional<Member> member = memberService.findOneByUid(cookieUid);
        model.addAttribute("uid", cookieUid);
        if (member.get().getLocation() != null) {
            model.addAttribute("x", member.get().getLocation().getCoordinate().x);
            model.addAttribute("y", member.get().getLocation().getCoordinate().y);
        }
        return "matching";
    }

    @PostMapping("/members/position")
    public String setPosition(Model model, @CookieValue(name = USER_UID_COOKIE) String cookieUid, String LocationX, String LocationY) {

        log.info("[userLocation] x:" + LocationX + " y:" + LocationY);
        Double latitude = Double.parseDouble(LocationX);
        Double longitude = Double.parseDouble(LocationY);

        Optional<Member> user = memberService.findOneByUid(cookieUid);

        memberService.setPoint(user, latitude, longitude);

        return "redirect:/";

    }

    @RequestMapping(value = {"/matching/near/{x}/{y}"}, method = RequestMethod.GET)
    @ResponseBody()
    public List<Member> getNearUser(@PathVariable String x, @PathVariable String y) {

        log.info("근처유저 가져오기 x:" + x + " y:" + y);
        Double latitude = Double.parseDouble(x);
        Double longitude = Double.parseDouble(y);

        List<Member> members = memberService.findNearUser(latitude, longitude, NUM_NEAR_USER);

        return members;

    }
}

