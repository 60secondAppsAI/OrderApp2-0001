package com.OrderApp2.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.OrderApp2.dao.GenericDAO;
import com.OrderApp2.service.GenericService;
import com.OrderApp2.service.impl.GenericServiceImpl;
import com.OrderApp2.dao.TradeDAO;
import com.OrderApp2.domain.Trade;
import com.OrderApp2.dto.TradeDTO;
import com.OrderApp2.dto.TradeSearchDTO;
import com.OrderApp2.dto.TradePageDTO;
import com.OrderApp2.dto.TradeConvertCriteriaDTO;
import com.OrderApp2.dto.common.RequestDTO;
import com.OrderApp2.dto.common.ResultDTO;
import com.OrderApp2.service.TradeService;
import com.OrderApp2.util.ControllerUtils;


@Service
public class TradeServiceImpl extends GenericServiceImpl<Trade, Integer> implements TradeService {

    private final static Logger logger = LoggerFactory.getLogger(TradeServiceImpl.class);

	@Autowired
	TradeDAO tradeDao;

	

	@Override
	public GenericDAO<Trade, Integer> getDAO() {
		return (GenericDAO<Trade, Integer>) tradeDao;
	}
	
	public List<Trade> findAll () {
		List<Trade> trades = tradeDao.findAll();
		
		return trades;	
		
	}

	public ResultDTO addTrade(TradeDTO tradeDTO, RequestDTO requestDTO) {

		Trade trade = new Trade();

		trade.setTradeId(tradeDTO.getTradeId());

		trade.setTradeDate(tradeDTO.getTradeDate());

		trade.setQuantity(tradeDTO.getQuantity());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		trade = tradeDao.save(trade);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Trade> getAllTrades(Pageable pageable) {
		return tradeDao.findAll(pageable);
	}

	public Page<Trade> getAllTrades(Specification<Trade> spec, Pageable pageable) {
		return tradeDao.findAll(spec, pageable);
	}

	public ResponseEntity<TradePageDTO> getTrades(TradeSearchDTO tradeSearchDTO) {
	
			Integer tradeId = tradeSearchDTO.getTradeId(); 
 			Date tradeDate = tradeSearchDTO.getTradeDate();
 			Integer quantity = tradeSearchDTO.getQuantity(); 
 			String sortBy = tradeSearchDTO.getSortBy();
			String sortOrder = tradeSearchDTO.getSortOrder();
			String searchQuery = tradeSearchDTO.getSearchQuery();
			Integer page = tradeSearchDTO.getPage();
			Integer size = tradeSearchDTO.getSize();

	        Specification<Trade> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, tradeId, "tradeId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, tradeDate, "tradeDate");
			
			spec = ControllerUtils.andIfNecessary(spec, quantity, "quantity"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Trade> trades = this.getAllTrades(spec, pageable);
		
		//System.out.println(String.valueOf(trades.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(trades.getTotalPages()));
		
		List<Trade> tradesList = trades.getContent();
		
		TradeConvertCriteriaDTO convertCriteria = new TradeConvertCriteriaDTO();
		List<TradeDTO> tradeDTOs = this.convertTradesToTradeDTOs(tradesList,convertCriteria);
		
		TradePageDTO tradePageDTO = new TradePageDTO();
		tradePageDTO.setTrades(tradeDTOs);
		tradePageDTO.setTotalElements(trades.getTotalElements());
		return ResponseEntity.ok(tradePageDTO);
	}

	public List<TradeDTO> convertTradesToTradeDTOs(List<Trade> trades, TradeConvertCriteriaDTO convertCriteria) {
		
		List<TradeDTO> tradeDTOs = new ArrayList<TradeDTO>();
		
		for (Trade trade : trades) {
			tradeDTOs.add(convertTradeToTradeDTO(trade,convertCriteria));
		}
		
		return tradeDTOs;

	}
	
	public TradeDTO convertTradeToTradeDTO(Trade trade, TradeConvertCriteriaDTO convertCriteria) {
		
		TradeDTO tradeDTO = new TradeDTO();

		tradeDTO.setTradeId(trade.getTradeId());

		tradeDTO.setTradeDate(trade.getTradeDate());

		tradeDTO.setQuantity(trade.getQuantity());
		
		return tradeDTO;
	}

	public ResultDTO updateTrade(TradeDTO tradeDTO, RequestDTO requestDTO) {
		
		Trade trade = tradeDao.getById(tradeDTO.getTradeId());
		
		trade.setTradeId(ControllerUtils.setValue(trade.getTradeId(), tradeDTO.getTradeId()));
		
		trade.setTradeDate(ControllerUtils.setValue(trade.getTradeDate(), tradeDTO.getTradeDate()));
		
		trade.setQuantity(ControllerUtils.setValue(trade.getQuantity(), tradeDTO.getQuantity()));

        trade = tradeDao.save(trade);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public TradeDTO getTradeDTOById(Integer tradeId) {
	
		Trade trade = tradeDao.getById(tradeId);
		
		TradeConvertCriteriaDTO convertCriteria = new TradeConvertCriteriaDTO();
		return(this.convertTradeToTradeDTO(trade,convertCriteria));
	}

}
