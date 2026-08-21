package com.OrderApp2.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.OrderApp2.domain.Market;
import com.OrderApp2.dto.MarketDTO;
import com.OrderApp2.dto.MarketSearchDTO;
import com.OrderApp2.dto.MarketPageDTO;
import com.OrderApp2.dto.MarketConvertCriteriaDTO;
import com.OrderApp2.service.GenericService;
import com.OrderApp2.dto.common.RequestDTO;
import com.OrderApp2.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface MarketService extends GenericService<Market, Integer> {

	List<Market> findAll();

	ResultDTO addMarket(MarketDTO marketDTO, RequestDTO requestDTO);

	ResultDTO updateMarket(MarketDTO marketDTO, RequestDTO requestDTO);

    Page<Market> getAllMarkets(Pageable pageable);

    Page<Market> getAllMarkets(Specification<Market> spec, Pageable pageable);

	ResponseEntity<MarketPageDTO> getMarkets(MarketSearchDTO marketSearchDTO);
	
	List<MarketDTO> convertMarketsToMarketDTOs(List<Market> markets, MarketConvertCriteriaDTO convertCriteria);

	MarketDTO getMarketDTOById(Integer marketId);



	
}
