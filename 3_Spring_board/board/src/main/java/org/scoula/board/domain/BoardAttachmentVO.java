package org.scoula.board.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.scoula.common.util.UploadFiles;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardAttachmentVO {
    private Long no;
    private Long bno;
    private String filename;
    private byte[] data;
    private String contentType;
    private Long size;
    private Date regDate;

    //팩토리 매서드
    public static BoardAttachmentVO of(MultipartFile part, Long bno) throws Exception {

        return builder()
                .bno(bno)
                .filename(part.getOriginalFilename())
                .data(part.getBytes())
                .contentType(part.getContentType())
                .size(part.getSize())
                .build();
    }
    public String getFileSize(){
        return UploadFiles.getFormatSize(size);
    }
}
