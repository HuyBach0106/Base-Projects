package com.java.project3.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.java.project3.entity.Bill;

public interface BillRepo extends JpaRepository<Bill, Integer>{
	@Modifying
	@Query("DELETE FROM Bill u WHERE u.buyDate >= :x")
	List<Bill> searchByDate(@Param("x") Date s);
	
	// đếm số lượng đơn group by Month(buyDate)
	// - dùng custom object để build
	// SELECT id, MONTH(buyDate) from bill;
	// SELECT COUNT(*), MONTH(buyDate) from BILL
	// GROUP BY MONTH(buyDate)
	// mảng object tượng trưng cho cột
	@Query("SELECT count(b.id), month(b.buyDate), year(b.buyDate) " 
	+ "FROM Bill b GROUP BY month(b.buyDate), year(b.buyDate) ")
	List<Object[]> thongKeBill();	
	
	
}
