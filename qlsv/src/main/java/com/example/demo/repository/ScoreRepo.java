package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Score;

public interface ScoreRepo extends JpaRepository<Score,Integer>{
	@Query("SELECT s FROM Score s  WHERE s.id = :id ")
    Page<Score> searchById(@Param("id") Integer id, Pageable pageable);
    @Query("SELECT s FROM Score s  WHERE s.score = :score ")
    Page<Score> searchByScore(@Param("score") Double score, Pageable pageable);

    @Query("SELECT s FROM Score s JOIN s.course c WHERE c.id = :x ")
    Page<Score> searchByCourseId(@Param("x") int cid, Pageable pageable);

    @Query("SELECT s FROM Score s JOIN s.student st WHERE st.id = :x ")
    Page<Score> searchByStudentId(@Param("x") int sId, Pageable pageable);

    @Query("SELECT s FROM Score s JOIN s.course c WHERE c.name LIKE :x ")
    Page<Score> searchByCourseName(@Param("x") String s, Pageable pageable);

    @Query("SELECT s FROM Score s JOIN s.student st WHERE st.studentCode LIKE :x ")
    Page<Score> searchByStudentCode(@Param("x") String s, Pageable pageable);
}
