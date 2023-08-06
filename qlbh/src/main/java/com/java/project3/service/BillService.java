package com.java.project3.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.java.project3.dto.BillDTO;
import com.java.project3.dto.BillItemDTO;
import com.java.project3.dto.BillStatisticDTO;
import com.java.project3.dto.CategoryDTO;
import com.java.project3.dto.PageDTO;
import com.java.project3.entity.Bill;
import com.java.project3.entity.BillItem;
import com.java.project3.entity.Category;
import com.java.project3.entity.User;
import com.java.project3.repo.BillRepo;
import com.java.project3.repo.ProductRepo;
import com.java.project3.repo.UserRepo;

@Service
public class BillService {
	@Autowired
	BillRepo billRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	ProductRepo productRepo;
	
	@PersistenceContext
	EntityManager entityManager;
	
	// nhớ dùng hàm này ở 1 class mới trong lớp Repo
	@SuppressWarnings("unchecked")
	public List<Bill> searchByDate(@Param("x") Date s) {
		String jpql = "SELECT b FROM Bill b WHERE b.createdAt >= :x";
		return entityManager.
			createQuery(jpql).setParameter("x", s).
			setMaxResults(10).setFirstResult(0).getResultList();
	}
	
	@Transactional
	public void create(BillDTO billDTO) {
		User user = userRepo.findById(billDTO.getUser().getId()).orElseThrow(NoResultException::new);
		Bill bill = new Bill();
		bill.setUser(user);
		
		List<BillItem> billItems = new ArrayList<>();
		
		for(BillItemDTO billItemDTO : billDTO.getBillItems()) {
			BillItem billItem = new BillItem();
			billItem.setBill(bill);
			billItem.setProduct(productRepo.findById(billItemDTO.getProduct().getId()).orElseThrow(NoResultException::new));
			billItem.setPrice(billItemDTO.getPrice());
			billItem.setQuantity(billItemDTO.getQuantity());
			billItems.add(billItem);
		}
		
		bill.setBillItems(billItems);
		billRepo.save(bill);
	}
	
	
	@Transactional
	public void update(BillDTO billDTO) {
		User user = userRepo.findById(billDTO.getUser().getId()).orElseThrow(NoResultException::new);
		Bill bill = billRepo.findById(billDTO.getId()).orElseThrow(NoResultException::new);
//		bill.getBillItems().remove(0); 
//		for(BillItemDTO billItemDTO : billDTO.getBillItems()) {
//			BillItem billItem = new BillItem();
//			billItem.setBill(bill);
//			billItem.setProduct(productRepo.findById(billItemDTO.getProduct().getId()).orElseThrow(NoResultException::new));
//			billItem.setPrice(billItemDTO.getPrice());
//			billItem.setQuantity(billItemDTO.getQuantity());
//			bill.getBillItems().add(billItem);
//		}
		bill.setUser(user);
		billRepo.save(bill);
	}
	
	@Transactional
	public void delete(int id) {
		billRepo.deleteById(id);
		
	}
	
	@Cacheable(cacheNames = "bill-search") // dữ liệu khác nhau phải để tên cache khác nhau
	public PageDTO<BillDTO> searchAllBill(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		
		Page<Bill> pageRS = billRepo.findAll(pageable);
		PageDTO<BillDTO> pageDTO = new PageDTO<>();
		pageDTO.setTotalPages(pageRS.getTotalPages());
		pageDTO.setTotalElements(pageDTO.getTotalElements());
		
		// java 8: lambda, stream
		List<BillDTO> billDTOs = pageRS.get().
				map(bill -> new ModelMapper().map(bill, BillDTO.class))
				.collect(Collectors.toList());
		pageDTO.setContents(billDTOs); // set vao pageDTO
		return pageDTO;
	}

	public BillDTO getById(int id) {
		Bill bill = billRepo.findById(id).orElseThrow(NoResultException::new);
		BillDTO billDTO = new ModelMapper().map(bill, BillDTO.class);
		return billDTO;
	}
	
	// thống kê các đơn hàng
	public PageDTO<BillStatisticDTO> statistic() {
		List<Object[]> list = billRepo.thongKeBill();
		PageDTO<BillStatisticDTO> pageDTO = new PageDTO<>();
		pageDTO.setTotalPages(1);
		pageDTO.setTotalElements(list.size());
		List<BillStatisticDTO> billStatisticDTOs = new ArrayList<>();
		for(Object[] arr : list) {
			BillStatisticDTO billStatisticDTO = new BillStatisticDTO((long) (arr[0]),
					String.valueOf(arr[1]) + "/" + String.valueOf(arr[2]));
			billStatisticDTOs.add(billStatisticDTO);
		}
		pageDTO.setContents(billStatisticDTOs);		
		return pageDTO;
	}
}
