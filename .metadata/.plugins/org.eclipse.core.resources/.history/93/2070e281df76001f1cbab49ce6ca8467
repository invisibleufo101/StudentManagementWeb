package com.university.validation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.university.model.impl.Student;
import com.university.querybuilder.QueryBuilder;

public class UpdateValidator {
	
	private Map<String, Object> params;
	private List<Student> records;
	private Map<String, String> errorBag = new LinkedHashMap<>();
	
	public UpdateValidator() {}
	
	public boolean validate(Map<String, Object> updateParams) {
		this.params = updateParams;
		
		getStudentRecords(updateParams);
		
		Long id = (Long) updateParams.get("id");
		String updatedMajor = (String) updateParams.get("major");
		String updatedPhoneNumber = (String) updateParams.get("phoneNumber");
		
		if (!validateUpdatedMajor(updatedMajor)) {
			return false;
		}
		
		if (!validateUpdatedPhoneNumber(updatedPhoneNumber)) {
			return false;
		}
		
		return errorBag.isEmpty();
	}
	
	private boolean validateUpdatedMajor(String value) {
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
	
	private boolean validateUpdatedPhoneNumber(String value) {
		if (value.equals("")) {
			errorBag.put("NullException", "전화번호를 입력해주세요.");
			return false;
		}
		
		String phoneNumberPattern = "010-\\d{4}-\\d{4}";
		if (!value.matches(phoneNumberPattern)) {
			errorBag.put("InvalidPhoneNumberException", "전화번호는 010-XXXX-XXXX 형식으로 입력해주세요.");
			return false;
		}
		
		// If the phone number is duplicate with a record in the database and it's not the updated student
		Long updateRowId = (Long) this.params.get("id");
		for (Student record : records) {
			if (record.getField("phoneNumber").equals(value) && record.getField("id") != updateRowId) {
				errorBag.put("DuplicatePhoneNumberException", "이미 존재하는 전화번호입니다. 다시 입력해주세요.");
				return false;
			}
		}
		
		return true;
	}
	
	private void getStudentRecords(Map<String, Object> params){
		this.records = new QueryBuilder(Student.class).select("id", "phoneNumber").getAll();
	}
	
	public Map<String, String> getErrorBag(){
		return errorBag;
	}
}
