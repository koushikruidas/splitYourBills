/*
 * package com.projectY.splitYourBills.converter;
 * 
 * import com.projectY.splitYourBills.utility.Split;
 * 
 * import javax.persistence.AttributeConverter; import
 * javax.persistence.Converter; import java.util.ArrayList; import
 * java.util.List;
 * 
 * @Converter public class SplitListConverter implements
 * AttributeConverter<List<Split>, String> { private static final String
 * SPLIT_DELIMITER = ";"; private static final String AMOUNT_DELIMITER = ":";
 * 
 * @Override public String convertToDatabaseColumn(List<Split> splits) {
 * StringBuilder sb = new StringBuilder(); for (Split split : splits) {
 * sb.append(split.getUserId()) .append(AMOUNT_DELIMITER)
 * .append(split.getAmount()) .append(SPLIT_DELIMITER); } return sb.toString();
 * }
 * 
 * @Override public List<Split> convertToEntityAttribute(String dbData) {
 * List<Split> splits = new ArrayList<>(); String[] splitStrs =
 * dbData.split(SPLIT_DELIMITER); for (String splitStr : splitStrs) { String[]
 * tokens = splitStr.split(AMOUNT_DELIMITER); splits.add(new
 * Split(Long.parseLong(tokens[0]), Double.parseDouble(tokens[1]))); } return
 * splits; } }
 */