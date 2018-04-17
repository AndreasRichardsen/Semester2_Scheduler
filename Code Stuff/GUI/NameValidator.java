package gui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameValidator {

	private Pattern pattern;
	private Matcher matcher;
	private static final String NAME_PATTERN = 
			"^[\\p{L} .'-]+$";

	public NameValidator() {
		pattern = Pattern.compile(NAME_PATTERN);
	}
	
	public boolean validate(final String name) {

		matcher = pattern.matcher(name);
		return matcher.matches();

	}
}
