package com.cdgs.temple.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdgs.temple.repository.ForgetPassRepository;
import com.cdgs.temple.service.ForgetPassService;

@Service
public class ForgetPassServiceImpl implements ForgetPassService {

	private static final Logger log = LoggerFactory.getLogger(ForgetPassService.class);

	private final ForgetPassRepository forgetpassRepository;

	@Autowired
	public ForgetPassServiceImpl(ForgetPassRepository forgetpassRepository) {
		this.forgetpassRepository = forgetpassRepository;
	}

	@Override
	public Integer countUser(String userName, String idCard, String phoneNumber) {
		Integer count = null;
		try {
			count = forgetpassRepository.countUser(userName, idCard, phoneNumber);
		} catch (Exception e) {
			log.error("ForgetPassServiceImpl>>" + e.getMessage());
		}
		return count;
	}

}