USE scoula_db;

DROP TABLE IF EXISTS springBoardFile;
DROP TABLE IF EXISTS springBoard;

CREATE TABLE springBoard (
                             bno BIGINT AUTO_INCREMENT PRIMARY KEY,

-- DATA: 자료실, GALLERY: 갤러리
                             board_type VARCHAR(20) NOT NULL,

-- 게시글 제목
                             title VARCHAR(200) NOT NULL,

-- 게시글 내용
                             content TEXT NOT NULL,

-- 작성자
                             writer VARCHAR(100) NOT NULL,

-- 조회수
                             view_count INT NOT NULL DEFAULT 0,

-- 등록일
                             created_at DATETIME NOT NULL
                                                     DEFAULT CURRENT_TIMESTAMP,

-- 수정일
                             updated_at DATETIME NOT NULL
                                                     DEFAULT CURRENT_TIMESTAMP
                                 ON UPDATE CURRENT_TIMESTAMP

);

CREATE TABLE springBoardFile (
                                 ano BIGINT AUTO_INCREMENT PRIMARY KEY,

-- 첨부파일이 속한 게시글 번호
                                 bno BIGINT NOT NULL,

-- 사용자가 올린 원래 파일명
                                 original_name VARCHAR(255) NOT NULL,

-- 서버에 실제로 저장된 UUID 파일명
                                 stored_name VARCHAR(255) NOT NULL,

-- 파일의 MIME 타입
                                 content_type VARCHAR(150),

-- 파일 크기
                                 file_size BIGINT NOT NULL DEFAULT 0,

-- 이미지 여부
                                 image_file BOOLEAN NOT NULL DEFAULT FALSE,

-- 등록일
                                 created_at DATETIME NOT NULL
                                     DEFAULT CURRENT_TIMESTAMP,

                                 CONSTRAINT fk_springBoardFile_springBoard
                                     FOREIGN KEY (bno)
                                         REFERENCES springBoard(bno)
                                         ON DELETE CASCADE

);

CREATE INDEX idx_springBoard_type
    ON springBoard(board_type);

CREATE INDEX idx_springBoardFile_bno
    ON springBoardFile(bno);