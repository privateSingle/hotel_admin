$(function () {

    //标记是否通过验证
    var flag = true;
    /**
     * 响应下一步按钮
     */
    $("#frm").submit(function () {
        verifyPhoneAndUserName();
        //判断验证码
        if(flag){
            //获取用户输入的验证码
            var random = $(".form_control").val();
            //异步验证验证码是否正确
            $.post(
                "UtilServlet?opr=verifyRandom&random="+random,"text",
                function (data) {
                    if(data == "0"){
                        window.location.href = "resetPassword.html";
                    }else if(data == "1"){
                        $(".random_msg").html("您的验证码输入有误请重新核对！");
                    }else if(data == "-1"){
                        $(".random_msg").html("验证码已失效请重新获取！");
                    }
                }
            )
        }
        return false;
    });

    function verifyPhoneAndUserName(){
        //验证手机号与真实姓名
        var userName = $(".form_userName").val();
        var phone = $(".form_phone").val();
        var regPhone=/^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/;
        var regUserName = /^([a-zA-Z][A-Za-z0-9]{5,11})$/;
        var userMsg = $(".user_msg");
        var phoneMsg = $(".phone_msg");
        var randomMsg = $(".random_msg");
        if (userName == "") {
            userMsg.html("请输入真实姓名");
            flag = false;
        }else if (!regUserName.test(userName)) {
            userMsg.html("请输入正确的真实姓名(首字母为英文,6-12位)");
            $(".form_userName").val("");
            flag = false;
        }
        if (phone == "") {
            phoneMsg.html("请输入手机号");
            flag = false;
        }else if (!regPhone.test(phone)) {
            phoneMsg.html("手机号格式错误,请输入正确的手机号");
            $(".form_phone").val("");
            flag = false;
        }
    }

    /**
     * 响应验证码按钮
     */
    $(".get_random").bind("click",function () {
        getRandomClick();
    });

    /**
     * 获取验证码单击事件
     */
    function getRandomClick() {
        verifyPhoneAndUserName();
        if (flag) {   //通过验证
            var userName = $(".form_userName").val();   //获取真实姓名
            var phone = $(".form_phone").val();         //获取手机号
            //异步验证真实姓名与手机号是否匹配
            $.post(
                "EasyBuyUserServlet?opr=verifyPhone&userName=" + userName + "&phone=" + phone, "text",
                function (data) {
                    // alert(data);
                    if (data == "true") {
                        //正确，异步发送验证码
                        $.post(
                            "UtilServlet?opr=phoneRandom&phone=" + phone, "text",
                            function (data) {
                                if (data == "true") {
                                    $(".random_msg").css("color", "#7abd54");
                                    $(".random_msg").html("短信已发送，请在五分钟内输入您的验证码...");
                                } else {
                                    $(".random_msg").html("短信发送失败，请稍后再试...");
                                }
                                setTimeout(function () {
                                    $(".random_msg").removeAttr("style");
                                    $(".random_msg").html("");
                                }, 3000);
                                settime();
                            }
                        )
                    } else {
                        $(".phone_msg").html("");
                        $(".phone_msg").html("您输入的手机号与真实姓名不匹配！");
                        $(".form_phone").val("");
                    }
                })
        }
    }

    //设置倒计时时间
    var countdown=60;
    //获取按钮
    var _generate_code = $(".get_random");
    function settime() {
        if (countdown == 0) {
            _generate_code.bind("click",function () {
                getRandomClick();
            });
            _generate_code.val("获取验证码");
            countdown = 60;
            return false;
        } else {
            _generate_code.unbind("click");
            _generate_code.val("重新发送(" + countdown + ")");
            countdown--;
        }
        setTimeout(function() {
            settime();
        },1000);
    }

    /**
     * 响应取消按钮和返回按钮
     */
    $(".off_btn,.back_button").click(function () {
        window.location.href = "findPassword.html";
    });

    /**
     * 获取光标时清空提示语句
     */
    $(".form_userName,.form_phone").focus(function () {
        var userMsg = $(".user_msg");
        var phoneMsg = $(".phone_msg");
        var randomMsg = $(".random_msg");
        userMsg.html("");
        phoneMsg.html("");
        randomMsg.html("");
    });

    /**
     * 响应确定按钮
     */
    $(".ok_btn").click(function () {
        var flag = true;
        //验证密码框
        var pwd = $(".pwd");
        var repwd = $(".repwd");
        var regPwd =/^[\w_-]{6,16}$/;
        $(".pwd_msg").html("");
        $(".repwd_msg").html("");
        if(pwd.val()==""){
            $(".pwd_msg").html("请输入密码");
            flag = false;
        }else if (!regPwd.test(pwd.val())) {
            $("#pid").html("密码格式不正确(可包含大小写字母数字和'_'-',6-16位)");
            pwd.val("");
            flag = false;
        }
        if(repwd.val()==""){
            $(".repwd_msg").html("请输入确认密码");
            flag = false;
        }else if (repwd.val() != pwd.val()) {
            $(".repwd_msg").html("请确认密码,两次密码输入不同");
            rePwd.val("");
            flag = false;
        }
        //验证通过
        if(flag){
            // alert(pwd.val());
            //异步更新密码
            $.post(
                "EasyBuyUserServlet?opr=updatePassword&pwd="+pwd.val(),"text",function (data) {
                    if(data == "true"){
                        window.location.href = "successPwd.html";
                    }else{
                        $(".repwd_msg").html("");
                        $(".repwd_msg").html("系统繁忙请稍后再试！");
                    }
                }
            )
        }
    })
});