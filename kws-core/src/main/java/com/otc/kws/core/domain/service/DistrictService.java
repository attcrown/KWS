package com.otc.kws.core.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.entity.District;
import com.otc.kws.core.domain.repository.jpa.JpaDistrictRepository;

@Service
public class DistrictService extends RepositoryKwsService<JpaDistrictRepository, District, String>
{
	@Autowired
	protected JpaDistrictRepository districtRepository;
	
	
	@Override
	protected JpaDistrictRepository getRepository() 
	{
		return districtRepository;
	}

	@Override
	protected String getId(District entity) 
	{
		return entity.getId();
	}
	
	
	public District findByCountryIdAndCode(String countryId, String code)
	{
		return districtRepository.findByCountryIdAndCode(countryId, code);
	}
	
	public List<District> findByCountryId(String countryId)
	{
		return districtRepository.findByCountryId(countryId);
	}
	
	public List<District> findByProvinceId(String provinceId)
	{
		return districtRepository.findByProvinceId(provinceId);
	}
	
	public List<District> findByAmphurId(String amphurId)
	{
		return districtRepository.findByAmphurId(amphurId);
	}
}