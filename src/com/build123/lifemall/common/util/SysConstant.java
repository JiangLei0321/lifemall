package com.build123.lifemall.common.util;

/**
 * 系统常量
 * @author ydd
 *
 */
public class SysConstant {

    /** UUID静态常量 **/
    public static final String UUID = "uuid";

	/*-----------------------商城用户------------------------*/
	/**注销状态**/
	public static final Integer STATUS_DISABLE=0;
	/**激活**/
	public static final Integer STATUS_ACTIVATION=1;
	
	
	/*-----------------------商城购物车------------------------*/
	/**商城购物车状态 已购买：1**/
	public static final Integer SHOPPINGCART_STATUS_BUY=1;
	/**商城购物车状态 未购买：0**/
	public static final Integer SHOPPINGCART_STATUS_NOTBUY=0;

    /*-----------------------商品三大活动类型------------------------*/
    /** 商品轮播活动 **/
    public static final Integer PRODUCT_BROADCASTING_ACTIVITY=1;
    /** 商品固定活动 **/
    public static final Integer PRODUCT_FIXED_ACTIVITY=2;
    /** 商品编辑精选 **/
    public static final Integer PRODUCT_PICK_ACTIVITY=3;

    /*-----------------------添加操作数字常量------------------------*/
    /** 添加成功 **/
    public static final Integer SUCCESS_ADD=1;
    /** 重复添加 **/
    public static final Integer REPEAT_ADD=0;
    /** 添加失败，由于系统故障或者网络原因 **/
    public static final Integer FAIL_ADD=-1;

    /*-----------------------删除操作数字常量------------------------*/
    /** 删除成功 **/
    public static final Integer SUCCESS_DELETE=1;
    /** 删除失败，由于系统故障或者网络原因 **/
    public static final Integer FAIL_DELETE=-1;

    /*-----------------------修改操作数字常量------------------------*/
    /** 删除成功 **/
    public static final Integer SUCCESS_MODIFY=1;
    /** 删除失败，由于系统故障或者网络原因 **/
    public static final Integer FAIL_MODIFY=-1;
	
	/**
	 * 获取订单状态描述
	 */
	public static String getStatusDesc(int status) {
		String statusDesc = null;
		switch (status) {
			case 0 :
				statusDesc = "未接收";
				break;
			case 1 :
				statusDesc = "已接收";
				break;
			case 2 :
				statusDesc = "已发出";
				break;
			case 3 :
				statusDesc = "完结";
				break;
			case 4 :
				statusDesc = "未付款";
				break;
			case 5 :
				statusDesc = "已付款";
				break;
			default :
				statusDesc = "未定义";
				break;
		}
		return statusDesc;
	}
	
	public static String getCountryDesc(int countryId) {
		String countryDesc = null;
		switch (countryId) {
			case 1 :
				countryDesc = "美国";
				break;
			case 7 :
				countryDesc = "俄罗斯";
				break;
			case 33 :
				countryDesc = "法国";
				break;
			case 39 :
				countryDesc = "意大利";
				break;
			case 44 :
				countryDesc = "英国";
				break;
			case 49 :
				countryDesc = "德国";
				break;
			case 81 :
				countryDesc = "日本";
				break;
			case 82 :
				countryDesc = "韩国";
				break;
			case 86 :
				countryDesc = "中国";
				break;
			case 852 :
				countryDesc = "中国香港";
				break;
			case 853 :
				countryDesc = "中国澳门";
				break;
			case 886 :
				countryDesc = "中国台湾";
				break;
			default :
				countryDesc = "未定义";
				break;
		}
		return countryDesc;
	}

    /**
     * 定义状态枚举
     */
    public enum Status{

        UNPUBLISHED(0,"未发布"),
        PUBLISHED(1,"发布"),
        DELETED(-1,"删除");

        private Integer value;
        private String name;

        Status(Integer value,String name){
            this.value = value;
            this.name = name;
        }

        public static String getName(Integer value) {
            for (Status status : Status.values()) {
                if (status.getValue() == value) {
                    return status.name;
                }
            }
            return null;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }
    }
}
