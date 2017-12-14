package com.bwzk.pojo;

import java.util.ArrayList;
import java.util.List;

public class FlowDataItemExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FlowDataItemExample() {
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

        public Criteria andIdEqualTo(Object value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Object value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Object value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Object value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Object value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Object value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Object> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Object> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Object value1, Object value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Object value1, Object value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andPidIsNull() {
            addCriterion("pid is null");
            return (Criteria) this;
        }

        public Criteria andPidIsNotNull() {
            addCriterion("pid is not null");
            return (Criteria) this;
        }

        public Criteria andPidEqualTo(Object value) {
            addCriterion("pid =", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotEqualTo(Object value) {
            addCriterion("pid <>", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidGreaterThan(Object value) {
            addCriterion("pid >", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidGreaterThanOrEqualTo(Object value) {
            addCriterion("pid >=", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLessThan(Object value) {
            addCriterion("pid <", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLessThanOrEqualTo(Object value) {
            addCriterion("pid <=", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidIn(List<Object> values) {
            addCriterion("pid in", values, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotIn(List<Object> values) {
            addCriterion("pid not in", values, "pid");
            return (Criteria) this;
        }

        public Criteria andPidBetween(Object value1, Object value2) {
            addCriterion("pid between", value1, value2, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotBetween(Object value1, Object value2) {
            addCriterion("pid not between", value1, value2, "pid");
            return (Criteria) this;
        }

        public Criteria andTablenameIsNull() {
            addCriterion("tablename is null");
            return (Criteria) this;
        }

        public Criteria andTablenameIsNotNull() {
            addCriterion("tablename is not null");
            return (Criteria) this;
        }

        public Criteria andTablenameEqualTo(Object value) {
            addCriterion("tablename =", value, "tablename");
            return (Criteria) this;
        }

        public Criteria andTablenameNotEqualTo(Object value) {
            addCriterion("tablename <>", value, "tablename");
            return (Criteria) this;
        }

        public Criteria andTablenameGreaterThan(Object value) {
            addCriterion("tablename >", value, "tablename");
            return (Criteria) this;
        }

        public Criteria andTablenameGreaterThanOrEqualTo(Object value) {
            addCriterion("tablename >=", value, "tablename");
            return (Criteria) this;
        }

        public Criteria andTablenameLessThan(Object value) {
            addCriterion("tablename <", value, "tablename");
            return (Criteria) this;
        }

        public Criteria andTablenameLessThanOrEqualTo(Object value) {
            addCriterion("tablename <=", value, "tablename");
            return (Criteria) this;
        }

        public Criteria andTablenameIn(List<Object> values) {
            addCriterion("tablename in", values, "tablename");
            return (Criteria) this;
        }

        public Criteria andTablenameNotIn(List<Object> values) {
            addCriterion("tablename not in", values, "tablename");
            return (Criteria) this;
        }

        public Criteria andTablenameBetween(Object value1, Object value2) {
            addCriterion("tablename between", value1, value2, "tablename");
            return (Criteria) this;
        }

        public Criteria andTablenameNotBetween(Object value1, Object value2) {
            addCriterion("tablename not between", value1, value2, "tablename");
            return (Criteria) this;
        }

        public Criteria andKeywordIsNull() {
            addCriterion("keyword is null");
            return (Criteria) this;
        }

        public Criteria andKeywordIsNotNull() {
            addCriterion("keyword is not null");
            return (Criteria) this;
        }

        public Criteria andKeywordEqualTo(Object value) {
            addCriterion("keyword =", value, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordNotEqualTo(Object value) {
            addCriterion("keyword <>", value, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordGreaterThan(Object value) {
            addCriterion("keyword >", value, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordGreaterThanOrEqualTo(Object value) {
            addCriterion("keyword >=", value, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordLessThan(Object value) {
            addCriterion("keyword <", value, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordLessThanOrEqualTo(Object value) {
            addCriterion("keyword <=", value, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordIn(List<Object> values) {
            addCriterion("keyword in", values, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordNotIn(List<Object> values) {
            addCriterion("keyword not in", values, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordBetween(Object value1, Object value2) {
            addCriterion("keyword between", value1, value2, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordNotBetween(Object value1, Object value2) {
            addCriterion("keyword not between", value1, value2, "keyword");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(Object value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(Object value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(Object value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(Object value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(Object value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(Object value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<Object> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<Object> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(Object value1, Object value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(Object value1, Object value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andMjIsNull() {
            addCriterion("mj is null");
            return (Criteria) this;
        }

        public Criteria andMjIsNotNull() {
            addCriterion("mj is not null");
            return (Criteria) this;
        }

        public Criteria andMjEqualTo(Object value) {
            addCriterion("mj =", value, "mj");
            return (Criteria) this;
        }

        public Criteria andMjNotEqualTo(Object value) {
            addCriterion("mj <>", value, "mj");
            return (Criteria) this;
        }

        public Criteria andMjGreaterThan(Object value) {
            addCriterion("mj >", value, "mj");
            return (Criteria) this;
        }

        public Criteria andMjGreaterThanOrEqualTo(Object value) {
            addCriterion("mj >=", value, "mj");
            return (Criteria) this;
        }

        public Criteria andMjLessThan(Object value) {
            addCriterion("mj <", value, "mj");
            return (Criteria) this;
        }

        public Criteria andMjLessThanOrEqualTo(Object value) {
            addCriterion("mj <=", value, "mj");
            return (Criteria) this;
        }

        public Criteria andMjIn(List<Object> values) {
            addCriterion("mj in", values, "mj");
            return (Criteria) this;
        }

        public Criteria andMjNotIn(List<Object> values) {
            addCriterion("mj not in", values, "mj");
            return (Criteria) this;
        }

        public Criteria andMjBetween(Object value1, Object value2) {
            addCriterion("mj between", value1, value2, "mj");
            return (Criteria) this;
        }

        public Criteria andMjNotBetween(Object value1, Object value2) {
            addCriterion("mj not between", value1, value2, "mj");
            return (Criteria) this;
        }

        public Criteria andBgqxIsNull() {
            addCriterion("bgqx is null");
            return (Criteria) this;
        }

        public Criteria andBgqxIsNotNull() {
            addCriterion("bgqx is not null");
            return (Criteria) this;
        }

        public Criteria andBgqxEqualTo(Object value) {
            addCriterion("bgqx =", value, "bgqx");
            return (Criteria) this;
        }

        public Criteria andBgqxNotEqualTo(Object value) {
            addCriterion("bgqx <>", value, "bgqx");
            return (Criteria) this;
        }

        public Criteria andBgqxGreaterThan(Object value) {
            addCriterion("bgqx >", value, "bgqx");
            return (Criteria) this;
        }

        public Criteria andBgqxGreaterThanOrEqualTo(Object value) {
            addCriterion("bgqx >=", value, "bgqx");
            return (Criteria) this;
        }

        public Criteria andBgqxLessThan(Object value) {
            addCriterion("bgqx <", value, "bgqx");
            return (Criteria) this;
        }

        public Criteria andBgqxLessThanOrEqualTo(Object value) {
            addCriterion("bgqx <=", value, "bgqx");
            return (Criteria) this;
        }

        public Criteria andBgqxIn(List<Object> values) {
            addCriterion("bgqx in", values, "bgqx");
            return (Criteria) this;
        }

        public Criteria andBgqxNotIn(List<Object> values) {
            addCriterion("bgqx not in", values, "bgqx");
            return (Criteria) this;
        }

        public Criteria andBgqxBetween(Object value1, Object value2) {
            addCriterion("bgqx between", value1, value2, "bgqx");
            return (Criteria) this;
        }

        public Criteria andBgqxNotBetween(Object value1, Object value2) {
            addCriterion("bgqx not between", value1, value2, "bgqx");
            return (Criteria) this;
        }

        public Criteria andArcidIsNull() {
            addCriterion("arcid is null");
            return (Criteria) this;
        }

        public Criteria andArcidIsNotNull() {
            addCriterion("arcid is not null");
            return (Criteria) this;
        }

        public Criteria andArcidEqualTo(Long value) {
            addCriterion("arcid =", value, "arcid");
            return (Criteria) this;
        }

        public Criteria andArcidNotEqualTo(Long value) {
            addCriterion("arcid <>", value, "arcid");
            return (Criteria) this;
        }

        public Criteria andArcidGreaterThan(Long value) {
            addCriterion("arcid >", value, "arcid");
            return (Criteria) this;
        }

        public Criteria andArcidGreaterThanOrEqualTo(Long value) {
            addCriterion("arcid >=", value, "arcid");
            return (Criteria) this;
        }

        public Criteria andArcidLessThan(Long value) {
            addCriterion("arcid <", value, "arcid");
            return (Criteria) this;
        }

        public Criteria andArcidLessThanOrEqualTo(Long value) {
            addCriterion("arcid <=", value, "arcid");
            return (Criteria) this;
        }

        public Criteria andArcidIn(List<Long> values) {
            addCriterion("arcid in", values, "arcid");
            return (Criteria) this;
        }

        public Criteria andArcidNotIn(List<Long> values) {
            addCriterion("arcid not in", values, "arcid");
            return (Criteria) this;
        }

        public Criteria andArcidBetween(Long value1, Long value2) {
            addCriterion("arcid between", value1, value2, "arcid");
            return (Criteria) this;
        }

        public Criteria andArcidNotBetween(Long value1, Long value2) {
            addCriterion("arcid not between", value1, value2, "arcid");
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