package org.scoula.mapper;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.scoula.config.RootConfig;
import org.scoula.domain.BoardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Log4j2
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RootConfig.class)
class BoardMapperTest {

    @Autowired
    private BoardMapper boardMapper;

    @Test
    @DisplayName("자료실 게시글을 등록하고 조회한다.")
    void insertAndGetTest() {

        BoardVO board =
                BoardVO.builder()
                        .boardType("DATA")
                        .title("Spring 수업 자료")
                        .content("파일 업로드 실습 자료입니다.")
                        .writer("강사")
                        .build();

        int count =
                boardMapper.insert(board);

        assertEquals(1, count);
        assertNotNull(board.getBno());

        BoardVO savedBoard =
                boardMapper.get(
                        board.getBno()
                );

        assertNotNull(savedBoard);

        assertEquals(
                "Spring 수업 자료",
                savedBoard.getTitle()
        );

        log.info(savedBoard);
    }

    @Test
    @DisplayName("자료실 게시글 목록을 조회한다.")
    void getListTest() {

        List<BoardVO> list =
                boardMapper.getList("DATA");

        assertNotNull(list);

        list.forEach(
                board -> log.info(board)
        );
    }

}