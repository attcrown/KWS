package com.otc.kws.marketpet.lib.domain.service.customer;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.constant.RoleConst;
import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.constant.value.UserAccountGenusValue;
import com.otc.kws.core.domain.entity.EmailTemplate;
import com.otc.kws.core.domain.entity.UserAccount;
import com.otc.kws.core.domain.entity.UserRoleMapper;
import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.marketpet.lib.domain.constant.value.MkpCustomerRegisterStatusValue;
import com.otc.kws.marketpet.lib.domain.entity.MkpCustomer;
import com.otc.kws.marketpet.lib.domain.entity.MkpCustomerRegister;

@Component
public class MkpCustomerActivatedRegistrationCommandV1 extends MkpCustomerActivatedRegistrationCommand
{
	@Override
	public Response activatedRegistration(Request request) 
	{
		MkpCustomerRegister mkpCustomerRegister = mkpCustomerRegisterService.findById(request.getCustomerRegisterId());
		
		if(mkpCustomerRegister == null) {
			throw new KwsRuntimeException("CustomerRegister id ["+request.getCustomerRegisterId()+"] Not Found");
		}
		
		/*
		UserAccount userAccount = new UserAccount();
		userAccount.setId(UUID.randomUUID().toString());
		userAccount.setUsername(mkpCustomerRegister.getEmail());
		userAccount.setPassword(mkpCustomerRegister.getPassword());
		userAccount.setEmail(mkpCustomerRegister.getEmail());
		userAccount.setGenus(UserAccountGenusValue.Human);
		userAccount.setAccountLocked(false);
		userAccount.setPasswordLocked(false);
		userAccount.setStatus(UserAccountStatusValue.Inactived);
		userAccount.setCreatedBy(userAccount.getId());
		userAccount.setCreatedAt(request.getPerformedAt());
		try {
			userAccount = userAccountService.create(userAccount);
			logger.info("update userAccount => [" + userAccount + "]");
		} catch(Exception ex) {
			throw new KwsRuntimeException(ex);
		}
		
		UserRoleMapper userRoleMapper = new UserRoleMapper();
		userRoleMapper.setId(UUID.randomUUID().toString());
		userRoleMapper.setUserId(userAccount.getId());
		userRoleMapper.setRoleId(RoleConst.ID_MKP_CUSTOMER);
		userRoleMapper.setStatus(MasterStatusValue.Pending);
		userRoleMapper.setCreatedBy(userAccount.getId());
		userRoleMapper.setCreatedAt(request.getPerformedAt());
		try {
			userRoleMapper = userRoleMapperService.create(userRoleMapper);
			logger.info("update userRoleMapper => [" + userRoleMapper + "]");
		} catch(Exception ex) {
			throw new KwsRuntimeException(ex);
		}
		*/
		
		MkpCustomer invitedCustomer = null;
		
		if(mkpCustomerRegister.getInvitedCustomerId() != null) {
			invitedCustomer = mkpCustomerService.findById(mkpCustomerRegister.getInvitedCustomerId());
			
			if(invitedCustomer == null) {
				throw new KwsRuntimeException("Invited Customer id ["+mkpCustomerRegister.getInvitedCustomerId()+"] Not Found");
			}
		}
		
		MkpCustomer mkpCustomer = new MkpCustomer();
		mkpCustomer.setId(UUID.randomUUID().toString());
		mkpCustomer.setUserId(UUID.randomUUID().toString());
		if(invitedCustomer == null) {
			mkpCustomer.setLineHierachyLevel(1);
		} else {
			mkpCustomer.setLineHierachyLevel(invitedCustomer.getLineHierachyLevel() + 1);
			mkpCustomer.setRootLineId(invitedCustomer.getRootLineId());
			mkpCustomer.setParentLineId(invitedCustomer.getId());
		}
		mkpCustomer.setUsername(mkpCustomerRegister.getEmail());
		mkpCustomer.setPassword(mkpCustomerRegister.getPassword());
		mkpCustomer.setMobileNo(mkpCustomerRegister.getMobileNo());
		mkpCustomer.setEmail(mkpCustomerRegister.getEmail());
		mkpCustomer.setPoint(new BigDecimal(0.00));
		mkpCustomer.setScore(0);
		mkpCustomer.setStatus(MasterStatusValue.Pending);
		mkpCustomer.setCreatedBy(mkpCustomer.getUserId());
		mkpCustomer.setCreatedAt(request.getPerformedAt());
		try {
			mkpCustomer = mkpCustomerService.create(mkpCustomer);
			logger.info("create mkpCustomer => [" + mkpCustomer + "]");
		} catch(Exception ex) {
			throw new KwsRuntimeException(ex);
		}
		
		mkpCustomerRegister.setCustomerId(mkpCustomer.getId());
		mkpCustomerRegister.setUserId(mkpCustomer.getUserId());
		mkpCustomerRegister.setRegisterStatus(MkpCustomerRegisterStatusValue.Activated);
		mkpCustomerRegister.setActivatedAt(request.getPerformedAt());
		try {
			mkpCustomerRegister = mkpCustomerRegisterService.update(mkpCustomerRegister);
			logger.info("update mkpCustomerRegister => [" + mkpCustomerRegister + "]");
		} catch(Exception ex) {
			throw new KwsRuntimeException(ex);
		}
		
		sendEmail(mkpCustomerRegister);
		
		Response response = new Response();
		response.setMkpCustomerRegister(mkpCustomerRegister);
		response.setMkpCustomer(mkpCustomer);
		//response.setUserAccount(userAccount);
		//response.setUserRoleMappers(Arrays.asList(userRoleMapper));
		
		return response;
	}
	
	protected void sendEmail(MkpCustomerRegister mkpCustomerRegister)
	{
		logger.info("### " + getClass().getSimpleName() + ".sendEmail ###");
		
		EmailTemplate emailTemplate = emailTemplateService.findById("mkp.customer.registration.activated");
		
		if(emailTemplate != null) {
			String fromEmail = emailTemplate.getFromEmail();
			String fromName = emailTemplate.getFromName();
			List<String> mailTos = Arrays.asList(mkpCustomerRegister.getEmail());
			String subject = emailTemplate.getSubjectTemplate();
			String content = emailTemplate.getContentTemplate();
			
			logger.info("fromEmail => " + fromEmail);
			logger.info("fromName => " + fromName);
			logger.info("mailTos => " + mailTos);
			logger.info("subject => " + subject);
			logger.info("content => " + content);
			
			if(fromEmail != null && fromName != null) {
				emailService.sendMail(fromEmail, fromName, mailTos, subject, content);
			} else {
				emailService.sendMail(mailTos, subject, content);
			}
		}
	}
}