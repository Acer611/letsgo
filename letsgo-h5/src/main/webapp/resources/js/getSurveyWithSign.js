$(document).ready(function() {
    if($(".pingfen span").css("width")>="80px" && screen.width<330){
        $(".pingfen").css("margin-bottom","30px");
    }
    //iCheck
    $('input').iCheck({
        checkboxClass: 'icheckbox_minimal-blue',
        radioClass: 'iradio_minimal-blue',
        increaseArea: '20%' // optional
    });
    //背景灰色
    $(".itemList ul li").hover(function(){
        $(this).css("background","#f0eff5");
    },function(){
        $(this).css("background","transparent");
    });
    $("ins").hover(function(){
        $(this).parent().parent().css("background","#f0eff5");
    },function(){
        $(this).parent().parent().css("background","transparent");
    });
    //问题号
    //var len = $(".quesTitle span").length;//总长度
    //for(var i=0;i<len;i++){
    //    $(".quesTitle span").eq(i).html(i+1+".");
    //}
    $("#confirmSurvey").on("click",function(){
        layer.open({
            btn: ['确认', '取消'],
            content:"满意度调查问卷确认无误？",
            success: function(elem){
                //console.log(elem);
            },
            shadeClose:false,
            yes: function(index){
                $.ajax({
                    type: 'POST',
                    url: CONTEXTPATH+'/web/survey/confirmSurvey?surveyUserId='+getQueryString("surveyUserId")+'&client_id='+getQueryString("client_id")+'&ver='+getQueryString("ver")+'&timestamp='+getQueryString("timestamp")+'&sign='+getQueryString("sign")+'&access_token='+getQueryString("access_token"),
                    //data:JSON.stringify(dataStr),
                    contentType:'application/json;charset=utf-8',
                    dataType: 'json',
                    // timeout: 3000,
                    context: $('body'),
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
                                yes: function(index){
                                    window.location.reload();
                                },
                                content:"确认问卷成功"
                            })
                        }else{
                            //alert(data.retMsg);
                            layer.open({
                                btn: ['确定'],
                                content:data.retMsg
                            })
                        }
                    },
                    error: function(xhr, type){
                        alert('网络错误')
                    }
                })
                layer.close(index)
            },
            no: function(index){
                layer.close(index)
            }
        })

    })
});
//获取url参数的值：name是参数名
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return (r[2]);
    }
    return null;
}