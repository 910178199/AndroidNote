package cn.handanlutong.parking.http;

import com.baidu.mapapi.model.LatLng;
import com.lidroid.xutils.HttpUtils;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by zhangyonggang on 2017/6/7.
 */

public class HttpMethod {

    /**
     * 手机验证码登录
     *
     * @param httpUtils
     * @param callBack
     * @param resultCode
     */
    public static void PassCodeLogins(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.VERIFICATIONCODE_LOGIN_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 发送验证码
     *
     * @param httpUtils
     * @param jobj
     * @param callBack
     * @param resultCode
     */
    public static void GainLoginPassCodes(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.GETPASSCODE_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 获取首页轮播页图片 首页活动弹窗
     *
     * @param httpUtils
     * @param jobj
     * @param callBack
     * @param resultCode
     */
    public static void AppUserGetImageData(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.FIRSTPAGE_IMAGE_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 添加车辆
     *
     * @param httpUtils
     * @param callBack
     * @param resultCode
     */
    public static void AppUsergetAddCars(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.CAR_ADD_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 获取车辆列表
     *
     * @param httpUtils
     * @param jobj
     * @param callBack
     * @param resultCode
     */
    public static void AppUsergetCarLists(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.CAR_GETLIST_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 删除车辆
     *
     * @param httpUtils
     * @param jobj
     * @param callBack
     * @param resultCode
     */
    public static void AppUsergetDeleteCars(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.CAR_DELETE_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 自动支付
     *
     * @param httpUtils
     * @param jobj
     * @param callBack
     * @param resultCode
     */
    public static void AppUseIsOpenAutoPay(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.CAR_ISAUTOPAY_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 获取账单记录
     *
     * @param httpUtils
     * @param jobj
     * @param callBack
     * @param resultCode
     */
    public static void AppUsergetBillRecords(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.CAR_BILL_RECORD_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 我的订单
     *
     * @param httpUtils
     * @param jobj
     * @param callBack
     * @param resultCode
     */
    public static void AppUsergetOrderRecords(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.CAR_ORDER_RECORD_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 获取账单或订单详情
     *
     * @param httpUtils
     * @param jobj
     * @param callBack
     * @param resultCode
     */
    public static void AppUsergetBillRecordDetails(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.CAR_BILL_RECORD_DETAIL_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 修改用户昵称
     *
     * @param httpUtils
     * @param jobj
     * @param callBack
     * @param resultCode
     */
    public static void AppUserUpdateName(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.UPDATE_NAME_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 修改用户性别
     *
     * @param httpUtils
     * @param jobj
     * @param callBack
     * @param resultCode
     */
    public static void AppUserUpdateSex(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.UPDATE_SEX_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 修改用户年龄
     *
     * @param httpUtils
     * @param jobj
     * @param callBack
     * @param resultCode
     */
    public static void AppUserUpdateAge(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.UPDATE_AGE_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 修改头像
     *
     * @param httpUtils
     * @param jobj
     * @param callBack
     * @param resultCode
     */
    public static void AppUserUpdateHead(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.UPLOADPHOTO_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 上传行驶证 照片
     */
    public static void CarVehiclecard(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.Car_Vehiclecard_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 上传行驶证 照片
     */
    public static void CarVehiclecard2(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.Car_Vehiclecard2_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 确认支付
     *
     * @param httpUtils
     * @param jobj
     * @param callBack
     * @param resultCode
     */
    public static void AppUserMyOrderPay(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.MYORDER_PAY_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 生成订单
     *
     * @param httpUtils
     * @param jobj
     * @param callBack
     * @param resultCode
     */
    public static void AppUserGenerateOrder(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.GENERATE_ORDER_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 获取账户余额
     *
     * @param httpUtils

     * @param jobj
     * @param callBack
     * @param resultCode
     */
    public static void AppUsergetTotalBalances(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.APPBALANCE_GETTOTAL_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 获取用户可用的优惠券
     */
    public static void GETuserYHQ(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.USER_YHQ_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 获取充值返现列表
     *
     * @param httpUtils
     * @param jobj
     * @param callBack
     * @param resultCode
     */
    public static void AppUserBalancesReChargeList(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.APPBALANCE_GETRECHARGE_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 支付宝支付
     *
     * @param httpUtils
     * @param jobj
     * @param callBack
     * @param resultCode
     */
    public static void AppUserAliPayReCharge(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.MYWALLET_ALIPAY_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 微信支付
     *
     * @param httpUtils
     * @param jobj
     * @param callBack
     * @param resultCode
     */
    public static void AppUserWeChatPayReCharge(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.MYWALLET_WECHATPAY_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 微信 支付订单
     *
     * @param httpUtils
     * @param jobj
     * @param callBack
     * @param resultCode
     */
    public static void WXzhifuDd(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.USER_wx_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 支付宝 支付订单
     *
     * @param httpUtils
     * @param jobj
     * @param callBack
     * @param resultCode
     */
    public static void ZFBzhifuDd(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.USER_zhifubao_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 获取充值列表
     *
     * @param httpUtils
     * @param jobj
     * @param callBack
     * @param resultCode
     */
    public static void AppUserRechargeGetList(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.RECHARGE_PAYIN_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 获取消费明细
     *
     * @param httpUtils
     * @param jobj
     * @param callBack
     * @param resultCode
     */
    public static void AppUserPayOutGetList(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.RECHARGE_PAYOUT_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }


    /**
     * 百度地图 查询500米内停车场
     */
    public static void BaiDuFindPlace(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode, LatLng location) {
        String url = UrlConfig.markUrl_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 车场收费标准
     */
    public static void CCsf_GUIze(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.CCsf_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 客户帮查找问题列表
     *
     * @param httpUtils
     * @param jobj
     * @param callBack
     * @param resultCode
     */
    public static void AppUserGetHelpList(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.GET_TROUBLE_LIST_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 提交问题
     *
     * @param httpUtils
     * @param jobj
     * @param callBack
     * @param resultCode
     */
    public static void AppUseraddServiceInfo(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.GET_SERVICE_ADDINFO_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }


    /**
     * 检测版本更新
     *
     * @param httpUtils
     * @param jobj
     * @param callBack
     * @param resultCode
     */
    public static void AppCheckVersion(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.VERSION_UPDATE_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 检验用户手机号 接口
     *
     * @param httpUtils
     * @param jobj
     * @param callBack
     * @param resultCode
     */
    public static void UserMobileDetails(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.USER_Mobile_jiance_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 用户领取优惠券 接口
     *
     * @param httpUtils
     * @param jobj
     * @param callBack
     * @param resultCode
     */
    public static void UserLQYHQ(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.USER_LQ_YHQ_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 活动列表
     *
     * @param httpUtils
     * @param jobj
     * @param callBack
     * @param resultCode
     */
    public static void AppUserGetActionList(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.USER_GETACTIONLIST_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 用户提交反馈
     *
     * @param httpUtils
     * @param jobj
     * @param callBack
     * @param resultCode
     */
    public static void AppUserSubmite(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.USER_SUBMITEYIJINA_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 绑定激光推送
     *
     * @param httpUtils
     * @param jobj
     * @param callBack
     * @param resultCode
     */
    public static void JpushBand(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.USER_JPUSH_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 首页广告接口
     *
     * @param httpUtils
     * @param jobj
     * @param callBack
     * @param resultCode
     */
    public static void AppUserAdvertising(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.USER_ADVERTISING_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 接口整体 切换
     *
     * @param httpUtils
     * @param jobj
     * @param callBack
     * @param resultCode
     */
    public static void jiekouQieHuan(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.BASE_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 推荐车场 搜索附近停车场
     *
     * @param httpUtils
     * @param jobj
     * @param callBack
     * @param resultCode
     */
    public static void postTuiJianCarPlace(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.Car_Place_TuiJian_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 获取充值返现 规则
     *
     * @param httpUtils
     * @param jobj
     * @param callBack
     * @param resultCode
     */
    public static void getGuiZeData(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.GET_GUI_ZE_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 获取充值赠送 规则
     *
     * @param httpUtils
     * @param jobj
     * @param callBack
     * @param resultCode
     */
    public static void getGuiZeData2(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.GET_GUI_ZE_CZZS_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 获取优惠券 规则
     *
     * @param httpUtils
     * @param jobj
     * @param callBack
     * @param resultCode
     */
    public static void getYHQGuiZeData(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.GET_YHQ_GUI_ZE_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 获取 可开发票 额度
     */
    public static void postFaPiaoData(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.APPBALANCE_GETTOTAL_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 发票信息提交 接口
     */
    public static void postCommitFaPiaoData(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.Commit_FaPiao_Message_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 获取 开发票历史记录
     */
    public static void postFaPiaoHistoryData(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.GET_FaPiao_History_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 获取 我的消息列表
     */
    public static void postMyNewsData(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.GET_My_News_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 获取 我的消息模块  所有内容
     */
    public static void postMsgSysData(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.GET_Msg_Sys_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 优惠券兑换
     */
    public static void postYHQDuiHuanData(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.YHQDuiHuan_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 分享接口调用
     */
    public static void AppUserGetShareFriendUrl(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.GET_INVITED_FRIEND_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 修改用户手机号  判断是否在30天之内
     */
    public static void postUpdatePhoneData(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.Update_USER_Phone_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 修改用户手机号  发送验证码
     */
    public static void SendLoginPassCode(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.Update_USER_Phone_SMS_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 修改用户手机号
     */
    public static void Verificationcodelogin(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.Update_USER_Phone_Success_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 需要帮助  调用接口
     */
    public static void postNeedHelpData(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.NEED_HELP_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 我的月卡 查询接口
     */
    public static void postMonthCardData(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.USER_Month_Card_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 去购买月卡 接口
     */
    public static void postMonthCardBuyData(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.USER_Month_Card_Buy_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 去购买月卡去支付 接口
     */
    public static void postMonthCardZhiFuData(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.USER_Month_Card_Zhi_Fu_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 查询已配置月卡和车辆 接口
     */
    public static void postMonthCardAndMyCarData(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.USER_Month_Card_And_Car_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 微信 支付订单
     *
     * @param httpUtils
     * @param jobj
     * @param callBack
     * @param resultCode
     */
    public static void WXzhifuMonthCard(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.USER_wx_Month_Card_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 支付宝 支付订单
     *
     * @param httpUtils
     * @param jobj
     * @param callBack
     * @param resultCode
     */
    public static void ZFBzhifuMonthCard(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.USER_zhifubao_Month_Card_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 获取 停车记录欠费总金额 额度
     */
    public static void postTingCheQianFeiData(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.STOP_CAR_TOTAL_AMOUNT_ARREARS_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }

    /**
     * 删除完成停车记录
     */
    public static void postShanChuWanChengJiluData(HttpUtils httpUtils, JSONObject jobj, ObserverCallBack callBack, int resultCode) {
        String url = UrlConfig.STOP_CAR_TOTAL_AMOUNT_ARREARS_DELECT_URL;
        AnsynHttpRequest.requestGetOrPosts(AnsynHttpRequest.POST, url, jobj,
                callBack, httpUtils, resultCode);
    }
}
