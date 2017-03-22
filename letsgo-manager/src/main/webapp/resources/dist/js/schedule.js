var dataobj1={};
var interval=$("input[name=interval]:checked").val();
var leaderStatus=$("input[name=leaderStatus]:checked").val();
var sex=$.trim($("input[name=sex]:checked").val());
var dayId=$("#form1").find("[name=id]").val();
var _leaderType=1;
var removelibNum=0;
var daybool=[];
var mySwiper ;
var phonesrc,tId;
var cache = {};
var cacheHotel = {};
var cacheRegion={};
var destinationBtnArr=[];
var areaCode="";
var countryCodeMember=$("#areaCodeMember").val()||"+86";
var countryCodeLeader=$("#areaCode").val()||"+86";
$(document).ready(function(){
   /* $("#myNav").affix({
        offset: {
            top:125
        }
    });*/
    //http://localhost:8080/travel/region/getRegion?alias=%E5%8C%97%E4%BA%AC
    $("#areaCode").on("change",function(){
        countryCodeLeader=$("#areaCode").val();
        phonesrc=countryCodeLeader+phonesrc;
        $('#form3').data('bootstrapValidator').validateField("phone");
    })
    $.ajax({
        type: "GET",
        url: CONTEXTPATH+"/resources/dist/js/country.json",
        success: function (data) {
            var data=data.RECORDS;
            for(var i=0;i<data.length;i++) {
                areaCode += '<option value="'+data[i].countryCode+'">' + data[i].countryCode + '</option>';
            }
            $("#areaCode").empty().append(areaCode);
            $("#areaCodeMember").empty().append(areaCode);
            $("#areaCodeMemberLay").empty().append(areaCode);
            countryCodeLeader=$("#areaCode").val();
            countryCodeMember=$("#areaCodeMember").val();
        },
        error:function(){

        }
    });
    //iCheck for checkbox and radio inputs
    $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
        checkboxClass: 'icheckbox_minimal-blue',
        radioClass: 'iradio_minimal-blue'
    });
    $(".box").on("ifChanged",'.proptCheck input',function(event){
        if($(this).is(":checked")){
            var ind=$(this).parents("label").index();
            $("#form3").find(".box").eq(ind).show();
        }else{
            var ind=$(this).parents("label").index();
            $("#form3").find(".box").eq(ind).hide();
        }
    })
   $(".proptCheck").find("input").each(function(){
        if($(this).is(":checked")){
            var ind=$(this).parents("label").index();
            $("#form3").find(".box").eq(ind).show();
        }else{
            var ind=$(this).parents("label").index();
            $("#form3").find(".box").eq(ind).hide();
        }
    });


    $(".form-group").find("input[name*=hotel]").each(function(){
        autosearchHotel($(this));
    })
    $(".form-group").find("input[id^=destination]").each(function(){
       autosearchRegion($(this));
    })
    //autocomplete插件
    //缓存

    $(".form-group").find("input[id*=scenicList]").each(function(){

        autosearchSpot($(this))
    })
    $(".form-group").find("input[name*=flight]").each(function(){

        autosearchAir($(this))
    })

    mySwiper = new Swiper('.swiper-container', {
        // Optional parameters
        setWrapperSize:true,
        slidesPerView:5,
        spaceBetween: 0,
        grabCursor:true,
        roundLengths:true,
        scrollbar:'.swiper-scrollbar',
        scrollbarHide : false,
        scrollbarDraggable : true,
        scrollbarSnapOnRelease : true

    })

    /*$("#edittab").on("click",">.box",function(event){
        event.stopPropagation();
        var ind=$(this).index("#edittab>.box");
        var startDate=$("#startDate").val();
        var endDate=$("#endDate").val();
        var _v=false;
        //行程开始日期和结束日期的天数差
        var diffnum= dateDiff(startDate,endDate);
        var mlimit;
        var that=$(this);
        if(startDate&&endDate&&$("[name=jourId]").val()){

        }else{
            if($(this).attr("id")!="section-1"){
                $("#dialogModal").find(".modal-body>p").html("请先填写保存行程概述");
                blockUIOpenShare("dialogModal");
                return false;
            }

        }
        $("#editArea").find("form").each(function(){

            if(that.attr("id")!="section-1"&&$(this).find("[name=dayNum]").length>0){
                if(!$.trim($(this).find("[name=id]").val())){
                    var subInt=$(this).parent(".box").index();
                    if(subInt!=ind){
                        mlimit=$(this).attr("id").split("form2")[1];
                        _v=true;
                        return false;
                    }else if(subInt==ind){
                        return false;
                    }else if(subInt>ind){
                        return false;
                    }
                }
            }
        })
        if(mlimit&&_v){
            $("#dialogModal").find(".modal-body>p").html("请先保存D"+mlimit);
            blockUIOpenShare("dialogModal");
            return false;
        }
        $(this).find(".overdivlay").css({"backgroundColor":"rgba(0,0,0,0.1)","color":"#00c0ef"}).end().siblings().find(".overdivlay").css({"backgroundColor":"rgba(0,0,0,0.5)",color:"#fff"});
        window.scrollTo(0,100);
        $("#editArea>div.box").eq(ind).show().siblings(".box").hide();
        $('#schedulePhotosUrl1').diyUpload({
            url: CONTEXTPATH + '/cloudFile/uploadCloudPhotos',
            success: function (data) {
                var data = data;//$(data._raw).find("item").text();
                var len=[];
                $("#photoLibrary1").find(":hidden").each(function () {
                    if (!$.trim($(this).val())) {
                        $(this).val(data);
                        len.push(data)
                        return false;
                    }else{
                        len.push($(this).val());
                    }
                })
                if(len.length>=3){
                    $('#schedulePhotosUrl1').hide();
                }
            },
            buttonText: '<button type="button" class="btn btn-primary pull-right btn-block btn-sm">上传行程图片</button>',
            error: function (err) {
                console.info(err);
            },
            fileNumLimit: 10
        });
        $("#editArea>div.box").eq(ind).find('.webuploader-container').find("label").parent().css({width:   $("#editArea>div.box").eq(ind).find('.webuploader-container').find("button").outerWidth(),height:  $("#editArea>div.box").eq(ind).find('.webuploader-container').find("button").outerHeight()})

    });*/
   $(".sui-steps").on("click",".wrap>.finished,.wrap>.current",function(event){
        event.stopPropagation();
        var ind=$(this).parent(".wrap").index();
        //var startDate=$("#startDate").val();
        //var endDate=$("#endDate").val();
        //var _v=false;
        ////行程开始日期和结束日期的天数差
        //var diffnum= dateDiff(startDate,endDate);
        //var mlimit;
        //var that=$(this);
        //if(startDate&&endDate&&$("[name=jourId]").val()){
        //
        //}else{
        //    if($(this).attr("id")!="section-1"){
        //        $("#dialogModal").find(".modal-body>p").html("请先填写保存行程概述");
        //        blockUIOpenShare("dialogModal");
        //        return false;
        //    }
        //
        //}
        //$("#editArea").find("form").each(function(){
        //
        //    if(that.attr("id")!="section-1"&&$(this).find("[name=dayNum]").length>0){
        //        if(!$.trim($(this).find("[name=id]").val())){
        //            var subInt=$(this).parent(".box").index();
        //            if(subInt!=ind){
        //                mlimit=$(this).attr("id").split("form2")[1];
        //                _v=true;
        //                return false;
        //            }else if(subInt==ind){
        //                return false;
        //            }else if(subInt>ind){
        //                return false;
        //            }
        //        }
        //    }
        //})
        //if(mlimit&&_v){
        //    $("#dialogModal").find(".modal-body>p").html("请先保存D"+mlimit);
        //    blockUIOpenShare("dialogModal");
        //    return false;
        //}
       // $(this).find(".overdivlay").css({"backgroundColor":"rgba(0,0,0,0.1)","color":"#00c0ef"}).end().siblings().find(".overdivlay").css({"backgroundColor":"rgba(0,0,0,0.5)",color:"#fff"});
        //window.scrollTo(0,100);
        $("#editArea>div.box").eq(ind).show().siblings(".box").hide();
        mySwiper.slideTo(ind, 1000, true);

        $("#editArea>div.box").eq(ind).find('.webuploader-container').find("label").parent().css({width:   $("#editArea>div.box").eq(ind).find('.webuploader-container').find("button").outerWidth(),height:  $("#editArea>div.box").eq(ind).find('.webuploader-container').find("button").outerHeight()})
    });
    photoD1();

    //显示当前正在填写
    $(".sui-steps").find(".wrap>div").each(function(){
        if($(this).hasClass("current")){
            var _ind=$(this).parent().index();
            $("#editArea>div.box").eq(_ind).show().siblings(".box").hide();
            mySwiper.slideTo(_ind, 1000, true);
        }
    })
    $("#disabledBtn").on("click",function(){
        $("#dialogModal2").find(".modal-body>p").html("行程已经创建成功");
        blockUIOpenShare("dialogModal2");
    })
    destinationBtnArr[1]=[];
    $("form").each(function(){
        if($(this).attr("id")) {
            if ($(this).attr("id").indexOf("form2") >= 0) {
                var _n = $(this).attr("id").split("form2")[1];
                destinationBtnArr[_n] = [];
                $(this).find("input[name^='countryId']").each(function () {
                    destinationBtnArr[_n].push(parseInt($(this).attr("name").split("countryId")[1]));

                })
            }
        }
    })
    /*增加目的地*/
    $("#destinationBtn").on("click",function(){
        var num=$(this).parents("form").find("input[id^='destination']").size();
        if(num>=4){
            $("#dialogModal").find(".modal-body>p").html("最多可以添加4个目的地");
            blockUIOpenShare("dialogModal");
            return;
        }
        var that=$(this);
        var addDes=function(obj,num){
            var html='<div class="form-group"><label for="destination'+(num+1)+'" class="control-label col-sm-3">目的地'+(num+1)+'：</label> <div class="col-sm-6"><input type="text" placeholder="目的地" class="form-control" autocomplete="off" name="destination'+(num+1)+'" id="destination'+(num+1)+'"><input type="hidden"  name="destinationId'+(num+1)+'" > <input type="hidden" name="country'+(num+1)+'"><input type="hidden" name="countryId'+(num+1)+'"><p class="help-block small">为了便于我们给游客展示准确的天气信息，请录入标准的目的地城市名称</p> </div> <div class="col-sm-3"> <button class="btn btn-block btn-danger destinationremove" type="button">删除</button></div></div>';
            if(obj.parents("form").find('#destination'+num).length>0){
                obj.parents("form").find('#destination'+num).parents(".form-group").after(html);
            }else{
                obj.parents("form").find("[id^=destination]").eq(0).parents(".form-group").after(html);
            }
            autosearchRegion($("#destination"+(num+1)));
           if(obj.parents("form").data('bootstrapValidator').getFieldElements('countryId'+(num+1))){
                obj.parents("form").data('bootstrapValidator').addField('countryId'+(num+1));
            }
            obj.parents("form").data('bootstrapValidator').enableFieldValidators('countryId'+(num+1), true);
            obj.parents("form").data('bootstrapValidator').resetField('countryId'+(num+1));
           // obj.parents("form").data('bootstrapValidator').validateField('countryId'+(num+1));

            if(destinationBtnArr[1].indexOf(num+1)<=-1){
                destinationBtnArr[1].push((num+1));
            }
        }
        if(destinationBtnArr[1].length>=1) {
                if (num + 1 == 2) {
                    if (destinationBtnArr[1].indexOf(2) <= -1) {
                        addDes(that, 1);
                    } else {
                        if (destinationBtnArr[1].indexOf(3) <= -1) {
                            addDes(that, 2);
                        } else if (destinationBtnArr[1].indexOf(4) <= -1) {
                            addDes(that, 3);
                        }
                    }
                }else if (num + 1 == 3) {
                    if (destinationBtnArr[1].indexOf(3) <= -1) {
                        addDes(that, 2);
                    } else {
                        if (destinationBtnArr[1].indexOf(2) <= -1) {
                            addDes(that, 1);
                        } else if (destinationBtnArr[1].indexOf(4) <= -1) {
                            addDes(that, 3);
                        }
                    }
                } else if (num + 1 == 4) {
                    if (destinationBtnArr[1].indexOf(4) <= -1) {
                        addDes(that, 3);
                    } else {
                        if (destinationBtnArr[1].indexOf(2) <= -1) {
                            addDes(that, 1);
                        } else if (destinationBtnArr[1].indexOf(3) <= -1) {
                            addDes(that, 2);
                        }
                    }
                }
        }else{
            addDes(that, 1);
        }
       // $(this).parents("form").data('bootstrapValidator').enableFieldValidators('country'+(num+1), true);
        //$(this).parents("form").data('bootstrapValidator').on('added.field.fv', function(e, data) {
        //})
        //$(this).parents("form").bootstrapValidator('enableFieldValidators', 'country'+(num+1), true)
    });
    $("#flightAdd").on("click",function(){
        var num=$(this).parents("form").find("input[id^='flight']").size();
        if(num>=2){
            $("#dialogModal").find(".modal-body>p").html("最多可以添加2个航班");
            blockUIOpenShare("dialogModal");
            return;
        }
        var html='<div class="form-group"><label class="control-label col-md-3" for="flight'+(num+1)+'">航班信息'+(num+1)+'：</label><div class="col-md-6"><input type="text" placeholder="航班" id="flight'+(num+1)+'" autocomplete="off" name="flight'+(num+1)+'" class="form-control"></div><div class="col-sm-3"> <button class="btn btn-block btn-danger flightremove" type="button">删除</button></div></div>';

        $(this).parents("form").find('#flight'+num).parents(".form-group").after(html);
        autosearchAir($("#flight"+(num+1)))
    })
    $("#scenicListBtn").on("click",function(){
        var num=$(this).parents("form").find("input[name^='scenicList']").size();
        //var m=parseInt($(this).attr("id").split("scenicListBtn")[1])-1;
        $(this).parents("form").find("input[id^='scenicList']").each(function(){
            num=parseInt($(this).attr("id").split("scenicList")[1]);
        })
        var that=$(this);
        var addScenic=function(obj,num){
            var html='<div class="form-group">\
            <label class="control-label col-md-3" for="scenicList'+(num+1)+'">景点'+(num+1)+'：</label>\
            <div class="col-md-6">\
            <input type="text" placeholder="景点" id="scenicList'+(num+1)+'"    name="scenicList['+(num)+'].name" autocomplete="off" class="form-control">\
            <input type="hidden"  id="spotId'+(num+1)+'" name="scenicList['+(num)+'].spotId">\
            </div>\
            <div class="col-sm-3">\
            <button class="btn btn-block btn-danger scenicListmove" type="button">删除</button>\
            </div>\
            </div>';
            obj.parents("form").find('#scenicList'+num).parents(".form-group").after(html);
            autosearchSpot($('#scenicList'+(num+1)));
            if(obj.parents("form").data('bootstrapValidator').getFieldElements("scenicList["+num+"].spotId")){
                //obj.parents("form").data('bootstrapValidator').addField("scenicList["+num+"].spotId");
                obj.parents("form").data('bootstrapValidator').addField("scenicList["+num+"].spotId",{
                    validators: {
                        scenicCheck: {
                            message: '请选择一个景点'
                        }
                    }
                });
            }
            obj.parents("form").data('bootstrapValidator').enableFieldValidators("scenicList["+num+"].spotId", true);
            obj.parents("form").data('bootstrapValidator').resetField("scenicList["+num+"].spotId");

            //if(destinationBtnArr[1].indexOf(num+1)<=-1){
            //    destinationBtnArr[1].push((num+1));
            //}
        }
        addScenic(that,num);
    })
    /*$("#flightAdd").on("click",function(){
     var num=$(this).parents("form").find("input[id^='flight']").size();
     if(num>=2){
     alert("最多可以添加2个航班")
     return;
     }
     var html='<div class="form-group"><label class="control-label col-md-4" for="flight'+(num+1)+'">航班信息'+(num+1)+'：</label><div class="col-md-5"><input type="text" placeholder="航班" id="flight'+(num+1)+'" name="flight'+(num+1)+'" class="form-control"></div></div>';

     $(this).parents("form").find('#flight'+num).parents(".form-group").after(html);
     })*/
    $(".box-body").on("click",".flightremove",function(event){
        event.preventDefault();
        event.stopPropagation();
        $(this).parents(".form-group:eq(0)").remove();
    })
    $(".box-body").on("click",".destinationremove",function(event){
        event.preventDefault();
        event.stopPropagation();
        var form=$(this).parents("form");
        var name=$(this).parents(".form-group:eq(0)").find("[name^=countryId]").attr("name");
        form.data('bootstrapValidator').enableFieldValidators(name, false);
        //form.data('bootstrapValidator').removeField(name);
        $(this).parents(".form-group:eq(0)").empty().remove();
        var r_ind=form.attr("id").split("form2")[1]
        destinationBtnArr[r_ind].removeByValue(name.split("countryId")[1]);
    });
    $(".box-body").on("click",".scenicListmove",function(event){
        event.preventDefault();
        event.stopPropagation();
        var form=$(this).parents("form");
        var name=$(this).parents(".form-group:eq(0)").find("[name$=spotId]").attr("name");
        form.data('bootstrapValidator').enableFieldValidators(name, false);
        $(this).parents(".form-group:eq(0)").remove();

    })
    $("body").on("click","input[name=hotelConfirm]",function(){

        if($(this).is(":checked")){
            if($(this).parent("label.control-label").siblings("div").find("input[type=text]").attr("name")=="hotel"){
                $(this).parents(".form-group:eq(0)").next(".form-group").find("input[type=text]").attr("disabled",true);
                $(this).parents(".form-group:eq(0)").next(".form-group").find("input[type=hidden]").attr("disabled",true);
                $(this).parent("label.control-label").siblings("div").find("input[type=checkbox]").removeAttr("disabled");
            }else{
                $(this).parents(".form-group:eq(0)").prev(".form-group").find("input[type=text]").attr("disabled",true);
                $(this).parents(".form-group:eq(0)").prev(".form-group").find("input[type=hidden]").attr("disabled",true);
                $(this).parents(".form-group:eq(0)").prev(".form-group").find("input[type=checkbox]").attr("disabled",true);
            }
            $(this).parent("label.control-label").siblings("div").find("input[type=hidden]").removeAttr("disabled");
            $(this).parent("label.control-label").siblings("div").find("input[type=text]").removeAttr("disabled");
        }
    })
    /*$(".removeSub").on("click",function(event){
        event.stopPropagation();
        event.preventDefault();
        var ind=$(this).parents(".box:eq(0)").attr("id").split("-")[1];
        removelibNum++;
        var removeHtml=$(this).parents(".box:eq(0)").html();
        var formHtml=$("#customsection-"+ind).clone();
        var tit=$("#customsection-"+ind).find("h4").text();
        $(this).parents(".box:eq(0)").hide();
        $("#customsection-"+ind).remove();
        //formHtml.appendTo($(".returnBus").find(".dropdown-menu"));
        var hnew=$('<li indexqreas="'+ind+'"><ul class="menu"><li><a href="javascript:;"><i class="fa fa-plus-circle"></i>'+tit+' </a></li></ul></li>');
        hnew.data("remove",formHtml);
        $(".returnBus").find(".dropdown-menu").find(".header").text(removelibNum+"个删除项目");
        $(".returnBus").find(".dropdown-menu").append(hnew);
    })*/
   /* $(".returnBus").find(".dropdown-menu").on("click",">li",function(event){
        event.stopPropagation();
        event.preventDefault();
        var ind=$(this).attr("indexqreas");
        removelibNum--;
        $("#section-"+ind).show();
        $(".returnBus").find(".dropdown-menu").find(".header").text(removelibNum+"个删除项目");
        $("#form3").prepend($(this).data("remove"));
        $(this).remove();
    });*/
    //name: $.trim($("input[name=leader_serch]").val())?$.trim($("input[name=leader_serch]").val()):"";
    $("#leaderSearchBtn").click(function(){
        dataobj1={
            name:$.trim($("input[name=leader_serch]").val()),
            leaderType:_leaderType==1?1:2
        }
        if(dataobj1.leaderType==1) {
            var startDateleader= $("#startDate").val();
            var endDateleader=$("#endDate").val();
            if(!startDateleader||!endDateleader){
                $("#dialogModal").find(".modal-body>p").html("请先填写行程开始和结束时间");
                blockUIOpenShare("dialogModal");
                return false;
            }
            dataobj1.startTime=startDateleader;
            dataobj1.endTime=endDateleader;
        }

        if($.trim($("input[name=leader_serch]").val())) {
            loadLeaderList();
        }else{
            alert("请输入姓名");
        }
    })

    $('body').on("click",".leaderadd",function(){
        var indLeader=$(this).parent().prev().children().attr("id").split("leaderSend")[1];
        blockUIOpenShare2("leaderAddcontent");
        var that=$(this);
        var html='<option value="1">领队</option><option value="3">领队兼导游</option>';
        var html2='<option value="2">导游</option><option value="3">领队兼导游</option>';
        $('#leaderAddForm').data("_index",indLeader);
        if(indLeader==1){
            _leaderType=1;
            $("[name='leaderType']").empty().append(html);
            $("#leaderAddcontent").find(".box-title:eq(0)").text("添加领队");
        }else{
            _leaderType=2;
            $("[name='leaderType']").empty().append(html2);
            $("#leaderAddcontent").find(".box-title:eq(0)").text("添加导游");
        }

    });
    $('body').on("click",".leaderSend",function(){
        var indLeader=$(this).attr("id").split("leaderSend")[1];
        blockUIOpenShare("leaderLay");
        var that=$(this);
        if(indLeader==1){
            _leaderType=1;
            $("#leaderLay").find(".box-title:eq(0)").text("指派领队");
        }else{
            _leaderType=2;
            $("#leaderLay").find(".box-title:eq(0)").text("指派导游");
        }

        dataobj1={
            leaderType:_leaderType==1?1:2,
        }
        if(dataobj1.leaderType==1) {
            var startDateleader = $("#startDate").val();
            var endDateleader = $("#endDate").val();
            if (!startDateleader || !endDateleader) {
                $("#dialogModal").find(".modal-body>p").html("请先填写行程开始和结束时间");
                blockUIOpenShare("dialogModal");
                return false;
            }
            dataobj1.startTime=startDateleader;
            dataobj1.endTime=endDateleader;
        }
        interval=$("input[name=interval]:checked").val();
        leaderStatus=$("input[name=leaderStatus]:checked").val();
        sex=$.trim($("input[name=sex]:checked").val());
        if(interval){
            dataobj1.interval=interval;
        }
        if(leaderStatus){
            dataobj1.leaderStatus=leaderStatus;
        }
        if(sex){
            dataobj1.sex=sex;
        }
        loadLeaderList();
        leaderSelect(indLeader);
    });
    $('#leaderAddForm').bootstrapValidator({
        message: '填写信息格式不正确',
        feedbackIcons: {
            valid: '',
            invalid: '',
            validating: ''
        },
        fields: {
            name: {
                message: '姓名格式不正确',
                validators: {
                    notEmpty: {
                        message: '姓名不能为空'
                    }
                }
            },
            sex: {
                validators: {
                    notEmpty: {
                        message: '请选择性别'
                    }
                }
            },
            leaderType: {
                validators: {
                    notEmpty: {
                        message: '请选择类型'
                    }
                }
            },
            leaderStatus: {
                validators: {
                    notEmpty: {
                        message: '请选择状态'
                    }
                }
            },
            leadTime :{
                validators: {
                    notEmpty: {
                        message: '请填写开始带团时间'
                    },
                    date: {
                        format: 'YYYY-MM-DD',
                        message: '日期格式不正确'
                    }
                }
            },
            phone: {
                validators: {
                    notEmpty: {
                        message: '手机号码不能为空'
                    },
                    threshold : 11 , //有11字符以上才发送ajax请求，（input中输入一个字符，插件会向服务器发送一次，设置限制，6字符以上才开始）
                    phoneleaderCheck:{
                        message: '已存在该手机号的领队或导游'
                    },
                    regexp: {
                        regexp:  /^[0-9]*$/,
                        message: '手机号码格式不正确'
                    }
                    //remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}
                    //    url: CONTEXTPATH + '/leader/leaderPhoneRepeatCheck',//验证地址
                    //    message: '已存在该手机号的领队或导游',//提示消息
                    //    delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                    //    type: 'GET'//请求方式
                    //    /**自定义提交数据，默认值提交当前input value
                    //     *  data: function(validator) {
                    //           return {
                    //               password: $('[name="passwordNameAttributeInYourForm"]').val(),
                    //               whatever: $('[name="whateverNameAttributeInYourForm"]').val()
                    //           };
                    //        }
                    //     */
                    //}
                    //regexp: {
                    //    regexp:  /^(\+86)?((1[3,5,8][0-9])|(14[5,7])|(17[0,1,6,7,8]))\d{8}$/,
                    //    message: '手机号码格式不正确'
                    //}
                }
            }
        },
        submitHandler: function (validator, form, submitButton) {

        }
    }).on('success.form.bv', function (e) {
        // Prevent form submission
        //e.preventDefault();
        var _form = $(e.target);
        _form.next(".overlays").show();
        _form.ajaxForm({
            success: function (data) {
                if(data.retCode==0){
                    alert("保存成功");
                    var phone=data.leader.phone;
                    var name=data.leader.name;
                    var sex=data.leader.sex;
                    var id=data.leader.id;
                    _form.bootstrapValidator('disableSubmitButtons', false);
                    addLeader(_form.data("_index"),name,sex,id,phone);
                    _form.resetForm();
                    _form.data('bootstrapValidator').resetForm();
                }else{
                    alert(data.retMsg);
                    _form.bootstrapValidator('disableSubmitButtons', false);
                }
                _form.next(".overlays").hide();
            },
            error: function(XmlHttpRequest, textStatus, errorThrown){
                alert("网络错误，请重试！");
                _form.next(".overlays").hide();
            }
        })
    });
    $('#leadTime').datetimepicker({
        language:  'zh-CN',
        format: 'yyyy-mm-dd',
        autoclose: true,
        endDate:new Date(),
        minView: "month"
    }).on('changeDate show', function(e) {
        // Revalidate the date when user change it
        $('#leaderAddForm').bootstrapValidator('revalidateField', 'leadTime');
    });
    $("#serchLeader").on("click",function(event){
        event.preventDefault();

        dataobj1={
            leaderType:_leaderType==1?1:2,
        }
        if(dataobj1.leaderType==1) {
            var startDateleader = $("#startDate").val();
            var endDateleader = $("#endDate").val();
            if (!startDateleader || !endDateleader) {
                $("#dialogModal").find(".modal-body>p").html("请先填写行程开始和结束时间");
                blockUIOpenShare("dialogModal");
                return false;
            }
            dataobj1.startTime = startDateleader;
            dataobj1.endTime = endDateleader;
        }
        interval=$("input[name=interval]:checked").val();
        leaderStatus=$("input[name=leaderStatus]:checked").val();
        sex=$.trim($("input[name=sex]:checked").val());
        if(interval){
            dataobj1.interval=interval;
        }
        if(leaderStatus){
            dataobj1.leaderStatus=leaderStatus;
        }
        if(sex){
            dataobj1.sex=sex;
        }
        loadLeaderList();
    });
    //清空

    $("#sercrLeaderset").on("click",function(event){
        $(this).parents("form").find("input").each(function(){
            $(this).iCheck('uncheck');

        });
    });
    loadLeaderList();

    /*指派领队end*/
    //iCheck for checkbox and radio inputs
    $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
        checkboxClass: 'icheckbox_minimal-blue',
        radioClass: 'iradio_minimal-blue'
    });

    /*保存行程概述*/
    $('#form1').bootstrapValidator({
        message: '填写信息格式不正确',
        feedbackIcons: {
            valid: '',
            invalid: '',
            validating: ''
        },
        fields: {
            teamNum: {
                message: '团号不能为空',
                validators: {
                    notEmpty: {//teamNumIsRepeat
                        message: '团号不能为空'
                    },
                    threshold :  2 , //有2字符以上才发送ajax请求，（input中输入一个字符，插件会向服务器发送一次，设置限制，6字符以上才开始）
                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}
                        url: CONTEXTPATH + '/schedule/teamNumIsRepeat',//验证地址
                        message: '该团号已被用过',//提示消息
                        delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                        type: 'GET',//请求方式
                        /**自定义提交数据，默认值提交当前input value
                         *
                         * @param validator
                         * @returns {{password: jQuery, whatever: jQuery}}
                         */
                        data: function(validator) {
                               return {
                                  // password: $('[name="passwordNameAttributeInYourForm"]').val(),
                                  // whatever: $('[name="whateverNameAttributeInYourForm"]').val()
                                   teamNum:  $.trim($("[name=teamNum]").val())
                               };
                        }

                    },
                }
            },
            name: {
                validators: {
                    notEmpty: {
                        message: '行程名称不能为空'
                    },
                    stringLength: {
                        min: 1,
                        max: 200,
                        message: '行程名称不能超过200个字符'
                    }
                }
            },
            startPlace: {
                validators: {
                    notEmpty: {
                        message: '出发地不能为空'
                    }
                }
            },
            endPlace: {
                validators: {
                    notEmpty: {
                        message: '目的地国家不能为空'
                    }
                }
            },
            startDate: {
                validators: {
                    notEmpty: {
                        message: '日期不能为空'
                    },
                    date: {
                        format: 'YYYY-MM-DD',
                        message: '日期格式不正确'
                    }
                }
            },
            endDate: {
                validators: {
                    notEmpty: {
                        message: '日期不能为空'
                    },
                    date: {
                        format: 'YYYY-MM-DD',
                        message: '日期格式不正确'
                    }
                }
            },
            collectionTime: {
                validators: {
                    date: {
                        format: 'YYYY-MM-DD HH:SS',
                        message: '日期格式不正确'
                    }
                }
            },
            leadName: {
                validators: {
                    notEmpty: {
                        message: '请选择领队'
                    }
                }
            }
        },
        submitHandler: function (validator, form, submitButton) {

        }
    }).on('success.form.bv', function (e) {
        // Prevent form submission
        //e.preventDefault();
        var _form = $(e.target);
        _form.next(".overlays").show();
        _form.ajaxForm({
            success: function (data) {
                if(data.retCode==0){
                    $("#dialogModal").find(".modal-body>p").html("保存成功");
                    blockUIOpenShare("dialogModal");
                    tId=data.scheduleEntity.teamId;
                    $("#memberUpdateLay").find("[name=tId]").val(tId);
                    $("#importMemberform").find("[name=tId]").val(tId);
                    $("#confirmForm").find("[name=tId]").val(tId);
                    $("#updateMemberform").find("[name=tId]").val(tId);
                    dayId=data.scheduleEntity.id;
                    $("[name=jourId]").each(function(){
                        $(this).val(data.scheduleEntity.id);
                    });
                    $("[name=scheduleId]").val(data.scheduleEntity.id);
                    $("[name=tId]").val(data.scheduleEntity.teamId);
                    _form.find("[name=id]").val(data.scheduleEntity.id);
                    _form.attr("action",CONTEXTPATH+"/schedule/scheduleUpdate");
                    _form.bootstrapValidator('disableSubmitButtons', false);
                    var startDate=$("#startDate").val();
                    var endDate=$("#endDate").val();
                    //行程开始日期和结束日期的天数差
                    var diffnum= parseInt(dateDiff(startDate,endDate))+1;
                    var newDate=startDate;
                    $("#scheduleDate").val(newDate).change();
                    $("#scheduleDate").siblings("p").text(newDate);
                    $("#section-21").find(".tit_time").text(newDate);
                   var msum= $("#editArea").find("form").find("[name=dayNum]").length;
                    if(types=="commonTemplate") {
                        if (diffnum == 2 && msum < diffnum) {
                            newDate = getNewDay(newDate, 1);
                            addDaynum(1, newDate);
                        } else if (msum < (diffnum)) {
                            if (msum > 1) {
                                newDate = getNewDay(newDate, (msum - 1));
                            }
                            for (var i = msum; i < diffnum; i++) {
                                newDate = getNewDay(newDate, 1);
                                addDaynum(i, newDate);
                            }
                        }
                    }else if(types=="updateTemplate"){
                        if (diffnum == 2 && msum < diffnum) {
                            newDate = getNewDay(newDate, 1);
                            addDaynum(1, newDate);
                        } else if (msum < (diffnum)) {
                            if (msum > 1) {
                                newDate = getNewDay(newDate, (msum - 1));
                            }
                            for (var i = msum; i < diffnum; i++) {
                                newDate = getNewDay(newDate, 1);
                                addDaynum(i, newDate);
                            }
                        }
                        var mnewDate=startDate;
                        for (var i = 1; i < diffnum; i++) {
                            mnewDate = getNewDay(mnewDate, 1);
                            $("#form2"+(i+1)).find("[name='scheduleDate']").val(mnewDate).change();
                            $("#form2"+(i+1)).find("[name='scheduleDate']").siblings("p").text(mnewDate);
                        }
                        //计算好开始日期和结束日期差后删掉多余的行程
                       var nowDiff = msum - diffnum;
                        if(nowDiff==0){

                        }else {
                            for (var d = msum; d > diffnum; d--) {
                                $("#form2" + d).parent(".box").remove();
                                $(".sui-steps").find(".wrap").eq(d).remove();
                            }
                            mySwiper.update();
                        }
                    }
                    $("#startDate").siblings("p").text(startDate).show();
                    $("#startDate").parent().parent().addClass("col-sm-2");
                    $("#endDate").siblings("p").text(endDate).show();
                    $("#startDate").hide();
                    $("#endDate").hide();
                    $("#startDate").siblings("div").hide();
                    $("#endDate").siblings("div").hide();
                    _form.parent(".box").hide();
                    _form.parent(".box").next().show();
                    $(".sui-steps").find(".wrap").eq(1).find("div").addClass("current").removeClass("todo");
                    $(".sui-steps").find(".wrap").eq(0).find("div").addClass("finished");
                    $(".sui-steps").find(".wrap").eq(0).find("label>.round").html('<i class="sui-icon icon-pc-right"></i>');
                    mySwiper.slideTo(1, 1000, true);
                    $("#editArea>div.box").eq(1).find('.webuploader-container').find("label").parent().css({width:   $("#editArea>div.box").eq(1).find('.webuploader-container').find("button").outerWidth(),height:  $("#editArea>div.box").eq(1).find('.webuploader-container').find("button").outerHeight()})

                }else{
                    $("#dialogModal").find(".modal-body>p").html(data.retMsg);
                    blockUIOpenShare("dialogModal");
                    _form.bootstrapValidator('disableSubmitButtons', false);
                }
                _form.next(".overlays").hide();
            },
            error: function(XmlHttpRequest, textStatus, errorThrown){
                $("#dialogModal").find(".modal-body>p").html("错误，请重试。");
                blockUIOpenShare("dialogModal");
                _form.next(".overlays").hide();
            }
        })
    });
    $("#form21").bootstrapValidator({
        excluded:[":disabled"],//':hidden', ':not(:visible)'//包含disabled的元素的校验
        message: '填写信息格式不正确',
        feedbackIcons:{
            valid: '',
            invalid: '',
            validating: ''
        },
        fields: {
            scheduleDate: {
                trigger:"change",
                message: '日期格式不正确',
                    validators: {

                        notEmpty: {
                            message: '日期不能为空'
                        },
                        date: {
                            format: 'YYYY-MM-DD',
                            message: '日期格式不正确'
                        }
                }
            },
            startPlace: {
                validators: {
                    notEmpty: {
                        message: '出发地不能为空'
                    }
                }
            },
            //destination1: {
            //    validators: {
            //        notEmpty: {
            //            message: '目的地不能为空'
            //        }
            //    }
            //},
            countryId1:{
                trigger:"change",
                validators: {
                    notEmpty: {
                        message: '请选择一个目的地'
                    }
                }
            },
            countryId2:{
                trigger:"change",
                validators: {
                    notEmpty: {
                        message: '请选择一个目的地'
                    }
                }
            },
            countryId3:{
                trigger:"change",
                validators: {
                    notEmpty: {
                        message: '请选择一个目的地'
                    }
                }
            },
            countryId4:{
                trigger:"change",
                validators: {
                    notEmpty: {
                        message: '请选择一个目的地'
                    }
                }
            },
            "scenicList[0].spotId":{
                trigger:"change",
                validators: {
                    scenicCheck: {
                        message: '请选择一个景点'
                    }
                }
            },
            "scenicList[1].spotId":{
                trigger:"change",
                validators: {
                    scenicCheck: {
                        message: '请选择一个景点'
                    }
                }
            },
            "scenicList[2].spotId":{
                trigger:"change",
                validators: {
                    scenicCheck: {
                        message: '请选择一个景点'
                    }
                }
            },
            "scenicList[3].spotId":{
                trigger:"change",
                validators: {
                    scenicCheck: {
                        message: '请选择一个景点'
                    }
                }
            },
            "scenicList[4].spotId":{
                trigger:"change",
                validators: {
                    scenicCheck: {
                        message: '请选择一个景点'
                    }
                }
            },
            "scenicList[5].spotId":{
                trigger:"change",
                validators: {
                    scenicCheck: {
                        message: '请选择一个景点'
                    }
                }
            },
            "scenicList[6].spotId":{
                trigger:"change",
                validators: {
                    scenicCheck: {
                        message: '请选择一个景点'
                    }
                }
            },
            "scenicList[7].spotId":{
                trigger:"change",
                validators: {
                    scenicCheck: {
                        message: '请选择一个景点'
                    }
                }
            },
            "scenicList[8].spotId":{
                trigger:"change",
                validators: {
                    scenicCheck: {
                        message: '请选择一个景点'
                    }
                }
            },
            "scenicList[9].spotId":{
                trigger:"change",
                validators: {
                    scenicCheck: {
                        message: '请选择一个景点'
                    }
                }
            },
            "scenicList[10].spotId":{
                trigger:"change",
                validators: {
                    scenicCheck: {
                        message: '请选择一个景点'
                    }
                }
            },
            "scenicList[11].spotId":{
                trigger:"change",
                validators: {
                    scenicCheck: {
                        message: '请选择一个景点'
                    }
                }
            },
            "scenicList[12].spotId":{
                trigger:"change",
                validators: {
                    scenicCheck: {
                        message: '请选择一个景点'
                    }
                }
            },
            desc: {
                validators: {
                    notEmpty: {
                        message: '行程概述不能为空'
                    }
                }
            },
            hotel:{
                validators: {
                   /* notEmpty: {
                        message: '酒店名称不能为空'
                    },*/
                    stringLength: {
                        min: 1,
                        max: 200,
                        message: '酒店名称长度不能超过200个字符'
                    }
                }
            },
            hotelInput:{
                validators: {
                    /*notEmpty: {
                        message: '酒店名称不能为空'
                    },*/
                    stringLength: {
                        min:1,
                        max:200,
                        message: '酒店名称长度不能超过200个字符'
                    }
                }
            },
            cateringInfo: {
                validators: {
                    notEmpty: {
                        message: '餐饮说明不能为空'
                    }
                }
            }
        },
        submitHandler: function (validator, form, submitButton) {
            var _form = form;

        }
    }).on('success.form.bv', function (e) {
        // Prevent form submission
        e.preventDefault();
    }).on('added.field.bv', function (e) {
        //validateField
        var _form = $(e.target)
        _form.data('bootstrapValidator').validateField();
    });
    // 保存提示信息
    $("#form3").bootstrapValidator({
        excluded:[":disabled"],//':hidden', ':not(:visible)'//包含disabled的元素的校验
        message: '填写信息格式不正确',
        feedbackIcons:{
            valid: '',
            invalid: '',
            validating: ''
        },
        fields: {
            newTitle:{
                validators: {
                    /* notEmpty: {
                     message: '酒店名称不能为空'
                     },*/
                    stringLength: {
                        min: 1,
                        max: 32,
                        message: '新增标题名称不能超过32个字符'
                    }
                }
            }
        }
    }).on('success.form.bv', function (e) {
        // Prevent form submission
        // e.preventDefault();
        var _form = $(e.target);
        _form.parent(".box-body").next(".overlays").show();

        _form.ajaxForm({
            beforeSubmit: function (formData, jqForm, options) {
                if (!_form.find("[name=id]").val() && _form.attr("action").indexOf("promptInfoUpdate") > 0) {
                    options.url = CONTEXTPATH + "/schedule/promptInfoSave";
                }
                if (_form.find("[name=id]").val() && _form.attr("action").indexOf("promptInfoSave") > 0) {
                    options.url = CONTEXTPATH + "/schedule/promptInfoUpdate";
                }
                if (!$.trim(_form.find("[name=scheduleId]").val())) {
                    $("#dialogModal").find(".modal-body>p").html("请先保存行程概述");
                    blockUIOpenShare("dialogModal");
                    _form.bootstrapValidator('disableSubmitButtons', false);
                    return false;
                }
            },
            success: function (data) {
                if (data.retCode == 0) {
                    $("#dialogModal").find(".modal-body>p").html("保存成功");
                    blockUIOpenShare("dialogModal");
                    _form.attr("action", CONTEXTPATH + "/schedule/promptInfoUpdate");
                    _form.find("[name=id]").val(data.promptInfoEntity.id);
                    _form.bootstrapValidator('disableSubmitButtons', false);
                    _form.parents(".box:eq(0)").hide();
                    _form.parents(".box:eq(0)").next().show();
                    var _index = _form.parents(".box:eq(0)").index();
                    $(".sui-steps").find(".wrap").eq(_index + 1).find("div").addClass("current").removeClass("todo");
                    $(".sui-steps").find(".wrap").eq(_index).find("div").addClass("finished");
                    $(".sui-steps").find(".wrap").eq(_index).find("label>.round").html('<i class="sui-icon icon-pc-right"></i>');
                    mySwiper.slideTo(_index+1, 200, true);
                    daoru();
                } else {
                    $("#dialogModal").find(".modal-body>p").html(data.retMsg);
                    blockUIOpenShare("dialogModal");
                    _form.bootstrapValidator('disableSubmitButtons', false);
                }
                _form.parent(".box-body").next(".overlays").hide();
            },
            error: function (XmlHttpRequest, textStatus, errorThrown) {
                _form.parent(".box-body").next(".overlays").hide();
                $("#dialogModal").find(".modal-body>p").html("错误，请重试。");
                blockUIOpenShare("dialogModal");
            }
        });
    });

    importMember();
    $('.memberImportExcel').click(function(){
        doUpload()
    });
    $('#birthday').datetimepicker({
        language:  'zh-CN',
        format: 'yyyy-mm-dd',
        autoclose: true,
        endDate:new Date(),
        minView: "month"
    }).on('changeDate show', function(e) {
        // Revalidate the date when user change it
        $('#importMemberform').bootstrapValidator('revalidateField', 'birthday');
    });
    $('#cardTime').datetimepicker({
        language:  'zh-CN',
        format: 'yyyy-mm-dd',
        autoclose: true,
        startDate:new Date(),
        minView: "month"
    }).on('changeDate show', function(e) {
        // Revalidate the date when user change it
        $('#importMemberform').bootstrapValidator('revalidateField', 'cardTime');
    });
    if($("#cardType").val()&&$("#cardType").val()!="无"){
        $("#importMemberform").find("[name=cardType]").val($("#cardType").val());
        $("#importMemberform").find("[name=cardNum]").parents(".form-group").show();
        $("#importMemberform").find("[name=cardTime]").parents(".form-group").show();
    }else{
        $("#importMemberform").find("[name=cardType]").val("");
        $("#importMemberform").find("[name=cardNum]").parents(".form-group").hide();
        $("#importMemberform").find("[name=cardTime]").parents(".form-group").hide();
    }
    $("#cardType").on("change",function(){
        if($("#cardType").val()&&$("#cardType").val()!="无"){
            $("#importMemberform").find("[name=cardType]").val($(this).val());
            $("#importMemberform").find("[name=cardNum]").parents(".form-group").show();
            $("#importMemberform").find("[name=cardTime]").parents(".form-group").show();
        }else{
            $("#importMemberform").find("[name=cardType]").val("");
            $("#importMemberform").find("[name=cardNum]").parents(".form-group").hide();
            $("#importMemberform").find("[name=cardTime]").parents(".form-group").hide();
        }
    })
    $('#birthdayupdate').datetimepicker({
        language:  'zh-CN',
        format: 'yyyy-mm-dd',
        autoclose: true,
        endDate:new Date(),
        minView: "month"
    }).on('changeDate show', function(e) {
        // Revalidate the date when user change it
        $('#updateMemberform').bootstrapValidator('revalidateField', 'birthday');
    });
    $('#cardTimeupdate').datetimepicker({
        language:  'zh-CN',
        format: 'yyyy-mm-dd',
        autoclose: true,
        startDate:new Date(),
        minView: "month"
    }).on('changeDate show', function(e) {
        // Revalidate the date when user change it
        $('#updateMemberform').bootstrapValidator('revalidateField', 'cardTime');
    });
    if($("#cardTypeupdate").val()&&$("#cardTypeupdate").val()!="无"){
        $("#updateMemberform").find("[name=cardType]").val($("#cardType").val());
        $("#updateMemberform").find("[name=cardNum]").parents(".form-group").show();
        $("#updateMemberform").find("[name=cardTime]").parents(".form-group").show();
    }else{
        $("#updateMemberform").find("[name=cardType]").val("");
        $("#updateMemberform").find("[name=cardNum]").parents(".form-group").hide();
        $("#updateMemberform").find("[name=cardTime]").parents(".form-group").hide();
    }
    $("#cardTypeupdate").on("change",function(){

        if($("#cardTypeupdate").val()&&$("#cardTypeupdate").val()!="无"){
            $("#updateMemberform").find("[name=cardType]").val($(this).val());
            $("#updateMemberform").find("[name=cardNum]").parents(".form-group").show();
            $("#updateMemberform").find("[name=cardTime]").parents(".form-group").show();
        }else{
            $("#updateMemberform").find("[name=cardType]").val("");
            $("#updateMemberform").find("[name=cardNum]").parents(".form-group").hide();
            $("#updateMemberform").find("[name=cardTime]").parents(".form-group").hide();
        }
    })
    $("#confirmForm").bootstrapValidator({


    }).on('success.form.bv', function (e) {
        // Prevent form submission
        //e.preventDefault();
        //console.log('Coucou');
        var _form = $(e.target);
        _form.parents(".box-body").next(".overlays").show();
        _form.ajaxForm({
            beforeSubmit: function (formData, jqForm, options) {

                _form.parents(".box-body").next(".overlays").show();
            },
            success: function (data) {
                if (data.retCode == 0) {
                    $("#dialogModal2").find(".modal-body>p").html("行程创建成功");
                    blockUIOpenShare("dialogModal2");
                } else {
                    $("#dialogModal").find(".modal-body>p").html(data.retMsg);
                    blockUIOpenShare("dialogModal");
                    _form.bootstrapValidator('disableSubmitButtons', false);
                }
                _form.parents(".box-body").next(".overlays").hide();
            },
            error: function (XmlHttpRequest, textStatus, errorThrown) {
                _form.parents(".box-body").next(".overlays").hide();
                $("#dialogModal").find(".modal-body>p").html("错误，请重试。");
                blockUIOpenShare("dialogModal");
            }
        });
    })
    $("#confirmScheduleBtn").on("click",function(event){
        event.preventDefault();
        if($("#memberImportExcel").is(":hidden")||$("#memberImportExcel").find("tbody").find("tr").length<=0){
            $("#dialogModal").find(".modal-body>p").html("请导入游客再点击确认");
            blockUIOpenShare("dialogModal");
            return;
        }
        $.ajax({
            type: "POST",
            url: CONTEXTPATH + "/schedule/confirmSchedule",
            data: {id: $("[name=id]").val()},
            dataType: "json",
            success: function (returndata) {
                if (returndata.retCode == 0) {
                    $("#dialogModal2").find(".modal-body>p").html("行程创建成功，请通知相应领队在领队端确认");
                    blockUIOpenShare("dialogModal2");
                }else{
                    $("#dialogModal").find(".modal-body>p").html("行程创建失败，请重试");
                    blockUIOpenShare("dialogModal2");
                }
            },
            error:function(){
                $("#dialogModal").find(".modal-body>p").html("行程创建失败，请重试");
                blockUIOpenShare("dialogModal2");
            }
        })
        var _index= $(this).parents(".box-info:eq(0)").index();
        $(".sui-steps").find(".wrap").eq(_index+1).find("div").addClass("current").removeClass("todo");
        $(".sui-steps").find(".wrap").eq(_index).find("div").addClass("finished");
        $(".sui-steps").find(".wrap").eq(_index).find("label>.round").html('<i class="sui-icon icon-pc-right"></i>');
    })
    $("#noneMember").on("click",function(event){
        event.preventDefault();
        $.ajax({
            type: "POST",
            url: CONTEXTPATH + "/schedule/confirmSchedule",
            data: {id: $("[name=id]").val()},
            dataType: "json",
            success: function (returndata) {
                if (returndata.retCode == 0) {
                    $("#dialogModal2").find(".modal-body>p").html("行程创建成功，请通知相应领队在领队端确认");
                    blockUIOpenShare("dialogModal2");
                }else{
                    $("#dialogModal").find(".modal-body>p").html("行程创建失败，请重试");
                    blockUIOpenShare("dialogModal2");
                }
            },
            error:function(){
                $("#dialogModal").find(".modal-body>p").html("行程创建失败，请重试");
                blockUIOpenShare("dialogModal2");
            }
        })
        var _index= $(this).parents(".box-info:eq(0)").index();
        $(".sui-steps").find(".wrap").eq(_index+1).find("div").addClass("current").removeClass("todo");
        $(".sui-steps").find(".wrap").eq(_index).find("div").addClass("finished");
        $(".sui-steps").find(".wrap").eq(_index).find("label>.round").html('<i class="sui-icon icon-pc-right"></i>');
    })
    $("#sure").on("click",function(){
        window.location.href=CONTEXTPATH+"/schedule/main";
        blockUIClose();
    });

    $("#submitJour").click(function(){
        var dayNumlimit=[];
        var startDate=$("#startDate").val();
        var endDate=$("#endDate").val();
        var _v=false;
        //行程开始日期和结束日期的天数差
        var diffnum= dateDiff(startDate,endDate);
        $("#editArea").find("form").each(function(){
            dayNumlimit.push[ $(this).find("[name=dayNum]"),$(this).find("[name=dayNum]").siblings("[name=id]").val()];
            if($(this).find("[name=dayNum]").val()&&!$(this).find("[name=dayNum]").siblings("[name=id]").val()){
                    _v=true;
                    return false;


            }

        });
        if(_v){
            $("#dialogModal").find(".modal-body>p").html("请您确认已经保存行程开始日期和结束日期之间对应天数的行程");
            blockUIOpenShare("dialogModal");
            return false;
        }
        if(startDate&&endDate&&dayNumlimit.length==parseInt(diffnum)+1){
            $("#dialogModal").find(".modal-body>p").html("请您确认已经保存行程开始日期和结束日期之间的行程");
            blockUIOpenShare("dialogModal");
            return false;
        }
        if($.trim($('[name=tId]').val())) {
            location.href = CONTEXTPATH + '/schedule/memberImportInit?tId=' + $('[name=tId]').val();
        }else{
            $("#dialogModal").find(".modal-body>p").html("请先录入行程");
            blockUIOpenShare("dialogModal");
        }
    });
    $("#viewSchedule").click(function(){
        if($.trim($('[name=tId]').val())&&$.trim($('[name=jourId]').val())) {
            window.open( CONTEXTPATH + '/schedule/schedulePreview?id=' + $('[name=jourId]').val());
        }else{
            $("#dialogModal").find(".modal-body>p").html("请先录入行程");
            blockUIOpenShare("dialogModal");
        }
    })
    $('#startDate').datetimepicker({
        language:  'zh-CN',
        format: 'yyyy-mm-dd',
        autoclose: true,
        startDate:new Date(),
        minView: "month"
    }).on('changeDate show', function(e) {
        // Revalidate the date when user change it
        $('#form1').bootstrapValidator('revalidateField', 'startDate');
        if($("#startDate").val()){
            $("#collectionTime").datetimepicker("setStartDate", $("#startDate").val());
            $("#endDate").datetimepicker("setStartDate", getNewDay($("#startDate").val(),1));
            $("#endDate").val(getNewDay($("#startDate").val(),1));
            $('#form1').bootstrapValidator('revalidateField', 'endDate');
        }

    });
    $('#endDate').datetimepicker({
        language:  'zh-CN',
        format: 'yyyy-mm-dd',
        autoclose: true,
        startDate:new Date(),
        minView: "month"
    }).on('changeDate show', function(e) {
        // Revalidate the date when user change it
        $('#form1').bootstrapValidator('revalidateField', 'endDate');
        $("#collectionTime").datetimepicker("setEndDate", $("#endDate").val());

    })

   /* $('#scheduleDate').datetimepicker({
        language:  'zh-CN',
        format: 'yyyy-mm-dd',
        autoclose: true,
        statrDate:new Date(),
        minView: "month"
    }).on('changeDate show', function(e) {
        // Revalidate the date when user change it
        $('#form21').bootstrapValidator('revalidateField', 'scheduleDate');
    }).on("changeDate", function (ev) {
        $("#scheduleDate").datetimepicker("setStartDate", $("#startDate").val());
    });*/
    $('#collectionTime').datetimepicker({
        language:  'zh-CN',
        format: 'yyyy-mm-dd hh:ii',
        autoclose: true,
        startDate:new Date(),
        // minView: "month"
    }).on('changeDate show', function(e) {
        // Revalidate the date when user change it
        $("#collectionTime").datetimepicker("setStartDate", $("#startDate").val());
        $("#collectionTime").datetimepicker("setEndDate", $("#endDate").val());
        $('#form1').bootstrapValidator('revalidateField', 'collectionTime');
    })
    /*酒店确认未确认*/
    $("body").on("click","[name=sameLevel]",function(){
        if($(this).is(":checked")){
            $(this).val(1);

        }else{
            $(this).val(0)
        }
    })
    $('#featurePhoto').diyUpload({
        url: CONTEXTPATH + '/cloudFile/uploadCloudPhotos',
        success: function (data) {
            var data = data;//$(data._raw).find("item").text();
            $("[name=featurePhoto]").val(data);
            $("#featurePhoto").hide();
        },
        buttonText: '<button type="button" class="btn btn-primary pull-right btn-block btn-sm">上传行程特色图片</button>',
        error: function (err) {
            //console.info(err);
        },
        fileNumLimit: 1
    });

})
Array.prototype.removeByValue = function(val) {
    for(var i=0; i<this.length; i++) {
        if(this[i] == val) {
            this.splice(i, 1);
            break;
        }
    }
}
function validateCallback(num,form, callback, confirmMsg){
    var _form = $(form);
    var data = _form.data('bootstrapValidator');
    if (data) {
        // 修复记忆的组件不验证
        data.validate();
        if (!data.isValid()) {
            return false;
        }
    }
    //$.ajax({
    //    type : form.method || 'POST',
    //    url : $form.attr("action"),
    //    data : $form.serializeArray(),
    //    dataType : "json",
    //    cache : false,
    //    success : callback,
    //    error : function(){
    //
    //    }
    //});
    //var _form = $(e.target);


    //if($.trim(_form.find("[name=jourId]").val())) {
    //
    //    _form.ajaxForm({
    //        beforeSubmit:function(formData, jqForm, options){
    //            if(!_form.find("[name=id]").val()&& _form.attr("action").indexOf("scheduleDetailUpdate")>0){
    //                options.url=CONTEXTPATH+"/schedule/scheduleDetailSave";
    //            }
    //            if(_form.find("[name=id]").val()&& _form.attr("action").indexOf("scheduleDetailSave")>0){
    //                options.url=CONTEXTPATH+"/schedule/scheduleDetailUpdate";
    //            }
    //            if(!$.trim(_form.find("[name=jourId]").val())){
    //                $("#dialogModal").find(".modal-body>p").html("请先保存行程概述");
    //                blockUIOpenShare("dialogModal");
    //                //_form.find(".scheduleDetailSave").removeAttr("disabled");
    //                _form.bootstrapValidator('disableSubmitButtons', false);
    //                return false;
    //            }
    //        },
    //        success: function (data) {
    //            if(data.retCode==0){
    //                $("#dialogModal").find(".modal-body>p").html("保存成功");
    //                blockUIOpenShare("dialogModal");
    //                _form.attr("boolable",true);
    //                _form.find("[name=id]").val(data.scheduleDetailEntity.id);
    //                _form.attr("action",CONTEXTPATH+"/schedule/scheduleDetailUpdate");
    //                _form.bootstrapValidator('disableSubmitButtons', false);
    //                _form.parent(".box").hide();
    //                _form.parent(".box").next().show();
    //                $(".sui-steps").find(".wrap").eq((ind+2)).find("div:eq(0)").addClass("current").removeClass("todo");
    //                $(".sui-steps").find(".wrap").eq((ind+1)).find("div").addClass("finished");
    //                $(".sui-steps").find(".wrap").eq((ind+1)).find("label>.round").html('<i class="sui-icon icon-pc-right"></i>');
    //                $("#editArea>div.box").each(function(i){
    //                    if(i>=(ind+1)){
    //                        $("#editArea>div.box").eq(i).find('.webuploader-container').find("label").parent().css({width:   $("#editArea>div.box").eq(i).find('.webuploader-container').find("button").outerWidth(),height:  $("#editArea>div.box").eq(i).find('.webuploader-container').find("button").outerHeight()})
    //                    }
    //                })
    //                mySwiper.slideTo((ind+2), 1000, true);
    //            }else{
    //                $("#dialogModal").find(".modal-body>p").html(data.retMsg);
    //                blockUIOpenShare("dialogModal");
    //                _form.bootstrapValidator('disableSubmitButtons', false);
    //            }
    //            _form.next(".overlays").hide();
    //        },
    //        error: function(XmlHttpRequest, textStatus, errorThrown){
    //            _form.next(".overlays").hide();
    //            $("#dialogModal").find(".modal-body>p").html("错误，请重试。");
    //            blockUIOpenShare("dialogModal");
    //        }
    //    })
    //}else{
    //    e.preventDefault();
    //    $("#dialogModal").find(".modal-body>p").html("必须先保存行程概述");
    //    blockUIOpenShare("dialogModal");
    //}

    _form.next(".overlays").show();
    if($.trim(_form.find("[name=jourId]").val())) {
        _form.ajaxSubmit({
            beforeSubmit: function (formData, jqForm, options) {
                if (!_form.find("[name=id]").val() && _form.attr("action").indexOf("scheduleDetailUpdate") > 0) {
                    options.url = CONTEXTPATH + "/schedule/scheduleDetailSave";
                }
                if (_form.find("[name=id]").val() && _form.attr("action").indexOf("scheduleDetailSave") > 0) {
                    options.url = CONTEXTPATH + "/schedule/scheduleDetailUpdate";
                }
                if (!$.trim(_form.find("[name=jourId]").val())) {
                    $("#dialogModal").find(".modal-body>p").html("请先保存行程概述");
                    blockUIOpenShare("dialogModal");
                    _form.bootstrapValidator('disableSubmitButtons', false);
                    return false;
                }
            },
            success: function (data) {
                if (data.retCode == 0) {
                    $("#dialogModal").find(".modal-body>p").html("保存成功");
                    blockUIOpenShare("dialogModal");
                    _form.attr("boolable", true);
                    _form.find("[name=id]").val(data.scheduleDetailEntity.id);
                    _form.attr("action", CONTEXTPATH + "/schedule/scheduleDetailUpdate");
                    _form.bootstrapValidator('disableSubmitButtons', false);
                    _form.parent(".box").hide();
                    _form.parent(".box").next().show();
                    if(num==1) {
                        $(".sui-steps").find(".wrap").eq(2).find("div").addClass("current").removeClass("todo");
                        $(".sui-steps").find(".wrap").eq(1).find("div").addClass("finished");
                        $(".sui-steps").find(".wrap").eq(1).find("label>.round").html('<i class="sui-icon icon-pc-right"></i>');
                        $("#editArea>div.box").each(function (i) {
                            if (i >= 2) {
                                $("#editArea>div.box").eq(i).find('.webuploader-container').find("label").parent().css({
                                    width: $("#editArea>div.box").eq(i).find('.webuploader-container').find("button").outerWidth(),
                                    height: $("#editArea>div.box").eq(i).find('.webuploader-container').find("button").outerHeight()
                                })
                            }
                        })
                        mySwiper.slideTo(2, 1000, true);
                    }else if(num>1) {
                        $(".sui-steps").find(".wrap").eq(num+1).find("div:eq(0)").addClass("current").removeClass("todo");
                        $(".sui-steps").find(".wrap").eq(num).find("div").addClass("finished");
                        $(".sui-steps").find(".wrap").eq(num).find("label>.round").html('<i class="sui-icon icon-pc-right"></i>');
                        $("#editArea>div.box").each(function (i) {
                            if (i >= num) {
                                $("#editArea>div.box").eq(i).find('.webuploader-container').find("label").parent().css({
                                    width: $("#editArea>div.box").eq(i).find('.webuploader-container').find("button").outerWidth(),
                                    height: $("#editArea>div.box").eq(i).find('.webuploader-container').find("button").outerHeight()
                                })
                            }
                        })
                        mySwiper.slideTo((num + 1), 1000, true);
                    }

                } else {
                    $("#dialogModal").find(".modal-body>p").html(data.retMsg);
                    blockUIOpenShare("dialogModal");
                    _form.bootstrapValidator('disableSubmitButtons', false);
                }
                _form.next(".overlays").hide();
            },
            error: function (XmlHttpRequest, textStatus, errorThrown) {
                _form.next(".overlays").hide();
                $("#dialogModal").find(".modal-body>p").html("错误，请重试。");
                blockUIOpenShare("dialogModal");
            }
        })
    }else{
        $("#dialogModal").find(".modal-body>p").html("必须先保存行程概述");
        blockUIOpenShare("dialogModal");
    }
    return false;
}
function importMemberCallback(form, callback, confirmMsg){
    var _form = $(form);
    var data = _form.data('bootstrapValidator');
    if (data) {
        // 修复记忆的组件不验证
        data.validate();
        if (!data.isValid()) {
            return false;
        }
    }
    //$.ajax({
    //    type : form.method || 'POST',
    //    url : $form.attr("action"),
    //    data : $form.serializeArray(),
    //    dataType : "json",
    //    cache : false,
    //    success : callback,
    //    error : function(){
    //
    //    }
    //});
    //var _form = $(e.target);

    _form.next(".overlays").show();
    _form.ajaxSubmit({
        //beforeSubmit:function(formData, jqForm, options){
        //
        //},
        success: function (data) {
            var html="";
            if(data.retCode==0){
                var dataObj=data.member;
                var realName=dataObj.realName?dataObj.realName:"";
                var sex=(dataObj.sex == "1") ? '男' : '女';
                var sexnum=dataObj.sex;
                var birthday=(dataObj.birthday?dataObj.birthday:"");
                var phone=(dataObj.phone?dataObj.phone:"");
                var cardType=(dataObj.cardType?dataObj.cardType:"");
                var cardNum=(dataObj.cardNum?dataObj.cardNum:"");
                var cardTime=dataObj.cardTime?dataObj.cardTime:"";
                var countryCode=dataObj.countryCode?dataObj.countryCode:"";
                if(dataObj.id){
                    html= '<tr>'+
                        '<td>'+ realName+'</td>'+
                        '<td>'+ sex+'</td>'+
                        '<td>'+ birthday+'</td>'+
                        '<td>'+ phone+'</td>'+
                        '<td>'+ cardType+'</td>'+
                        '<td>'+ cardNum+'</td>'+
                        '<td>'+ cardTime+'</td>'+
                        '<td><a href="javascript:;" onclick="memberUpdate(\''+dataObj.id+'\',\''+realName+'\',\''+sexnum+'\',\''+birthday+'\',\''+phone+'\',\''+cardType+'\',\''+cardNum+'\',\''+cardTime+'\',\''+countryCode+'\',this)" class="memberUpdate">修改</a>|<a href="javascript:;" onclick="memberDelete('+dataObj.id+',this);" class="memberDelete">删除</a></td>' +
                        '</tr>';
                    $("#memberImportExcel").find("tbody").append(html);
                    $("#memberImportExcel").parent().show();
                    $("#importMemberform").bootstrapValidator('disableSubmitButtons', false);
                    $("#dialogModal").find(".modal-body>p").html("添加游客成功");
                    blockUIOpenShare("dialogModal");
                    //清空
                    $("#importMemberform").resetForm();
                    $("#importMemberform").data('bootstrapValidator').resetForm().validateField("realName");
                    $("#importMemberform").find("[name=cardType]").val("");
                    $("#importMemberform").find("[name=cardNum]").parents(".form-group").hide();
                    $("#importMemberform").find("[name=cardTime]").parents(".form-group").hide();
                }
            }else{
                $("#dialogModal").find(".modal-body>p").html(data.retMsg);
                blockUIOpenShare("dialogModal");
                $("#importMemberform").bootstrapValidator('disableSubmitButtons', false);
            }
            _form.next(".overlays").hide();
        },
        error: function(XmlHttpRequest, textStatus, errorThrown){
            _form.next(".overlays").hide();
            $("#dialogModal").find(".modal-body>p").html("错误，请重试。");
            blockUIOpenShare("dialogModal");
            $("#importMemberform").bootstrapValidator('disableSubmitButtons', false);
        }
    })
    return false;
}
function importMember(){
    //$("#importMemberform").next(".overlays").show();
    tId=$('#importMemberform').find("[name=tId]").val();
    $('#importMemberform').bootstrapValidator({
        excluded:[":disabled",":not(:visible)"],//':hidden', ':not(:visible)'//包含disabled的元素的校验
        message: '填写信息格式不正确',
        feedbackIcons: {
            valid: '',
            invalid: '',
            validating: ''
        },
        fields: {
            realName: {
                message: '姓名格式不正确',
                validators: {
                    notEmpty: {
                        message: '姓名不能为空'
                    }
                }
            },
            birthday: {
                validators: {
                    date: {
                        format: 'YYYY-MM-DD',
                        message: '日期格式不正确'
                    }
                }
            },
            phone: {
                validators: {
                    /*notEmpty: {
                        message: '请填写手机号'
                    },*/
                    threshold :  11 , //有11字符以上才发送ajax请求，（input中输入一个字符，插件会向服务器发送一次，设置限制，6字符以上才开始）
                    phonememberCheck:{
                        message: '当前团队已存在该手机号，不可重复录入'
                    },
                    regexp: {
                        regexp:  /^[0-9]*$/,
                        message: '手机号码格式不正确'
                    }
                    //remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}
                    //    url: CONTEXTPATH + '/member/memberPhoneRepeatCheck',//验证地址
                    //    message: '当前团队已存在该手机号，不可重复录入',//提示消息
                    //    delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                    //    type: 'GET',//请求方式
                    //    /**自定义提交数据，默认值提交当前input value*/
                    //    data: function(validator) {
                    //        return {
                    //            phone: $('[name="phone"]').val(),
                    //            tId:$('[name="tId"]').val()
                    //        }
                    //    }
                    //}
                    //regexp: {
                    //    regexp:  /^(\+86)?((1[3,5,8][0-9])|(14[5,7])|(17[0,1,6,7,8]))\d{8}$/,
                    //    message: '手机号码格式不正确'
                    //}
                }
            },
            cardNum :{
                validators: {
                    notEmpty: {
                        message: '请填写证件号码'
                    }
                }
            },
            cardTime :{
                validators: {
                    notEmpty: {
                        message: '请填写证件有效期'
                    },
                    date: {
                        format: 'YYYY-MM-DD',
                        message: '日期格式不正确'
                    }
                }
            }
        },
        submitHandler: function (validator, form, submitButton) {

        }
    }).on('success.form.bv', function (e) {
        // Prevent form submission
        e.preventDefault();
        //console.log('Coucou');
        //var _form = $(e.target);
        //_form.next(".overlays").show();
        //var value_p = _form.find('[name=phone]').val();
        //var boolphone1=true
        //if(value_p.indexOf("+86")>=0){
        //    value_p= value_p.replace(/\+/g, "%2B");
        //}
        //$.ajax({
        //    type: "GET",
        //    url:  CONTEXTPATH + "/member/memberPhoneRepeatCheck?phone=" + value_p,//验证地址
        //    data: {tId:$("[name=tId]").val()},
        //    async:false,
        //    dataType: "json",
        //    success: function (data) {
        //        if (data.valid == false) {
        //            boolphone1= true;
        //        } else if (data.valid == true) {
        //            boolphone1= false;
        //        }
        //    }
        //});
        //if(!boolphone1){
        //    $("#dialogModal").find(".modal-body>p").html("当前团队已存在该手机号，不可重复录入");
        //    blockUIOpenShare("dialogModal");
        //    _form.next(".overlays").hide();
        //    return false;
        //}

    });
    countryCodeMember=$("#areaCodeMember").val()||"+86";
    if( $('#importMemberform').find("[name=phone]").val().indexOf("+")<0){
        phonesrc=countryCodeMember+ $('#importMemberform').find("[name=phone]").val();
    }else{
        phonesrc=$('#importMemberform').find("[name=phone]").val();
    }

    $("#areaCodeMember").off().on("change",function(){
        countryCodeMember=$("#areaCodeMember").val();
        phonesrc=countryCodeMember+phonesrc;
        $('#importMemberform').data('bootstrapValidator').validateField("phone");
    })
}
function daoru(){
    var dayNumlimit=[];
    var startDate=$("#startDate").val();
    var endDate=$("#endDate").val();
    var _v=false;
    //行程开始日期和结束日期的天数差
    var diffnum= dateDiff(startDate,endDate);
    $("#editArea").find("form").each(function(){
        dayNumlimit.push[ $(this).find("[name=dayNum]"),$(this).find("[name=dayNum]").siblings("[name=id]").val()];
        if($(this).find("[name=dayNum]").val()&&!$(this).find("[name=dayNum]").siblings("[name=id]").val()){
            _v=true;
            return false;


        }

    });
    if(_v){
        $("#dialogModal").find(".modal-body>p").html("请您确认已经保存行程开始日期和结束日期之间对应天数的行程");
        blockUIOpenShare("dialogModal");
        return false;
    }
    if(startDate&&endDate&&dayNumlimit.length==parseInt(diffnum)+1){
        $("#dialogModal").find(".modal-body>p").html("请您确认已经保存行程开始日期和结束日期之间的行程");
        blockUIOpenShare("dialogModal");
        return false;
    }
    if($.trim($('[name=tId]').val())) {
       // location.href = CONTEXTPATH + '/schedule/memberImportInit?tId=' + $('[name=tId]').val();
        //游客列表
        $.ajax({
            type: "GET",
            url: CONTEXTPATH+"/member/memberImportInit",
            data: {tId:$.trim($('[name=tId]').val())},
            dataType: "json",
            success: function (returndata) {
                var html="";
                if(returndata.retCode==0) {
                    var dataObj = returndata.memberList;
                    if (dataObj.length > 0) {
                        for (var i = 0; i < dataObj.length; i++) {
                            var realName=dataObj[i].realName?dataObj[i].realName:"";
                            var sex=(dataObj[i].sex == "1") ? '男' : '女';
                            var sexnum=dataObj[i].sex;
                            var birthday=(dataObj[i].birthday?dataObj[i].birthday:"");
                            var phone=(dataObj[i].phone?dataObj[i].phone:"");
                            var cardType=(dataObj[i].cardType?dataObj[i].cardType:"");
                            var cardNum=(dataObj[i].cardNum?dataObj[i].cardNum:"");
                            var cardTime=dataObj[i].cardTime?dataObj[i].cardTime:"";
                            var countryCode=dataObj[i].countryCode?dataObj[i].countryCode:"";
                            html += '<tr>' +
                                '<td>' + realName + '</td>' +
                                '<td>' + sex + '</td>' +
                                '<td>' + birthday + '</td>' +
                                '<td>' + phone + '</td>' +
                                '<td>' + cardType + '</td>' +
                                '<td>' + cardNum + '</td>' +
                                '<td>' + cardTime + '</td>' +
                                '<td><a href="javascript:;" onclick="memberUpdate(\''+dataObj[i].id+'\',\''+realName+'\',\''+sexnum+'\',\''+birthday+'\',\''+phone+'\',\''+cardType+'\',\''+cardNum+'\',\''+cardTime+'\',\''+countryCode+'\',this)" class="memberUpdate">修改</a>|<a href="javascript:;" onclick="memberDelete('+dataObj[i].id+',this);" class="memberDelete">删除</a></td>' +
                                '</tr>';

                        }
                        $("#memberImportExcel").find("tbody").empty().append(html);
                        $("#memberImportExcel").parent().show();
                    }
                }else{
                    $("#dialogModal").find(".modal-body>p").html(returndata.retMsg);
                    blockUIOpenShare("dialogModal");
                }
                $(".overlays").hide();
            },
            error: function (returndata) {
                $(".overlays").hide();
            }
        })
    }else{
        $("#dialogModal").find(".modal-body>p").html("请先录入行程");
        blockUIOpenShare("dialogModal");
    }
}
function doUpload() {
    $(".overlays").show();
    var formData = new FormData($( "#exampleInputFile" )[0]);
    $.ajax({
        url: CONTEXTPATH+'/member/memberImportExcel?tId='+$.trim($('[name=tId]').val()),
        type: 'POST',
        data: formData,
        async: true,
        cache: false,
        contentType: false,
        processData: false,
        success: function (returndata) {
            var html="";
            if(returndata.retCode==0) {
                var dataObj = returndata.memberList;
                if (dataObj.length > 0) {
                    for (var i = 0; i < dataObj.length; i++) {
                        var realName=dataObj[i].realName?dataObj[i].realName:"";
                        var sex=(dataObj[i].sex == "1") ? '男' : '女';
                        var sexnum=dataObj[i].sex;
                        var birthday=(dataObj[i].birthday?dataObj[i].birthday:"");
                        var phone=(dataObj[i].phone?dataObj[i].phone:"");
                        var cardType=(dataObj[i].cardType?dataObj[i].cardType:"");
                        var cardNum=(dataObj[i].cardNum?dataObj[i].cardNum:"");
                        var cardTime=dataObj[i].cardTime?dataObj[i].cardTime:"";
                        var countryCode=dataObj[i].countryCode?dataObj[i].countryCode:"";
                        html += '<tr>' +
                            '<td>' + realName + '</td>' +
                            '<td>' + sex + '</td>' +
                            '<td>' + birthday + '</td>' +
                            '<td>' + phone + '</td>' +
                            '<td>' + cardType + '</td>' +
                            '<td>' + cardNum + '</td>' +
                            '<td>' + cardTime + '</td>' +
                            '<td><a href="javascript:;" onclick="memberUpdate(\''+dataObj[i].id+'\',\''+realName+'\',\''+sexnum+'\',\''+birthday+'\',\''+phone+'\',\''+cardType+'\',\''+cardNum+'\',\''+cardTime+'\',\''+countryCode+'\',this)" class="memberUpdate">修改</a>|<a href="javascript:;" onclick="memberDelete('+dataObj[i].id+',this);" class="memberDelete">删除</a></td>' +
                            '</tr>';

                    }
                    $("#memberImportExcel").find("tbody").append(html);
                    $("#memberImportExcel").parent().show();
                }
            }else{
                $("#dialogModal").find(".modal-body>p").html(returndata.retMsg);
                blockUIOpenShare("dialogModal");
            }
            $(".overlays").hide();
        },
        error: function (returndata) {
            $(".overlays").hide();
        }
    });
}
function leaderSelect(i){
    $("#leader2").one("click","[name=leadersd]",function(){
        if($(this).is(":checked")) {
            if (i == 1){
                $("#form" + i).find("[name=leadName]").val($(this).attr("_name"));
                $("#form" + i).find("[name=leadName]").siblings("p").text($(this).attr("_name")).parent().show();
                $("#form"+i).bootstrapValidator('revalidateField', 'leadName');
            }else{
                $("#form" + i).find("[name=name]").val($(this).attr("_name"));
                $("#form" + i).find("[name=name]").siblings("p").text($(this).attr("_name")).parent().show();
                //$("#form"+i).bootstrapValidator('revalidateField', 'name');
            }
            $("#form"+i).find("[name=phone]").val($(this).attr("phone"));
            $("#form"+i).find("[name=phone]").siblings("p").text($(this).attr("phone")).parents(".form-group").show();
            $("#form"+i).find("[name=leaderId]").val($(this).attr("id"));
            $("#form"+i).find("[name=sex]").val($(this).attr("sex"));

            //$("#form"+i).bootstrapValidator('revalidateField', 'phone');
            blockUIClose();
        }
    })
}
function addLeader(i,name,sex,id,phone){
    if (i == 1){
        $("#form" + i).find("[name=leadName]").val(name);
        $("#form" + i).find("[name=leadName]").siblings("p").text(name).parent().show();
        $("#form"+i).bootstrapValidator('revalidateField', 'leadName');
    }else{
        $("#form" + i).find("[name=name]").val(name);
        $("#form" + i).find("[name=name]").siblings("p").text(name).parent().show();
        //$("#form"+i).bootstrapValidator('revalidateField', 'name');
    }
    $("#form"+i).find("[name=phone]").val(phone);
    $("#form"+i).find("[name=phone]").siblings("p").text(phone).parents(".form-group").show();
    $("#form"+i).find("[name=leaderId]").val(id);
    $("#form"+i).find("[name=sex]").val(sex);

    //$("#form"+i).bootstrapValidator('revalidateField', 'phone');
    blockUIClose();
}
function dotform(ind){

    /*form*/
    $("#form2"+(ind+1)).bootstrapValidator({
        excluded:[":disabled"],//':hidden', ':not(:visible)'//包含disabled的元素的校验
        message: '填写信息格式不正确',
        feedbackIcons:{
            valid: '',
            invalid: '',
            validating: ''
        },
        fields: {
            scheduleDate: {
                message: '日期格式不正确',
                validators: {
                    notEmpty: {
                        message: '日期不能为空'
                    },
                    date: {
                        format: 'YYYY-MM-DD',
                        message: '日期格式不正确'
                    }
                }
            },
            startPlace: {
                validators: {
                    notEmpty: {
                        message: '出发地不能为空'
                    }
                }
            },
            //destination1: {
            //    validators: {
            //        notEmpty: {
            //            message: '目的地不能为空'
            //        }
            //    }
            //},
            countryId1:{
                trigger:"change",
                validators: {
                    notEmpty: {
                        message: '请选择一个目的地'
                    }
                }
            },
            countryId2:{
                trigger:"change",
                validators: {
                    notEmpty: {
                        message: '请选择一个目的地'
                    }
                }
            },
            countryId3:{
                trigger:"change",
                validators: {
                    notEmpty: {
                        message: '请选择一个目的地'
                    }
                }
            },
            countryId4:{
                trigger:"change",
                validators: {
                    notEmpty: {
                        message: '请选择一个目的地'
                    }
                }
            },
            "scenicList[0].spotId":{
                trigger:"change",
                validators: {
                    scenicCheck: {
                        message: '请选择一个景点'
                    }
                }
            },
            "scenicList[1].spotId":{
                trigger:"change",
                validators: {
                    scenicCheck: {
                        message: '请选择一个景点'
                    }
                }
            },
            "scenicList[2].spotId":{
                trigger:"change",
                validators: {
                    scenicCheck: {
                        message: '请选择一个景点'
                    }
                }
            },
            "scenicList[3].spotId":{
                trigger:"change",
                validators: {
                    scenicCheck: {
                        message: '请选择一个景点'
                    }
                }
            },
            "scenicList[4].spotId":{
                trigger:"change",
                validators: {
                    scenicCheck: {
                        message: '请选择一个景点'
                    }
                }
            },
            "scenicList[5].spotId":{
                trigger:"change",
                validators: {
                    scenicCheck: {
                        message: '请选择一个景点'
                    }
                }
            },
            "scenicList[6].spotId":{
                trigger:"change",
                validators: {
                    scenicCheck: {
                        message: '请选择一个景点'
                    }
                }
            },
            "scenicList[7].spotId":{
                trigger:"change",
                validators: {
                    scenicCheck: {
                        message: '请选择一个景点'
                    }
                }
            },
            "scenicList[8].spotId":{
                trigger:"change",
                validators: {
                    scenicCheck: {
                        message: '请选择一个景点'
                    }
                }
            },
            "scenicList[9].spotId":{
                trigger:"change",
                validators: {
                    scenicCheck: {
                        message: '请选择一个景点'
                    }
                }
            },
            "scenicList[10].spotId":{
                trigger:"change",
                validators: {
                    scenicCheck: {
                        message: '请选择一个景点'
                    }
                }
            },
            "scenicList[11].spotId":{
                trigger:"change",
                validators: {
                    scenicCheck: {
                        message: '请选择一个景点'
                    }
                }
            },
            "scenicList[12].spotId":{
                trigger:"change",
                validators: {
                    scenicCheck: {
                        message: '请选择一个景点'
                    }
                }
            },
            desc: {
                validators: {
                    notEmpty: {
                        message: '行程概述不能为空'
                    }
                }
            },
            hotel:{
                validators: {
                    /*notEmpty: {
                        message: '酒店名称不能为空'
                    },*/
                    stringLength: {
                        min:1,
                        max:200,
                        message: '酒店名称长度不能超过200个字符'
                    }
                }
            },
            hotelInput:{
                validators: {
                    /*notEmpty: {
                        message: '酒店名称不能为空'
                    },*/
                    stringLength: {
                        min:1,
                        max:200,
                        message: '酒店名称长度不能超过200个字符'
                    }
                }
            },
            cateringInfo: {
                validators: {
                    notEmpty: {
                        message: '餐饮说明不能为空'
                    }
                }
            }
        },
        submitHandler: function (validator, form, submitButton) {

        }
    }).on('success.form.bv', function (e) {
        // Prevent form submission
        e.preventDefault();
        //var _form = $(e.target);
        //_form.next(".overlays").show();
        //if($.trim(_form.find("[name=jourId]").val())) {
        //
        //    _form.ajaxForm({
        //        beforeSubmit:function(formData, jqForm, options){
        //            if(!_form.find("[name=id]").val()&& _form.attr("action").indexOf("scheduleDetailUpdate")>0){
        //                options.url=CONTEXTPATH+"/schedule/scheduleDetailSave";
        //            }
        //            if(_form.find("[name=id]").val()&& _form.attr("action").indexOf("scheduleDetailSave")>0){
        //                options.url=CONTEXTPATH+"/schedule/scheduleDetailUpdate";
        //            }
        //            if(!$.trim(_form.find("[name=jourId]").val())){
        //                $("#dialogModal").find(".modal-body>p").html("请先保存行程概述");
        //                blockUIOpenShare("dialogModal");
        //                //_form.find(".scheduleDetailSave").removeAttr("disabled");
        //                _form.bootstrapValidator('disableSubmitButtons', false);
        //                return false;
        //            }
        //        },
        //        success: function (data) {
        //            if(data.retCode==0){
        //                $("#dialogModal").find(".modal-body>p").html("保存成功");
        //                blockUIOpenShare("dialogModal");
        //                _form.attr("boolable",true);
        //                _form.find("[name=id]").val(data.scheduleDetailEntity.id);
        //                _form.attr("action",CONTEXTPATH+"/schedule/scheduleDetailUpdate");
        //               // _form.find("[type=submit]").removeAttr("disabled");
        //                _form.bootstrapValidator('disableSubmitButtons', false);
        //                _form.parent(".box").hide();
        //                _form.parent(".box").next().show();
        //                $(".sui-steps").find(".wrap").eq((ind+2)).find("div:eq(0)").addClass("current").removeClass("todo");
        //                $(".sui-steps").find(".wrap").eq((ind+1)).find("div").addClass("finished");
        //                $(".sui-steps").find(".wrap").eq((ind+1)).find("label>.round").html('<i class="sui-icon icon-pc-right"></i>');
        //                $("#editArea>div.box").each(function(i){
        //                    if(i>=(ind+1)){
        //                        $("#editArea>div.box").eq(i).find('.webuploader-container').find("label").parent().css({width:   $("#editArea>div.box").eq(i).find('.webuploader-container').find("button").outerWidth(),height:  $("#editArea>div.box").eq(i).find('.webuploader-container').find("button").outerHeight()})
        //                    }
        //                })
        //                mySwiper.slideTo((ind+2), 1000, true);
        //            }else{
        //                $("#dialogModal").find(".modal-body>p").html(data.retMsg);
        //                blockUIOpenShare("dialogModal");
        //                //_form.find("[type=submit]").attr("disabled",false);
        //                _form.bootstrapValidator('disableSubmitButtons', false);
        //            }
        //            _form.next(".overlays").hide();
        //        },
        //        error: function(XmlHttpRequest, textStatus, errorThrown){
        //            _form.next(".overlays").hide();
        //            $("#dialogModal").find(".modal-body>p").html("错误，请重试。");
        //            blockUIOpenShare("dialogModal");
        //        }
        //    })
        //}else{
        //    e.preventDefault();
        //    $("#dialogModal").find(".modal-body>p").html("必须先保存行程概述");
        //    blockUIOpenShare("dialogModal");
        //}
    });
    $('#scheduleDate'+(ind+1)).datetimepicker({
        language:  'zh-CN',
        format: 'yyyy-mm-dd',
        autoclose: true,
        startDate:new Date(),
        minView: "month"
    }).on('changeDate show', function(e) {
        // Revalidate the date when user change it
        $('#form2'+(ind+1)).bootstrapValidator('revalidateField', 'scheduleDate');
    }).on("click", function (ev) {
        $('#scheduleDate'+(ind+1)).datetimepicker("setStartDate", $("#startDate").val());
    });
    if( !destinationBtnArr[(ind+1)]){
        destinationBtnArr[(ind+1)]=[];
    }
    $("#destinationBtn"+(ind+1)).off().on("click",function(){
        var num=$(this).parents("form").find("input[id^='destination']").size();
        var m=parseInt($(this).attr("id").split("destinationBtn")[1])-1;
        if(num>=4){
            $("#dialogModal").find(".modal-body>p").html("最多可以添加4个目的地");
            blockUIOpenShare("dialogModal");
            return;
        }

        var that=$(this);
        var addDes=function(obj,num){
            var html='<div class="form-group"><label for="destination'+(ind)+''+(num+1)+'" class="control-label col-sm-3">目的地'+(num+1)+'：</label> <div class="col-sm-6"><input type="text" placeholder="目的地"  class="form-control" autocomplete="off" id="destination'+(ind)+''+(num+1)+'" name="destination'+(num+1)+'"> <input type="hidden"  name="destinationId'+(num+1)+'" ><input type="hidden" name="country'+(num+1)+'"><input type="hidden" name="countryId'+(num+1)+'"><p class="help-block small">为了便于我们给游客展示准确的天气信息，请录入标准的目的地城市名称</p></div> <div class="col-sm-3"> <button class="btn btn-block btn-danger destinationremove" type="button">删除</button></div></div>';
            if( obj.parents("form").find('#destination' + m + '' + (parseInt(num))).length>0) {
                obj.parents("form").find('#destination' + m + '' + (parseInt(num))).parents(".form-group").after(html);
            }else{
                obj.parents("form").find("[id^=destination]").eq(0).parents(".form-group").after(html);
            }
            autosearchRegion($("#destination"+(ind)+(num+1)));

            if(obj.parents("form").data('bootstrapValidator').getFieldElements('countryId'+(num+1))){
                obj.parents("form").data('bootstrapValidator').addField('countryId'+(num+1));
            }
            obj.parents("form").data('bootstrapValidator').enableFieldValidators('countryId'+(num+1), true);

            //obj.parents("form").data('bootstrapValidator').addField('countryId'+(num+1));
           // obj.parents("form").data('bootstrapValidator').resetField('countryId'+(num+1));
            //obj.parents("form").data('bootstrapValidator').validateField('countryId'+(num+1));
            if(destinationBtnArr[(ind+1)].indexOf(num+1)<=-1){
                destinationBtnArr[(ind+1)].push((num+1));
            }
        }
        if(destinationBtnArr[(ind+1)].length>=1) {
            if (num + 1 == 2) {
                if (destinationBtnArr[(ind+1)].indexOf(2) <= -1) {
                    addDes(that, 1);
                } else {
                    if (destinationBtnArr[(ind+1)].indexOf(3) <= -1) {
                        addDes(that, 2);
                    } else if (destinationBtnArr[(ind+1)].indexOf(4) <= -1) {
                        addDes(that, 3);
                    }
                }
            }else if (num + 1 == 3) {
                if (destinationBtnArr[(ind+1)].indexOf(3) <= -1) {
                    addDes(that, 2);
                } else {
                    if (destinationBtnArr[(ind+1)].indexOf(2) <= -1) {
                        addDes(that, 1);
                    } else if (destinationBtnArr[(ind+1)].indexOf(4) <= -1) {
                        addDes(that, 3);
                    }
                }
            } else if (num + 1 == 4) {
                if (destinationBtnArr[(ind+1)].indexOf(4) <= -1) {
                    addDes(that, 3);
                } else {
                    if (destinationBtnArr[(ind+1)].indexOf(2) <= -1) {
                        addDes(that, 1);
                    } else if (destinationBtnArr[(ind+1)].indexOf(3) <= -1) {
                        addDes(that, 2);
                    }
                }
            }
        }else{
            addDes(that, 1);
        }
    });
    $("#form2"+(ind+1)).find(".form-group").find("input[name*=hotel]").each(function(){
        autosearchHotel($(this));
    })
    $("#flightAdd"+(ind+1)).on("click",function(){
        var num=$(this).parents("form").find("input[id^='flight']").size();
        var m=parseInt($(this).attr("id").split("flightAdd")[1])-1;
        if(num>=2){
            $("#dialogModal").find(".modal-body>p").html("最多可以添加2个航班");
            blockUIOpenShare("dialogModal");
            return;
        }
        var html='<div class="form-group"><label class="control-label col-md-3" for="flight'+(ind)+''+(num+1)+'">航班信息'+(num+1)+'：</label><div class="col-md-6"><input type="text" placeholder="航班" autocomplete="off" name="flight'+(num+1)+'" id="flight'+(ind)+''+(num+1)+'" class="form-control"></div><div class="col-sm-3"> <button class="btn btn-block btn-danger flightremove" type="button">删除</button></div></div>';

        $(this).parents("form").find('#flight'+m+''+(parseInt(num))).parents(".form-group").after(html);
        autosearchAir($("#flight"+(ind)+(num+1)));

    })
    autosearchRegion($("#destination"+(ind)+(1)));
    autosearchAir($("#flight"+(ind)+(1)))
    $("#scenicListBtn"+(ind+1)).on("click",function(){
        var num=$(this).parents("form").find("input[id^='scenicList']").size();
        var m=parseInt($(this).attr("id").split("scenicListBtn")[1])-1;
        $(this).parents("form").find("input[id^='scenicList']").each(function(){
            num=parseInt($(this).attr("id").split("scenicList"+ind)[1]);
        })
        var that=$(this);
        var addScenic=function(obj,num){
            var html='<div class="form-group">\
            <label class="control-label col-md-3" for="scenicList'+(ind)+''+(num+1)+'">景点'+(num+1)+'：</label>\
            <div class="col-md-6">\
            <input type="text" placeholder="景点"  name="scenicList['+(num)+'].name" id="scenicList'+(ind)+''+(num+1)+'" autocomplete="off" class="form-control">\
            <input type="hidden"    id="spotId'+(ind)+''+(num+1)+'" name="scenicList['+(num)+'].spotId">\
            </div>\
            <div class="col-sm-3">\
            <button class="btn btn-block btn-danger scenicListmove" type="button">删除</button>\
            </div>\
            </div>';
            obj.parents("form").find('#scenicList'+m+''+(parseInt(num))).parents(".form-group").after(html);
            autosearchSpot($('#scenicList'+(ind)+(num+1)));
            if(obj.parents("form").data('bootstrapValidator').getFieldElements("scenicList["+num+"].spotId")){
                //obj.parents("form").data('bootstrapValidator').addField("scenicList["+num+"].spotId");
                obj.parents("form").data('bootstrapValidator').addField("scenicList["+num+"].spotId",{
                    validators: {
                        scenicCheck: {
                            message: '请选择一个景点'
                        }
                    }
                });
            }
            obj.parents("form").data('bootstrapValidator').enableFieldValidators("scenicList["+num+"].spotId", true);
            obj.parents("form").data('bootstrapValidator').resetField("scenicList["+num+"].spotId");

            //if(destinationBtnArr[1].indexOf(num+1)<=-1){
            //    destinationBtnArr[1].push((num+1));
            //}
        }
        addScenic(that,num);

    })
    autosearchSpot($('#scenicList'+(ind)+1));
    $('#schedulePhotosUrl'+(ind+1)).diyUpload({
        url: CONTEXTPATH + '/cloudFile/uploadCloudPhotos',
        success: function (data) {

            var data = data;//$(data._raw).find("item").text();
            var len=[];
            $("#photoLibrary"+(ind+1)).find(":hidden").each(function () {
                if (!$.trim($(this).val())) {
                    $(this).val(data);
                    len.push(data)
                    return false;
                }else{
                    len.push($(this).val());
                }
            })
            if(len.length>=3){
                $('#schedulePhotosUrl'+(ind+1)).hide();
            }

        },
        buttonText: '<button type="button" class="btn btn-primary pull-right btn-block btn-sm">上传行程图片</button>',
        error: function (err) {
            //console.info(err);
        },
        fileNumLimit: 100
    });
}
function loadLeaderList() {
    $("#leader2").find("tbody").empty().html('<td  colspan="9">  <div class="overlays"> <i class="fa fa-refresh fa-spin"></i> </div></td>').end().parents(".box:eq(0)").show();
    $("#pagination").hide();
    var html = "";
    $.ajax({
        type: "GET",
        url: CONTEXTPATH+"/leader/searchAjax",
        data: dataobj1,
        dataType: "json",
        success: function (data) {
            if(data.totals&&data.totals!=0) {
                var lenpages=data.totals;
                $('#pagination').pagination({
                    dataSource: CONTEXTPATH + '/leader/searchAjax',
                    locator: function () {
                        // find data and return
                        return "data.list"
                    },
                    totalNumber: lenpages,
                    pageSize: 3,
                    pageRange:1,
                    alias: {
                        pageNumber: 'pageNum',
                        pageSize: 'pageSize'
                    },
                    ajax: {
                        data: dataobj1,
                        beforeSend: function () {
                           // $("#leaderTable").find("tbody").empty().html("<td  colspan='9'>Loading ...</td>").end().parents(".box:eq(0)").show();
                        }
                    },
                    callback: function (data, pagination) {
                        $("#leader2").find(".box-title").text("找到" + lenpages + "个结果");
                        html = "";
                        if (data.length > 0) {
                            for (var i = 0; i < data.length; i++) {
                                html += '<tr>' +
                                    '<td><input type="radio" name="leadersd" id="' + data[i].id + '" sex="' + data[i].sex + '" phone="' + data[i].phone + '" _name="' + data[i].name + '"/></td><td>' + data[i].name + '</td>' +

                                    '<td>' + ((data[i].sex == "1") ? '男' : '女') + '</td>' +
                                    '<td>' + ((data[i].leaderStatus == 1) ? "专职" : data[i].leaderStatus == 2 ? "兼职" : "离职") + '</td>' +
                                    '<td>' + (data[i].leadYears&&data[i].leadYears!=0 ? data[i].leadYears + "年" : "1年以内") + '</td>' +
                                    //'<td>' + (data[i].destinationGroup1 ? data[i].destinationGroup1 : "") + (data[i].destinationGroup2 ? data[i].destinationGroup2 : "") + (data[i].destinationGroup3 ? data[i].destinationGroup3 : "") + (data[i].destinationGroup4 ? data[i].destinationGroup4 : "") + (data[i].destinationGroup5 ? data[i].destinationGroup5 : "") + (data[i].destinationGroup6 ? data[i].destinationGroup6 : "") + (data[i].destinationGroup7 ? data[i].destinationGroup7 : "") + '</td>' +
                                    '<td><a href="' + CONTEXTPATH + '/leader/leaderPreview?id=' + data[i].id + '" target="_blank">查看</a></td>' +
                                    '</tr>';

                            }
                            $("#pagination").show();
                            $("#leader2").find("tbody").empty().append(html).end().show();
                        } else {
                            $("#pagination").hide();
                            $("#leader2").find(".box-title").text("找到0个结果")
                            $("#leader2").find("tbody").empty().html("<td  colspan='9'>没有结果</td>").end().parents(".box:eq(0)").show();
                        }
                    }
                })
            }else{
                $("#pagination").hide();
                $("#leader2").find(".box-title").text("找到0个结果")
                $("#leader2").find("tbody").empty().html("<td  colspan='9'>没有结果</td>").end().parents(".box:eq(0)").show();
            }
        }
    })
}
function addDaynum(ind,_date){
   // $("#edittab").find("#sectionAdd").click(function(){
        //var ind=$(this).prev().index();

        /*var html=$("<div class='box box-info' id=''>"+$("#sectionAdd").prev().html()+"</div>").attr("id","section-2"+(ind+1));
        html.find(".box-title").text("D"+(ind+1));
        html.find(".tit_time").text(_date);
        html.find(".overdivlay>h3").html('<i class="fa fa-edit"></i>编辑第'+(ind+1)+'天行情');
        $("#sectionAdd").before(html);*/
var html='<div class="wrap swiper-slide">\
        <div class="todo">\
        <label><span class="round">'+(ind+2)+'</span><span>D'+(ind+1)+'</span></label><i class="triangle-right-bg"></i><i class="triangle-right"></i>\
        </div>\
        </div>';
        $(".sui-steps").find(".wrap").eq((ind)).after(html);
        for(var m=0;m< $(".sui-steps").find(".wrap").length;m++){
            if($(".sui-steps").find(".wrap").eq(m).find(".round>.sui-icon").length<=0) {
                $(".sui-steps").find(".wrap").eq(m).find(".round").text(m + 1);
            }
        }

        //$("#navsection3").before('<li><a href="#section-2'+(ind+1)+'">行程单：D'+(ind+1)+'</a></li>');
        var h='	<input type="hidden" name="jourId" value="'+dayId+'"/>\
            <input type="hidden" name="dayNum" value="'+(ind+1)+'"/>\
            <input type="hidden" autocomplete="off" name="id" value=""/>\
            <div class="box-body">\
            <div class="form-group">\
            <label class="control-label col-sm-3" for="startPlace'+(ind+1)+'"><strong>*</strong>日期：</label>\
        <div class="col-sm-6">\
         <p class="form-control-static">'+_date+'</p>\
            <input type="hidden" id="scheduleDate'+(ind+1)+'" name="scheduleDate" placeholder="yyyy-mm-dd" class="form-control" value="'+_date+'">\
        </div>\
        </div>\
        <div class="form-group">\
            <label class="control-label col-sm-3" for="startPlace'+(ind+1)+'"><strong>*</strong>出发地：</label>\
        <div class="col-sm-6">\
            <input type="text" id="startPlace'+(ind+1)+'" name="startPlace" class="form-control" autocomplete="off"  placeholder="出发地">\
            </div>\
            </div>\
            <div class="form-group">\
            <label class="control-label col-sm-3" for="destination'+ind+''+1+'"><strong>*</strong>目的地：</label>\
        <div class="col-sm-6">\
            <input type="text" id="destination'+ind+''+1+'" name="destination1" class="form-control" autocomplete="off"  placeholder="目的地">\
            <input type="hidden"  name="destinationId1" >\
            <input type="hidden" name="country1">\
            <input type="hidden" name="countryId1">\
            <p class="help-block small">为了便于我们给游客展示准确的天气信息，请录入标准的目的地城市名称</p>\
            </div>\
            <div class="col-sm-3">\
            <button class="btn btn-block btn-warning" id="destinationBtn'+(ind+1)+'" type="button">增加</button>\
            </div>\
            </div>\
            <div class="form-group">\
            <label class="control-label col-sm-3">行程图片：</label>\
            <div class="col-sm-9">\
            <div id="schedulePhotosUrl'+(ind+1)+'">\
            </div>\
            <p class="help-block small">每日行程可上传3张图片，上传图片格式为png,jpeg,jpg,gif格式，单个图片大小不超过500KB</p>\
            <div id="photoLibrary'+(ind+1)+'" style="display: none;">\
            <input type="hidden" autocomplete="off" name="schedulePhotosUrl1" value=""/>\
            <input type="hidden" autocomplete="off" name="schedulePhotosUrl2" value=""/>\
            <input type="hidden" autocomplete="off" name="schedulePhotosUrl3" value=""/>\
            </div>\
            </div>\
            </div>\
            <div class="form-group">\
            <label class="control-label col-md-3" for="desc"><strong>*</strong>行程概述：</label>\
        <div class="col-md-9">\
            <textarea placeholder="行程概述" rows="3" class="form-control" name="desc" id="desc'+(ind+1)+'"></textarea>\
            </div>\
            </div>\
            <div class="form-group">\
            <label class="control-label col-md-3" for="scenicList'+ind+''+1+'">景点：</label>\
        <div class="col-md-6">\
            <input type="text" placeholder="景点" id="scenicList'+ind+''+1+'" name="scenicList[0].name" autocomplete="off"  class="form-control">\
            <input type="hidden" id="spotId'+ind+''+1+'" name="scenicList[0].spotId">\
            </div>\
            <div class="col-sm-3">\
            <button class="btn btn-block btn-warning" id="scenicListBtn'+(ind+1)+'" type="button">增加</button>\
            </div>\
            </div>\
            <div class="form-group ">\
            <label class="control-label  col-md-3" for="hotel">\
            酒店已确认：\
        <input type="radio" name="hotelConfirm" value="1" checked />\
        </label>\
        <div class="col-md-6">\
            <input type="text" placeholder="酒店名称" id="hotel'+(ind+1)+'" name="hotel" autocomplete="off"  class="form-control">\
            </div>\
            <div class="col-md-3">\
            <label class="control-label" for="sameLevel"><input type="checkbox" name="sameLevel" value="0" id="sameLevel'+(ind+1)+'"/>同级酒店</label>\
            </div>\
            </div>\
            <div class="form-group">\
            <label class="control-label col-md-3" for="hotelInput">酒店未确认\
        <input type="radio" name="hotelConfirm" value="0">\
            </label>\
            <div class="col-md-6">\
            <input type="text" placeholder="酒店名称" autocomplete="off"  id="hotelInput'+(ind+1)+'" name="hotelInput" class="form-control" disabled>\
        </div>\
        </div>\
        <div class="form-group">\
            <label class="control-label col-md-3" for="flight1'+(ind)+'">航班信息：</label>\
        <div class="col-md-6">\
            <input type="text" placeholder="航班" autocomplete="off"  id="flight'+ind+''+1+'" name="flight1" class="form-control">\
            </div>\
            <div class="col-sm-3">\
            <button class="btn btn-block btn-warning" type="button" id="flightAdd'+(ind+1)+'">增加</button>\
            </div>\
            </div>\
            <div class="form-group">\
            <label class="control-label  col-md-3" for="inputNamel'+(ind+1)+'">交通信息：</label>\
        <div class="col-md-9">\
            <input type="text" placeholder="交通信息" id="trafficInfo'+(ind+1)+'" name="trafficInfo" class="form-control">\
            </div>\
            </div>\
            <div class="form-group">\
            <label class="control-label col-md-3" for="shoppInfo'+(ind+1)+'">购物点：</label>\
        <div class="col-md-9">\
            <textarea placeholder="购物点" rows="3" class="form-control" name="shoppInfo" id="shoppInfo'+(ind+1)+'"></textarea>\
            </div>\
            </div>\
            <div class="form-group">\
            <label class="control-label col-md-3" for="cateringInfo'+(ind+1)+'"><strong>*</strong>餐饮说明：</label>\
        <div class="col-md-9">\
            <textarea placeholder="餐饮说明" rows="3" id="cateringInfo'+(ind+1)+'" name="cateringInfo" class="form-control"></textarea>\
            </div>\
            </div>\
            <div class="form-group">\
            <label class="control-label  col-sm-3" for="name'+(ind+1)+'">导游姓名：</label>\
        <div class="col-sm-9 row">\
            <div class="col-sm-4" style="display:none">\
            <p class="form-control-static"></p>\
            <input type="hidden" placeholder="导游姓名" id="leadName'+(ind+1)+'" readonly class="form-control" name="name">\
            </div>\
            <div class="col-sm-2" style="display:none;">\
            <button class="btn btn-primary pull-right btn-block btn-sm leaderSend" id="leaderSend2'+(ind+1)+'" type="button">搜索</button>\
            </div>\
            <div class="col-sm-2">\
            <button class="btn btn-primary btn-block btn-sm leaderadd" id="leaderAdd'+(ind+1)+'" type="button">添加</button>\
        </div>\
            <input type="hidden" name="leaderId" value=""/>\
            <input type="hidden" name="sex" value=""/>\
            </div>\
            </div>\
            <div class="form-group" style="display:none;">\
            <label class="control-label col-sm-3" for="leaderphone'+(ind+1)+'">导游电话：</label>\
        <div class="col-sm-6">\
        <p class="form-control-static"></p>\
        <input type="hidden" placeholder="联系电话" id="leaderphone'+(ind+1)+'" value="" readonly class="form-control" name="phone">\
        </div>\
        </div>\
            <div class="form-group">\
            <label class="control-label col-md-3" for="newEntry'+(ind+1)+'">新增标题：</label>\
        <div class="col-md-9">\
            <input type="text" placeholder="新增标题" id="newEntry'+(ind+1)+'" name="newEntry" class="form-control">\
            </div>\
            </div>\
            <div class="form-group">\
            <label class="control-label col-md-3" for="newContant'+(ind+1)+'">新增内容：</label>\
        <div class="col-md-9">\
            <textarea placeholder="新增内容" rows="3" id="newContant'+(ind+1)+'" name="newContant" class="form-control"></textarea>\
            </div>\
            </div>\
            <div class="input-group-btn">\
            <div class="col-sm-6 col-sm-offset-3">\
            <button class="btn btn-primary pull-right btn-block btn-sm scheduleDetailSave" type="submit">保存</button>\
            </div>\
            </div>\
            </div>';

        var htmlform='<div class="box box-info" style="display:none;">\
            <div class="box-header with-border">\
            <h3 class="box-title">D'+(ind+1)+'</h3>\
            </div>\
            <form role="form" action="'+CONTEXTPATH+'/schedule/scheduleDetailSave" method="post" id="form2'+(ind+1)+'" onsubmit="return validateCallback('+(ind+1)+',this)" class="scheduleDetailSave form-horizontal">\
            <div class="box-body">'+h+ '</div>\
            </form>\
            <div class="overlays" style="display:none;">\
            <i class="fa fa-refresh fa-spin"></i>\
            </div>\
            </div>';
        $(".plusDay").before(htmlform);
        dotform(ind);
    mySwiper.update();

}
function confirmScheduleInitform(){
    $("#confirmScheduleInitform").next(".overlays").show();
    $("#confirmScheduleInitform").ajaxForm({
        beforeSubmit:function(formData, jqForm, options){
            $("#confirmScheduleInitform").next(".overlays").show();
        },
        success:function(data) {
            if(data.retCode==0) {
                $("#confirmScheduleInitform").parents(".box-info:eq(0)").hide();
                $("#confirmScheduleInitform").parents(".box-info:eq(0)").next().show();
                var _index=$("#confirmScheduleInitform").parents(".box-info:eq(0)").index();
                $(".sui-steps").find(".wrap").eq(_index+1).find("div").addClass("current").removeClass("todo");
                $(".sui-steps").find(".wrap").eq(_index).find("div").addClass("finished");
                $(".sui-steps").find(".wrap").eq(_index).find("label>.round").html('<i class="sui-icon icon-pc-right"></i>');
            }else{
                $("#dialogModal").find(".modal-body>p").html(data.retMsg);
                blockUIOpenShare("dialogModal");
                $("#confirmScheduleInitform").bootstrapValidator('disableSubmitButtons', false);
            }
            $("#confirmScheduleInitform").next(".overlays").hide();
        },
        error: function(XmlHttpRequest, textStatus, errorThrown){
            $("#confirmScheduleInitform").next(".overlays").hide();
            $("#dialogModal").find(".modal-body>p").html("错误，请重试。");
            blockUIOpenShare("dialogModal");
        }
    });
}
function photoD1(){
    $('#schedulePhotosUrl1').diyUpload({
        url: CONTEXTPATH + '/cloudFile/uploadCloudPhotos',
        success: function (data) {
            var data = data;//$(data._raw).find("item").text();
            var len=[];
            $("#photoLibrary1").find(":hidden").each(function () {
                if (!$.trim($(this).val())) {
                    $(this).val(data);
                    len.push(data)
                    return false;
                }else{
                    len.push($(this).val());
                }
            })
            if(len.length>=3){
                $('#schedulePhotosUrl1').hide();
            }
        },
        buttonText: '<button type="button" class="btn btn-primary pull-right btn-block btn-sm">上传行程图片</button>',
        error: function (err) {
            //console.info(err);
        },
        fileNumLimit: 10
    });

}
function removeImages(obj,fun){
    var that=obj;
    if(that.attr("srcimg")) {
        $.ajax({
            type: "GET",
            url: CONTEXTPATH+"/cloudFile/deleteCloudPhotos?url=" + that.attr("srcimg"),
            success: function (data) {
                //webUploader.removeFile( "fileBox_WU_FILE_"+(parseInt(ind)+1));
                //that.parent("li").remove();
                //$("#featurePhoto").show();
                if(fun){
                    fun();
                }

            },
            error:function(){
                obj.parents("li").remove();
            }
        });
        $("body").find(":hidden").each(function () {
            if ($.trim($(this).val()) == that.attr("srcimg")) {
                $(this).val("");
                return false;
            }
        })

    }

}
function dateDiff(date1, date2){
    var type1 = typeof date1, type2 = typeof date2;
    if(type1 == 'string')
        date1 = stringToTime(date1);
    else if(date1.getTime)
        date1 = date1.getTime();
    if(type2 == 'string')
        date2 = stringToTime(date2);
    else if(date2.getTime)
        date2 = date2.getTime();
    return (date2 - date1) / 1000 / 60 / 60 / 24;//除1000是毫秒，不加是秒
}
//字符串转成Time(dateDiff)所需方法
function stringToTime(string){
    var f = string.split(' ', 2);
    var d = (f[0] ? f[0] : '').split('-', 3);
    var t = (f[1] ? f[1] : '').split(':', 3);
    return (new Date(
        parseInt(d[0], 10) || null,
        (parseInt(d[1], 10) || 1)-1,
        parseInt(d[2], 10) || null,
        parseInt(t[0], 10) || null,
        parseInt(t[1], 10) || null,
        parseInt(t[2], 10) || null)).getTime();
}

// 日期，在原有日期基础上，增加days天数，默认增加1天
function getNewDay(date, days) {
    if (days == undefined || days == '') {
        days = 1;
    }
    var date = new Date(date);
    date.setDate(date.getDate() + days);
    var month = date.getMonth() + 1;
    var day = date.getDate();
    return date.getFullYear() + '-' + getFormatDate(month) + '-' + getFormatDate(day);
}

// 日期月份/天的显示，如果是1位数，则在前面加上'0'
function getFormatDate(arg) {
    if (arg == undefined || arg == '') {
        return '';
    }

    var re = arg + '';
    if (re.length < 2) {
        re = '0' + re;
    }

    return re;
}
function memberDelete(id,obj){
    $("#dialogModal3").find(".modal-body>p").html("确认需要删除此位游客吗？");
    blockUIOpenShare("dialogModal3");
    $("#sureMember").one("click",function(){
        $.ajax({
            type: "GET",
            url: CONTEXTPATH + "/member/memberDeleteAjax",
            data: {id: id},
            dataType: "json",
            success: function (returndata) {
                if (returndata.retCode == 0) {
                    $(obj).parents("tr").remove();
                    $("#dialogModal").find(".modal-body>p").html("删除成功");
                    blockUIOpenShare("dialogModal");
                }else{
                    $("#dialogModal").find(".modal-body>p").html("删除失败，请重试");
                    blockUIOpenShare("dialogModal");
                }
            },
            error:function(){
                $("#dialogModal").find(".modal-body>p").html("删除失败，请重试");
                blockUIOpenShare("dialogModal");
            }
        })
    })

}

function memberUpdate(id,realName,sex,birthday,phone,cardType,cardNum,cardTime,countryCode,obj){
    $('#updateMemberform').find("[type=submit]").attr('disabled', false);
    $('#updateMemberform').find("[name=realName]").val($.trim($(obj).parents("tr").find("td").eq(0).text()));
    $('#updateMemberform').find("[name=sex]").val($.trim($(obj).parents("tr").find("td").eq(1).text())=="男"?1:2);
    $('#updateMemberform').find("[name=birthday]").val($.trim($(obj).parents("tr").find("td").eq(2).text()));
    if(countryCode) {
        $('#updateMemberform').find("[name=phone]").val($.trim($(obj).parents("tr").find("td").eq(3).text()).split(countryCode)[1]);
    }else{
        $('#updateMemberform').find("[name=phone]").val($.trim($(obj).parents("tr").find("td").eq(3).text()));
    }
    $('#updateMemberform').find("[name=cardType]").val($.trim($(obj).parents("tr").find("td").eq(4).text()));
    $('#updateMemberform').find("[name=cardNum]").val($.trim($(obj).parents("tr").find("td").eq(5).text()));
    $('#updateMemberform').find("[name=cardTime]").val($.trim($(obj).parents("tr").find("td").eq(6).text()));
    if(countryCode) {
        $("#areaCodeMemberLay").val(countryCode);
    }
    var updateList=function (obj,realName,sex,birthday,phone,cardType,cardNum,cardTime,countryCode){
        obj.find("td").eq(0).text(realName);
        obj.find("td").eq(1).text(sex);
        obj.find("td").eq(2).text(birthday);
        obj.find("td").eq(3).text(countryCode+phone);
        obj.find("td").eq(4).text(cardType);
        obj.find("td").eq(5).text(cardNum);
        obj.find("td").eq(6).text(cardTime);
    }
    countryCodeMember=countryCode;
    if(phone.indexOf("+")<0){
        phonesrc=countryCode+phone;
    }else{
        phonesrc=phone;
    }

    if($.trim($(obj).parents("tr").find("td").eq(4).text())){
        $("#cardTypeupdate").val(cardType);
        $("#updateMemberform").find("[name=cardNum]").parents(".form-group").show();
        $("#updateMemberform").find("[name=cardTime]").parents(".form-group").show();
    }else{
        $("#cardTypeupdate").val("无");
        $("#updateMemberform").find("[name=cardNum]").parents(".form-group").hide();
        $("#updateMemberform").find("[name=cardTime]").parents(".form-group").hide();
    }
    blockUIOpenShare("memberUpdateLay");
    $('#updateMemberform').find("[name=id]").val(id);
    $('#updateMemberform').bootstrapValidator({
        message: '填写信息格式不正确',
        feedbackIcons: {
            valid: '',
            invalid: '',
            validating: ''
        },
        fields: {
            realName: {
                message: '姓名格式不正确',
                validators: {
                    notEmpty: {
                        message: '姓名不能为空'
                    }
                }
            },
            sex: {
                validators: {
                    notEmpty: {
                        message: '请选择性别'
                    }
                }
            },
            birthday: {
                validators: {
                    date: {
                        format: 'YYYY-MM-DD',
                        message: '日期格式不正确'
                    }
                }
            },
            phone: {
                validators: {
                    notEmpty: {
                        message: '请填写手机号'
                    },
                    threshold :  11 , //有11字符以上才发送ajax请求，（input中输入一个字符，插件会向服务器发送一次，设置限制，6字符以上才开始）
                    phonememberCheck:{
                        message: '当前团队已存在该手机号，不可重复录入'
                    },
                    regexp: {
                        regexp:  /^[0-9]*$/,
                        message: '手机号码格式不正确'
                    }
                    //regexp: {
                    //    regexp:  /^(\+86)?((1[3,5,8][0-9])|(14[5,7])|(17[0,1,6,7,8]))\d{8}$/,
                    //    message: '手机号码格式不正确'
                    //}
                }
            },
            cardNum :{
                validators: {
                    notEmpty: {
                        message: '请填写证件号码'
                    }
                }
            },
            cardTime :{
                validators: {
                    notEmpty: {
                        message: '请填写证件有效期'
                    },
                    date: {
                        format: 'YYYY-MM-DD',
                        message: '日期格式不正确'
                    }
                }
            }
        },
        submitHandler: function (validator, form, submitButton) {

        }
    }).on('success.form.bv', function (e) {
        // Prevent form submission
        //e.preventDefault();
        //console.log('Coucou');
        var _form = $(e.target);
        _form.next(".overlays").show();
        _form.ajaxForm({
            success: function (data) {
                if(data.retCode==0){
                    var dataObjupdate=data.member;
                    var realNameupdate=dataObjupdate.realName?dataObjupdate.realName:"";
                    var sexupdate=dataObjupdate.sex == "1" ? '男' : '女';
                    var birthdayupdate=dataObjupdate.birthday?dataObjupdate.birthday:"";
                    var phoneupdate=dataObjupdate.phone?dataObjupdate.phone:"";
                    var cardTypeupdate=dataObjupdate.cardType?dataObjupdate.cardType:"";
                    var cardNumupdate=dataObjupdate.cardNum?dataObjupdate.cardNum:"";
                    var cardTimeupdate=dataObjupdate.cardTime?dataObjupdate.cardTime:"";
                    var countryCodeupdate=dataObjupdate.countryCode?dataObjupdate.countryCode:"";
                    if(dataObjupdate.id){
                      //  console.log( target)
                        $("#memberImportExcel").find("tr").each(function(i){
                            if(i>=1) {
                                if ($(this).find("a").attr("onclick").indexOf(dataObjupdate.id) > 0) {
                                    updateList($(this),realNameupdate,sexupdate,birthdayupdate,phoneupdate,cardTypeupdate,cardNumupdate,cardTimeupdate,countryCodeupdate);

                                }
                            }
                        })
                        _form.bootstrapValidator('disableSubmitButtons', false);
                        //清空
                        _form.resetForm();
                        _form.data('bootstrapValidator').resetForm();
                        _form.find("[name=cardType]").val("");
                        _form.find("[name=cardNum]").parents(".form-group").hide();
                        _form.find("[name=cardTime]").parents(".form-group").hide();
                    }
                    $("#dialogModal").find(".modal-body>p").html("修改成功");
                    blockUIOpenShare("dialogModal");
                }else{
                    $("#dialogModal").find(".modal-body>p").html(data.retMsg);
                    blockUIOpenShare("dialogModal");
                    _form.bootstrapValidator('disableSubmitButtons', false);
                }
                _form.next(".overlays").hide();
            },
            error: function(XmlHttpRequest, textStatus, errorThrown){
                _form.next(".overlays").hide();
                $("#dialogModal").find(".modal-body>p").html("错误，请重试。");
                blockUIOpenShare("dialogModal");
                _form.bootstrapValidator('disableSubmitButtons', false);
            }
        })
    });
    $("#areaCodeMemberLay").off().on("change",function(){

        countryCodeMember=$("#areaCodeMemberLay").val();
        phonesrc=countryCodeMember+phonesrc;
        $('#updateMemberform').data('bootstrapValidator').validateField("phone");
    })
}
function  autosearchRegion(obj) {
    var ms=obj.autocomplete({
        delay: 500,
        scrollHeight: 200,
        source: function(request, response ) {
            var term = request.term;
            if ( term in cacheRegion ) {
                response( $.map( cacheRegion[ term ], function( item ) {
                    return {
                        value:item.alias,
                        areaid:item.areaid,
                        country : item.country,
                        countryId :item.countryId
                    }
                }));
                return;
            }
            if($.trim(request.term).length>=1) {
                $.ajax({
                    url: CONTEXTPATH + '/schedule/getCityByContent',
                    dataType: "json",
                    //data: {
                    //    alias: $.trim(request.term)
                    //},
                    data: {
                        countryName:$.trim( request.term ),
                    },
                    success: function (data) {
                        cacheRegion[term] = data;
                        response($.map(data, function (item) {
                            // console.log(item)
                            return {
                                value: item.alias,
                                areaid: item.areaid,
                                country : item.country,
                                countryId :item.countryId
                            }
                        }));
                    }
                });
            }
        },
        minLength: 1,
        autoFocus: false,
        select: function( event, ui ) {
            $this = $(this);
            $(this).attr("areaid",ui.item.areaid);
            $(event.target).next().val(ui.item.areaid).change();
            $(event.target).next().next().val(ui.item.country).change();
            $(event.target).next().next().next().val(ui.item.countryId).change();
            setTimeout(function () {
                $this.blur();
            }, 1);
        },
        focus: function( event, ui ) {
          return false;
        },
        close : function( event, ui ){
           return false;
        }
    }).focus(function (event) {
        $(this).autocomplete("search");
    })
    obj.on("input",function(event){
        $(this).next().next().val("").change();
        $(this).next().next().next().val("").change();
    });
    //ms.data("ui-autocomplete")._renderMenu=function( ul, items ) {
    //    var that = this;
    //
    //    $.each( items, function( index, item ) {
    //        that._renderItemData( ul, item );
    //    });
    //    $( ul ).find( "li:odd" ).addClass( "odd" );
    //}
    if( ms.data("ui-autocomplete")) {
        ms.data("ui-autocomplete")._renderItem = function (ul, item) {
            return $("<li class='ui-menu-item' style='line-height:26px;padding:0 10px'></li>")
                .attr("data-value", item.value)
                .append("<div  style='display:inline-block;'>" + item.label + "</div>")
                .append("<div  style='display:inline-block;padding-left:10px;color:#999;font-size:12px;'>" + item.country + "</div>")
                .appendTo(ul);
        }
    }
}
function  autosearchSpot(obj) {

    obj.autocomplete({
        delay: 500,
        scrollHeight: 200,
        source: function(request, response ) {
            var term = request.term;
            //缓存
            //if ( term in cache ) {
            //    response( $.map( cache[ term ], function( item ) {
            //        return {
            //            value:item.name,
            //            mafengId:item.mafengId
            //        }
            //    }));
            //    return;
            //}
            var destinationArr=[];
            obj.parents("form").find("[id^=destination]").each(function(){
                if($(this).attr("id").indexOf("Btn")<0) {
                    var that = $(this);
                    if ($(this).attr("areaid")) {
                        destinationArr.push($(this).attr("areaid"));
                    } else {
                        $.ajax({
                            url: CONTEXTPATH + '/region/getRegion',
                            dataType: "json",
                            async: false,
                            data: {
                                alias: $.trim($(this).val())
                            },
                            success: function (data) {
                                if (data.length > 1) {
                                    for (var i = 0; i < data.length; i++) {
                                        if (data[i].lv == 3) {
                                            destinationArr.push(data[i].areaid);
                                            that.attr("areaid", data[i].areaid)
                                            return false;
                                        }
                                    }
                                } else {
                                    destinationArr.push(data[0].areaid);
                                    that.attr("areaid", data[0].areaid)

                                }
                            }
                        });
                    }
                }
            })
            if($.trim(request.term).length>=1) {
                $.ajax({
                    url: CONTEXTPATH + '/getSpotData',
                    dataType: "json",
                    data: {
                        query: request.term,
                        cities: destinationArr.join(",")
                    },
                    success: function (data) {
                        cache[term] = data;
                        response($.map(data, function (item) {
                            // console.log(item)
                            return {
                                value: item.name,
                                mafengId:item.mafengId
                            }
                        }));
                    }
                });
            }
    },
    minLength: 1,
    select: function( event, ui ) {
        // console.log(ui)
        $(event.target).next().val(ui.item.mafengId).change();
    }
}).focus(function (event) {
        $(this).autocomplete("search");
    });
    obj.on("input",function(event){
        $(this).next().val("").change();
    });;
}
function  autosearchHotel(obj) {
    obj.autocomplete({
        delay: 500,
        scrollHeight: 200,
        source: function(request, response ) {
            var term = request.term;
            if ( term in cacheHotel ) {
                response( $.map( cacheHotel[ term ], function( item ) {
                    return {
                        value:item.hotelChineseName,
                        hotelId:item.hotelId
                    }
                }));
                return;
            }
            var hotelArr=[];
            obj.parents("form").find("[id^=destination][type=text]").each(function(i){
                if(i==parseInt(obj.parents("form").find("[id^=destination][type=text]").length)-1) {
                    var that = $(this);
                    if ($(this).attr("areaid")) {
                        hotelArr.push($(this).attr("areaid"));
                    } else {
                        $.ajax({
                            url: CONTEXTPATH + '/region/getRegion',
                            dataType: "json",
                            async: false,
                            data: {
                                alias: $.trim($(this).val())
                            },
                            success: function (data) {
                                if(data.length>1) {
                                    for (var i = 0; i < data.length; i++) {
                                        if (data[i].lv == 3) {
                                            hotelArr.push(data[i].areaid);
                                            that.attr("areaid", data[i].areaid)
                                            return false;
                                        }
                                    }
                                }else{
                                    if(data[0].areaid) {
                                        hotelArr.push(data[0].areaid);
                                        that.attr("areaid", data[0].areaid)
                                    }
                                }
                            }
                        });
                    }
                }
            })
            if($.trim(request.term).length>=1) {
                $.ajax({
                    url: CONTEXTPATH + '/hotel/getHotelMessage',
                    dataType: "json",
                    data: {
                        hotelChineseName:$.trim( request.term ),
                        areaid: hotelArr.join(",")
                    },
                    success: function (data) {
                        cacheHotel[term] = data;
                        response($.map(data, function (item) {
                            return {
                                value: item.hotelChineseName,
                                hotelId:item.hotelId
                            }
                        }));
                    }
                });
            }
        },
        minLength: 1,
        select: function( event, ui ) {
            $(event.target).next().val(ui.item.hotelId)
        },
        close: function( event, ui ) {
           // $(event.target).next().val(ui.item.hotelId)
        }
    });
    obj.on( "autocompleteclose", function( event, ui ) {
        //console.log(event)
        $(event.target).val()
    } );
}
//getAirportData
function  autosearchAir(obj) {
    obj.autocomplete({
        delay: 500,
        scrollHeight: 200,
        source: function(request, response ) {
            var term = request.term;
            if ( term in cacheHotel ) {
                response( $.map( cacheHotel[ term ], function( item ) {
                    return {
                        value:item.fightNo+"   "+item.fromName+"起飞到"+item.toName+"（"+item.takeoffTime+"--"+item.arrivelTime+"）",
                        fromCode:item.fromCode
                    }
                }));
                return;
            }
            if($.trim(request.term).length>=1) {
                $.ajax({
                    url: CONTEXTPATH + '/getAirportData',
                    dataType: "json",
                    data: {
                        query: $.trim(request.term),
                    },
                    success: function (data) {
                        cacheHotel[term] = data;
                        response($.map(data, function (item) {
                            return {
                                value: item.fightNo + "   " + item.fromName + "起飞到" + item.toName + "（" + item.takeoffTime + "--" + item.arrivelTime + "）",
                                fromCode: item.fromCode
                            }
                        }));
                    }
                });
            }
        },
        _renderItem: function( ul, items ) {
            var that = this;
            $.each( items, function( index, item ) {
                that._renderItemData( ul, item );
            });
            $( ul ).find( "li:odd" ).addClass( "odd" );
        },
        minLength: 1,
        select: function( event, ui ) {
        }
    })

}