package kr.hogink.mbti.MBTILovers.web.member;

import com.google.firebase.auth.UserRecord;
import kr.hogink.mbti.MBTILovers.web.firestore.FirebaseAuthentication;
import kr.hogink.mbti.MBTILovers.web.firestore.FirebaseInitializer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class MemberRepositoryImpl implements MemberRepository {
    private final JdbcTemplate jdbcTemplate;
    private final FirebaseAuthentication fbAuth;
    private UserRecord userRecord;

    public MemberRepositoryImpl(DataSource dataSource) {
        FirebaseInitializer firebaseInitializer = new FirebaseInitializer();
        fbAuth = firebaseInitializer.initFirebase();
        fbAuth.setUserRecord("1laInCxF3bMY2dHqx7eap8aOSo22");//임시
        userRecord = fbAuth.getUserRecord();
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());
        parameters.put("uid", userRecord.getUid());
        parameters.put("age", member.getAge());
        parameters.put("gender", member.getGender());
        parameters.put("mbti", member.getMbti());
        parameters.put("stateMessage", member.getStateMessage());
        parameters.put("profileImage",member.getProfileImage());
        parameters.put("lastConnectTime" , member.getLastConnectTime());
        Number key = jdbcInsert.executeAndReturnKey(new
                MapSqlParameterSource(parameters));
        member.setId(key.longValue());
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }

    @Override
    public Optional<Member> findByUid(String uid) {
        List<Member> result = jdbcTemplate.query("select * from member where uid = ?", memberRowMapper(), uid);
        return result.stream().findAny();
    }

    @Override
    public void updateUser(Member member) {
        jdbcTemplate.update("update member set age = ? where id = ?",  member.getAge(), member.getId());

    }

    @Override
    public void keepLogin(String uid, String sessionId, Date next) throws Exception {

    }

    @Override
    public Member checkUserWithSessionKey(String value) throws Exception {
        return null;
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
        return result.stream().findAny();
    }

    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            member.setUid(rs.getString("uid"));
            member.setAge(rs.getInt("age"));
            member.setGender(rs.getString("gender"));
            member.setMbti(rs.getString("mbti"));
            member.setStateMessage(rs.getString("stateMessage"));
            member.setProfileImage(rs.getString("profileImage"));
            member.setLastConnectTime(rs.getDate("lastConnectTime"));
            return member;
        };
    }




}

