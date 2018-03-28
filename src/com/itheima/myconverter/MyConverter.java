package com.itheima.myconverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.Converter;

public class MyConverter implements Converter {

	@Override
	public Object convert(Class clazz, Object value) {
		// Class 要转成的类型
		// object 页面上传入的值
		
		// 将 object转成 date
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = sdf.parse((String) value);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
