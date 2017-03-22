$(document).ready(function(){
    //iCheck
    $('input').iCheck({
        checkboxClass: 'icheckbox_square-blue',
        radioClass: 'iradio_square-blue',
        increaseArea: '20%' // optional
    });
    //点击删除---事件代理
    $(".quesNum").on("click",".question button",function(){
        $(this).parent().parent().hide();
        if($(this).parent().parent().attr("id")=="allQue"){
            $('#allpj').iCheck('uncheck');
        }
        if($(this).parent().parent().attr("id")=="hotelQue"){
            $('#hotelpj').iCheck('uncheck');
        }
        if($(this).parent().parent().attr("id")=="fullQue"){
            $('#foodpj').iCheck('uncheck');
        }
        if($(this).parent().parent().attr("id")=="jdQue"){
            $('#secpj').iCheck('uncheck');
        }
        if($(this).parent().parent().attr("id")=="severQue"){
            $('#severpj').iCheck('uncheck');
        }
        if($(this).parent().parent().attr("id")=="ldQue"){
            $('#severpj').iCheck('uncheck');
        }
        if($(this).parent().parent().attr("id")=="dyQue"){
            $('#severpj').iCheck('uncheck');
        }
        if($(this).parent().parent().attr("id")=="sjQue"){
            $('#severpj').iCheck('uncheck');
        }
        var len = $(".question:visible .quesTitle span").length;//总长度
        for(var i=0;i<len;i++){
            $(".question:visible .quesTitle span").eq(i).html(i+1);
        }
        $(".quesNum").eq(0).find(".question:visible:last").css("border","none");
    });
    //点击删除x
    $(".form-horizontal").on("click",".itemAdd i",function(){
        $(this).parent().remove();
        var len = $(".itemAdd label span").length;
        for(var i=0;i<len;i++){
            $(".itemAdd label span").eq(i).html(i+1);
        }
    });
    var len = $(".question .quesTitle span").length;//总长度
    for(var china,i=0;i<len;i++){

        $(".question .quesTitle span").eq(i).html(i+1);
    };
    //添加选项
    $(".addItem").on("click",function(){
        var len = $(".itemAdd label span").length;
        for(var i=0;i<len;i++){
            $(".itemAdd label span").eq(i).html(i+1);
        }
        var str='<div class="form-group itemAdd" style="margin-top:20px;">'+
            '<label for="" class="col-sm-1 control-label additem">'+'选项<span>'+(i+1)+'</span>、'+'</label>'+
            '<i class="fa fa-times" style="margin-top:10px;"></i>'+
            '<div class="col-sm-3">'+
            ' <input type="text" class="form-control" placeholder="输入内容...">'+
            ' </div>'+
            '</div>';

        $(".brforeInsert").before(str);
    });
    $("select[name=questionType]").on("change",function(){
        if($(this).val()==3){
            $(".itemAdd").hide();
            $(".brforeInsert").hide();
        }else{
            $(".itemAdd").show();
            $(".brforeInsert").show();
        }
    });
    $("#deleteQuestion input").on('ifClicked', function(event){

        if($(this).attr("id")=="allpj"){
            if(!$(this).is(":checked")){
                $("#allQue").show();
            }else{
                $("#allQue").hide();
            }
        }
        if($(this).attr("id")=="hotelpj"){
            if(!$(this).is(":checked")){
                $("#hotelQue").show();
            }else{
                $("#hotelQue").hide();
            }
        }
        if($(this).attr("id")=="foodpj"){
            if(!$(this).is(":checked")){
                $("#fullQue").show();
            }else{
                $("#fullQue").hide();
            }
        }
        if($(this).attr("id")=="secpj"){
            if(!$(this).is(":checked")){
                $("#jdQue").show();
            }else{
                $("#jdQue").hide();
            }
        }
        if($(this).attr("id")=="severpj"){
            if(!$(this).is(":checked")){
                $("#severQue").show();
                $("#ldQue").show();
                $("#dyQue").show();
                $("#sjQue").show();
            }else{
                $("#severQue").hide();
                $("#ldQue").hide();
                $("#dyQue").hide();
                $("#sjQue").hide();
            }
        }
        if($(".question").is(":visible")) {
            var len = $(".question:visible .quesTitle span").length;//总长度
            for (var i = 0; i < len; i++) {
                $(".question:visible .quesTitle span").eq(i).html(i + 1);
            }
        }
    });
    //提交问题
    $(".tijiao").on("click",function(){
        if($(".addQues .writeQues").val() == ""){
            $("#dialogModal").find(".modal-body>p").html("请填写要提交的问题");
            blockUIOpenShare("dialogModal");
            return false;
        }
        if($(".addQues .itemAdd").length<1||$(".addQues .itemAdd").find("input").val() == ""){
            if( $("select[name=questionType]").val()!=3) {
                $("#dialogModal").find(".modal-body>p").html("请填写要提交的问题的选项");
                blockUIOpenShare("dialogModal");
                return false;
            }
        }
            var len = $(".question:visible .quesTitle span").length;//总长度
            for(var i=0;i<len;i++){
                $(".question:visible .quesTitle span").eq(i).html(i+1);
            }
            var questionType=$("select[name=questionType]").val();
            if(questionType!=3) {
                var add1 = $(".addQues .writeQues").val();
                var add2 = $(".addQues .itemAdd").eq(0).find("input").val();
                var add3 = $(".addQues .itemAdd").eq(1).find("input").val();
                var add4 = $(".addQues .itemAdd").eq(2).find("input").val();
                var add5 = $(".addQues .itemAdd").eq(3).find("input").val();
                var add6 = $(".addQues .itemAdd").eq(4).find("input").val();
                var add7 = $(".addQues .itemAdd").eq(5).find("input").val();
                var add8 = $(".addQues .itemAdd").eq(6).find("input").val();
                var add9 = $(".addQues .itemAdd").eq(7).find("input").val();
                var add10 = $(".addQues .itemAdd").eq(8).find("input").val();
                var add11 = $(".addQues .itemAdd").eq(9).find("input").val();
                var str = '<div class="question col-md-10 col-md-offset-1">' +
                    '<h3 class="quesTitle col-md-12">' +
                    '<div class="col-md-10" style="display:inline;">问题<span>' + (i + 1) + '</span>：<p class="questionTitle">' + add1 + '</p></div>' +
                    '<input type="hidden" value="' + questionType + '" name="questionTypesub"/>' +
                    '<button type="button" class="col-md-2 btn btn-block btn-danger btn-sm" style="width:70px;display:inline;">删除</button>' +
                    '</h3>' +
                    '<ul class="col-md-10">' +
                    '<li class="col-md-2 col-md-offset-1"><span>A、</span><p>' + add2 + '</p></li>' +
                    '<li class="col-md-2"><span>B、</span><p>' + add3 + '</li>' +
                    '</ul>' +
                    '</div>';
                $("#questionsub ").append(str);
                if ($(".itemAdd").length == 3) {
                    $(".zidingyi .question:last-child").find("ul li:last-child").after('<li class="col-md-2"><span>C、</span><p>' + add4 + '</p></li>');
                } else if ($(".itemAdd").length == 4) {
                    $(".zidingyi .question:last-child").find("ul li:last-child").after('<li class="col-md-2"><span>C、</span><p>' + add4 + '</p></li><li class="col-md-2"><span>D、</span><p>' + add5 + '</p></li>');
                } else if ($(".itemAdd").length == 5) {
                    $(".zidingyi .question:last-child").find("ul li:last-child").after('<li class="col-md-2"><span>C、</span><p>' + add4 + '</p></li><li class="col-md-2"><span>D、</span><p>' + add5 + '</p></li><li class="col-md-2"><span>E、</span><p>' + add6 + '</p></li>');
                } else if ($(".itemAdd").length == 6) {
                    $(".zidingyi .question:last-child").find("ul li:last-child").after('<li class="col-md-2"><span>C、</span><p>' + add4 + '</p></li><li class="col-md-2"><span>D、</span><p>' + add5 + '</p></li><li class="col-md-2"><span>E、</span><p>' + add6 + '</p></li><li class="col-md-2 col-md-offset-1"><span>F、</span><p>' + add7 + '</p></li>');
                } else if ($(".itemAdd").length == 7) {
                    $(".zidingyi .question:last-child").find("ul li:last-child").after('<li class="col-md-2"><span>C、</span><p>' + add4 + '</p></li><li class="col-md-2"><span>D、</span><p>' + add5 + '</p></li><li class="col-md-2"><span>E、</span><p>' + add6 + '</p></li><li class="col-md-2 col-md-offset-1"><span>F、</span><p>' + add7 + '</p></li><li class="col-md-2"><span>G、</span><p>' + add8 + '</p></li>');
                } else if ($(".itemAdd").length == 8) {
                    $(".zidingyi .question:last-child").find("ul li:last-child").after('<li class="col-md-2"><span>C、</span><p>' + add4 + '</p></li><li class="col-md-2"><span>D、</span><p>' + add5 + '</p></li><li class="col-md-2"><span>E、</span><p>' + add6 + '</p></li><li class="col-md-2 col-md-offset-1"><span>F、</span><p>' + add7 + '</p></li><li class="col-md-2"><span>G、</span><p>' + add8 + '</p></li><li class="col-md-2"><span>H、</span><p>' + add9 + '</p></li>');
                }
            }else{
                var add1 = $(".addQues .writeQues").val();
                var str = '<div class="question col-md-10 col-md-offset-1">' +
                    '<h3 class="quesTitle col-md-12">' +
                    '<div class="col-md-10" style="display:inline;">问题<span>' + (i + 1) + '</span>：<p class="questionTitle">' + add1 + '</p></div>' +
                    '<input type="hidden" value="' + questionType + '" name="questionTypesub"/>' +
                    '<button type="button" class="col-md-2 btn btn-block btn-danger btn-sm" style="width:70px;display:inline;">删除</button>' +
                    '</h3>' +
                    '<div class="form-group col-md-12">'+
                    '<textarea class="form-control" rows="5" placeholder="请写下您的意见和建议..." ></textarea>'+
                    '</div>'+
                    '</div>';
                $("#questionsub").append(str);
            }
            $(".addQues input").val("");//提交后清空

    });
    $("#sure").on("click",function(){
        //window.location.href=CONTEXTPATH+"/survey/surveyQueryOrSave?teamId="+teamId+"&title="+title;
        window.location.reload();
    })
    $("#btns").click(function(){
        var question=[];
        var questionType=$("[name=questionType]").val()?$("[name=questionType]").val():4;
        $(".questionTitle").each(function(){
            var list=[];
            if( $(this).parents("h3").parent("div").is(":visible")) {
                $(this).parents("h3").siblings("ul").find("li").each(function () {
                    list.push($(this).find("p").text());
                });
                question.push({
                    surveyId: id,
                    title: $.trim($(this).text()),
                    questionType: $(this).parent().siblings("input[name=questionTypesub]").val(),
                    list: list
                })
            }
        })
        $.ajax({
            type:"post",
            url:CONTEXTPATH+"/survey/saveQuestionAndOpiton",
            data:JSON.stringify(question),
            contentType:'application/json;charset=utf-8',
            dataType:"json",
            success:function(data){
                if(data.code==0){
                    $("#dialogModal2").find(".modal-body>p").html("满意度调研问卷创建成功");
                    blockUIOpenShare("dialogModal2");

                }else{
                    $("#dialogModal2").find(".modal-body>p").html("满意度调研问卷创建失败，请稍后再试。");
                    blockUIOpenShare("dialogModal2");
                }

            }
        });
    })
});
function questions(){


    $.ajax({
        type:"post",
        url:CONTEXTPATH+"/survey/saveQuestionAndOpiton",
        data:JSON.stringify(question),
        contentType:'application/json;charset=utf-8',
        dataType:"json",
        success:function(){}
    });
}