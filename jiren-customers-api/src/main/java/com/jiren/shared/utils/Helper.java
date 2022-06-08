package com.jiren.shared.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;

public class Helper {

    public static String formatAddress(String address, String city){
        return address + " - " + city;
    }

	public static Boolean validaExtensionExcel(String fileName) {
    	if (fileName == null) {
    		return false;
    	}
        String extension = FilenameUtils.getExtension(fileName);
        if (extension != null && !extension.equalsIgnoreCase("xlsx")) {
            return false;
        }
        return true;
    }
	
	public static boolean isValid(String field, String regex) {
        if(field == null || regex == null)
            return false;
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(field);
        return matcher.matches();
    }
	
	public static boolean isNullOrEmpty(String field) {
        if(field == null || field.isEmpty())
            return true;
        return false;
    }
	
	public static boolean isValidTime(String time) {
        return isValid(time,"([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]");
    }
	
	public static boolean isValidLength(String field, int len) {
        if(field == null)
            return false;
        return field.length() <= len;
    }
	
	public static boolean isValidCe(String ce) {
        if(ce == null || ce.isEmpty())
            return  false;

        return ce.length() <= 12 && ce.length() >= 7;
    }
	
	public static boolean isValidDni(String dni) {
        if(dni == null || dni.isEmpty())
            return false;
        if(dni.length() != 8)
            return false;
        return isValid(dni, "^([0-9]*)$");
    }
	
	public static boolean isValidRuc(String ruc) {
        if (ruc == null) {
            return false;
        }
        final int[] multipliers = {5, 4, 3, 2, 7, 6, 5, 4, 3, 2};
        final String[] prefixes = {"10", "15", "17", "20"};
        final int length = multipliers.length + 1;

        if (ruc.length() != length) {
            return false;
        }

        boolean isPrefixOk = false;

        for (String prefix : prefixes) {
            if (ruc.substring(0, 2).equals(prefix)) {
                isPrefixOk = true;
                break;
            }
        }

        if (!isPrefixOk) {
            return false;
        }

        int sum = 0;

        for (int i = 0; i < multipliers.length; i++) {
            final char section = ruc.charAt(i);

            if (!Character.isDigit(section)) {
                return false;
            }

            sum += Character.getNumericValue(ruc.charAt(i)) * multipliers[i];
        }

        final int rest = sum % length;
        final String response = String.valueOf(length - rest);

        return response.charAt(response.length() - 1) == ruc.charAt(ruc.length() - 1);
    }
	
	public static boolean isValidEmail(String email) {
        return isValid(email, "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
    }
	
	public static boolean isValidDate(String date, String format) {
		if(isNullOrEmpty(date) || isNullOrEmpty(format))
            return false;

        DateFormat sdf = new SimpleDateFormat(format);
        sdf.setLenient(false);
        try {
            sdf.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
	
	public static List<String> findDuplicate(List<String> list){
		
		List<String> duplicateValues = list
                .stream().collect(Collectors.groupingBy(s -> s))
                .entrySet().stream().filter(e -> e.getValue().size() > 1)
                .map(e -> e.getKey())
                .collect(Collectors.toList());
		
		return duplicateValues;
	}
	
	public static String reemplazarEspaciosString(String cadena) {
        cadena = cadena.trim();
        cadena = cadena.replace(" ", "_");
        return cadena;
    }
}
