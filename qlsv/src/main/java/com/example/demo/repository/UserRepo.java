package com.example.demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.User;

public interface UserRepo extends JpaRepository<User,Integer>{
	@Query("SELECT u FROM User u left join u.userRoles ur WHERE u.name LIKE :x ")
    Page<User> searchByName(@Param("x") String s, Pageable pageable);

    @Query("SELECT u FROM User u " + "WHERE u.createdAt >= :start and u.createdAt <= :end")
    Page<User> searchByDate(@Param("start") Date start, @Param("end") Date end, Pageable pageable);

    @Query("SELECT u FROM User u " + "WHERE u.createdAt >= :start")
    Page<User> searchByStartDate(@Param("start") Date start, Pageable pageable);

    @Query("SELECT u FROM User u " + "WHERE u.createdAt <= :end")
    Page<User> searchByEndDate(@Param("end") Date end, Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.name LIKE :x AND u.createdAt >= :start AND u.createdAt <= :end")
    Page<User> searchByNameAndDate(@Param("x") String s, @Param("start") Date start, @Param("end") Date end,
                                   Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.name LIKE :x AND u.createdAt >= :start")
    Page<User> searchByNameAndStartDate(@Param("x") String s, @Param("start") Date start, Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.name LIKE :x AND u.createdAt <= :end")
    Page<User> searchByNameAndEndDate(@Param("x") String s, @Param("end") Date end, Pageable pageable);

	User findByUsername(String username);
}
