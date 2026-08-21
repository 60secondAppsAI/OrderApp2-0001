package com.OrderApp2.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.OrderApp2.domain.Portfolio;
import com.OrderApp2.dto.PortfolioDTO;
import com.OrderApp2.dto.PortfolioSearchDTO;
import com.OrderApp2.dto.PortfolioPageDTO;
import com.OrderApp2.dto.PortfolioConvertCriteriaDTO;
import com.OrderApp2.service.GenericService;
import com.OrderApp2.dto.common.RequestDTO;
import com.OrderApp2.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface PortfolioService extends GenericService<Portfolio, Integer> {

	List<Portfolio> findAll();

	ResultDTO addPortfolio(PortfolioDTO portfolioDTO, RequestDTO requestDTO);

	ResultDTO updatePortfolio(PortfolioDTO portfolioDTO, RequestDTO requestDTO);

    Page<Portfolio> getAllPortfolios(Pageable pageable);

    Page<Portfolio> getAllPortfolios(Specification<Portfolio> spec, Pageable pageable);

	ResponseEntity<PortfolioPageDTO> getPortfolios(PortfolioSearchDTO portfolioSearchDTO);
	
	List<PortfolioDTO> convertPortfoliosToPortfolioDTOs(List<Portfolio> portfolios, PortfolioConvertCriteriaDTO convertCriteria);

	PortfolioDTO getPortfolioDTOById(Integer portfolioId);



	
}
