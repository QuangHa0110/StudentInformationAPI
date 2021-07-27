package com.manageuniversity.controller.request;

import java.util.ArrayList;
import java.util.List;

import com.manageuniversity.exception.BadRequestException;
import com.manageuniversity.repository.specification.SearchCriteria;
import com.manageuniversity.repository.specification.SearchOperation;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

public class GetParamRequest {

	public static List<List<SearchCriteria>> getListParam(List<List<String>> requestList) {

		if (requestList == null)
			return new ArrayList<List<SearchCriteria>>();
			
		List<List<SearchCriteria>> list = new ArrayList<List<SearchCriteria>>();
		for (List<String> s : requestList) {
			List<SearchCriteria> listSearchCriterias = new ArrayList<>();

			for (String a : s) {
				if (a.indexOf('=') == -1) {
					throw new BadRequestException("Param didn't have '=' ");
				}
				if (!a.trim().isEmpty()) {
					String key = a.substring(0, a.indexOf('=')).trim();
					if (key.indexOf('.') == -1)
						throw new BadRequestException("Param  didn't have a dot");
					String value = a.substring(a.indexOf('=') + 1).trim();

					String[] nameSplit = key.split("[.]");

					switch (nameSplit[1]) {
						case "equal":

							listSearchCriterias.add(new SearchCriteria(nameSplit[0], SearchOperation.EQUAL, value));
							break;
						case "notEqual":
							listSearchCriterias.add(new SearchCriteria(nameSplit[0], SearchOperation.NOT_EQUAL, value));
							break;
						case "greaterThan":
							listSearchCriterias
									.add(new SearchCriteria(nameSplit[0], SearchOperation.GREATER_THAN, value));
							break;
						case "greaterThanOrEqual":
							listSearchCriterias
									.add(new SearchCriteria(nameSplit[0], SearchOperation.GREATER_THAN_EQUAL, value));
							break;
						case "lessThan":
							listSearchCriterias.add(new SearchCriteria(nameSplit[0], SearchOperation.LESS_THAN, value));
							break;
						case "lessThanOrEqual":
							listSearchCriterias
									.add(new SearchCriteria(nameSplit[0], SearchOperation.LESS_THAN_EQUAL, value));
							break;
						case "like":
							listSearchCriterias.add(new SearchCriteria(nameSplit[0], SearchOperation.LIKE, value));
							break;
						default:
							throw new BadRequestException("Parameter incorrect");
					}
				}

			}
			list.add(listSearchCriterias);
		}

		return list;
	}

	public static Pageable getPageable(Integer pageNumber, Integer pageSize, String[] sort) {
		List<Order> orders = new ArrayList<Order>();
		for (String sortBy : sort) {
			if (sortBy.charAt(0) == '+') {
				Order order = new Order(Sort.Direction.ASC, sortBy.substring(1));
				orders.add(order);

			} else if (sortBy.charAt(0) == '-') {
				Order order = new Order(Sort.Direction.DESC, sortBy.substring(1));
				orders.add(order);
			} else {
				throw new BadRequestException("Parameter incorrect");
			}
		}
		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orders));
		return pageable;

	}

}
