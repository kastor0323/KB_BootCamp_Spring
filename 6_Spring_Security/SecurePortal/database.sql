-- 1. 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS secure_portal_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

-- 2. 사용자 계정 생성
CREATE USER IF NOT EXISTS 'secure_app'@'%'
    IDENTIFIED BY '1234';

-- 3. secure_portal_db 데이터베이스에 대한 권한 부여
GRANT ALL PRIVILEGES
    ON secure_portal_db.*
    TO 'secure_app'@'%';

-- 4. 권한 설정 즉시 반영
FLUSH PRIVILEGES;

USE secure_portal_db;

-- 외래 키를 가진 자식 테이블을 먼저 삭제합니다.
DROP TABLE IF EXISTS portal_user_role;

-- 부모 테이블을 나중에 삭제합니다.
DROP TABLE IF EXISTS portal_user;

CREATE TABLE portal_user (
                             user_id VARCHAR(50) PRIMARY KEY,
                             password VARCHAR(100) NOT NULL,
                             user_name VARCHAR(100) NOT NULL,
                             enabled BOOLEAN NOT NULL DEFAULT TRUE,
                             created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE portal_user_role (
                                  user_id VARCHAR(50) NOT NULL,
                                  role_name VARCHAR(50) NOT NULL,

                                  PRIMARY KEY (user_id, role_name),

                                  CONSTRAINT fk_portal_user_role
                                      FOREIGN KEY (user_id)
                                          REFERENCES portal_user(user_id)
                                          ON DELETE CASCADE
);

INSERT INTO portal_user (
    user_id,
    password,
    user_name,
    enabled
) VALUES
      (
          'member1',
          '$2a$10$EsIMfxbJ6NuvwX7MDj4WqOYFzLU9U/lddCyn0nic5dFo3VfJYrXYC',
          '일반 회원',
          TRUE
      ),
      (
          'admin1',
          '$2a$10$EsIMfxbJ6NuvwX7MDj4WqOYFzLU9U/lddCyn0nic5dFo3VfJYrXYC',
          '포털 관리자',
          TRUE
      );

INSERT INTO portal_user_role (
    user_id,
    role_name
) VALUES
      ('member1', 'ROLE_MEMBER'),
      ('admin1', 'ROLE_MEMBER'),
      ('admin1', 'ROLE_ADMIN');

SELECT
    u.user_id,
    u.user_name,
    u.enabled,
    r.role_name
FROM portal_user u
         LEFT JOIN portal_user_role r
                   ON u.user_id = r.user_id
ORDER BY
    u.user_id,
    r.role_name;

