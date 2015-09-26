package com.build123.lifemall.common.util;

/**
 * Created by Administrator on 04.02.15.
 */
public class ConstantUtil {
    /**
     * Leancloud 用户名和密码登陆 url
     */
    public static final String leancloud_login_url = "https://leancloud.cn/1.1/login";

    /**
     * Leancloud 根据id查询用户
     */
    public static final String leancloud_check_id = " https://leancloud.cn/1.1/users";

    /**
     * Leancloud 修改密码url
     */
    public static final String leancloud_set_psd = "https://leancloud.cn/1.1/users/OBJECTID/updatePassword";

    /**
     * Leancloud 图片上传地址url
     */
    public static final String leancloud_upload_img = "https://leancloud.cn/1.1/files/IMGAGENAME";


    /**
     * Leancloud 修改用户信息url
     */
    public static final String leancloud_updata_msg = "https://leancloud.cn/1.1/users/USERID";


}