package com.manageuniversity.repository.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.manageuniversity.config.HandleVietnamese;
import com.manageuniversity.controller.request.GetParamRequest;
import com.manageuniversity.entity.Class;
import com.manageuniversity.entity.Class_;
import com.manageuniversity.entity.Course;
import com.manageuniversity.entity.Course_;
import com.manageuniversity.entity.Teacher;
import com.manageuniversity.entity.Teacher_;

import org.springframework.data.jpa.domain.Specification;

public class ClassSpecification extends MySpecification<Class> implements Specification<Class> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<List<SearchCriteria>> list;
	private Integer size;
	

	public ClassSpecification() {
	}

	public ClassSpecification(List<List<String>> requestList, Integer size) {
		// TODO Auto-generated constructor stub
		this.size = size;
		if (requestList != null) {
			this.list = GetParamRequest.getListParam(requestList);
		} else {
			this.list = null;

		}

	}

	@Override
	public Predicate toPredicate(Root<Class> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		List<Predicate> predicates = new ArrayList<>();
		if(this.list==null) return builder.and(predicates.toArray(new Predicate[0]));
		Join<Class, Teacher> teacherJoin = root.join(Class_.TEACHER, JoinType.LEFT);
		Join<Class, Course> courseJoin = root.join(Class_.COURSE, JoinType.LEFT);

		for (List<SearchCriteria> listSearch : list) {

			List<Predicate> predicates1 = new ArrayList<>();
			for (SearchCriteria criteria : listSearch) {
				SearchOperation searchOperation = criteria.getOperation();

				switch (searchOperation) {
				case GREATER_THAN:
					if (criteria.getKey().equals("teacherId")) {
						predicates1.add(builder.greaterThan(teacherJoin.get(Teacher_.ID),
								Integer.parseInt(criteria.getValue().toString())));
					} else if (criteria.getKey().equals("courseId")) {
						predicates1.add(builder.greaterThan(courseJoin.get(Course_.ID),
								Integer.parseInt(criteria.getValue().toString())));
					} else
						predicates1.add(GeneralPredicate.greaterThanPredicate(root, query, builder, criteria));
					break;
				case LESS_THAN:
					if (criteria.getKey().equals("teacherId")) {
						predicates1.add(builder.lessThan(teacherJoin.get(Teacher_.ID),
								Integer.parseInt(criteria.getValue().toString())));
					} else if (criteria.getKey().equals("courseId")) {
						predicates1.add(builder.lessThan(courseJoin.get(Course_.ID),
								Integer.parseInt(criteria.getValue().toString())));
					} else
						predicates1.add(GeneralPredicate.lessThanPredicate(root, query, builder, criteria));

					break;
				case GREATER_THAN_EQUAL:
					if (criteria.getKey().equals("teacherId")) {
						predicates1.add(builder.greaterThanOrEqualTo(teacherJoin.get(Teacher_.ID),
								Integer.parseInt(criteria.getValue().toString())));
					} else if (criteria.getKey().equals("courseId")) {
						predicates1.add(builder.greaterThanOrEqualTo(courseJoin.get(Course_.ID),
								Integer.parseInt(criteria.getValue().toString())));
					} else
						predicates1.add(GeneralPredicate.greaterThanOrEqualPredicate(root, query, builder, criteria));
					break;
				case LESS_THAN_EQUAL:
					if (criteria.getKey().equals("teacherId")) {
						predicates1.add(builder.lessThanOrEqualTo(teacherJoin.get(Teacher_.ID),
								Integer.parseInt(criteria.getValue().toString())));
					} else if (criteria.getKey().equals("courseId")) {
						predicates1.add(builder.lessThanOrEqualTo(courseJoin.get(Course_.ID),
								Integer.parseInt(criteria.getValue().toString())));
					} else
						predicates1.add(GeneralPredicate.lessThanOrEqualPredicate(root, query, builder, criteria));

					break;
				case EQUAL:
					if (criteria.getKey().equals("teacherId")) {
						predicates1.add(builder.equal(teacherJoin.get(Teacher_.ID),
								Integer.parseInt(criteria.getValue().toString())));
					} else if (criteria.getKey().equals("courseId")) {
						predicates1.add(builder.equal(courseJoin.get(Course_.ID),
								Integer.parseInt(criteria.getValue().toString())));
					} else if (criteria.getKey().equals("teacherName")) {
						Expression<String> vietnamePredicate = builder.function("unaccent", String.class,
								teacherJoin.get(Teacher_.FULLNAME));

						predicates1.add(builder.equal(builder.lower(vietnamePredicate),
								HandleVietnamese.removeAccent(criteria.getValue().toString())));
					} else if (criteria.getKey().equals("courseName")) {
						Expression<String> vietnamePredicate = builder.function("unaccent", String.class,
								courseJoin.get(Course_.NAME));
						predicates1.add(builder.equal(builder.lower(vietnamePredicate),
								HandleVietnamese.unAccent(criteria.getValue().toString())));
					} else
						predicates1.add(GeneralPredicate.equalPredicate(root, query, builder, criteria));

					break;
				case NOT_EQUAL:
					if (criteria.getKey().equals("teacherId")) {
						predicates1.add(builder.notEqual(teacherJoin.get(Teacher_.ID),
								Integer.parseInt(criteria.getValue().toString())));
					} else if (criteria.getKey().equals("courseId")) {
						predicates1.add(builder.notEqual(courseJoin.get(Course_.ID),
								Integer.parseInt(criteria.getValue().toString())));
					} else if (criteria.getKey().equals("teacherName")) {
						Expression<String> vietnamePredicate = builder.function("unaccent", String.class,
								teacherJoin.get(Teacher_.FULLNAME));
						predicates1.add((builder.notEqual(builder.lower(vietnamePredicate),
								HandleVietnamese.unAccent(criteria.getValue().toString()))));
					} else if (criteria.getKey().equals("courseName")) {

						Expression<String> vietnamePredicate = builder.function("unaccent", String.class,
								courseJoin.get(Course_.NAME));
						predicates1.add((builder.notEqual(builder.lower(vietnamePredicate),
								HandleVietnamese.unAccent(criteria.getValue().toString()))));
					} else
						predicates1.add(GeneralPredicate.notEqualPredicate(root, query, builder, criteria));

					break;
				case LIKE:
					if (criteria.getKey().equals("teacherName")) {
						Expression<String> vietnamePredicate = builder.function("unaccent", String.class,
								teacherJoin.get(Teacher_.FULLNAME));
						predicates1.add(builder.like(builder.lower(vietnamePredicate),
								"%" + HandleVietnamese.unAccent(criteria.getValue().toString()).toLowerCase() + "%"));
					} else if (criteria.getKey().equals("courseName")) {
						Expression<String> vietnamePredicate = builder.function("unaccent", String.class,
								courseJoin.get(Course_.NAME));
						predicates1.add(builder.like(builder.lower(vietnamePredicate),
								"%" + HandleVietnamese.unAccent(criteria.getValue().toString()).toLowerCase() + "%"));
					} else
						predicates1.add(GeneralPredicate.likePreidcate(root, query, builder, criteria));

					break;
				default:
					break;
				}
			}
			predicates.add(builder.or(predicates1.toArray(new Predicate[0])));
		}
		System.out.println(predicates.toString());
		if (this.size == 1) {
			return builder.or(predicates.toArray(new Predicate[0]));
		}

		else
			return builder.and(predicates.toArray(new Predicate[0]));

	}
}
