package com.capstone.repository;

import com.capstone.domain.Video;
import com.capstone.domain.VideoType;
import com.capstone.domain.Voice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video,Long> {


   /* @Query("""
    SELECT v FROM Video v
    WHERE v.book.bookId = :bookId 
      AND v.videoType = :type 
      AND v.user.userId = :userId
""")
    Optional<Video> findCustomVideo(@Param("userId") Long userId,@Param("bookId") Long bookId,@Param("type") VideoType type);*/

    @Query("""
    SELECT v From Video v
    WHERE v.book.bookId = :bookId
        AND v.videoType = :type
        AND v.user.userId = :userId
        AND v.voice = :voice
""")
    Optional<Video> findCustomVideo(@Param("userId") Long userId,@Param("bookId") Long bookId,@Param("type") VideoType type, @Param("voice") Voice voice);

    @Query("""
    SELECT v FROM Video v
    WHERE v.book.bookId = :bookId AND v.videoType = :type
""")
    Optional<Video> findDefaultVideo(@Param("bookId") Long bookId, @Param("type") VideoType type);




}
