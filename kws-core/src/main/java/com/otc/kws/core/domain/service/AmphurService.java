package com.otc.kws.core.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.entity.Amphur;
import com.otc.kws.core.domain.repository.jpa.JpaAmphurRepository;

@Service
public class AmphurService extends RepositoryKwsService<JpaAmphurRepository, Amphur, String>
{
	@Autowired
	protected JpaAmphurRepository amphurRepository;
	
	
	@Override
	protected JpaAmphurRepository getRepository() 
	{
		return amphurRepository;
	}

	@Override
	protected String getId(Amphur entity) 
	{
		return entity.getId();
	}
	
	
	public Amphur findByCountryIdAndCode(String countryId, String code)
	{
		return amphurRepository.findByCountryIdAndCode(countryId, code);
	}
	
	public List<Amphur> findByCountryId(String countryId)
	{
		return amphurRepository.findByCountryId(countryId);
	}
	
	public List<Amphur> findByProvinceId(String provinceId)
	{
		return amphurRepository.findByProvinceId(provinceId);
	}
}