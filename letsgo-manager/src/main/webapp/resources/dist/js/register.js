$(function () {
    $('input').iCheck({
        checkboxClass: 'icheckbox_square-blue',
        radioClass: 'iradio_square-blue',
        increaseArea: '20%' // optional
    });
    $('input').focus(function(){
        $("#errorServer").hide();
    })
    $('#registerForm').bootstrapValidator({
        message: '填写信息格式不正确',
        feedbackIcons: {
            valid: '',
            invalid: '',
            validating: ''
        },
        fields: {
            userName: {
                message: '用户名格式不正确',
                validators: {
                    notEmpty: {
                        message: '用户名不能为空'
                    },
                    stringLength: {
                        min: 6,
                        max: 30,
                        message: '用户名不能少于6个字符并且超过30个字符'
                    },
                    threshold :  6 , //有6字符以上才发送ajax请求，（input中输入一个字符，插件会向服务器发送一次，设置限制，6字符以上才开始）
                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}
                        url: CONTEXTPATH + '/usernameRepeatCheck',//验证地址
                        message: '用户已存在',//提示消息
                        delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                        type: 'GET'//请求方式
                        /**自定义提交数据，默认值提交当前input value
                         *  data: function(validator) {
                               return {
                                   password: $('[name="passwordNameAttributeInYourForm"]').val(),
                                   whatever: $('[name="whateverNameAttributeInYourForm"]').val()
                               };
                            }
                         */
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_]+$/,
                        message: '用户名由数字字母下划线组成'
                    }
                }
            },
            password: {
                message: '密码格式不正确',
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    },
                    stringLength: {
                        min: 6,
                        max: 30,
                        message: '密码不能少于6个字符并且超过30个字符'
                    },
                    identical: {//相同
                        field: 'password', //需要进行比较的input name值
                        message: '两次密码不一致'
                    },
                    different: {//不能和用户名相同
                        field: 'userName',//需要进行比较的input name值
                        message: '不能和用户名相同'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_\.]+$/,
                        message: 'The username can only consist of alphabetical, number, dot and underscore'
                    }
                }
            },
            repassword:{
                message: '确认密码和密码不一致',
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    },
                    stringLength: {
                        min: 6,
                        max: 30,
                        message: '密码不能少于6个字符并且超过30个字符'
                    },
                    identical: {//相同
                        field: 'password',
                        message: '两次密码不一致'
                    },
                    different: {//不能和用户名相同
                        field: 'userName',
                        message: '不能和用户名相同'
                    },
                    regexp: {//匹配规则
                        regexp: /^[a-zA-Z0-9_\.]+$/,
                        message: 'The username can only consist of alphabetical, number, dot and underscore'
                    }
                }
            },
            validationCode: {
                validators: {
                    notEmpty: {
                        message: '动态验证码不能为空'
                    }
                }
            },
            email:{
                validators: {
                    emailAddress:{
                        massge:'Email地址格式不正确'
                    }
                }
            },
            /*address: {
                validators: {
                    notEmpty: {
                        message: '地址不能为空'
                    }
                }
            },*/
            /*contactPerson: {//contactPhone
                validators: {
                    notEmpty: {
                        message: '联系人不能为空'
                    }
                }
            },*/
            contactPhone: {//contactPhone
                validators: {
                    notEmpty: {
                        message: '联系电话不能为空'
                    },
                    threshold :  11 , //有11字符以上才发送ajax请求，（input中输入一个字符，插件会向服务器发送一次，设置限制，6字符以上才开始）
                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}
                        url: CONTEXTPATH + '/travelPhoneRepeatCheck',//验证地址
                        message: '手机号已用过',//提示消息
                        delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                        type: 'GET'//请求方式
                        /**自定义提交数据，默认值提交当前input value
                         *  data: function(validator) {
                               return {
                                   password: $('[name="passwordNameAttributeInYourForm"]').val(),
                                   whatever: $('[name="whateverNameAttributeInYourForm"]').val()
                               };
                            }
                         */
                    },
                    regexp: {
                        regexp: /^(\+86)?((1[3,5,8][0-9])|(14[5,7])|(17[0,1,6,7,8]))\d{8}$/,
                        message: '手机号码格式不正确'
                    }
                }
            },
           /* licenseUrl: {
                validators: {
                    notEmpty: {
                        message: '请上传营业执照副本'
                    }
                }
            },*/
            small: {
                validators: {
                    notEmpty: {
                        message: '请确认阅读并且准守服务协议'
                    }
                }
            }
        }
    }).on('success.form.bv', function (e) {
        // Prevent form submission
        //e.preventDefault();
        var _form = $(e.target);
        _form.ajaxForm({
            success: function (data) {
                // var data=eval("("+data+")");
                if(data.retCode==0){
                    alert(data.retMsg);
                    //     $("[name=jourId]").val(data.scheduleEntity.id);
                    //     $("[name=scheduleId]").val(data.scheduleEntity.id);
                    //     $("[name=teamId]").val(data.scheduleEntity.teamId)
                }else{
                    alert(data.retMsg);
                }

            }})
    });
    //注册页短信发送
    $(".get_password_btn").off().click(function(){
        var phonenum= $.trim($("[name=contactPhone]").val());
        var that=$(this);
        if(!IsEmpty(phonenum)){
            blockUiOpen('请输入手机号');
            return false;
        }else if(!IsMobileNoAlert(phonenum)){
            blockUiOpen('请输入正确的手机号码！');
            return false;
        }
        $.ajax({
            url:CONTEXTPATH+"/sendCodeMessage",
            data:{phone:phonenum},
            type : "GET",
            cache : false,
            dataType : "json",
            success: function(data){
                if(data.retCode==0){
                    blockUiOpen(data.retMsg);
                    onTimer(that);
                }else {
                    blockUiOpen(data.retMsg);
                }
            },
            error:function(){
                blockUiOpen('短信发送失败，请重新获取密码');
            }
        })
    })
    $('#userHeadImg').diyUploadHead2({
        url:CONTEXTPATH + '/cloudFile/uploadCloudPhotos',
        dataType:"json",
        success:function( data ) {
            var data=$(data._raw).find("item").text();
            if($.trim($("[name=licenseUrl]").val())){
                $.ajax({
                    url: CONTEXTPATH + '/cloudFile/deleteCloudPhotos',
                    data: {url: $("[name=licenseUrl]").val()},
                    type: "GET",
                    cache: false,
                    dataType: "json",
                    success: function (data) {
                        if (data.retCode == 0) {
                            //blockUiOpen(data.retMsg);
                        } else {
                           // blockUiOpen(data.retMsg);
                        }
                    },
                    error: function () {
                    }
                })
            }
            $("[name=licenseUrl]").val(data);
        },
        buttonText : '<img src="'+CONTEXTPATH+'/resources/plugins/zyfile/control/images/yyzz.jpg"/>',
        error:function( err ) {
            console.info( err );
        },
        fileNumLimit:100
    });
});
//验证码发送成功后倒计时禁用按钮
function onTimer(obj){
    var i=60;
    var timer=setTimeout(function(){
        obj.text(i--+"秒后重发").attr("disabled","disabled").addClass("disbutton");
        var timerWhile=setTimeout(arguments.callee,1000);
        if(i<0){
            obj.text("获取动态验证码").removeAttr("disabled").removeClass("disbutton");
            clearTimeout(timerWhile)
        }
    },1000)
}
//字符串不能为空
function IsEmpty(string)
{
    var str = string.replace(/\s+/g,"");
    if (str == '')
    {
        return false;
    }
    else
    {
        return str;
    }
}
//验证Email
function IsEmail(String)
{
    var testreg=/^[a-zA-Z0-9_+.-]+\@([a-zA-Z0-9-]+\.)+[a-zA-Z0-9]{2,4}$/;
    if(testreg.test($.trim(String)))
    {
        return true;
    }
    else
    {
        return false;
    }
}
//手机号码验证，无alert
function IsMobileNoAlert(string)
{
    var mobile = IsEmpty(string);
    if (mobile == false) return false;
    if(/^(\+86)?((1[3,5,8][0-9])|(14[5,7])|(17[0,1,6,7,8]))\d{8}$/.test(mobile))
    {
        return true;
    }
    else
    {
        return false;
    }
}