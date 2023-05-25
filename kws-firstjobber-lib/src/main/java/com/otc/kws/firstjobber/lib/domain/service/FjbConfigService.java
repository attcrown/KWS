package com.otc.kws.firstjobber.lib.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.entity.Config;
import com.otc.kws.core.domain.service.ConfigService;
import com.otc.kws.firstjobber.lib.domain.constant.KwsFirstJobberConst;

@Service
public class FjbConfigService extends BaseKwsFirstJobberService
{
	@Autowired
	protected ConfigService configService;
	
	
	protected Config getByCategoryAndName(String category, String name)
	{
		return configService.getByModuleAndCategoryAndName(KwsFirstJobberConst.MODULE_NAME, category, name);
	}
	
	protected String getValueByCategoryAndName(String category, String name)
	{
		Config config = getByCategoryAndName(category, name);
		if(config != null) {
			return config.getValue();
		}
		return null;
	}
	
	
	public String getInternshipUploadTestLink()
	{
		return getValueByCategoryAndName("internship", "upload_test_link");
	}
	
	public String getTestHigh5Name()
	{
		return getValueByCategoryAndName("registration", "test.name.high5");
	}
	
	public String getTestHigh5Link()
	{
		return getValueByCategoryAndName("registration", "test.link.high5");
	}
	
	public String getTestSixteenPersonalityName()
	{
		return getValueByCategoryAndName("registration", "test.name.16personalities");
	}
	
	public String getTestSixteenPersonalityLink()
	{
		return getValueByCategoryAndName("registration", "test.link.16personalities");
	}
	
	public String getTestIQName()
	{
		return getValueByCategoryAndName("registration", "test.name.iq");
	}
	
	public String getTestIQLink()
	{
		return getValueByCategoryAndName("registration", "test.link.iq");
	}
}