package com.otc.kws.firstjobber.lib.domain.service.employer;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.firstjobber.lib.domain.dto.FjbEmployerSearchRequestDto;
import com.otc.kws.firstjobber.lib.domain.dto.FjbEmployerSearchResponseDto;
import com.otc.kws.firstjobber.lib.domain.dto.FjbEmployerSearchRequestDto.Sort.Order;
import com.otc.kws.firstjobber.lib.domain.entity.FjbEmployer;

@Component
public class FjbEmployerSearchCommandV1 extends FjbEmployerSearchCommand
{
	@Override
	public Response searchEmployer(Request request) 
	{
		Page<FjbEmployer> employerPage = search(request);
		
		FjbEmployerSearchResponseDto responseDto = new FjbEmployerSearchResponseDto();
		if(employerPage != null) {
			responseDto.setPageNo(employerPage.getNumber() + 1);
			responseDto.setPageSize(employerPage.getSize());
			responseDto.setTotalPage(employerPage.getTotalPages());
			responseDto.setTotalSize((int) employerPage.getTotalElements());
			responseDto.setDatas(employerPage.getContent());
		}
		
		Response response = new Response();
		response.setEmployerSearchResponseDto(responseDto);
		
		return response;
	}
	
	
	protected Pageable buildPageable(Request request)
	{
		Pageable pageable = null;
		
		FjbEmployerSearchRequestDto requestDto = request.getEmployerSearchRequestDto();
		
		if(requestDto.getPaging() == null) {
			pageable = PageRequest.of(0, (int) fjbEmployerService.count(), Sort.by("name"));
		} else {
			Sort sort = Sort.by("name");
			int pageNo = requestDto.getPaging().getPageNo() - 1;
			int pageSize = requestDto.getPaging().getPageSize();
			
			if(requestDto.getSortBy() != null) {
				int index = 1;
				for(FjbEmployerSearchRequestDto.Sort sortBy : requestDto.getSortBy()) {
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
		
		return pageable;
	}
	
	
	protected Page<FjbEmployer> search(Request request)
	{
		Pageable pageable = buildPageable(request);
		Page<FjbEmployer> employerPage = null;
		FjbEmployerSearchRequestDto.Criteria criteria = request.getEmployerSearchRequestDto().getCriteria();
		
		if(criteria == null) {
			employerPage = fjbEmployerService.findAll(pageable);
		} else {
			
			List<String> employerTypeIds = criteria.getEmployerTypeIds();
			String name = criteria.getName();
			List<MasterStatusValue> statuses = criteria.getStatuses();
			
			if(employerTypeIds != null && !employerTypeIds.isEmpty() && name != null && !name.isEmpty() && statuses != null && !statuses.isEmpty()) {
				
				employerPage = fjbEmployerService.getRepository().findByEmployerTypeIdInAndNameContainsAndStatusIn(employerTypeIds, name, statuses, pageable);
				
			} else if((employerTypeIds == null || employerTypeIds.isEmpty()) && (name == null || name.isEmpty()) && (statuses == null || statuses.isEmpty())) {
				
				employerPage = fjbEmployerService.findAll(pageable);
				
			} else if(employerTypeIds != null && !employerTypeIds.isEmpty() && name != null && !name.isEmpty()) {
				
				employerPage = fjbEmployerService.getRepository().findByEmployerTypeIdInAndNameContains(employerTypeIds, name, pageable);
				
			} else if(employerTypeIds != null && !employerTypeIds.isEmpty() && statuses != null && !statuses.isEmpty()) {
				
				employerPage = fjbEmployerService.getRepository().findByEmployerTypeIdInAndStatusIn(employerTypeIds, statuses, pageable);
				
			} else if(name != null && !name.isEmpty() && statuses != null && !statuses.isEmpty()) {
				
				employerPage = fjbEmployerService.getRepository().findByNameContainsAndStatusIn(name, statuses, pageable);
				
			} else if(employerTypeIds != null && !employerTypeIds.isEmpty()) {
				
				employerPage = fjbEmployerService.getRepository().findByEmployerTypeIdIn(employerTypeIds, pageable);
				
			} else if(name != null && !name.isEmpty()) {
				
				employerPage = fjbEmployerService.getRepository().findByNameContains(name, pageable);
				
			} else if(statuses != null && !statuses.isEmpty()) {
				
				employerPage = fjbEmployerService.getRepository().findByStatusIn(statuses, pageable);
				
			} 
			
		}
		
		return employerPage;
	}
}