package project.graduate.lele.accountbook.event;

/**
 * Created by zhpan on 2017/2/11.
 *   登陆成功EventBus的事件类
 */

public class MsgEvent {
    public  int status;

    public MsgEvent(int status) {
        this.status = status;
    }
}
