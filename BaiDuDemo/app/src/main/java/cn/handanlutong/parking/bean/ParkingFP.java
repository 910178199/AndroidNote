/*
 * ParkingFP.java
 * Copyright(C) 20xx-2015 xxxxxx鍏徃
 * All rights reserved.
 * -----------------------------------------------
 * 2017-11-24 Created
 */
package cn.handanlutong.parking.bean;

/**
 * null
 * 
 * @author 
 * @version 1.0 2017-11-24
 */
public class ParkingFP {

    /**
     * 记录ID
     */
    private Long id;
    /**
     * 发票请求唯一流水号:和外层报文中DATAEXCHANGEID保持一致
     */
    private String fpqqlsh;
    /**
     * 订单号:由企业自定义
     */
    private String ddh;
    /**
     * 平台编码:与外层报文的USERNAME内容相同
     */
    private String dsptbm;
    /**
     * 开票方识别号:纳税人识别号
     */
    private String nsrsbh;
    /**
     * 开票方名称:纳税人名称
     */
    private String nsrmc;
    /**
     * 开票方电子档案号:暂未启用，置空即可
     */
    private String nsrdzdah;
    /**
     * 税务机构代码:详询财务人员
     */
    private String swjgDm;
    /**
     * 代开标志:1：自开（0）；2：代开（1）；默认为自开
     */
    private String dkbz;
    /**
     * 票样代码:全部固定为“000001”
     */
    private String pydm;
    /**
     * 主要开票项目:主要开票商品，或者第一条商品，取项目信息中第一条数据的项目名称（或传递大类例如：办公用品）
     */
    private String kpxm;
    /**
     * 销货方识别号:必填，如果是企业自营开具发票，填写第四项中的开票方识别号，如果是商家驻店开具发票，填写商家的纳税人识别号
     */
    private String xhfNsrsbh;
    /**
     * 销货方名称:必填，如果是企业自营开具发票，填写第五项中的开票方名称，如果是商家驻店开具发票，填写商家的纳税人名称
     */
    private String xhfmc;
    /**
     * 销货方地址:注册地址
     */
    private String xhfDz;
    /**
     * 销货方电话:注册电话
     */
    private String xhfDh;
    /**
     * 销货方银行账号:开户行及账号
     */
    private String xhfYhzh;
    /**
     * 销货方微信:
     */
    private String xhfWx;
    /**
     * 购货方名称:购货方名称，即发票抬头，购货方为“个人”时，可输入名称，输入名称是为“个人（名称）”，“（”为半角
     */
    private String ghfmc;
    /**
     * 购货方识别号:
     */
    private String ghfNsrsbh;
    /**
     * 购货方地址:
     */
    private String ghfDz;
    /**
     * 购货方省份:使用各省行政编码
     */
    private String ghfSf;
    /**
     * 购货方固定电话:
     */
    private String ghfGddh;
    /**
     * 购货方手机:手机必填，用于接收发票信息推送短信
     */
    private String ghfSj;
    /**
     * 购货方邮箱:选填，如果填写邮箱，则系统自动发送发票信息邮件，否则不发送           
     */
    private String ghfEmail;
    /**
     * 购货方微信:
     */
    private String ghfWx;
    /**
     * 购货方企业类型:01：企业02：机关事业单位03：个人04：其他
     */
    private String ghfqylx;
    /**
     * 购货方银行账号:开户行及账号
     */
    private String ghfYhzh;
    /**
     * 行业代码:由企业端系统自动填写（根据企业注册信息）
     */
    private String hyDm;
    /**
     * 行业名称:由企业端系统自动填写（根据企业注册信息）
     */
    private String hyMc;
    /**
     * 开票点编号:开票点编号
     */
    private String kpdbh;
    /**
     * 开票员:企业自定义
     */
    private String kpy;
    /**
     * 收款员
     */
    private String sky;
    /**
     * 复核人
     */
    private String fhr;
    /**
     * 开票日期:格式YYYY-MM-DDHH:MI:SS（由开票系统生成）
     */
    private String kprq;
    /**
     * 开票类型:0：正票1：红票
     */
    private String kplx;
    /**
     * 原发票代码:冲红时使用，如果CZDM不是10或KPLX为红票时候都是必填          
     */
    private String yfpDm;
    /**
     * 原发票号码:冲红时使用，如果CZDM不是10或KPLX为红票时候都是必填          
     */
    private String yfpHm;
    /**
     * 特殊冲红标志:0：正常冲红（电子发票）1：特殊冲红（冲红纸质等）
     */
    private String tschbz;
    /**
     * 操作代码:10：正票正常开具11：正票错票重开20：退货折让红票21：错票重开红票22：换票冲红（全冲红电子发票，开具纸质发票）
     */
    private String czdm;
    /**
     * 冲红原因:冲红时填写，由企业定义
     */
    private String chyy;
    /**
     * 价税合计金额:小数点后2位，以元为单位精确到分
     */
    private String kphjje;
    /**
     * 合计不含税金额:小数点后2位，以元为单位精确到分（单行商品金额之和），平台处理价税分离，此值传0
     */
    private String hjbhsje;
    /**
     * 合计税额:小数点后2位，以元为单位精确到分（单行商品金额之和），平台处理价税分离，此值传0 
     */
    private String hjse;
    /**
     * 编码表版本号:目前为1.0
     */
    private String bmbBbh;
    /**
     * 清单标志:默认为0
     */
    private String qdBz;
    /**
     * 清单项目名称:可不填，暂不作处理
     */
    private String qdxmmc;
    /**
     * 备注
     */
    private String bz;
    /**
     * 内部订单号
     */
    private String byzd1;
    /**
     * 备用字段2
     */
    private String byzd2;
    /**
     * 项目名称:非商品编码表版本：如果是折扣行对应被折扣项目为一行则折扣项目名称必须为“折扣（XX.XXX%）”;如果折扣行对应项目为多行，则折扣项目名称为“折扣行数X（XX.XXX%）”,且项目金额和税额为负数。折扣行必须紧邻被折扣行的下方。同一个税率下的被折扣项目，可以对应一个折扣行或多个折扣行。
     */
    private String xmmc;
    /**
     * 项目单位
     */
    private String xmdw;
    /**
     * 项目数量:小数点后8位，小数点后都是0时，PDF上只显示整数
     */
    private String xmsl;
    /**
     * 含税标志:表示项目单价和项目金额是否含税，固定为1
     */
    private String hsbz;
    /**
     * 项目单价:小数点后8位小数点后都是0时，PDF上只显示2位小数；否则只显示至最后一位不为0的数字
     */
    private String xmdj;
    /**
     * 项目编码
     */
    private String xmbm;
    /**
     * 项目金额:小数点后2位，以元为单位精确到分。等于=单价*数量，根据含税标志，确定此金额是否为含税金额
     */
    private String xmje;
    /**
     * 税率:如果税率为0，表示免税
     */
    private String sl;
    /**
     * 税额:小数点后2位，以元为单位精确到分
     */
    private String se;
    /**
     * 发票行性质:0正常行1折扣行2被折扣行
     */
    private String fphxz;
    /**
     * 商品编码:所属商品分类详询财务人员
     */
    private String spbm;
    /**
     * 自行编码:默认为空
     */
    private String zxbm;
    /**
     * 优惠政策标识:0：不使用1：使用详询财务人员
     */
    private String yhzcbs;
    /**
     * 零税率标识:"空：非零税率1：免税2：征税3：普通零税率详询财务人员"           
     */
    private String lslbs;
    /**
     * 增值税特殊管理:当YHZCBS为1时必填详询财务人员
     */
    private String zzstsgl;
    /**
     * 退货单号:在开具红字发票退货、折让的时候必须填写
     */
    private String thdh;
    /**
     * 订单时间:格式YYYY-MM-DDHH:MI:SS
     */
    private String dddate;
    /**
     * 名称
     */
    private String ddmc;
    /**
     * 单位
     */
    private String dw;
    /**
     * 单价:小数点后8位，小数点后都是0时，PDF上只显示2位小数；否则只显示至最后一位不为0的数字
     */
    private String dj;
    /**
     * 金额:小数点后2位，精确到分
     */
    private String je;
    /**
     * 支付方式
     */
    private String zffs;
    /**
     * 支付流水号
     */
    private String zflsh;
    /**
     * 支付平台
     */
    private String zfpt;
    /**
     * 承运公司
     */
    private String cygs;
    /**
     * 送货时间:YYYY-MM-DDHH:MI:SS
     */
    private String shsj;
    /**
     * 物流单号
     */
    private String wldh;
    /**
     * 送货地址:否
     */
    private String shdz;
    /**
     * 用户ID
     */
    private String userid;
    /**
     * 创建时间
     */
    private String cjsj;
    /**
     * 发票状态
     */
    private String fpzt;
    /**
     * 发票是否显示
     */
    private String fpsfxs;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFpqqlsh() {
        return fpqqlsh;
    }
    public void setFpqqlsh(String fpqqlsh) {
        this.fpqqlsh = fpqqlsh == null ? null : fpqqlsh.trim();
    }
    public String getDdh() {
        return ddh;
    }
    public void setDdh(String ddh) {
        this.ddh = ddh == null ? null : ddh.trim();
    }
    public String getDsptbm() {
        return dsptbm;
    }
    public void setDsptbm(String dsptbm) {
        this.dsptbm = dsptbm == null ? null : dsptbm.trim();
    }
    public String getNsrsbh() {
        return nsrsbh;
    }
    public void setNsrsbh(String nsrsbh) {
        this.nsrsbh = nsrsbh == null ? null : nsrsbh.trim();
    }
    public String getNsrmc() {
        return nsrmc;
    }
    public void setNsrmc(String nsrmc) {
        this.nsrmc = nsrmc == null ? null : nsrmc.trim();
    }
    public String getNsrdzdah() {
        return nsrdzdah;
    }
    public void setNsrdzdah(String nsrdzdah) {
        this.nsrdzdah = nsrdzdah == null ? null : nsrdzdah.trim();
    }
    public String getSwjgDm() {
        return swjgDm;
    }
    public void setSwjgDm(String swjgDm) {
        this.swjgDm = swjgDm == null ? null : swjgDm.trim();
    }
    public String getDkbz() {
        return dkbz;
    }
    public void setDkbz(String dkbz) {
        this.dkbz = dkbz == null ? null : dkbz.trim();
    }
    public String getPydm() {
        return pydm;
    }
    public void setPydm(String pydm) {
        this.pydm = pydm == null ? null : pydm.trim();
    }
    public String getKpxm() {
        return kpxm;
    }
    public void setKpxm(String kpxm) {
        this.kpxm = kpxm == null ? null : kpxm.trim();
    }
    public String getXhfNsrsbh() {
        return xhfNsrsbh;
    }
    public void setXhfNsrsbh(String xhfNsrsbh) {
        this.xhfNsrsbh = xhfNsrsbh == null ? null : xhfNsrsbh.trim();
    }
    public String getXhfmc() {
        return xhfmc;
    }
    public void setXhfmc(String xhfmc) {
        this.xhfmc = xhfmc == null ? null : xhfmc.trim();
    }
    public String getXhfDz() {
        return xhfDz;
    }
    public void setXhfDz(String xhfDz) {
        this.xhfDz = xhfDz == null ? null : xhfDz.trim();
    }
    public String getXhfDh() {
        return xhfDh;
    }
    public void setXhfDh(String xhfDh) {
        this.xhfDh = xhfDh == null ? null : xhfDh.trim();
    }
    public String getXhfYhzh() {
        return xhfYhzh;
    }
    public void setXhfYhzh(String xhfYhzh) {
        this.xhfYhzh = xhfYhzh == null ? null : xhfYhzh.trim();
    }
    public String getXhfWx() {
        return xhfWx;
    }
    public void setXhfWx(String xhfWx) {
        this.xhfWx = xhfWx == null ? null : xhfWx.trim();
    }
    public String getGhfmc() {
        return ghfmc;
    }
    public void setGhfmc(String ghfmc) {
        this.ghfmc = ghfmc == null ? null : ghfmc.trim();
    }
    public String getGhfNsrsbh() {
        return ghfNsrsbh;
    }
    public void setGhfNsrsbh(String ghfNsrsbh) {
        this.ghfNsrsbh = ghfNsrsbh == null ? null : ghfNsrsbh.trim();
    }
    public String getGhfDz() {
        return ghfDz;
    }
    public void setGhfDz(String ghfDz) {
        this.ghfDz = ghfDz == null ? null : ghfDz.trim();
    }
    public String getGhfSf() {
        return ghfSf;
    }
    public void setGhfSf(String ghfSf) {
        this.ghfSf = ghfSf == null ? null : ghfSf.trim();
    }
    public String getGhfGddh() {
        return ghfGddh;
    }
    public void setGhfGddh(String ghfGddh) {
        this.ghfGddh = ghfGddh == null ? null : ghfGddh.trim();
    }
    public String getGhfSj() {
        return ghfSj;
    }
    public void setGhfSj(String ghfSj) {
        this.ghfSj = ghfSj == null ? null : ghfSj.trim();
    }
    public String getGhfEmail() {
        return ghfEmail;
    }
    public void setGhfEmail(String ghfEmail) {
        this.ghfEmail = ghfEmail == null ? null : ghfEmail.trim();
    }
    public String getGhfWx() {
        return ghfWx;
    }
    public void setGhfWx(String ghfWx) {
        this.ghfWx = ghfWx == null ? null : ghfWx.trim();
    }
    public String getGhfqylx() {
        return ghfqylx;
    }
    public void setGhfqylx(String ghfqylx) {
        this.ghfqylx = ghfqylx == null ? null : ghfqylx.trim();
    }
    public String getGhfYhzh() {
        return ghfYhzh;
    }
    public void setGhfYhzh(String ghfYhzh) {
        this.ghfYhzh = ghfYhzh == null ? null : ghfYhzh.trim();
    }
    public String getHyDm() {
        return hyDm;
    }
    public void setHyDm(String hyDm) {
        this.hyDm = hyDm == null ? null : hyDm.trim();
    }
    public String getHyMc() {
        return hyMc;
    }
    public void setHyMc(String hyMc) {
        this.hyMc = hyMc == null ? null : hyMc.trim();
    }
    public String getKpdbh() {
        return kpdbh;
    }
    public void setKpdbh(String kpdbh) {
        this.kpdbh = kpdbh == null ? null : kpdbh.trim();
    }
    public String getKpy() {
        return kpy;
    }
    public void setKpy(String kpy) {
        this.kpy = kpy == null ? null : kpy.trim();
    }
    public String getSky() {
        return sky;
    }
    public void setSky(String sky) {
        this.sky = sky == null ? null : sky.trim();
    }
    public String getFhr() {
        return fhr;
    }
    public void setFhr(String fhr) {
        this.fhr = fhr == null ? null : fhr.trim();
    }
    public String getKprq() {
        return kprq;
    }
    public void setKprq(String kprq) {
        this.kprq = kprq == null ? null : kprq.trim();
    }
    public String getKplx() {
        return kplx;
    }
    public void setKplx(String kplx) {
        this.kplx = kplx == null ? null : kplx.trim();
    }
    public String getYfpDm() {
        return yfpDm;
    }
    public void setYfpDm(String yfpDm) {
        this.yfpDm = yfpDm == null ? null : yfpDm.trim();
    }
    public String getYfpHm() {
        return yfpHm;
    }
    public void setYfpHm(String yfpHm) {
        this.yfpHm = yfpHm == null ? null : yfpHm.trim();
    }
    public String getTschbz() {
        return tschbz;
    }
    public void setTschbz(String tschbz) {
        this.tschbz = tschbz == null ? null : tschbz.trim();
    }
    public String getCzdm() {
        return czdm;
    }
    public void setCzdm(String czdm) {
        this.czdm = czdm == null ? null : czdm.trim();
    }
    public String getChyy() {
        return chyy;
    }
    public void setChyy(String chyy) {
        this.chyy = chyy == null ? null : chyy.trim();
    }
    public String getKphjje() {
        return kphjje;
    }
    public void setKphjje(String kphjje) {
        this.kphjje = kphjje == null ? null : kphjje.trim();
    }
    public String getHjbhsje() {
        return hjbhsje;
    }
    public void setHjbhsje(String hjbhsje) {
        this.hjbhsje = hjbhsje == null ? null : hjbhsje.trim();
    }
    public String getHjse() {
        return hjse;
    }
    public void setHjse(String hjse) {
        this.hjse = hjse == null ? null : hjse.trim();
    }
    public String getBmbBbh() {
        return bmbBbh;
    }
    public void setBmbBbh(String bmbBbh) {
        this.bmbBbh = bmbBbh == null ? null : bmbBbh.trim();
    }
    public String getQdBz() {
        return qdBz;
    }
    public void setQdBz(String qdBz) {
        this.qdBz = qdBz == null ? null : qdBz.trim();
    }
    public String getQdxmmc() {
        return qdxmmc;
    }
    public void setQdxmmc(String qdxmmc) {
        this.qdxmmc = qdxmmc == null ? null : qdxmmc.trim();
    }
    public String getBz() {
        return bz;
    }
    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }
    public String getByzd1() {
        return byzd1;
    }
    public void setByzd1(String byzd1) {
        this.byzd1 = byzd1 == null ? null : byzd1.trim();
    }
    public String getByzd2() {
        return byzd2;
    }
    public void setByzd2(String byzd2) {
        this.byzd2 = byzd2 == null ? null : byzd2.trim();
    }
    public String getXmmc() {
        return xmmc;
    }
    public void setXmmc(String xmmc) {
        this.xmmc = xmmc == null ? null : xmmc.trim();
    }
    public String getXmdw() {
        return xmdw;
    }
    public void setXmdw(String xmdw) {
        this.xmdw = xmdw == null ? null : xmdw.trim();
    }
    public String getXmsl() {
        return xmsl;
    }
    public void setXmsl(String xmsl) {
        this.xmsl = xmsl == null ? null : xmsl.trim();
    }
    public String getHsbz() {
        return hsbz;
    }
    public void setHsbz(String hsbz) {
        this.hsbz = hsbz == null ? null : hsbz.trim();
    }
    public String getXmdj() {
        return xmdj;
    }
    public void setXmdj(String xmdj) {
        this.xmdj = xmdj == null ? null : xmdj.trim();
    }
    public String getXmbm() {
        return xmbm;
    }
    public void setXmbm(String xmbm) {
        this.xmbm = xmbm == null ? null : xmbm.trim();
    }
    public String getXmje() {
        return xmje;
    }
    public void setXmje(String xmje) {
        this.xmje = xmje == null ? null : xmje.trim();
    }
    public String getSl() {
        return sl;
    }
    public void setSl(String sl) {
        this.sl = sl == null ? null : sl.trim();
    }
    public String getSe() {
        return se;
    }
    public void setSe(String se) {
        this.se = se == null ? null : se.trim();
    }
    public String getFphxz() {
        return fphxz;
    }
    public void setFphxz(String fphxz) {
        this.fphxz = fphxz == null ? null : fphxz.trim();
    }
    public String getSpbm() {
        return spbm;
    }
    public void setSpbm(String spbm) {
        this.spbm = spbm == null ? null : spbm.trim();
    }
    public String getZxbm() {
        return zxbm;
    }
    public void setZxbm(String zxbm) {
        this.zxbm = zxbm == null ? null : zxbm.trim();
    }
    public String getYhzcbs() {
        return yhzcbs;
    }
    public void setYhzcbs(String yhzcbs) {
        this.yhzcbs = yhzcbs == null ? null : yhzcbs.trim();
    }
    public String getLslbs() {
        return lslbs;
    }
    public void setLslbs(String lslbs) {
        this.lslbs = lslbs == null ? null : lslbs.trim();
    }
    public String getZzstsgl() {
        return zzstsgl;
    }
    public void setZzstsgl(String zzstsgl) {
        this.zzstsgl = zzstsgl == null ? null : zzstsgl.trim();
    }
    public String getThdh() {
        return thdh;
    }
    public void setThdh(String thdh) {
        this.thdh = thdh == null ? null : thdh.trim();
    }
    public String getDddate() {
        return dddate;
    }
    public void setDddate(String dddate) {
        this.dddate = dddate == null ? null : dddate.trim();
    }
    public String getDdmc() {
        return ddmc;
    }
    public void setDdmc(String ddmc) {
        this.ddmc = ddmc == null ? null : ddmc.trim();
    }
    public String getDw() {
        return dw;
    }
    public void setDw(String dw) {
        this.dw = dw == null ? null : dw.trim();
    }
    public String getDj() {
        return dj;
    }
    public void setDj(String dj) {
        this.dj = dj == null ? null : dj.trim();
    }
    public String getJe() {
        return je;
    }
    public void setJe(String je) {
        this.je = je == null ? null : je.trim();
    }
    public String getZffs() {
        return zffs;
    }
    public void setZffs(String zffs) {
        this.zffs = zffs == null ? null : zffs.trim();
    }
    public String getZflsh() {
        return zflsh;
    }
    public void setZflsh(String zflsh) {
        this.zflsh = zflsh == null ? null : zflsh.trim();
    }
    public String getZfpt() {
        return zfpt;
    }
    public void setZfpt(String zfpt) {
        this.zfpt = zfpt == null ? null : zfpt.trim();
    }
    public String getCygs() {
        return cygs;
    }
    public void setCygs(String cygs) {
        this.cygs = cygs == null ? null : cygs.trim();
    }
    public String getShsj() {
        return shsj;
    }
    public void setShsj(String shsj) {
        this.shsj = shsj == null ? null : shsj.trim();
    }
    public String getWldh() {
        return wldh;
    }
    public void setWldh(String wldh) {
        this.wldh = wldh == null ? null : wldh.trim();
    }
    public String getShdz() {
        return shdz;
    }
    public void setShdz(String shdz) {
        this.shdz = shdz == null ? null : shdz.trim();
    }
    public String getUserid() {
        return userid;
    }
    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }
    public String getCjsj() {
        return cjsj;
    }
    public void setCjsj(String cjsj) {
        this.cjsj = cjsj == null ? null : cjsj.trim();
    }
    public String getFpzt() {
        return fpzt;
    }
    public void setFpzt(String fpzt) {
        this.fpzt = fpzt == null ? null : fpzt.trim();
    }
    public String getFpsfxs() {
        return fpsfxs;
    }
    public void setFpsfxs(String fpsfxs) {
        this.fpsfxs = fpsfxs == null ? null : fpsfxs.trim();
    }
}