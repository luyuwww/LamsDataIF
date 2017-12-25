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

        public Criteria andPidIsNull() {
            addCriterion("pid is null");
            return (Criteria) this;
        }

        public Criteria andPidIsNotNull() {
            addCriterion("pid is not null");
            return (Criteria) this;
        }

        public Criteria andPidEqualTo(String value) {
            addCriterion("pid =", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotEqualTo(String value) {
            addCriterion("pid <>", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidGreaterThan(String value) {
            addCriterion("pid >", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidGreaterThanOrEqualTo(String value) {
            addCriterion("pid >=", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLessThan(String value) {
            addCriterion("pid <", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLessThanOrEqualTo(String value) {
            addCriterion("pid <=", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLike(String value) {
            addCriterion("pid like", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotLike(String value) {
            addCriterion("pid not like", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidIn(List<String> values) {
            addCriterion("pid in", values, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotIn(List<String> values) {
            addCriterion("pid not in", values, "pid");
            return (Criteria) this;
        }

        public Criteria andPidBetween(String value1, String value2) {
            addCriterion("pid between", value1, value2, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotBetween(String value1, String value2) {
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

        public Criteria andTablenameEqualTo(String value) {
            addCriterion("tablename =", value, "tablename");
            return (Criteria) this;
        }

        public Criteria andTablenameNotEqualTo(String value) {
            addCriterion("tablename <>", value, "tablename");
            return (Criteria) this;
        }

        public Criteria andTablenameGreaterThan(String value) {
            addCriterion("tablename >", value, "tablename");
            return (Criteria) this;
        }

        public Criteria andTablenameGreaterThanOrEqualTo(String value) {
            addCriterion("tablename >=", value, "tablename");
            return (Criteria) this;
        }

        public Criteria andTablenameLessThan(String value) {
            addCriterion("tablename <", value, "tablename");
            return (Criteria) this;
        }

        public Criteria andTablenameLessThanOrEqualTo(String value) {
            addCriterion("tablename <=", value, "tablename");
            return (Criteria) this;
        }

        public Criteria andTablenameLike(String value) {
            addCriterion("tablename like", value, "tablename");
            return (Criteria) this;
        }

        public Criteria andTablenameNotLike(String value) {
            addCriterion("tablename not like", value, "tablename");
            return (Criteria) this;
        }

        public Criteria andTablenameIn(List<String> values) {
            addCriterion("tablename in", values, "tablename");
            return (Criteria) this;
        }

        public Criteria andTablenameNotIn(List<String> values) {
            addCriterion("tablename not in", values, "tablename");
            return (Criteria) this;
        }

        public Criteria andTablenameBetween(String value1, String value2) {
            addCriterion("tablename between", value1, value2, "tablename");
            return (Criteria) this;
        }

        public Criteria andTablenameNotBetween(String value1, String value2) {
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

        public Criteria andKeywordEqualTo(String value) {
            addCriterion("keyword =", value, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordNotEqualTo(String value) {
            addCriterion("keyword <>", value, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordGreaterThan(String value) {
            addCriterion("keyword >", value, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordGreaterThanOrEqualTo(String value) {
            addCriterion("keyword >=", value, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordLessThan(String value) {
            addCriterion("keyword <", value, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordLessThanOrEqualTo(String value) {
            addCriterion("keyword <=", value, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordLike(String value) {
            addCriterion("keyword like", value, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordNotLike(String value) {
            addCriterion("keyword not like", value, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordIn(List<String> values) {
            addCriterion("keyword in", values, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordNotIn(List<String> values) {
            addCriterion("keyword not in", values, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordBetween(String value1, String value2) {
            addCriterion("keyword between", value1, value2, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordNotBetween(String value1, String value2) {
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

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
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

        public Criteria andMjEqualTo(String value) {
            addCriterion("mj =", value, "mj");
            return (Criteria) this;
        }

        public Criteria andMjNotEqualTo(String value) {
            addCriterion("mj <>", value, "mj");
            return (Criteria) this;
        }

        public Criteria andMjGreaterThan(String value) {
            addCriterion("mj >", value, "mj");
            return (Criteria) this;
        }

        public Criteria andMjGreaterThanOrEqualTo(String value) {
            addCriterion("mj >=", value, "mj");
            return (Criteria) this;
        }

        public Criteria andMjLessThan(String value) {
            addCriterion("mj <", value, "mj");
            return (Criteria) this;
        }

        public Criteria andMjLessThanOrEqualTo(String value) {
            addCriterion("mj <=", value, "mj");
            return (Criteria) this;
        }

        public Criteria andMjLike(String value) {
            addCriterion("mj like", value, "mj");
            return (Criteria) this;
        }

        public Criteria andMjNotLike(String value) {
            addCriterion("mj not like", value, "mj");
            return (Criteria) this;
        }

        public Criteria andMjIn(List<String> values) {
            addCriterion("mj in", values, "mj");
            return (Criteria) this;
        }

        public Criteria andMjNotIn(List<String> values) {
            addCriterion("mj not in", values, "mj");
            return (Criteria) this;
        }

        public Criteria andMjBetween(String value1, String value2) {
            addCriterion("mj between", value1, value2, "mj");
            return (Criteria) this;
        }

        public Criteria andMjNotBetween(String value1, String value2) {
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

        public Criteria andBgqxEqualTo(String value) {
            addCriterion("bgqx =", value, "bgqx");
            return (Criteria) this;
        }

        public Criteria andBgqxNotEqualTo(String value) {
            addCriterion("bgqx <>", value, "bgqx");
            return (Criteria) this;
        }

        public Criteria andBgqxGreaterThan(String value) {
            addCriterion("bgqx >", value, "bgqx");
            return (Criteria) this;
        }

        public Criteria andBgqxGreaterThanOrEqualTo(String value) {
            addCriterion("bgqx >=", value, "bgqx");
            return (Criteria) this;
        }

        public Criteria andBgqxLessThan(String value) {
            addCriterion("bgqx <", value, "bgqx");
            return (Criteria) this;
        }

        public Criteria andBgqxLessThanOrEqualTo(String value) {
            addCriterion("bgqx <=", value, "bgqx");
            return (Criteria) this;
        }

        public Criteria andBgqxLike(String value) {
            addCriterion("bgqx like", value, "bgqx");
            return (Criteria) this;
        }

        public Criteria andBgqxNotLike(String value) {
            addCriterion("bgqx not like", value, "bgqx");
            return (Criteria) this;
        }

        public Criteria andBgqxIn(List<String> values) {
            addCriterion("bgqx in", values, "bgqx");
            return (Criteria) this;
        }

        public Criteria andBgqxNotIn(List<String> values) {
            addCriterion("bgqx not in", values, "bgqx");
            return (Criteria) this;
        }

        public Criteria andBgqxBetween(String value1, String value2) {
            addCriterion("bgqx between", value1, value2, "bgqx");
            return (Criteria) this;
        }

        public Criteria andBgqxNotBetween(String value1, String value2) {
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

        public Criteria andArcidEqualTo(Integer value) {
            addCriterion("arcid =", value, "arcid");
            return (Criteria) this;
        }

        public Criteria andArcidNotEqualTo(Integer value) {
            addCriterion("arcid <>", value, "arcid");
            return (Criteria) this;
        }

        public Criteria andArcidGreaterThan(Integer value) {
            addCriterion("arcid >", value, "arcid");
            return (Criteria) this;
        }

        public Criteria andArcidGreaterThanOrEqualTo(Integer value) {
            addCriterion("arcid >=", value, "arcid");
            return (Criteria) this;
        }

        public Criteria andArcidLessThan(Integer value) {
            addCriterion("arcid <", value, "arcid");
            return (Criteria) this;
        }

        public Criteria andArcidLessThanOrEqualTo(Integer value) {
            addCriterion("arcid <=", value, "arcid");
            return (Criteria) this;
        }

        public Criteria andArcidIn(List<Integer> values) {
            addCriterion("arcid in", values, "arcid");
            return (Criteria) this;
        }

        public Criteria andArcidNotIn(List<Integer> values) {
            addCriterion("arcid not in", values, "arcid");
            return (Criteria) this;
        }

        public Criteria andArcidBetween(Integer value1, Integer value2) {
            addCriterion("arcid between", value1, value2, "arcid");
            return (Criteria) this;
        }

        public Criteria andArcidNotBetween(Integer value1, Integer value2) {
            addCriterion("arcid not between", value1, value2, "arcid");
            return (Criteria) this;
        }

        public Criteria andIdLikeInsensitive(String value) {
            addCriterion("upper(id) like", value.toUpperCase(), "id");
            return (Criteria) this;
        }

        public Criteria andPidLikeInsensitive(String value) {
            addCriterion("upper(pid) like", value.toUpperCase(), "pid");
            return (Criteria) this;
        }

        public Criteria andTablenameLikeInsensitive(String value) {
            addCriterion("upper(tablename) like", value.toUpperCase(), "tablename");
            return (Criteria) this;
        }

        public Criteria andKeywordLikeInsensitive(String value) {
            addCriterion("upper(keyword) like", value.toUpperCase(), "keyword");
            return (Criteria) this;
        }

        public Criteria andTitleLikeInsensitive(String value) {
            addCriterion("upper(title) like", value.toUpperCase(), "title");
            return (Criteria) this;
        }

        public Criteria andMjLikeInsensitive(String value) {
            addCriterion("upper(mj) like", value.toUpperCase(), "mj");
            return (Criteria) this;
        }

        public Criteria andBgqxLikeInsensitive(String value) {
            addCriterion("upper(bgqx) like", value.toUpperCase(), "bgqx");
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