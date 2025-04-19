-- 사용자 초기 데이터
-- 비밀번호는 "password"가 bcrypt로 인코딩된 값입니다
INSERT INTO user (login_id, password, role) VALUES 
('admin', '$2a$10$0IYHlSFVOpD.eSMJKTqIz.aB0kwV.NSrWRuLCvXjKhqb3xyqKVV5C', 'ROLE_ADMIN'),
('user1', '$2a$10$0IYHlSFVOpD.eSMJKTqIz.aB0kwV.NSrWRuLCvXjKhqb3xyqKVV5C', 'ROLE_CLIENT');

-- 책 초기 데이터
INSERT INTO book (title, book_type, image_path, image_name, summary) VALUES
('첫 번째 동화책', 'CLASSIC', '/images/books/book1.jpg', 'book1.jpg', '첫 번째 동화책의 줄거리입니다.'),
('두 번째 동화책', 'FOLKTALE', '/images/books/book2.jpg', 'book2.jpg', '두 번째 동화책의 줄거리입니다.'),
('세 번째 동화책', 'CLASSIC', '/images/books/book3.jpg', 'book3.jpg', '세 번째 동화책의 줄거리입니다.');

-- 기본 비디오 데이터
-- book_id 참조 (1, 2, 3은 위의 INSERT 순서에 따른 ID 값)
INSERT INTO video (book_id, video_type, video_path, video_name) VALUES
(1, 'DEFAULT', '/videos/book1_default.mp4', 'book1_default.mp4'),
(2, 'DEFAULT', '/videos/book2_default.mp4', 'book2_default.mp4'),
(3, 'DEFAULT', '/videos/book3_default.mp4', 'book3_default.mp4');

-- 샘플 오디오 데이터 (user1에 연결)
-- user_id=2는 위에서 두 번째로 삽입된 'user1'의 ID
INSERT INTO audio (user_id, audio_path, audio_name, audio_type, description) VALUES
(2, '/audios/user1_sample1.mp3', 'sample1.mp3', 'DEFAULT', '샘플 음성 1'),
(2, '/audios/user1_sample2.mp3', 'sample2.mp3', 'CUSTOM', '샘플 음성 2'); 