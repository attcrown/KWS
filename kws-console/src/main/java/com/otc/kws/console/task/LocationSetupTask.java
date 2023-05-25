package com.otc.kws.console.task;

import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.entity.Amphur;
import com.otc.kws.core.domain.entity.District;
import com.otc.kws.core.domain.entity.Province;
import com.otc.kws.core.domain.repository.jpa.JpaAmphurRepository;
import com.otc.kws.core.domain.repository.jpa.JpaDistrictRepository;
import com.otc.kws.core.domain.repository.jpa.JpaProvinceRepository;

//@Component
public class LocationSetupTask 
{
	@Autowired
	protected JpaProvinceRepository provinceRepository;
	
	@Autowired
	protected JpaAmphurRepository amphurRepository;
	
	@Autowired
	protected JpaDistrictRepository districtRepository;
	
	
	@PostConstruct
	public void setup()
	{
		System.out.println("### LocationSetupTask.setup ###");
		
		List<Province> provinces = provinceRepository.findAll();
		List<Amphur> amphurs = amphurRepository.findAll();
		List<District> districts = districtRepository.findAll();
		
		// 1. set all district.id to uuid
		for(District district : districts) {
			districtRepository.deleteById(district.getId());
			district.setId(UUID.randomUUID().toString());
		}
		
		// 2. loop each amphur
		for(Amphur amphur : amphurs) {
			String oldAmphurId = amphur.getId();
			String newAmphutId = UUID.randomUUID().toString();
			
			amphurRepository.deleteById(oldAmphurId);
			
			// 2.1 set current amphur.id to uuid
			amphur.setId(newAmphutId);
			
			for(District district : districts) {
				if(district.getAmphurId().equals(oldAmphurId)) {
					// 2.2 set all district.amphur_id (district.amphur_id = old_amphur_id) to new_amphur_id
					district.setAmphurId(newAmphutId);
				}
			}
		}
		
		// 3. loop each province
		for(Province province : provinces) {
			String oldProvinceId = province.getId();
			String newProvinceId = UUID.randomUUID().toString();
			
			provinceRepository.deleteById(oldProvinceId);
			
			// 3.1 set current province.id to uuid
			province.setId(newProvinceId);
			
			// 3.2 set all amphur.province_id (amphur.province_id = old_province_id) to new_province_id
			for(Amphur amphur : amphurs) {
				if(amphur.getProvinceId().equals(oldProvinceId)) {
					amphur.setProvinceId(newProvinceId);
					
					// 3.3 set all district.province_id (district.amphur_id = current_amphur_id) to amphur.province_id
					for(District district : districts) {
						if(district.getAmphurId().equals(amphur.getId())) {
							district.setProvinceId(newProvinceId);
						}
					}
				}
			}
		}
		
		/*
		System.out.println("****************************** Provinces ******************************");
		provinces.forEach(System.out::println);
		System.out.println();
		
		System.out.println("****************************** Amphurs ******************************");
		amphurs.forEach(System.out::println);
		System.out.println();
		
		System.out.println("****************************** Districts ******************************");
		districts.forEach(System.out::println);
		System.out.println();
		*/
		
		/*
		// 4. saveAll province
		provinceRepository.saveAll(provinces);
		
		// 5. saveAll amphur
		amphurRepository.saveAll(amphurs);
		
		// 6. saveAll district
		districtRepository.saveAll(districts);
		*/
	}
}