INSERT INTO book (title, book_type, image_path, image_name, summary) VALUES
('금도끼 은도끼','FOLKTALE','/uploads/images/Golden_axe,_silver_axe.jpg','Golden_axe,_silver_axe','욕심 없이 정직했던 나무꾼에게 신비한 산신령이 나타났어요!
과연 산신령은 어떤 도끼를 내밀고, 나무꾼의 선택은 어떤 보답을 받을까요?'),
('아낌없이 주는 나무','CLASSIC','/uploads/images/The_giving_tree.jpg','The_giving_tree','작은 소년과 한 그루의 나무가 친구가 되었어요.
시간이 흐를수록 달라지는 소년의 모습과 변함없이 주는 나무의 마음, 그 따뜻한 이야기 속으로 함께 떠나볼까요?');

INSERT INTO video (book_id, video_type, video_path, video_name) VALUES
(4,'DEFAULT','/uploads/videos/default/axe.mp4','axe.mp4'),
(5,'DEFAULT','/uploads/videos/default/The_giving_tree.mp4','The_giving_tree.mp4');