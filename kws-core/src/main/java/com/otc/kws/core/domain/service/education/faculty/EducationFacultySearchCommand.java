package com.otc.kws.core.domain.service.education.faculty;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.dto.EducationFacultySearchRequestDto;
import com.otc.kws.core.domain.dto.EducationFacultySearchRequestDto.Sort.Order;
import com.otc.kws.core.domain.entity.EducationFaculty;
import com.otc.kws.core.domain.dto.EducationFacultySearchResponseDto;
import com.otc.kws.core.domain.repository.jpa.JpaEducationFacultyRepository;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;

import lombok.Data;

@Component
public class EducationFacultySearchCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaEducationFacultyRepository educationFacultyRepository;
	
	
	public Response search(Request request)
	{
		EducationFacultySearchRequestDto.Criteria criteria = request.getEducationFacultySearchRequestDto().getCriteria();
		EducationFacultySearchRequestDto.Paging paging = request.getEducationFacultySearchRequestDto().getPaging();
		List<EducationFacultySearchRequestDto.Sort> sortByList = request.getEducationFacultySearchRequestDto().getSortBy();
		
		Pageable pageable = null;
		if(paging == null) {
			pageable = PageRequest.of(0, (int) educationFacultyRepository.count(), Sort.by("name"));
		} else {
			Sort sort = Sort.by("name");
			int pageNo = paging.getPageNo() - 1;
			int pageSize = paging.getPageSize();
			
			if(sortByList != null) {
				int index = 1;
				for(EducationFacultySearchRequestDto.Sort sortBy : sortByList) {
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
		
		Page<EducationFaculty> educationFacultyPage = null;
		if(criteria == null) {
			educationFacultyPage = educationFacultyRepository.findAll(pageable);
		} else {
			educationFacultyPage = educationFacultyRepository.findByNameContains(criteria.getName(), pageable);
		}
		
		EducationFacultySearchResponseDto educationFacultySearchResponseDto = new EducationFacultySearchResponseDto();
		if(educationFacultyPage != null) {
			educationFacultySearchResponseDto.setPageNo(educationFacultyPage.getNumber() + 1);
			educationFacultySearchResponseDto.setPageSize(educationFacultyPage.getSize());
			educationFacultySearchResponseDto.setTotalPage(educationFacultyPage.getTotalPages());
			educationFacultySearchResponseDto.setTotalSize((int) educationFacultyPage.getTotalElements());
			educationFacultySearchResponseDto.setDatas(educationFacultyPage.getContent());
		}
		
		Response response = new Response();
		response.setEducationFacultySearchResponseDto(educationFacultySearchResponseDto);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected EducationFacultySearchRequestDto educationFacultySearchRequestDto;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected EducationFacultySearchResponseDto educationFacultySearchResponseDto;
	}
}