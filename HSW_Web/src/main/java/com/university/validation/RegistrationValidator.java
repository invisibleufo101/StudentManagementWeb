package com.university.validation;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.university.model.impl.Student;
import com.university.querybuilder.QueryBuilder;

public class RegistrationValidator {

	private List<Student> records;
	private Map<String, String> errorBag = new LinkedHashMap<>();
	
	public RegistrationValidator() {}
		
	public boolean validate(Map<String, String> requestParams) throws NoSuchFieldException {
		getStudentRecords(requestParams);
		
		for (Map.Entry<String, String> entry : requestParams.entrySet()) {
			String field = entry.getKey();
			String value = entry.getValue();
			
			switch(field) {
				case "studentId":
					if (!validateStudentId(value)) return false;
					break;
				
				case "name":
					if (!validateName(value)) return false;
					break;
				
				case "major":
					if (!validateMajor(value)) return false;
					break;
				
				case "phoneNumber":
					if (!validatePhoneNumber(value)) return false;
					break;
				
				default:
					throw new NoSuchFieldException("No such field exists!");
			}
		}
		
		return errorBag.isEmpty();
	}
	
	private boolean validateStudentId(String value) {
		// Not Null Integrity
		if (value == null || value.equals("")) {
			errorBag.put("NullStudentIdException", "학번을 입력해주세요.");
			return false;
		}
		
		// Check for Valid Number Format (10 digits)
		if (!value.matches("\\d{10}")) {
			errorBag.put("InvalidStudentIdException", "학번은 숫자 10자리로 입력해주세요.");
			return false;
		}
		
		try {
			long testNumber = Long.parseLong(value);
		} catch (NumberFormatException nfe) {
			errorBag.put("InvalidStudentIdException", "학번은 숫자 10자리로 입력해주세요");
			return false;
		}
		
		// Unique Constraint
		for (Student record : records) {
			if (record.getField("studentId").equals(value)) {
				errorBag.put("DuplicateStudentIdException", "이미 존재하는 학번입니다. 다시 입력해주세요.");
				return false;
			}
		}
		
		return true;
	}
	
	private boolean validateName(String value) {
		if (value.equals("")) {
			errorBag.put("NullException", "이름을 입력해주세요.");
			return false;
		}
		
		if (value.length() < 2 && value.length() >= 8) {
			errorBag.put("InvalidNameFormatException", "이름은 2글자에서 7글자 사이로 입력해주세요.");
			return false;
		}
		
		String namePattern = "^[가-힣]{2,7}$";
		if (!value.matches(namePattern)) {
			errorBag.put("NonKoreanNameException", "이름은 한글로 2글자에서 7글자 사이로 입력해주세요.");
			return false;
		}
		
		return true;
	}
	
	private boolean validateMajor(String value) {
		if (value.equals("")) {
			errorBag.put("NullException", "학과명을 입력해주세요.");
			return false;
		}
		String majorPattern = "^[가-힣]+학과$";
		if (!value.matches(majorPattern)) {
			errorBag.put("InvalidMajorException", "학과명은 반드시 '학과'로 끝나야합니다. 다시 입력해주세요.");
			return false;
		}
		
		return true;
	}
	
	private boolean validatePhoneNumber(String value) {
		if (value.equals("")) {
			errorBag.put("NullException", "전화번호를 입력해주세요.");
			return false;
		}
		
		String phoneNumberPattern = "010-\\d{4}-\\d{4}";
		if (!value.matches(phoneNumberPattern)) {
			errorBag.put("InvalidPhoneNumberException", "전화번호는 010-XXXX-XXXX 형식으로 입력해주세요.");
			return false;
		}
		
		for (Student record : records) {
			if (record.getField("phoneNumber").equals(value)) {
				errorBag.put("DuplicatePhoneNumberException", "이미 존재하는 전화번호입니다. 다시 입력해주세요.");
				return false;
			}
		}
		
		return true;
	}
	
	private void getStudentRecords(Map<String, String> params){
		String[] columns = params.keySet().stream().toArray(String[]::new);
		this.records = new QueryBuilder(Student.class).select(columns).getAll();
	}
	
	public Map<String, String> getErrorBag(){
		return this.errorBag;
	}
	
	public void clearErrorbag() {
		this.errorBag.clear();
	}
}
