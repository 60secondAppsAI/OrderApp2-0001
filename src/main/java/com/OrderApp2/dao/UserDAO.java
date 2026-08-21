package com.OrderApp2.dao;

import java.util.List;
import java.util.Date;

import com.OrderApp2.dao.GenericDAO;
import com.OrderApp2.domain.User;

import java.util.Optional;



import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface UserDAO extends GenericDAO<User, Integer> {
  
	List<User> findAll();
	


}

