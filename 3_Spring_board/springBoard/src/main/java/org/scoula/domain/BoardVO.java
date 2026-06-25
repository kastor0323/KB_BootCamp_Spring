package org.scoula.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardVO {

    // 게시글 번호
    private Long bno;

    // DATA 또는 GALLERY
    private String boardType;

    // 제목
    private String title;

    // 내용
    private String content;

    // 작성자
    private String writer;

    // 조회수
    private Integer viewCount;

    // 등록일
    private LocalDateTime createdAt;

    // 수정일
    private LocalDateTime updatedAt;

    // 게시글에 첨부된 파일 목록
    @Builder.Default
    private List<AttachmentVO> attachments =
            new ArrayList<>();

    // 갤러리 목록에서 사용할 대표 이미지
    private AttachmentVO thumbnail;

}