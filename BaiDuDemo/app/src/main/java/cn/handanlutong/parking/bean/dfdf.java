package cn.handanlutong.parking.bean;

/**
 * Created by zhangyonggang on 2018/3/6.
 */

public class dfdf {


    /**
     * message : 操作成功
     * result : {"age":"","authkey":"2d5213dd5b331daf477d49dcec9df4b6","carnoIds":",","createTime":"2018-03-06 09:56:41","fullName":"13681378873","id":100577100585,"locked":false,"password":"254e5b482545345a8fa8576244e2ab78","phoneNo":"13681378873","salt":"f396f6040c604ca18344723546a5994a","sex":"","userImagePath":"","username":"13681378873","yhye":0}
     * code : 0000
     */

    private String message;
    private ResultBean result;
    private String code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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

    public static class ResultBean {
        /**
         * age :
         * authkey : 2d5213dd5b331daf477d49dcec9df4b6
         * carnoIds : ,
         * createTime : 2018-03-06 09:56:41
         * fullName : 13681378873
         * id : 100577100585
         * locked : false
         * password : 254e5b482545345a8fa8576244e2ab78
         * phoneNo : 13681378873
         * salt : f396f6040c604ca18344723546a5994a
         * sex :
         * userImagePath :
         * username : 13681378873
         * yhye : 0
         */

        private String age;
        private String authkey;
        private String carnoIds;
        private String createTime;
        private String fullName;
        private long id;
        private boolean locked;
        private String password;
        private String phoneNo;
        private String salt;
        private String sex;
        private String userImagePath;
        private String username;
        private int yhye;

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

        public long getId() {
            return id;
        }

        public void setId(long id) {
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

        public int getYhye() {
            return yhye;
        }

        public void setYhye(int yhye) {
            this.yhye = yhye;
        }
    }
}
