package com.manageuniversity.repository.specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.manageuniversity.config.ConvertString;
import com.manageuniversity.config.HandleVietnamese;

public class GeneralPredicate {
	@SuppressWarnings("deprecation")
	public static <T> Predicate greaterThanPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder,
			SearchCriteria criteria) {
		if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(Date.class)) {
			Date date = ConvertString.convetString(criteria.getValue().toString());
			date.setHours(23);
			date.setMinutes(59);
			date.setSeconds(59);

			return builder.greaterThan(root.get(criteria.getKey()).as(Date.class), date);
		} else if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(String.class)) {
			return builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString());
		} else if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(Integer.class)) {
			return builder.greaterThan(root.get(criteria.getKey()), Integer.parseInt(criteria.getValue().toString()));
		}
		return null;

	}

	public static <T> Predicate lessThanPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder,
			SearchCriteria criteria) {
		if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(Date.class)) {
			return builder.lessThan(root.get(criteria.getKey()).as(Date.class),
					ConvertString.convetString(criteria.getValue().toString()));
		} else if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(String.class)) {
			return builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString());
		} else if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(Integer.class)) {
			return builder.lessThan(root.get(criteria.getKey()), Integer.parseInt(criteria.getValue().toString()));
		}
		return null;
	}

	public static <T> Predicate greaterThanOrEqualPredicate(Root<T> root, CriteriaQuery<?> query,
			CriteriaBuilder builder, SearchCriteria criteria) {

		if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(Date.class)) {
			return builder.greaterThanOrEqualTo(root.get(criteria.getKey()).as(Date.class),
					ConvertString.convetString(criteria.getValue().toString()));
		} else if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(String.class)) {
			return builder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString());
		} else if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(Integer.class)) {
			return builder.greaterThanOrEqualTo(root.get(criteria.getKey()),
					Integer.parseInt(criteria.getValue().toString()));
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public static <T> Predicate lessThanOrEqualPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder,
			SearchCriteria criteria) {

		if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(Date.class)) {
			Date date = ConvertString.convetString(criteria.getValue().toString());
			date.setHours(23);
			date.setMinutes(59);
			date.setSeconds(59);
			return builder.lessThanOrEqualTo(root.get(criteria.getKey()).as(Date.class), date);
		} else if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(String.class)) {
			return builder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString());
		} else if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(Integer.class)) {
			return builder.lessThanOrEqualTo(root.get(criteria.getKey()),
					Integer.parseInt(criteria.getValue().toString()));
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public static <T> Predicate equalPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder,
			SearchCriteria criteria) {

		if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(Date.class)) {
			Date date = ConvertString.convetString(criteria.getValue().toString());
			date.setHours(23);
			date.setMinutes(59);
			date.setSeconds(59);

			Date date2 = new Date(date.getYear(), date.getMonth(), date.getDate());

			return builder.between(root.get(criteria.getKey()).as(Date.class), date2, date);
		} else if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(String.class)) {
			// xử lý tiếng việt không dấu trong db
			Expression<String> vietnamePredicate = builder.function("unaccent", String.class,
					root.get(criteria.getKey()));
			return builder.equal(builder.lower(vietnamePredicate),
					HandleVietnamese.removeAccent(criteria.getValue().toString()));
		} else if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(Integer.class)) {
			return builder.equal(root.get(criteria.getKey()), Integer.parseInt(criteria.getValue().toString()));
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public static <T> Predicate notEqualPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder,
			SearchCriteria criteria) {

		if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(Date.class)) {
			Date date = ConvertString.convetString(criteria.getValue().toString());
			date.setHours(23);
			date.setMinutes(59);
			date.setSeconds(59);
			Date date2 = new Date(date.getYear(), date.getMonth(), date.getDate());
			Predicate lessthanPredicate = builder.lessThan(root.get(criteria.getKey()).as(Date.class), date2);
			Predicate greaterThanPredicate = builder.greaterThan(root.get(criteria.getKey()).as(Date.class), date);

			return builder.or(lessthanPredicate, greaterThanPredicate);

		} else if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(String.class)) {
			return builder.notEqual(builder.lower(root.get(criteria.getKey())),
					HandleVietnamese.unAccent(criteria.getValue().toString()));
		}
		return null;
	}

	public static <T> Predicate likePreidcate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder,
			SearchCriteria criteria) {

		Expression<String> vietnamePredicate = builder.function("unaccent", String.class, root.get(criteria.getKey()));
		return builder.like(builder.lower(vietnamePredicate),
				"%" + HandleVietnamese.removeAccent(criteria.getValue().toString()).toLowerCase() + "%");

	}

	@SuppressWarnings("deprecation")
	public static <T> Predicate inPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder,
			SearchCriteria criteria) {

		if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(Date.class)) {
			List<Predicate> predicates2 = new ArrayList<>();
			for (Date datetime : ConvertString.convertListDate((String[]) criteria.getValue())) {
				Date date = datetime;
				date.setHours(23);
				date.setMinutes(59);
				date.setSeconds(59);

				Date date2 = new Date(date.getYear(), date.getMonth(), date.getDate());

				predicates2.add(builder.between(root.get(criteria.getKey()).as(Date.class), date2, date));
			}
			return builder.or(predicates2.toArray(new Predicate[0]));
		} else if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(String.class)) {
			return builder.in(root.get(criteria.getKey()))
					.value(ConvertString.convertListString((String[]) criteria.getValue()));
		} else if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(Integer.class)) {
			return builder.in(root.get(criteria.getKey()))
					.value(ConvertString.convertListInteger((String[]) criteria.getValue()));
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public static <T> Predicate notInPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder,
			SearchCriteria criteria) {

		if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(Date.class)) {
			List<Predicate> predicates3 = new ArrayList<>();
			for (Date datetime : ConvertString.convertListDate((String[]) criteria.getValue())) {
				Date date = datetime;
				date.setHours(23);
				date.setMinutes(59);
				date.setSeconds(59);

				Date date2 = new Date(date.getYear(), date.getMonth(), date.getDate());
				Predicate lessthanPredicate = builder.lessThan(root.get(criteria.getKey()).as(Date.class), date2);
				Predicate greaterThanPredicate = builder.greaterThan(root.get(criteria.getKey()).as(Date.class), date);

				predicates3.add(builder.or(lessthanPredicate, greaterThanPredicate));
			}
			return builder.or(predicates3.toArray(new Predicate[0]));
		} else if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(String.class)) {
			return builder.in(root.get(criteria.getKey()))
					.value(ConvertString.convertListString((String[]) criteria.getValue())).not();
		} else if (root.get(criteria.getKey()).getJavaType().isAssignableFrom(Integer.class)) {
			return builder.in(root.get(criteria.getKey()))
					.value(ConvertString.convertListInteger((String[]) criteria.getValue())).not();
		}
		return null;
	}
}
