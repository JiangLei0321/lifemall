package com.build123.lifemall.common.model;

import java.io.Serializable;

/**
 * Created by Administrator on 14-3-31.
 */
public class SharePageEntity extends BaseModel implements Serializable {

    private static final long serialVersionUID = -2231793745960218722L;
    /**不采用分页时，totalcount的默认值**/
    public static final Integer NOTSHARE_TOTALCOUNT=-1;
    /**分页存储总条数的key str**/
    public static final String SHAREPAGE_COUNT_STR="count";
    /**分页存储当前页列表集合key str**/
    public static final String SHAREPAGE_ITEMS_STR="items";


    /**mysql 分页起始位置**/
    private Integer index;
    /**mysql 分页数（页面大小）**/
    private Integer row;
    /**Mysql 分页的结束位置**/
    private Integer end;
    /**升序or降序**/
    private String orderSort;
    /**排序字段**/
    private String orderColumn;

    public static Integer getNotshareTotalcount() {
        return NOTSHARE_TOTALCOUNT;
    }

    public static String getSharepageCountStr() {
        return SHAREPAGE_COUNT_STR;
    }

    public static String getSharepageItemsStr() {
        return SHAREPAGE_ITEMS_STR;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public String getOrderSort() {
        return orderSort;
    }

    public void setOrderSort(String orderSort) {
        this.orderSort = orderSort;
    }

    public String getOrderColumn() {
        return orderColumn;
    }

    public void setOrderColumn(String orderColumn) {
        this.orderColumn = orderColumn;
    }
}
