package org.scoula.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberVO {

    private Long memberId;

    private String username;

    private String password;

    private String name;

    private String role;

    private boolean enabled;

}
