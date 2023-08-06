package com.java.project3.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.project3.dto.BillDTO;
import com.java.project3.dto.BillStatisticDTO;
import com.java.project3.dto.CategoryDTO;
import com.java.project3.dto.PageDTO;
import com.java.project3.dto.ResponseDTO;
import com.java.project3.repo.BillRepo;
import com.java.project3.service.BillService;

@RestController
@RequestMapping("/api/bill")
public class BillRestController {
	@Autowired
	BillService billService;
	
	//jack son
	@DeleteMapping("/delete") 
	public ResponseDTO<Void> delete(@RequestParam("id") int id) {
		billService.delete(id);
		return ResponseDTO.<Void>builder().status(200).build();
	}
	
	@GetMapping("/get/{id}") 
	public ResponseDTO<BillDTO> get(@PathVariable("id") int id) {
		BillDTO billDTO = billService.getById(id);
//		ResponseDTO<UserDTO> responseDTO = new ResponseDTO<UserDTO>();
//		responseDTO.setStatus(200);
//		responseDTO.setData(userDTO);
		return ResponseDTO.<BillDTO>builder().
				status(200).data(billDTO).build();
	}
	
	@GetMapping("/search")
	public ResponseDTO<PageDTO<BillDTO>> search(
		@RequestParam(name = "id", required = false) Integer id,
		@RequestParam(name = "name", required = false) String name, 
		@RequestParam(name = "size", required = false) Integer size,
		@RequestParam(name = "page", required = false) Integer page) {
			
		size = size == null ? 10 : size;
		page = page == null ? 0 : page;
		
		PageDTO<BillDTO> pageRS = billService.searchAllBill(page, size);
		
		return ResponseDTO.<PageDTO<BillDTO>>builder()
				.status(200).data(pageRS).build();
	}
	
	@PostMapping("/new")
	public ResponseDTO<BillDTO> create(@RequestBody @ModelAttribute BillDTO billDTO) throws IllegalStateException, IOException {
		billService.create(billDTO);
		return ResponseDTO.<BillDTO>builder().
				status(200).data(billDTO).build();
	}
	
	@PostMapping("/edit") // ?id=1
	public ResponseDTO<Void> edit(@ModelAttribute BillDTO billDTO) throws IllegalStateException, IOException {
		billService.update(billDTO);
		return ResponseDTO.<Void>builder().status(200).build();
	}
	
	@GetMapping("/statistic") // thống kê các đơn hàng 
	public ResponseDTO<PageDTO<BillStatisticDTO>> searchBillStatistic() {
		PageDTO<BillStatisticDTO> pageRS = billService.statistic();
		return ResponseDTO.<PageDTO<BillStatisticDTO>>builder().
				status(200).msg("Search statistic successfully").data(pageRS).build();
	}
}
