package com.otc.kws.core.domain.repository.jpa;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.constant.value.GenderValue;
import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.constant.value.UserAccountGenusValue;
import com.otc.kws.core.domain.entity.UserAccount;
import com.otc.kws.core.domain.model.UserProfile;

@Repository
public interface JpaUserAccountRepository extends KwsJpaRepository<UserAccount, String>
{
	public static final String QUERY_USER_BY_USER_ID = 
			"SELECT ua.id, ua.username, ua.password, ua.email, ua.genus, ua.profile_image_uri, " + 
			"ua.is_account_locked, ua.is_password_locked, ua.activated_date, ua.expired_date, ua.status, " + 
			"upi.gender, upi.prefix_name_id, upi.first_name, upi.last_name, upi.nick_name, " + 
			"GROUP_CONCAT(urm.role_id SEPARATOR ',') AS 'role_ids' " + 
			"FROM user_account ua " + 
			"LEFT JOIN user_role_mapper urm ON ua.id = urm.user_id " + 
			"LEFT JOIN user_personal_info upi ON ua.id = upi.user_id " + 
			"WHERE ua.id = :userId AND " + 
			"urm.status = 'Active' AND " + 
			"urm.activated_at <= CURRENT_TIMESTAMP() AND " + 
			"urm.expired_at >= CURRENT_TIMESTAMP() " + 
			"GROUP BY ua.id";
	
	public static final String QUERY_USER_BY_USERNAME = 
			"SELECT ua.id, ua.username, ua.password, ua.email, ua.genus, ua.profile_image_uri, " + 
			"ua.is_account_locked, ua.is_password_locked, ua.activated_date, ua.expired_date, ua.status, " + 
			"upi.gender, upi.prefix_name_id, upi.first_name, upi.last_name, upi.nick_name, " + 
			"GROUP_CONCAT(urm.role_id SEPARATOR ',') AS 'role_ids' " + 
			"FROM user_account ua " + 
			"LEFT JOIN user_role_mapper urm ON ua.id = urm.user_id " + 
			"LEFT JOIN user_personal_info upi ON ua.id = upi.user_id " + 
			"WHERE ua.username = :username AND " + 
			"urm.status = 'Active' AND " + 
			"urm.activated_at <= CURRENT_TIMESTAMP() AND " + 
			"urm.expired_at >= CURRENT_TIMESTAMP() " + 
			"GROUP BY ua.id";
	
	public UserAccount findByUsername(String username);
	public UserAccount findByEmail(String email);

	
	public default UserProfile.User findUserByUserId(String userId)
	{
		List<Object[]> datas = queryUserByUserId(userId);
		return buildUser(datas);
	}
	
	public default UserProfile.User findUserByUsername(String username)
	{
		List<Object[]> datas = queryUserByUsername(username);
		return buildUser(datas);
	}
	
	
	// ****************************** query ****************************** //
	@Query(value = QUERY_USER_BY_USER_ID, nativeQuery = true)
	public List<Object[]> queryUserByUserId(@Param("userId") String userId);
		
	@Query(value = QUERY_USER_BY_USERNAME, nativeQuery = true)
	public List<Object[]> queryUserByUsername(@Param("username") String username);
	// ****************************** query ****************************** //
	
	public default UserProfile.User buildUser(List<Object[]> datas)
	{
		UserProfile.User user = null;
		
		if(datas != null && !datas.isEmpty()) {
			user = new UserProfile.User();
			
			user.setId((String) datas.get(0)[0]);
			user.setUsername((String) datas.get(0)[1]);
			user.setPassword((String) datas.get(0)[2]);
			user.setEmail((String) datas.get(0)[3]);
			if(datas.get(0)[4] != null) {
				user.setGenus(UserAccountGenusValue.valueOf((String) datas.get(0)[4]));
			}
			user.setProfileImageURI((String) datas.get(0)[5]);
			user.setAccountLocked((Boolean) datas.get(0)[6]);
			user.setPasswordLocked((Boolean) datas.get(0)[7]);
			user.setActivatedDate((Date) datas.get(0)[8]);
			user.setExpiredDate((Date) datas.get(0)[9]);
			if(datas.get(0)[10] != null) {
				user.setStatus(MasterStatusValue.valueOf((String) datas.get(0)[10]));
			}
			
			if(datas.get(0)[11] != null) {
				user.setGender(GenderValue.valueOf((String) datas.get(0)[11]));
			}
			user.setPrefixNameId((String) datas.get(0)[12]);
			user.setFirstName((String) datas.get(0)[13]);
			user.setLastName((String) datas.get(0)[14]);
			user.setNickName((String) datas.get(0)[15]);
			
			if(datas.get(0)[16] != null) {
				user.setRoleIds(Arrays.asList(((String) datas.get(0)[16]).split(",")));
			}
		}
		
		return user;
	}
}