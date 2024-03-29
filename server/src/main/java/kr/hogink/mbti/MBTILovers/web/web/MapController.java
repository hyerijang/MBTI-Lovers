package kr.hogink.mbti.MBTILovers.web.web;


import kr.hogink.mbti.MBTILovers.web.domain.member.Member;
import kr.hogink.mbti.MBTILovers.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
            //Point(x,y) x : 위도, y : 경도
            model.addAttribute("lat", member.get().getLocation().getCoordinate().x);
            model.addAttribute("lng", member.get().getLocation().getCoordinate().y);
        }
        return "matching";
    }

    @PostMapping("/members/position")
    public String setPosition(Model model, @CookieValue(name = USER_UID_COOKIE) String cookieUid, String currentLat, String currentLng) {
        Double latitude = Double.parseDouble(currentLat); //위도
        Double longitude = Double.parseDouble(currentLng); //경도
        Optional<Member> user = memberService.findOneByUid(cookieUid);

        log.info(" lat:" + currentLat + "lng:" + currentLng + "is current Location of " + user.get().getUid());
        memberService.setPoint(user, latitude, longitude);


        return "redirect:/";

    }

    @RequestMapping(value = {"/matching/near/{lat}/{lng}"}, method = RequestMethod.GET)
    @ResponseBody()
    public List<Member> getNearUser(@PathVariable String lat, @PathVariable String lng, @CookieValue(name = USER_UID_COOKIE) String cookieUid) {

        log.info("getNearUser ( " + "lat:" + lat + " lng:" + lng + ")");
        Double latitude = Double.parseDouble(lat); // 위도
        Double longitude = Double.parseDouble(lng); // 경도

        List<Member> members = memberService.findNearUser(latitude, longitude, NUM_NEAR_USER);
        return members;

    }

    @RequestMapping(value = {"/matching/nearNotFriend/{lat}/{lng}"}, method = RequestMethod.GET)
    @ResponseBody()
    public List<Member> getNearUserNotFriend(@PathVariable String lat, @PathVariable String lng, @CookieValue(name = USER_UID_COOKIE) String cookieUid) {

        log.info("getNearUser ( " + "lat:" + lat + " lng:" + lng + ")");
        Double latitude = Double.parseDouble(lat); // 위도
        Double longitude = Double.parseDouble(lng); // 경도

        List<Member> members = memberService.findNearUserNotFriend(latitude, longitude, NUM_NEAR_USER, cookieUid);
        return members;

    }
}

