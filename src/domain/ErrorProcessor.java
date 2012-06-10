package domain;

import bean.ErrorBean;

import com.google.gson.Gson;

public class ErrorProcessor
{

	public static String toJSON(ErrorBean bean)
	{
		return new Gson().toJson(bean); 
	}
}
