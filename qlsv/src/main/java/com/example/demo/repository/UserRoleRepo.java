package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.UserRole;

public interface UserRoleRepo extends JpaRepository<UserRole,Integer>{
	@Modifying // đánh dấu câu lệnh Query là update/delete -> khi có Join bảng
    @Query("DELETE from UserRole ur WHERE ur.user.id = :uid")
    public void deleteByUserId(@Param("uid") int userId);

    @Query("select ur from UserRole ur JOIN ur.user u WHERE u.id = :uid")
    Page<UserRole> searchByUserId(@Param("uid") int userId, Pageable pageable);

    @Query("select ur from UserRole ur JOIN ur.user u WHERE u.id = :uid and ur.role = :role")
    Page<UserRole> searchByUserIdRole(@Param("uid") int userId,@Param("role") String role, Pageable pageable);

    @Query("select ur from UserRole ur WHERE ur.role like :role")
    Page<UserRole> searchByRole(@Param("role") String role, Pageable pageable);
}
