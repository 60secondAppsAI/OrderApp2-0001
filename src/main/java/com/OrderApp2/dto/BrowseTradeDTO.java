package com.OrderApp2.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseTradeDTO {

	private Integer ownerId;

	private Integer tradeId;

	private Integer tradeStatus;
	
	private Integer nextOrPrevious;
}

