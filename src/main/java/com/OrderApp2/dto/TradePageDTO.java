package com.OrderApp2.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TradePageDTO {

	private Integer page = 0;
	private Long totalElements = 0L;

	private List<TradeDTO> trades;
}





