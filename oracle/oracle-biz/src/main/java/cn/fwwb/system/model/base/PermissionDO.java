package cn.fwwb.system.model.base;

import java.io.Serializable;
/**
 * @author 16224
 * @Description: 权限实体类
 * @date 2018年1月8日 下午4:07:50
 */
public class PermissionDO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String remark;
	private String enabled;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "PermissionDO [id=" + id + ", remark=" + remark + ", enabled=" + enabled + "]";
	}

}
