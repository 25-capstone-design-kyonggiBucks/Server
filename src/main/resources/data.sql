-- 사용자 초기 데이터
-- 비밀번호는 "password"가 bcrypt로 인코딩된 값입니다
INSERT INTO user (login_id, password, role) VALUES
('user1', '$2a$10$0IYHlSFVOpD.eSMJKTqIz.aB0kwV.NSrWRuLCvXjKhqb3xyqKVV5C', 'ROLE_CLIENT');

-- 책 초기 데이터
INSERT INTO book (title, book_type, image_path, image_name, summary) VALUES
('첫 번째 동화책', 'NONE', '/uploads/images/book1.jpg', 'book1.jpg', '첫 번째 동화책의 줄거리입니다.'),
('두 번째 동화책', 'NONE', '/uploads/images/book2.jpg', 'book2.jpg', '두 번째 동화책의 줄거리입니다.'),
('세 번째 동화책', 'NONE', '/uploads/images/book3.jpg', 'book3.jpg', '세 번째 동화책의 줄거리입니다.'),
('금도끼 은도끼','FOLKTALE','/uploads/images/Golden_axe,_silver_axe.jpg','Golden_axe,_silver_axe','욕심 없이 정직했던 나무꾼에게 신비한 산신령이 나타났어요!
과연 산신령은 어떤 도끼를 내밀고, 나무꾼의 선택은 어떤 보답을 받을까요?'),
('아낌없이 주는 나무','CLASSIC','/uploads/images/The_giving_tree.jpg','The_giving_tree','작은 소년과 한 그루의 나무가 친구가 되었어요.
시간이 흐를수록 달라지는 소년의 모습과 변함없이 주는 나무의 마음, 그 따뜻한 이야기 속으로 함께 떠나볼까요?');

-- 기본 비디오 데이터
-- book_id 참조 (1, 2, 3은 위의 INSERT 순서에 따른 ID 값)
INSERT INTO video (book_id, video_type, video_path, video_name) VALUES
(1, 'DEFAULT', '/uploads/videos/book1_default.mp4', 'book1_default.mp4'),
(2, 'DEFAULT', '/uploads/videos/book2_default.mp4', 'book2_default.mp4'),
(3, 'DEFAULT', '/uploads/videos/book3_default.mp4', 'book3_default.mp4'),
(4,'DEFAULT','/uploads/videos/default/axe.mp4','axe.mp4'),
(5,'DEFAULT','/uploads/videos/default/The_giving_tree.mp4','The_giving_tree.mp4');


-- 샘플 오디오 데이터 (user1에 연결)
-- user_id=2는 위에서 두 번째로 삽입된 'user1'의 ID
INSERT INTO audio (user_id, audio_path, audio_name, audio_type, description) VALUES
(1, '/uploads/audios/user1_sample1.mp3', 'sample1.mp3', 'DEFAULT', '샘플 음성 1'),
(1, '/uploads/audios/user1_sample2.mp3', 'sample2.mp3', 'CUSTOM', '샘플 음성 2');