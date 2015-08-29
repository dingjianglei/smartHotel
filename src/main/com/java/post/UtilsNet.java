package java.post;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilsNet {
	public final static String MACREG = "[0-9a-fA-F][0-9a-fA-F]:[0-9a-fA-F][0-9a-fA-F]:[0-9a-fA-F][0-9a-fA-F]:[0-9a-fA-F][0-9a-fA-F]:[0-9a-fA-F][0-9a-fA-F]:[0-9a-fA-F][0-9a-fA-F]";
	public final static String MACREG2 = "[0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F]";

	public static boolean isMac(String mac) {
		 
		Pattern pattern = Pattern.compile(MACREG, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(mac);
		if (matcher.matches()) {
			return true;
		}

		pattern = Pattern.compile(MACREG2, Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(mac);
		if (matcher.matches()) {
			return true;
		}

		return false;
	}
}