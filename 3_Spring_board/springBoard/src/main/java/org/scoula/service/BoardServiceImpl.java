package org.scoula.service;

import lombok.RequiredArgsConstructor;
import org.scoula.domain.AttachmentVO;
import org.scoula.domain.BoardVO;
import org.scoula.mapper.BoardMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;
    private final FileStorageService fileStorageService;

    /**
     * 게시판 종류에 맞는 게시글 목록을 조회합니다.
     */
    @Override
    public List<BoardVO> getList(String boardType) {

        validateBoardType(boardType);

        List<BoardVO> list =
                boardMapper.getList(boardType);

        /*
         * 갤러리 게시판은 각 게시글의 첫 번째 이미지 파일을
         * 목록 화면에서 사용할 썸네일로 설정합니다.
         */
        if ("GALLERY".equals(boardType)) {

            for (BoardVO board : list) {

                List<AttachmentVO> attachments =
                        boardMapper.getAttachments(
                                board.getBno()
                        );

                AttachmentVO thumbnail =
                        attachments.stream()
                                .filter(attachment ->
                                        Boolean.TRUE.equals(
                                                attachment.getImageFile()
                                        )
                                )
                                .findFirst()
                                .orElse(null);

                board.setThumbnail(thumbnail);
            }
        }

        return list;
    }

    /**
     * 게시글 한 건을 조회합니다.
     *
     * @param bno               게시글 번호
     * @param increaseViewCount 조회수 증가 여부
     */
    @Override
    @Transactional
    public BoardVO get(
            Long bno,
            boolean increaseViewCount
    ) {

        if (bno == null) {
            throw new IllegalArgumentException(
                    "게시글 번호가 필요합니다."
            );
        }

        if (increaseViewCount) {
            boardMapper.increaseViewCount(bno);
        }

        BoardVO board =
                boardMapper.get(bno);

        if (board == null) {
            throw new IllegalArgumentException(
                    "게시글을 찾을 수 없습니다."
            );
        }

        board.setAttachments(
                boardMapper.getAttachments(bno)
        );

        return board;
    }

    /**
     * 게시글을 등록합니다.
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(
            BoardVO board,
            MultipartFile[] files
    ) throws IOException {

        if (board == null) {
            throw new IllegalArgumentException(
                    "게시글 정보가 필요합니다."
            );
        }

        validateBoardType(
                board.getBoardType()
        );

        int result =
                boardMapper.insert(board);

        if (result != 1 ||
                board.getBno() == null) {

            throw new IllegalStateException(
                    "게시글 등록에 실패했습니다."
            );
        }

        /*
         * 게시글 등록 후 MyBatis의 useGeneratedKeys로
         * 자동 생성된 bno가 board에 저장됩니다.
         */
        saveFiles(
                board.getBno(),
                files,
                board.getBoardType()
        );

        return board.getBno();
    }

    /**
     * 게시글을 수정하고 새로운 첨부파일을 등록합니다.
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(
            BoardVO board,
            MultipartFile[] files
    ) throws IOException {

        if (board == null ||
                board.getBno() == null) {

            throw new IllegalArgumentException(
                    "수정할 게시글 정보가 필요합니다."
            );
        }

        BoardVO savedBoard =
                boardMapper.get(
                        board.getBno()
                );

        if (savedBoard == null) {
            throw new IllegalArgumentException(
                    "수정할 게시글을 찾을 수 없습니다."
            );
        }

        /*
         * 사용자가 요청값을 조작하여 게시판 종류를
         * 변경하지 못하도록 DB에 저장된 종류를 사용합니다.
         */
        board.setBoardType(
                savedBoard.getBoardType()
        );

        int result =
                boardMapper.update(board);

        if (result != 1) {
            throw new IllegalStateException(
                    "게시글 수정에 실패했습니다."
            );
        }

        saveFiles(
                board.getBno(),
                files,
                savedBoard.getBoardType()
        );
    }

    /**
     * 게시글 전체를 삭제합니다.
     *
     * 실제 첨부파일과 DB의 첨부파일 정보,
     * 게시글 정보를 차례대로 삭제합니다.
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long bno)
            throws IOException {

        if (bno == null) {
            throw new IllegalArgumentException(
                    "삭제할 게시글 번호가 필요합니다."
            );
        }

        BoardVO board =
                boardMapper.get(bno);

        if (board == null) {
            throw new IllegalArgumentException(
                    "삭제할 게시글을 찾을 수 없습니다."
            );
        }

        List<AttachmentVO> attachments =
                boardMapper.getAttachments(bno);

        /*
         * 먼저 서버 폴더에 저장된 실제 파일을 삭제합니다.
         * 파일이 존재하지 않아도 FileStorageService의
         * deleteIfExists() 때문에 오류 없이 계속 진행됩니다.
         */
        for (AttachmentVO attachment : attachments) {

            if (attachment.getStoredName() != null) {
                fileStorageService.delete(
                        attachment.getStoredName()
                );
            }
        }

        /*
         * 첨부파일 DB 정보를 먼저 삭제합니다.
         * 외래키가 ON DELETE CASCADE여도 실행해도 되지만,
         * 이 경우 명시적으로 먼저 삭제하는 구조입니다.
         */
        boardMapper.deleteAttachmentsByBno(bno);

        int result =
                boardMapper.delete(bno);

        if (result != 1) {
            throw new IllegalStateException(
                    "게시글 삭제에 실패했습니다."
            );
        }
    }

    /**
     * 첨부파일 정보 한 건을 조회합니다.
     */
    @Override
    public AttachmentVO getAttachment(Long ano) {

        if (ano == null) {
            throw new IllegalArgumentException(
                    "첨부파일 번호가 필요합니다."
            );
        }

        AttachmentVO attachment =
                boardMapper.getAttachment(ano);

        if (attachment == null) {
            throw new IllegalArgumentException(
                    "첨부파일 정보를 찾을 수 없습니다."
            );
        }

        return attachment;
    }

    /**
     * 첨부파일 한 건을 삭제합니다.
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAttachment(Long ano)
            throws IOException {

        AttachmentVO attachment =
                getAttachment(ano);

        /*
         * 서버 폴더에 저장된 실제 파일을 삭제합니다.
         */
        fileStorageService.delete(
                attachment.getStoredName()
        );

        /*
         * DB의 첨부파일 정보를 삭제합니다.
         */
        int result =
                boardMapper.deleteAttachment(ano);

        if (result != 1) {
            throw new IllegalStateException(
                    "첨부파일 삭제에 실패했습니다."
            );
        }
    }

    /**
     * 게시글에 첨부파일을 저장합니다.
     */
    private void saveFiles(
            Long bno,
            MultipartFile[] files,
            String boardType
    ) throws IOException {

        if (files == null ||
                files.length == 0) {
            return;
        }

        for (MultipartFile file : files) {

            if (file == null ||
                    file.isEmpty()) {
                continue;
            }

            AttachmentVO attachment =
                    fileStorageService.store(
                            bno,
                            file
                    );

            /*
             * 갤러리 게시판에는 이미지만 등록할 수 있습니다.
             */
            if ("GALLERY".equals(boardType) &&
                    !Boolean.TRUE.equals(
                            attachment.getImageFile()
                    )) {

                /*
                 * 이미지가 아닌 파일은 이미 서버에 저장된 상태이므로
                 * 실제 파일을 다시 삭제한 후 예외를 발생시킵니다.
                 */
                fileStorageService.delete(
                        attachment.getStoredName()
                );

                throw new IllegalArgumentException(
                        "갤러리에는 이미지 파일만 등록할 수 있습니다."
                );
            }

            int result =
                    boardMapper.insertAttachment(
                            attachment
                    );

            if (result != 1) {

                /*
                 * DB 저장에 실패했다면 서버에 저장된 파일도
                 * 남지 않도록 삭제합니다.
                 */
                fileStorageService.delete(
                        attachment.getStoredName()
                );

                throw new IllegalStateException(
                        "첨부파일 정보 저장에 실패했습니다."
                );
            }
        }
    }

    /**
     * 게시판 종류가 DATA 또는 GALLERY인지 검사합니다.
     */
    private void validateBoardType(
            String boardType
    ) {

        if (!"DATA".equals(boardType) &&
                !"GALLERY".equals(boardType)) {

            throw new IllegalArgumentException(
                    "올바르지 않은 게시판 종류입니다."
            );
        }
    }
}