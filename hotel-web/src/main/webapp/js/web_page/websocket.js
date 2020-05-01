var websocket = null;
//获取路径
var host = document.location.host;
//获取路径
var pathName=window.document.location.pathname;
//截取，得到项目名称
var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
if ('WebSocket' in window) {
    websocket = new WebSocket("ws://"+host+projectName+"/websocket");
} else {
    console.log("不支持WebSocket！");
}

//连接发生错误的回调方法
websocket.onerror = function () {
    console.log("WebSocket连接发生错误");
};

//连接成功建立的回调方法
websocket.onopen = function () {
    console.log("WebSocket连接成功");
};

//连接关闭的回调方法
websocket.onclose = function () {
    console.log("WebSocket连接关闭");
};

//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function () {
    closeWebSocket();
};

//关闭WebSocket连接
function closeWebSocket() {
    websocket.close();
}

//发送消息
function send(msg) {
    websocket.send(msg);
}