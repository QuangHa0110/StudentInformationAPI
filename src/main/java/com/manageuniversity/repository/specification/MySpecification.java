package com.manageuniversity.repository.specification;

import java.util.ArrayList;
import java.util.List;

public class MySpecification<T> {
	private List<List<SearchCriteria>> list;

	public MySpecification() {
		// TODO Auto-generated constructor stub
		this.list = new ArrayList<>();
	}

	public List<List<SearchCriteria>> getList() {
		return list;
	}

	public void setList(List<List<SearchCriteria>> list) {
		this.list = list;
	}

	public void add(SearchCriteria searchCriteria) {
	}

	

}
