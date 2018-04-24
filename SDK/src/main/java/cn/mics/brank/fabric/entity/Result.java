package cn.mics.brank.fabric.entity;

import java.util.List;

public class Result {

		private String result;
		private String message;
		private Object content;
		private List<Error> errors;

		public Result() {
			this.setResult("");
		}
		
		public static Result constructPUREDTO(Object object) {
			Result result = new Result();
			result.setContent(object);
			return result;
		}
		
		public static Result constructNullInputDTO() {
			Result result = new Result();
			result.setResult(ResultConstants.STATE_FAIL);
			return result;
		}

		public static Result constructFailDTO() {
			Result result = new Result();
			result.setResult(ResultConstants.STATE_FAIL);
			return result;
		}
		
		public static Result constructFailDTO(String message) {
			Result result = new Result();
			result.setResult(ResultConstants.STATE_FAIL);
			result.setMessage(message);
			return result;
		}
		
		public static Result constructFailDTO(List<Error> errors) {
			Result result = new Result();
			result.setResult(ResultConstants.STATE_FAIL);
			result.setErrors(errors);
			return result;
		}
		
		public static Result constructFailDTO(Object content, String message, List<Error> errors) {
			Result result = new Result();
			result.setResult(ResultConstants.STATE_FAIL);
			result.setMessage(message);
			result.setContent(content);
			result.setErrors(errors);
			return result;
		}
		
		public static Result constructSuccessDTO(String message) {
			Result result = new Result();
			result.setResult(ResultConstants.STATE_SUCCESS);
			result.setMessage(message);
			return result;
		}
		
		public static Result constructSuccessDTO(Object object) {
			Result result = new Result();
			result.setResult(ResultConstants.STATE_SUCCESS);
			result.setContent(object);
			return result;
		}
		
		public static Result constructSuccessDTO(Object content, String message) {
			Result result = new Result();
			result.setResult(ResultConstants.STATE_SUCCESS);
			result.setContent(content);
			result.setMessage(message);
			return result;
		}
		
		public static Result constructSuccessDTO(Object content, String message, List<Error> errors) {
			Result result = new Result();
			result.setResult(ResultConstants.STATE_SUCCESS);
			result.setContent(content);
			result.setMessage(message);
			result.setErrors(errors);
			return result;
		}

		public String getResult() {
			return result;
		}

		public void setResult(String result) {
			this.result = result;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
		
		public Object getContent() {
			return content;
		}

		public void setContent(Object content) {
			this.content = content;
		}
		
		public List<Error> getErrors() {
			return errors;
		}

		public void setErrors(List<Error> errors) {
			this.errors = errors;
		}
	}


