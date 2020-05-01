package cn.li.component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author: Mr.Wang
 * @date: 2020/02/16
 * @time: 18:12
 * @comment: 处理WebSocket
 */
@ServerEndpoint("/websocket")
public class WebSocketHandle {

    //记录当前在线连接数。线程安全的。
    private static int onlineCount = 0;

    //Set使用Set确保线程安全，用来存放每个客户端对应的WebSocketHandle对象。
    private static CopyOnWriteArraySet<WebSocketHandle> webSocketSet = new CopyOnWriteArraySet<WebSocketHandle>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message) {
        System.out.println("来自客户端的消息:" + message);

        //群发消息
        for (WebSocketHandle item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    /**
     * 在线人数累加，设置同步锁确保线程安全
     */
    public static synchronized void addOnlineCount() {
        WebSocketHandle.onlineCount++;
    }

    /**
     * 在线人数累减，设置同步锁确保线程安全
     */
    public static synchronized void subOnlineCount() {
        WebSocketHandle.onlineCount--;
    }
    
}
