package org.scoula.service;

import org.scoula.domain.AttachmentVO;
import org.scoula.domain.BoardVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BoardService {

    // 게시판 목록을 조회합니다.
    List<BoardVO> getList(
            String boardType
    );

    // 게시글을 조회합니다.
    BoardVO get(
            Long bno,
            boolean increaseViewCount
    );

    // 게시글과 첨부파일을 등록합니다.
    Long create(
            BoardVO board,
            MultipartFile[] files
    ) throws IOException;

    // 게시글을 수정하고 파일을 추가합니다.
    void update(
            BoardVO board,
            MultipartFile[] files
    ) throws IOException;

    // 첨부파일 한 건을 조회합니다.
    AttachmentVO getAttachment(
            Long ano
    );

    // 첨부파일 한 건을 삭제합니다.
    void deleteAttachment(
            Long ano
    ) throws IOException;

    // 게시글과 실제 파일을 삭제합니다.
    void delete(
            Long bno
    ) throws IOException;

}