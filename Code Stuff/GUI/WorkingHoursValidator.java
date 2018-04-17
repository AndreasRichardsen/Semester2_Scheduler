package gui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WorkingHoursValidator {

	private Pattern pattern;
	private Matcher matcher;
	private static final String WORKING_HOURS_PATTERN = 
			"(0|[1-9]\\d{0,2})";

	public WorkingHoursValidator() {
		pattern = Pattern.compile(WORKING_HOURS_PATTERN);
	}
	
	public boolean validate(final String workingHours) {

		matcher = pattern.matcher(workingHours);
		return matcher.matches();

	}

}
