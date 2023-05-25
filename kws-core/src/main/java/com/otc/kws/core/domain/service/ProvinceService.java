package com.otc.kws.core.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.entity.Province;
import com.otc.kws.core.domain.repository.jpa.JpaProvinceRepository;

@Service
public class ProvinceService extends RepositoryKwsService<JpaProvinceRepository, Province, String>
{
	@Autowired
	protected JpaProvinceRepository provinceRepository;
	
	
	@Override
	protected JpaProvinceRepository getRepository() 
	{
		return provinceRepository;
	}

	@Override
	protected String getId(Province entity) 
	{
		return entity.getId();
	}
	
	
	public Province findByCountryIdAndCode(String countryId, String code)
	{
		return provinceRepository.findByCountryIdAndCode(countryId, code);
	}
	
	public List<Province> findByCountryId(String countryId)
	{
		return provinceRepository.findByCountryId(countryId);
	}
}