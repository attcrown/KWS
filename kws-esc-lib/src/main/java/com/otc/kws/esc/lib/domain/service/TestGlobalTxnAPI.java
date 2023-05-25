package com.otc.kws.esc.lib.domain.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.entity.UserAccount;
import com.otc.kws.core.domain.repository.jpa.JpaUserAccountRepository;
import com.otc.kws.esc.lib.domain.entity.Course;
import com.otc.kws.esc.lib.domain.repository.jpa.JpaCourseRepository;
import com.otc.kws.esc.lib.gateway.web.api.BaseKwsEscAPI;

@RestController
@RequestMapping("/api/test/global_txn")
public class TestGlobalTxnAPI extends BaseKwsEscAPI
{
	@Autowired
	protected JpaUserAccountRepository userAccountRepository;
	
	@Autowired
	protected JpaCourseRepository courseRepository;
	
	
	@Transactional
	@GetMapping
	public String test(@RequestParam String userId, @RequestParam String courseId, @RequestParam boolean error)
	{
		System.out.println("### TestGlobalTxnAPI.test ###");
		
		UserAccount userAccount = userAccountRepository.findById(userId).orElse(null);
		userAccount.setLastModifieddAt(new Date());
		userAccountRepository.save(userAccount);
		
		if(error) {
			throw new RuntimeException("TEST ERROR");
		}
		
		Course course = courseRepository.findById("course-001").orElse(null);
		course.setLastModifieddAt(new Date());
		courseRepository.save(course);
		
		return "success";
	}
}