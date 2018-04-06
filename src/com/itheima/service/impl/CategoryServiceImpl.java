package com.itheima.service.impl;

import java.util.List;

import com.itheima.dao.CategoryDao;
import com.itheima.dao.impl.CategoryDaoImpl;
import com.itheima.domain.Category;
import com.itheima.service.CategoryService;

public class CategoryServiceImpl implements CategoryService {

	/**
	 * 查询所有的分类
	 */
	@Override
	public List<Category> findAll() throws Exception {
		CategoryDao cd = new CategoryDaoImpl();
		
		return cd.findAll();
	}

}
