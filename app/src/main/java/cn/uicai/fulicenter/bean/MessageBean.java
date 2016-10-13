package cn.uicai.fulicenter.bean;

/**
 * Created by xiaomiao on 2016/10/14.
 */
public class MessageBean {

    /**
     * success : true
     * msg : 收藏成功
     */

    private boolean success;
    private String msg;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public MessageBean() {
    }

    @Override
    public String toString() {
        return "MessageBean{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                '}';
    }
}
