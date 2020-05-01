$(function () {

    //标记是否通过验证
    var flag = true;
    /**
     * 响应下一步按钮
     */
    $("#frm").submit(function () {
        verifyEmailAndUserName();
        //判断验证码
        if(flag){
            //获取用户输入的验证码
            var random = $(".form_control").val();
            //异步验证验证码是否正确
            $.post(
                "verifyRandom?random="+random,"text",
                function (data) {
                    if(data.result == "success"){
                        window.location.href = "resetPassword";
                    } else {
                        $(".random_msg").html(data.msg);
                    }
                }
            )
        }
        return false;
    });

    function verifyEmailAndUserName(){
        //验证邮箱与真实姓名
        // var userName = $(".form_userName").val();
        var email = $(".form_email").val();
        var regEmail=/^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/;
        // var regUserName = /^([a-zA-Z][A-Za-z0-9]{5,11})$/;
        var userMsg = $(".user_msg");
        var emailMsg = $(".email_msg");
        var randomMsg = $(".random_msg");
        if (email == "") {
            emailMsg.html("请输入邮箱");
            flag = false;
        }else if (!regEmail.test(email)) {
            emailMsg.html("邮箱格式错误,请输入正确的邮箱");
            $(".form_email").val("");
            flag = false;
        }
    }

    /**
     * 响应验证码按钮
     */
    $(".get_random").click(function () {
        getRandomClick();
    });

    /**
     * 获取验证码单击事件
     */
    function getRandomClick() {
        verifyEmailAndUserName();
        if (flag) {   //通过验证
            var userName = $(".form_userName").val();   //获取真实姓名
            var email = $(".form_email").val();         //获取邮箱
            //异步验证真实姓名与邮箱是否匹配
            $.post(
                "verifyEmailAndName?email=" + email+"&name="+userName, "text",
                function (data) {
                    if (data.result == "success") {
                        settime();
                        //正确，异步发送验证码
                        $.post(
                            "emailSend?email=" + email, "text",
                            function (data) {
                                if (data.result == "success") {
                                    // $(".random_msg").css("color", "#7abd54");
                                    // $(".random_msg").html("邮件已发送，请在五分钟内输入您的验证码...");
                                } else {
                                    $(".random_msg").html("邮件发送失败，请稍后再试...");
                                    setTimeout(function () {
                                        // $(".random_msg").removeAttr("style");
                                        $(".random_msg").html("");
                                    }, 3000);
                                }
                            }
                        )
                    } else {
                        $(".email_msg").html("");
                        $(".email_msg").html("您输入的邮箱与真实姓名不匹配！");
                        $(".form_email").val("");
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
    $(".form_userName,.form_email").focus(function () {
        var userMsg = $(".user_msg");
        var emailMsg = $(".email_msg");
        var randomMsg = $(".random_msg");
        userMsg.html("");
        emailMsg.html("");
        randomMsg.html("");
    });

    /**
     * 响应确定修改密码按钮
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
            repwd.val("");
            flag = false;
        }
        //验证通过
        if(flag){
            // alert(pwd.val());
            var getData = $("#resetPasswordForm").serialize();
            //异步更新密码
            $.post(
                "updateClientPassword?"+getData,function (data) {
                    if(data.result == "success"){
                        window.location.href = "successPwd";
                    }else{
                        $(".repwd_msg").html("");
                        $(".repwd_msg").html("系统繁忙请稍后再试！");
                    }
                }
            )
        }
    })
});