package cn.handanlutong.parking.bean;

/**
 * 登录成功返回信息
 * Created by zhangyonggang on 2017/6/8.
 */

public class LoginInfoBean {

    /**
     * result : {"age":"90后","authkey":"12a80c804430d7630013ea579d132482","carnoIds":",5046,5047,5050,5057","createTime":"2017-08-10 14:50:17","fullName":"王大拿","id":100029,"locked":false,"password":"3bd10c016be8515d4a74df901fc7ab6e","phoneNo":"13681378873","salt":"4a31636227384d5a8b8fa6617cb278ab","sex":"0","userImagePath":"http://120.77.202.83:6477/TTparkImg/userImage/100029/1708/100029.png","username":"13681378873"}
     * code : 0
     * type : 用户登录
     */

    private ResultBean result;
    private String code;
    private String type;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static class ResultBean {
        /**
         * age : 90后
         * authkey : 12a80c804430d7630013ea579d132482
         * carnoIds : ,5046,5047,5050,5057
         * createTime : 2017-08-10 14:50:17
         * fullName : 王大拿
         * id : 100029
         * locked : false
         * password : 3bd10c016be8515d4a74df901fc7ab6e
         * phoneNo : 13681378873
         * salt : 4a31636227384d5a8b8fa6617cb278ab
         * sex : 0
         * userImagePath : http://120.77.202.83:6477/TTparkImg/userImage/100029/1708/100029.png
         * username : 13681378873
         */

        private String age;
        private String authkey;
        private String carnoIds;
        private String createTime;
        private String fullName;
        private int id;
        private boolean locked;
        private String password;
        private String phoneNo;
        private String salt;
        private String sex;
        private String userImagePath;
        private String username;

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getAuthkey() {
            return authkey;
        }

        public void setAuthkey(String authkey) {
            this.authkey = authkey;
        }

        public String getCarnoIds() {
            return carnoIds;
        }

        public void setCarnoIds(String carnoIds) {
            this.carnoIds = carnoIds;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isLocked() {
            return locked;
        }

        public void setLocked(boolean locked) {
            this.locked = locked;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhoneNo() {
            return phoneNo;
        }

        public void setPhoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
        }

        public String getSalt() {
            return salt;
        }

        public void setSalt(String salt) {
            this.salt = salt;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getUserImagePath() {
            return userImagePath;
        }

        public void setUserImagePath(String userImagePath) {
            this.userImagePath = userImagePath;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
