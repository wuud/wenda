package cn.wenda.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 得到application.properties文件内自定义的常量
 * @author wuu
 * 2019年2月15日
 */
@Component
@ConfigurationProperties(prefix="wenda")
public class Constants {
	//分页大小，包括首页分页，用户首页分页
	public int pageSize;
	//系统账号id
	public int systemId;
	
	public String hostName;

	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setSystemId(int systemId) {
		this.systemId = systemId;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	
	

	
	
	
	
	

	
	

}
