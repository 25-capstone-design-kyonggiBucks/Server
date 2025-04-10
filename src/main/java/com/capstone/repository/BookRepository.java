package com.capstone.repository;

import com.capstone.domain.Book;
import com.capstone.domain.BookType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {


    @Query("""
    SELECT b FROM Book b
    WHERE b.bookType = :type
""")
    List<Book> findAllByBookType(@Param("type") BookType bookType);

}
