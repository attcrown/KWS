package com.otc.kws.core.domain.service.education.major;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.dto.EducationMajorSearchRequestDto;
import com.otc.kws.core.domain.dto.EducationMajorSearchRequestDto.Sort.Order;
import com.otc.kws.core.domain.dto.EducationMajorSearchResponseDto;
import com.otc.kws.core.domain.entity.EducationMajor;
import com.otc.kws.core.domain.repository.jpa.JpaEducationMajorRepository;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;

import lombok.Data;

@Component
public class EducationMajorSearchCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaEducationMajorRepository educationMajorRepository;
	
	
	public Response search(Request request)
	{
		EducationMajorSearchRequestDto.Criteria criteria = request.getEducationMajorSearchRequestDto().getCriteria();
		EducationMajorSearchRequestDto.Paging paging = request.getEducationMajorSearchRequestDto().getPaging();
		List<EducationMajorSearchRequestDto.Sort> sortByList = request.getEducationMajorSearchRequestDto().getSortBy();
		
		Pageable pageable = null;	
		if(paging == null) {
			pageable = PageRequest.of(0, (int) educationMajorRepository.count(), Sort.by("name"));
		} else {
			Sort sort = Sort.by("name");
			int pageNo = paging.getPageNo() - 1;
			int pageSize = paging.getPageSize();
			
			if(sortByList != null) {
				int index = 1;
				for(EducationMajorSearchRequestDto.Sort sortBy : sortByList) {
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
		
		Page<EducationMajor> ducationFacultyPage = null;
		if(criteria == null) {
			ducationFacultyPage = educationMajorRepository.findAll(pageable);
		} else {
			String name = criteria.getName();
			List<String> educationFacultyIds = criteria.getEducationFacultyIds();
			
			if(name != null && !name.isEmpty() && educationFacultyIds != null && !educationFacultyIds.isEmpty()) {
				ducationFacultyPage = educationMajorRepository.findByNameContainsAndEducationFacultyIdIn(name, educationFacultyIds, pageable);
			} else if(name != null && !name.isEmpty()) {
				ducationFacultyPage = educationMajorRepository.findByNameContains(name, pageable);
			} else if(educationFacultyIds != null && !educationFacultyIds.isEmpty()) {
				ducationFacultyPage = educationMajorRepository.findByEducationFacultyIdIn(educationFacultyIds, pageable);
			} else {
				ducationFacultyPage = educationMajorRepository.findAll(pageable);
			}
		}
		
		EducationMajorSearchResponseDto educationMajorSearchResponseDto = new EducationMajorSearchResponseDto();
		if(ducationFacultyPage != null) {
			educationMajorSearchResponseDto.setPageNo(ducationFacultyPage.getNumber() + 1);
			educationMajorSearchResponseDto.setPageSize(ducationFacultyPage.getSize());
			educationMajorSearchResponseDto.setTotalPage(ducationFacultyPage.getTotalPages());
			educationMajorSearchResponseDto.setTotalSize((int) ducationFacultyPage.getTotalElements());
			educationMajorSearchResponseDto.setDatas(ducationFacultyPage.getContent());
		}
		
		Response response = new Response();
		response.setEducationMajorSearchResponseDto(educationMajorSearchResponseDto);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected EducationMajorSearchRequestDto educationMajorSearchRequestDto;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected EducationMajorSearchResponseDto educationMajorSearchResponseDto;
	}
}