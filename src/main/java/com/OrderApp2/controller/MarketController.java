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

import com.OrderApp2.domain.Market;
import com.OrderApp2.dto.MarketDTO;
import com.OrderApp2.dto.MarketSearchDTO;
import com.OrderApp2.dto.MarketPageDTO;
import com.OrderApp2.service.MarketService;
import com.OrderApp2.dto.common.RequestDTO;
import com.OrderApp2.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/market")
@RestController
public class MarketController {

	private final static Logger logger = LoggerFactory.getLogger(MarketController.class);

	@Autowired
	MarketService marketService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Market> getAll() {

		List<Market> markets = marketService.findAll();
		
		return markets;	
	}

	@GetMapping(value = "/{marketId}")
	@ResponseBody
	public MarketDTO getMarket(@PathVariable Integer marketId) {
		
		return (marketService.getMarketDTOById(marketId));
	}

 	@RequestMapping(value = "/addMarket", method = RequestMethod.POST)
	public ResponseEntity<?> addMarket(@RequestBody MarketDTO marketDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = marketService.addMarket(marketDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/markets")
	public ResponseEntity<MarketPageDTO> getMarkets(MarketSearchDTO marketSearchDTO) {
 
		return marketService.getMarkets(marketSearchDTO);
	}	

	@RequestMapping(value = "/updateMarket", method = RequestMethod.POST)
	public ResponseEntity<?> updateMarket(@RequestBody MarketDTO marketDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = marketService.updateMarket(marketDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
