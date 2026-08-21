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
import com.OrderApp2.dao.PortfolioDAO;
import com.OrderApp2.domain.Portfolio;
import com.OrderApp2.dto.PortfolioDTO;
import com.OrderApp2.dto.PortfolioSearchDTO;
import com.OrderApp2.dto.PortfolioPageDTO;
import com.OrderApp2.dto.PortfolioConvertCriteriaDTO;
import com.OrderApp2.dto.common.RequestDTO;
import com.OrderApp2.dto.common.ResultDTO;
import com.OrderApp2.service.PortfolioService;
import com.OrderApp2.util.ControllerUtils;


@Service
public class PortfolioServiceImpl extends GenericServiceImpl<Portfolio, Integer> implements PortfolioService {

    private final static Logger logger = LoggerFactory.getLogger(PortfolioServiceImpl.class);

	@Autowired
	PortfolioDAO portfolioDao;

	

	@Override
	public GenericDAO<Portfolio, Integer> getDAO() {
		return (GenericDAO<Portfolio, Integer>) portfolioDao;
	}
	
	public List<Portfolio> findAll () {
		List<Portfolio> portfolios = portfolioDao.findAll();
		
		return portfolios;	
		
	}

	public ResultDTO addPortfolio(PortfolioDTO portfolioDTO, RequestDTO requestDTO) {

		Portfolio portfolio = new Portfolio();

		portfolio.setPortfolioId(portfolioDTO.getPortfolioId());

		portfolio.setPortfolioName(portfolioDTO.getPortfolioName());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		portfolio = portfolioDao.save(portfolio);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Portfolio> getAllPortfolios(Pageable pageable) {
		return portfolioDao.findAll(pageable);
	}

	public Page<Portfolio> getAllPortfolios(Specification<Portfolio> spec, Pageable pageable) {
		return portfolioDao.findAll(spec, pageable);
	}

	public ResponseEntity<PortfolioPageDTO> getPortfolios(PortfolioSearchDTO portfolioSearchDTO) {
	
			Integer portfolioId = portfolioSearchDTO.getPortfolioId(); 
 			String portfolioName = portfolioSearchDTO.getPortfolioName(); 
 			String sortBy = portfolioSearchDTO.getSortBy();
			String sortOrder = portfolioSearchDTO.getSortOrder();
			String searchQuery = portfolioSearchDTO.getSearchQuery();
			Integer page = portfolioSearchDTO.getPage();
			Integer size = portfolioSearchDTO.getSize();

	        Specification<Portfolio> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, portfolioId, "portfolioId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, portfolioName, "portfolioName"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("portfolioName")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Portfolio> portfolios = this.getAllPortfolios(spec, pageable);
		
		//System.out.println(String.valueOf(portfolios.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(portfolios.getTotalPages()));
		
		List<Portfolio> portfoliosList = portfolios.getContent();
		
		PortfolioConvertCriteriaDTO convertCriteria = new PortfolioConvertCriteriaDTO();
		List<PortfolioDTO> portfolioDTOs = this.convertPortfoliosToPortfolioDTOs(portfoliosList,convertCriteria);
		
		PortfolioPageDTO portfolioPageDTO = new PortfolioPageDTO();
		portfolioPageDTO.setPortfolios(portfolioDTOs);
		portfolioPageDTO.setTotalElements(portfolios.getTotalElements());
		return ResponseEntity.ok(portfolioPageDTO);
	}

	public List<PortfolioDTO> convertPortfoliosToPortfolioDTOs(List<Portfolio> portfolios, PortfolioConvertCriteriaDTO convertCriteria) {
		
		List<PortfolioDTO> portfolioDTOs = new ArrayList<PortfolioDTO>();
		
		for (Portfolio portfolio : portfolios) {
			portfolioDTOs.add(convertPortfolioToPortfolioDTO(portfolio,convertCriteria));
		}
		
		return portfolioDTOs;

	}
	
	public PortfolioDTO convertPortfolioToPortfolioDTO(Portfolio portfolio, PortfolioConvertCriteriaDTO convertCriteria) {
		
		PortfolioDTO portfolioDTO = new PortfolioDTO();

		portfolioDTO.setPortfolioId(portfolio.getPortfolioId());

		portfolioDTO.setPortfolioName(portfolio.getPortfolioName());
		
		return portfolioDTO;
	}

	public ResultDTO updatePortfolio(PortfolioDTO portfolioDTO, RequestDTO requestDTO) {
		
		Portfolio portfolio = portfolioDao.getById(portfolioDTO.getPortfolioId());
		
		portfolio.setPortfolioId(ControllerUtils.setValue(portfolio.getPortfolioId(), portfolioDTO.getPortfolioId()));
		
		portfolio.setPortfolioName(ControllerUtils.setValue(portfolio.getPortfolioName(), portfolioDTO.getPortfolioName()));

        portfolio = portfolioDao.save(portfolio);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public PortfolioDTO getPortfolioDTOById(Integer portfolioId) {
	
		Portfolio portfolio = portfolioDao.getById(portfolioId);
		
		PortfolioConvertCriteriaDTO convertCriteria = new PortfolioConvertCriteriaDTO();
		return(this.convertPortfolioToPortfolioDTO(portfolio,convertCriteria));
	}

}
