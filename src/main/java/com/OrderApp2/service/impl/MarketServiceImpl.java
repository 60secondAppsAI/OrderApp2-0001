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
import com.OrderApp2.dao.MarketDAO;
import com.OrderApp2.domain.Market;
import com.OrderApp2.dto.MarketDTO;
import com.OrderApp2.dto.MarketSearchDTO;
import com.OrderApp2.dto.MarketPageDTO;
import com.OrderApp2.dto.MarketConvertCriteriaDTO;
import com.OrderApp2.dto.common.RequestDTO;
import com.OrderApp2.dto.common.ResultDTO;
import com.OrderApp2.service.MarketService;
import com.OrderApp2.util.ControllerUtils;


@Service
public class MarketServiceImpl extends GenericServiceImpl<Market, Integer> implements MarketService {

    private final static Logger logger = LoggerFactory.getLogger(MarketServiceImpl.class);

	@Autowired
	MarketDAO marketDao;

	

	@Override
	public GenericDAO<Market, Integer> getDAO() {
		return (GenericDAO<Market, Integer>) marketDao;
	}
	
	public List<Market> findAll () {
		List<Market> markets = marketDao.findAll();
		
		return markets;	
		
	}

	public ResultDTO addMarket(MarketDTO marketDTO, RequestDTO requestDTO) {

		Market market = new Market();

		market.setMarketId(marketDTO.getMarketId());

		market.setTickerSymbol(marketDTO.getTickerSymbol());

		market.setCurrentPrice(marketDTO.getCurrentPrice());

		market.setPriceChange(marketDTO.getPriceChange());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		market = marketDao.save(market);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Market> getAllMarkets(Pageable pageable) {
		return marketDao.findAll(pageable);
	}

	public Page<Market> getAllMarkets(Specification<Market> spec, Pageable pageable) {
		return marketDao.findAll(spec, pageable);
	}

	public ResponseEntity<MarketPageDTO> getMarkets(MarketSearchDTO marketSearchDTO) {
	
			Integer marketId = marketSearchDTO.getMarketId(); 
 			String tickerSymbol = marketSearchDTO.getTickerSymbol(); 
   			String sortBy = marketSearchDTO.getSortBy();
			String sortOrder = marketSearchDTO.getSortOrder();
			String searchQuery = marketSearchDTO.getSearchQuery();
			Integer page = marketSearchDTO.getPage();
			Integer size = marketSearchDTO.getSize();

	        Specification<Market> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, marketId, "marketId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, tickerSymbol, "tickerSymbol"); 
			
			
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("tickerSymbol")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Market> markets = this.getAllMarkets(spec, pageable);
		
		//System.out.println(String.valueOf(markets.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(markets.getTotalPages()));
		
		List<Market> marketsList = markets.getContent();
		
		MarketConvertCriteriaDTO convertCriteria = new MarketConvertCriteriaDTO();
		List<MarketDTO> marketDTOs = this.convertMarketsToMarketDTOs(marketsList,convertCriteria);
		
		MarketPageDTO marketPageDTO = new MarketPageDTO();
		marketPageDTO.setMarkets(marketDTOs);
		marketPageDTO.setTotalElements(markets.getTotalElements());
		return ResponseEntity.ok(marketPageDTO);
	}

	public List<MarketDTO> convertMarketsToMarketDTOs(List<Market> markets, MarketConvertCriteriaDTO convertCriteria) {
		
		List<MarketDTO> marketDTOs = new ArrayList<MarketDTO>();
		
		for (Market market : markets) {
			marketDTOs.add(convertMarketToMarketDTO(market,convertCriteria));
		}
		
		return marketDTOs;

	}
	
	public MarketDTO convertMarketToMarketDTO(Market market, MarketConvertCriteriaDTO convertCriteria) {
		
		MarketDTO marketDTO = new MarketDTO();

		marketDTO.setMarketId(market.getMarketId());

		marketDTO.setTickerSymbol(market.getTickerSymbol());

		marketDTO.setCurrentPrice(market.getCurrentPrice());

		marketDTO.setPriceChange(market.getPriceChange());
		
		return marketDTO;
	}

	public ResultDTO updateMarket(MarketDTO marketDTO, RequestDTO requestDTO) {
		
		Market market = marketDao.getById(marketDTO.getMarketId());
		
		market.setMarketId(ControllerUtils.setValue(market.getMarketId(), marketDTO.getMarketId()));
		
		market.setTickerSymbol(ControllerUtils.setValue(market.getTickerSymbol(), marketDTO.getTickerSymbol()));
		
		market.setCurrentPrice(ControllerUtils.setValue(market.getCurrentPrice(), marketDTO.getCurrentPrice()));
		
		market.setPriceChange(ControllerUtils.setValue(market.getPriceChange(), marketDTO.getPriceChange()));

        market = marketDao.save(market);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public MarketDTO getMarketDTOById(Integer marketId) {
	
		Market market = marketDao.getById(marketId);
		
		MarketConvertCriteriaDTO convertCriteria = new MarketConvertCriteriaDTO();
		return(this.convertMarketToMarketDTO(market,convertCriteria));
	}

}
