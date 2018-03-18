package cn.fwwb.system.service;

import java.util.List;

import cn.fwwb.system.model.base.PermissionDO;
import cn.fwwb.system.model.base.UserDO;

public interface UserLoginService {

	String getPasswordByAccount(String account);

	// 得到放入session中的user对象
	UserDO getUserDOToSessionByAccount(String account);

	Integer getRoleIdByUserId(int id);

	List<PermissionDO> listPermissionByRoleId(int roleId);

	String getRoleByUserId(int userId);
}