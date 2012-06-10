package util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestDateUtil 
{
	@Test
	public void testGetTwoDigitNum()
	{
		assertEquals("11",DateUtil.getTwoDigitNum(11));
		assertEquals("09",DateUtil.getTwoDigitNum(9));
		assertEquals("?",DateUtil.getTwoDigitNum(null));
	}
	
	@Test
	public void testGetAbbreviatedYear()
	{
		assertEquals("99", DateUtil.getAbbreviatedYear(1999));
		assertEquals("00", DateUtil.getAbbreviatedYear(2000));
		assertEquals("51", DateUtil.getAbbreviatedYear(1951));
	}
	
	@Test
	public void testValidDate()
	{
		assertEquals(false, DateUtil.validDate(-1));
		assertEquals(false, DateUtil.validDate(0));
		assertEquals(false, DateUtil.validDate(33));
		assertEquals(false, DateUtil.validDate(null));
		assertEquals(true, DateUtil.validDate(5));
	}
	
	@Test
	public void testValidMonth()
	{
		assertEquals(false, DateUtil.validMonth(-1));
		assertEquals(false, DateUtil.validMonth(0));
		assertEquals(false, DateUtil.validMonth(13));
		assertEquals(false, DateUtil.validMonth(null));
		assertEquals(true, DateUtil.validDate(5));
	}
	
	@Test
	public void testValidYear()
	{
		assertEquals(false, DateUtil.validYear(-1));
		assertEquals(false, DateUtil.validYear(0));
		assertEquals(false, DateUtil.validYear(1945));
		assertEquals(false, DateUtil.validYear(null));
		assertEquals(false, DateUtil.validYear(3000));
		assertEquals(true, DateUtil.validYear(1995));
	}
	
	@Test
	public void testGetMonthNameFromMonthNum()
	{
		assertEquals("January", DateUtil.getMonthNameFromMonthNum(1));
		assertEquals("February", DateUtil.getMonthNameFromMonthNum(2));
		assertEquals("March", DateUtil.getMonthNameFromMonthNum(3));
		assertEquals("April", DateUtil.getMonthNameFromMonthNum(4));
		assertEquals("May", DateUtil.getMonthNameFromMonthNum(5));
		assertEquals("June", DateUtil.getMonthNameFromMonthNum(6));
		assertEquals("July", DateUtil.getMonthNameFromMonthNum(7));
		assertEquals("August", DateUtil.getMonthNameFromMonthNum(8));
		assertEquals("September", DateUtil.getMonthNameFromMonthNum(9));
		assertEquals("October", DateUtil.getMonthNameFromMonthNum(10));
		assertEquals("November", DateUtil.getMonthNameFromMonthNum(11));
		assertEquals("December", DateUtil.getMonthNameFromMonthNum(12));
	}
	
	@Test
	public void testGetSupplementedDateValue()
	{
		assertEquals("1st", DateUtil.getSupplementedDateValue(1));
		assertEquals("2nd", DateUtil.getSupplementedDateValue(2));
		assertEquals("3rd", DateUtil.getSupplementedDateValue(3));
		assertEquals("4th", DateUtil.getSupplementedDateValue(4));
		assertEquals("5th", DateUtil.getSupplementedDateValue(5));
		assertEquals("6th", DateUtil.getSupplementedDateValue(6));
		assertEquals("7th", DateUtil.getSupplementedDateValue(7));
		assertEquals("8th", DateUtil.getSupplementedDateValue(8));
		assertEquals("9th", DateUtil.getSupplementedDateValue(9));
		assertEquals("10th", DateUtil.getSupplementedDateValue(10));
		assertEquals("11th", DateUtil.getSupplementedDateValue(11));
		assertEquals("12th", DateUtil.getSupplementedDateValue(12));
		assertEquals("13th", DateUtil.getSupplementedDateValue(13));
		assertEquals("14th", DateUtil.getSupplementedDateValue(14));
		assertEquals("15th", DateUtil.getSupplementedDateValue(15));
		assertEquals("16th", DateUtil.getSupplementedDateValue(16));
		assertEquals("17th", DateUtil.getSupplementedDateValue(17));
		assertEquals("18th", DateUtil.getSupplementedDateValue(18));
		assertEquals("19th", DateUtil.getSupplementedDateValue(19));
		assertEquals("20th", DateUtil.getSupplementedDateValue(20));
		assertEquals("21st", DateUtil.getSupplementedDateValue(21));
		assertEquals("22nd", DateUtil.getSupplementedDateValue(22));
		assertEquals("23rd", DateUtil.getSupplementedDateValue(23));
		assertEquals("24th", DateUtil.getSupplementedDateValue(24));
		assertEquals("25th", DateUtil.getSupplementedDateValue(25));
		assertEquals("26th", DateUtil.getSupplementedDateValue(26));
		assertEquals("27th", DateUtil.getSupplementedDateValue(27));
		assertEquals("28th", DateUtil.getSupplementedDateValue(28));
		assertEquals("29th", DateUtil.getSupplementedDateValue(29));
		assertEquals("30th", DateUtil.getSupplementedDateValue(30));
		assertEquals("31st", DateUtil.getSupplementedDateValue(31));
		assertEquals("32nd", DateUtil.getSupplementedDateValue(32));
	}

}
