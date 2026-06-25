package org.scoula.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentVO {

    // 첨부파일 번호
    private Long ano;

    // 게시글 번호
    private Long bno;

    // 사용자가 업로드한 원래 파일명
    private String originalName;

    // 서버에 저장된 UUID 파일명
    private String storedName;

    // MIME 타입
    private String contentType;

    // 파일 크기
    private Long fileSize;

    // 이미지 파일 여부
    private Boolean imageFile;

    // 등록일
    private LocalDateTime createdAt;

}