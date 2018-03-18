package cn.fwwb.system.dao.base;

import cn.fwwb.system.model.base.UserDO;

public interface UserMapper {
	/**
	 *@Description: 得到密码
	 * @param account
	 * @return
	 */
	String getPasswordByAccount(String account);
	/**
	 * 
	 *@Description:得到用户数据库对象
	 * @param account
	 * @return
	 */
	UserDO getUserDOByAccount(String account);
}
