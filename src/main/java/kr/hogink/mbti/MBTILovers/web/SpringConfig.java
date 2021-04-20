package kr.hogink.mbti.MBTILovers.web;


import kr.hogink.mbti.MBTILovers.web.chat.room.RoomRepository;
import kr.hogink.mbti.MBTILovers.web.chat.room.RoomService;
import kr.hogink.mbti.MBTILovers.web.chat.room.RoomServiceImpl;
import kr.hogink.mbti.MBTILovers.web.friend.FriendRepository;
import kr.hogink.mbti.MBTILovers.web.friend.FriendService;
import kr.hogink.mbti.MBTILovers.web.friend.FriendServiceImpl;
import kr.hogink.mbti.MBTILovers.web.login.LoginService;
import kr.hogink.mbti.MBTILovers.web.login.LoginServiceImpl;
import kr.hogink.mbti.MBTILovers.web.member.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    private final MemberRepository memberRepository;
    private final FriendRepository friendRepository;
    private final RoomRepository roomRepository;

    public SpringConfig(MemberRepository memberRepository, FriendRepository friendRepository, RoomRepository roomRepository) {
        this.memberRepository = memberRepository;
        this.friendRepository = friendRepository;
        this.roomRepository = roomRepository;
    }

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository);
    }

    @Bean
    public FriendService friendService() {
        return new FriendServiceImpl(friendRepository, memberRepository);
    }

    @Bean
    public LoginService loginService() {
        return new LoginServiceImpl(memberService());
    }


    @Bean
    public RoomService roomService() {
        return new RoomServiceImpl(roomRepository);
    }
}

