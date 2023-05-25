package com.otc.kws.core.domain.service.education.school;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.constant.value.SchoolOwnerTypeValue;
import com.otc.kws.core.domain.dto.SchoolSearchRequestDto;
import com.otc.kws.core.domain.dto.SchoolSearchRequestDto.Sort.Order;
import com.otc.kws.core.domain.dto.SchoolSearchResponseDto;
import com.otc.kws.core.domain.entity.School;
import com.otc.kws.core.domain.repository.jpa.JpaSchoolRepository;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;

import lombok.Data;

@Component
public class SchoolSearchCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaSchoolRepository schoolRepository;
	
	
	public Response search(Request request)
	{
		Pageable pageable = null;	
		if(request.getSchoolSearchRequestDto().getPaging() == null) {
			pageable = PageRequest.of(0, (int) schoolRepository.count(), Sort.by("name"));
		} else {
			Sort sort = Sort.by("name");
			int pageNo = request.getSchoolSearchRequestDto().getPaging().getPageNo() - 1;
			int pageSize = request.getSchoolSearchRequestDto().getPaging().getPageSize();
			
			if(request.getSchoolSearchRequestDto().getSortBy() != null) {
				int index = 1;
				for(SchoolSearchRequestDto.Sort sortBy : request.getSchoolSearchRequestDto().getSortBy()) {
					if(index == 1) {
						if(sortBy.getOrder() == Order.asc) {
							sort = Sort.by(sortBy.getField()).ascending();
						} else if(sortBy.getOrder() == Order.desc) {
							sort = Sort.by(sortBy.getField()).descending();
						}
					} else {
						if(sortBy.getOrder() == Order.asc) {
							sort = sort.and(Sort.by(sortBy.getField()).ascending());
						} else if(sortBy.getOrder() == Order.desc) {
							sort = sort.and(Sort.by(sortBy.getField()).descending());
						}
					}
					index++;
				}
			}
			pageable = PageRequest.of(pageNo, pageSize, sort);
		}
		
		Page<School> schoolPage = null;
		if(request.getSchoolSearchRequestDto().getCriteria() == null) {
			schoolPage = schoolRepository.findAll(pageable);
		} else {
			String name = request.getSchoolSearchRequestDto().getCriteria().getName();
			List<String> schoolTypeIds = request.getSchoolSearchRequestDto().getCriteria().getSchoolTypeIds();
			List<SchoolOwnerTypeValue> schoolOwnerTypeIds = request.getSchoolSearchRequestDto().getCriteria().getSchoolOwnerTypeIds();
			
			if(name != null && !name.isEmpty() && schoolTypeIds != null && !schoolTypeIds.isEmpty() && schoolOwnerTypeIds != null && !schoolOwnerTypeIds.isEmpty()) {
				
				schoolPage = schoolRepository.findByNameContainsAndSchoolTypeIdInAndSchoolOwnerTypeIdIn(name, schoolTypeIds, schoolOwnerTypeIds, pageable);
				
			} else if((name == null || name.isEmpty()) && (schoolTypeIds == null || schoolTypeIds.isEmpty()) && (schoolOwnerTypeIds == null || schoolOwnerTypeIds.isEmpty())) {
				
				schoolPage = schoolRepository.findAll(pageable);
				
			} else if(name != null && !name.isEmpty() && schoolTypeIds != null && !schoolTypeIds.isEmpty()) {
				
				schoolPage = schoolRepository.findByNameContainsAndSchoolTypeIdIn(name, schoolTypeIds, pageable);
				
			} else if(name != null && !name.isEmpty() && schoolOwnerTypeIds != null && !schoolOwnerTypeIds.isEmpty()) {
				
				schoolPage = schoolRepository.findByNameContainsAndSchoolOwnerTypeIdIn(name, schoolOwnerTypeIds, pageable);
				
			} else if(schoolTypeIds != null && !schoolTypeIds.isEmpty() && schoolOwnerTypeIds != null && !schoolOwnerTypeIds.isEmpty()) {
				
				schoolPage = schoolRepository.findBySchoolTypeIdInAndSchoolOwnerTypeIdIn(schoolTypeIds, schoolOwnerTypeIds, pageable);
				
			} else if(name != null && !name.isEmpty()) {
				
				schoolPage = schoolRepository.findByNameContains(name, pageable);
				
			} else if(schoolTypeIds != null && !schoolTypeIds.isEmpty()) {
				
				schoolPage = schoolRepository.findBySchoolTypeIdIn(schoolTypeIds, pageable);
				
			} else if(schoolOwnerTypeIds != null && !schoolOwnerTypeIds.isEmpty()) {
				
				schoolPage = schoolRepository.findBySchoolOwnerTypeIdIn(schoolOwnerTypeIds, pageable);
				
			} 
		}
		
		SchoolSearchResponseDto schoolSearchResponseDto = new SchoolSearchResponseDto();
		if(schoolPage != null) {
			schoolSearchResponseDto.setPageNo(schoolPage.getNumber() + 1);
			schoolSearchResponseDto.setPageSize(schoolPage.getSize());
			schoolSearchResponseDto.setTotalPage(schoolPage.getTotalPages());
			schoolSearchResponseDto.setTotalSize((int) schoolPage.getTotalElements());
			schoolSearchResponseDto.setDatas(schoolPage.getContent());
		}
		
		Response response = new Response();
		response.setSchoolSearchResponseDto(schoolSearchResponseDto);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected SchoolSearchRequestDto schoolSearchRequestDto;
	}
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected SchoolSearchResponseDto schoolSearchResponseDto;
	}
}