package gui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {
	
	private Pattern pattern;
	private Matcher matcher;
	private static final String EMAIL_PATTERN = 
			"^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

	public EmailValidator() {
		pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
	}
	
	public boolean validate(final String email) {

		matcher = pattern.matcher(email);
		return matcher.matches();

	}

}
