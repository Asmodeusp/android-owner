package com.saimawzc.shipper.weight.utils.preference;

/**
 * @Description: 用于存放Preference的Key
 */
public class PreferenceKey {



    public static final String ID = "id";
    /**
     * android系统版本是否低于4.4
     * 是则返回true
     */
    public static final String VERSION = "version";

    /**
     * ActionBar和StatusBar的高度和
     */
    public static final String BAR_HIGHT = "bar_hight";

    /**
     * StatusBar样式设置是否有效 有效=true 无效=false
     */
    public static final String FLAG_STATUSBAR_SET = "Flag_StatusBar_Set";
    public static final String PERSON_CENTER = "personcenter";

    public static final String USER_INFO = "user_info";
    public static final String CITY_INFO = "city_info";
    public static final String IS_TUOYUN = "istuoyungognsi";

    public static final String READ_PRIVACY = "privacy";//是否已经阅读隐私政策
    public static final String USER_ACCOUNT = "account";
    public static final String PASS_WORD = "pass_word";
    public static final String ISREMEMBER_PASS = "is_remember_pass";//1已经记住

    public static final String AuthorID = "AuthorID";
    public static final String Editor = "Editor";

    public static final String HZ_IS_RZ = "hz_is_rz";//货主是否认证  1为已经认证

    public static final String isChange_or_login = "isChange_or_login_hz";//



}
