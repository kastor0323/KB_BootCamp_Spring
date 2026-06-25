package org.scoula.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.domain.AttachmentVO;
import org.scoula.domain.BoardVO;
import org.scoula.service.BoardService;
import org.scoula.service.FileStorageService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Log4j2
public class BoardController {

    private final BoardService boardService;
    private final FileStorageService fileStorageService;

    // 자료실 목록
    @GetMapping("/data")
    public String dataList(
            Model model) {

        model.addAttribute(
                "boardType",
                "DATA"
        );

        model.addAttribute(
                "list",
                boardService.getList("DATA")
        );

        return "board/list";
    }

    // 갤러리 목록
    @GetMapping("/gallery")
    public String galleryList(
            Model model) {

        model.addAttribute(
                "boardType",
                "GALLERY"
        );

        model.addAttribute(
                "list",
                boardService.getList("GALLERY")
        );

        return "board/gallery";
    }

    // 등록 화면
    @GetMapping("/create")
    public String createForm(
            String boardType,
            Model model) {

        validateBoardType(boardType);

        model.addAttribute(
                "boardType",
                boardType
        );

        return "board/create";
    }

    // 게시글 등록
    @PostMapping("/create")
    public String create(
            BoardVO board,
            MultipartFile[] files,
            RedirectAttributes redirectAttributes)
            throws Exception {

        Long bno =
                boardService.create(
                        board,
                        files
                );

        redirectAttributes.addFlashAttribute(
                "message",
                "게시글이 등록되었습니다."
        );

        return "redirect:/board/read?bno=" +
                bno;
    }

    // 게시글 상세보기
    @GetMapping("/read")
    public String read(
            Long bno,
            Model model) {

        model.addAttribute(
                "board",
                boardService.get(
                        bno,
                        true
                )
        );

        return "board/read";
    }

    // 수정 화면
    @GetMapping("/edit")
    public String editForm(
            Long bno,
            Model model) {

        model.addAttribute(
                "board",
                boardService.get(
                        bno,
                        false
                )
        );

        return "board/edit";
    }

    // 게시글 수정
    @PostMapping("/edit")
    public String edit(
            BoardVO board,
            MultipartFile[] files,
            RedirectAttributes redirectAttributes)
            throws Exception {

        boardService.update(
                board,
                files
        );

        redirectAttributes.addFlashAttribute(
                "message",
                "게시글이 수정되었습니다."
        );

        return "redirect:/board/read?bno=" +
                board.getBno();
    }

    // 첨부파일 한 건 삭제
    @PostMapping("/file/delete")
    public String deleteFile(
            Long ano,
            Long bno,
            RedirectAttributes redirectAttributes)
            throws Exception {

        boardService.deleteAttachment(ano);

        redirectAttributes.addFlashAttribute(
                "message",
                "첨부파일이 삭제되었습니다."
        );

        return "redirect:/board/edit?bno=" +
                bno;
    }

    // 게시글 삭제
    @PostMapping("/delete")
    public String delete(
            Long bno,
            String boardType,
            RedirectAttributes redirectAttributes)
            throws Exception {

        validateBoardType(boardType);

        boardService.delete(bno);

        redirectAttributes.addFlashAttribute(
                "message",
                "게시글이 삭제되었습니다."
        );

        if ("GALLERY".equals(boardType)) {
            return "redirect:/board/gallery";
        }

        return "redirect:/board/data";
    }

    // 첨부파일 다운로드
    @GetMapping("/download/{ano}")
    public ResponseEntity<Resource> download(
            @PathVariable Long ano)
            throws MalformedURLException {

        AttachmentVO attachment =
                boardService.getAttachment(ano);

        Path filePath =
                fileStorageService.load(
                        attachment.getStoredName()
                );

        Resource resource =
                new UrlResource(
                        filePath.toUri()
                );

        ContentDisposition disposition =
                ContentDisposition
                        .attachment()
                        .filename(
                                attachment.getOriginalName(),
                                StandardCharsets.UTF_8
                        )
                        .build();

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        disposition.toString()
                )
                .contentType(
                        MediaType.APPLICATION_OCTET_STREAM
                )
                .contentLength(
                        attachment.getFileSize()
                )
                .body(resource);
    }

    // 갤러리 및 상세 화면에 이미지를 출력합니다.
    @GetMapping("/image/{ano}")
    public ResponseEntity<Resource> image(
            @PathVariable Long ano)
            throws MalformedURLException {

        AttachmentVO attachment =
                boardService.getAttachment(ano);

        if (!Boolean.TRUE.equals(
                attachment.getImageFile())) {

            throw new IllegalArgumentException(
                    "이미지 파일이 아닙니다."
            );
        }

        Path filePath =
                fileStorageService.load(
                        attachment.getStoredName()
                );

        Resource resource =
                new UrlResource(
                        filePath.toUri()
                );

        MediaType mediaType =
                MediaType.APPLICATION_OCTET_STREAM;

        if (attachment.getContentType() != null) {
            mediaType = MediaType.parseMediaType(
                    attachment.getContentType()
            );
        }

        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(resource);
    }

    private void validateBoardType(
            String boardType) {

        if (!"DATA".equals(boardType) &&
                !"GALLERY".equals(boardType)) {

            throw new IllegalArgumentException(
                    "올바르지 않은 게시판 종류입니다."
            );
        }
    }

}