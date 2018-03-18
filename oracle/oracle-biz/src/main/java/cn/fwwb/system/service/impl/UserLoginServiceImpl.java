package cn.fwwb.system.service.impl;

import java.util.List;

import org.apache.ibatis.jdbc.SqlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.fwwb.system.dao.base.PermissionMapper;
import cn.fwwb.system.dao.base.RoleMapper;
import cn.fwwb.system.dao.base.RolePermissionMapper;
import cn.fwwb.system.dao.base.UserMapper;
import cn.fwwb.system.dao.base.UserRoleMapper;
import cn.fwwb.system.model.base.PermissionDO;
import cn.fwwb.system.model.base.UserDO;
import cn.fwwb.system.service.UserLoginService;

@Service
public class UserLoginServiceImpl implements UserLoginService {
	@Autowired
	UserMapper userMapper;
	@Autowired
	UserRoleMapper userRoleMapper;
	@Autowired
	RolePermissionMapper rolePermissionMapper;
	@Autowired
	PermissionMapper permissionMapper;
	@Autowired
	RoleMapper roleMapper;

	@Override
	public String getPasswordByAccount(String account) {
		if (account == null) {
			return null;
		}
		String password = userMapper.getPasswordByAccount(account);
		return password;
	}

	// 得到放入session中的user对象
	@Override
	public UserDO getUserDOToSessionByAccount(String account) {
		if (account == null) {
			return null;
		}
		return userMapper.getUserDOByAccount(account);
	}

	@Override
	public Integer getRoleIdByUserId(int id) {
		Integer roleid = userRoleMapper.GetRoleIdByUserId(id);
		return roleid;
	}

	@Override
	public List<PermissionDO> listPermissionByRoleId(int roleId) {
		List<PermissionDO> list = permissionMapper.listPermissionByRole(roleId);
		return list;
	}

	@Override
	// @Transactional(isolation = Isolation.REPEATABLE_READ, propagation =
	// Propagation.REQUIRED, readOnly = false)
	public String getRoleByUserId(int userId) {
		int roleId = userRoleMapper.GetRoleIdByUserId(userId);
		String role = roleMapper.getRoleByRoleId(roleId);
		return role;
	}

}
