package com.java.project3.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Bill {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@CreatedDate
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	@Column(updatable = false)
	private Date buyDate;
	
	@ManyToOne // mặc định là FetchEagerr
	private User user;
	
	//Cascade: Nếu xóa bill mà có bill items ở đây thì xóa hết billItems
	// luôn chứ k phải xóa billItems trước (foregin key)
	@OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, 
			orphanRemoval = true) 
	private List<BillItem> billItems;
}
