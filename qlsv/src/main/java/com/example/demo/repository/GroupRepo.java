package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Group;
import com.example.demo.entity.User;

public interface GroupRepo extends JpaRepository<Group,Integer>{

	@Modifying
    @Query("SELECT g FROM Group g WHERE g.name = :name")
    public void deleteByName(@Param("name") String name);

    @Query("SELECT g FROM Group g WHERE g.id = :gid")
    Page<Group> searchById(@Param("gid") int id, Pageable pageable);

    @Query("SELECT g FROM Group g  WHERE g.name = :x")
    Page<Group> searchByName(@Param("x") String name, Pageable pageable);

    @Query("SELECT u FROM User u JOIN u.groups g WHERE u.id = :id")
    Page<Group> searchByUser(@Param("id") int id, Pageable pageable);
}
