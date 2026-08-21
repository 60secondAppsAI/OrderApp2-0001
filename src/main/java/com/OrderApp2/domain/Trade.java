package com.OrderApp2.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.time.Year;
import jakarta.persistence.Transient;

@Entity
@Table(name="trades")
@Getter @Setter @NoArgsConstructor
public class Trade {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="trade_id")
	private Integer tradeId;
    
  	@Column(name="trade_date")
	private Date tradeDate;
    
  	@Column(name="quantity")
	private Integer quantity;
    

}
