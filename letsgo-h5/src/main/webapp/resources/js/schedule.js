//获取客服电话
var _telephone="";
var flag=GetRequest(window.location.href);
var getCustomerServicePhone_url=CONTEXTPATH+'/web/schedule/getCustomerServicePhone?'+parseParam(flag);
var confirmSchedule_url=CONTEXTPATH+ '/web/schedule/confirmSchedule?'+parseParam(flag);
$(function(){
    if(iswechat!="Wechat") {
        $.ajax({
            type: 'get',
            url: getCustomerServicePhone_url,
            //data:JSON.stringify({tId:"$!{response.scheduleEntity.teamId}"}),
            contentType: 'application/json;charset=utf-8',
            dataType: 'json',
            // timeout: 3000,
            context: $('body'),
            success: function (data) {
                _telephone = data;
            },
            error: function (xhr, type) {
                alert('网络错误')
            }
        })
    }
    $("#errorSchedule").on("click",function(){
        layer.open({
            btn: ['拨打', '取消'],
            shadeClose:false,
            content:"行程确认有误？可直接拨打我们的客服电话联系我们："+_telephone,
            shadeClose:false,
            no: function(index){
                layer.close(index)
            },
            yes: function(index){
               $(".layui-m-layerbtn").attr("href","tel:"+_telephone)
            },
            success: function(elem){
               // console.log(elem);
                $(elem).find(".layui-m-layerbtn").find("span").eq(1).empty().append("<a href='tel:"+_telephone+"'>拨打</a>");
            }
        })
    })
    //确认行程
    $("#confirmSchedule").on("click",function(){
        layer.open({
            btn: ['确认', '取消'],
            shadeClose: false,
            content:"行程确认无误后将发送短信邀请团队成员加入？",
            success: function(elem){
               // console.log(elem);
            },
            yes: function(index){
                layer.open({type: 2,shadeClose:false});
                $.ajax({
                    type: 'POST',
                    url:confirmSchedule_url,
                    data:JSON.stringify({teamId:teamId}),
                    contentType:'application/json;charset=utf-8',
                    dataType: 'json',
                    // timeout: 3000,
                    context: $('body'),
                    shadeClose:false,
                    success: function(data){
                        // Supposing this JSON payload was received:
                        //   {"project": {"id": 42, "html": "<div>..." }}
                        // append the HTML to context object.
                        //this.append(data.project.html)
                        if(data.retCode==0) {
                            // window.location.href = '$!{link.contextPath}$!{link.requestPath}?flag=preview&teamId=$!{response.teamId}&scheduleDetaildId=$!{response.scheduleDetailEntity.id}&client_id=${response.signMap.get("$!{response.scheduleDetailEntity.id}preview").client_id}&ver=${response.signMap.get("$!{response.scheduleDetailEntity.id}preview").ver}&sign=${response.signMap.get("$!{response.scheduleDetailEntity.id}preview").sign}&timestamp=${response.signMap.get("$!{response.scheduleDetailEntity.id}preview").timestamp}&access_token=${response.signMap.get("$!{response.scheduleDetailEntity.id}preview").access_token}';
                            //window.location.reload();
                            layer.open({
                                btn: ['确定'],
                                shadeClose:false,
                                yes: function(index){
                                    finishClosed();
                                },
                                content:data.retMsg
                            })
                        }else{
                            //alert(data.retMsg);
                            layer.open({
                                btn: ['确定'],
                                shadeClose:false,
                                content:data.retMsg
                            })
                        }
                    },
                    error: function(xhr, type){
                        alert('网络错误');
                        layer.closeAll();
                    }
                })
                layer.close(index)
            },
            no: function(index){
                layer.close(index)
            }
        })

    })
})
function finishClosed() {
    javascript:control.finishClosed();
}

function GetRequest() {
    var url = location.search; //获取url中"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for(var i = 0; i < strs.length; i ++) {
            theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
        }
    }
    return theRequest;
}

function parseParam(param, key) {

    var paramStr = "";

    if (param instanceof String || param instanceof Number || param instanceof Boolean)
    {

        paramStr += "&" + key + "=" + encodeURIComponent(param);

    }
    else
    {

        $.each(param, function (i) {

            var k = key == null ? i : key + (param instanceof Array ? "[" + i + "]" : "." + i);
            paramStr += '&' + parseParam(this, k);

        });

    }

    return paramStr.substr(1);
}

/**
 * 获取指定的URL参数值
 * URL:http://www.quwan.com/index?name=tyler
 * 参数：paramName URL参数
 * 调用方法:getParam("name")
 * 返回值:tyler
 */
function getParam(paramName) {
    paramValue = "", isFound = !1;
    if (this.location.search.indexOf("?") == 0 && this.location.search.indexOf("=") > 1) {
        arrSource = unescape(this.location.search).substring(1, this.location.search.length).split("&"), i = 0;
        while (i < arrSource.length && !isFound) arrSource[i].indexOf("=") > 0 && arrSource[i].split("=")[0].toLowerCase() == paramName.toLowerCase() && (paramValue = arrSource[i].split("=")[1], isFound = !0), i++
    }
    return paramValue == "" && (paramValue = null), paramValue
}
//获取url参数的值：name是参数名
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return (r[2]);
    }
    return null;
}