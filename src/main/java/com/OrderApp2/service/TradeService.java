package com.OrderApp2.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.OrderApp2.domain.Trade;
import com.OrderApp2.dto.TradeDTO;
import com.OrderApp2.dto.TradeSearchDTO;
import com.OrderApp2.dto.TradePageDTO;
import com.OrderApp2.dto.TradeConvertCriteriaDTO;
import com.OrderApp2.service.GenericService;
import com.OrderApp2.dto.common.RequestDTO;
import com.OrderApp2.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface TradeService extends GenericService<Trade, Integer> {

	List<Trade> findAll();

	ResultDTO addTrade(TradeDTO tradeDTO, RequestDTO requestDTO);

	ResultDTO updateTrade(TradeDTO tradeDTO, RequestDTO requestDTO);

    Page<Trade> getAllTrades(Pageable pageable);

    Page<Trade> getAllTrades(Specification<Trade> spec, Pageable pageable);

	ResponseEntity<TradePageDTO> getTrades(TradeSearchDTO tradeSearchDTO);
	
	List<TradeDTO> convertTradesToTradeDTOs(List<Trade> trades, TradeConvertCriteriaDTO convertCriteria);

	TradeDTO getTradeDTOById(Integer tradeId);



	
}
