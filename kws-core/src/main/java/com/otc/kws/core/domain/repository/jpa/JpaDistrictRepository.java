package com.otc.kws.core.domain.repository.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.entity.District;

@Repository
public interface JpaDistrictRepository extends KwsJpaRepository<District, String>
{
	public District findByCountryIdAndCode(String countryId, String code);
	public List<District> findByCountryId(String countryId);
	public List<District> findByProvinceId(String provinceId);
	public List<District> findByAmphurId(String amphurId);
}