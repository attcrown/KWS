package com.otc.kws.core.domain.repository.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.entity.Amphur;

@Repository
public interface JpaAmphurRepository extends KwsJpaRepository<Amphur, String>
{
	public Amphur findByCountryIdAndCode(String countryId, String code);
	public List<Amphur> findByCountryId(String countryId);
	public List<Amphur> findByProvinceId(String provinceId);
}