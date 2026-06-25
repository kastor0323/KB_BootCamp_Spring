package org.scoula.member.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MemberVO {
    Long id;
    String password;
    String username;
    String email;
    Integer birthday;
    Date regDate;
    Date updateDate;
}
