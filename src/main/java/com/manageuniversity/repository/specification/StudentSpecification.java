package com.manageuniversity.repository.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.manageuniversity.controller.request.GetParamRequest;
import com.manageuniversity.entity.Student;

import org.springframework.data.jpa.domain.Specification;

public class StudentSpecification extends MySpecification<Student> implements Specification<Student> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<List<SearchCriteria>> list;
	private Integer size;

	public StudentSpecification(List<List<String>> requestList, Integer size) {
		// TODO Auto-generated constructor stub
		this.size = size;
		if (requestList != null) {
			this.list = GetParamRequest.getListParam(requestList);
		} else {
			this.list = new ArrayList<List<SearchCriteria>>();

		}

	}

	@Override
	public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		List<Predicate> predicates = new ArrayList<>();

		for (List<SearchCriteria> listSearch : list) {

			List<Predicate> predicates1 = new ArrayList<>();
			for (SearchCriteria criteria : listSearch) {
				SearchOperation searchOperation = criteria.getOperation();

				switch (searchOperation) {
				case GREATER_THAN:
					predicates1.add(GeneralPredicate.greaterThanPredicate(root, query, builder, criteria));
					break;
				case LESS_THAN:

					predicates1.add(GeneralPredicate.lessThanPredicate(root, query, builder, criteria));

					break;
				case GREATER_THAN_EQUAL:

					predicates1.add(GeneralPredicate.greaterThanOrEqualPredicate(root, query, builder, criteria));
					break;
				case LESS_THAN_EQUAL:

					predicates1.add(GeneralPredicate.lessThanOrEqualPredicate(root, query, builder, criteria));

					break;
				case EQUAL:

					predicates1.add(GeneralPredicate.equalPredicate(root, query, builder, criteria));

					break;
				case NOT_EQUAL:

					predicates1.add(GeneralPredicate.notEqualPredicate(root, query, builder, criteria));

					break;
				case LIKE:

					predicates1.add(GeneralPredicate.likePreidcate(root, query, builder, criteria));

					break;
				default:
					break;
				}
			}
			predicates.add(builder.or(predicates1.toArray(new Predicate[0])));
		}
		if (this.size == 1) {
			return builder.or(predicates.toArray(new Predicate[0]));
		}

		else
			return builder.and(predicates.toArray(new Predicate[0]));

	}
}
