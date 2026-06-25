package org.scoula.mapper;

import org.apache.ibatis.annotations.Param;
import org.scoula.domain.AttachmentVO;
import org.scoula.domain.BoardVO;

import java.util.List;

public interface BoardMapper {

    // 게시판 종류에 따른 게시글 목록을 조회합니다.
    List<BoardVO> getList(
            @Param("boardType")
            String boardType
    );

    // 게시글 번호로 게시글 한 건을 조회합니다.
    BoardVO get(
            @Param("bno")
            Long bno
    );

    // 게시글을 등록합니다.
    int insert(BoardVO board);

    // 게시글을 수정합니다.
    int update(BoardVO board);

    // 게시글을 삭제합니다.
    int delete(
            @Param("bno")
            Long bno
    );

    // 조회수를 1 증가시킵니다.
    int increaseViewCount(
            @Param("bno")
            Long bno
    );

    // 첨부파일 정보를 등록합니다.
    int insertAttachment(
            AttachmentVO attachment
    );

    // 게시글의 첨부파일 목록을 조회합니다.
    List<AttachmentVO> getAttachments(
            @Param("bno")
            Long bno
    );

    // 첨부파일 번호로 파일 한 건을 조회합니다.
    AttachmentVO getAttachment(
            @Param("ano")
            Long ano
    );

    // 첨부파일 정보 한 건을 삭제합니다.
    int deleteAttachment(
            @Param("ano")
            Long ano
    );

    // 게시글에 속한 첨부파일 정보를 모두 삭제합니다.
    int deleteAttachmentsByBno(
            @Param("bno")
            Long bno
    );

}