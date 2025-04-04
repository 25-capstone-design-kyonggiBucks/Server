package com.capstone.repository;

import com.capstone.domain.Book;
import com.capstone.domain.User;
import com.capstone.domain.Video;
import com.capstone.domain.VideoType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video,Long> {


    @Query("""
    SELECT v FROM Video v
    JOIN FETCH v.book
    WHERE v.user = :user
""")
    List<Video> findAllByUserWithBook(@Param("user") User user);

    @Query("""
    SELECT v FROM Video v
    WHERE v.book = :book AND v.videoType = :type
""")
    Optional<Video> findDefaultVideoByBook(@Param("book") Book book, @Param("type") VideoType type);

}
