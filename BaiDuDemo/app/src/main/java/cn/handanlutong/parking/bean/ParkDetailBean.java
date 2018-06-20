package cn.handanlutong.parking.bean;

/**
 * 车场详情
 * Created by zhangyonggang on 2017/6/16.
 */

public class ParkDetailBean {

    /**
     * total : 0
     * status : true
     * data : {"info":{"rsUserParkId":null,"tobType":null,"cityId":"93","cityName":null,"type":1,"endTime":"00:00:00","parkStatus":0,"peakSpace":null,"showLevel":null,"manageWay":null,"startTime":"11:50:24","description":"","chargeRuleDesc":"","showCode":"PA20170417114733","isClosed":0,"parkCode":"PA20170417115028","monthlySpace":null,"parkId":"149240102846405748","sbport":null,"exitDesc":null,"image":"http://hd-business-image.oss-cn-beijing.aliyuncs.com/2017/04/17/park/1492400827386.jpg","cost":0.01,"rsType":0,"tenantName":null,"businessModel":1,"detailCount":null,"operateCount":null,"owner":null,"tenantId":"","port":null,"synStatus":null,"operateState":null,"lng":114.475788,"areaId":"","creator":"149187762935802033","totalCount":null,"dis":null,"emptySpace":10,"parkType":3,"createDate":"2017-04-17 11:50:28","lat":36.622557,"eStatus":null,"totalSpace":10,"isPartner":0,"totalTimes":0,"rslUserId":"","ip":null,"pwd":null,"parkName":"赵苑公园","areaName":null,"address":"河北省邯郸市复兴区幸福街63号赵苑公园","isFree":0,"activityDate":null,"entryDesc":null,"fullName":null,"detailPercent":0,"chargeType":0,"user":null,"totalMoney":0,"operateStateStr":null}}
     * code : 0000
     * msg : 操作成功！
     */

    private String total;
    private boolean status;
    private DataBean data;
    private String code;
    private String msg;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * info : {"rsUserParkId":null,"tobType":null,"cityId":"93","cityName":null,"type":1,"endTime":"00:00:00","parkStatus":0,"peakSpace":null,"showLevel":null,"manageWay":null,"startTime":"11:50:24","description":"","chargeRuleDesc":"","showCode":"PA20170417114733","isClosed":0,"parkCode":"PA20170417115028","monthlySpace":null,"parkId":"149240102846405748","sbport":null,"exitDesc":null,"image":"http://hd-business-image.oss-cn-beijing.aliyuncs.com/2017/04/17/park/1492400827386.jpg","cost":0.01,"rsType":0,"tenantName":null,"businessModel":1,"detailCount":null,"operateCount":null,"owner":null,"tenantId":"","port":null,"synStatus":null,"operateState":null,"lng":114.475788,"areaId":"","creator":"149187762935802033","totalCount":null,"dis":null,"emptySpace":10,"parkType":3,"createDate":"2017-04-17 11:50:28","lat":36.622557,"eStatus":null,"totalSpace":10,"isPartner":0,"totalTimes":0,"rslUserId":"","ip":null,"pwd":null,"parkName":"赵苑公园","areaName":null,"address":"河北省邯郸市复兴区幸福街63号赵苑公园","isFree":0,"activityDate":null,"entryDesc":null,"fullName":null,"detailPercent":0,"chargeType":0,"user":null,"totalMoney":0,"operateStateStr":null}
         */

        private InfoBean info;

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public static class InfoBean {
            /**
             * rsUserParkId : null
             * tobType : null
             * cityId : 93
             * cityName : null
             * type : 1
             * endTime : 00:00:00
             * parkStatus : 0
             * peakSpace : null
             * showLevel : null
             * manageWay : null
             * startTime : 11:50:24
             * description :
             * chargeRuleDesc :
             * showCode : PA20170417114733
             * isClosed : 0
             * parkCode : PA20170417115028
             * monthlySpace : null
             * parkId : 149240102846405748
             * sbport : null
             * exitDesc : null
             * image : http://hd-business-image.oss-cn-beijing.aliyuncs.com/2017/04/17/park/1492400827386.jpg
             * cost : 0.01
             * rsType : 0
             * tenantName : null
             * businessModel : 1
             * detailCount : null
             * operateCount : null
             * owner : null
             * tenantId :
             * port : null
             * synStatus : null
             * operateState : null
             * lng : 114.475788
             * areaId :
             * creator : 149187762935802033
             * totalCount : null
             * dis : null
             * emptySpace : 10
             * parkType : 3
             * createDate : 2017-04-17 11:50:28
             * lat : 36.622557
             * eStatus : null
             * totalSpace : 10
             * isPartner : 0
             * totalTimes : 0
             * rslUserId :
             * ip : null
             * pwd : null
             * parkName : 赵苑公园
             * areaName : null
             * address : 河北省邯郸市复兴区幸福街63号赵苑公园
             * isFree : 0
             * activityDate : null
             * entryDesc : null
             * fullName : null
             * detailPercent : 0
             * chargeType : 0
             * user : null
             * totalMoney : 0
             * operateStateStr : null
             */

            private Object rsUserParkId;
            private Object tobType;
            private String cityId;
            private Object cityName;
            private int type;
            private String endTime;
            private int parkStatus;
            private Object peakSpace;
            private Object showLevel;
            private Object manageWay;
            private String startTime;
            private String description;
            private String chargeRuleDesc;
            private String showCode;
            private int isClosed;
            private String parkCode;
            private Object monthlySpace;
            private String parkId;
            private Object sbport;
            private Object exitDesc;
            private String image;
            private double cost;
            private int rsType;
            private Object tenantName;
            private int businessModel;
            private Object detailCount;
            private Object operateCount;
            private Object owner;
            private String tenantId;
            private Object port;
            private Object synStatus;
            private Object operateState;
            private double lng;
            private String areaId;
            private String creator;
            private Object totalCount;
            private Object dis;
            private int emptySpace;
            private int parkType;
            private String createDate;
            private double lat;
            private Object eStatus;
            private int totalSpace;
            private int isPartner;
            private int totalTimes;
            private String rslUserId;
            private Object ip;
            private Object pwd;
            private String parkName;
            private Object areaName;
            private String address;
            private int isFree;
            private Object activityDate;
            private Object entryDesc;
            private Object fullName;
            private int detailPercent;
            private int chargeType;
            private Object user;
            private int totalMoney;
            private Object operateStateStr;

            public Object getRsUserParkId() {
                return rsUserParkId;
            }

            public void setRsUserParkId(Object rsUserParkId) {
                this.rsUserParkId = rsUserParkId;
            }

            public Object getTobType() {
                return tobType;
            }

            public void setTobType(Object tobType) {
                this.tobType = tobType;
            }

            public String getCityId() {
                return cityId;
            }

            public void setCityId(String cityId) {
                this.cityId = cityId;
            }

            public Object getCityName() {
                return cityName;
            }

            public void setCityName(Object cityName) {
                this.cityName = cityName;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public int getParkStatus() {
                return parkStatus;
            }

            public void setParkStatus(int parkStatus) {
                this.parkStatus = parkStatus;
            }

            public Object getPeakSpace() {
                return peakSpace;
            }

            public void setPeakSpace(Object peakSpace) {
                this.peakSpace = peakSpace;
            }

            public Object getShowLevel() {
                return showLevel;
            }

            public void setShowLevel(Object showLevel) {
                this.showLevel = showLevel;
            }

            public Object getManageWay() {
                return manageWay;
            }

            public void setManageWay(Object manageWay) {
                this.manageWay = manageWay;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getChargeRuleDesc() {
                return chargeRuleDesc;
            }

            public void setChargeRuleDesc(String chargeRuleDesc) {
                this.chargeRuleDesc = chargeRuleDesc;
            }

            public String getShowCode() {
                return showCode;
            }

            public void setShowCode(String showCode) {
                this.showCode = showCode;
            }

            public int getIsClosed() {
                return isClosed;
            }

            public void setIsClosed(int isClosed) {
                this.isClosed = isClosed;
            }

            public String getParkCode() {
                return parkCode;
            }

            public void setParkCode(String parkCode) {
                this.parkCode = parkCode;
            }

            public Object getMonthlySpace() {
                return monthlySpace;
            }

            public void setMonthlySpace(Object monthlySpace) {
                this.monthlySpace = monthlySpace;
            }

            public String getParkId() {
                return parkId;
            }

            public void setParkId(String parkId) {
                this.parkId = parkId;
            }

            public Object getSbport() {
                return sbport;
            }

            public void setSbport(Object sbport) {
                this.sbport = sbport;
            }

            public Object getExitDesc() {
                return exitDesc;
            }

            public void setExitDesc(Object exitDesc) {
                this.exitDesc = exitDesc;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public double getCost() {
                return cost;
            }

            public void setCost(double cost) {
                this.cost = cost;
            }

            public int getRsType() {
                return rsType;
            }

            public void setRsType(int rsType) {
                this.rsType = rsType;
            }

            public Object getTenantName() {
                return tenantName;
            }

            public void setTenantName(Object tenantName) {
                this.tenantName = tenantName;
            }

            public int getBusinessModel() {
                return businessModel;
            }

            public void setBusinessModel(int businessModel) {
                this.businessModel = businessModel;
            }

            public Object getDetailCount() {
                return detailCount;
            }

            public void setDetailCount(Object detailCount) {
                this.detailCount = detailCount;
            }

            public Object getOperateCount() {
                return operateCount;
            }

            public void setOperateCount(Object operateCount) {
                this.operateCount = operateCount;
            }

            public Object getOwner() {
                return owner;
            }

            public void setOwner(Object owner) {
                this.owner = owner;
            }

            public String getTenantId() {
                return tenantId;
            }

            public void setTenantId(String tenantId) {
                this.tenantId = tenantId;
            }

            public Object getPort() {
                return port;
            }

            public void setPort(Object port) {
                this.port = port;
            }

            public Object getSynStatus() {
                return synStatus;
            }

            public void setSynStatus(Object synStatus) {
                this.synStatus = synStatus;
            }

            public Object getOperateState() {
                return operateState;
            }

            public void setOperateState(Object operateState) {
                this.operateState = operateState;
            }

            public double getLng() {
                return lng;
            }

            public void setLng(double lng) {
                this.lng = lng;
            }

            public String getAreaId() {
                return areaId;
            }

            public void setAreaId(String areaId) {
                this.areaId = areaId;
            }

            public String getCreator() {
                return creator;
            }

            public void setCreator(String creator) {
                this.creator = creator;
            }

            public Object getTotalCount() {
                return totalCount;
            }

            public void setTotalCount(Object totalCount) {
                this.totalCount = totalCount;
            }

            public Object getDis() {
                return dis;
            }

            public void setDis(Object dis) {
                this.dis = dis;
            }

            public int getEmptySpace() {
                return emptySpace;
            }

            public void setEmptySpace(int emptySpace) {
                this.emptySpace = emptySpace;
            }

            public int getParkType() {
                return parkType;
            }

            public void setParkType(int parkType) {
                this.parkType = parkType;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            public Object getEStatus() {
                return eStatus;
            }

            public void setEStatus(Object eStatus) {
                this.eStatus = eStatus;
            }

            public int getTotalSpace() {
                return totalSpace;
            }

            public void setTotalSpace(int totalSpace) {
                this.totalSpace = totalSpace;
            }

            public int getIsPartner() {
                return isPartner;
            }

            public void setIsPartner(int isPartner) {
                this.isPartner = isPartner;
            }

            public int getTotalTimes() {
                return totalTimes;
            }

            public void setTotalTimes(int totalTimes) {
                this.totalTimes = totalTimes;
            }

            public String getRslUserId() {
                return rslUserId;
            }

            public void setRslUserId(String rslUserId) {
                this.rslUserId = rslUserId;
            }

            public Object getIp() {
                return ip;
            }

            public void setIp(Object ip) {
                this.ip = ip;
            }

            public Object getPwd() {
                return pwd;
            }

            public void setPwd(Object pwd) {
                this.pwd = pwd;
            }

            public String getParkName() {
                return parkName;
            }

            public void setParkName(String parkName) {
                this.parkName = parkName;
            }

            public Object getAreaName() {
                return areaName;
            }

            public void setAreaName(Object areaName) {
                this.areaName = areaName;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getIsFree() {
                return isFree;
            }

            public void setIsFree(int isFree) {
                this.isFree = isFree;
            }

            public Object getActivityDate() {
                return activityDate;
            }

            public void setActivityDate(Object activityDate) {
                this.activityDate = activityDate;
            }

            public Object getEntryDesc() {
                return entryDesc;
            }

            public void setEntryDesc(Object entryDesc) {
                this.entryDesc = entryDesc;
            }

            public Object getFullName() {
                return fullName;
            }

            public void setFullName(Object fullName) {
                this.fullName = fullName;
            }

            public int getDetailPercent() {
                return detailPercent;
            }

            public void setDetailPercent(int detailPercent) {
                this.detailPercent = detailPercent;
            }

            public int getChargeType() {
                return chargeType;
            }

            public void setChargeType(int chargeType) {
                this.chargeType = chargeType;
            }

            public Object getUser() {
                return user;
            }

            public void setUser(Object user) {
                this.user = user;
            }

            public int getTotalMoney() {
                return totalMoney;
            }

            public void setTotalMoney(int totalMoney) {
                this.totalMoney = totalMoney;
            }

            public Object getOperateStateStr() {
                return operateStateStr;
            }

            public void setOperateStateStr(Object operateStateStr) {
                this.operateStateStr = operateStateStr;
            }
        }
    }
}
