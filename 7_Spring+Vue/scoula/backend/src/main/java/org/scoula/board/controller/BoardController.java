package org.scoula.board.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.board.dto.BoardDTO;
import org.scoula.board.service.BoardService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/board")
@Api(tags = "게시물 관리")
public class BoardController {

    private final BoardService service;

    @ApiOperation(value = "게시물 목록", notes = "계시글 목록을 얻는 API")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로요청이처리되었습니다.", response = BoardDTO.class),
            @ApiResponse(code = 400, message = "잘못된요청입니다."),
            @ApiResponse(code = 500, message = "서버에서오류가발생했습니다.") })
    @GetMapping("")
    public ResponseEntity<List<BoardDTO>> getList(){
        return ResponseEntity.ok(service.getList());
    }

    @ApiOperation(value = "상세정보 얻기", notes = "게시글 상제 정보를 얻는 API")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로요청이처리되었습니다.", response = BoardDTO.class),
            @ApiResponse(code = 400, message = "잘못된요청입니다."),
            @ApiResponse(code = 500, message = "서버에서오류가발생했습니다.") })
    @GetMapping("/{no}")
    public ResponseEntity<BoardDTO> get(
            @ApiParam(value = "게시글 ID", required = true, example = "1")
            @PathVariable Long no) {
        return ResponseEntity.ok(service.get(no));
    }

    @ApiOperation(value = "게시글삭제", notes = "게시글삭제API")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "성공적으로요청이처리되었습니다."),
            @ApiResponse(code = 400, message = "잘못된요청입니다."),
            @ApiResponse(code = 500, message = "서버에서오류가발생했습니다.") })
    @DeleteMapping("/{no}")
    public ResponseEntity<BoardDTO> delete(
            @ApiParam(value = "게시글ID", required = true, example = "1")
            @PathVariable Long no) {
        return ResponseEntity.ok(service.delete(no));
    }
    @ApiOperation(value = "게시글 생성", notes = "게시글 생성 API")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로요청이처리되었습니다.", response = BoardDTO.class),
            @ApiResponse(code = 400, message = "잘못된요청입니다."),
            @ApiResponse(code = 500, message = "서버에서오류가발생했습니다.") })
    @PostMapping("") public ResponseEntity<BoardDTO> create(
            @ApiParam(value = "게시글 객체", required = true)
            @RequestBody BoardDTO board) {
        return ResponseEntity.ok(service.create(board));
    }

    @ApiOperation(value = "게시글수정", notes = "게시글수정API")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로요청이처리되었습니다.", response = BoardDTO.class),
            @ApiResponse(code = 400, message = "잘못된요청입니다."),
            @ApiResponse(code = 500, message = "서버에서오류가발생했습니다.") })
    @PutMapping("/{no}")
    public ResponseEntity<BoardDTO> update(
            @ApiParam(value = "게시글ID", required = true, example = "1")
            @PathVariable Long no,
            @ApiParam(value = "게시글객체", required = true)
            @RequestBody BoardDTO board) {
        board.setNo(no);
        return ResponseEntity.ok(service.update(board));
    }

    @ApiOperation(value = "첨부파일 다운로드", notes = "게시글 첨부파일을 다운로드하는 API")
    @GetMapping("/download/{no}")
    public ResponseEntity<byte[]> download(@PathVariable Long no) throws Exception {
        org.scoula.board.domain.BoardAttachmentVO attach = service.getAttachment(no);
        if(attach == null) return ResponseEntity.notFound().build();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, attach.getContentType());
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + java.net.URLEncoder.encode(attach.getFilename(), java.nio.charset.StandardCharsets.UTF_8) + "\"");

        return ResponseEntity.ok()
                .headers(headers)
                .body(attach.getData());
    }
}
