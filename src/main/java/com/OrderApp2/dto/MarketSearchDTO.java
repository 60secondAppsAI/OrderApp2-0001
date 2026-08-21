package com.OrderApp2.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MarketSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer marketId;
	
	private String tickerSymbol;
	
	private double currentPrice;
	
	private double priceChange;
	
}
