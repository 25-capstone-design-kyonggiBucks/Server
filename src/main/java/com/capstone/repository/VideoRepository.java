package com.capstone.repository;

import com.capstone.domain.User;
import com.capstone.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video,Long> {


    @Query("""
    SELECT v FROM Video v
    JOIN FETCH v.book
    WHERE v.user = :user
""")
    List<Video> findAllByUserWithBook(@Param("user") User user);
}
