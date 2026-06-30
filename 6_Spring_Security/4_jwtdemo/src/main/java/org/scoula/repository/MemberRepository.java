package org.scoula.repository;


import lombok.RequiredArgsConstructor;
import org.scoula.domain.MemberVO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public Optional<MemberVO> findByUsername(String username){
        String sql = """
                SELECT
                member_id,
                username,
                password,
                name,
                role,
                enabled
                FROM jwt_member
                WHERE username=?
                """;

        List<MemberVO> members = jdbcTemplate.query(
                sql,
                this::mapRow,
                username
        );

        if(members.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(members.get(0));
    }

    public int countByUsername(String username){
        String sql = """
                SELECT COUNT(*)
                FROM jwt_member
                WHERE username=?
                """;

        Integer count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                username
        );

        return count == null ? 0 : count;
    }

    public void insert(MemberVO member){
        String sql = """
                INSERT INTO jwt_member(
                username,
                password,
                name,
                role,
                enabled
                )
                VALUES(?, ?, ?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                member.getUsername(),
                member.getPassword(),
                member.getName(),
                member.getRole(),
                member.isEnabled()
        );
    }

    private MemberVO mapRow(ResultSet rs, int rowNum)
        throws SQLException{
        return MemberVO.builder()
                .memberId(rs.getLong("member_id"))
                .username(rs.getString("username"))
                .password(rs.getString("password"))
                .name(rs.getString("name"))
                .role(rs.getString("role"))
                .enabled(rs.getBoolean("enabled"))
                .build();
    }
}
