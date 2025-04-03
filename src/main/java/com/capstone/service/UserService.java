package com.capstone.service;

import com.capstone.repository.UserRepository;
import com.capstone.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final VideoRepository videoRepository;


    /*
     * 사용자 custom 동영상은 동화당 최대 1개씩 저장할 수 있다.
     * */
    @Transactional
    public void addCustomVideo(Long userId, Long bookId, String videoPath, String videoName) {

    }
}
