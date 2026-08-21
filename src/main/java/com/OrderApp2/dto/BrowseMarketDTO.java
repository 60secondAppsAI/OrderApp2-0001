package com.OrderApp2.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseMarketDTO {

	private Integer ownerId;

	private Integer marketId;

	private Integer marketStatus;
	
	private Integer nextOrPrevious;
}

