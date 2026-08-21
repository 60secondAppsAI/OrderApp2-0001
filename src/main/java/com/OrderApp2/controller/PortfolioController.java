package com.OrderApp2.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.ZoneId;


import com.OrderApp2.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;

import com.OrderApp2.domain.Portfolio;
import com.OrderApp2.dto.PortfolioDTO;
import com.OrderApp2.dto.PortfolioSearchDTO;
import com.OrderApp2.dto.PortfolioPageDTO;
import com.OrderApp2.service.PortfolioService;
import com.OrderApp2.dto.common.RequestDTO;
import com.OrderApp2.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/portfolio")
@RestController
public class PortfolioController {

	private final static Logger logger = LoggerFactory.getLogger(PortfolioController.class);

	@Autowired
	PortfolioService portfolioService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Portfolio> getAll() {

		List<Portfolio> portfolios = portfolioService.findAll();
		
		return portfolios;	
	}

	@GetMapping(value = "/{portfolioId}")
	@ResponseBody
	public PortfolioDTO getPortfolio(@PathVariable Integer portfolioId) {
		
		return (portfolioService.getPortfolioDTOById(portfolioId));
	}

 	@RequestMapping(value = "/addPortfolio", method = RequestMethod.POST)
	public ResponseEntity<?> addPortfolio(@RequestBody PortfolioDTO portfolioDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = portfolioService.addPortfolio(portfolioDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/portfolios")
	public ResponseEntity<PortfolioPageDTO> getPortfolios(PortfolioSearchDTO portfolioSearchDTO) {
 
		return portfolioService.getPortfolios(portfolioSearchDTO);
	}	

	@RequestMapping(value = "/updatePortfolio", method = RequestMethod.POST)
	public ResponseEntity<?> updatePortfolio(@RequestBody PortfolioDTO portfolioDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = portfolioService.updatePortfolio(portfolioDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
