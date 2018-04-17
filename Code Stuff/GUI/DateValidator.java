package gui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateValidator {

	private Pattern pattern;
	private Matcher matcher;
	private static final String DATE_PATTERN = 
			"^(19|20)\\d{2}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$";

	public DateValidator() {
		pattern = Pattern.compile(DATE_PATTERN);
	}

	public boolean validate(final String date) {

		matcher = pattern.matcher(date);
		return matcher.matches();

	}
}
