package com.mz.common.controller;

import com.mz.common.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统日志
 */
@RestController
@RequestMapping("/sys/log")
public class SysLogController extends AbstractController {

	@Autowired
	private SysLogService sysLogService;

}
