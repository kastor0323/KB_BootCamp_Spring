package org.scoula.member.mapper;

import org.scoula.member.domain.MemberVO;

import java.util.List;

public interface MemberMapper {

    public List<MemberVO> getList();

    public MemberVO get(Long no);

    public void create(MemberVO member);

    public int update(MemberVO member);

    public int delete(Long no);
}
