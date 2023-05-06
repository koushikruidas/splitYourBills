/*
 * package com.projectY.splitYourBills.service;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.stereotype.Service;
 * 
 * import com.projectY.splitYourBills.utility.ExpenseSplitType;
 * 
 * @Service public class SplitFactory {
 * 
 * @Autowired private EqualExpenseSplit equalExpenseSplit;
 * 
 * @Autowired private UnequalExpenseSplit unequalExpenseSplit;
 * 
 * @Autowired private PercentageExpenseSplit percentageExpenseSplit;
 * 
 * public static ExpenseSplit getSplitObject(ExpenseSplitType splitType) {
 * 
 * switch (splitType) { case EQUAL: return new EqualExpenseSplit(); case
 * UNEQUAL: return new UnequalExpenseSplit(); case PERCENTAGE: return new
 * PercentageExpenseSplit(); default: return null; } } }
 */