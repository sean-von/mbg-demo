package com.smikevon.easy.common.utils;

import java.io.Serializable;

/**
 * Created by sean (smikevon@163.com) on 2018/8/21.
 */
public class PageParam implements Serializable {

    private static final long serialVersionUID = -9116744197723089288L;
    /**
     * 设定默认获取第一页
     */
    private Integer pageNum = 1;

    /**
     * 设定默认分页大小为 20
     */
    private Integer pageSize = 20;

    public PageParam() {
    }

    public Integer getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PageParam)) {
            return false;
        }
        final PageParam other = (PageParam) o;
        if (!other.canEqual((Object) this)) {
            return false;
        }
        final Object this$pageNum = this.getPageNum();
        final Object other$pageNum = other.getPageNum();
        if (this$pageNum == null ? other$pageNum != null : !this$pageNum.equals(other$pageNum)) {
            return false;
        }
        final Object this$pageSize = this.getPageSize();
        final Object other$pageSize = other.getPageSize();
        if (this$pageSize == null ? other$pageSize != null : !this$pageSize.equals(other$pageSize)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $pageNum = this.getPageNum();
        result = result * PRIME + ($pageNum == null ? 43 : $pageNum.hashCode());
        final Object $pageSize = this.getPageSize();
        result = result * PRIME + ($pageSize == null ? 43 : $pageSize.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof PageParam;
    }

    public String toString() {
        return "PageParam(pageNum=" + this.getPageNum() + ", pageSize=" + this.getPageSize() + ")";
    }
}
