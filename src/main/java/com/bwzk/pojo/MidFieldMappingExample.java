package com.bwzk.pojo;

import java.util.ArrayList;
import java.util.List;

public class MidFieldMappingExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table P_Z430
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table P_Z430
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table P_Z430
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table P_Z430
     *
     * @mbggenerated
     */
    public MidFieldMappingExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table P_Z430
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table P_Z430
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table P_Z430
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table P_Z430
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table P_Z430
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table P_Z430
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table P_Z430
     *
     * @mbggenerated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table P_Z430
     *
     * @mbggenerated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table P_Z430
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table P_Z430
     *
     * @mbggenerated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table P_Z430
     *
     * @mbggenerated
     */
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

        public Criteria andDidIsNull() {
            addCriterion("DID is null");
            return (Criteria) this;
        }

        public Criteria andDidIsNotNull() {
            addCriterion("DID is not null");
            return (Criteria) this;
        }

        public Criteria andDidEqualTo(Integer value) {
            addCriterion("DID =", value, "did");
            return (Criteria) this;
        }

        public Criteria andDidNotEqualTo(Integer value) {
            addCriterion("DID <>", value, "did");
            return (Criteria) this;
        }

        public Criteria andDidGreaterThan(Integer value) {
            addCriterion("DID >", value, "did");
            return (Criteria) this;
        }

        public Criteria andDidGreaterThanOrEqualTo(Integer value) {
            addCriterion("DID >=", value, "did");
            return (Criteria) this;
        }

        public Criteria andDidLessThan(Integer value) {
            addCriterion("DID <", value, "did");
            return (Criteria) this;
        }

        public Criteria andDidLessThanOrEqualTo(Integer value) {
            addCriterion("DID <=", value, "did");
            return (Criteria) this;
        }

        public Criteria andDidIn(List<Integer> values) {
            addCriterion("DID in", values, "did");
            return (Criteria) this;
        }

        public Criteria andDidNotIn(List<Integer> values) {
            addCriterion("DID not in", values, "did");
            return (Criteria) this;
        }

        public Criteria andDidBetween(Integer value1, Integer value2) {
            addCriterion("DID between", value1, value2, "did");
            return (Criteria) this;
        }

        public Criteria andDidNotBetween(Integer value1, Integer value2) {
            addCriterion("DID not between", value1, value2, "did");
            return (Criteria) this;
        }

        public Criteria andPpidIsNull() {
            addCriterion("PPID is null");
            return (Criteria) this;
        }

        public Criteria andPpidIsNotNull() {
            addCriterion("PPID is not null");
            return (Criteria) this;
        }

        public Criteria andPpidEqualTo(Integer value) {
            addCriterion("PPID =", value, "ppid");
            return (Criteria) this;
        }

        public Criteria andPpidNotEqualTo(Integer value) {
            addCriterion("PPID <>", value, "ppid");
            return (Criteria) this;
        }

        public Criteria andPpidGreaterThan(Integer value) {
            addCriterion("PPID >", value, "ppid");
            return (Criteria) this;
        }

        public Criteria andPpidGreaterThanOrEqualTo(Integer value) {
            addCriterion("PPID >=", value, "ppid");
            return (Criteria) this;
        }

        public Criteria andPpidLessThan(Integer value) {
            addCriterion("PPID <", value, "ppid");
            return (Criteria) this;
        }

        public Criteria andPpidLessThanOrEqualTo(Integer value) {
            addCriterion("PPID <=", value, "ppid");
            return (Criteria) this;
        }

        public Criteria andPpidIn(List<Integer> values) {
            addCriterion("PPID in", values, "ppid");
            return (Criteria) this;
        }

        public Criteria andPpidNotIn(List<Integer> values) {
            addCriterion("PPID not in", values, "ppid");
            return (Criteria) this;
        }

        public Criteria andPpidBetween(Integer value1, Integer value2) {
            addCriterion("PPID between", value1, value2, "ppid");
            return (Criteria) this;
        }

        public Criteria andPpidNotBetween(Integer value1, Integer value2) {
            addCriterion("PPID not between", value1, value2, "ppid");
            return (Criteria) this;
        }

        public Criteria andPidIsNull() {
            addCriterion("PID is null");
            return (Criteria) this;
        }

        public Criteria andPidIsNotNull() {
            addCriterion("PID is not null");
            return (Criteria) this;
        }

        public Criteria andPidEqualTo(Integer value) {
            addCriterion("PID =", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotEqualTo(Integer value) {
            addCriterion("PID <>", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidGreaterThan(Integer value) {
            addCriterion("PID >", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidGreaterThanOrEqualTo(Integer value) {
            addCriterion("PID >=", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLessThan(Integer value) {
            addCriterion("PID <", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLessThanOrEqualTo(Integer value) {
            addCriterion("PID <=", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidIn(List<Integer> values) {
            addCriterion("PID in", values, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotIn(List<Integer> values) {
            addCriterion("PID not in", values, "pid");
            return (Criteria) this;
        }

        public Criteria andPidBetween(Integer value1, Integer value2) {
            addCriterion("PID between", value1, value2, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotBetween(Integer value1, Integer value2) {
            addCriterion("PID not between", value1, value2, "pid");
            return (Criteria) this;
        }

        public Criteria andSfieldIsNull() {
            addCriterion("SFIELD is null");
            return (Criteria) this;
        }

        public Criteria andSfieldIsNotNull() {
            addCriterion("SFIELD is not null");
            return (Criteria) this;
        }

        public Criteria andSfieldEqualTo(String value) {
            addCriterion("SFIELD =", value, "sfield");
            return (Criteria) this;
        }

        public Criteria andSfieldNotEqualTo(String value) {
            addCriterion("SFIELD <>", value, "sfield");
            return (Criteria) this;
        }

        public Criteria andSfieldGreaterThan(String value) {
            addCriterion("SFIELD >", value, "sfield");
            return (Criteria) this;
        }

        public Criteria andSfieldGreaterThanOrEqualTo(String value) {
            addCriterion("SFIELD >=", value, "sfield");
            return (Criteria) this;
        }

        public Criteria andSfieldLessThan(String value) {
            addCriterion("SFIELD <", value, "sfield");
            return (Criteria) this;
        }

        public Criteria andSfieldLessThanOrEqualTo(String value) {
            addCriterion("SFIELD <=", value, "sfield");
            return (Criteria) this;
        }

        public Criteria andSfieldLike(String value) {
            addCriterion("SFIELD like", value, "sfield");
            return (Criteria) this;
        }

        public Criteria andSfieldNotLike(String value) {
            addCriterion("SFIELD not like", value, "sfield");
            return (Criteria) this;
        }

        public Criteria andSfieldIn(List<String> values) {
            addCriterion("SFIELD in", values, "sfield");
            return (Criteria) this;
        }

        public Criteria andSfieldNotIn(List<String> values) {
            addCriterion("SFIELD not in", values, "sfield");
            return (Criteria) this;
        }

        public Criteria andSfieldBetween(String value1, String value2) {
            addCriterion("SFIELD between", value1, value2, "sfield");
            return (Criteria) this;
        }

        public Criteria andSfieldNotBetween(String value1, String value2) {
            addCriterion("SFIELD not between", value1, value2, "sfield");
            return (Criteria) this;
        }

        public Criteria andTfieldIsNull() {
            addCriterion("TFIELD is null");
            return (Criteria) this;
        }

        public Criteria andTfieldIsNotNull() {
            addCriterion("TFIELD is not null");
            return (Criteria) this;
        }

        public Criteria andTfieldEqualTo(String value) {
            addCriterion("TFIELD =", value, "tfield");
            return (Criteria) this;
        }

        public Criteria andTfieldNotEqualTo(String value) {
            addCriterion("TFIELD <>", value, "tfield");
            return (Criteria) this;
        }

        public Criteria andTfieldGreaterThan(String value) {
            addCriterion("TFIELD >", value, "tfield");
            return (Criteria) this;
        }

        public Criteria andTfieldGreaterThanOrEqualTo(String value) {
            addCriterion("TFIELD >=", value, "tfield");
            return (Criteria) this;
        }

        public Criteria andTfieldLessThan(String value) {
            addCriterion("TFIELD <", value, "tfield");
            return (Criteria) this;
        }

        public Criteria andTfieldLessThanOrEqualTo(String value) {
            addCriterion("TFIELD <=", value, "tfield");
            return (Criteria) this;
        }

        public Criteria andTfieldLike(String value) {
            addCriterion("TFIELD like", value, "tfield");
            return (Criteria) this;
        }

        public Criteria andTfieldNotLike(String value) {
            addCriterion("TFIELD not like", value, "tfield");
            return (Criteria) this;
        }

        public Criteria andTfieldIn(List<String> values) {
            addCriterion("TFIELD in", values, "tfield");
            return (Criteria) this;
        }

        public Criteria andTfieldNotIn(List<String> values) {
            addCriterion("TFIELD not in", values, "tfield");
            return (Criteria) this;
        }

        public Criteria andTfieldBetween(String value1, String value2) {
            addCriterion("TFIELD between", value1, value2, "tfield");
            return (Criteria) this;
        }

        public Criteria andTfieldNotBetween(String value1, String value2) {
            addCriterion("TFIELD not between", value1, value2, "tfield");
            return (Criteria) this;
        }

        public Criteria andDefaultvalueIsNull() {
            addCriterion("DEFAULTVALUE is null");
            return (Criteria) this;
        }

        public Criteria andDefaultvalueIsNotNull() {
            addCriterion("DEFAULTVALUE is not null");
            return (Criteria) this;
        }

        public Criteria andDefaultvalueEqualTo(String value) {
            addCriterion("DEFAULTVALUE =", value, "defaultvalue");
            return (Criteria) this;
        }

        public Criteria andDefaultvalueNotEqualTo(String value) {
            addCriterion("DEFAULTVALUE <>", value, "defaultvalue");
            return (Criteria) this;
        }

        public Criteria andDefaultvalueGreaterThan(String value) {
            addCriterion("DEFAULTVALUE >", value, "defaultvalue");
            return (Criteria) this;
        }

        public Criteria andDefaultvalueGreaterThanOrEqualTo(String value) {
            addCriterion("DEFAULTVALUE >=", value, "defaultvalue");
            return (Criteria) this;
        }

        public Criteria andDefaultvalueLessThan(String value) {
            addCriterion("DEFAULTVALUE <", value, "defaultvalue");
            return (Criteria) this;
        }

        public Criteria andDefaultvalueLessThanOrEqualTo(String value) {
            addCriterion("DEFAULTVALUE <=", value, "defaultvalue");
            return (Criteria) this;
        }

        public Criteria andDefaultvalueLike(String value) {
            addCriterion("DEFAULTVALUE like", value, "defaultvalue");
            return (Criteria) this;
        }

        public Criteria andDefaultvalueNotLike(String value) {
            addCriterion("DEFAULTVALUE not like", value, "defaultvalue");
            return (Criteria) this;
        }

        public Criteria andDefaultvalueIn(List<String> values) {
            addCriterion("DEFAULTVALUE in", values, "defaultvalue");
            return (Criteria) this;
        }

        public Criteria andDefaultvalueNotIn(List<String> values) {
            addCriterion("DEFAULTVALUE not in", values, "defaultvalue");
            return (Criteria) this;
        }

        public Criteria andDefaultvalueBetween(String value1, String value2) {
            addCriterion("DEFAULTVALUE between", value1, value2, "defaultvalue");
            return (Criteria) this;
        }

        public Criteria andDefaultvalueNotBetween(String value1, String value2) {
            addCriterion("DEFAULTVALUE not between", value1, value2, "defaultvalue");
            return (Criteria) this;
        }

        public Criteria andBzIsNull() {
            addCriterion("BZ is null");
            return (Criteria) this;
        }

        public Criteria andBzIsNotNull() {
            addCriterion("BZ is not null");
            return (Criteria) this;
        }

        public Criteria andBzEqualTo(String value) {
            addCriterion("BZ =", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzNotEqualTo(String value) {
            addCriterion("BZ <>", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzGreaterThan(String value) {
            addCriterion("BZ >", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzGreaterThanOrEqualTo(String value) {
            addCriterion("BZ >=", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzLessThan(String value) {
            addCriterion("BZ <", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzLessThanOrEqualTo(String value) {
            addCriterion("BZ <=", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzLike(String value) {
            addCriterion("BZ like", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzNotLike(String value) {
            addCriterion("BZ not like", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzIn(List<String> values) {
            addCriterion("BZ in", values, "bz");
            return (Criteria) this;
        }

        public Criteria andBzNotIn(List<String> values) {
            addCriterion("BZ not in", values, "bz");
            return (Criteria) this;
        }

        public Criteria andBzBetween(String value1, String value2) {
            addCriterion("BZ between", value1, value2, "bz");
            return (Criteria) this;
        }

        public Criteria andBzNotBetween(String value1, String value2) {
            addCriterion("BZ not between", value1, value2, "bz");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table P_Z430
     *
     * @mbggenerated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table P_Z430
     *
     * @mbggenerated
     */
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