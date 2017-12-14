package com.bwzk.pojo;

import java.util.ArrayList;
import java.util.List;

public class OaDepExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OaDepExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameIsNull() {
            addCriterion("departmentname is null");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameIsNotNull() {
            addCriterion("departmentname is not null");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameEqualTo(String value) {
            addCriterion("departmentname =", value, "departmentname");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameNotEqualTo(String value) {
            addCriterion("departmentname <>", value, "departmentname");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameGreaterThan(String value) {
            addCriterion("departmentname >", value, "departmentname");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameGreaterThanOrEqualTo(String value) {
            addCriterion("departmentname >=", value, "departmentname");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameLessThan(String value) {
            addCriterion("departmentname <", value, "departmentname");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameLessThanOrEqualTo(String value) {
            addCriterion("departmentname <=", value, "departmentname");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameLike(String value) {
            addCriterion("departmentname like", value, "departmentname");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameNotLike(String value) {
            addCriterion("departmentname not like", value, "departmentname");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameIn(List<String> values) {
            addCriterion("departmentname in", values, "departmentname");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameNotIn(List<String> values) {
            addCriterion("departmentname not in", values, "departmentname");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameBetween(String value1, String value2) {
            addCriterion("departmentname between", value1, value2, "departmentname");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameNotBetween(String value1, String value2) {
            addCriterion("departmentname not between", value1, value2, "departmentname");
            return (Criteria) this;
        }

        public Criteria andSupdepidIsNull() {
            addCriterion("supdepid is null");
            return (Criteria) this;
        }

        public Criteria andSupdepidIsNotNull() {
            addCriterion("supdepid is not null");
            return (Criteria) this;
        }

        public Criteria andSupdepidEqualTo(String value) {
            addCriterion("supdepid =", value, "supdepid");
            return (Criteria) this;
        }

        public Criteria andSupdepidNotEqualTo(String value) {
            addCriterion("supdepid <>", value, "supdepid");
            return (Criteria) this;
        }

        public Criteria andSupdepidGreaterThan(String value) {
            addCriterion("supdepid >", value, "supdepid");
            return (Criteria) this;
        }

        public Criteria andSupdepidGreaterThanOrEqualTo(String value) {
            addCriterion("supdepid >=", value, "supdepid");
            return (Criteria) this;
        }

        public Criteria andSupdepidLessThan(String value) {
            addCriterion("supdepid <", value, "supdepid");
            return (Criteria) this;
        }

        public Criteria andSupdepidLessThanOrEqualTo(String value) {
            addCriterion("supdepid <=", value, "supdepid");
            return (Criteria) this;
        }

        public Criteria andSupdepidLike(String value) {
            addCriterion("supdepid like", value, "supdepid");
            return (Criteria) this;
        }

        public Criteria andSupdepidNotLike(String value) {
            addCriterion("supdepid not like", value, "supdepid");
            return (Criteria) this;
        }

        public Criteria andSupdepidIn(List<String> values) {
            addCriterion("supdepid in", values, "supdepid");
            return (Criteria) this;
        }

        public Criteria andSupdepidNotIn(List<String> values) {
            addCriterion("supdepid not in", values, "supdepid");
            return (Criteria) this;
        }

        public Criteria andSupdepidBetween(String value1, String value2) {
            addCriterion("supdepid between", value1, value2, "supdepid");
            return (Criteria) this;
        }

        public Criteria andSupdepidNotBetween(String value1, String value2) {
            addCriterion("supdepid not between", value1, value2, "supdepid");
            return (Criteria) this;
        }

        public Criteria andSubcompanyid1IsNull() {
            addCriterion("subcompanyid1 is null");
            return (Criteria) this;
        }

        public Criteria andSubcompanyid1IsNotNull() {
            addCriterion("subcompanyid1 is not null");
            return (Criteria) this;
        }

        public Criteria andSubcompanyid1EqualTo(String value) {
            addCriterion("subcompanyid1 =", value, "subcompanyid1");
            return (Criteria) this;
        }

        public Criteria andSubcompanyid1NotEqualTo(String value) {
            addCriterion("subcompanyid1 <>", value, "subcompanyid1");
            return (Criteria) this;
        }

        public Criteria andSubcompanyid1GreaterThan(String value) {
            addCriterion("subcompanyid1 >", value, "subcompanyid1");
            return (Criteria) this;
        }

        public Criteria andSubcompanyid1GreaterThanOrEqualTo(String value) {
            addCriterion("subcompanyid1 >=", value, "subcompanyid1");
            return (Criteria) this;
        }

        public Criteria andSubcompanyid1LessThan(String value) {
            addCriterion("subcompanyid1 <", value, "subcompanyid1");
            return (Criteria) this;
        }

        public Criteria andSubcompanyid1LessThanOrEqualTo(String value) {
            addCriterion("subcompanyid1 <=", value, "subcompanyid1");
            return (Criteria) this;
        }

        public Criteria andSubcompanyid1Like(String value) {
            addCriterion("subcompanyid1 like", value, "subcompanyid1");
            return (Criteria) this;
        }

        public Criteria andSubcompanyid1NotLike(String value) {
            addCriterion("subcompanyid1 not like", value, "subcompanyid1");
            return (Criteria) this;
        }

        public Criteria andSubcompanyid1In(List<String> values) {
            addCriterion("subcompanyid1 in", values, "subcompanyid1");
            return (Criteria) this;
        }

        public Criteria andSubcompanyid1NotIn(List<String> values) {
            addCriterion("subcompanyid1 not in", values, "subcompanyid1");
            return (Criteria) this;
        }

        public Criteria andSubcompanyid1Between(String value1, String value2) {
            addCriterion("subcompanyid1 between", value1, value2, "subcompanyid1");
            return (Criteria) this;
        }

        public Criteria andSubcompanyid1NotBetween(String value1, String value2) {
            addCriterion("subcompanyid1 not between", value1, value2, "subcompanyid1");
            return (Criteria) this;
        }

        public Criteria andIdLikeInsensitive(String value) {
            addCriterion("upper(id) like", value.toUpperCase(), "id");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameLikeInsensitive(String value) {
            addCriterion("upper(departmentname) like", value.toUpperCase(), "departmentname");
            return (Criteria) this;
        }

        public Criteria andSupdepidLikeInsensitive(String value) {
            addCriterion("upper(supdepid) like", value.toUpperCase(), "supdepid");
            return (Criteria) this;
        }

        public Criteria andSubcompanyid1LikeInsensitive(String value) {
            addCriterion("upper(subcompanyid1) like", value.toUpperCase(), "subcompanyid1");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}