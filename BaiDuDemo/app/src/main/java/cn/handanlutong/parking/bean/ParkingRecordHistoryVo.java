
package cn.handanlutong.parking.bean;

/**
 * 历史停车记录
 * 
 * @author 
 * @version 1.0 2017-02-21
 */
public class ParkingRecordHistoryVo {

    /**
     * id
     */
    private Long id;
    /**
     * 流水号
     */
    private String lsh;
    /**
     * 设备编号
     */
    private String sbbh;
    /**
     * 识别记录编码
     */
    private String sbjlbm;
    /**
     * 车场编号
     */
    private String ccbh;
    /**
     * 车位编号
     */
    private String cwbh;
    /**
     * 入位时间
     */
    private String rwsj;
    /**
     * 离位时间
     */
    private String lwsj;
    /**
     * 号牌种类
     */
    private String hpzl;
    /**
     * 号牌号码
     */
    private String hphm;
    /**
     * 入位照片1
     */
    private String rwtp1Path;
    /**
     * 入位照片2
     */
    private String rwtp2Path;
    /**
     * 离位照片1
     */
    private String lwtp1Path;
    /**
     * 离位照片2
     */
    private String lwtp2Path;
    /**
     * 停车时长
     */
    private String tcsc;
    /**
     * 入位情况 0正常停车 1跨位停车 2斜位停车 3侧位停车 4反复入位
     */
    private String rwqk;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户手机号
     */
    private String userPhone;
    /**
     * 创建时间
     */
    private String cjsj;
    /**
     * 流水状态 a入位 b离位 
     */
    private String lszt;
    /**
     * 管理员id
     */
    private String adminId;
    /**
     * 是否支付1:是.0:否
     */
    private String isPay;
    /**
     * 数据来源1 管理员 2 识别主机
     */
    private String sjly;
    /**
     * 订单总金额
     */
    private String zje;
    
    //支付金额
    private String zfje;
    
    private String qkje;
    
    /**
     * 车场ID
     * */
    private Long ccid;
    
    /**
     * 车场地址
     * */
    private String ccdz;
    
    /**
     * 车场名称
     * */
    private String ccmc;
    
    /**
     * 收费方式(定价类型)
     */
    private String djlx;
    
    //订单编号
    private String ddbh;
    
    //订单状态
    private String ddzt;
    
    //支付方式
    private String zffs;
    
    //支付渠道
    private String zfqd;
    
    //优惠券金额
    private Integer yhjje;
    
    /**
     * 入位时间-月日
     */
    private String rwsjYR;
    
    /**
     * 入位时间-时分秒
     */
    private String rwsjSFM;
    /**
     * 离位时间-月日
     */
    private String lwsjYR;
    
    /**
     * 离位时间-时分秒
     */
    private String lwsjSFM;

	private String tkje;

	private String tksj;

	/**
	 * 支付类型 1充值 2消费 3退款 4赠送 5包期
	 */
	private String zflx;

	/**
	 * 月卡是否过期 1已过期 0未过期
	 */
	private String yksfgq;

	public String getYksfgq() {
		return yksfgq;
	}

	public void setYksfgq(String yksfgq) {
		this.yksfgq = yksfgq;
	}

	public String getZflx() {
		return zflx;
	}

	public void setZflx(String zflx) {
		this.zflx = zflx;
	}

	public String getTkje() {
		return tkje;
	}

	public void setTkje(String tkje) {
		this.tkje = tkje;
	}

	public String getTksj() {
		return tksj;
	}

	public void setTksj(String tksj) {
		this.tksj = tksj;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLsh() {
		return lsh;
	}

	public void setLsh(String lsh) {
		this.lsh = lsh;
	}

	public String getSbbh() {
		return sbbh;
	}

	public void setSbbh(String sbbh) {
		this.sbbh = sbbh;
	}

	public String getSbjlbm() {
		return sbjlbm;
	}

	public void setSbjlbm(String sbjlbm) {
		this.sbjlbm = sbjlbm;
	}

	public String getCcbh() {
		return ccbh;
	}

	public void setCcbh(String ccbh) {
		this.ccbh = ccbh;
	}

	public String getCwbh() {
		return cwbh;
	}

	public void setCwbh(String cwbh) {
		this.cwbh = cwbh;
	}

	public String getRwsj() {
		return rwsj;
	}

	public void setRwsj(String rwsj) {
		this.rwsj = rwsj;
	}

	public String getLwsj() {
		return lwsj;
	}

	public void setLwsj(String lwsj) {
		this.lwsj = lwsj;
	}

	public String getHpzl() {
		return hpzl;
	}

	public void setHpzl(String hpzl) {
		this.hpzl = hpzl;
	}

	public String getHphm() {
		return hphm;
	}

	public void setHphm(String hphm) {
		this.hphm = hphm;
	}

	public String getRwtp1Path() {
		return rwtp1Path;
	}

	public void setRwtp1Path(String rwtp1Path) {
		this.rwtp1Path = rwtp1Path;
	}

	public String getRwtp2Path() {
		return rwtp2Path;
	}

	public void setRwtp2Path(String rwtp2Path) {
		this.rwtp2Path = rwtp2Path;
	}

	public String getLwtp1Path() {
		return lwtp1Path;
	}

	public void setLwtp1Path(String lwtp1Path) {
		this.lwtp1Path = lwtp1Path;
	}

	public String getLwtp2Path() {
		return lwtp2Path;
	}

	public void setLwtp2Path(String lwtp2Path) {
		this.lwtp2Path = lwtp2Path;
	}

	public String getTcsc() {
		return tcsc;
	}

	public void setTcsc(String tcsc) {
		this.tcsc = tcsc;
	}

	public String getRwqk() {
		return rwqk;
	}

	public void setRwqk(String rwqk) {
		this.rwqk = rwqk;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getCjsj() {
		return cjsj;
	}

	public void setCjsj(String cjsj) {
		this.cjsj = cjsj;
	}

	public String getLszt() {
		return lszt;
	}

	public void setLszt(String lszt) {
		this.lszt = lszt;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getIsPay() {
		return isPay;
	}

	public void setIsPay(String isPay) {
		this.isPay = isPay;
	}

	public String getSjly() {
		return sjly;
	}

	public void setSjly(String sjly) {
		this.sjly = sjly;
	}

	public String getZje() {
		return zje;
	}

	public void setZje(String zje) {
		this.zje = zje;
	}

	public String getZfje() {
		return zfje;
	}

	public void setZfje(String zfje) {
		this.zfje = zfje;
	}

	public String getQkje() {
		return qkje;
	}

	public void setQkje(String qkje) {
		this.qkje = qkje;
	}

	public Long getCcid() {
		return ccid;
	}

	public void setCcid(Long ccid) {
		this.ccid = ccid;
	}

	public String getCcdz() {
		return ccdz;
	}

	public void setCcdz(String ccdz) {
		this.ccdz = ccdz;
	}

	public String getCcmc() {
		return ccmc;
	}

	public void setCcmc(String ccmc) {
		this.ccmc = ccmc;
	}

	public String getDjlx() {
		return djlx;
	}

	public void setDjlx(String djlx) {
		this.djlx = djlx;
	}

	public String getDdbh() {
		return ddbh;
	}

	public void setDdbh(String ddbh) {
		this.ddbh = ddbh;
	}

	public String getDdzt() {
		return ddzt;
	}

	public void setDdzt(String ddzt) {
		this.ddzt = ddzt;
	}

	public String getZffs() {
		return zffs;
	}

	public void setZffs(String zffs) {
		this.zffs = zffs;
	}

	public String getZfqd() {
		return zfqd;
	}

	public void setZfqd(String zfqd) {
		this.zfqd = zfqd;
	}

	public Integer getYhjje() {
		return yhjje;
	}

	public void setYhjje(Integer yhjje) {
		this.yhjje = yhjje;
	}

	public String getRwsjYR() {
		return rwsjYR;
	}

	public void setRwsjYR(String rwsjYR) {
		this.rwsjYR = rwsjYR;
	}

	public String getRwsjSFM() {
		return rwsjSFM;
	}

	public void setRwsjSFM(String rwsjSFM) {
		this.rwsjSFM = rwsjSFM;
	}

	public String getLwsjYR() {
		return lwsjYR;
	}

	public void setLwsjYR(String lwsjYR) {
		this.lwsjYR = lwsjYR;
	}

	public String getLwsjSFM() {
		return lwsjSFM;
	}

	public void setLwsjSFM(String lwsjSFM) {
		this.lwsjSFM = lwsjSFM;
	}

}