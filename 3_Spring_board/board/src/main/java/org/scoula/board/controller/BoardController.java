package org.scoula.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.board.domain.BoardAttachmentVO;
import org.scoula.board.dto.BoardDTO;
import org.scoula.board.service.BoardService;
import org.scoula.common.util.UploadFiles;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Log4j2
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    final private BoardService service;

    @GetMapping("/list")
    public void list(Model model){
        log.info("list");
        model.addAttribute("list", service.getList());
    }

    @GetMapping("/create")
    public void create() {
        log.info("create");
    }

    @PostMapping("/create")
    public String create(BoardDTO board) {
        log.info("create: " + board);
        service.create(board);
        return "redirect:/board/list";
    }

    @GetMapping({ "/get", "/update" })
    public void get(@RequestParam("no") Long no, Model model) {
        log.info("/get or update");
        model.addAttribute("board", service.get(no));
    }

    @PostMapping("/update")
    public String update(BoardDTO board) {
        log.info("update:" + board);
        service.update(board); return "redirect:/board/list";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("no") Long no) {
        log.info("delete..." + no); service.delete(no);
        return "redirect:/board/list";
    }

    @GetMapping("/download/{no}")
    @ResponseBody
    public void download(@PathVariable("no") Long no, HttpServletResponse response) throws Exception {

        // 1. DB에서 첨부파일(BLOB 데이터 포함) 정보 조회
        BoardAttachmentVO attach = service.getAttachment(no);

        // 2. 파일 데이터가 없으면 404 에러 반환
        if (attach == null || attach.getData() == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
            return;
        }

        // 3. 브라우저 다운로드를 위한 HTTP 헤더 설정
        String encodedFilename = URLEncoder.encode(attach.getFilename(), StandardCharsets.UTF_8)
                .replaceAll("\\+", "%20"); // 공백 처리 안전장치

        response.setContentType(attach.getContentType());
        response.setContentLengthLong(attach.getSize());
        response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedFilename + "\"");

        // 4. Response의 OutputStream을 통해 byte[] 데이터를 클라이언트로 전송
        try (OutputStream os = response.getOutputStream()) {
            os.write(attach.getData());
            os.flush();
        }
    }

}
