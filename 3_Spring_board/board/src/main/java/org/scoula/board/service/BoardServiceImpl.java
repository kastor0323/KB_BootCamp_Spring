package org.scoula.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.board.domain.BoardAttachmentVO;
import org.scoula.board.domain.BoardVO;
import org.scoula.board.dto.BoardDTO;
import org.scoula.board.mapper.BoardMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final static String BASE_DIR = "C:/Coding/WorkSpace/BootCamp/Spring/board/upload";
    final private BoardMapper mapper;
    @Override
    public List<BoardDTO> getList() {
        return mapper.getList().stream()
                .map(BoardDTO::of)
                .toList();
    }

    @Override public BoardDTO get(Long no) {
        log.info("get......" + no);
        BoardVO boardVO = mapper.get(no);

        if (boardVO == null) {
            throw new NoSuchElementException();
        }

        List<BoardAttachmentVO> attaches = mapper.getAttachmentList(no);
        boardVO.setAttaches(attaches);
        return BoardDTO.of(boardVO);
//        BoardDTO board = BoardDTO.of(mapper.get(no));
//        return Optional.ofNullable(board)
//                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    @Override
    public void create(BoardDTO board) {
        log.info("create......" + board);
        BoardVO boardVO = board.toVo();
        mapper.create(boardVO);

        List<MultipartFile> files = board.getFiles();
        if(files != null && !files.isEmpty()){
            upload(boardVO.getNo(), files);
        }
    }

    @Override
    public boolean update(BoardDTO board) {
        log.info("update......" + board);

        return mapper.update(board.toVo()) == 1;
    }

    @Transactional
    @Override
    public boolean delete(Long no) {
        log.info("delete...." + no);

        mapper.deleteAttachmentByBno(no);

        return mapper.delete(no) == 1;
    }

    private void upload(Long bno, List<MultipartFile> files){
        for(MultipartFile part : files){
            if(part.isEmpty()) continue;
            try {
                BoardAttachmentVO attach = BoardAttachmentVO.of(part, bno);
                mapper.createAttachment(attach);
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public BoardAttachmentVO getAttachment(Long no) {
        return mapper.getAttachment(no);
    }

    @Override public boolean deleteAttachment(Long no) {
        return mapper.deleteAttachment(no) == 1;
    }
}
