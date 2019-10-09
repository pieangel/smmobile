package signalmaster.com.smmobile.notuse;

public class SmLogin {
    private String reg_id;
    private Info user_info;

    public String getReg_id() {
        return reg_id;
    }

    public void setReg_id(String reg_id) {
        this.reg_id = reg_id;
    }

    public Info getUser_info() {
        return user_info;
    }

    public void setUser_info(Info user_info) {
        this.user_info = user_info;
    }

    public class Info {
        private String id;
        private String pwd;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }
    }

}
