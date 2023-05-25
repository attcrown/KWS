package com.otc.kws.core.domain.repository.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.entity.Province;

@Repository
public interface JpaProvinceRepository extends KwsJpaRepository<Province, String>
{
	public Province findByCountryIdAndCode(String countryId, String code);
	public List<Province> findByCountryId(String countryId);
}