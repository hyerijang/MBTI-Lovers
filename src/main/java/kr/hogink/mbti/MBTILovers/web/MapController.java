package kr.hogink.mbti.MBTILovers.web;


import kr.hogink.mbti.MBTILovers.web.member.Member;
import kr.hogink.mbti.MBTILovers.web.member.MemberService;
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
public class MapController {

    private MemberService memberService;

    public MapController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/matching")
    public String matching() {
        return "matching";
    }

    @PostMapping("/members/position")
    public String setPosition(Model model, @CookieValue(name = USER_UID_COOKIE) String cookieUid, String positionX, String positionY) {

        log.info("[UserPosition] x:" + positionX + " y:" + positionY);
        Double latitude = Double.parseDouble(positionX);
        Double longitude = Double.parseDouble(positionY);

        Optional<Member> user = memberService.findOneByUid(cookieUid);
        if (user.isPresent()) {
            Member temp = user.get();

            String pointWKT = String.format("POINT(%s %s)", longitude, latitude);
            try {
                Point point = (Point) new WKTReader().read(pointWKT);
                temp.setLocation(point);
                memberService.edit(temp);
                log.info(positionX + " " + positionY);
                log.info(cookieUid + "님의 현재 위치를 저장하였습니다.");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return "redirect:/";

    }

    @RequestMapping(value = {"/matching/near/{x}/{y}"}, method= RequestMethod.GET)
    @ResponseBody()
    public List<Member> getNearUser(@PathVariable String x, @PathVariable  String y) {

        log.info("근처유저 가져오기 x:" + x + " y:" + y);
        Double latitude = Double.parseDouble(x);
        Double longitude = Double.parseDouble(y);

        List<Member> members = memberService.findNearUser(latitude, longitude, 10);

        return members;

    }
}

