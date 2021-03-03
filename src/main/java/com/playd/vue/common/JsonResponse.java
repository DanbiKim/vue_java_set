package com.playd.vue.common;

import java.util.List;

/**
 * @author 장재용
 */
public class JsonResponse {
	private static final String DEFAULT_FAIL_MESSAGE = "시스템 오류가 발생하였습니다.\n문제가 계속될 경우 관리자에게 문의해주세요.";
	private boolean success = false;
	private String message = "";
	private Object data = null;
	private String code = "";
	
	public JsonResponse() {
	}

	/**
	 * 성공여부
	 * 성공여부가 false이고 message를 입력하지 않으면 기본 에러메시지가 들어간다.
	 * @param success 성공여부
	 */
	public JsonResponse(boolean success) {
		this.success = success;
		this.message=getMessage();
		if(success) {
			this.code="200";
		}
	}

	/**
	 * 성공여부와 데이터
	 * @param success 성공여부
	 * @param data 데이터
	 */
	public JsonResponse(boolean success, Object data) {
		this.success = success;
		this.data = data;
		this.message=getMessage();
		if(success) {
			this.code="200";
		}
	}

	public boolean isSuccess() {
		return success;
	}

	public JsonResponse setSuccess(boolean success) {
		this.success = success;

		return this;
	}

	public String getMessage() {
		if (!this.success && "".equals(message)) {
			this.message = DEFAULT_FAIL_MESSAGE;
		}
		return message;
	}
	
	public JsonResponse setCode(String code) {
		this.code = code;

		return this;
	}
	
	public String getCode() {
		if (!this.success && "".equals(code)) {
			this.code="500";
		}
		return code;
	}

	/**
	 * 메시지를 입력하지 않고 success가 false인경우 기본 에러메시지가 들어간다.
	 * @param message
	 * @return
	 */
	public JsonResponse setMessage(String message) {
		this.message = message;

		return this;
	}

	public Object getData() {
		return data;
	}

	public JsonResponse setData(Object data) {
		this.data = data;
		return this;
	}

	@Override
	public String toString() {
		if (data == null) {
			return "{success=" + success + ",code="+code+", message=" + message + ", data=null}";
		} else {
			return "{success=" + success + ",code="+code+", message=" + message + ", data=" + data.getClass().getCanonicalName() + "}";
		}
	}
}
