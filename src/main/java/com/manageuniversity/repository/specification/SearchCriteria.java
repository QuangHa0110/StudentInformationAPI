package com.manageuniversity.repository.specification;

public class SearchCriteria {
	private String key;
	private SearchOperation operation;
	private Object value;
//	private static final String DATE_FORMAT = "yyyy-MM-dd";
//	public static final String[] operator = { "eq", "gt", "lt", "gte", "like", "not", "in", "not-in", "lte" };
//	public static final SearchOperation[] searchOperations = { SearchOperation.EQUAL, SearchOperation.GREATER_THAN,
//			SearchOperation.LESS_THAN, SearchOperation.GREATER_THAN_EQUAL, SearchOperation.LIKE,
//			SearchOperation.NOT_EQUAL, SearchOperation.IN, SearchOperation.NOT_IN, SearchOperation.LESS_THAN_EQUAL };

	public SearchCriteria(String key, SearchOperation operation, Object value) {
	
		this.key = key;
		this.operation = operation;
		this.value = value;
	}
	

//	public SearchCriteria(String key, String param, Class field) {
//		this.key = key;
//
//		String operation = param.substring(0, param.indexOf(":")).trim();
//		String value = param.substring(param.indexOf(":") + 1).trim();
//		for (int i = 0; i < operator.length; i++) {
//			if (operator[i].equals(operation)) {
//				this.operation = searchOperations[i];
//				break;
//			}
//
//		}
//		if (operation.equals("eq") && field.isAssignableFrom(Date.class)) {
//			try {
//				this.value = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(value);
//
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} else {
//			this.value = castToRequiredType(field, value);
//		}
//
//	}

	@Override
	public String toString() {
		return "SearchCriteria [key=" + key + ", operation=" + operation + ", value=" + value + "]";
	}


	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public SearchOperation getOperation() {
		return operation;
	}

	public void setOperation(SearchOperation operation) {
		this.operation = operation;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

//	private Object castToRequiredType(Class fieldType, String value) {
//		if (fieldType.isAssignableFrom(Double.class)) {
//			return Double.valueOf(value);
//		} else if (fieldType.isAssignableFrom(Integer.class)) {
//			return Integer.valueOf(value);
//		} else if (Enum.class.isAssignableFrom(fieldType)) {
//			return Enum.valueOf(fieldType, value);
//		} else if (Date.class.isAssignableFrom(fieldType)) {
//			try {
//
//				return new SimpleDateFormat(DATE_FORMAT).parse(value);
//
//			} catch (ParseException e) {
//
//				throw new APIException("Server error");
//			}
//		}
//		return value;
//	}

}
