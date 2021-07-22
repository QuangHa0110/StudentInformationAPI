package com.manageuniversity.repository.specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class MySpecification<T> implements Specification<T> {
	private List<SearchCriteria> list;

	public MySpecification() {
		// TODO Auto-generated constructor stub
		this.list = new ArrayList<>();
	}

	public void add(SearchCriteria criteria) {
		list.add(criteria);
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		List<Predicate> predicates = new ArrayList<>();
		for (SearchCriteria criteria : list) {
		
			SearchOperation searchOperation = criteria.getOperation();
			switch (searchOperation) {
			case GREATER_THAN:
				if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(Date.class)) {
					predicates.add(builder.greaterThan(root.get(criteria.getKey()).as(Date.class),
							(Date) criteria.getValue()));
				} else {
					predicates.add(builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString()));
				}

				break;

			case LESS_THAN:
				if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(Date.class)) {
					predicates.add(
							builder.lessThan(root.get(criteria.getKey()).as(Date.class), (Date) criteria.getValue()));
				} else {
					predicates.add(builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString()));
				}
				break;
			case GREATER_THAN_EQUAL:
				if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(Date.class)) {
					predicates.add(builder.greaterThanOrEqualTo(root.get(criteria.getKey()).as(Date.class),
							(Date) criteria.getValue()));
				} else {
					predicates.add(
							builder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
				}

				break;
			case LESS_THAN_EQUAL:
				if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(Date.class)) {
					predicates.add(builder.lessThanOrEqualTo(root.get(criteria.getKey()).as(Date.class),
							(Date) criteria.getValue()));
				} else {
					predicates.add(
							builder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
				}
				break;
			case EQUAL:
				
				predicates.add(builder.equal(root.get(criteria.getKey()), criteria.getValue()));
			

				break;
			case NOT_EQUAL:
				predicates.add(builder.notEqual(root.get(criteria.getKey()), criteria.getValue()));
				break;
			case LIKE:
				predicates.add(builder.like(builder.lower(root.get(criteria.getKey())),
						"%" + criteria.getValue().toString().toLowerCase() + "%"));
				break;
			case IN:
				predicates.add(builder.in(root.get(criteria.getKey())).value(criteria.getValue()));
				break;
			case NOT_IN:
				predicates.add(builder.not(root.get(criteria.getKey())).in(criteria.getValue()));
			default:
				break;
			}

		}

		return builder.and(predicates.toArray(new Predicate[0]));

	}

}
