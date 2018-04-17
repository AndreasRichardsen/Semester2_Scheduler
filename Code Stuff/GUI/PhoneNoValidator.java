package gui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNoValidator {
	
	private Pattern pattern;
	private Matcher matcher;
	private static final String PHONE_NO_PATTERN = 
			"(0045|\\+45|45)?\\d{8}";

	public PhoneNoValidator() {
		pattern = Pattern.compile(PHONE_NO_PATTERN);
	}
	
	public boolean validate(final String phoneNo) {

		matcher = pattern.matcher(phoneNo);
		return matcher.matches();

	}

}
