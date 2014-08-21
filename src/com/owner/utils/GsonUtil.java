package com.owner.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {
	private static  Gson gson=new GsonBuilder().create();
	public synchronized static Gson getGson()
	{
		if(gson==null)
		{
			gson=new GsonBuilder().create();
		}
		return gson;
	}
}
