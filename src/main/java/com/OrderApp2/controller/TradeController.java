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

import com.OrderApp2.domain.Trade;
import com.OrderApp2.dto.TradeDTO;
import com.OrderApp2.dto.TradeSearchDTO;
import com.OrderApp2.dto.TradePageDTO;
import com.OrderApp2.service.TradeService;
import com.OrderApp2.dto.common.RequestDTO;
import com.OrderApp2.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/trade")
@RestController
public class TradeController {

	private final static Logger logger = LoggerFactory.getLogger(TradeController.class);

	@Autowired
	TradeService tradeService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Trade> getAll() {

		List<Trade> trades = tradeService.findAll();
		
		return trades;	
	}

	@GetMapping(value = "/{tradeId}")
	@ResponseBody
	public TradeDTO getTrade(@PathVariable Integer tradeId) {
		
		return (tradeService.getTradeDTOById(tradeId));
	}

 	@RequestMapping(value = "/addTrade", method = RequestMethod.POST)
	public ResponseEntity<?> addTrade(@RequestBody TradeDTO tradeDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = tradeService.addTrade(tradeDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/trades")
	public ResponseEntity<TradePageDTO> getTrades(TradeSearchDTO tradeSearchDTO) {
 
		return tradeService.getTrades(tradeSearchDTO);
	}	

	@RequestMapping(value = "/updateTrade", method = RequestMethod.POST)
	public ResponseEntity<?> updateTrade(@RequestBody TradeDTO tradeDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = tradeService.updateTrade(tradeDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
