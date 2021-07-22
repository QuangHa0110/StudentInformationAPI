package com.manageuniversity.repository.specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.manageuniversity.config.ConvertString;
import com.manageuniversity.config.HandleVietnamese;
import com.manageuniversity.entity.Class;
import com.manageuniversity.entity.Event;

public class EventSpecification implements Specification<Event> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<SearchCriteria> list;

	public EventSpecification() {
		this.list = new ArrayList<>();
	}

	public void add(SearchCriteria criteria) {
		list.add(criteria);
	}

	@SuppressWarnings({ "deprecation" })
	@Override
	public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		List<Predicate> predicates = new ArrayList<>();
		Join<Event, Class> classJoin = root.join("classes", JoinType.LEFT);

		for (SearchCriteria criteria : list) {
			SearchOperation searchOperation = criteria.getOperation();

			Date date;
			switch (searchOperation) {
			case GREATER_THAN:
				if (criteria.getKey().equals("classId")) {
					predicates.add(builder.greaterThan(classJoin.get("id"),
							Integer.parseInt(criteria.getValue().toString())));
				} else if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(Date.class)) {
					date = ConvertString.convetString(criteria.getValue().toString());
					date.setHours(23);
					date.setMinutes(59);
					date.setSeconds(59);

					predicates.add(builder.greaterThan(root.get(criteria.getKey()).as(Date.class), date));
				} else if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(String.class)) {
					predicates.add(builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString()));
				} else if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(Integer.class)) {
					predicates.add(builder.greaterThan(root.get(criteria.getKey()),
							Integer.parseInt(criteria.getValue().toString())));
				}

				break;
			case LESS_THAN:
				if (criteria.getKey().equals("classId")) {
					predicates.add(
							builder.lessThan(classJoin.get("id"), Integer.parseInt(criteria.getValue().toString())));
				} else if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(Date.class)) {
					predicates.add(builder.lessThan(root.get(criteria.getKey()).as(Date.class),
							ConvertString.convetString(criteria.getValue().toString())));
				} else if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(String.class)) {
					predicates.add(builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString()));
				} else if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(Integer.class)) {
					predicates.add(builder.lessThan(root.get(criteria.getKey()),
							Integer.parseInt(criteria.getValue().toString())));
				}

				break;
			case GREATER_THAN_EQUAL:
				if (criteria.getKey().equals("classId")) {
					predicates.add(builder.greaterThanOrEqualTo(classJoin.get("id"),
							Integer.parseInt(criteria.getValue().toString())));
				} else if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(Date.class)) {
					predicates.add(builder.greaterThanOrEqualTo(root.get(criteria.getKey()).as(Date.class),
							ConvertString.convetString(criteria.getValue().toString())));
				} else if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(String.class)) {
					predicates.add(
							builder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
				} else if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(Integer.class)) {
					predicates.add(builder.greaterThanOrEqualTo(root.get(criteria.getKey()),
							Integer.parseInt(criteria.getValue().toString())));
				}
				break;
			case LESS_THAN_EQUAL:
				if (criteria.getKey().equals("classId")) {
					predicates.add(builder.lessThanOrEqualTo(classJoin.get("id"),
							Integer.parseInt(criteria.getValue().toString())));
				}  else if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(Date.class)) {
					date = ConvertString.convetString(criteria.getValue().toString());
					date.setHours(23);
					date.setMinutes(59);
					date.setSeconds(59);
					predicates.add(builder.lessThanOrEqualTo(root.get(criteria.getKey()).as(Date.class), date));
				} else if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(String.class)) {
					predicates.add(
							builder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
				} else if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(Integer.class)) {
					predicates.add(builder.lessThanOrEqualTo(root.get(criteria.getKey()),
							Integer.parseInt(criteria.getValue().toString())));
				}

				break;
			case EQUAL:
				if (criteria.getKey().equals("classId")) {
					predicates.add(
							builder.equal(classJoin.get("id"), Integer.parseInt(criteria.getValue().toString())));
				} else if (criteria.getKey().equals("className")) {
					Expression<String> vietnamePredicate = builder.function("converttvkdau", String.class,
							classJoin.get("name"));

					predicates.add(builder.equal(vietnamePredicate,
							HandleVietnamese.removeAccent(criteria.getValue().toString())));
				}  else if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(Date.class)) {
					date = ConvertString.convetString(criteria.getValue().toString());
					date.setHours(23);
					date.setMinutes(59);
					date.setSeconds(59);

					Date date2 = new Date(date.getYear(), date.getMonth(), date.getDate());

					predicates.add(builder.between(root.get(criteria.getKey()).as(Date.class), date2, date));
				} else if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(String.class)) {
					// xử lý tiếng việt không dấu trong db
					Expression<String> vietnamePredicate = builder.function("converttvkdau", String.class,
							root.get(criteria.getKey()));

					predicates.add(builder.equal(vietnamePredicate,
							HandleVietnamese.removeAccent(criteria.getValue().toString())));
				} else if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(Integer.class)) {
					predicates.add(builder.equal(root.get(criteria.getKey()),
							Integer.parseInt(criteria.getValue().toString())));
				}

				break;
			case NOT_EQUAL:
				if (criteria.getKey().equals("classId")) {
					predicates.add(
							builder.notEqual(classJoin.get("id"), Integer.parseInt(criteria.getValue().toString())));
				}  else if (criteria.getKey().equals("className")) {
					Expression<String> vietnamePredicate = builder.function("converttvkdau", String.class,
							classJoin.get("name"));
					predicates.add((builder.notEqual(vietnamePredicate,
							HandleVietnamese.removeAccent(criteria.getValue().toString()))));
				} else if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(Date.class)) {
					date = ConvertString.convetString(criteria.getValue().toString());
					date.setHours(23);
					date.setMinutes(59);
					date.setSeconds(59);
					Date date2 = new Date(date.getYear(), date.getMonth(), date.getDate());
					Predicate lessthanPredicate = builder.lessThan(root.get(criteria.getKey()).as(Date.class), date2);
					Predicate greaterThanPredicate = builder.greaterThan(root.get(criteria.getKey()).as(Date.class),
							date);

					predicates.add(builder.or(lessthanPredicate, greaterThanPredicate));

				} else if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(String.class)) {
					predicates.add(builder.notEqual(root.get(criteria.getKey()), criteria.getValue().toString()));
				}

				break;
			case LIKE:
				if (criteria.getKey().equals("className")) {
					Expression<String> vietnamePredicate = builder.function("converttvkdau", String.class,
							classJoin.get("name"));
					predicates.add(builder.like(builder.lower(vietnamePredicate),
							"%" + HandleVietnamese.removeAccent(criteria.getValue().toString()).toLowerCase() + "%"));
				}  else {
					Expression<String> vietnamePredicate = builder.function("converttvkdau", String.class,
							root.get(criteria.getKey()));
					predicates.add(builder.like(builder.lower(vietnamePredicate),
							"%" + HandleVietnamese.removeAccent(criteria.getValue().toString()).toLowerCase() + "%"));
				}

				break;
			case IN:
				if (criteria.getKey().equals("classId")) {
					predicates.add(builder.in(classJoin.get("id"))
							.value(ConvertString.convertListInteger((String[]) criteria.getValue())));
				} else if (criteria.getKey().equals("className")) {
					predicates.add(builder.in(classJoin.get("name"))
							.value(ConvertString.convertListString((String[]) criteria.getValue())));
				} else if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(Date.class)) {
					List<Predicate> predicates2 = new ArrayList<>();
					for (Date datetime : ConvertString.convertListDate((String[]) criteria.getValue())) {
						date = datetime;
						date.setHours(23);
						date.setMinutes(59);
						date.setSeconds(59);

						Date date2 = new Date(date.getYear(), date.getMonth(), date.getDate());

						predicates2.add(builder.between(root.get(criteria.getKey()).as(Date.class), date2, date));
					}
					predicates.add(builder.or(predicates2.toArray(new Predicate[0])));
				} else if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(String.class)) {
					predicates.add(builder.in(root.get(criteria.getKey()))
							.value(ConvertString.convertListString((String[]) criteria.getValue())));
				} else if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(Integer.class)) {
					predicates.add(builder.in(root.get(criteria.getKey()))
							.value(ConvertString.convertListInteger((String[]) criteria.getValue())));
				}

				break;
			case NOT_IN:
				if (criteria.getKey().equals("classId")) {

					predicates.add(builder.in(classJoin.get("id"))
							.value(ConvertString.convertListInteger((String[]) criteria.getValue())).not());
				} else if (criteria.getKey().equals("className")) {
					predicates.add(builder.in(classJoin.get("name"))
							.value(ConvertString.convertListDate((String[]) criteria.getValue())).not());
				} else if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(Date.class)) {
					List<Predicate> predicates3 = new ArrayList<>();
					for (Date datetime : ConvertString.convertListDate((String[]) criteria.getValue())) {
						date = datetime;
						date.setHours(23);
						date.setMinutes(59);
						date.setSeconds(59);

						Date date2 = new Date(date.getYear(), date.getMonth(), date.getDate());
						Predicate lessthanPredicate = builder.lessThan(root.get(criteria.getKey()).as(Date.class),
								date2);
						Predicate greaterThanPredicate = builder.greaterThan(root.get(criteria.getKey()).as(Date.class),
								date);

						predicates3.add(builder.or(lessthanPredicate, greaterThanPredicate));
					}
					predicates.add(builder.or(predicates3.toArray(new Predicate[0])));
				} else if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(String.class)) {
					predicates.add(builder.in(root.get(criteria.getKey()))
							.value(ConvertString.convertListString((String[]) criteria.getValue())).not());
				} else if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(Integer.class)) {
					predicates.add(builder.in(root.get(criteria.getKey()))
							.value(ConvertString.convertListInteger((String[]) criteria.getValue())).not());
				}

				break;
			default:
				break;
			}

		}
		return builder.and(predicates.toArray(new Predicate[0]));

	}

}
