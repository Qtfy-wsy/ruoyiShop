package com.zsTrade.web.other.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zsTrade.common.utils.LogUtils;

@Controller
@RequestMapping("/ErrorHandler")
public class ErrorHandlerController {

	@RequestMapping("404")
	public String to404(HttpServletRequest request){
		LogUtils.logPageError(request);
		return "error/404";
	}
	@RequestMapping("400")
	public String to400(HttpServletRequest request){
		LogUtils.logPageError(request);
		return "error/400";
	}
	@RequestMapping("500")
	public String to500(HttpServletRequest request){
		LogUtils.logPageError(request);
		return "error/500";
	}
	@RequestMapping("error")
	public String errorHandler(HttpServletRequest request){
		LogUtils.logPageError(request);
		return "error/error";
	}
}
