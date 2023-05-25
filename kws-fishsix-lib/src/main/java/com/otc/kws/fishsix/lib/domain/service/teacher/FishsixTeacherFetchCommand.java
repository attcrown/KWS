package com.otc.kws.fishsix.lib.domain.service.teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.entity.Role;
import com.otc.kws.core.domain.entity.UserAccount;
import com.otc.kws.core.domain.entity.UserRoleMapper;
import com.otc.kws.core.domain.repository.jpa.JpaRoleRepository;
import com.otc.kws.core.domain.repository.jpa.JpaUserAccountRepository;
import com.otc.kws.core.domain.repository.jpa.JpaUserRoleMapperRepository;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.core.domain.service.filestore.FileStoreService;
import com.otc.kws.fishsix.lib.domain.entity.Teacher;
import com.otc.kws.fishsix.lib.domain.entity.TeacherSubjectMapper;
import com.otc.kws.fishsix.lib.domain.model.TeacherProfile;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaTeacherRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaTeacherSubjectMapperRepository;

import lombok.Data;

@Component
public class FishsixTeacherFetchCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaTeacherRepository teacherRepository;
	
	@Autowired
	protected JpaTeacherSubjectMapperRepository teacherSubjectMapperRepository;
	
	@Autowired
	protected JpaUserAccountRepository userAccountRepository;
	
	@Autowired
	protected JpaUserRoleMapperRepository userRoleMapperRepository;
	
	@Autowired
	protected JpaRoleRepository roleRepository;
	
	@Autowired
	protected FileStoreService fileStoreService;
	
	
	public Response fetchTeacher(Request request)
	{
		logger.info("### {}.fetchTeacher ###", getClass().getSimpleName());
		
		List<Teacher> teachers = teacherRepository.findAll();
		
		if(teachers != null && !teachers.isEmpty() && request.getCriteria() != null) {
			String firstName = request.getCriteria().getFirstName();
			String lastName = request.getCriteria().getLastName();
			String nickName = request.getCriteria().getNickName();
			List<String> hireTypeIds = request.getCriteria().getHireTypeIds();
			List<String> worktimeTypeIds = request.getCriteria().getWorktimeTypeIds();
			List<MasterStatusValue> statuses = request.getCriteria().getStatuses();
			
			Predicate<Teacher> firstNameFilter = e -> {
				if(firstName != null && !firstName.isEmpty()) {
					return e.getFirstName().contains(firstName);
				}
				return true;
			};
			
			Predicate<Teacher> lastNameFilter = e -> {
				if(lastName != null && !lastName.isEmpty()) {
					return e.getLastName().contains(lastName);
				}
				return true;
			};
			
			Predicate<Teacher> nickNameFilter = e -> {
				if(nickName != null && !nickName.isEmpty()) {
					return e.getNickName().contains(nickName);
				}
				return true;
			};
			
			Predicate<Teacher> hireTypeFilter = e -> {
				if(hireTypeIds != null && !hireTypeIds.isEmpty()) {
					return hireTypeIds.contains(e.getHireTypeId());
				}
				return true;
			};
			
			Predicate<Teacher> worktimeTypeFilter = e -> {
				if(worktimeTypeIds != null && !worktimeTypeIds.isEmpty()) {
					return worktimeTypeIds.contains(e.getWorktimeTypeId());
				}
				return true;
			};
			
			Predicate<Teacher> statusFilter = e -> {
				if(statuses != null && !statuses.isEmpty()) {
					return statuses.contains(e.getStatus());
				}
				return true;
			};
			
			teachers = teachers.stream().
					filter(firstNameFilter).
					filter(lastNameFilter).
					filter(nickNameFilter).
					filter(hireTypeFilter).
					filter(worktimeTypeFilter).
					filter(statusFilter).
					collect(Collectors.toList());
		}
		
		List<TeacherProfile> teacherProfiles = null;
		
		if(teachers != null && !teachers.isEmpty()) {
			List<String> teacherIds = teachers.stream().map(Teacher::getId).distinct().collect(Collectors.toList());
			List<TeacherSubjectMapper> teacherSubjectMappers = teacherSubjectMapperRepository.findByTeacherIds(teacherIds);
			List<String> userIds = teachers.stream().map(Teacher::getUserId).distinct().collect(Collectors.toList());
			List<UserAccount> userAccounts = userAccountRepository.findAllById(userIds);
			List<UserRoleMapper> userRoleMappers = userRoleMapperRepository.findByUserIds(userIds);
			List<String> roleIds = userRoleMappers.stream().map(UserRoleMapper::getRoleId).distinct().collect(Collectors.toList());
			List<Role> roles = roleRepository.findAllById(roleIds);
			
			teacherProfiles = new ArrayList<>();
			
			for(Teacher teacher : teachers) {
				List<TeacherSubjectMapper> _teacherSubjectMappers = teacherSubjectMappers.
						stream().
						filter(e -> e.getTeacherId().equals(teacher.getId())).
						collect(Collectors.toList());
				
				UserAccount userAccount = userAccounts.
						stream().
						filter(e -> e.getId().equals(teacher.getUserId())).
						findFirst().
						orElse(null);
				
				List<UserRoleMapper> _userRoleMappers = userRoleMappers.
						stream().
						filter(e -> e.getUserId().equals(teacher.getUserId())).
						collect(Collectors.toList());
				
				List<Role> _roles = null;
				
				if(_userRoleMappers != null && !_userRoleMappers.isEmpty()) {
					List<String> _roleIds = _userRoleMappers.
							stream().
							map(UserRoleMapper::getRoleId).
							distinct().
							collect(Collectors.toList());
					
					_roles = roles.
							stream().
							filter(e -> _roleIds.contains(e.getId())).
							collect(Collectors.toList());
				}
				
				TeacherProfile teacherProfile = new TeacherProfile();
				teacherProfile.setTeacher(teacher);
				teacherProfile.setTeacherSubjectMappers(_teacherSubjectMappers);
				teacherProfile.setUserAccount(userAccount);
				teacherProfile.setUserRoleMappers(_userRoleMappers);
				teacherProfile.setRoles(_roles);
				
				if(teacher.getIdcardFileURI() != null) {
					String fileURL = fileStoreService.getURL(req -> {
						req.copyFrom(request);
						req.setFileURI(teacher.getIdcardFileURI());
					}).getUrl();
					
					if(fileURL != null) {
						TeacherProfile.Attachment attachment = new TeacherProfile.Attachment();
						attachment.setIdCardFileURL(fileURL);
						teacherProfile.setAttachment(attachment);
					}
				}
				
				teacherProfiles.add(teacherProfile);
			}
		}
		
		Response response = new Response();
		response.setTeacherProfiles(teacherProfiles);
		
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
			protected List<String> hireTypeIds;
			protected List<String> worktimeTypeIds;
			protected List<MasterStatusValue> statuses;
		}
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected List<TeacherProfile> teacherProfiles;
	}
}