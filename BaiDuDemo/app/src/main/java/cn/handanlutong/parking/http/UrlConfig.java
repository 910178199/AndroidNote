package cn.handanlutong.parking.http;

/**
 * 接口请求地址管理
 * Created by zhangyonggang on 2017/6/7.
 */

public class UrlConfig {
    // *******************************测试环境地址*************************//

    public static final String BASE_URL = "http://119.23.223.134:6411/ttpark-project/project/findPath";//总接口地址

//    public static final String ROOTS_URL = "http://39.108.63.245:6477/ttpark-user/"; //正式环境
//    public static final String PAYS_URL = "http://112.74.170.188:6488/ttpark-pay/"; //正式环境支付

//    public static final String ROOTS_URL = "http://39.108.6.245:6477/ttpark-user/"; //正式环境
//    public static final String PAYS_URL = "http://112.74.170.188:6588/ttpark-pay/"; //正式环境支付2

//    public static final String ROOTS_URL = "http://39.108.63.245:6577/ttpark-user/";//正式环境3
//    public static final String PAYS_URL = "http://112.74.170.188:6388/ttpark-pay/"; //正式环境支付3

    public static final String ROOTS_URL = "http://39.108.63.245:6577/ttpark-user/"; //正式环境4
    public static final String PAYS_URL = "http://112.74.170.188:6588/ttpark-pay/"; //正式环境支付2

//    public static final String ROOTS_URL = "http://120.77.202.83:6477/ttpark-user/"; //测试环境
//    public static final String PAYS_URL = "http://120.77.202.83:6488/ttpark-pay/"; //测试环境支付


//    public static final String ROOTS_URL = "http://10.0.12.152:8080/ttpark-user/"; //本地环境
//    public static final String PAYS_URL = "http://10.0.7.41:8080/ttpark-pay/"; //本地环境

    // *******************************具体接口地址*************************//
    public static final String VERIFICATIONCODE_LOGIN_URL = ROOTS_URL + "UserLogin/login";//手机验证码登录
    public static final String GETPASSCODE_URL = ROOTS_URL + "UserLogin/sms";//获取手机验证码
    public static final String CAR_ADD_URL = ROOTS_URL + "Car/addUserHPHM";//添加车辆
    public static final String FIRSTPAGE_IMAGE_URL = ROOTS_URL + "style/findBanner";//获取轮播图片
    public static final String CAR_GETLIST_URL = ROOTS_URL + "Car/findMyCar";//获取车辆列表
    public static final String CAR_DELETE_URL = ROOTS_URL + "Car/removeUserHPHM";//删除车辆
    public static final String CAR_ISAUTOPAY_URL = ROOTS_URL + "Car/setUserZDZF";//自动支付
    public static final String CAR_BILL_RECORD_URL = ROOTS_URL + "ParkRecord/okParkRecord";//获取账单记录
    public static final String CAR_BILL_RECORD_DETAIL_URL = ROOTS_URL + "ParkRecord/parkRecordInfo";//获取账单/订单详情
    public static final String CAR_ORDER_RECORD_URL = ROOTS_URL + "ParkRecord/notParkRecord";//我的订单
    public static final String GENERATE_ORDER_URL = ROOTS_URL + "ParkBill/FindParkBillInfo";//生成订单
    public static final String UPDATE_NAME_URL = ROOTS_URL + "User/updateName";//修改昵称
    public static final String UPDATE_SEX_URL = ROOTS_URL + "User/updateSex";//修改用户性别
    public static final String UPDATE_AGE_URL = ROOTS_URL + "User/updateAge";//修改用户年龄
    public static final String UPLOADPHOTO_URL = ROOTS_URL + "User/updateImage";//上传头像
    public static final String APPBALANCE_GETTOTAL_URL = ROOTS_URL + "UserWallet/findWalletInfo";//获取账户余额
    public static final String APPBALANCE_GETRECHARGE_URL = ROOTS_URL + "czfx/findCzfx";//获取充值返现列表
    public static final String GET_GUI_ZE_URL = ROOTS_URL + "activityInfo/findCZFXInfo";//获取充值返现列表中的充值返现规则
    public static final String GET_GUI_ZE_CZZS_URL = ROOTS_URL + "activityInfo/findCZZSInfo";//获取充值返现列表中的充值赠送规则
    public static final String RECHARGE_PAYIN_URL = ROOTS_URL + "UserWallet/userPayIn";//获取充值明细
    public static final String RECHARGE_PAYOUT_URL = ROOTS_URL + "UserWallet/userPayOut";//获取消费明细
    public static final String VERSION_UPDATE_URL = ROOTS_URL + "AppVersion/version";//检测版本是否更新
    public static final String USER_GETACTIONLIST_URL = ROOTS_URL + "activity/findActivity";//活动列表
    public static final String USER_SUBMITEYIJINA_URL = ROOTS_URL + "MessageInfo/save";//提交反馈意见
    public static final String USER_JPUSH_URL = ROOTS_URL + "UserLogin/JPush";//绑定极光推送
    public static final String USER_ADVERTISING_URL = ROOTS_URL + "style/findGGY";//广告页
    public static final String Car_Vehiclecard_URL = ROOTS_URL + "Car/vehiclecard";//车辆自动认证 审核
    public static final String Car_Vehiclecard2_URL = ROOTS_URL + "Car/back";//车辆找回认证 审核
    public static final String Commit_FaPiao_Message_URL = ROOTS_URL + "parkFP/addParkFp";//发票信息提交
    public static final String GET_FaPiao_History_URL = ROOTS_URL + "parkFP/findParkFpList";//获取 开发票历史记录
    public static final String GET_My_News_URL = ROOTS_URL + "MessageUserInfo/findInfoList";//获取 我的消息列表
    public static final String GET_Msg_Sys_URL = ROOTS_URL + "MessageUserInfo/findInfoTop";//获取 我的消息模块 所有内容
    public static final String USER_YHQ_URL = ROOTS_URL + "couponReceive/findMyCoupon";//获取用户优惠券列表
    public static final String USER_Mobile_jiance_URL = ROOTS_URL + "couponReceive/cehckIphone";//校验用户手机号
    public static final String USER_LQ_YHQ_URL = ROOTS_URL + "couponReceive/couponReceive";//用户领取优惠券
    public static final String YHQDuiHuan_URL = ROOTS_URL + "couponReceive/couponByXLH";//优惠券兑换
    public static final String GET_YHQ_GUI_ZE_URL = ROOTS_URL + "activityInfo/findCouponInfo";//获取优惠券列表中的规则
    public static final String GET_INVITED_FRIEND_URL = ROOTS_URL + "activityInfo/findShareInfo";//获取分享好友的url
    public static final String Update_USER_Phone_URL = ROOTS_URL + "User/updatePhoneNoCheck";//是否在30天内可以 修改用户手机号
    public static final String Update_USER_Phone_SMS_URL = ROOTS_URL + "User/updatePhoneNoSMS";//修改用户手机号 获取验证码
    public static final String Update_USER_Phone_Success_URL = ROOTS_URL + "User/updatePhoneNo";//修改用户手机号
    public static final String NEED_HELP_URL = ROOTS_URL + "ParkingService/findNumber";//需要帮助按钮
    public static final String USER_Month_Card_URL = ROOTS_URL + "ParkingCard/findCardLogList";//月卡数量
    public static final String USER_Month_Card_Buy_URL = ROOTS_URL + "ParkingCard/checkCard";//去购买月卡
    public static final String USER_Month_Card_Zhi_Fu_URL = ROOTS_URL + "ParkingCard/cardOrder";//去购买月卡 支付
    public static final String USER_Month_Card_And_Car_URL = ROOTS_URL + "ParkingCard/findCardList";//查询已配置月卡和车辆
    // 首页 搜索附近停车场
    public static final String Car_Place_TuiJian_URL = ROOTS_URL + "ParkGarage/findMyGarage";
    //百度地图 标记 marker 查询500米内停车场
    public static final String markUrl_URL = ROOTS_URL + "ParkGarage/findGarage";
    //车场收费标准
    public static final String CCsf_URL = ROOTS_URL + "ParkGarage/findGarageChargeDetail";
    public static final String GET_TROUBLE_LIST_URL = ROOTS_URL + "ParkingService/findTypeList";//客服帮助查找问题列表
    public static final String GET_SERVICE_ADDINFO_URL = ROOTS_URL + "ParkingService/addServiceInfo";//客服帮助提交问题

    public static final String MYORDER_PAY_URL = PAYS_URL + "userWallet/userWalletPay";//确认支付(钱包支付)
    public static final String MYWALLET_ALIPAY_URL = PAYS_URL + "aliPay/getWalletPrepayId";//支付宝充值
    public static final String MYWALLET_WECHATPAY_URL = PAYS_URL + "wxPay/getWalletPrepayId";//微信充值
    public static final String USER_wx_URL = PAYS_URL + "wxPay/getPrepayId";//用户微信支付
    public static final String USER_zhifubao_URL = PAYS_URL + "aliPay/getPrepayId";//用户支付宝支付
    public static final String USER_wx_Month_Card_URL = PAYS_URL + "wxPay/getParkingCardPrepayId";//用户微信支付月卡
    public static final String USER_zhifubao_Month_Card_URL = PAYS_URL + "aliPay/getParkingCardPrepayId";//用户支付宝支付月卡
    public static final String STOP_CAR_TOTAL_AMOUNT_ARREARS_URL = ROOTS_URL + "ParkBill/findCarBill";//停车记录欠费总金额
    public static final String STOP_CAR_TOTAL_AMOUNT_ARREARS_DELECT_URL = ROOTS_URL + "ParkRecord/deleteRecord";// 删除完成停车记录


    public static final int GETPASSCODE_CODE = 0x1;//获取手机验证码
    public static final int VERIFICATIONCODE_LOGIN_CODE = 0x2;//手机验证码登录
    public static final int FIRSTPAGE_IMAGE_CODE = 0x3;//获取首页轮播图片
    public static final int GET_My_News_CODE = 0x4;//获取 我的消息列表
    public static final int GET_Msg_Sys_CODE = 0x5;//获取 我的消息模块 所有内容
    public static final int MYWALLET_ALIPAY_CODE = 0x6;//支付宝充值
    public static final int MYWALLET_WECHATPAY_CODE = 0x7;//微信充值
    public static final int GENERATE_ORDER_CODE = 0x8;//生成订单
    public static final int APPBALANCE_GETTOTAL_CODE = 0x9;//获取账户余额
    public static final int APPBALANCE_GETRECHARGE_CODE = 0x10;//获取充值返现列表
    public static final int MYORDER_PAY_CODE = 0x11;//确认支付
    public static final int CAR_ADD_CODE = 0x12;//添加车辆
    public static final int Update_USER_Phone_CODE = 0x13;//是否在30天内可以 修改用户手机号
    public static final int Update_USER_Phone_SMS_CODE = 0x14;//修改用户手机号 发送验证码
    public static final int Update_USER_Phone_SUCCESS_CODE = 0x15;//修改用户手机号
    public static final int UPDATE_NAME_CODE = 0x16;//修改昵称
    public static final int UPDATE_SEX_CODE = 0x17;//修改性别
    public static final int UPDATE_AGE_CODE = 0x18;//修改年龄
    public static final int CAR_GETLIST_CODE = 0x19;//获取车辆列表
    public static final int CAR_DELETE_CODE = 0x20;//删除车辆
    public static final int CAR_ISAUTOPAY_CODE = 0x21;//自动支付
    public static final int CAR_BILL_RECORD_CODE = 0x22;//获取账单记录
    public static final int CAR_BILL_RECORD_DETAIL_CODE = 0x23;//获取账单记录详情
    public static final int CAR_ORDER_RECORD_CODE = 0x24;//我的订单
    public static final int GET_TROUBLE_LIST_CODE = 0x25;//客服帮助查找问题列表
    public static final int UPLOADPHOTO_CODE = 0x26;//上传头像
    public static final int RECHARGE_PAYIN_CODE = 0x27;//获取充值明细
    public static final int RECHARGE_PAYOUT_CODE = 0x28;//获取消费明细
    public static final int markUrl_CODE = 0x29;//百度地图mark标记
    public static final int CCsf_CODE = 0x30;//车场收费标准
    public static final int VERSION_UPDATE_CODE = 0x31;//检测版本是否更新
    public static final int NEED_HELP_CODE = 0x32;//需要帮助按钮
    public static final int USER_GETACTIONLIST_CODE = 0x34;//活动列表
    public static final int USER_SUBMITEYIJINA_CODE = 0x35;//提交反馈意见
    public static final int USER_JPUSH_CODE = 0x36;//绑定极光推送
    public static final int USER_ADVERTISING_CODE = 0x37;//广告页
    public static final int USER_YHQ_CODE = 0x39;//获取用户优惠券列表
    public static final int USER_Mobile_jiance_CODE = 0x40;//校验用户手机号
    public static final int USER_LQ_YHQ_CODE = 0x41;//用户领取优惠券
    public static final int SHOUYE_TC_CODE = 0x42;//首页弹框
    public static final int Car_Vehiclecard_CODE = 0x43;//车辆认证 审核
    public static final int BASE_CODE = 0x45;//总接口
    public static final int Car_Place_TuiJian_CODE = 0x46;//搜索附近推荐停车场
    public static final int GET_GUI_ZE_CODE = 0x47;//获取充值返现列表中的规则
    public static final int GET_YHQ_GUI_ZE_CODE = 0x48;//获取充值返现列表中的规则
    public static final int GET_GUI_ZE_CZZS_CODE = 0x53;//获取充值返现列表中的充值赠送规则
    public static final int Commit_FaPiao_Message_CODE = 0x49;//发票信息提交
    public static final int GET_FaPiao_History_CODE = 0x50;//获取 开发票历史记录
    public static final int YHQDuiHuan_CODE = 0x51;//优惠券兑换
    public static final int GET_INVITED_FRIEND_URL_CODE = 0x52;//获取分享好友url
    public static final int SERVICE_ADDINFO_CODE = 0x54;//获取分享好友url
    public static final int USER_Month_Card_CODE = 0x55;//月卡数量查询
    public static final int USER_Month_Card_Buy_CODE = 0x56;//去购买月卡
    public static final int USER_Month_Card_Zhi_Fu_CODE = 0x57;//去购买月卡 支付
    public static final int USER_Month_Card_And_Car_CODE = 0x58;//查询已配置月卡和车辆
    public static final int USER_Month_Card_WX_CODE = 0x59;//用户微信支付月卡
    public static final int USER_Month_Card_ZFB_CODE = 0x60;//用户支付宝支付月卡
    public static final int STOP_CAR_TOTAL_AMOUNT_ARREARS = 0x61;//停车记录欠费总金额
    public static final int STOP_CAR_TOTAL_AMOUNT_ARREARS_DELECT = 0x62;//删除完成停车记录

    public static boolean isSetDialogShow = true;
    public static int type = 0;
    public static int android_type = 1;
}
