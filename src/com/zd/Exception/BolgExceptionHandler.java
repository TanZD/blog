//package com.zd.Exception;
//
//import java.io.IOException;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.TypeMismatchException;
//import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.web.HttpRequestMethodNotSupportedException;
//import org.springframework.web.bind.MissingServletRequestParameterException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.zd.DTO.JSONResult;
//import com.zd.Util.Msg;
//
//import net.sf.json.JSONObject;
//
//@ControllerAdvice
//public class BolgExceptionHandler {
//
//	@ExceptionHandler(RuntimeException.class)
//	@ResponseBody
//	public String runtimeException(RuntimeException runtimeException, HttpServletResponse response) {
//		System.out.println(runtimeException.getMessage());
//		response.setCharacterEncoding("utf-8");
//		JSONResult<Object> result = new JSONResult<>(Msg.Others, Msg.err_code, Msg.RunTimeException);
//		return JSONObject.fromObject(result).toString();
//	}
//
//	@ExceptionHandler(NullPointerException.class)
//	@ResponseBody
//	public String nullPointException(NullPointerException e, HttpServletResponse response) {
//		System.out.println(e.getMessage());
//		response.setCharacterEncoding("utf-8");
//		JSONResult<Object> result = new JSONResult<>(Msg.Others, Msg.err_code, Msg.NullPointException);
//		return JSONObject.fromObject(result).toString();
//	}
//
//	@ExceptionHandler({ HttpMessageNotReadableException.class })
//	@ResponseBody
//	public String requestNotReadable(TypeMismatchException e, HttpServletResponse response) {
//		e.printStackTrace();
//		System.out.println(e.getMessage());
//		response.setCharacterEncoding("utf-8");
//		JSONResult<Object> result = new JSONResult<>(Msg.NotReadable, Msg.err_code, Msg.NotReadableException);
//		return JSONObject.fromObject(result).toString();
//	}
//
//	@ExceptionHandler({ TypeMismatchException.class })
//	@ResponseBody
//	public String requestTypeMismatch(TypeMismatchException e, HttpServletResponse response) {
//		System.out.println(e.getMessage());
//		response.setCharacterEncoding("utf-8");
//		JSONResult<Object> result = new JSONResult<>(Msg.NotReadable, Msg.err_code, Msg.TypeMediaException);
//		return JSONObject.fromObject(result).toString();
//	}
//
//	@ExceptionHandler({ MissingServletRequestParameterException.class })
//	@ResponseBody
//	public String missingRequest(MissingServletRequestParameterException e, HttpServletResponse response) {
//		System.out.println(e.getMessage());
//		response.setCharacterEncoding("utf-8");
//		JSONResult<Object> result = new JSONResult<>(Msg.NotReadable, Msg.err_code, Msg.MissingRquest);
//		return JSONObject.fromObject(result).toString();
//	}
//
//	@ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
//	@ResponseBody
//	public String requestMethodNotSupport(HttpRequestMethodNotSupportedException e, HttpServletResponse response) {
//		System.out.println(e.getMessage());
//		response.setCharacterEncoding("utf-8");
//		JSONResult<Object> result = new JSONResult<>(Msg.MediaType, Msg.err_code, Msg.RequestMethodNotSupport);
//		System.out.println(result.toString());
//		return JSONObject.fromObject(result).toString();
//	}
//
//	@ExceptionHandler({ Exception.class })
//	@ResponseBody
//	public String OthrtsException(Exception e, HttpServletRequest request, HttpServletResponse response) {
//		System.out.println(e.getMessage());
//		response.setContentType("text/json;charset=utf-8");
//		JSONResult<Object> result = new JSONResult<>(Msg.Others, Msg.err_code, Msg.RunTimeException);
//		System.out.println(result.toString());
//		return JSONObject.fromObject(result).toString();
//	}
//
//}
