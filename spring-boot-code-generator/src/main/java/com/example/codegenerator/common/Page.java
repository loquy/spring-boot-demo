package com.example.codegenerator.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author loquy
 */
public class Page<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private final int limit;

    @JsonIgnore
    private int offset;

    @JsonIgnore
    private final int currentPage;

    private List<T> result = new ArrayList<>();

    private int totalCount = 0;

    @JsonIgnore
    private String sortField;

    private int totalPage = 0;

    public Page(Integer page, Integer limit) {
        this.currentPage = page - 1;
        this.limit = limit;
    }

    public int getOffset() {
        if (offset == 0) {
            this.offset = currentPage * limit;
        }
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(final List<T> result) {
        this.result = result;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(final int totalCount) {
        this.totalCount = totalCount;
        this.totalPage = this.totalCount / this.limit;
        if (totalCount % limit > 0) {
            this.totalPage++;
        }
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    @JsonIgnore
    public String getOrderString() {
        String orderString = "";
        if (StringUtils.isNotBlank(sortField)) {
            orderString = " order by " + sortField;
        }
        return orderString;
    }
}
