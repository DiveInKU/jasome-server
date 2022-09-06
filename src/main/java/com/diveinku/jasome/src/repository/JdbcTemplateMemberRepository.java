package com.diveinku.jasome.src.repository;

import com.diveinku.jasome.src.domain.Member;
import com.diveinku.jasome.src.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplateMemberRepository implements MemberRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplateMemberRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("email", member.getEmail());
        parameters.put("name", member.getName());
        parameters.put("password", member.getPassword());
        parameters.put("role", member.getRole().name());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        member.setId(key.longValue());
        return member;
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM member where id = ?",
                (rs, rowNum) -> Member.builder()
                        .email(rs.getString("email"))
                        .name(rs.getString("name"))
                        .password(rs.getString("password"))
                        .role(Role.valueOf(rs.getString("role")))
                        .build(), email));
    }

    @Override
    public boolean findExistsByEmail(String email) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT EXISTS(SELECT email from member where email = ?)",
                int.class, email)).map(i -> i == 1).orElse(false);
    }
}
