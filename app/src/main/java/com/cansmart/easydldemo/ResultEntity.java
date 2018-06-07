package com.cansmart.easydldemo;

import java.util.List;

public class ResultEntity {

    /**
     * log_id : 12678262723648429019
     * results : [{"name":"shubiao","score":0.6137178540229797},{"name":"jianpan","score":0.38628214597702026}]
     */

    private String log_id;
    private List<ResultsBean> results;

    public String getLog_id() {
        return log_id;
    }

    public void setLog_id(String log_id) {
        this.log_id = log_id;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "ResultEntity{" +
                "log_id='" + log_id + '\'' +
                ", results=" + results +
                '}';
    }

    public static class ResultsBean {
        /**
         * name : shubiao
         * score : 0.6137178540229797
         */

        private String name;
        private double score;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }
    }
}
