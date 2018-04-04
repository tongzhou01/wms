package com.mz.common.aspect;

import com.alibaba.fastjson.JSON;
import com.mz.common.annotation.SysLog;
import com.mz.common.dao.SysLogDao;
import com.mz.common.entity.SysLogEntity;
import com.mz.common.util.CommonUtil;
import com.mz.common.util.HttpContextUtils;
import com.mz.common.util.IpUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;


/**
 * 系统日志，切面处理类
 */
@Aspect
@Component
public class SysLogAspect {
	
	@Autowired
	private SysLogDao sysLogDao;
	
	@Pointcut("@annotation(com.mz.common.annotation.SysLog)")
	public void logPointCut() { 
		
	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		//执行方法
		Object result = point.proceed();
		//执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;
		//保存日志
		saveSysLog(point, time);
		return result;
	}

	private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		SysLogEntity sysLog = new SysLogEntity();
		SysLog syslog = method.getAnnotation(SysLog.class);
		if(syslog != null){
			//注解上的描述
			sysLog.setOperation(syslog.value());
		}
		//请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		sysLog.setMethod(className + "." + methodName + "()");
		//请求的参数
		Object[] args = joinPoint.getArgs();
		try{
			String params = JSON.toJSONString(args[0]);
			sysLog.setParams(params);
		}catch (Exception e){

		}
		//获取request
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		//设置IP地址
		sysLog.setIp(IpUtil.getIpAddr(request));
		//用户名
		//SysUserEntity currUser = ShiroUtils.getUserEntity();
		if(CommonUtil.isNullOrEmpty("")) {
			if(CommonUtil.isNullOrEmpty(sysLog.getParams())) {
				sysLog.setUserId(-1L);
				sysLog.setUsername(sysLog.getParams());
			} else {
				sysLog.setUserId(-1L);
				sysLog.setUsername("获取用户信息为空");
			}
		} else {
			/*sysLog.setUserId(ShiroUtils.getUserId());
			sysLog.setUsername(ShiroUtils.getUserEntity().getUsername());*/
		}
		sysLog.setTime(time);
		//保存系统日志
		sysLogDao.insertSelective(sysLog);
	}
	
}
