package com.otc.kws.fishsix.lib.domain.service.student;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.entity.UserAccount;
import com.otc.kws.core.domain.entity.UserRoleMapper;
import com.otc.kws.core.domain.repository.jpa.JpaUserAccountRepository;
import com.otc.kws.core.domain.repository.jpa.JpaUserRoleMapperRepository;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.fishsix.lib.domain.entity.Student;
import com.otc.kws.fishsix.lib.domain.model.StudentProfile;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudentRepository;

import lombok.Data;

@Component
public class FishsixStudentFetchCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaStudentRepository studentRepository;
	
	@Autowired
	protected JpaUserAccountRepository userAccountRepository;
	
	@Autowired
	protected JpaUserRoleMapperRepository userRoleMapperRepository;
	
	
	public Response fetchStudent(Request request)
	{
		logger.info("### {}.fetchStudent ###", getClass().getSimpleName());
		
		Response response = new Response();
		
		List<StudentProfile> studentProfiles = null;
		
		List<Student> students = studentRepository.findAll();
		
		if(students != null && !students.isEmpty() && request.getCriteria() != null) {
			String firstName = request.getCriteria().getFirstName();
			String lastName = request.getCriteria().getLastName();
			String nickName = request.getCriteria().getNickName();
			List<MasterStatusValue> statuses = request.getCriteria().getStatuses();
			
			Predicate<Student> firstNameFilter = e -> {
				if(firstName != null && !firstName.isEmpty()) {
					return e.getFirstName().contains(firstName);
				}
				return true;
			};
			
			Predicate<Student> lastNameFilter = e -> {
				if(lastName != null && !lastName.isEmpty()) {
					return e.getLastName().contains(lastName);
				}
				return true;
			};
			
			Predicate<Student> nickNameFilter = e -> {
				if(nickName != null && !nickName.isEmpty()) {
					return e.getNickName().contains(nickName);
				}
				return true;
			};
			
			Predicate<Student> statusFilter = e -> {
				if(statuses != null && !statuses.isEmpty()) {
					return statuses.contains(e.getStatus());
				}
				return true;
			};
			
			students = students.stream().
					filter(firstNameFilter).
					filter(lastNameFilter).
					filter(nickNameFilter).
					filter(statusFilter).
					collect(Collectors.toList());
		}
		
		if(students != null && !students.isEmpty()) {
			studentProfiles = new ArrayList<>();
			List<String> userIds = students.stream().map(Student::getUserId).distinct().collect(Collectors.toList());
			List<UserAccount> userAccounts = userAccountRepository.findAllById(userIds);
			List<UserRoleMapper> userRoleMappers = userRoleMapperRepository.findByUserIds(userIds);
			
			for(Student student : students) {
				UserAccount userAccount = userAccounts.
						stream().
						filter(e -> e.getId().equals(student.getUserId())).
						findFirst().
						orElse(null);
				
				List<UserRoleMapper> _userRoleMappers = userRoleMappers.
						stream().
						filter(e -> e.getUserId().equals(student.getUserId())).
						collect(Collectors.toList());
				
				StudentProfile studentProfile = new StudentProfile();
				studentProfile.setStudent(student);
				studentProfile.setUserAccount(userAccount);
				studentProfile.setUserRoleMappers(_userRoleMappers);
				
				studentProfiles.add(studentProfile);
			}
		}
		
		response.setStudentProfiles(studentProfiles);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected Criteria criteria;
		
		
		@Data
		public static class Criteria
		{
			protected String firstName;
			protected String lastName;
			protected String nickName;
			protected List<MasterStatusValue> statuses;
		}
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected List<StudentProfile> studentProfiles;
	}
}