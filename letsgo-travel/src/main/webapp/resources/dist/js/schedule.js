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
var scheduleDetailScenicEntitys=[];
var shoppingScheduleList=[];
var ownExpenseScheduleList=[];
var ownExpenseMoreList=[];
var shoppingMoreList=[];
var sourceObjscenic="";
var sourceObjshopping="";
var sourceObjownExpense="";
var hotels=[];
$(document).ready(function(){
    /* $("#myNav").affix({
         offset: {
             top:125
         }
     });*/
    //http://localhost:8080/travel/region/getRegion?alias=%E5%8C%97%E4%BA%AC
    $("#spotForm>div,#hotelForm>div,#ownExpenseForm>div,#shoppingForm>div").css("height",$(window).height()*0.8);

    $("#addDesc").on("click",function(){
        var booldesc=false;
        var ind=$("#form4").find(".box").size();
        $("#form4").find(".box").each(function(i){
            var valdesc=$(this).find("input").val();
            var datadesc= CKEDITOR.instances["desccontent"+(i+1)].getData();
            if(valdesc&&datadesc){
                booldesc=true;
            }else{
                booldesc=false;
            }
        });
        if(!booldesc){
            $("#dialogModal").find(".modal-body>p").html("请先录入其他空白信息");
            blockUIOpenShare("dialogModal");
            return false;
        }
       var htmlDesc='<div class="box" id="desc'+(ind+1)+'">\
            <div class="box-body">\
            <div class="form-group">\
            <div class="col-sm-8 col-sm-offset-2">\
            <input type="text" placeholder="请输入标题" id="desctitle'+(ind+1)+'"  autocomplete="off" value="" class="form-control">\
            <div style="margin-top:20px;">\
            <textarea placeholder="请输入内容" rows="3" class="form-control"  value="" id="desccontent'+(ind+1)+'"></textarea>\
        </div>\
        </div>\
        </div>\
        </div>\
        </div>';
        $("#form4").find(".box:last").after(htmlDesc);
        ckeditor("desccontent"+(ind+1));
    });
    //$("#form4").find(".box").each(function(i){
    //    console.log(i)
    //    ckeditor("desccontent"+(i+1));
    //});
    $(".fileBoxUl").on("click",".schedulePhotos2 .diySuccess",function(){
        removeImages($(this))
    })
    $("#addShopping").on("click",function(){
        if($(this).parents(".form-horizontal").find("#shoppingInfo").length<=0) {
            var html = '<div class="form-group">\
                      <label class="control-label col-sm-3" for="shoppingInfo">购物场所：</label>\
                     <div class="col-sm-9">\
                        <input placeholder="请输入购物场所" class="form-control" value=""  id="shoppingInfo"/>\
                     </div>\
                   </div>';
            $(this).parents(".form-group").before(html);

            autosearchMoreShopping($("#shoppingInfo"));
        }else{
            $(this).parents(".form-group").prev().show();
        }
        $(this).parents(".form-group").hide();
    });
    $("#addFree").on("click",function(){
        if($(this).parents(".form-horizontal").find("#freeInfo").length<=0) {
            var html = '<div class="form-group">\
                      <label class="control-label col-sm-3" for="freeInfo">自费项目：</label>\
                     <div class="col-sm-9">\
                        <input placeholder="请输入自费项目" class="form-control" value=""  id="freeInfo"/>\
                     </div>\
                   </div>';
            $(this).parents(".form-group").before(html);

            autosearchownMoreExpense($("#freeInfo"));
        }else{
            $(this).parents(".form-group").prev().show();
        }
        $(this).parents(".form-group").hide();
    });
    $("#editSoppingpos").click(function(){
        window.document.getElementById('mapshopping').contentWindow.map.addListener('dragend', function() {
            window.document.getElementById('mapshopping').contentWindow.pixels();
        });
        //window.document.getElementById('mapshopping').contentWindow.markers[0].setMap(null);
        $("#pin",window.document.getElementById('mapshopping').contentWindow.document).show();
    });
    $("#editSpotpos").click(function(){
        window.document.getElementById('mapSpot').contentWindow.map.addListener('dragend', function() {
            window.document.getElementById('mapSpot').contentWindow.pixels();
        });
        //window.document.getElementById('mapSpot').contentWindow.markers[0].setMap(null);
        $("#pin",window.document.getElementById('mapSpot').contentWindow.document).show();
    });
    $("#edithotelpos").click(function(){
        window.document.getElementById('mapHotel').contentWindow.map.addListener('dragend', function() {
            window.document.getElementById('mapHotel').contentWindow.pixels();
        });
        //window.document.getElementById('mapHotel').contentWindow.markers[0].setMap(null);
        $("#pin",window.document.getElementById('mapHotel').contentWindow.document).show();
    });
    $("#editOwnpos").click(function(){
        window.document.getElementById('mapOwn').contentWindow.map.addListener('dragend', function() {
            window.document.getElementById('mapOwn').contentWindow.pixels();
        });
        //window.document.getElementById('mapOwn').contentWindow.markers[0].setMap(null);
        $("#pin",window.document.getElementById('mapOwn').contentWindow.document).show();
    });
    spotremovelay();
    hotelremovelay();
    ownremovelay();
    shoppingremovelay();
    ////评分星星
    //$(".scorerigger ul li").on("click",function(){
    //    $(this).parent().find("img").attr("src",CONTEXTPATH+"/resources/dist/images/star.png");
    //    $(this).prevAll().find("img").attr("src",CONTEXTPATH+"/resources/dist/images/star-bg.png");
    //    $(this).find("img").attr("src",CONTEXTPATH+"/resources/dist/images/star-bg.png");
    //    $(this).parent().find("[type=radio]").attr("checked",false);
    //    $(this).prevAll().find("[type=radio]").attr("checked",false);
    //    $(this).find("[type=radio]").attr('checked',true);
    //});
    $("#areaCode").on("change",function(){
        countryCodeLeader=$("#areaCode").val();
        phonesrc=countryCodeLeader+phonesrc;
        $('#form3').data('bootstrapValidator').validateField("phone");
    });
    ownTable(ownExpenseScheduleList,$("#freeTable"));
    shoppingTable(shoppingScheduleList,$("#shoppingTable"));
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
            $("#areaCodeMemberLay").empty().append(areaCode)
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
   // $(".box").on("ifChanged",'.proptCheck input',function(event){
   //     if($(this).is(":checked")){
   //         var ind=$(this).parents("label").index();
   //         $("#form3").find(".box").eq(ind).show();
   //     }else{
   //         var ind=$(this).parents("label").index();
   //         $("#form3").find(".box").eq(ind).hide();
   //     }
   // })
   //$(".proptCheck").find("input").each(function(){
   //     if($(this).is(":checked")){
   //         var ind=$(this).parents("label").index();
   //         $("#form3").find(".box").eq(ind).show();
   //     }else{
   //         var ind=$(this).parents("label").index();
   //         $("#form3").find(".box").eq(ind).hide();
   //     }
   // });

    autosearchStratPlace($("#startPlace"));
    autosearchStratPlace($("#startPlace1"));
    $(".hotel").find("input[name*=hotel]").each(function(){
        autosearchHotel($(this));
    })
    $(".form-group").find("input[id^=destination]").each(function(){
       autosearchRegion($(this));
    })
    //autocomplete插件
    //缓存

    $(".form-group").find("input[id*=scenicList]").each(function(){

        autosearchSpot($(this))
    });
    $(".form-group").find("input[id*=ownExpenseList]").each(function(){

        autosearchownExpense($(this))
    })
    $(".form-group").find("input[id*=shoppingList]").each(function(){

        autosearchShopping($(this))
    })
    //暂时去掉航班联想
    //$(".form-group").find("input[name*=flight]").each(function(){
    //
    //    autosearchAir($(this))
    //})

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
   // photoD1();

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
        if(num>=5){
            $("#dialogModal").find(".modal-body>p").html("最多可以添加6个目的地");
            blockUIOpenShare("dialogModal");
            return;
        }
        var booldes=false;
        $(this).parents("form").find("input[id^='destination'],input[id^=startPlace]").each(function(){
            if(!$(this).val()){
                booldes=true;
            }
        })
        if( booldes){
            $("#dialogModal").find(".modal-body>p").html("请把空白的目的地填写完成再添加");
            blockUIOpenShare("dialogModal");
            return false;
        }
        var that=$(this);
        //var addDes=function(obj,num){
        //    var html='<div class="form-group"><label for="destination'+(num+1)+'" class="control-label col-sm-3">目的地'+(num+1)+'：</label> <div class="col-sm-6"><input type="text" placeholder="目的地" class="form-control" autocomplete="off" name="destination'+(num+1)+'" id="destination'+(num+1)+'"><input type="hidden"  name="destinationId'+(num+1)+'" > <input type="hidden" name="country'+(num+1)+'"><input type="hidden" name="countryId'+(num+1)+'"><p class="help-block small">为了便于我们给游客展示准确的天气信息，请录入标准的目的地城市名称</p> </div> <div class="col-sm-3"> <button class="btn btn-block btn-danger destinationremove" type="button">删除</button></div></div>';
        //    if(obj.parents("form").find('#destination'+num).length>0){
        //        obj.parents("form").find('#destination'+num).parents(".form-group").after(html);
        //    }else{
        //        obj.parents("form").find("[id^=destination]").eq(0).parents(".form-group").after(html);
        //    }
        //    autosearchRegion($("#destination"+(num+1)));
        //   if(obj.parents("form").data('bootstrapValidator').getFieldElements('countryId'+(num+1))){
        //        obj.parents("form").data('bootstrapValidator').addField('countryId'+(num+1));
        //    }
        //    obj.parents("form").data('bootstrapValidator').enableFieldValidators('countryId'+(num+1), true);
        //    obj.parents("form").data('bootstrapValidator').resetField('countryId'+(num+1));
        //   // obj.parents("form").data('bootstrapValidator').validateField('countryId'+(num+1));
        //
        //    if(destinationBtnArr[1].indexOf(num+1)<=-1){
        //        destinationBtnArr[1].push((num+1));
        //    }
        //}
        var addDes=function(obj,num){
                var html='<div class="col-sm-4"><div class="row"><div class="col-sm-4" style="margin-bottom: 10px;">\
                    <select class="selectpicker form-control" name="traffic'+(num+1)+'">\
                        <option title="<i class=\'fa fa-fw fa-arrow-right\'></i>" data-icon="fa fa-fw  fa-arrow-right" value="0">暂未确定交通方式</option>\
                        <option title="<i class=\'fa fa-fw fa-plane\'></i>" data-icon="fa fa-fw  fa-plane"  value="1">飞机</option>\
                        <option title="<i class=\'fa fa-fw fa-train\'></i>" data-icon="fa fa-fw  fa-train"  value="2">火车</option>\
                        <option title="<i class=\'fa fa-fw fa-bus\'></i>" data-icon="fa fa-fw  fa-bus"  value="3">大巴</option>\
                        <option title="<i class=\'fa fa-fw fa-ship\'></i>" data-icon="fa fa-fw  fa-ship"  value="4">船</option>\
                    </select>\
                </div>\
                <div class="col-sm-8" style="margin-bottom: 10px;">\
                <input type="text" placeholder="请输入名称" class="form-control" autocomplete="off" name="destination'+(num+1)+'" id="destination'+(num+1)+'"> \
            <input type="hidden"  name="destinationId'+(num+1)+'" >  \
            <input type="hidden" name="country'+(num+1)+'"><input type="hidden" name="countryId'+(num+1)+'"></div></div></div>';
            //if(obj.parents("form").find('#destination'+num).length>0){
            //    obj.parents("form").find('#destination'+num).parents(".form-group").after(html);
            //}else{
            //    obj.parents("form").find("[id^=destination]").eq(0).parents(".form-group").after(html);
            //}
            //obj.parent().parent().children("div:eq("+(num+1)+")").show();
            obj.parent().before(html);
            $(".selectpicker").selectpicker();
            autosearchRegion($("#destination"+(num+1)));
            if(obj.parents("form").data('bootstrapValidator').getFieldElements('destination'+(num+1))){
                obj.parents("form").data('bootstrapValidator').addField('destination'+(num+1));
            }
            obj.parents("form").data('bootstrapValidator').enableFieldValidators('destination'+(num+1), true);
            obj.parents("form").data('bootstrapValidator').resetField('destination'+(num+1));
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
                        if (destinationBtnArr[1].indexOf(1) <= -1) {
                            addDes(that, 0);
                        }else if (destinationBtnArr[1].indexOf(3) <= -1) {
                            addDes(that, 2);
                        } else if (destinationBtnArr[1].indexOf(4) <= -1) {
                            addDes(that, 3);
                        }else if (destinationBtnArr[1].indexOf(5) <= -1) {
                            addDes(that, 4);
                        }
                    }
                }else if (num + 1 == 3) {
                    if (destinationBtnArr[1].indexOf(3) <= -1) {
                        addDes(that, 2);
                    } else {
                        if (destinationBtnArr[1].indexOf(1) <= -1) {
                            addDes(that, 0);
                        }else if (destinationBtnArr[1].indexOf(2) <= -1) {
                            addDes(that, 1);
                        } else if (destinationBtnArr[1].indexOf(4) <= -1) {
                            addDes(that, 3);
                        }else if (destinationBtnArr[1].indexOf(5) <= -1) {
                            addDes(that, 4);
                        }
                    }
                } else if (num + 1 == 4) {
                    if (destinationBtnArr[1].indexOf(4) <= -1) {
                        addDes(that, 3);
                    } else {
                        if (destinationBtnArr[1].indexOf(1) <= -1) {
                            addDes(that, 0);
                        }else if (destinationBtnArr[1].indexOf(2) <= -1) {
                            addDes(that, 1);
                        } else if (destinationBtnArr[1].indexOf(3) <= -1) {
                            addDes(that, 2);
                        } else if (destinationBtnArr[1].indexOf(5) <= -1) {
                            addDes(that, 4);
                        }
                    }
                }else if (num + 1 == 5) {
                    if (destinationBtnArr[1].indexOf(5) <= -1) {
                        addDes(that, 4);
                    } else {
                        if (destinationBtnArr[1].indexOf(1) <= -1) {
                            addDes(that, 0);
                        }else if (destinationBtnArr[1].indexOf(2) <= -1) {
                            addDes(that, 1);
                        } else if (destinationBtnArr[1].indexOf(3) <= -1) {
                            addDes(that, 2);
                        }else if (destinationBtnArr[1].indexOf(4) <= -1) {
                            addDes(that, 3);
                        }
                    }
                }
        }else{
            addDes(that, 0);
        }
        $(this).parents("form").find("input[type=text][name^=destination]").each(function(i){
            //$(this).attr("name","destination"+(i+1));
            $(this).parent().find("input").each(function(){
                $(this).attr("name", $(this).attr("name").substring(0,$(this).attr("name").length-1)+(i+1))
            })
        })
       // $(this).parents("form").data('bootstrapValidator').enableFieldValidators('country'+(num+1), true);
        //$(this).parents("form").data('bootstrapValidator').on('added.field.fv', function(e, data) {
        //})
        //$(this).parents("form").bootstrapValidator('enableFieldValidators', 'country'+(num+1), true)
    });
    //$("#flightAdd").on("click",function(){
    //    var num=$(this).parents("form").find("input[id^='flight']").size();
    //    if(num>=2){
    //        $("#dialogModal").find(".modal-body>p").html("最多可以添加2个航班");
    //        blockUIOpenShare("dialogModal");
    //        return;
    //    }
    //    var html='<div class="form-group"><label class="control-label col-md-3" for="flight'+(num+1)+'">航班信息'+(num+1)+'：</label><div class="col-md-3"><input type="text" placeholder="航班" id="flight'+(num+1)+'" autocomplete="off" name="flight'+(num+1)+'" class="form-control"></div><div class="col-sm-2"> <button class="btn btn-block btn-danger flightremove" type="button">删除</button></div></div>';
    //
    //    $(this).parents("form").find('#flight'+num).parents(".form-group").after(html);
    //    //autosearchAir($("#flight"+(num+1)))
    //})
    $("#scenicListBtn").on("click",function(){
        var num=$(this).parents("form").find("input[name^='scenicList']").size();
        //var m=parseInt($(this).attr("id").split("scenicListBtn")[1])-1;
        $(this).parents("form").find("input[id^='scenicList']").each(function(){
            num=parseInt($(this).attr("id").split("scenicList")[1]);
        })
        var that=$(this);
        //<label class="control-label col-md-3" for="scenicList'+(num+1)+'">景点'+(num+1)+'：</label>\
        var addScenic=function(obj,num){
            var html='<div class="form-group">\
            <div class="col-md-3 col-md-offset-3">\
            <input type="text" placeholder="景点" id="scenicList'+(num+1)+'"  autocomplete="off" class="form-control">\
            <a href="javascript:;" class="scenicListmove"><i class="fa fa-fw fa-trash"></i></a>\
            </div>\
            </div>';
            obj.parents("form").find('#scenicList'+num).parents(".form-group").after(html);
            autosearchSpot($('#scenicList'+(num+1)));
           /* if(obj.parents("form").data('bootstrapValidator').getFieldElements("scenicList["+num+"].spotId")){
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
            *///17.1.11
            //if(destinationBtnArr[1].indexOf(num+1)<=-1){
            //    destinationBtnArr[1].push((num+1));
            //}
        }
        addScenic(that,num);
    });
    $("#shoppingListBtn").on("click",function(){
        var num=$(this).parents("form").find("input[name^='shoppingList']").size();
        $(this).parents("form").find("input[id^='shoppingList']").each(function(){
            num=parseInt($(this).attr("id").split("shoppingList")[1]);
        })
        var that=$(this);
        //<label class="control-label col-md-3" for="shoppingList'+(num+1)+'">购物场所'+(num+1)+'：</label>\
        var addShopping=function(obj,num){
            var html='<div class="form-group">\
            <div class="col-md-3 col-md-offset-3">\
            <input type="text"  placeholder="购物场所" id="shoppingList'+(num+1)+'"   autocomplete="off" class="form-control">\
            <a href="javascript:;" class="shoppingListmove"><i class="fa fa-fw fa-trash"></i></a>\
            </div>\
            </div>';
            obj.parents("form").find('#shoppingList'+num).parents(".form-group").after(html);
            autosearchShopping($('#shoppingList'+(num+1)));
            /* if(obj.parents("form").data('bootstrapValidator').getFieldElements("scenicList["+num+"].spotId")){
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
             *///17.1.11
            //if(destinationBtnArr[1].indexOf(num+1)<=-1){
            //    destinationBtnArr[1].push((num+1));
            //}
        }
        addShopping(that,num);
    });
    $("#ownExpenseListBtn").on("click",function(){
        var num=$(this).parents("form").find("input[name^='ownExpenseList']").size();
        $(this).parents("form").find("input[id^='ownExpenseList']").each(function(){
            num=parseInt($(this).attr("id").split("ownExpenseList")[1]);
        })
        var that=$(this);
        //<label class="control-label col-md-3" for="ownExpenseList'+(num+1)+'">自费项目'+(num+1)+'：</label>\
        var addShopping=function(obj,num){
            var html='<div class="form-group">\
            <div class="col-md-3 col-md-offset-3">\
            <input type="text" placeholder="自费项目" id="ownExpenseList'+(num+1)+'"   autocomplete="off" class="form-control">\
            <a href="javascript:;" class="ownExpenseListmove"><i class="fa fa-fw fa-trash"></i></a>\
            </div>\
            </div>';
            obj.parents("form").find('#ownExpenseList'+num).parents(".form-group").after(html);
            autosearchownExpense($('#ownExpenseList'+(num+1)));
            /* if(obj.parents("form").data('bootstrapValidator').getFieldElements("scenicList["+num+"].spotId")){
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
             *///17.1.11
            //if(destinationBtnArr[1].indexOf(num+1)<=-1){
            //    destinationBtnArr[1].push((num+1));
            //}
        }
        addShopping(that,num);
    });
    $("#editArea").on("click",".box .closecity",function(event){
        event.preventDefault();
        event.stopPropagation();
        var form=$(this).parents("form");
        var name=$(this).parent().find("[name^=destination]").attr("name");
        form.data('bootstrapValidator').enableFieldValidators(name, false);
        //form.data('bootstrapValidator').removeField(name);
        var r_ind=form.attr("id").split("form2")[1];
        destinationBtnArr[r_ind].removeByValue(name.split("destination")[1]);
        //$(this).parent().find("[name^=destination]").autocomplete( "enable");
        //$(this).parent().find("[name^=destination]").removeAttr("readonly");
        $(this).parent().parent().parent().remove();
        //$(this).parent().prev().remove();
        //$(this).parent().remove();
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
    //$(".box-body").on("click",".destinationremove",function(event){
    //    event.preventDefault();
    //    event.stopPropagation();
    //    var form=$(this).parents("form");
    //    var name=$(this).parents(".form-group:eq(0)").find("[name^=countryId]").attr("name");
    //    form.data('bootstrapValidator').enableFieldValidators(name, false);
    //    //form.data('bootstrapValidator').removeField(name);
    //    $(this).parents(".form-group:eq(0)").empty().remove();
    //    var r_ind=form.attr("id").split("form2")[1]
    //    destinationBtnArr[r_ind].removeByValue(name.split("countryId")[1]);
    //});
    /*
    *删除景点
    * */
    $(".box-body").on("click",".scenicListmove",function(event){
        event.preventDefault();
        event.stopPropagation();
        var form=$(this).parents("form");
        var daynum=$(this).parents("form").attr("id").split("form2")[1];
        var scenicId=$(this).parents(".form-group:eq(0)").find("input[type=text]").attr("scenicid");
        var id=$(this).parents(".form-group:eq(0)").find("input[type=text]").attr("id");
        //var name=$(this).parents(".form-group:eq(0)").find("[name$=spotId]").attr("name");
       // form.data('bootstrapValidator').enableFieldValidators(name, false);
        $(this).parents(".form-group:eq(0)").remove();
        $.each(scheduleDetailScenicEntitys, function(i, item){
            if(i==daynum-1){
                $.each(item, function(t,items){
                    if(id&&id!=""&&items.code==id||scenicId&&scenicId!=""&&items.id==scenicId){
                        scheduleDetailScenicEntitys[i].splice(t,1);
                        return false;
                    }
                });
           }
        });
       // console.log(scheduleDetailScenicEntitys)
    });
    /*
     *删除每日第一个景点
     * */
    $(".box-body").on("click",".scenicListmoveStart",function(event){
        event.preventDefault();
        event.stopPropagation();
        var form=$(this).parents("form");
        var daynum=$(this).parents("form").attr("id").split("form2")[1];
        var scenicId=$(this).parents(".form-group:eq(0)").find("input[type=text]").attr("scenicid");
        var id=$(this).parents(".form-group:eq(0)").find("input[type=text]").attr("id");
        //var name=$(this).parents(".form-group:eq(0)").find("[name$=spotId]").attr("name");
        // form.data('bootstrapValidator').enableFieldValidators(name, false);

        $.each(scheduleDetailScenicEntitys, function(i, item){
            if(i==daynum-1){
                $.each(item, function(t,items){
                    if(id&&id!=""&&items.code==id||scenicId&&scenicId!=""&&items.id==scenicId){
                        scheduleDetailScenicEntitys[i].splice(t,1);
                        return false;
                    }
                });
            }
        });
        $(this).parents(".form-group:eq(0)").find("input[type=text]").val("").attr("scenicid","").show();
        $(this).parents(".form-group:eq(0)").find(".form-control-static").remove();

        //console.log(scheduleDetailScenicEntitys)
    });
    /*
     *编辑景点
     * */
    $("#editArea").on("click",".scenicListedit",function(event){
       //$(this).parents(".form-group:eq(0)").find(".form-control-static").remove();
       // $(this).parents(".form-group:eq(0)").find("input[type=text]").show();
        var daynum=$(event.target).parents("form").attr("id").split("form2")[1];
        var arr=scheduleDetailScenicEntitys[daynum-1];
        var scenicid=$(event.target).parents(".form-group:eq(0)").find("input[type=text]").attr("scenicid");
        var objId=$(event.target).parents(".form-group:eq(0)").find("input[type=text]").attr("id");
        var index;
        $.each(arr,function(i,item){
            if(scenicid&&item.id&&item.id==scenicid||item.code&&item.code==objId){
                index=i;
            }
        })
        if(arr[index].lon&&arr[index].lat){
            _lngnow=Number(arr[index].lon);
            _latnow=Number(arr[index].lat);
        }else{
            _lngnow=Number(116);
            _latnow=Number(39);
        }
        $("#spotName").html('<label class="control-label col-md-6"  id="spotname">'+arr[index].scenicName+'</label>').removeClass("has-success").removeClass("has-error");
        $("#brief").val(arr[index].briefintroduction);
        $("#scenicCity").val(arr[index].scenicCity);
        $("#scenicCity").attr("areaid",arr[index].cityId);
        $("#spotName").attr("daynum",daynum);
        $("#spotName").attr("eventid",objId);
        //$("#spotName").attr("editScenicId",ui.item.id);
        //$("#spotname").text(label);
        var imageList=arr[index].imageList;
        var imgHtml="";
        $('#scenicPhotosUrl1').show();
        photoScenic($('#scenicPhotosUrl1'),imageList);
        $.each(imageList, function(i, item){
            imgHtml+=' <li class="schedulePhotos2">\
                        <div class="viewThumb">\
                      <img src="'+item+'"/>\
                   </div>\
                   <div class="diyCancel"></div>\
                   <div title="删除" class="diySuccess" srcimg="'+item+'" style="display: block;"></div>\
                    <div class="diyBar" style="display: none;">\
                       <div class="diyProgress" style="width: 100%;"></div>\
                       <div class="diyProgressText">上传完成</div>\
                  </div>\
               </li>';
        });
        $("#scenicListImg").html(imgHtml);
        if(scenicid) {
            $("#spotName").attr("scenicId", scenicid);
        }else{
            $("#spotName").attr("scenicId", "");
        }
        $('#mapSpot').attr("src",CONTEXTPATH+"/mapsearch.html");
        autosearchsearSpot($("#scenicCity"));

        scenicremove();
        blockUIOpenShareNo("dialogModalspot");
        if($("#spotForm").data('bootstrapValidator').getFieldElements('spotname')) {
            $("#spotForm").data('bootstrapValidator').removeField("spotname");
        }
        $("#spotForm").data('bootstrapValidator').resetField("scenicCity");
        $(".returnCitySpot").off().on("click",function(){

            blockUIClose();
        });
    });
    /*
     *删除购物场所
     * */
    $(".box-body").on("click",".shoppingListmove",function(event){
        event.preventDefault();
        event.stopPropagation();
        var form=$(this).parents("form");
        var daynum=$(this).parents("form").attr("id").split("form2")[1];
        var shoppingId=$(this).parents(".form-group:eq(0)").find("input[type=text]").attr("shoppingId");
        var id=$(this).parents(".form-group:eq(0)").find("input[type=text]").attr("id");
        //var name=$(this).parents(".form-group:eq(0)").find("[name$=spotId]").attr("name");
        // form.data('bootstrapValidator').enableFieldValidators(name, false);
        $(this).parents(".form-group:eq(0)").remove();
        $.each(shoppingScheduleList, function(i, item){
            if(i==daynum-1){
                $.each(item, function(t,items){
                    if(id&&id!=""&&items.code==id||shoppingId&&shoppingId!=""&&items.id==shoppingId){
                        shoppingScheduleList[i].splice(t,1);
                        return false;
                    }
                });
            }
        });
        shoppingTable(shoppingScheduleList,$("#shoppingTable"));
        //console.log(shoppingScheduleList)
    });
    /*
     *删除每日第一个购物场所
     * */
    $(".box-body").on("click",".shoppingListmoveStart",function(event){
        event.preventDefault();
        event.stopPropagation();
        var form=$(this).parents("form");
        var daynum=$(this).parents("form").attr("id").split("form2")[1];
        var shoppingId=$(this).parents(".form-group:eq(0)").find("input[type=text]").attr("shoppingId");
        var id=$(this).parents(".form-group:eq(0)").find("input[type=text]").attr("id");
        //var name=$(this).parents(".form-group:eq(0)").find("[name$=spotId]").attr("name");
        // form.data('bootstrapValidator').enableFieldValidators(name, false);
        $.each(shoppingScheduleList, function(i, item){
            if(i==daynum-1){
                $.each(item, function(t,items){
                    if(id&&id!=""&&items.code==id||shoppingId&&shoppingId!=""&&items.id==shoppingId){
                        shoppingScheduleList[i].splice(t,1);
                        return false;
                    }
                });
            }
        });
        //console.log(shoppingScheduleList)
        $(this).parents(".form-group:eq(0)").find("input[type=text]").val("").attr("shoppingId","").show();
        $(this).parents(".form-group:eq(0)").find(".form-control-static").remove();

        shoppingTable(shoppingScheduleList,$("#shoppingTable"));
    });
    /*
     *编辑购物场所
     * */
    $("#editArea").on("click",".shoppingListedit",function(event){
        //$(this).parents(".form-group:eq(0)").find(".form-control-static").remove();
        //$(this).parents(".form-group:eq(0)").find("input[type=text]").show();

        var daynum=$(event.target).parents("form").attr("id").split("form2")[1];
        var arr=shoppingScheduleList[daynum-1];
        var shoppingId=$(event.target).parents(".form-group:eq(0)").find("input[type=text]").attr("shoppingid");
        var objId=$(event.target).parents(".form-group:eq(0)").find("input[type=text]").attr("id");
        var index;
        $.each(arr,function(i,item){
            if(shoppingId&&item.id&&item.id==shoppingId||item.code&&item.code==objId){
                index=i;
            }
        })
        if(arr[index].lon&&arr[index].lat){
            _lngnow=Number(arr[index].lon);
            _latnow=Number(arr[index].lat);
        }else{
            _lngnow=Number(116);
            _latnow=Number(39);
        }

        $("#shoppingName").html('<label class="control-label col-md-6"  id="shoppingname">'+arr[index].shoppingName+'</label>').removeClass("has-success").removeClass("has-error");

        $("#shoppingCity").val(arr[index].shoppingCity);
        $("#sellProducts").val(arr[index].sellProducts);
        $("#residenceTime").val(arr[index].residenceTime);
        $("#shoppingCity").attr("areaid",arr[index].cityId);
        $("#shoppingName").attr("daynum",daynum);
        $("#shoppingName").attr("eventid",objId);
        //$("#shoppingName").attr("editShoppingId",ui.item.id);

        if(shoppingId) {
            $("#shoppingName").attr("shoppingId", shoppingId);
        }else{
            $("#shoppingName").attr("shoppingId", "");
        }
        $('#mapshopping').attr("src",CONTEXTPATH+"/mapsearch.html");
        autosearchsearSpot($("#shoppingCity"));

        shoppingremove();
        blockUIOpenShareNo("dialogModalshopping");
        if($("#shoppingForm").data('bootstrapValidator').getFieldElements('shoppingname')) {
            $("#shoppingForm").data('bootstrapValidator').removeField("shoppingname");
        }
        $("#shoppingForm").data('bootstrapValidator').resetField("shoppingCity");
        $(".returnCityShopping").off().on("click",function(){

            blockUIClose();
        });
    });
    /*
     *删除自费项目
     * */
    $(".box-body").on("click",".ownExpenseListmove",function(event){
        event.preventDefault();
        event.stopPropagation();
        var form=$(this).parents("form");
        var daynum=$(this).parents("form").attr("id").split("form2")[1];
        var ownExpenseId=$(this).parents(".form-group:eq(0)").find("input[type=text]").attr("ownExpenseId");
        var id=$(this).parents(".form-group:eq(0)").find("input[type=text]").attr("id");
        //var name=$(this).parents(".form-group:eq(0)").find("[name$=spotId]").attr("name");
        // form.data('bootstrapValidator').enableFieldValidators(name, false);
        $(this).parents(".form-group:eq(0)").remove();
        $.each(ownExpenseScheduleList, function(i, item){
            if(i==daynum-1){
                $.each(item, function(t,items){
                    if(id&&id!=""&&items.code==id||ownExpenseId&&ownExpenseId!=""&&items.id==ownExpenseId){
                        ownExpenseScheduleList[i].splice(t,1);
                        return false;
                    }
                });
            }
        });
        ownTable(ownExpenseScheduleList,$("#freeTable"));
        //console.log(ownExpenseScheduleList)
    })
    /*
     *删除每日第一个自费项目
     * */
    $(".box-body").on("click",".ownExpenseListmoveStart",function(event){
        event.preventDefault();
        event.stopPropagation();
        var form=$(this).parents("form");
        var daynum=$(this).parents("form").attr("id").split("form2")[1];
        var ownExpenseId=$(this).parents(".form-group:eq(0)").find("input[type=text]").attr("ownExpenseId");
        var id=$(this).parents(".form-group:eq(0)").find("input[type=text]").attr("id");
        //var name=$(this).parents(".form-group:eq(0)").find("[name$=spotId]").attr("name");
        // form.data('bootstrapValidator').enableFieldValidators(name, false);
        $.each(ownExpenseScheduleList, function(i, item){
            if(i==daynum-1){
                $.each(item, function(t,items){
                    if(id&&id!=""&&items.code==id||ownExpenseId&&ownExpenseId!=""&&items.id==ownExpenseId){
                        ownExpenseScheduleList[i].splice(t,1);
                        return false;
                    }
                });
            }
        });
        $(this).parents(".form-group:eq(0)").find("input[type=text]").val("").attr("ownExpenseId","").show();
        $(this).parents(".form-group:eq(0)").find(".form-control-static").remove();
        ownTable(ownExpenseScheduleList,$("#freeTable"));
        //console.log(ownExpenseScheduleList)
    })
    /*
     *编辑自费项目
     * */
    $("#editArea").on("click",".ownExpenseListedit",function(event){
        //$(this).parents(".form-group:eq(0)").find(".form-control-static").remove();
        //$(this).parents(".form-group:eq(0)").find("input[type=text]").show();
        var daynum=$(event.target).parents("form").attr("id").split("form2")[1];
        var arr=ownExpenseScheduleList[daynum-1];
        var ownExpenseId=$(event.target).parents(".form-group:eq(0)").find("input[type=text]").attr("ownexpenseid");
        var objId=$(event.target).parents(".form-group:eq(0)").find("input[type=text]").attr("id");
        var index;
        $.each(arr,function(i,item){
            try {
                if (ownExpenseId && item.id && item.id == ownExpenseId || item.code && item.code == objId) {
                    index = i;
                }
            }catch(e){

            }
        })

        if(arr[index].lon&&arr[index].lat){
            _lngnow=Number(arr[index].lon);
            _latnow=Number(arr[index].lat);
        }else{
            _lngnow=Number(116);
            _latnow=Number(39);
        }
        var imgHtml="";
        $("#ownExpenseName").html('<label class="control-label col-md-6"  id="ownExpensename">'+arr[index].itemName+'</label>').removeClass("has-success").removeClass("has-error");

        $("#ownbrief").val(arr[index].briefintroduction);
        $("#ownExpenseCity").val(arr[index].itemCity);
        //$("#ownExpenseCity").attr("areaid",destionationId);
        $("#ownExpenseName").attr("daynum",daynum);
        $("#ownExpenseName").attr("eventid",objId);
       // $("#ownExpenseName").attr("editOwnId",ui.item.id);
        $("#limitNumber").val(arr[index].limitNumber);
        $("#serviceItem").val(arr[index].serviceItem);
        $("#referencePrice").val(arr[index].referencePrice);
        var photoUrls=arr[index].photoUrls;
        $("#ownExpensePhotosUrl1").show();
        photoScenic($('#ownExpensePhotosUrl1'),photoUrls);
        $.each(photoUrls, function(i, item){
            imgHtml+=' <li class="schedulePhotos2">\
                        <div class="viewThumb">\
                      <img src="'+item+'"/>\
                   </div>\
                   <div class="diyCancel"></div>\
                   <div title="删除" class="diySuccess" srcimg="'+item+'" style="display: block;"></div>\
                    <div class="diyBar" style="display: none;">\
                       <div class="diyProgress" style="width: 100%;"></div>\
                       <div class="diyProgressText">上传完成</div>\
                  </div>\
               </li>';
        });
        $("#ownExpenseListImg").html(imgHtml);
        if(ownExpenseId) {
            $("#ownExpenseName").attr("ownExpenseId", ownExpenseId);
        }else{
            $("#ownExpenseName").attr("ownExpenseId", "");
        }
        $('#mapOwn').attr("src",CONTEXTPATH+"/mapsearch.html");
        autosearchsearSpot($("#ownExpenseCity"));

        ownExpenseremove();
        blockUIOpenShareNo("dialogModalownExpense");
        if($("#ownExpenseForm").data('bootstrapValidator').getFieldElements('ownExpensename')) {
            $("#ownExpenseForm").data('bootstrapValidator').removeField("ownExpensename");
        }
        $("#ownExpenseForm").data('bootstrapValidator').resetField("ownExpenseCity");
        $(".returnCityOwn").off().on("click",function(){

            blockUIClose();
        });

    });
    //$("body").on("click","input[name=hotelConfirm]",function(){
    //
    //    if($(this).is(":checked")){
    //        if($(this).parent("label.control-label").siblings("div").find("input[type=text]").attr("name")=="hotel"){
    //            $(this).parents(".form-group:eq(0)").next(".form-group").find("input[type=text]").attr("disabled",true);
    //            $(this).parents(".form-group:eq(0)").next(".form-group").find("input[type=hidden]").attr("disabled",true);
    //            $(this).parent("label.control-label").siblings("div").find("input[type=checkbox]").removeAttr("disabled");
    //        }else{
    //            $(this).parents(".form-group:eq(0)").prev(".form-group").find("input[type=text]").attr("disabled",true);
    //            $(this).parents(".form-group:eq(0)").prev(".form-group").find("input[type=hidden]").attr("disabled",true);
    //            $(this).parents(".form-group:eq(0)").prev(".form-group").find("input[type=checkbox]").attr("disabled",true);
    //        }
    //        $(this).parent("label.control-label").siblings("div").find("input[type=hidden]").removeAttr("disabled");
    //        $(this).parent("label.control-label").siblings("div").find("input[type=text]").removeAttr("disabled");
    //    }
    //})
    $("body").on("click","input[name=hotelConfirm]",function(){
        var parentobj=$(this).parents("form");
        if($(this).val()==0){
            parentobj.find(".hotelInput").show();
            parentobj.find(".hotel").hide();
            parentobj.find(".hotelInput").find("input[type=text]").removeAttr("disabled");
            parentobj.find(".hotelInput").find("input[type=hidden]").removeAttr("disabled");
            parentobj.find(".hotel").find("input[type=text]").attr("disabled",true);
            parentobj.find(".hotel").find("input[type=hidden]").attr("disabled",true);
            parentobj.find(".hotel").find("input[type=checkbox]").attr("disabled",true);
        }else{
            parentobj.find(".hotel").show();
            parentobj.find(".hotelInput").hide();
            parentobj.find(".hotel").find("input[type=text]").removeAttr("disabled");
            parentobj.find(".hotel").find("input[type=hidden]").removeAttr("disabled");
            parentobj.find(".hotel").find("input[type=checkbox]").removeAttr("disabled");
            parentobj.find(".hotelInput").find("input[type=text]").attr("disabled",true);
            parentobj.find(".hotelInput").find("input[type=hidden]").attr("disabled",true);
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
            dataobj1.tId=tId;
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
            dataobj1.tId=tId;
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
                        message: '该行程中已存在此号码的成员或者已经存在此手机号的领队，请尝试搜索选择！'
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
            dataobj1.tId = tId;

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
    jQuery('#form1').find("[type=submit]").click(function(){
        //需要手动更新CKEDITOR字段
        for ( instance in CKEDITOR.instances )
            CKEDITOR.instances[instance].updateElement();
        return true;
    });
    /*保存行程概述*/
    $('#form1').bootstrapValidator({
        //excluded:[":disabled"],//':hidden', ':not(:visible)'//包含disabled的元素的校验
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
                        max: 500,
                        message: '行程名称不能超过500个字符'
                    }
                }
            },
            startPlace: {
               // trigger:"change",
                validators: {
                    notEmpty: {
                        message: '请选择一个出发地'
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
            emergencyContact: {
                validators: {
                    stringLength: {
                        min: 1,
                        max: 500,
                        message: '紧急联系人不能超过500个字符'
                    }
                }
            }
            //,
            //leadName: {
            //    validators: {
            //        notEmpty: {
            //            message: '请选择领队'
            //        }
            //    }
            //}
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
            //startPlace: {
            //    validators: {
            //        notEmpty: {
            //            message: '出发地不能为空'
            //        }
            //    }
            //},
            //destination1: {
            //    validators: {
            //        notEmpty: {
            //            message: '目的地不能为空'
            //        }
            //    }
            //},
            startPlace:{
                trigger:"change",
                validators: {
                    notEmpty: {
                        message: '请选择一个城市'
                    }
                }
            },
            destination1:{
                trigger:"change",
                validators: {
                    notEmpty: {
                        message: '请选择一个目的地'
                    }
                }
            },
            destination2:{
                trigger:"change",
                validators: {
                    notEmpty: {
                        message: '请选择一个目的地'
                    }
                }
            },
            destination3:{
                trigger:"change",
                validators: {
                    notEmpty: {
                        message: '请选择一个目的地'
                    }
                }
            },
            destination4:{
                trigger:"change",
                validators: {
                    notEmpty: {
                        message: '请选择一个目的地'
                    }
                }
            },
            destination5:{
                trigger:"change",
                validators: {
                    notEmpty: {
                        message: '请选择一个目的地'
                    }
                }
            },
            //"scenicList[0].spotId":{
            //    trigger:"change",
            //    validators: {
            //        scenicCheck: {
            //            message: '请选择一个景点'
            //        }
            //    }
            //},
            //"scenicList[1].spotId":{
            //    trigger:"change",
            //    validators: {
            //        scenicCheck: {
            //            message: '请选择一个景点'
            //        }
            //    }
            //},
            //"scenicList[2].spotId":{
            //    trigger:"change",
            //    validators: {
            //        scenicCheck: {
            //            message: '请选择一个景点'
            //        }
            //    }
            //},
            //"scenicList[3].spotId":{
            //    trigger:"change",
            //    validators: {
            //        scenicCheck: {
            //            message: '请选择一个景点'
            //        }
            //    }
            //},
            //"scenicList[4].spotId":{
            //    trigger:"change",
            //    validators: {
            //        scenicCheck: {
            //            message: '请选择一个景点'
            //        }
            //    }
            //},
            //"scenicList[5].spotId":{
            //    trigger:"change",
            //    validators: {
            //        scenicCheck: {
            //            message: '请选择一个景点'
            //        }
            //    }
            //},
            //"scenicList[6].spotId":{
            //    trigger:"change",
            //    validators: {
            //        scenicCheck: {
            //            message: '请选择一个景点'
            //        }
            //    }
            //},
            //"scenicList[7].spotId":{
            //    trigger:"change",
            //    validators: {
            //        scenicCheck: {
            //            message: '请选择一个景点'
            //        }
            //    }
            //},
            //"scenicList[8].spotId":{
            //    trigger:"change",
            //    validators: {
            //        scenicCheck: {
            //            message: '请选择一个景点'
            //        }
            //    }
            //},
            //"scenicList[9].spotId":{
            //    trigger:"change",
            //    validators: {
            //        scenicCheck: {
            //            message: '请选择一个景点'
            //        }
            //    }
            //},
            //"scenicList[10].spotId":{
            //    trigger:"change",
            //    validators: {
            //        scenicCheck: {
            //            message: '请选择一个景点'
            //        }
            //    }
            //},
            //"scenicList[11].spotId":{
            //    trigger:"change",
            //    validators: {
            //        scenicCheck: {
            //            message: '请选择一个景点'
            //        }
            //    }
            //},
            //"scenicList[12].spotId":{
            //    trigger:"change",
            //    validators: {
            //        scenicCheck: {
            //            message: '请选择一个景点'
            //        }
            //    }
            //},
            desc: {
                trigger:"change",
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
    // 保存更多信息
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
        },
        submitHandler: function (validator, form, submitButton) {
            var _form = form;
        }
    }).on('success.form.bv', function (e) {
        // Prevent form submission
        e.preventDefault();
        var _form = $(e.target);
    }).on('added.field.bv', function (e) {
        //validateField
        var _form = $(e.target)
        //_form.data('bootstrapValidator').validateField();
    })

    // 保存其他信息
    $("#form4").bootstrapValidator({
        excluded:[":disabled"],//':hidden', ':not(:visible)'//包含disabled的元素的校验
        message: '填写信息格式不正确',
        feedbackIcons:{
            valid: '',
            invalid: '',
            validating: ''
        },
        fields: {
            //content:{
            //    validators: {
            //        /* notEmpty: {
            //         message: '酒店名称不能为空'
            //         },*/
            //        stringLength: {
            //            min: 1,
            //            max: 32,
            //            message: '新增标题名称不能超过32个字符'
            //        }
            //    }
            //}
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
        //_form.data('bootstrapValidator').validateField();
    });
    // 保存其他信息
    $("#hotelForm").bootstrapValidator({
        excluded:[":disabled"],//':hidden', ':not(:visible)'//包含disabled的元素的校验
        message: '填写信息格式不正确',
        feedbackIcons:{
            valid: '',
            invalid: '',
            validating: ''
        },
        fields: {
            hotelCity:{
                validators: {
                     notEmpty: {
                     message: '酒店所在城市不能为空'
                     }
                    //stringLength: {
                    //    min: 1,
                    //    max: 32,
                    //    message: '新增标题名称不能超过32个字符'
                    //}
                }
            }
            //},
            //hotelnames:{
            //    trigger:"change",
            //    validators: {
            //        notEmpty: {
            //            message: '酒店所在城市不能为空'
            //        }
            //    }
            //}
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
    // 保存其他信息
    $("#shoppingForm").bootstrapValidator({
        excluded:[":disabled"],//':hidden', ':not(:visible)'//包含disabled的元素的校验
        message: '填写信息格式不正确',
        feedbackIcons:{
            valid: '',
            invalid: '',
            validating: ''
        },
        fields: {
            shoppingCity:{
                validators: {
                    notEmpty: {
                        message: '购物场所所在城市不能为空'
                    }
                    //stringLength: {
                    //    min: 1,
                    //    max: 32,
                    //    message: '新增标题名称不能超过32个字符'
                    //}
                }
            }
            //},
            //hotelnames:{
            //    trigger:"change",
            //    validators: {
            //        notEmpty: {
            //            message: '酒店所在城市不能为空'
            //        }
            //    }
            //}
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
    // 保存其他信息
    $("#ownExpenseForm").bootstrapValidator({
        excluded:[":disabled"],//':hidden', ':not(:visible)'//包含disabled的元素的校验
        message: '填写信息格式不正确',
        feedbackIcons:{
            valid: '',
            invalid: '',
            validating: ''
        },
        fields: {
            ownExpenseCity:{
                validators: {
                    notEmpty: {
                        message: '自费项目所在城市不能为空'
                    }
                    //stringLength: {
                    //    min: 1,
                    //    max: 32,
                    //    message: '新增标题名称不能超过32个字符'
                    //}
                }
            }
            //},
            //hotelnames:{
            //    trigger:"change",
            //    validators: {
            //        notEmpty: {
            //            message: '酒店所在城市不能为空'
            //        }
            //    }
            //}
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
    // 保存其他信息
    $("#spotForm").bootstrapValidator({
        excluded:[":disabled"],//':hidden', ':not(:visible)'//包含disabled的元素的校验
        message: '填写信息格式不正确',
        feedbackIcons:{
            valid: '',
            invalid: '',
            validating: ''
        },
        fields: {
            scenicCity:{
                validators: {
                    notEmpty: {
                        message: '景点所在城市不能为空'
                    }
                    //stringLength: {
                    //    min: 1,
                    //    max: 32,
                    //    message: '新增标题名称不能超过32个字符'
                    //}
                }
            }
            //},
            //hotelnames:{
            //    trigger:"change",
            //    validators: {
            //        notEmpty: {
            //            message: '酒店所在城市不能为空'
            //        }
            //    }
            //}
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
            $("#dialogModal").find(".modal-body>p").html("请导入游客再点击下一步");
            blockUIOpenShare("dialogModal");
            return;
        }
        $(this).parents(".box-info:eq(0)").hide();
        $(this).parents(".box-info:eq(0)").next().show();
        var _index= $(this).parents(".box-info:eq(0)").index();
        $(".sui-steps").find(".wrap").eq(_index+1).find("div").addClass("current").removeClass("todo");
        $(".sui-steps").find(".wrap").eq(_index).find("div").addClass("finished");
        $(".sui-steps").find(".wrap").eq(_index).find("label>.round").html('<i class="sui-icon icon-pc-right"></i>');
    })
    $("#noneMember").on("click",function(event){
        event.preventDefault();
        $(this).parents(".box-info:eq(0)").hide();
        $(this).parents(".box-info:eq(0)").next().show();
        var _index= $(this).parents(".box-info:eq(0)").index();
        $(".sui-steps").find(".wrap").eq(_index+1).find("div").addClass("current").removeClass("todo");
        $(".sui-steps").find(".wrap").eq(_index).find("div").addClass("finished");
        $(".sui-steps").find(".wrap").eq(_index).find("label>.round").html('<i class="sui-icon icon-pc-right"></i>');
    })
    $("#sure").on("click",function(){
        window.location.href=CONTEXTPATH+"/schedule/mainPage";
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
    $("body").on("input","input[type=text][name^=startPlace]",function(){
        if(!$(this).val()){
            $(this).parent().find(".closecitystart").remove()
        }
    })
    $("body").on("input","input[type=text][name^=destination]",function(){
        if(!$(this).val()){
            $(this).parent().find(".closecity").remove()
        }
    })
    $("#hotelForm").on("submit",function(){
        validateSubmitCallback(this,hotelUpdate);
        return false;
    })
    $("#spotForm").on("submit",function(){
        validateSubmitCallback(this,spotUpdate);
        return false;
    })
    $("#shoppingForm").on("submit",function(){
        validateSubmitCallback(this,shoppingUpdate);
        return false;
    })
    $("#ownExpenseForm").on("submit",function(){
        validateSubmitCallback(this,ownExpenseUpdate);
        return false;
    })
    //$('#featurePhoto').diyUpload({
    //    url: CONTEXTPATH + '/cloudFile/uploadCloudPhotos',
    //    success: function (data) {
    //        var data = data;//$(data._raw).find("item").text();
    //        $("[name=featurePhoto]").val(data);
    //        $("#featurePhoto").hide();
    //    },
    //    buttonText: '<button type="button" class="btn btn-primary pull-right btn-block btn-sm">上传行程特色图片</button>',
    //    error: function (err) {
    //        //console.info(err);
    //    },
    //    fileNumLimit: 1
    //});

})
Array.prototype.removeByValue = function(val) {
    for(var i=0; i<this.length; i++) {
        if(this[i] == val) {
            this.splice(i, 1);
            break;
        }
    }
}
function validateCallbackMoreInfo(form, callback, confirmMsg){
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


    _form.parent(".box-body").next(".overlays").show();



            if (!_form.find("[name=id]").val() && _form.attr("action").indexOf("promptInfoUpdate") > 0) {
                var url = CONTEXTPATH + "/schedule/promptInfoSave";
            }
            if (_form.find("[name=id]").val() && _form.attr("action").indexOf("promptInfoSave") > 0) {
                var url = CONTEXTPATH + "/schedule/promptInfoUpdate";
            }
            if (!$.trim(_form.find("[name=scheduleId]").val())) {
                $("#dialogModal").find(".modal-body>p").html("请先保存行程概述");
                blockUIOpenShare("dialogModal");
                _form.bootstrapValidator('disableSubmitButtons', false);
                return false;
            }
   var promptInfo= {
        id:_form.find("[name=id]").val(),
        scheduleId:_form.find("[name=scheduleId]").val(),
        shoppingScheduleList:shoppingMoreList,
        ownExpenseScheduleList:ownExpenseMoreList,
        travelAgencyInfo:$("#travelAgencyInfo").val(),
        groupClubInfo:$("#groupClubInfo").val(),
        shoppingPlace:CKEDITOR.instances["shoppingPlace"].getData(),
        ownExpenceInfo:CKEDITOR.instances["ownExpenceInfo"].getData()
    }
    $.ajax({
        type : _form.attr("method")|| 'POST',
        url : url||_form.attr("action"),
        contentType: 'application/json;charset=utf-8',
        context: $('body'),
        data :JSON.stringify(promptInfo),
        dataType : "json",
        cache : false,
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
    return false;
}
function validateCallbackDesc(form, callback, confirmMsg){
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
    if (!_form.find("[name=id]").val() && _form.attr("action").indexOf("updateScheduleDesc") > 0) {
        var url = CONTEXTPATH + "/schedule/createScheduleDesc";
    }
    if (_form.find("[name=id]").val() && _form.attr("action").indexOf("createScheduleDesc") > 0) {
        var url = CONTEXTPATH + "/schedule/updateScheduleDesc";
    }
    var desc=[];
    _form.find(".box").each(function(i){
        var valdesc=$(this).find("input").val();
        var datadesc= CKEDITOR.instances["desccontent"+(i+1)].getData();
        if(valdesc&&datadesc) {
            desc[i] = {};
            desc[i].scheduleId = _form.find("[name=scheduleId]").val();
            desc[i].id = $(this).find("[name=id]").val();
            desc[i].title = valdesc;
            desc[i].content = datadesc;
        }
    })
    if (!$.trim(_form.find("[name=scheduleId]").val())) {
        $("#dialogModal").find(".modal-body>p").html("请先保存行程概述");
        blockUIOpenShare("dialogModal");
        _form.bootstrapValidator('disableSubmitButtons', false);
        return false;
    }
    $.ajax({
        type : _form.attr("method")|| 'POST',
        url : url||_form.attr("action"),
        contentType: 'application/json;charset=utf-8',
        context: $('body'),
        data :JSON.stringify({scheduleDescs:desc}),
        dataType : "json",
        cache : false,
        success: function (data) {
            if (data.retCode == 0) {
                $("#dialogModal").find(".modal-body>p").html("保存成功");
                blockUIOpenShare("dialogModal");
                _form.attr("action", CONTEXTPATH + "/schedule/updateScheduleDesc");
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
                $(".overlays").hide();
            }
            _form.next(".overlays").hide();
        },
        error: function (XmlHttpRequest, textStatus, errorThrown) {
            _form.next(".overlays").hide();
            $("#dialogModal").find(".modal-body>p").html("错误，请重试。");
            blockUIOpenShare("dialogModal");
        }
    });
    return false;
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
        //_form.ajaxSubmit({
        //
        //    beforeSubmit: function (formData, jqForm, options) {
        //
        //        var scheduleDetailEntity={scheduleDetailEntity:formData};
        //        formData=scheduleDetailEntity;
        //        console.log(formData)
        //        if (!_form.find("[name=id]").val() && _form.attr("action").indexOf("scheduleDetailUpdate") > 0) {
        //            options.url = CONTEXTPATH + "/schedule/scheduleDetailSave";
        //        }
        //        if (_form.find("[name=id]").val() && _form.attr("action").indexOf("scheduleDetailSave") > 0) {
        //            options.url = CONTEXTPATH + "/schedule/scheduleDetailUpdate";
        //        }
        //        if (!$.trim(_form.find("[name=jourId]").val())) {
        //            $("#dialogModal").find(".modal-body>p").html("请先保存行程概述");
        //            blockUIOpenShare("dialogModal");
        //            _form.bootstrapValidator('disableSubmitButtons', false);
        //            return false;
        //        }
        //    },
        //    data: {
        //        'title': 111,
        //        'content': 222
        //    },
        //    success: function (data) {
        //        if (data.retCode == 0) {
        //            $("#dialogModal").find(".modal-body>p").html("保存成功");
        //            blockUIOpenShare("dialogModal");
        //            _form.attr("boolable", true);
        //            _form.find("[name=id]").val(data.scheduleDetailEntity.id);
        //            _form.attr("action", CONTEXTPATH + "/schedule/scheduleDetailUpdate");
        //            _form.bootstrapValidator('disableSubmitButtons', false);
        //            _form.parent(".box").hide();
        //            _form.parent(".box").next().show();
        //            if(num==1) {
        //                $(".sui-steps").find(".wrap").eq(2).find("div").addClass("current").removeClass("todo");
        //                $(".sui-steps").find(".wrap").eq(1).find("div").addClass("finished");
        //                $(".sui-steps").find(".wrap").eq(1).find("label>.round").html('<i class="sui-icon icon-pc-right"></i>');
        //                $("#editArea>div.box").each(function (i) {
        //                    if (i >= 2) {
        //                        $("#editArea>div.box").eq(i).find('.webuploader-container').find("label").parent().css({
        //                            width: $("#editArea>div.box").eq(i).find('.webuploader-container').find("button").outerWidth(),
        //                            height: $("#editArea>div.box").eq(i).find('.webuploader-container').find("button").outerHeight()
        //                        })
        //                    }
        //                })
        //                mySwiper.slideTo(2, 1000, true);
        //            }else if(num>1) {
        //                $(".sui-steps").find(".wrap").eq(num+1).find("div:eq(0)").addClass("current").removeClass("todo");
        //                $(".sui-steps").find(".wrap").eq(num).find("div").addClass("finished");
        //                $(".sui-steps").find(".wrap").eq(num).find("label>.round").html('<i class="sui-icon icon-pc-right"></i>');
        //                $("#editArea>div.box").each(function (i) {
        //                    if (i >= num) {
        //                        $("#editArea>div.box").eq(i).find('.webuploader-container').find("label").parent().css({
        //                            width: $("#editArea>div.box").eq(i).find('.webuploader-container').find("button").outerWidth(),
        //                            height: $("#editArea>div.box").eq(i).find('.webuploader-container').find("button").outerHeight()
        //                        })
        //                    }
        //                })
        //                mySwiper.slideTo((num + 1), 1000, true);
        //            }
        //
        //        } else {
        //            $("#dialogModal").find(".modal-body>p").html(data.retMsg);
        //            blockUIOpenShare("dialogModal");
        //            _form.bootstrapValidator('disableSubmitButtons', false);
        //        }
        //        _form.next(".overlays").hide();
        //    },
        //    error: function (XmlHttpRequest, textStatus, errorThrown) {
        //        _form.next(".overlays").hide();
        //        $("#dialogModal").find(".modal-body>p").html("错误，请重试。");
        //        blockUIOpenShare("dialogModal");
        //    }
        //})
        if (!_form.find("[name=id]").val() && _form.attr("action").indexOf("scheduleDetailUpdate") > 0) {
                        var url = CONTEXTPATH + "/schedule/scheduleDetailSave";
        }
        if (_form.find("[name=id]").val() && _form.attr("action").indexOf("scheduleDetailSave") > 0) {
            url = CONTEXTPATH + "/schedule/scheduleDetailUpdate";
        }
        if (!$.trim(_form.find("[name=jourId]").val())) {
            $("#dialogModal").find(".modal-body>p").html("请先保存行程概述");
            blockUIOpenShare("dialogModal");
            _form.bootstrapValidator('disableSubmitButtons', false);
            return false;
        }
        var cityDefined=[];
        _form.find("[name^='startPlace'][type='text']").each(function(){
            if(!$(this).next().val()){
                cityDefined.push({id:$(this).attr("id"),name:$(this).attr("name"),value:$(this).val()});
            }
        });
        _form.find("[name^='destination'][type=text]").each(function(){
            if(!$(this).next().val()){
                cityDefined.push({id:$(this).attr("id"),name:$(this).attr("name"),value:$(this).val()});
            }
        });
        if(cityDefined.length>0){
            _form.next(".overlays").hide();
            blockUIOpenShareNo("dialogModalsave");
            var cityHtml="";
            var autoCityid=[];
            for(var i=0;i<cityDefined.length;i++){
                cityHtml+='<div class="col-md-12"><div class="col-md-2">'+cityDefined[i].value+'</div><div class="col-md-4">距离最近的城市</div><div class="col-md-3"><input type="text" id="sear'+cityDefined[i].id+'" value=""  class="form-control" autocomplete="off"  placeholder="请输入距离最近的城市名称"> </div></div>';
                $("#dialogModalsave").find(".citydefined").html(cityHtml);
                autoCityid.push(cityDefined[i].id);
            }
            for(var m=0;m<autoCityid.length;m++){
                autosearchsearRegion($("#sear"+autoCityid[m]));
            }

            _form.bootstrapValidator('disableSubmitButtons', false);
            $(".returnCitysave").off().on("click",function(){
                _form.bootstrapValidator('disableSubmitButtons', false);
                blockUIClose();
            })
            $("#saveCity").off().on("click",function(){
                submitDetial(url,_form,num);
            })
            return false;
        }
        submitDetial(url,_form,num);

    }else{
        $("#dialogModal").find(".modal-body>p").html("必须先保存行程概述");
        blockUIOpenShare("dialogModal");
    }
    return false;
}
function submitDetial(url,obj,num){
   // var serializeParam=obj.serialize();
   // var data=paramString2obj(serializeParam);
    var serializeParam={}
    serializeParam.cateringInfo=obj.find("[name=cateringInfo]").val();
    serializeParam.dayNum=obj.find("[name=dayNum]").val();
    serializeParam.desc=obj.find("[name=desc]").val();
    serializeParam.jourId=obj.find("[name=jourId]").val();
    serializeParam.scheduleDate=obj.find("[name=scheduleDate]").val();
    serializeParam.id=obj.find("[name=id]").val();
    if(obj.find("[name=newEntry]").val()) {
        serializeParam.newEntry = obj.find("[name=newEntry]").val();
    }
    if(obj.find("[name=newContant]").val()) {
        serializeParam.newContant = obj.find("[name=newContant]").val();
    }

    if(obj.find("[name=flight1]").val()) {
        serializeParam.flight1 = obj.find("[name=flight1]").val();
    }
    if(obj.find("[name=startPlace]").val()) {
        serializeParam.startPlace = obj.find("[name=startPlace]").val();
        serializeParam.startPlaceId=obj.find("[name=startPlaceId]").val();
    }
    if(obj.find("[name=traffic1]").val()) {
        serializeParam.traffic1 = obj.find("[name=traffic1]").val();
    }
    if(obj.find("[name=traffic2]").val()) {
        serializeParam.traffic2 = obj.find("[name=traffic2]").val();
    }
    if(obj.find("[name=traffic3]").val()) {
        serializeParam.traffic3 = obj.find("[name=traffic3]").val();
    }
    if(obj.find("[name=traffic4]").val()) {
        serializeParam.traffic4 = obj.find("[name=traffic4]").val();
    }
    if(obj.find("[name=traffic5]").val()) {
        serializeParam.traffic5 = obj.find("[name=traffic5]").val();
    }
    if(obj.find("[name=destinationId1]").val()) {
        serializeParam.destinationId1 = obj.find("[name=destinationId1]").val();
    }
    if(obj.find("[name=destinationId2]").val()) {
        serializeParam.destinationId2 = obj.find("[name=destinationId2]").val();
    }
    if(obj.find("[name=destinationId3]").val()) {
        serializeParam.destinationId3 = obj.find("[name=destinationId3]").val();
    }
    if(obj.find("[name=destinationId4]").val()) {
        serializeParam.destinationId4 = obj.find("[name=destinationId4]").val();
    }
    if(obj.find("[name=destinationId5]").val()) {
        serializeParam.destinationId5 = obj.find("[name=destinationId5]").val();
    }
    if(obj.find("[name=destination1]").val()) {
        serializeParam.destination1 = obj.find("[name=destination1]").val();
    }
    if(obj.find("[name=destination2]").val()) {
        serializeParam.destination2 = obj.find("[name=destination2]").val();
    }
    if(obj.find("[name=destination3]").val()) {
        serializeParam.destination3 = obj.find("[name=destination3]").val();
    }
    if(obj.find("[name=destination4]").val()) {
        serializeParam.destination4 = obj.find("[name=destination4]").val();
    }
    if(obj.find("[name=destination5]").val()) {
        serializeParam.destination5 = obj.find("[name=destination5]").val();
    }
    if(obj.find("[name=countryId1]").val()) {
        serializeParam.countryId1 = obj.find("[name=countryId1]").val();
    }
    if(obj.find("[name=countryId2]").val()) {
        serializeParam.countryId2 = obj.find("[name=countryId2]").val();
    }
    if(obj.find("[name=countryId3]").val()) {
        serializeParam.countryId3 = obj.find("[name=countryId3]").val();
    }
    if(obj.find("[name=countryId4]").val()) {
        serializeParam.countryId4 = obj.find("[name=countryId4]").val();
    }
    if(obj.find("[name=countryId5]").val()) {
        serializeParam.countryId5 = obj.find("[name=countryId5]").val();
    }
    if(obj.find("[name=country1]").val()) {
        serializeParam.country1 = obj.find("[name=country1]").val();
    }
    if(obj.find("[name=country2]").val()) {
        serializeParam.country2 = obj.find("[name=country2]").val();
    }
    if(obj.find("[name=country3]").val()) {
        serializeParam.country3 = obj.find("[name=country3]").val();
    }
    if(obj.find("[name=country4]").val()) {
        serializeParam.country4 = obj.find("[name=country4]").val();
    }
    if(obj.find("[name=country5]").val()) {
        serializeParam.country5 = obj.find("[name=country5]").val();
    }
    //CKEDITOR.instances["desccontent"+(i+1)].getData();
    if(hotels[num - 1]) {
        $.each(hotels, function (i, item) {
            if (i == num - 1) {
                if(obj.find("input[name=hotelId]").val()) {
                    hotels[i]["id"] = obj.find("input[name=hotelId]").val();
                }
                var hotelConfirm = obj.find("input[name=hotelConfirm]:checked").val();
                if (hotelConfirm == 1) {
                    //var sameLevel = obj.find("input[name=sameLevel]").val();
                    //if (obj.find("input[name=sameLevel]").is(":checked")) {
                    //    sameLevel = 1;
                    //} else {
                    //    sameLevel = 0;
                    //}
                    //hotels[i]["hotelConfirm"] = 1;
                    //hotels[i]["hotelName"] = obj.find("input[name=hotel]").val();
                    //hotels[i]["sameLevel"] = sameLevel;
                } else {
                    hotels[i]["hotelConfirm"] = 0;
                    hotels[i]["hotelInput"] = obj.find("input[name=hotelInput]").val();
                    hotels[i]["briefintroduction"] = null;
                    hotels[i]["city"] =  null;
                    hotels[i]["lon"] = null;
                    hotels[i]["lat"] = null;
                    hotels[i]["photoUrls"] = [];
                }
            }
        });
    }else if(!hotels[num - 1]){
        hotels[num - 1]={};
        if(obj.find("input[name=hotelId]").val()) {
            hotels[num - 1]["id"] = obj.find("input[name=hotelId]").val();
        }
        var hotelConfirm = obj.find("input[name=hotelConfirm]:checked").val();
        if (hotelConfirm == 1) {
            var sameLevel = obj.find("input[name=sameLevel]").val();
            if (obj.find("input[name=sameLevel]").is(":checked")) {
                sameLevel = 1;
            } else {
                sameLevel = 0;
            }
            hotels[num - 1]["hotelConfirm"] = 1;
            hotels[num - 1]["hotelName"] = obj.find("input[name=hotel]").val();
            hotels[num - 1]["sameLevel"] = sameLevel;
        } else {
            hotels[num - 1]["hotelConfirm"] = 0;
            hotels[num - 1]["hotelInput"] = obj.find("input[name=hotelInput]").val();
            hotels[num - 1]["briefintroduction"] = null;
            hotels[num - 1]["city"] = null;
            hotels[num - 1]["lon"] =  null;
            hotels[num - 1]["lat"] =  null;
            hotels[num - 1]["photoUrls"] = [];
        }
    }

    $.ajax({
        type : obj.attr("method")|| 'POST',
        url : url||obj.attr("action"),
        contentType: 'application/json;charset=utf-8',
        context: $('body'),
        data :JSON.stringify({
            scheduleDetailEntity:serializeParam,
            scheduleDetailScenicEntitys:scheduleDetailScenicEntitys[num-1],
            hotelScheduleEntity:hotels[num-1],
            shoppingScheduleList:shoppingScheduleList[num-1],
            ownExpenseScheduleList:ownExpenseScheduleList[num-1]
        }),
        dataType : "json",
        cache : false,
        success :  function (data) {
            if (data.retCode == 0) {
                $("#dialogModal").find(".modal-body>p").html("保存成功");
                blockUIOpenShare("dialogModal");
                obj.attr("boolable", true);
                obj.find("[name=id]").val(data.scheduleDetailEntity.id);
                obj.attr("action", CONTEXTPATH + "/schedule/scheduleDetailUpdate");
                obj.bootstrapValidator('disableSubmitButtons', false);
                obj.parent(".box").hide();
                obj.parent(".box").next().show();
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
                ownTable(ownExpenseScheduleList,$("#freeTable"));
                shoppingTable(shoppingScheduleList,$("#shoppingTable"));

            } else {
                $("#dialogModal").find(".modal-body>p").html(data.retMsg);
                blockUIOpenShare("dialogModal");
                obj.bootstrapValidator('disableSubmitButtons', false);
            }
            obj.next(".overlays").hide();
        },
        error: function (XmlHttpRequest, textStatus, errorThrown) {
            obj.next(".overlays").hide();
            $("#dialogModal").find(".modal-body>p").html("错误，请重试。");
            blockUIOpenShare("dialogModal");
        }
    });
}
function validateSubmitCallback(form, callback, confirmMsg){
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


    callback(_form)
    return false;
}
/*
 serializedParams格式为"key1=value1&key2=value2".
 也支持'key.sonkey=value'
 */
function paramString2obj (serializedParams) {
    var obj={};
    function evalThem (str) {
        var strAry = new Array();
        strAry = str.split("=");
        //使用decodeURIComponent解析uri 组件编码
        for(var i = 0; i < strAry.length; i++){
            strAry[i] = decodeURIComponent(strAry[i]);
        }
        var attributeName = strAry[0];
        var attributeValue = strAry[1].trim();
        //如果值中包含"="符号，需要合并值
        if(strAry.length > 2){
            for(var i = 2;i<strAry.length;i++){
                attributeValue += "="+strAry[i].trim();
            }
        }
        if(!attributeValue){
            return ;
        }
        var attriNames = attributeName.split("."),
            curObj = obj;
        for(var i = 0; i < (attriNames.length - 1); i++){
            curObj[attriNames[i]]?"":(curObj[attriNames[i]] = {});
            curObj = curObj[attriNames[i]];
        }
        //使用赋值方式obj[attributeName] = attributeValue.trim();替换
        //eval("obj."+attributeName+"=\""+attributeValue.trim()+"\";");
        //解决值attributeValue中包含单引号、双引号时无法处理的问题
        curObj[attriNames[i]] = attributeValue.trim();
    };
    var properties = serializedParams.split("&");
    for (var i = 0; i < properties.length; i++) {
        //处理每一个键值对
        evalThem(properties[i]);
    };
    return obj;
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
               // $("#form"+i).bootstrapValidator('revalidateField', 'leadName');
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
        //$("#form"+i).bootstrapValidator('revalidateField', 'leadName');
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
            //startPlace: {
            //    validators: {
            //        notEmpty: {
            //            message: '出发地不能为空'
            //        }
            //    }
            //},
            startPlace:{
                trigger:"change",
                validators: {
                    notEmpty: {
                        message: '请选择一个出发地'
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
            destination1:{
                trigger:"change",
                validators: {
                    notEmpty: {
                        message: '请选择一个目的地'
                    }
                }
            },
            destination2:{
                trigger:"change",
                validators: {
                    notEmpty: {
                        message: '请选择一个目的地'
                    }
                }
            },
            destination3:{
                trigger:"change",
                validators: {
                    notEmpty: {
                        message: '请选择一个目的地'
                    }
                }
            },
            destination4:{
                trigger:"change",
                validators: {
                    notEmpty: {
                        message: '请选择一个目的地'
                    }
                }
            },
            destination5:{
                trigger:"change",
                validators: {
                    notEmpty: {
                        message: '请选择一个目的地'
                    }
                }
            },
            //"scenicList[0].spotId":{
            //    trigger:"change",
            //    validators: {
            //        scenicCheck: {
            //            message: '请选择一个景点'
            //        }
            //    }
            //},
            //"scenicList[1].spotId":{
            //    trigger:"change",
            //    validators: {
            //        scenicCheck: {
            //            message: '请选择一个景点'
            //        }
            //    }
            //},
            //"scenicList[2].spotId":{
            //    trigger:"change",
            //    validators: {
            //        scenicCheck: {
            //            message: '请选择一个景点'
            //        }
            //    }
            //},
            //"scenicList[3].spotId":{
            //    trigger:"change",
            //    validators: {
            //        scenicCheck: {
            //            message: '请选择一个景点'
            //        }
            //    }
            //},
            //"scenicList[4].spotId":{
            //    trigger:"change",
            //    validators: {
            //        scenicCheck: {
            //            message: '请选择一个景点'
            //        }
            //    }
            //},
            //"scenicList[5].spotId":{
            //    trigger:"change",
            //    validators: {
            //        scenicCheck: {
            //            message: '请选择一个景点'
            //        }
            //    }
            //},
            //"scenicList[6].spotId":{
            //    trigger:"change",
            //    validators: {
            //        scenicCheck: {
            //            message: '请选择一个景点'
            //        }
            //    }
            //},
            //"scenicList[7].spotId":{
            //    trigger:"change",
            //    validators: {
            //        scenicCheck: {
            //            message: '请选择一个景点'
            //        }
            //    }
            //},
            //"scenicList[8].spotId":{
            //    trigger:"change",
            //    validators: {
            //        scenicCheck: {
            //            message: '请选择一个景点'
            //        }
            //    }
            //},
            //"scenicList[9].spotId":{
            //    trigger:"change",
            //    validators: {
            //        scenicCheck: {
            //            message: '请选择一个景点'
            //        }
            //    }
            //},
            //"scenicList[10].spotId":{
            //    trigger:"change",
            //    validators: {
            //        scenicCheck: {
            //            message: '请选择一个景点'
            //        }
            //    }
            //},
            //"scenicList[11].spotId":{
            //    trigger:"change",
            //    validators: {
            //        scenicCheck: {
            //            message: '请选择一个景点'
            //        }
            //    }
            //},
            //"scenicList[12].spotId":{
            //    trigger:"change",
            //    validators: {
            //        scenicCheck: {
            //            message: '请选择一个景点'
            //        }
            //    }
            //},
            desc: {
                trigger:"change",
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
        if(num>=5){
            $("#dialogModal").find(".modal-body>p").html("最多可以添加6个目的地");
            blockUIOpenShare("dialogModal");
            return;
        }
        var booldes=false;
        $(this).parents("form").find("input[id^='destination'],input[id^=startPlace]").each(function(){
            if(!$(this).val()){
                booldes=true;
            }
        })
        if( booldes){
            $("#dialogModal").find(".modal-body>p").html("请把空白的目的地填写完成再添加");
            blockUIOpenShare("dialogModal");
            return false;
        }
        var that=$(this);
        var addDes=function(obj,num){
            //var html='<div class="form-group"><label for="destination'+(ind)+''+(num+1)+'" class="control-label col-sm-3">目的地'+(num+1)+'：</label> <div class="col-sm-6"><input type="text" placeholder="目的地"  class="form-control" autocomplete="off" id="destination'+(ind)+''+(num+1)+'" name="destination'+(num+1)+'"> <input type="hidden"  name="destinationId'+(num+1)+'" ><input type="hidden" name="country'+(num+1)+'"><input type="hidden" name="countryId'+(num+1)+'"><p class="help-block small">为了便于我们给游客展示准确的天气信息，请录入标准的目的地城市名称</p></div> <div class="col-sm-3"> <button class="btn btn-block btn-danger destinationremove" type="button">删除</button></div></div>';
            var html=' <div class="col-sm-4"><div class="row"><div class="col-sm-4" style="margin-bottom: 10px;">\
                    <select class="selectpicker form-control" name="traffic'+(num+1)+'">\
                        <option title="<i class=\'fa fa-fw fa-arrow-right\'></i>" data-icon="fa fa-fw  fa-arrow-right" value="0">暂未确定交通方式</option>\
                        <option title="<i class=\'fa fa-fw fa-plane\'></i>" data-icon="fa fa-fw  fa-plane"  value="1">飞机</option>\
                        <option title="<i class=\'fa fa-fw fa-train\'></i>" data-icon="fa fa-fw  fa-train"  value="2">火车</option>\
                        <option title="<i class=\'fa fa-fw fa-bus\'></i>" data-icon="fa fa-fw  fa-bus"  value="3">大巴</option>\
                        <option title="<i class=\'fa fa-fw fa-ship\'></i>" data-icon="fa fa-fw  fa-ship"  value="4">船</option>\
                    </select>\
                </div>\
                <div class="col-sm-8" style="margin-bottom: 10px;">\
                <input type="text" placeholder="请输入名称" class="form-control" autocomplete="off" name="destination'+(num+1)+'" id="destination'+ind+''+(num+1)+'"> \
            <input type="hidden"  name="destinationId'+(num+1)+'" >  \
            <input type="hidden" name="country'+(num+1)+'"><input type="hidden" name="countryId'+(num+1)+'"></div></div></div>';
            //obj.parent().parent().children("div:eq("+(num+1)+")").show();
            obj.parent().before(html);
            $(".selectpicker").selectpicker();
            //if( obj.parents("form").find('#destination' + m + '' + (parseInt(num))).length>0) {
            //    obj.parents("form").find('#destination' + m + '' + (parseInt(num))).parents(".form-group").after(html);
            //}else{
            //    obj.parents("form").find("[id^=destination]").eq(0).parents(".form-group").after(html);
            //}
            autosearchRegion($("#destination"+(ind)+(num+1)));

            if(obj.parents("form").data('bootstrapValidator').getFieldElements('destination'+(num+1))){
                obj.parents("form").data('bootstrapValidator').addField('destination'+(num+1));
            }
            obj.parents("form").data('bootstrapValidator').enableFieldValidators('destination'+(num+1), true);

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
                    if (destinationBtnArr[(ind+1)].indexOf(1) <= -1) {
                        addDes(that, 0);
                    }else if (destinationBtnArr[(ind+1)].indexOf(3) <= -1) {
                        addDes(that, 2);
                    } else if (destinationBtnArr[(ind+1)].indexOf(4) <= -1) {
                        addDes(that, 3);
                    }else if (destinationBtnArr[(ind+1)].indexOf(5) <= -1) {
                        addDes(that, 4);
                    }
                }
            }else if (num + 1 == 3) {
                if (destinationBtnArr[(ind+1)].indexOf(3) <= -1) {
                    addDes(that, 2);
                } else {
                    if (destinationBtnArr[(ind+1)].indexOf(1) <= -1) {
                        addDes(that, 0);
                    }else if (destinationBtnArr[(ind+1)].indexOf(2) <= -1) {
                        addDes(that, 1);
                    } else if (destinationBtnArr[(ind+1)].indexOf(4) <= -1) {
                        addDes(that, 3);
                    }else if (destinationBtnArr[(ind+1)].indexOf(5) <= -1) {
                        addDes(that, 4);
                    }
                }
            } else if (num + 1 == 4) {
                if (destinationBtnArr[(ind+1)].indexOf(4) <= -1) {
                    addDes(that, 3);
                } else {
                    if (destinationBtnArr[(ind+1)].indexOf(1) <= -1) {
                        addDes(that, 0);
                    }else if (destinationBtnArr[(ind+1)].indexOf(2) <= -1) {
                        addDes(that, 1);
                    } else if (destinationBtnArr[(ind+1)].indexOf(3) <= -1) {
                        addDes(that, 2);
                    }else if (destinationBtnArr[(ind+1)].indexOf(5) <= -1) {
                        addDes(that, 4);
                    }
                }
            }else if (num + 1 == 5) {
                if (destinationBtnArr[(ind+1)].indexOf(5) <= -1) {
                    addDes(that, 4);
                } else {
                    if (destinationBtnArr[(ind+1)].indexOf(1) <= -1) {
                        addDes(that, 0);
                    }else if (destinationBtnArr[(ind+1)].indexOf(2) <= -1) {
                        addDes(that, 1);
                    } else if (destinationBtnArr[(ind+1)].indexOf(3) <= -1) {
                        addDes(that, 2);
                    }else if (destinationBtnArr[(ind+1)].indexOf(4) <= -1) {
                        addDes(that, 3);
                    }
                }
            }
        }else{
            addDes(that, 0);
        }
        $(this).parents("form").find("input[type=text][name^=destination]").each(function(i){
            //$(this).attr("name","destination"+(i+1));
            $(this).parent().find("input").each(function(){
                $(this).attr("name", $(this).attr("name").substring(0,$(this).attr("name").length-1)+(i+1))
            })
        })
    });
    $("#form2"+(ind+1)).find(".hotel").find("input[name*=hotel]").each(function(){
        autosearchHotel($(this));
    })
    //$("#flightAdd"+(ind+1)).on("click",function(){
    //    var num=$(this).parents("form").find("input[id^='flight']").size();
    //    var m=parseInt($(this).attr("id").split("flightAdd")[1])-1;
    //    if(num>=2){
    //        $("#dialogModal").find(".modal-body>p").html("最多可以添加2个航班");
    //        blockUIOpenShare("dialogModal");
    //        return;
    //    }
    //    var html='<div class="form-group"><label class="control-label col-md-3" for="flight'+(ind)+''+(num+1)+'">航班信息'+(num+1)+'：</label><div class="col-md-3"><input type="text" placeholder="航班" autocomplete="off" name="flight'+(num+1)+'" id="flight'+(ind)+''+(num+1)+'" class="form-control"></div><div class="col-sm-2"> <button class="btn btn-block btn-danger flightremove" type="button">删除</button></div></div>';
    //
    //    $(this).parents("form").find('#flight'+m+''+(parseInt(num))).parents(".form-group").after(html);
    //    //autosearchAir($("#flight"+(ind)+(num+1)));
    //
    //})
    if(ind+1>1){
        ckeditor("desc"+(ind+1));
    }
    $("#form2"+(ind+1)).find("[type=submit]").click(function(){
        //需要手动更新CKEDITOR字段
        for ( instance in CKEDITOR.instances )
            CKEDITOR.instances[instance].updateElement();
        return true;
    });
    autosearchStratPlace($("#startPlace"+(ind+1)));
    autosearchRegion($("#destination"+(ind)+(1)));
    //autosearchAir($("#flight"+(ind)+(1)))
    $("#scenicListBtn"+(ind+1)).on("click",function(){
        var num=$(this).parents("form").find("input[id^='scenicList']").size();
        var m=parseInt($(this).attr("id").split("scenicListBtn")[1])-1;
        $(this).parents("form").find("input[id^='scenicList']").each(function(){
            num=parseInt($(this).attr("id").split("scenicList"+ind)[1]);
        })
        var that=$(this);
        //<label class="control-label col-md-3" for="scenicList'+(ind)+''+(num+1)+'">景点'+(num+1)+'：</label>\

        var addScenic=function(obj,num){
            var html='<div class="form-group">\
            <div class="col-md-3 col-md-offset-3">\
            <input type="text" placeholder="景点" id="scenicList'+(ind)+''+(num+1)+'" autocomplete="off" class="form-control">\
            <a href="javascript:;" class="scenicListmove"><i class="fa fa-fw fa-trash"></i></a>\
            </div>\
            </div>';
            obj.parents("form").find('#scenicList'+m+''+(parseInt(num))).parents(".form-group").after(html);
            autosearchSpot($('#scenicList'+(ind)+(num+1)));
            /*if(obj.parents("form").data('bootstrapValidator').getFieldElements("scenicList["+num+"].spotId")){
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
            *///17.1.11暂时去掉
            //if(destinationBtnArr[1].indexOf(num+1)<=-1){
            //    destinationBtnArr[1].push((num+1));
            //}
        }
        addScenic(that,num);

    })
    $("#ownExpenseListBtn"+(ind+1)).on("click",function(){
        var num=$(this).parents("form").find("input[id^='ownExpenseList']").size();
        var m=parseInt($(this).attr("id").split("ownExpenseListBtn")[1])-1;
        $(this).parents("form").find("input[id^='ownExpenseList']").each(function(){
            num=parseInt($(this).attr("id").split("ownExpenseList"+ind)[1]);
        })
        var that=$(this);
        //<label class="control-label col-md-3" for="ownExpenseList'+(ind)+''+(num+1)+'">自费场所'+(num+1)+'：</label>\
        var addOwn=function(obj,num){
            var html='<div class="form-group">\
            <div class="col-md-3 col-md-offset-3">\
            <input type="text" placeholder="自费场所"   id="ownExpenseList'+(ind)+''+(num+1)+'" autocomplete="off" class="form-control">\
            <a href="javascript:;" class="ownExpenseListmove"><i class="fa fa-fw fa-trash"></i></a>\
            </div>\
            </div>';
            obj.parents("form").find('#ownExpenseList'+m+''+(parseInt(num))).parents(".form-group").after(html);
            autosearchownExpense($('#ownExpenseList'+(ind)+(num+1)));
            /*if(obj.parents("form").data('bootstrapValidator').getFieldElements("scenicList["+num+"].spotId")){
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
             *///17.1.11暂时去掉
            //if(destinationBtnArr[1].indexOf(num+1)<=-1){
            //    destinationBtnArr[1].push((num+1));
            //}
        }
        addOwn(that,num);

    })
    $("#shoppingListBtn"+(ind+1)).on("click",function(){
        var num=$(this).parents("form").find("input[id^='shoppingList']").size();
        var m=parseInt($(this).attr("id").split("shoppingListBtn")[1])-1;
        $(this).parents("form").find("input[id^='shoppingList']").each(function(){
            num=parseInt($(this).attr("id").split("shoppingList"+ind)[1]);
        })
        var that=$(this);
        //<label class="control-label col-md-3" for="shoppingList'+(ind)+''+(num+1)+'">购物场所'+(num+1)+'：</label>\
        var addShopping=function(obj,num){
            var html='<div class="form-group">\
            <div class="col-md-3 col-md-offset-3">\
            <input type="text" placeholder="购物场所"   id="shoppingList'+(ind)+''+(num+1)+'" autocomplete="off" class="form-control">\
            <a href="javascript:;" class="shoppingListmove"><i class="fa fa-fw fa-trash"></i></a>\
            </div>\
            </div>';
            obj.parents("form").find('#shoppingList'+m+''+(parseInt(num))).parents(".form-group").after(html);
            autosearchShopping($('#shoppingList'+(ind)+(num+1)));

            /*if(obj.parents("form").data('bootstrapValidator').getFieldElements("scenicList["+num+"].spotId")){
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
             *///17.1.11暂时去掉
            //if(destinationBtnArr[1].indexOf(num+1)<=-1){
            //    destinationBtnArr[1].push((num+1));
            //}
        }
        addShopping(that,num);

    })
    autosearchSpot($('#scenicList'+(ind)+1));
    autosearchownExpense($('#ownExpenseList'+(ind)+1));
    autosearchShopping($('#shoppingList'+(ind)+1));
    //$('#schedulePhotosUrl'+(ind+1)).diyUpload({
    //    url: CONTEXTPATH + '/cloudFile/uploadCloudPhotos',
    //    success: function (data) {
    //
    //        var data = data;//$(data._raw).find("item").text();
    //        var len=[];
    //        $("#photoLibrary"+(ind+1)).find(":hidden").each(function () {
    //            if (!$.trim($(this).val())) {
    //                $(this).val(data);
    //                len.push(data)
    //                return false;
    //            }else{
    //                len.push($(this).val());
    //            }
    //        })
    //        if(len.length>=3){
    //            $('#schedulePhotosUrl'+(ind+1)).hide();
    //        }
    //
    //    },
    //    buttonText: '<button type="button" class="btn btn-primary pull-right btn-block btn-sm">上传行程图片</button>',
    //    error: function (err) {
    //        //console.info(err);
    //    },
    //    fileNumLimit: 100
    //});
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
            <label class="control-label col-sm-3" for="destination'+ind+''+1+'"><strong>*</strong>城市（目的地）：</label>\
           <div class="col-sm-9">\
            <div class="row">\
        <div class="col-sm-3">\
            <input type="text" id="startPlace'+(ind+1)+'" name="startPlace" class="form-control" autocomplete="off"  placeholder="请输入名称">\
            <input type="hidden" name="startPlaceId" value="">\
            </div>\
           <div class="col-sm-4" style="display: none;">\
            <div class="row"></div>\
        </div>\
        <div class="col-sm-4" style="display: none;">\
        <div class="row"></div>\
        </div>\
        <div class="col-sm-4" style="display: none;">\
        <div class="row"></div>\
        </div>\
        <div class="col-sm-4" style="display: none;">\
        <div class="row"></div>\
        </div>\
        <div class="col-sm-4" style="display: none;">\
        <div class="row"></div>\
        </div>\
            <div class="col-sm-3" style="margin-bottom:10px;">\
            <button class="btn btn-block btn-warning" id="destinationBtn'+(ind+1)+'" type="button">增加城市（目的地）</button>\
            </div>\
            </div>\
            </div>\
            </div>\
            <div class="form-group">\
            <label class="control-label col-md-3" for="desc"><strong>*</strong>行程概述：</label>\
        <div class="col-md-9">\
            <textarea placeholder="当日行程" rows="3" class="form-control" name="desc" id="desc'+(ind+1)+'"></textarea>\
            </div>\
            </div>\
            <div class="form-group">\
            <label class="control-label col-md-3" for="scenicList'+ind+''+1+'">景点：</label>\
        <div class="col-md-3">\
            <input type="text" placeholder="景点" id="scenicList'+ind+''+1+'"  autocomplete="off"  class="form-control">\
            </div>\
            </div>\
            <div class="form-group">\
                <div class="col-sm-3 col-sm-offset-3">\
                    <button class="btn btn-block btn-warning" id="scenicListBtn'+(ind+1)+'" type="button"><i class="fa fa-fw fa-plus"></i>增加景点</button>\
                </div>\
            </div>\
            <div class="form-group ">\
            <label class="control-label col-sm-3" for="scenicList">酒店：</label>\
            <div class="col-sm-6">\
            <div class="row">\
           <div class="control-label  col-sm-3" for="hotel" style="text-align:left;padding-bottom:5px;">\
        <input type="radio" name="hotelConfirm" value="1" checked />\
    酒店已确认\
        </div>\
        <div class="control-label col-sm-3" for="hotelInput"  style="text-align:left;padding-bottom:5px;">\
        <input type="radio" name="hotelConfirm" value="0">\
        酒店未确认\
            </div>\
            </div>\
            </div>\
            <div class="hotel col-sm-6 col-sm-offset-3">\
            <div class="row">\
        <div class="col-md-9">\
            <input type="text" placeholder="酒店名称" id="hotel'+(ind+1)+'" name="hotel" autocomplete="off"  class="form-control">\
            <input type="hidden"   name="hotelId"  value="" class="form-control">\
            </div>\
            <div class="col-md-3">\
            <label class="control-label" for="sameLevel"><input type="checkbox" name="sameLevel" value="0" id="sameLevel'+(ind+1)+'"/>同级酒店</label>\
            </div>\
            </div>\
            </div>\
            <div class="hotelInput col-sm-6 col-sm-offset-3" style="display:none;">\
            <div class="row">\
            <div class="col-md-12">\
            <input type="text" placeholder="酒店名称" autocomplete="off"  id="hotelInput'+(ind+1)+'" name="hotelInput" class="form-control" disabled>\
           <input type="hidden"   name="hotelId" value="" class="form-control">\
            </div>\
        </div>\
        </div>\
            </div>\
        <div class="form-group">\
            <label class="control-label col-md-3" for="flight1'+(ind)+'">航班信息：</label>\
        <div class="col-md-9">\
            <textarea placeholder="航班" rows="3" id="flight'+ind+''+1+'" value="" name="flight1" class="form-control"></textarea>\
            </div>\
            </div>\
            <div class="form-group">\
            <label class="control-label col-md-3" for="ownExpenseList'+ind+''+1+'">自费项目：</label>\
        <div class="col-md-3">\
            <input type="text" placeholder="自费项目" id="ownExpenseList'+ind+''+1+'" ownExpenseId=""  autocomplete="off"  class="form-control">\
            </div>\
            </div>\
           <div class="form-group">\
            <div class="col-sm-3 col-sm-offset-3">\
            <button class="btn btn-block btn-warning" id="ownExpenseListBtn'+(ind+1)+'" type="button"><i class="fa fa-fw fa-plus"></i>增加自费项目</button>\
            </div>\
            </div>\
 <div class="form-group">\
            <label class="control-label col-md-3" for="shoppingList'+ind+''+1+'">购物场所：</label>\
        <div class="col-md-3">\
            <input type="text" placeholder="购物场所" id="shoppingList'+ind+''+1+'" shoppingId=""  autocomplete="off"  class="form-control">\
            </div>\
            </div>\
            <div class="form-group">\
            <div class="col-sm-3 col-sm-offset-3">\
            <button class="btn btn-block btn-warning" id="shoppingListBtn'+(ind+1)+'" type="button"><i class="fa fa-fw fa-plus"></i>增加购物场所</button>\
            </div>\
            </div>\
            <div class="form-group">\
            <label class="control-label col-md-3" for="cateringInfo'+(ind+1)+'"><strong>*</strong>餐饮说明：</label>\
        <div class="col-md-9">\
            <textarea placeholder="餐饮说明" rows="3" id="cateringInfo'+(ind+1)+'" name="cateringInfo" class="form-control"></textarea>\
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
            <button class="btn btn-primary pull-right btn-block btn-sm scheduleDetailSave" type="submit">下一步</button>\
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
    scheduleDetailScenicEntitys[ind]=[];
    shoppingScheduleList[ind]=[];
    ownExpenseScheduleList[ind]=[];
    dotform(ind);
    mySwiper.update();

}

//<div class="form-group">\
//<label class="control-label  col-sm-3" for="name'+(ind+1)+'">导游姓名：</label>\
//<div class="col-sm-9 row">\
//<div class="col-sm-4" style="display:none">\
//<p class="form-control-static"></p>\
//<input type="hidden" placeholder="导游姓名" id="leadName'+(ind+1)+'" readonly class="form-control" name="name">\
//</div>\
//<div class="col-sm-2">\
//<button class="btn btn-primary pull-right btn-block btn-sm leaderSend" id="leaderSend2'+(ind+1)+'" type="button">搜索</button>\
//</div>\
//<div class="col-sm-2">\
//<button class="btn btn-primary btn-block btn-sm leaderadd" id="leaderAdd'+(ind+1)+'" type="button">添加</button>\
//</div>\
//<input type="hidden" name="leaderId" value=""/>\
//<input type="hidden" name="sex" value=""/>\
//</div>\
//</div>\
//<div class="form-group" style="display:none;">\
//<label class="control-label col-sm-3" for="leaderphone'+(ind+1)+'">导游电话：</label>\
//<div class="col-sm-6">\
//<p class="form-control-static"></p>\
//<input type="hidden" placeholder="联系电话" id="leaderphone'+(ind+1)+'" value="" readonly class="form-control" name="phone">\
//</div>\
//</div>\

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
                obj.parents("li").remove();
            },
            error:function(){
                obj.parents("li").remove();
            }
        });
        //$("body").find(":hidden").each(function () {
        //    if ($.trim($(this).val()) == that.attr("srcimg")) {
        //        $(this).val("");
        //        return false;
        //    }
        //})

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
function  autosearchStratPlace(obj) {
    $(".form-group").on("click",".closecitystart",function(){
        //obj.autocomplete( "enable");
        //obj.removeAttr("readonly");
        //obj.css({"border-radius": "0",width:"auto"});
        $(this).parent().find("[name=startPlace]").val("");
        $(this).parent().find("[name=startPlaceId]").val("");
        $(this).remove();
    })
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
                        data.push({
                            alias:" 自定义城市“"+obj.val()+"”",
                            areaid: "",
                            countryId: "",
                            country: ""
                        });
                        cacheRegion[term] = data;
                        response($.map(data, function (item) {
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
           if(ui.item) {
               $this = $(this);
               $(this).attr("areaid", ui.item.areaid);
               $(event.target).next().val(ui.item.areaid).change();
               setTimeout(function () {
                   $this.blur();
               }, 1);
               //obj.autocomplete( "disable" );
               //obj.attr("readonly","readonly");
               //obj.css({"border-radius": "3px",width:"auto"});
               //obj.parent().css({position:"relative",width:"auto"});
               if(obj.parent().find(".closecitystart").length<=0) {
                   obj.parent().append('<div class="closecitystart" style="border-radius:10px;border:1px solid #000;position:absolute;cursor:pointer;top:-5px;right:8px;color:red;background-color: #a5a5a5;"><i class="fa fa-fw fa-close"></i></div>');
               }
           }
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
        $(this).next().val("").change();
    });

    if( ms.data("ui-autocomplete")) {
        //ms.data("ui-autocomplete")._renderMenu=function( ul, items ) {
        //    var that = this;
        //
        //    //$.each( items, function( index, item ) {
        //    //    that._renderItemData( ul, item );
        //    //});
        //    //$( ul ).find( "li:odd" ).addClass( "odd" );
        //    $(ul).append("<li style='line-height:26px;padding:0 10px'>自定义城市“”</li>");
        //}
        ms.data("ui-autocomplete")._renderItem = function (ul, item) {
            if(item.value.indexOf("自定义城市")<0) {
                return $("<li class='ui-menu-item' style='line-height:26px;padding:0 10px;'></li>")
                    .attr("data-value", item.value)
                    .append("<div  style='display:inline-block;'>" + item.label + "</div>")
                    .append("<div  style='display:inline-block;padding-left:10px;color:#999;font-size:12px;'>" + item.country + "</div>")
                    .appendTo(ul);
            }else{
                return $("<li style='line-height:26px;padding:0 10px;color:#1fbad1;'onclick='cityLast(this,\""+obj[0].id+"\",2)'></li>")
                    .attr("data-value", item.value)
                    .append("<div  style='display:inline-block;'>" + item.label + "</div>")
                    .append("<div  style='display:inline-block;padding-left:10px;color:#999;font-size:12px;'>" + item.country + "</div>")
                    .appendTo(ul);
            }
        }
        //ms.data("ui-autocomplete")._resizeMenu= function() {
        //    //this.menu.element.outerWidth( 500 );
        //
        //    this.menu.element.append($("<li style='line-height:26px;padding:0 10px;color:#1fbad1;'>自定义城市“"+obj.val()+"”</li>"));
        //    this.menu.element.find("li:last").on("click",function(){
        //        obj.next().val("").change();
        //        obj.autocomplete( "close" );
        //        obj.autocomplete( "disable" );
        //        obj.attr("readonly","readonly");
        //        obj.css({"border-radius": "3px",width:"auto"});
        //        obj.parent().css({position:"relative",width:"auto"});
        //        obj.parent().append('<div class="closecity" style="border-radius:10px;border:1px solid #000;position:absolute;cursor:pointer;top:5px;right:-5px;color:red;background-color: #a5a5a5;"><i class="fa fa-fw fa-close"></i></div>');
        //    })
        //
        //}
    }

}
function cityLast(o,obj,m){

        $("#"+obj).next().val("").change();
        $("#"+obj).next().next().val("").change();
        $("#"+obj).next().next().next().val("").change();
        //$("#"+obj).autocomplete( "close" );
        //$("#"+obj).autocomplete( "disable" );
        //$("#"+obj).attr("readonly","readonly");
       // $("#"+obj).css({"border-radius": "3px",width:"auto"});
        //$("#"+obj).parent().css({position:"relative",width:"auto"});
    if(m==1) {
        if($("#" + obj).parent().find(".closecity").length<=0) {
            $("#" + obj).parent().append('<div class="closecity" style="border-radius:10px;border:1px solid #000;position:absolute;cursor:pointer;top:-5px;right:8px;color:red;background-color: #a5a5a5;"><i class="fa fa-fw fa-close"></i></div>');
        }
        }else if(m=2){
        if($("#" + obj).parent().find(".closecitystart").length<=0) {
            $("#" + obj).parent().append('<div class="closecitystart" style="border-radius:10px;border:1px solid #000;position:absolute;cursor:pointer;top:-5px;right:8px;color:red;background-color: #a5a5a5;"><i class="fa fa-fw fa-close"></i></div>');
        }
    }
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
                        data.push({
                            alias:" 自定义城市“"+obj.val()+"”",
                            areaid: "",
                            countryId: "",
                            country: ""
                        });
                        cacheRegion[term] = data;
                        response($.map(data, function (item) {
                            return {
                                value: item.alias,
                                areaid: item.areaid,
                                country: item.country,
                                countryId: item.countryId
                            }
                        }));
                    }
                });
            }
        },
        minLength: 1,
        autoFocus: false,
        select: function( event, ui ) {
            $(event.target).blur();
            if (ui.item.id != 0) {
                //append_place(ui.item.name, ui.item.id, ui.item.shop_type, ui.item.address_geo, ui.item.contact, ui.item.email, ui.item.web);
                //$("#shop_search").val('');
            }
            $this = $(this);
            $(this).attr("areaid",ui.item.areaid);
            $(event.target).next().val(ui.item.areaid).change();
            $(event.target).next().next().val(ui.item.country).change();
            $(event.target).next().next().next().val(ui.item.countryId).change();
            setTimeout(function () {
                $this.blur();
            }, 1);
            //obj.autocomplete( "disable" );
            //obj.attr("readonly","readonly");
            //obj.css({"border-radius": "3px",width:"auto"});
            //obj.parent().css({position:"relative",width:"auto"});
            if(obj.parent().find(".closecity").length<=0) {
                obj.parent().append('<div class="closecity" style="border-radius:10px;border:1px solid #000;position:absolute;cursor:pointer;top:-5px;right:8px;color:red;background-color: #a5a5a5;"><i class="fa fa-fw fa-close"></i></div>');
            }

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
        $(this).next().val("").change();
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
            if(item.value.indexOf("自定义城市")<0) {
                return $("<li class='ui-menu-item' style='line-height:26px;padding:0 10px'></li>")
                    .attr("data-value", item.value)
                    .append("<div  style='display:inline-block;'>" + item.label + "</div>")
                    .append("<div  style='display:inline-block;padding-left:10px;color:#999;font-size:12px;'>" + item.country + "</div>")
                    .appendTo(ul);
            }else{
                return $("<li style='line-height:26px;padding:0 10px;color:#1fbad1;'onclick='cityLast(this,\""+obj[0].id+"\",1)'></li>")
                    .attr("data-value", item.value)
                    .append("<div  style='display:inline-block;'>" + item.label + "</div>")
                    .append("<div  style='display:inline-block;padding-left:10px;color:#999;font-size:12px;'>" + item.country + "</div>")
                    .appendTo(ul);
            }
        }
        //ms.data("ui-autocomplete")._resizeMenu= function() {
        //    //this.menu.element.outerWidth( 500 );
        //
        //    this.menu.element.append($("<li style='line-height:26px;padding:0 10px;color:#1fbad1;'>自定义城市“"+obj.val()+"”</li>"));
        //    this.menu.element.find("li:last").on("click",function(){
        //        obj.next().val("").change();
        //        obj.next().next().val("").change();
        //        obj.next().next().next().val("").change();
        //        obj.autocomplete( "close" );
        //        obj.autocomplete( "disable" );
        //        obj.attr("readonly","readonly");
        //        obj.css({"border-radius": "3px",width:"auto"});
        //        obj.parent().css({position:"relative",width:"auto"});
        //        obj.parent().append('<div class="closecity" style="border-radius:10px;border:1px solid #000;position:absolute;cursor:pointer;top:5px;right:-5px;color:red;background-color: #a5a5a5;"><i class="fa fa-fw fa-close"></i></div>');
        //    })
        //    $(".closecity").on("click",function(){
        //        $(this).parent().remove();
        //    })
        //}
    }

}

function  autosearchsearRegion(obj) {
    var ms=obj.autocomplete({
        delay: 500,
        scrollHeight: 200,
        source: function(request, response ) {
            var term = request.term;
            //if ( term in cacheRegion ) {
            //    response( $.map( cacheRegion[ term ], function( item ) {
            //        return {
            //            value:item.alias,
            //            areaid:item.areaid,
            //            country : item.country,
            //            countryId :item.countryId
            //        }
            //    }));
            //    return;
            //}
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
            var searobj=obj.attr("id").split("sear")[1];
            $("#"+searobj).attr("areaid",ui.item.areaid);
            $("#"+searobj).next().val(ui.item.areaid).change();
            $("#"+searobj).next().next().val(ui.item.country).change();
            $("#"+searobj).next().next().next().val(ui.item.countryId).change();
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
        $(this).next().val("").change();
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
function  autosearchsearSpot(obj) {
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
            $(event.target).attr("areaid",ui.item.areaid);
            $(event.target).blur();
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
        $(this).next().val("").change();
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

    var ms=obj.autocomplete({
            delay: 500,
            scrollHeight: 200,
            source: function(request, response ) {
                var term = request.term;
                //缓存
                //if ( term in cache ) {
                //    response( $.map( cache[ term ], function( item ) {
                //        return {
                //            value: item.name,
                //            city:item.city,
                //            enName:item.enName,
                //            lat:item.lat,
                //            lng:item.lng,
                //            destionationId:item.destionationId,
                //            brief:item.brief,
                //            imageList:item.imageList,
                //            id:item.id
                //        }
                //    }));
                //    return;
                //}
                //var destinationArr=[];
                //obj.parents("form").find("[id^=destination],[id^=startPlace]").each(function(){
                //    if($(this).attr("id").indexOf("Btn")<0) {
                //        var that = $(this);
                //        if ($(this).attr("areaid")) {
                //            destinationArr.push($(this).attr("areaid"));
                //        } else {
                //            $.ajax({
                //                url: CONTEXTPATH + '/region/getRegion',
                //                dataType: "json",
                //                async: false,
                //                data: {
                //                    alias: $.trim($(this).val())
                //                },
                //                success: function (data) {
                //                    if (data.length > 1) {
                //                        for (var i = 0; i < data.length; i++) {
                //                            if (data[i].lv == 3) {
                //                                destinationArr.push(data[i].areaid);
                //                                that.attr("areaid", data[i].areaid);
                //                                return false;
                //                            }
                //                        }
                //                    } else {
                //                        destinationArr.push(data[0].areaid);
                //                        that.attr("areaid", data[0].areaid);
                //
                //                    }
                //                }
                //            });
                //        }
                //    }
                //})
                if($.trim(request.term).length>=1) {
                    $.ajax({
                        url: CONTEXTPATH + '/schedule/scenic/getScenic',
                        dataType: "json",
                        data: {
                            name: request.term,
                           // cities: destinationArr.join(",")
                        },
                        success: function (data) {
                            var data=data.scenicInfoList;
                            data.push({
                                name:" 创建自定义景点“"+obj.val()+"”",
                                destionationId: "",
                                enName: "",
                                city: "",
                                lat:"",
                                lng:"",
                                brief:""
                            });
                            cache[term] = data;
                            response($.map(data, function (item) {
                                return {
                                    value: item.name,
                                    city:item.city,
                                    enName:item.enName,
                                    lat:item.lat,
                                    lng:item.lng,
                                    destionationId:item.destionationId,
                                    brief:item.brief,
                                    imageList:item.imageList,
                                    id:item.id,
                                    mafengId:item.mafengId
                                }
                            }));
                        }
                    });
                }
        },
        minLength: 1,
        select: function( event, ui ) {
            var that=$(event.target);
            $(event.target).blur();


            //$(event.target).next().val(ui.item.mafengId).change();
            if(ui.item.lng&&ui.item.lat){
                _lngnow=Number(ui.item.lng);
                _latnow=Number(ui.item.lat);
            }else{
                _lngnow=Number(116);
                _latnow=Number(39);
            }
            var eventid=$(event.target).attr("id");
            var brief=ui.item.brief;
            var city=ui.item.city;
            var label=ui.item.label;
            var enName=ui.item.enName;
            var imageList=ui.item.imageList||[];
            var mafengId=ui.item.mafengId;
            var destionationId=ui.item.destionationId;
            var daynum=$(event.target).parents("form").attr("id").split("form2")[1];
            var scenicId=$(event.target).attr("scenicId");
            var boolHas=false;
            var imgHtml="";
            $("#spotName").html('<label class="control-label col-md-6"   id="spotname">'+label+'</label>').removeClass("has-success").removeClass("has-error");
            $("#brief").val(brief);
            $("#scenicCity").val(city);
            $("#scenicCity").attr("areaid",destionationId);
            $("#spotName").attr("daynum",daynum);
            $("#spotName").attr("eventid",eventid);
            $("#spotName").attr("editScenicId",ui.item.id);
            $("#spotName").attr("mafengId",ui.item.mafengId);
            $("#spotname").text(label);
            //$("#spotenname").text(enName);
            $('#scenicPhotosUrl1').show();
            photoScenic($('#scenicPhotosUrl1'),imageList);
            $.each(imageList, function(i, item){
                imgHtml+=' <li class="schedulePhotos2">\
                        <div class="viewThumb">\
                      <img src="'+item+'"/>\
                   </div>\
                   <div class="diyCancel"></div>\
                   <div title="删除" class="diySuccess" srcimg="'+item+'" style="display: block;"></div>\
                    <div class="diyBar" style="display: none;">\
                       <div class="diyProgress" style="width: 100%;"></div>\
                       <div class="diyProgressText">上传完成</div>\
                  </div>\
               </li>';
            });
            $("#scenicListImg").html(imgHtml);
            if(scenicId) {
                $("#spotName").attr("scenicId", scenicId);
            }else{
                $("#spotName").attr("scenicId", "");
            }
            var spot={};
            if(scenicId) {
                spot["id"] = scenicId;
            }
            spot["scenicName"]=label;
            spot["enName"]=enName;
            spot["scenicCity"]=city;
            spot["cityId"]=destionationId;
            spot["briefintroduction"]=brief;
            spot["lat"]=ui.item.lat;
            spot["lon"]=ui.item.lng;
            spot["mafengId"]=ui.item.mafengId;
            spot["imageList"]=ui.item.imageList;
            spot.code=$(event.target).attr("id");
            //if(scheduleDetailScenicEntitys.length>0){
            //$.each(scheduleDetailScenicEntitys, function(i, item){
            //    if(i==daynum-1){
            //        $.each(item, function(t,val){
            //            if(eventid&&val.code==eventid||val.id&&scenicId&&val.id==scenicId) {
            //                boolHas = true;
            //                scheduleDetailScenicEntitys[i][t]["scenicName"] = spot.scenicName;
            //                //spot["enName"]=$("#spotenname").text();
            //                scheduleDetailScenicEntitys[i][t]["scenicCity"] = spot.scenicCity;
            //                scheduleDetailScenicEntitys[i][t]["cityId"] = spot.cityId;
            //                scheduleDetailScenicEntitys[i][t]["briefintroduction"] = spot.briefintroduction;
            //                scheduleDetailScenicEntitys[i][t]["lat"] = spot.lat;
            //                scheduleDetailScenicEntitys[i][t]["lon"] = spot.lon;
            //                scheduleDetailScenicEntitys[i][t]["imageList"] = spot["imageList"];
            //                return false;
            //            }
            //        });
            //    }
            //});
            //}
            //if(!boolHas){
            //    scheduleDetailScenicEntitys[daynum-1].push(spot);
            //}
            //console.log(scheduleDetailScenicEntitys)
            $('#mapSpot').attr("src",CONTEXTPATH+"/mapsearch.html");
            autosearchsearSpot($("#scenicCity"));

            scenicremove();
            blockUIOpenShareNo("dialogModalspot");
            if($("#spotForm").data('bootstrapValidator').getFieldElements('spotname')) {
                $("#spotForm").data('bootstrapValidator').removeField("spotname");
            }
            $("#spotForm").data('bootstrapValidator').resetField("scenicCity");
            $(".returnCitySpot").off().on("click",function(){
                that.parents(".form-group:eq(0)").find("input[type=text]").val("").attr("scenicid","").show();
                that.parents(".form-group:eq(0)").find(".form-control-static").remove();
                blockUIClose();
            });
        }
    }).focus(function (event) {
        sourceObjscenic=event.target;
        $(this).autocomplete("search");
    });
    obj.on("input",function(event){
        sourceObjscenic=event.target;
        $(this).next().val("").change();
    });
    if( ms.data("ui-autocomplete")) {
        ms.data("ui-autocomplete")._renderItem = function (ul, item) {
            if (item.value.indexOf("创建自定义景点") < 0) {
                return $("<li class='ui-menu-item' style='line-height:26px;padding:0 10px' lng="+item.lng+" lat="+item.lat+"></li>")
                    .attr("data-value", item.value)
                    .append("<div  style='display:inline-block;'>" + item.label +"</div>")
                    .append("<div  style='display:inline-block;padding-left:10px;color:#999;font-size:12px;'>（" + item.city + "）</div>")
                    .appendTo(ul);
            } else {
                return $("<li style='line-height:26px;padding:0 10px;color:#1fbad1;'onclick='spotLast(\"" + obj[0].id + "\",1)'></li>")
                    .attr("data-value", item.value)
                    .append("<div  style='display:inline-block;'>" + item.label + "</div>")
                    .append("<div  style='display:inline-block;padding-left:10px;color:#999;font-size:12px;'>" + item.city + "</div>")
                    .appendTo(ul);
            }
        }
    }
}

function  autosearchownExpense(obj) {

    var ms=obj.autocomplete({
        delay: 500,
        scrollHeight: 200,
        source: function(request, response ) {
            var term = request.term;
            //缓存
            //if ( term in cache ) {
            //    response( $.map( cache[ term ], function( item ) {
            //        return {
            //            value: item.name,
            //            city:item.city,
            //            enName:item.enName,
            //            lat:item.lat,
            //            lng:item.lng,
            //            destionationId:item.destionationId,
            //            brief:item.brief,
            //            imageList:item.imageList,
            //            id:item.id
            //        }
            //    }));
            //    return;
            //}
            //var destinationArr=[];
            //obj.parents("form").find("[id^=destination],[id^=startPlace]").each(function(){
            //    if($(this).attr("id").indexOf("Btn")<0) {
            //        var that = $(this);
            //        if ($(this).attr("areaid")) {
            //            destinationArr.push($(this).attr("areaid"));
            //        } else {
            //            $.ajax({
            //                url: CONTEXTPATH + '/region/getRegion',
            //                dataType: "json",
            //                async: false,
            //                data: {
            //                    alias: $.trim($(this).val())
            //                },
            //                success: function (data) {
            //                    if (data.length > 1) {
            //                        for (var i = 0; i < data.length; i++) {
            //                            if (data[i].lv == 3) {
            //                                destinationArr.push(data[i].areaid);
            //                                that.attr("areaid", data[i].areaid);
            //                                return false;
            //                            }
            //                        }
            //                    } else {
            //                        destinationArr.push(data[0].areaid);
            //                        that.attr("areaid", data[0].areaid);
            //
            //                    }
            //                }
            //            });
            //        }
            //    }
            //})
            if($.trim(request.term).length>=1) {
                $.ajax({
                    url: CONTEXTPATH + '/ownexpenseagency/getOwnExpenseAgencyByName',
                    dataType: "json",
                    type:"POST",
                    data: {
                        itemName: request.term,
                        //cities: destinationArr.join(",")
                    },
                    success: function (data) {
                        var data=data.ownExpenseAgencyList;
                        data.push({
                            itemName:" 创建自定义自费项目“"+obj.val()+"”",
                            itemCity: "",
                            lat:"",
                            lng:"",
                            briefintroduction:""
                        });
                        cache[term] = data;
                        response($.map(data, function (item) {
                            return {
                                value: item.itemName,
                                itemCity:item.itemCity,
                                lat:item.lat,
                                lon:item.lon,
                                briefintroduction:item.briefintroduction,
                                photoUrls:item.photoUrls,
                                id:item.id,
                                limitNumber:item.limitNumber,
                                serviceItem:item.serviceItem,
                                referencePrice:item.referencePrice,
                            }
                        }));
                    }
                });
            }
        },
        minLength: 1,
        select: function( event, ui ) {
            var that=$(event.target);
            $(event.target).blur();

            //$(event.target).next().val(ui.item.mafengId).change();
            if(ui.item.lon&&ui.item.lat){
                _lngnow=Number(ui.item.lon);
                _latnow=Number(ui.item.lat);
            }else{
                _lngnow=Number(116);
                _latnow=Number(39);
            }
            var eventid=$(event.target).attr("id");
            var brief=ui.item.briefintroduction;
            var city=ui.item.itemCity;
            var label=ui.item.label;
            var limitNumber=ui.item.limitNumber;
            var serviceItem=ui.item.serviceItem;
            var referencePrice=ui.item.referencePrice;
            var photoUrls=ui.item.photoUrls||[];
            var daynum=$(event.target).parents("form").attr("id").split("form2")[1];
            var ownExpenseId=$(event.target).attr("ownExpenseId");
            var boolHas=false;
            var imgHtml="";
            $("#ownExpenseName").html('<label class="control-label col-md-6"  id="ownExpensename">'+label+'</label>').removeClass("has-success").removeClass("has-error");
            $("#ownbrief").val(brief);
            $("#ownExpenseCity").val(city);
            //$("#ownExpenseCity").attr("areaid",destionationId);
            $("#ownExpenseName").attr("daynum",daynum);
            $("#ownExpenseName").attr("eventid",eventid);
            $("#ownExpenseName").attr("editOwnId",ui.item.id);
            $("#limitNumber").val(limitNumber);
            $("#serviceItem").val(serviceItem);
            $("#referencePrice").val(referencePrice);
            $("#ownExpensename").text(label);
            photoScenic($('#ownExpensePhotosUrl1'),photoUrls);
            $.each(photoUrls, function(i, item){
                imgHtml+=' <li class="schedulePhotos2">\
                        <div class="viewThumb">\
                      <img src="'+item+'"/>\
                   </div>\
                   <div class="diyCancel"></div>\
                   <div title="删除" class="diySuccess" srcimg="'+item+'" style="display: block;"></div>\
                    <div class="diyBar" style="display: none;">\
                       <div class="diyProgress" style="width: 100%;"></div>\
                       <div class="diyProgressText">上传完成</div>\
                  </div>\
               </li>';
            });
            $("#ownExpenseListImg").html(imgHtml);
            if(ownExpenseId) {
                $("#ownExpenseName").attr("ownExpenseId", ownExpenseId);
            }else{
                $("#ownExpenseName").attr("ownExpenseId", "");
            }
            var ownExpense={};
            if(ownExpenseId) {
                ownExpense["id"] = ownExpenseId;
            }
            ownExpense["itemName"]=label;
            ownExpense["itemCity"]=city;
           // ownExpense["cityId"]=destionationId;
            ownExpense["limitNumber"]=limitNumber;
            ownExpense["serviceItem"]=serviceItem;
            ownExpense["referencePrice"]=referencePrice;
            ownExpense["briefintroduction"]=brief;
            ownExpense["lat"]=ui.item.lat;
            ownExpense["lon"]=ui.item.lon;
            ownExpense["photoUrls"]=ui.item.photoUrls;
            ownExpense.code=$(event.target).attr("id");
            //$.each(ownExpenseScheduleList, function(i, item){
            //    if(i==daynum-1){
            //        $.each(item, function(t,val){
            //            if(eventid&&val.code==eventid||val.id&&ownExpenseId&&val.id==ownExpenseId) {
            //                boolHas = true;
            //                ownExpenseScheduleList[i][t]["itemName"] = ownExpense.itemName;
            //                //spot["enName"]=$("#spotenname").text();
            //                ownExpenseScheduleList[i][t]["itemCity"] = ownExpense.itemCity;
            //                ownExpenseScheduleList[i][t]["cityId"] = ownExpense.cityId;
            //                ownExpenseScheduleList[i][t]["briefintroduction"] = ownExpense.briefintroduction;
            //                ownExpenseScheduleList[i][t]["lat"] = ownExpense.lat;
            //                ownExpenseScheduleList[i][t]["lon"] = ownExpense.lon;
            //                ownExpenseScheduleList[i][t]["limitNumber"] = ownExpense.limitNumber;
            //                ownExpenseScheduleList[i][t]["serviceItem"] = ownExpense.serviceItem;
            //                ownExpenseScheduleList[i][t]["referencePrice"] = ownExpense.referencePrice;
            //                ownExpenseScheduleList[i][t]["photoUrls"] = ownExpense["photoUrls"];
            //                return false;
            //            }
            //        });
            //    }
            //});
            //if(!boolHas){
            //    ownExpenseScheduleList[daynum-1].push(ownExpense);
            //}
            //console.log(ownExpenseScheduleList)
            $('#mapOwn').attr("src",CONTEXTPATH+"/mapsearch.html");
            autosearchsearSpot($("#ownExpenseCity"));

            ownExpenseremove();
            blockUIOpenShareNo("dialogModalownExpense");
            if($("#ownExpenseForm").data('bootstrapValidator').getFieldElements('ownExpensename')) {
                $("#ownExpenseForm").data('bootstrapValidator').removeField("ownExpensename");
            }
            $("#ownExpenseForm").data('bootstrapValidator').resetField("ownExpenseCity");
            $(".returnCityOwn").off().on("click",function(){
                that.parents(".form-group:eq(0)").find("input[type=text]").val("").attr("ownexpenseid","").show();
                that.parents(".form-group:eq(0)").find(".form-control-static").remove();
                blockUIClose();
            });
        }
    }).focus(function (event) {
        sourceObjownExpense=event.target;
        $(this).autocomplete("search");
    });
    obj.on("input",function(event){
        sourceObjownExpense=event.target;
        $(this).next().val("").change();
    });
    if( ms.data("ui-autocomplete")) {
        ms.data("ui-autocomplete")._renderItem = function (ul, item) {
            if (item.value.indexOf("创建自定义自费项目") < 0) {
                return $("<li class='ui-menu-item' style='line-height:26px;padding:0 10px' lng="+item.lng+" lat="+item.lat+"></li>")
                    .attr("data-value", item.value)
                    .append("<div  style='display:inline-block;'>" + item.label +"</div>")
                    .append("<div  style='display:inline-block;padding-left:10px;color:#999;font-size:12px;'>（" + item.itemCity + "）</div>")
                    .appendTo(ul);
            } else {
                return $("<li style='line-height:26px;padding:0 10px;color:#1fbad1;'onclick='ownExpenseLast(\"" + obj[0].id + "\",1)'></li>")
                    .attr("data-value", item.value)
                    .append("<div  style='display:inline-block;'>" + item.label + "</div>")
                    .append("<div  style='display:inline-block;padding-left:10px;color:#999;font-size:12px;'>" + item.itemCity + "</div>")
                    .appendTo(ul);
            }
        }
    }
}
function  autosearchShopping(obj) {

    var ms=obj.autocomplete({
        delay: 500,
        scrollHeight: 200,
        source: function(request, response ) {
            var term = request.term;
            //缓存
            //if ( term in cache ) {
            //    response( $.map( cache[ term ], function( item ) {
            //        return {
            //            value: item.name,
            //            city:item.city,
            //            enName:item.enName,
            //            lat:item.lat,
            //            lng:item.lng,
            //            destionationId:item.destionationId,
            //            brief:item.brief,
            //            imageList:item.imageList,
            //            id:item.id
            //        }
            //    }));
            //    return;
            //}
            //var destinationArr=[];
            //obj.parents("form").find("[id^=destination],[id^=startPlace]").each(function(){
            //    if($(this).attr("id").indexOf("Btn")<0) {
            //        var that = $(this);
            //        if ($(this).attr("areaid")) {
            //            destinationArr.push($(this).attr("areaid"));
            //        } else {
            //            $.ajax({
            //                url: CONTEXTPATH + '/region/getRegion',
            //                dataType: "json",
            //                async: false,
            //                data: {
            //                    alias: $.trim($(this).val())
            //                },
            //                success: function (data) {
            //                    if (data.length > 1) {
            //                        for (var i = 0; i < data.length; i++) {
            //                            if (data[i].lv == 3) {
            //                                destinationArr.push(data[i].areaid);
            //                                that.attr("areaid", data[i].areaid);
            //                                return false;
            //                            }
            //                        }
            //                    } else {
            //                        destinationArr.push(data[0].areaid);
            //                        that.attr("areaid", data[0].areaid);
            //
            //                    }
            //                }
            //            });
            //        }
            //    }
            //})
            if($.trim(request.term).length>=1) {
                $.ajax({
                    url: CONTEXTPATH + '/shoppingagency/getShoppingAgencyByName',
                    dataType: "json",
                    type:"POST",
                    data: {
                        hotelName: request.term
                       // cities: destinationArr.join(",")
                    },
                    success: function (data) {
                        var data=data.shoppingAgencyList;
                        data.push({
                            shoppingName:" 创建自定义购物场所“"+obj.val()+"”",
                            cityId: "",
                            shoppingCity: "",
                            lat:"",
                            lng:"",
                            residenceTime:"",
                            sellProducts:""
                        });
                        cache[term] = data;
                        response($.map(data, function (item) {
                            return {
                                value: item.shoppingName,
                                shoppingCity:item.shoppingCity,
                                sellProducts:item.sellProducts,
                                residenceTime:item.residenceTime,
                                lat:item.lat,
                                lng:item.lon,
                                cityId:item.cityId,
                                id:item.id
                            }
                        }));
                    }
                });
            }
        },
        minLength: 1,
        select: function( event, ui ) {
            var that=$(event.target);
            $(event.target).blur();
            //$(event.target).next().val(ui.item.mafengId).change();
            if(ui.item.lng&&ui.item.lat){
            _lngnow=Number(ui.item.lng);
            _latnow=Number(ui.item.lat);
            }else{
                _lngnow=Number(116);
                _latnow=Number(39);
            }
            var eventid=$(event.target).attr("id");
            var shoppingCity=ui.item.shoppingCity;
            var label=ui.item.label;
            var cityId=ui.item.cityId;
            var sellProducts=ui.item.sellProducts;
            var residenceTime=ui.item.residenceTime;
            var daynum=$(event.target).parents("form").attr("id").split("form2")[1];
            var shoppingId=$(event.target).attr("shoppingId");
            var boolHas=false;
            var imgHtml="";
            $("#shoppingName").html('<label class="control-label col-md-6"  id="shoppingname">'+label+'</label>').removeClass("has-success").removeClass("has-error");
            $("#shoppingCity").val(shoppingCity);
            $("#sellProducts").val(sellProducts);
            $("#residenceTime").val(residenceTime);
            $("#shoppingCity").attr("areaid",cityId);
            $("#shoppingName").attr("daynum",daynum);
            $("#shoppingName").attr("eventid",eventid);
            $("#shoppingName").attr("editShoppingId",ui.item.id);
            $("#shoppingname").text(label);

            if(shoppingId) {
                $("#shoppingName").attr("shoppingId", shoppingId);
            }else{
                $("#shoppingName").attr("shoppingId", "");
            }
            var shoppingobj={};
            if(shoppingId) {
                shoppingobj["id"] = shoppingId;
            }
            shoppingobj["shoppingName"]=label;
            shoppingobj["shoppingCity"]=shoppingCity;
            shoppingobj["cityId"]=cityId;
            shoppingobj["sellProducts"]=sellProducts;
            shoppingobj["residenceTime"]=residenceTime;
            shoppingobj["cityId"]=cityId;
            shoppingobj["lat"]=ui.item.lat;
            shoppingobj["lon"]=ui.item.lng;
            shoppingobj.code=$(event.target).attr("id");
            //$.each(shoppingScheduleList, function(i, item){
            //    if(i==daynum-1){
            //        $.each(item, function(t,val){
            //            if(eventid&&val.code==eventid||val.id&&shoppingId&&val.id==shoppingId) {
            //                boolHas = true;
            //                shoppingScheduleList[i][t]["scenicName"] = shoppingobj.shoppingName;
            //                shoppingScheduleList[i][t]["scenicCity"] = shoppingobj.shoppingCity;
            //                shoppingScheduleList[i][t]["cityId"] = shoppingobj.cityId;
            //                shoppingScheduleList[i][t]["sellProducts"] = shoppingobj.sellProducts;
            //                shoppingScheduleList[i][t]["residenceTime"] = shoppingobj.residenceTime;
            //                shoppingScheduleList[i][t]["lat"] = shoppingobj.lat;
            //                shoppingScheduleList[i][t]["lon"] = shoppingobj.lng;
            //                return false;
            //            }
            //        });
            //    }
            //});
            //if(!boolHas){
            //    shoppingScheduleList[daynum-1].push(shoppingobj);
            //}
            //console.log(shoppingScheduleList)
            $('#mapshopping').attr("src",CONTEXTPATH+"/mapsearch.html");
            autosearchsearSpot($("#shoppingCity"));

            shoppingremove();
            blockUIOpenShareNo("dialogModalshopping");
            if($("#shoppingForm").data('bootstrapValidator').getFieldElements('shoppingname')) {
                $("#shoppingForm").data('bootstrapValidator').removeField("shoppingname");
            }
            $("#shoppingForm").data('bootstrapValidator').resetField("shoppingCity");
            $(".returnCityShopping").off().on("click",function(){
                that.parents(".form-group:eq(0)").find("input[type=text]").val("").attr("shoppingid","").show();
                that.parents(".form-group:eq(0)").find(".form-control-static").remove();
                blockUIClose();
            });
        }
    }).focus(function (event) {
        sourceObjshopping=event.target;
        $(this).autocomplete("search");
    });
    obj.on("input",function(event){
        sourceObjshopping=event.target;
        $(this).next().val("").change();
    });
    if( ms.data("ui-autocomplete")) {
        ms.data("ui-autocomplete")._renderItem = function (ul, item) {
            if (item.value.indexOf("创建自定义购物场所") < 0) {
                return $("<li class='ui-menu-item' style='line-height:26px;padding:0 10px' lng="+item.lng+" lat="+item.lat+"></li>")
                    .attr("data-value", item.value)
                    .append("<div  style='display:inline-block;'>" + item.label +"</div>")
                    .append("<div  style='display:inline-block;padding-left:10px;color:#999;font-size:12px;'>（" + item.shoppingCity + "）</div>")
                    .appendTo(ul);
            } else {
                return $("<li style='line-height:26px;padding:0 10px;color:#1fbad1;'onclick='shoppingLast(\"" + obj[0].id + "\",1)'></li>")
                    .attr("data-value", item.value)
                    .append("<div  style='display:inline-block;'>" + item.label + "</div>")
                    .append("<div  style='display:inline-block;padding-left:10px;color:#999;font-size:12px;'>" + item.shoppingCity + "</div>")
                    .appendTo(ul);
            }
        }
    }
}
function  autosearchHotel(obj) {
    var ms=obj.autocomplete({
        delay: 500,
        scrollHeight: 200,
        source: function(request, response ) {
            var term = request.term;
            //if ( term in cacheHotel ) {
            //    response( $.map( cacheHotel[ term ], function( item ) {
            //        return {
            //            briefintroduction: item.briefintroduction,
            //            value: item.hotelName,
            //            hotelEnglishName:item.hotelEnglishName,
            //            lat:item.lat,
            //            lon:item.lon,
            //            starLv:item.starLv,
            //            city:item.city,
            //            photoUrls:item.photoUrls,
        //                id:item.id
            //        }
            //    }));
            //    return;
            //}
            //var hotelArr=[];
            //obj.parents("form").find("[id^=destination][type=text],[id^=startPlace][type=text]").each(function(i){
            //    if(i==parseInt(obj.parents("form").find("[id^=destination][type=text],[id^=startPlace][type=text]").length)-1) {
            //        var that = $(this);
            //        if ($(this).attr("areaid")) {
            //           // hotelArr.push($(this).attr("areaid"));
            //            hotelArr.push($(this).val());
            //        } else {
            //            $.ajax({
            //                url: CONTEXTPATH + '/region/getRegion',
            //                dataType: "json",
            //                async: false,
            //                data: {
            //                    alias: $.trim($(this).val())
            //                },
            //                success: function (data) {
            //                    if(data.length>1) {
            //                        for (var i = 0; i < data.length; i++) {
            //                            if (data[i].lv == 3) {
            //                                hotelArr.push(data[i].areaid);
            //                                that.attr("areaid", data[i].areaid)
            //                                return false;
            //                            }
            //                        }
            //                    }else{
            //                        if(data[0].areaid) {
            //                            hotelArr.push(data[0].areaid);
            //                            that.attr("areaid", data[0].areaid)
            //                        }
            //                    }
            //                }
            //            });
            //        }
            //    }
            //})
            if($.trim(request.term).length>=1) {
                $.ajax({
                    url: CONTEXTPATH + '/hotelagency/getHotelAgencyByName',
                    dataType: "json",
                    type:"POST",
                    data: {
                        hotelName:$.trim( request.term )
                        //cities: hotelArr.join(",")
                    },
                    success: function (data) {
                        var data=data.hotelAgencyEntityList;
                        data.push({
                            hotelName:" 创建自定义酒店“"+obj.val()+"”",
                            hotelEnglishName: "",
                            city: "",
                            lat:"",
                            lon:"",
                            starLv:"",
                            briefintroduction:"",
                            cityId:""
                        });
                        cacheHotel[term] = data;
                        response($.map(data, function (item) {
                            return {
                                value: item.hotelName,
                                hotelEnglishName:item.hotelEnglishName,
                                lat:item.lat,
                                lon:item.lon,
                                starLv:item.starLv,
                                city:item.city,
                                briefintroduction: item.briefintroduction,
                                photoUrls:item.photoUrls,
                                id:item.id,
                                cityId:item.cityId,
                                hotelId:item.hotelId
                            }
                        }));
                    }
                });
            }
        },
        minLength: 1,
        select: function( event, ui ) {
           // $(event.target).next().val(ui.item.hotelId);
            var that=$(event.target);
            $(event.target).blur();
            if(ui.item.lon&&ui.item.lat) {
                _lngnow = Number(ui.item.lon);
                _latnow = Number(ui.item.lat);
            }else{
                _lngnow=Number(116);
                _latnow=Number(39);
            }
            var eventid=$(event.target).attr("id");
            var brief=ui.item.briefintroduction;
            var city=ui.item.city;
            var label=ui.item.value;
            var id=ui.item.id;
            //var enName=ui.item.hotelEnglishName;
            var cityId=ui.item.cityId;
            var daynum=$(event.target).parents("form").attr("id").split("form2")[1];
            var hotelId=$(event.target).attr("hotelId");
            var photoUrls=ui.item.photoUrls||[];
            var hotelId=ui.item.hotelId;
            var imgHtml="";
            $("#hotellayName").html('<label class="control-label col-md-6"  id="hotelname_lay">'+label+'</label>').removeClass("has-success").removeClass("has-error");
            //var starLv=ui.item.starLv;
            //starbg(starLv);
            $("#hotelPhotosUrl1").show();
            photoScenic($("#hotelPhotosUrl1"),photoUrls);
            $.each(photoUrls, function(i, item){
                if(item&&item!="null"&&item!=null) {
                    imgHtml += ' <li class="schedulePhotos2">\
                        <div class="viewThumb">\
                      <img src="' + item + '"/>\
                   </div>\
                   <div class="diyCancel"></div>\
                   <div title="删除" class="diySuccess" srcimg="' + item + '" style="display: block;"></div>\
                    <div class="diyBar" style="display: none;">\
                       <div class="diyProgress" style="width: 100%;"></div>\
                       <div class="diyProgressText">上传完成</div>\
                  </div>\
               </li>';
                }
            });
            $("#hotelListImg").html(imgHtml);
            $("#briefHotel").val(brief);
            $("#hotelCity").val(city);
            $("#hotelCity").attr("areaid",cityId);
            $("#hotellayName").attr("daynum",daynum);
            $("#hotellayName").attr("eventid",eventid);
            $("#hotellayName").attr("hotelIdnew",hotelId);
            $("#hotelname_lay").text(label);
           // $("#hotelenname_lay").text(enName);
            if(id) {
                $("#hotellayName").attr("hotelId", id);
            }else{
                $("#hotellayName").attr("hotelId", "");
            }
            var hotelConfirm=$(event.target).parents("form").find("input[name=hotelConfirm]:checked").val();
            var hotelobj={}
            hotelobj["id"]=hotelId;
            hotelobj["hotelName"]=label;
            hotelobj["sameLevel"]="";
            hotelobj["hotelConfirm"]=hotelConfirm;
            hotelobj["hotelInput"]="";
            hotelobj["briefintroduction"]=brief;
            hotelobj["city"]=city;
            hotelobj["lon"]=ui.item.lon;
            hotelobj["lat"]=ui.item.lat;
            hotelobj["photoUrls"]=photoUrls;
            hotelobj["hotelId"]=hotelId;
            //if(hotelConfirm==1){
            //   var  sameLevel=$(event.target).parents("form").find("input[name=sameLevel]").val();
            //    if($(event.target).parents("form").find("input[name=sameLevel]").is(":checked")){
            //        sameLevel=1;
            //    }else{
            //        sameLevel=0;
            //    }
            //    hotelobj["hotelConfirm"]=1;
            //    hotelobj["sameLevel"]=sameLevel;
            //}else{
            //    hotelobj["hotelConfirm"]=0;
            //    hotelobj["hotelInput"]=$(event.target).parents("form").find("input[name=hotelInput]").val();
            //}
            //if(hotels.length>0) {
            //    $.each(hotels, function (i, item) {
            //        if (i == daynum - 1) {
            //            if(hotelId) {
            //                hotels[i]["id"] = hotelId;
            //            }
            //            hotels[i]["hotelName"] = hotelobj["hotelName"];
            //            hotels[i]["briefintroduction"] = hotelobj["briefintroduction"];
            //            hotels[i]["city"] = hotelobj["city"];
            //            hotels[i]["lon"] = hotelobj["lon"];
            //            hotels[i]["lat"] = hotelobj["lat"];
            //            hotels[i]["photoUrls"] = hotelobj["photoUrls"];
            //            hotels[i]["hotelInput"] = hotelobj["hotelInput"];
            //            hotels[i]["sameLevel"] = hotelobj["sameLevel"];
            //            hotels[i]["hotelConfirm"] = hotelobj["hotelConfirm"];
            //
            //        }
            //    });
            //}else{
            //    hotels[daynum - 1]={}
            //    if(hotelId) {
            //        hotels[daynum - 1]["id"] = hotelId;
            //    }
            //    hotels[daynum - 1]["hotelName"] = hotelobj["hotelName"];
            //    hotels[daynum - 1]["briefintroduction"] = hotelobj["briefintroduction"];
            //    hotels[daynum - 1]["city"] = hotelobj["city"];
            //    hotels[daynum - 1]["lon"] = hotelobj["lon"];
            //    hotels[daynum - 1]["lat"] = hotelobj["lat"];
            //    hotels[daynum - 1]["photoUrls"] = hotelobj["photoUrls"];
            //    hotels[daynum - 1]["hotelInput"] = hotelobj["hotelInput"];
            //    hotels[daynum - 1]["sameLevel"] = hotelobj["sameLevel"];
            //    hotels[daynum - 1]["hotelConfirm"] = hotelobj["hotelConfirm"];
            //}
            $('#mapHotel').attr("src",CONTEXTPATH+"/mapsearch.html");
            autosearchsearSpot($("#hotelCity"));
            hotelremove()
            blockUIOpenShareNo("dialogModalhotel");
            //$('#hotelForm').bootstrapValidator('addField', "hotel_names",{
            //    validators: {
            //        notEmpty: {
            //            message: '酒店名字不能为空'
            //        }
            //    }
            //});
            if($("#hotelForm").data('bootstrapValidator').getFieldElements('hotel_names')) {
                $("#hotelForm").data('bootstrapValidator').removeField("hotel_names");
            }
            $("#hotelForm").data('bootstrapValidator').resetField("hotelCity");
            $(".returnCityHotel").off().on("click",function(){
                that.parents(".form-group:eq(0)").find("input[type=text]").val("");
                that.parents(".form-group:eq(0)").find("input[name=hotelId]").val("");
                blockUIClose();
            })
        },
        close: function( event, ui ) {
           // $(event.target).next().val(ui.item.hotelId)
        }
    }).focus(function (event) {
        $(this).autocomplete("search");
    });
    obj.on( "autocompleteclose", function( event, ui ) {
        $(event.target).val()
    });
    if( ms.data("ui-autocomplete")) {
        ms.data("ui-autocomplete")._renderItem = function (ul, item) {
            if (item.value.indexOf("创建自定义酒店") < 0) {
                return $("<li class='ui-menu-item' style='line-height:26px;padding:0 10px' lng="+item.lon+" lat="+item.lat+"></li>")
                    .attr("data-value", item.value)
                    .append("<div  style='display:inline-block;'>" + item.label + "</div>")
                    .append("<div  style='display:inline-block;padding-left:10px;color:#999;font-size:12px;'>（" + item.city + "）</div>")
                    .appendTo(ul);
            } else {
                return $("<li style='line-height:26px;padding:0 10px;color:#1fbad1;'onclick='hotelLast(\"" + obj[0].id + "\")'></li>")
                    .attr("data-value", item.value)
                    .append("<div  style='display:inline-block;'>" + item.label + "</div>")
                    .append("<div  style='display:inline-block;padding-left:10px;color:#999;font-size:12px;'>" + item.city + "</div>")
                    .appendTo(ul);
            }
        }
    }
}
function starbg(ind){
    $(".scorerigger ul li").find("img").attr("src",CONTEXTPATH+"/resources/dist/images/star.png");
    $(".scorerigger ul li").eq(ind-1).prevAll().find("img").attr("src",CONTEXTPATH+"/resources/dist/images/star-bg.png");
    $(".scorerigger ul li").eq(ind-1).find("img").attr("src",CONTEXTPATH+"/resources/dist/images/star-bg.png");
    $(".scorerigger ul li").find("[type=radio]").attr("checked",false);
    $(".scorerigger ul li").eq(ind-1).prevAll().find("[type=radio]").attr("checked",false);
    $(".scorerigger ul li").eq(ind-1).find("[type=radio]").attr('checked',true);
}
////getAirportData
//function  autosearchAir(obj) {
//    obj.autocomplete({
//        delay: 500,
//        scrollHeight: 200,
//        source: function(request, response ) {
//            var term = request.term;
//            if ( term in cacheHotel ) {
//                response( $.map( cacheHotel[ term ], function( item ) {
//                    return {
//                        value:item.fightNo+"   "+item.fromName+"起飞到"+item.toName+"（"+item.takeoffTime+"--"+item.arrivelTime+"）",
//                        fromCode:item.fromCode
//                    }
//                }));
//                return;
//            }
//            if($.trim(request.term).length>=1) {
//                $.ajax({
//                    url: CONTEXTPATH + '/getAirportData',
//                    dataType: "json",
//                    data: {
//                        query: $.trim(request.term),
//                    },
//                    success: function (data) {
//                        cacheHotel[term] = data;
//                        response($.map(data, function (item) {
//                            return {
//                                value: item.fightNo + "   " + item.fromName + "起飞到" + item.toName + "（" + item.takeoffTime + "--" + item.arrivelTime + "）",
//                                fromCode: item.fromCode
//                            }
//                        }));
//                    }
//                });
//            }
//        },
//        _renderItem: function( ul, items ) {
//            var that = this;
//            $.each( items, function( index, item ) {
//                that._renderItemData( ul, item );
//            });
//            $( ul ).find( "li:odd" ).addClass( "odd" );
//        },
//        minLength: 1,
//        select: function( event, ui ) {
//        }
//    })
//
//}

//工具栏为自定义类型
function ckeditor(id) {
    CKEDITOR.replace(id,
        {
            toolbar: [
                //加粗     斜体，     下划线      穿过线      下标字        上标字
                ['Bold', 'Italic', 'Underline', 'Strike', 'Subscript', 'Superscript'],
                // 数字列表          实体列表            减小缩进    增大缩进
                ['NumberedList', 'BulletedList', '-', 'Outdent', 'Indent'],
                //左对 齐             居中对齐          右对齐          两端对齐
                ['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock'],
                //超链接  取消超链接 锚点
                ['Link', 'Unlink', 'Anchor'],
                //图片    flash    表格       水平线            表情       特殊字符        分页符
                ['Image', 'Table', 'HorizontalRule', 'Smiley', 'SpecialChar', 'PageBreak'],
                '/',
                // 样式       格式      字体    字体大小
                ['Styles', 'Format', 'Font', 'FontSize'],
                //文本颜色     背景颜色
                ['TextColor', 'BGColor'],
                //全屏           显示区块
                ['Maximize', 'ShowBlocks', '-']
            ]
        }
    ).on( 'change', function( event ) {
            var data = this.getData();//内容
            CKEDITOR.instances[id].updateElement();
            $("#"+id).change()
            return true;
        });
}

function spotLast(objid){
    $("#"+objid).blur();
    scenicremove();
    var imageList=[];
    $(sourceObjscenic).blur();
    $('#scenicPhotosUrl1').show();
    photoScenic($('#scenicPhotosUrl1'),imageList);
    var daynum=$(sourceObjscenic).parents("form").attr("id").split("form2")[1];
    var eventid=$(sourceObjscenic).attr("id");
    $("#brief").val("");
    $("#scenicCity").val("");
    $("#scenicCity").attr("areaid","");
    $("#spotName").attr("daynum",daynum);
    $("#spotName").attr("eventid",eventid);
    $("#spotName").html('<label class="control-label col-md-3"><strong>*</strong>景点名称：</label><div class=" col-md-6"><input type="text" placeholder="景点名称" id="spotname" name="spotname" class="form-control"></div>');
    $("#spotName").removeAttr("editScenicId");
    $("#scenicListImg").empty();
    _lngnow=Number(116);
    _latnow=Number(39);
    $('#mapSpot').attr("src",CONTEXTPATH+"/mapsearch.html");
    autosearchsearSpot($("#scenicCity"));
    blockUIOpenShareNo("dialogModalspot");
    $('#spotForm').bootstrapValidator('addField', "spotname",{
        validators: {
            notEmpty: {
                message: '景点名称不能为空'
            }
        }
    });
    $("#spotForm").data('bootstrapValidator').resetField("scenicCity");
    $(".returnCitySpot").off().on("click",function(){
        $(sourceObjscenic).parents(".form-group:eq(0)").find("input[type=text]").val("").attr("scenicid","").show();
        //that.parents(".form-group:eq(0)").find(".form-control-static").remove();
        blockUIClose();
    });
}
function scenicremove(){
    $("#spotForm").find(".addicon").hide();
    $("#spotForm").find(".photoicon").hide();
    $("#spotForm").find(".positionicon").hide();
    $("#spotForm").find(".jsicon").hide();
    $("#spotForm").find(".addarea").hide();
    $("#removeSpotpos").parents(".box:eq(0)").show();
    $("#removeSpotji").parents(".box:eq(0)").show();
    $("#removeSpotp").parents(".box:eq(0)").show();
}
function hotelremove(){
    $("#hotelForm").find(".addicon").hide();
    $("#hotelForm").find(".photoicon").hide();
    $("#hotelForm").find(".positionicon").hide();
    $("#hotelForm").find(".jsicon").hide();
    $("#hotelForm").find(".addarea").hide();
    $("#removehotelpos").parents(".box:eq(0)").show();
    $("#removeHotelji").parents(".box:eq(0)").show();
    $("#removeHotelhotel").parents(".box:eq(0)").show();
    $("#removeHotelstar").parents(".box:eq(0)").show();
}
function shoppingremove(){
    $("#shoppingForm").find(".addicon").hide();
    $("#shoppingForm").find(".tingicon").hide();
    $("#shoppingForm").find(".positionicon").hide();
    $("#shoppingForm").find(".saleicon").hide();
    $("#shoppingForm").find(".addarea").hide();
    $("#removeSellProducts").parents(".box:eq(0)").show();
    $("#removeResidenceTime").parents(".box:eq(0)").show();
    $("#removeSoppingpos").parents(".box:eq(0)").show();
}
function ownExpenseremove(){
    $("#ownExpenseForm").find(".addicon").hide();
    $("#ownExpenseForm").find(".photoicon").hide();
    $("#ownExpenseForm").find(".positionicon").hide();
    $("#ownExpenseForm").find(".jsicon").hide();
    $("#ownExpenseForm").find(".limiticon").hide();
    $("#ownExpenseForm").find(".priceicon").hide();
    $("#ownExpenseForm").find(".servericon").hide();
    $("#ownExpenseForm").find(".addarea").hide();
    $("#removeOwnp").parents(".box:eq(0)").show();
    $("#removeownExpenseji").parents(".box:eq(0)").show();
    $("#removeownlimitNumber").parents(".box:eq(0)").show();
    $("#removeownserviceItem").parents(".box:eq(0)").show();
    $("#removeownreferencePrice").parents(".box:eq(0)").show();
    $("#removeOwnpos").parents(".box:eq(0)").show();
}
function hotelLast(objid){
    $("#"+objid).blur();
    $("#hotellayName").empty().append('<label class="control-label col-md-3"><strong>*</strong>酒店名称：</label><div class=" col-md-6"><input type="text" placeholder="酒店名称" id="hotelname_lay" name="hotel_names" class="form-control"/></div>');
    _lngnow=Number(116);
    _latnow=Number(39);
    //var starLv=1;
    //starbg(starLv);
    var photoUrls=[];
    $("#hotelPhotosUrl1").show();
    photoScenic($("#hotelPhotosUrl1"),photoUrls);
    $("#hotelCity").val("");
    $("#briefHotel").val("");
    $("#hotelname_lay").val("");
    $("#hotellayName").attr("hotelidnew","")
    $('#mapHotel').attr("src",CONTEXTPATH+"/mapsearch.html");
    $("#hotelListImg").empty();
    autosearchsearSpot($("#hotelCity"));
    hotelremove();
    blockUIOpenShareNo("dialogModalhotel");
    $('#hotelForm').bootstrapValidator('addField', "hotel_names",{
        validators: {
            notEmpty: {
                message: '酒店名字不能为空'
            }
        }
    });
    $("#hotelForm").data('bootstrapValidator').resetField("hotelCity");
    $(".returnCityHotel").off().on("click",function(){
        blockUIClose();
    });
}
function shoppingLast(objid){
    $("#"+objid).blur();
    $(sourceObjshopping).blur();
    $("#shoppingName").html('<label class="control-label col-md-3"><strong>*</strong>购物场所名称：</label><div class=" col-md-6"><input type="text" placeholder="购物场所名称" id="shoppingname" name="shoppingname" class="form-control"></div>');
    _lngnow=Number(116);
    _latnow=Number(39);
    var daynum=$(sourceObjshopping).parents("form").attr("id").split("form2")[1];
    var eventid=$(sourceObjshopping).attr("id");
    $("#shoppingName").attr("daynum",daynum);
    $("#shoppingName").attr("eventid",eventid);
    $("#shoppingName").removeAttr("editShoppingId");
    $('#mapshopping').attr("src",CONTEXTPATH+"/mapsearch.html");
    autosearchsearSpot($("#shoppingCity"));
    $("#sellProducts").val("");
    $("#residenceTime").val("");
    $("#shoppingCity").val("");
    $("#shoppingCity").attr("areaid","");
    shoppingremove();
    blockUIOpenShareNo("dialogModalshopping");
    $('#shoppingForm').bootstrapValidator('addField', "shoppingname",{
        validators: {
            notEmpty: {
                message: '购物场所名字不能为空'
            }
        }
    });
    $("#shoppingForm").data('bootstrapValidator').resetField("shoppingCity");
    $(".returnCityShopping").off().on("click",function(){
        $(sourceObjshopping).parents(".form-group:eq(0)").find("input[type=text]").val("").attr("shoppingid","").show();
      //  $(sourceObjshopping).parents(".form-group:eq(0)").find(".form-control-static").remove();
        blockUIClose();
    });
}
function ownExpenseLast(objid){
    $("#"+objid).blur();
    $(sourceObjownExpense).blur();
    $("#ownExpenseName").html('<label class="control-label col-md-3"><strong>*</strong>自费项目名称：</label><div class=" col-md-6"><input type="text" placeholder="自费项目名称" id="ownExpensename" name="ownExpensename" class="form-control"></div>');
    _lngnow=Number(116);
    _latnow=Number(39);
    var photoUrls=[];
    $("#ownExpensePhotosUrl1").show();
    photoScenic($("#ownExpensePhotosUrl1"),photoUrls);
    $("#ownExpenseListImg").empty();
    var daynum=$(sourceObjownExpense).parents("form").attr("id").split("form2")[1];
    var eventid=$(sourceObjownExpense).attr("id");
    $("#ownbrief").val("");
    $("#ownExpenseCity").val("");
    $("#ownExpenseName").removeAttr("editownid");
    $("#limitNumber").val("");
    $("#serviceItem").val("");
    $("#referencePrice").val("");
    $("#ownExpenseCity").attr("areaid","");
    $("#ownExpenseName").attr("daynum",daynum);
    $("#ownExpenseName").attr("eventid",eventid);
    $('#mapOwn').attr("src",CONTEXTPATH+"/mapsearch.html");
    autosearchsearSpot($("#ownExpenseCity"));
    ownExpenseremove();
    blockUIOpenShareNo("dialogModalownExpense");
    $('#ownExpenseForm').bootstrapValidator('addField', "ownExpensename",{
        validators: {
            notEmpty: {
                message: '自费项目名称不能为空'
            }
        }
    });
    $("#ownExpenseForm").data('bootstrapValidator').resetField("ownExpenseCity");
    $(".returnCityOwn").off().on("click",function(){
        $(sourceObjownExpense).parents(".form-group:eq(0)").find("input[type=text]").val("").attr("ownexpenseid","").show();
        //$(sourceObjownExpense).parents(".form-group:eq(0)").find(".form-control-static").remove();
        blockUIClose();
    });
}
function hotelUpdate(form){

    var hotelId=$("#hotellayName").attr("hotelId");
    var hotelIdnew=$("#hotellayName").attr("hotelIdnew");
    var daynum=$("#hotellayName").attr("daynum");
    var hotelobj={};
    if(hotelId) {
        hotelobj["id"] = hotelId;
    }
    if($("#hotelname_lay").attr("type")){
        hotelobj["hotelName"]=$("#hotelname_lay").val();
    }else{
        hotelobj["hotelName"]=$("#hotelname_lay").text();
    }
    var starLv="";
    var photoUrls=[];
    //$("#hotelForm").find(".pingfen ul li").find("[name=scoreForSpots]").each(function(){
    //    if($(this).attr("checked")=="checked"){
    //        starLv=$(this).val();
    //    }
    //})
    $("#hotelListImg").find(".diySuccess").each(function(){
        photoUrls.push($(this).attr("srcimg"));
    })
    //hotelobj["starLv"]=starLv;
    //spot["enName"]=$("#spotenname").text();
    hotelobj["city"]=$("#hotelCity").val();
    hotelobj["cityId"]=$("#hotelCity").attr("areaid");
    if($("#removeHotelji").parents(".box:eq(0)").is(":visible")) {
        hotelobj["briefintroduction"] = $("#briefHotel").val();
    }else{
        hotelobj["briefintroduction"] = "";
    }
    if($("#removehotelpos").parents(".box:eq(0)").is(":visible")) {
        hotelobj["lat"] = window.document.getElementById('mapHotel').contentWindow.placenow.lat();
        hotelobj["lon"] = window.document.getElementById('mapHotel').contentWindow.placenow.lng();
    }else{
        hotelobj["lat"] = "";
        hotelobj["lon"] = "";
    }
    if($("#removeHotelhotel").parents(".box:eq(0)").is(":visible")) {
        hotelobj["photoUrls"] = photoUrls;
    }else{
        hotelobj["photoUrls"] = [];
    }
    hotelobj["hotelId"] = hotelIdnew;
    $.ajax({
        type:'POST',
        url:CONTEXTPATH+"/hotelagency/saveHotelAgency",
        contentType: 'application/json;charset=utf-8',
        context: $('body'),
        data: JSON.stringify(hotelobj),
        dataType : "json",
        cache : false,
        success :  function (data) {
            if(data.retCode == 0) {
                $(".overlays").hide();
                form.bootstrapValidator('disableSubmitButtons', false);
                var eventid = $("#hotellayName").attr("eventid");
                //$("#"+eventid).attr("scenicid",data.scenicAgencyEntity.id);
                //scenicid
                //hotel[$!{Detail.dayNum}-1].push({id:"$Detail.hotelSchedule.id",hotelName:"$Detail.hotelSchedule.hotelName",sameLevel:"$Detail.hotelSchedule.sameLevel",hotelConfirm:"$Detail.hotelSchedule.hotelConfirm",hotelInput:"$Detail.hotelSchedule.hotelInput",briefintroduction:"$Detail.hotelSchedule.briefintroduction",lon:"$Detail.hotelSchedule.lon",lat:"$Detail.hotelSchedule.lat",photoUrls:$Detail.hotelSchedule.photoUrls})
                if (hotels[daynum - 1]) {
                    $.each(hotels, function (i, item) {
                        if (i == daynum - 1) {
                            if ($("#" + eventid).parents("form").find("input[name=hotelId]").val()) {
                                hotels[i]["id"] = $("#" + eventid).parents("form").find("input[name=hotelId]").val();
                            }
                            //hotels[i]["hotelName"]=hotelobj["hotelName"];
                            hotels[i]["briefintroduction"] = hotelobj["briefintroduction"];
                            hotels[i]["city"] = hotelobj["city"];
                            hotels[i]["lon"] = hotelobj["lon"];
                            hotels[i]["lat"] = hotelobj["lat"];
                            hotels[i]["photoUrls"] = hotelobj["photoUrls"];
                            //hotels[i]["hotelInput"]=photoUrls;
                            //hotels[i]["sameLevel"]= sameLevel;
                            //hotels[i]["hotelConfirm"]=hotelConfirm;
                            var hotelConfirm = $("#" + eventid).parents("form").find("input[name=hotelConfirm]:checked").val();
                            if (hotelConfirm == 1) {
                                var sameLevel = $("#" + eventid).parents("form").find("input[name=sameLevel]").val();
                                if ($("#" + eventid).parents("form").find("input[name=sameLevel]").is(":checked")) {
                                    sameLevel = 1;
                                } else {
                                    sameLevel = 0;
                                }
                                hotels[i]["hotelConfirm"] = 1;
                                hotels[i]["hotelName"] = $("#" + eventid).parents("form").find("input[name=hotel]").val();
                                hotels[i]["sameLevel"] = sameLevel;
                            } else {
                                hotels[i]["hotelConfirm"] = 0;
                                hotels[i]["hotelInput"] = $("#" + eventid).parents("form").find("input[name=hotelInput]").val();
                            }
                        }
                    });
                } else if (!hotels[daynum - 1]) {
                    hotels[daynum - 1] = {};
                    if ($("#" + eventid).parents("form").find("input[name=hotelId]").val()) {
                        hotels[daynum - 1]["id"] = $("#" + eventid).parents("form").find("input[name=hotelId]").val();
                    }
                    //hotels[i]["hotelName"]=hotelobj["hotelName"];
                    hotels[daynum - 1]["briefintroduction"] = hotelobj["briefintroduction"];
                    hotels[daynum - 1]["city"] = hotelobj["city"];
                    hotels[daynum - 1]["lon"] = hotelobj["lon"];
                    hotels[daynum - 1]["lat"] = hotelobj["lat"];
                    hotels[daynum - 1]["photoUrls"] = hotelobj["photoUrls"];
                    //hotels[i]["hotelInput"]=photoUrls;
                    //hotels[i]["sameLevel"]= sameLevel;
                    //hotels[i]["hotelConfirm"]=hotelConfirm;
                    var hotelConfirm = $("#" + eventid).parents("form").find("input[name=hotelConfirm]:checked").val();
                    if (hotelConfirm == 1) {
                        var sameLevel = $("#" + eventid).parents("form").find("input[name=sameLevel]").val();
                        if ($("#" + eventid).parents("form").find("input[name=sameLevel]").is(":checked")) {
                            sameLevel = 1;
                        } else {
                            sameLevel = 0;
                        }
                        hotels[daynum - 1]["hotelConfirm"] = 1;
                        hotels[daynum - 1]["hotelName"] = $("#" + eventid).parents("form").find("input[name=hotel]").val();
                        hotels[daynum - 1]["sameLevel"] = sameLevel;
                    } else {
                        hotels[daynum - 1]["hotelConfirm"] = 0;
                        hotels[daynum - 1]["hotelInput"] = $("#" + eventid).parents("form").find("input[name=hotelInput]").val();
                    }
                }
                //console.log(hotels)
            }else{
                form.bootstrapValidator('disableSubmitButtons', false);
                $(".overlays").hide();
            }
        },
        error: function () {
            form.next(".overlays").hide();
            $("#dialogModal").find(".modal-body>p").html("错误，请重试。");
            blockUIOpenShare("dialogModal");
        }
    })

    blockUIClose();

}
function spotUpdate(form){
    var scenicId=$("#spotName").attr("scenicId");
    var daynum=$("#spotName").attr("daynum");
    var eventid= $("#spotName").attr("eventid");
    var spot={};
    if(scenicId) {
        spot["id"] = scenicId;
    }
    if($("#spotname").attr("type")){
        spot["scenicName"]=$("#spotname").val();
    }else{
        spot["scenicName"]=$("#spotname").text();
    }
    var imageList=[];
    //spot["enName"]=$("#spotenname").text();
    spot["scenicCity"]=$("#scenicCity").val();
    spot["cityId"]=$("#scenicCity").attr("areaid");
    spot["mafengId"]=$("#spotName").attr("mafengId");

    spot["id"]=$("#spotName").attr("editScenicId");
    if($("#removeSpotji").parents(".box:eq(0)").is(":visible")) {
        spot["briefintroduction"] = $("#brief").val();
    }else{
        spot["briefintroduction"] = "";
    }
    if($("#removeSpotpos").parents(".box:eq(0)").is(":visible")) {
        spot["lat"] = window.document.getElementById('mapSpot').contentWindow.placenow.lat();
        spot["lon"] = window.document.getElementById('mapSpot').contentWindow.placenow.lng();
    }else{
        spot["lat"] = "";
        spot["lon"] = "";
    }
    $("#scenicListImg").find(".diySuccess").each(function(){
        imageList.push($(this).attr("srcimg"));
    });
    if($("#removeSpotp").parents(".box:eq(0)").is(":visible")) {
        spot["imageList"] = imageList;
    }else{
        spot["imageList"] = [];
    }
    spot.code=$("#"+eventid).attr("id");
    $("#"+eventid).parents(".form-group:eq(0)").find(".scenicListmove").remove();
    if($("#"+eventid).parents(".form-group:eq(0)").find(".form-control-static").length<=0) {
        $("#"+eventid).before('<p class="form-control-static">' + spot["scenicName"] + '</p>');
    }
    $("#"+eventid).hide();

    if($("#"+eventid).parents(".form-group:eq(0)").find(".scenicListedit").length<=0) {
        if($("#"+eventid).parents(".form-group:eq(0)").find(".scenicListmoveStart").length<=0&&eventid.charAt(eventid.length - 1)==1) {
            $("#"+eventid).parent().find("p").append('<a href="javascript:;" class="scenicListedit"><i class="fa fa-fw fa-edit"></i></a> <a href="javascript:;" class="scenicListmoveStart"><i class="fa fa-fw fa-trash"></i></a>');

        }else if($("#"+eventid).parents(".form-group:eq(0)").find(".scenicListmove").length<=0&&eventid.charAt(eventid.length - 1)!=1) {
            $("#"+eventid).parent().find("p").append('<a href="javascript:;" class="scenicListedit"><i class="fa fa-fw fa-edit"></i></a> <a href="javascript:;" class="scenicListmove"><i class="fa fa-fw fa-trash"></i></a>');

        }
    }
    $.ajax({
        type:'POST',
        url:CONTEXTPATH+"/schedule/scenic/saveScheduleDetailScenic",
        contentType: 'application/json;charset=utf-8',
        context: $('body'),
        data: JSON.stringify(spot),
        dataType : "json",
        cache : false,
        success :  function (data) {
            if(data.retCode == 0) {
                $(".overlays").hide();
                form.bootstrapValidator('disableSubmitButtons', false);
                // $("#"+eventid).attr("scenicid",data.scenicAgencyEntity.id);
                //scenicid
                var boolHas = false;
                $.each(scheduleDetailScenicEntitys, function (i, item) {
                    if (i == daynum - 1) {
                        $.each(item, function (t, val) {
                            if (val.code && $("#" + eventid).attr("id") == val.code || val.id && scenicId && val.id == scenicId) {
                                boolHas = true;
                                scheduleDetailScenicEntitys[i][t]["scenicName"] = spot["scenicName"];
                                //spot["enName"]=$("#spotenname").text();
                                scheduleDetailScenicEntitys[i][t]["scenicCity"] = spot["scenicCity"];
                                scheduleDetailScenicEntitys[i][t]["cityId"] = spot["cityId"];
                                scheduleDetailScenicEntitys[i][t]["briefintroduction"] = spot["briefintroduction"];
                                scheduleDetailScenicEntitys[i][t]["lat"] = spot["lat"];
                                scheduleDetailScenicEntitys[i][t]["lon"] = spot["lon"];
                                scheduleDetailScenicEntitys[i][t]["lon"] = spot["code"];
                                scheduleDetailScenicEntitys[i][t]["imageList"] = spot["imageList"];
                                return false;
                            }
                        });
                    }
                });
                if (!boolHas) {
                    scheduleDetailScenicEntitys[daynum - 1].push(spot);
                }
                //console.log(scheduleDetailScenicEntitys)
            }else{
                    form.bootstrapValidator('disableSubmitButtons', false);
                    $(".overlays").hide();
            }
        },
        error: function () {
            form.next(".overlays").hide();
            $("#dialogModal").find(".modal-body>p").html("错误，请重试。");
            blockUIOpenShare("dialogModal");
        }
    })
    blockUIClose();
}
function shoppingUpdate(form){
    var shoppingId=$("#shoppingName").attr("shoppingId");
    var daynum=$("#shoppingName").attr("daynum");
    var eventid= $("#shoppingName").attr("eventid");
    var shopping={};
    if(shoppingId) {
        shopping["id"] = shoppingId;
    }
    if($("#shoppingname").attr("type")){
        shopping["shoppingName"]=$("#shoppingname").val();
    }else{
        shopping["shoppingName"]=$("#shoppingname").text();
    }
    shopping["shoppingCity"]=$("#shoppingCity").val();
    shopping["cityId"]=$("#shoppingCity").attr("areaid");

    shopping["id"]=$("#shoppingName").attr("editShoppingId");
    if($("#removeSellProducts").parents(".box:eq(0)").is(":visible")) {
        shopping["sellProducts"] = $("#sellProducts").val();
    }else{
        shopping["sellProducts"] = "";
    }
    if($("#removeResidenceTime").parents(".box:eq(0)").is(":visible")) {
        shopping["residenceTime"] = $("#residenceTime").val();
    }else{
        shopping["residenceTime"] = "";
    }
    if($("#removeSoppingpos").parents(".box:eq(0)").is(":visible")) {
        shopping["lat"] = window.document.getElementById('mapshopping').contentWindow.placenow.lat();
        shopping["lon"] = window.document.getElementById('mapshopping').contentWindow.placenow.lng();
    }else{
        shopping["lat"] = "";
        shopping["lon"] = "";
    }
    shopping["code"]=$("#"+eventid).attr("id");
    if (eventid != "shoppingInfo") {
        $("#" + eventid).parents(".form-group:eq(0)").find(".shoppingListmove").remove();
        if ($("#" + eventid).parents(".form-group:eq(0)").find(".form-control-static").length <= 0) {
            $("#" + eventid).before('<p class="form-control-static">' + shopping["shoppingName"] + '</p>');
        }
        $("#" + eventid).hide();

        if ($("#" + eventid).parents(".form-group:eq(0)").find(".shoppingListedit").length <= 0) {
            if ($("#" + eventid).parents(".form-group:eq(0)").find(".shoppingListmoveStart").length <= 0 && eventid.charAt(eventid.length - 1) == 1) {
                $("#" + eventid).parent().find("p").append(' <a href="javascript:;" class="shoppingListedit"><i class="fa fa-fw fa-edit"></i></a><a href="javascript:;" class="shoppingListmoveStart"><i class="fa fa-fw fa-trash"></i></a>');

            } else if ($("#" + eventid).parents(".form-group:eq(0)").find(".shoppingListmove").length <= 0 && eventid.charAt(eventid.length - 1) != 1) {
                $("#" + eventid).parent().find("p").append('<a href="javascript:;" class="shoppingListedit"><i class="fa fa-fw fa-edit"></i></a><a href="javascript:;" class="shoppingListmove"><i class="fa fa-fw fa-trash"></i></a>');

            }
        }
    }
    $.ajax({
        type:'POST',
        url:CONTEXTPATH+"/shoppingagency/saveShoppingAgency",
        contentType: 'application/json;charset=utf-8',
        context: $('body'),
        data: JSON.stringify(shopping),
        dataType : "json",
        cache : false,
        success :  function (data) {
            if(data.retCode==0) {
                $(".overlays").hide();
                form.bootstrapValidator('disableSubmitButtons', false);
                var mid = data.shoppingAgencyEntity.id;
                if (eventid != "shoppingInfo") {
                    dayshopping(eventid, shoppingId, shopping, daynum, mid);
                } else {
                    daymoreshopping(eventid, shoppingId, shopping, mid);
                }
                // console.log(shoppingScheduleList)
            }else{
                form.bootstrapValidator('disableSubmitButtons', false);
                $(".overlays").hide();
            }
        },
        error: function () {
            form.next(".overlays").hide();
            $("#dialogModal").find(".modal-body>p").html("错误，请重试。");
            blockUIOpenShare("dialogModal");
        }
    })
    blockUIClose();
}
function daymoreshopping(eveid,id,obj,m){
    $("#"+eveid).attr("shoppingId",m);
    //shoppingId
    var boolHas=false;
    $.each(shoppingMoreList, function(i, item){
                if(item.id&&id&&item.id==id){
                    boolHas=true;
                    shoppingMoreList[i]["shoppingName"]=obj["shoppingName"];
                    shoppingMoreList[i]["shoppingCity"]=obj["shoppingCity"];
                    shoppingMoreList[i]["cityId"]=obj["cityId"];
                    shoppingMoreList[i]["sellProducts"]=obj["sellProducts"];
                    shoppingMoreList[i]["residenceTime"]=obj["residenceTime"];
                    shoppingMoreList[i]["lat"]=obj["lat"];
                    shoppingMoreList[i]["lon"]=obj["lon"];
                    shoppingMoreList[i]["id"]=obj["id"];
                    shoppingMoreList[i]["code"]=obj["code"];
                    return false;
                }
    });
    if(!boolHas){
        shoppingMoreList.push(obj);
    }

    shoppingTable(shoppingScheduleList,$("#shoppingTable"));
    $("#addShopping").parents(".form-group").show();
    $("#addShopping").parents(".form-group").prev().hide();
}
function dayshopping(eveid,id,obj,day,m){

    $("#"+eveid).attr("shoppingId",m);
    //shoppingId
    var boolHas=false;
    $.each(shoppingScheduleList, function(i, item){
        if(i==day-1){
            $.each(item, function(t,val){
                if(val.code&&$("#"+eveid).attr("id")==val.code||val.id&&id&&val.id==id){
                    boolHas=true;
                    shoppingScheduleList[i][t]["shoppingName"]=obj["shoppingName"];
                    shoppingScheduleList[i][t]["shoppingCity"]=obj["shoppingCity"];
                    shoppingScheduleList[i][t]["cityId"]=obj["cityId"];
                    shoppingScheduleList[i][t]["sellProducts"]=obj["sellProducts"];;
                    shoppingScheduleList[i][t]["residenceTime"]=obj["residenceTime"];
                    shoppingScheduleList[i][t]["lat"]=obj["lat"];
                    shoppingScheduleList[i][t]["lon"]=obj["lon"];
                    shoppingScheduleList[i][t]["code"]=obj["code"];
                    return false;
                }
            });
        }
    });
    if(!boolHas){
        shoppingScheduleList[day-1].push(obj);
    }
}
function ownExpenseUpdate(form){
    var ownExpenseId=$("#ownExpenseName").attr("ownExpenseId");
    var daynum=$("#ownExpenseName").attr("daynum");
    var eventid= $("#ownExpenseName").attr("eventid");
    var ownExpense={};
    if(ownExpenseId) {
        ownExpense["id"] = ownExpenseId;
    }
    if($("#ownExpensename").attr("type")){
        ownExpense["itemName"]=$("#ownExpensename").val();
    }else{
        ownExpense["itemName"]=$("#ownExpensename").text();
    }
    var photoUrls=[];
    //spot["enName"]=$("#spotenname").text();
    ownExpense["itemCity"]=$("#ownExpenseCity").val();
    //ownExpense["cityId"]=$("#ownExpenseCity").attr("areaid");

    ownExpense["id"]=$("#ownExpenseName").attr("editOwnId");
    if($("#removeownExpenseji").parents(".box:eq(0)").is(":visible")) {
        ownExpense["briefintroduction"] = $("#ownbrief").val();
    }else{
        ownExpense["briefintroduction"] = "";
    }
    if($("#removeownlimitNumber").parents(".box:eq(0)").is(":visible")) {
        ownExpense["limitNumber"] = $("#limitNumber").val();
    }else{
        ownExpense["limitNumber"] ="";
    }
    if($("#removeownserviceItem").parents(".box:eq(0)").is(":visible")) {
        ownExpense["serviceItem"] = $("#serviceItem").val();
    }else{
        ownExpense["serviceItem"] ="";
    }
    if($("#removeownreferencePrice").parents(".box:eq(0)").is(":visible")) {
        ownExpense["referencePrice"] = $("#referencePrice").val();
    }else{
        ownExpense["referencePrice"] = "";
    }
    if($("#removeOwnpos").parents(".box:eq(0)").is(":visible")) {
        ownExpense["lat"] = window.document.getElementById('mapOwn').contentWindow.placenow.lat();
        ownExpense["lon"] = window.document.getElementById('mapOwn').contentWindow.placenow.lng();
    }else{
        ownExpense["lat"]="";
        ownExpense["lon"]=""
    }
    $("#ownExpenseListImg").find(".diySuccess").each(function(){
        photoUrls.push($(this).attr("srcimg"));
    })
    if($("#removeOwnp").parents(".box:eq(0)").is(":visible")) {
        ownExpense["photoUrls"] = photoUrls;
    }else{
        ownExpense["photoUrls"] =[];
    }
    ownExpense["code"]=$("#"+eventid).attr("id");
    if(eventid!="freeInfo") {
        $("#" + eventid).parents(".form-group:eq(0)").find(".ownExpenseListmove").remove();
        if ($("#" + eventid).parents(".form-group:eq(0)").find(".form-control-static").length <= 0) {
            $("#" + eventid).before('<p class="form-control-static">' + ownExpense["itemName"] + '</p>');
        }
        $("#" + eventid).hide();
        if ($("#" + eventid).parents(".form-group:eq(0)").find(".ownExpenseListedit").length <= 0) {
            if ($("#" + eventid).parents(".form-group:eq(0)").find(".ownExpenseListmoveStart").length <= 0 && eventid.charAt(eventid.length - 1) == 1) {
                $("#" + eventid).parent().find("p").append('<a href="javascript:;" class="ownExpenseListedit"><i class="fa fa-fw fa-edit"></i></a><a href="javascript:;" class="ownExpenseListmoveStart"><i class="fa fa-fw fa-trash"></i></a>');

            } else if ($("#" + eventid).parents(".form-group:eq(0)").find(".ownExpenseListmove").length <= 0 && eventid.charAt(eventid.length - 1) != 1) {
                $("#" + eventid).parent().find("p").append('<a href="javascript:;" class="ownExpenseListedit"><i class="fa fa-fw fa-edit"></i></a><a href="javascript:;" class="ownExpenseListmove"><i class="fa fa-fw fa-trash"></i></a>');

            }
        }
    }
    $.ajax({
        type:'POST',
        url:CONTEXTPATH+"/ownexpenseagency/saveOwnExpenseAgency",
        contentType: 'application/json;charset=utf-8',
        context: $('body'),
        data: JSON.stringify(ownExpense),
        dataType : "json",
        cache : false,
        success :  function (data) {
            if(data.retCode==0) {
                $(".overlays").hide();
                form.bootstrapValidator('disableSubmitButtons', false);
                var mid = data.ownExpenseAgencyEntity.id;
                if (eventid != "freeInfo") {
                    dayOwnexp(eventid, ownExpenseId, ownExpense, daynum, mid, photoUrls);
                } else {
                    dayMoreOwnexp(eventid, ownExpenseId, ownExpense, mid, photoUrls);
                }
            }else{
                form.bootstrapValidator('disableSubmitButtons', false);
                $(".overlays").hide();
            }
        },
        error: function () {
            form.next(".overlays").hide();
            $("#dialogModal").find(".modal-body>p").html("错误，请重试。");
            blockUIOpenShare("dialogModal");
        }
    })
    blockUIClose();
}
function dayMoreOwnexp(eveid,id,obj,m,photo){
    $("#"+eveid).attr("ownExpenseId",m);
    //scenicid
    var boolHas=false;
    $.each(ownExpenseMoreList, function(i, item){
                if(item.id&&id&&item.id==id){
                    boolHas=true;
                    ownExpenseMoreList[i]["itemName"]=obj["itemName"];
                    ownExpenseMoreList[i]["itemCity"]=obj["itemCity"];
                    //ownExpenseMoreList[i]["cityId"]=obj["cityId"];
                    ownExpenseMoreList[i]["briefintroduction"]=obj["briefintroduction"];
                    ownExpenseMoreList[i]["limitNumber"]=obj["limitNumber"];
                    ownExpenseMoreList[i]["serviceItem"]= obj["serviceItem"];
                    ownExpenseMoreList[i]["referencePrice"]=obj["referencePrice"];
                    ownExpenseMoreList[i]["lat"]= obj["lat"];
                    ownExpenseMoreList[i]["lon"]= obj["lon"];
                    ownExpenseMoreList[i]["photoUrls"]=obj["photoUrls"];
                    ownExpenseMoreList[i]["id"]=obj["id"];
                    ownExpenseMoreList[i]["code"]=obj["code"];
                    return false;
                }
    });
    if(!boolHas){
        ownExpenseMoreList.push(obj);
    }
    ownTable(ownExpenseScheduleList,$("#freeTable"));
    $("#addFree").parents(".form-group").show();
    $("#addFree").parents(".form-group").prev().hide();
}
function dayOwnexp(eveid,id,obj,day,m,photo){

    $("#"+eveid).attr("ownExpenseId",m);
    //scenicid
    var boolHas=false;
    $.each(ownExpenseScheduleList, function(i, item){
        if(i==day-1){
            $.each(item, function(t,val){
                if(val.code&&$("#"+eveid).attr("id")==val.code||val.id&&id&&val.id==id){
                    boolHas=true;
                    ownExpenseScheduleList[i][t]["itemName"]=obj["itemName"];
                    ownExpenseScheduleList[i][t]["itemCity"]=obj["itemCity"];
                    //ownExpenseScheduleList[i][t]["cityId"]=obj["cityId"];
                    ownExpenseScheduleList[i][t]["briefintroduction"]=obj["briefintroduction"];
                    ownExpenseScheduleList[i][t]["limitNumber"]=obj["limitNumber"];
                    ownExpenseScheduleList[i][t]["serviceItem"]=obj["serviceItem"];
                    ownExpenseScheduleList[i][t]["referencePrice"]=obj["referencePrice"];
                    ownExpenseScheduleList[i][t]["lat"]=obj["lat"];
                    ownExpenseScheduleList[i][t]["lon"]=obj["lon"];
                    ownExpenseScheduleList[i][t]["photoUrls"]=obj["photoUrls"];
                    ownExpenseScheduleList[i][t]["code"]=obj["code"];
                    return false;
                }
            });
        }
    });
    if(!boolHas){
        ownExpenseScheduleList[day-1].push(obj);
    }
    //console.log(ownExpenseScheduleList)
}
function spotremovelay(){
    $("#removeSpotpos").on("click",function(){
        $(this).parents(".box:eq(0)").hide();
        $("#spotForm").find(".addicon").show();
        $("#spotForm").find(".positionicon").show();
    })
    $("#removeSpotp").on("click",function(){
        $(this).parents(".box:eq(0)").hide();
        $("#spotForm").find(".addicon").show();
        $("#spotForm").find(".photoicon").show()
    })
    $("#removeSpotji").on("click",function(){
        $(this).parents(".box:eq(0)").hide();
        $("#spotForm").find(".addicon").show();
        $("#spotForm").find(".jsicon").show();
    })
    $("#spotForm").find(".addicon").on("click",function(){
        $("#spotForm").find(".addarea").show();
    });
    $("#spotForm").find(".photoicon").on("click",function(){
        $("#removeSpotp").parents(".box:eq(0)").show();
        $(this).hide();
        $("#spotForm").find(".addarea").hide();
        $("#spotForm").find(".addicon").hide();
        $("#spotForm").find(".box").each(function(){
            if(!$(this).is(":visible")){
                $("#spotForm").find(".addarea").show();
                $("#spotForm").find(".addicon").show();
            }
        })
    })
    $("#spotForm").find(".jsicon").on("click",function(){
        $("#removeSpotji").parents(".box:eq(0)").show();
        $(this).hide();
        $("#spotForm").find(".addarea").hide();
        $("#spotForm").find(".addicon").hide();
        $("#spotForm").find(".box").each(function(){
            if(!$(this).is(":visible")){
                $("#spotForm").find(".addarea").show();
                $("#spotForm").find(".addicon").show();
            }
        })
    })
    $("#spotForm").find(".positionicon").on("click",function(){
        $("#removeSpotpos").parents(".box:eq(0)").show();
        $(this).hide();
        $("#spotForm").find(".addarea").hide();
        $("#spotForm").find(".addicon").hide();
        $("#spotForm").find(".box").each(function(){
            if(!$(this).is(":visible")){
                $("#spotForm").find(".addarea").show();
                $("#spotForm").find(".addicon").show();
            }
        })
    })
}
function shoppingremovelay(){
    $("#removeSellProducts").on("click",function(){
        $(this).parents(".box:eq(0)").hide();
        $("#shoppingForm").find(".addicon").show();
        $("#shoppingForm").find(".saleicon").show();
    })
    $("#removeResidenceTime").on("click",function(){
        $(this).parents(".box:eq(0)").hide();
        $("#shoppingForm").find(".addicon").show();
        $("#shoppingForm").find(".tingicon").show()
    })
    $("#removeSoppingpos").on("click",function(){
        $(this).parents(".box:eq(0)").hide();
        $("#shoppingForm").find(".addicon").show();
        $("#shoppingForm").find(".positionicon").show();
    })
    $("#shoppingForm").find(".addicon").on("click",function(){
        $("#shoppingForm").find(".addarea").show();
    });
    $("#shoppingForm").find(".saleicon").on("click",function(){
        $("#removeSellProducts").parents(".box:eq(0)").show();
        $(this).hide();
        $("#shoppingForm").find(".addarea").hide();
        $("#shoppingForm").find(".addicon").hide();
        $("#shoppingForm").find(".box").each(function(){
            if(!$(this).is(":visible")){
                $("#shoppingForm").find(".addarea").show();
                $("#shoppingForm").find(".addicon").show();
            }
        })
    })
    $("#shoppingForm").find(".tingicon").on("click",function(){
        $("#removeResidenceTime").parents(".box:eq(0)").show();
        $(this).hide();
        $("#shoppingForm").find(".addarea").hide();
        $("#shoppingForm").find(".addicon").hide();
        $("#shoppingForm").find(".box").each(function(){
            if(!$(this).is(":visible")){
                $("#shoppingForm").find(".addarea").show();
                $("#shoppingForm").find(".addicon").show();
            }
        })
    })
    $("#shoppingForm").find(".positionicon").on("click",function(){
        $("#removeSoppingpos").parents(".box:eq(0)").show();
        $(this).hide();
        $("#shoppingForm").find(".addarea").hide();
        $("#shoppingForm").find(".addicon").hide();
        $("#shoppingForm").find(".box").each(function(){
            if(!$(this).is(":visible")){
                $("#shoppingForm").find(".addarea").show();
                $("#shoppingForm").find(".addicon").show();
            }
        })
    })
}
function hotelremovelay(){
    $("#removeHotelstar").on("click",function(){
        $(this).parents(".box:eq(0)").hide();
        $("#hotelForm").find(".addicon").show();
        $("#hotelForm").find(".staricon").show();
    })
    $("#removehotelpos").on("click",function(){
        $(this).parents(".box:eq(0)").hide();
        $("#hotelForm").find(".addicon").show();
        $("#hotelForm").find(".positionicon").show();
    })
    $("#removeHotelhotel").on("click",function(){
        $(this).parents(".box:eq(0)").hide();
        $("#hotelForm").find(".addicon").show();
        $("#hotelForm").find(".photoicon").show()
    })
    $("#removeHotelji").on("click",function(){
        $(this).parents(".box:eq(0)").hide();
        $("#hotelForm").find(".addicon").show();
        $("#hotelForm").find(".jsicon").show();
    })
    $("#hotelForm").find(".addicon").on("click",function(){
        $("#hotelForm").find(".addarea").show();
    });
    $("#hotelForm").find(".staricon").on("click",function(){
        $("#removeHotelstar").parents(".box:eq(0)").show();
        $(this).hide();
        $("#hotelForm").find(".addarea").hide();
        $("#hotelForm").find(".addicon").hide();
        $("#hotelForm").find(".box").each(function(){
            if(!$(this).is(":visible")){
                $("#hotelForm").find(".addarea").show();
                $("#hotelForm").find(".addicon").show();
            }
        })
    })
    $("#hotelForm").find(".photoicon").on("click",function(){
        $("#removeHotelhotel").parents(".box:eq(0)").show();
        $(this).hide();
        $("#hotelForm").find(".addarea").hide();
        $("#hotelForm").find(".addicon").hide();
        $("#hotelForm").find(".box").each(function(){
            if(!$(this).is(":visible")){
                $("#hotelForm").find(".addarea").show();
                $("#hotelForm").find(".addicon").show();
            }
        })
    })
    $("#hotelForm").find(".jsicon").on("click",function(){
        $("#removeHotelji").parents(".box:eq(0)").show();
        $(this).hide();
        $("#hotelForm").find(".addarea").hide();
        $("#hotelForm").find(".addicon").hide();
        $("#hotelForm").find(".box").each(function(){
            if(!$(this).is(":visible")){
                $("#hotelForm").find(".addarea").show();
                $("#hotelForm").find(".addicon").show();
            }
        })
    })
    $("#hotelForm").find(".positionicon").on("click",function(){
        $("#removehotelpos").parents(".box:eq(0)").show();
        $(this).hide();
        $("#hotelForm").find(".addarea").hide();
        $("#hotelForm").find(".addicon").hide();
        $("#hotelForm").find(".box").each(function(){
            if(!$(this).is(":visible")){
                $("#hotelForm").find(".addarea").show();
                $("#hotelForm").find(".addicon").show();
            }
        })
    })
}
function ownremovelay(){
    $("#removeOwnpos").on("click",function(){
        $(this).parents(".box:eq(0)").hide();
        $("#ownExpenseForm").find(".addicon").show();
        $("#ownExpenseForm").find(".positionicon").show();
    })
    $("#removeOwnp").on("click",function(){
        $(this).parents(".box:eq(0)").hide();
        $("#ownExpenseForm").find(".addicon").show();
        $("#ownExpenseForm").find(".photoicon").show()
    })
    $("#removeownExpenseji").on("click",function(){
        $(this).parents(".box:eq(0)").hide();
        $("#ownExpenseForm").find(".addicon").show();
        $("#ownExpenseForm").find(".jsicon").show();
    })
    $("#removeownlimitNumber").on("click",function(){
        $(this).parents(".box:eq(0)").hide();
        $("#ownExpenseForm").find(".addicon").show();
        $("#ownExpenseForm").find(".limiticon").show();
    })
    $("#removeownserviceItem").on("click",function(){
        $(this).parents(".box:eq(0)").hide();
        $("#ownExpenseForm").find(".addicon").show();
        $("#ownExpenseForm").find(".servericon").show()
    })
    $("#removeownreferencePrice").on("click",function(){
        $(this).parents(".box:eq(0)").hide();
        $("#ownExpenseForm").find(".addicon").show();
        $("#ownExpenseForm").find(".priceicon").show();
    })
    $("#ownExpenseForm").find(".addicon").on("click",function(){
        $("#ownExpenseForm").find(".addarea").show();
    });
    $("#ownExpenseForm").find(".photoicon").on("click",function(){
        $("#removeOwnp").parents(".box:eq(0)").show();
        $(this).hide();
        $("#ownExpenseForm").find(".addarea").hide();
        $("#ownExpenseForm").find(".addicon").hide();
        $("#ownExpenseForm").find(".box").each(function(){
            if(!$(this).is(":visible")){
                $("#ownExpenseForm").find(".addarea").show();
                $("#ownExpenseForm").find(".addicon").show();
            }
        })
    })
    $("#ownExpenseForm").find(".jsicon").on("click",function(){
        $("#removeownExpenseji").parents(".box:eq(0)").show();
        $(this).hide();
        $("#ownExpenseForm").find(".addarea").hide();
        $("#ownExpenseForm").find(".addicon").hide();
        $("#ownExpenseForm").find(".box").each(function(){
            if(!$(this).is(":visible")){
                $("#ownExpenseForm").find(".addarea").show();
                $("#ownExpenseForm").find(".addicon").show();
            }
        })
    })
    $("#ownExpenseForm").find(".positionicon").on("click",function(){
        $("#removeOwnpos").parents(".box:eq(0)").show();
        $(this).hide();
        $("#ownExpenseForm").find(".addarea").hide();
        $("#ownExpenseForm").find(".addicon").hide();
        $("#ownExpenseForm").find(".box").each(function(){
            if(!$(this).is(":visible")){
                $("#ownExpenseForm").find(".addarea").show();
                $("#ownExpenseForm").find(".addicon").show();
            }
        })
    })
    $("#ownExpenseForm").find(".limiticon").on("click",function(){
        $("#removeownlimitNumber").parents(".box:eq(0)").show();
        $(this).hide();
        $("#ownExpenseForm").find(".addarea").hide();
        $("#ownExpenseForm").find(".addicon").hide();
        $("#ownExpenseForm").find(".box").each(function(){
            if(!$(this).is(":visible")){
                $("#ownExpenseForm").find(".addarea").show();
                $("#ownExpenseForm").find(".addicon").show();
            }
        })
    })
    $("#ownExpenseForm").find(".servericon").on("click",function(){
        $("#removeownserviceItem").parents(".box:eq(0)").show();
        $(this).hide();
        $("#ownExpenseForm").find(".addarea").hide();
        $("#ownExpenseForm").find(".addicon").hide();
        $("#ownExpenseForm").find(".box").each(function(){
            if(!$(this).is(":visible")){
                $("#ownExpenseForm").find(".addarea").show();
                $("#ownExpenseForm").find(".addicon").show();
            }
        })
    })
    $("#ownExpenseForm").find(".priceicon").on("click",function(){
        $("#removeownreferencePrice").parents(".box:eq(0)").show();
        $(this).hide();
        $("#ownExpenseForm").find(".addarea").hide();
        $("#ownExpenseForm").find(".addicon").hide();
        $("#ownExpenseForm").find(".box").each(function(){
            if(!$(this).is(":visible")){
                $("#ownExpenseForm").find(".addarea").show();
                $("#ownExpenseForm").find(".addicon").show();
            }
        })
    })
}
function photoScenic(obj,arr){
    obj.diyUpload({
        url: CONTEXTPATH + '/cloudFile/uploadCloudPhotos',
        success: function (data) {
            var data = data;//$(data._raw).find("item").text();
            arr.push(data);
            //$("#photoLibrary1").find(":hidden").each(function () {
            //    if (!$.trim($(this).val())) {
            //        $(this).val(data);
            //        arr.push(data);
            //        return false;
            //    }else{
            //        arr.push($(this).val());
            //    }
            //})
            if(arr.length>=10){
                obj.hide();
            }
        },
        buttonText: '<button type="button" class="btn btn-primary pull-right btn-block btn-sm">上传景点图片</button>',
        error: function (err) {
            //console.info(err);
        },
        fileNumLimit: 10
    });
    obj.find("label").parent().css({width:obj.find("button").outerWidth(),height:obj.find("button").outerHeight()})

}
function shoppingTable(arr,obj){
    var htmlTabel='';
    var index=0;
    obj.find("tbody").empty();
    $.each(arr,function(i,item){
        $.each(item,function(t,items){
            index++;
            htmlTabel+='<tr><td>'+index+'</td><td>' +items.shoppingCity+
                '</td><td>'+items.shoppingName+'</td><td>'+items.sellProducts+'</td><td>'+items.residenceTime+'</td></tr>'
            obj.find("tbody").html(htmlTabel);
        })
    })
    $.each(shoppingMoreList,function(i,item){
            index++;
            htmlTabel+='<tr><td>'+index+'</td><td>' +item.shoppingCity+
                '</td><td>'+item.shoppingName+'</td><td>'+item.sellProducts+'</td><td>'+item.residenceTime+'</td></tr>'
            obj.find("tbody").html(htmlTabel);

    })
}
function ownTable(arr,obj){
    var htmlTabel='';
    var index=0;
    obj.find("tbody").empty();
    $.each(arr,function(i,item){
        $.each(item,function(t,items){
            index++;
            htmlTabel+='<tr><td>'+index+'</td><td>' +items.itemName+
                '</td></tr>'
            obj.find("tbody").html(htmlTabel)
        })
    });
    $.each(ownExpenseMoreList,function(i,item){
        index++;
        htmlTabel+='<tr><td>'+index+'</td><td>' +item.itemName+
            '</td>';
        obj.find("tbody").html(htmlTabel);

    })
}
function  autosearchMoreShopping(obj) {

    var ms=obj.autocomplete({
        delay: 500,
        scrollHeight: 200,
        source: function(request, response ) {
            var term = request.term;
            if($.trim(request.term).length>=1) {
                $.ajax({
                    url: CONTEXTPATH + '/shoppingagency/getShoppingAgencyByName',
                    dataType: "json",
                    type:"POST",
                    data: {
                        hotelName: request.term
                        // cities: destinationArr.join(",")
                    },
                    success: function (data) {
                        var data=data.shoppingAgencyList;
                        data.push({
                            shoppingName:" 创建自定义购物场所“"+obj.val()+"”",
                            cityId: "",
                            shoppingCity: "",
                            lat:"",
                            lng:"",
                            residenceTime:"",
                            sellProducts:""
                        });
                        cache[term] = data;
                        response($.map(data, function (item) {
                            return {
                                value: item.shoppingName,
                                shoppingCity:item.shoppingCity,
                                sellProducts:item.sellProducts,
                                residenceTime:item.residenceTime,
                                lat:item.lat,
                                lng:item.lon,
                                cityId:item.cityId,
                                id:item.id
                            }
                        }));
                    }
                });
            }
        },
        minLength: 1,
        select: function( event, ui ) {
            $(event.target).blur();
            //$(event.target).next().val(ui.item.mafengId).change();
            if(ui.item.lng&&ui.item.lat){
                _lngnow=Number(ui.item.lng);
                _latnow=Number(ui.item.lat);
            }else{
                _lngnow=Number(116);
                _latnow=Number(39);
            }
            var eventid=$(event.target).attr("id");
            var shoppingCity=ui.item.shoppingCity;
            var label=ui.item.label;
            var cityId=ui.item.cityId;
            var sellProducts=ui.item.sellProducts;
            var residenceTime=ui.item.residenceTime;
            var daynum=$(event.target).parents("form").attr("id").split("form2")[1];
            //var shoppingId=$(event.target).attr("shoppingId");ui.item.lat
            var shoppingId=ui.item.id;
            var boolHas=false;
            var imgHtml="";
            $("#shoppingName").html('<label class="control-label col-md-6"  id="shoppingname">'+label+'</label>').removeClass("has-success").removeClass("has-error");;
            $("#shoppingCity").val(shoppingCity);
            $("#sellProducts").val(sellProducts);
            $("#residenceTime").val(residenceTime);
            $("#shoppingCity").attr("areaid",cityId);
            $("#shoppingName").attr("daynum",daynum);
            $("#shoppingName").attr("eventid",eventid);
            $("#shoppingName").attr("editShoppingId",ui.item.id);
            $("#shoppingname").text(label);

            if(shoppingId) {
                $("#shoppingName").attr("shoppingId", shoppingId);
            }else{
                $("#shoppingName").attr("shoppingId", "");
            }
            var shoppingobj={};
            if(shoppingId) {
                shoppingobj["id"] = shoppingId;
            }
            shoppingobj["shoppingName"]=label;
            shoppingobj["shoppingCity"]=shoppingCity;
            shoppingobj["cityId"]=cityId;
            shoppingobj["sellProducts"]=sellProducts;
            shoppingobj["residenceTime"]=residenceTime;
            shoppingobj["cityId"]=cityId;
            shoppingobj["lat"]=ui.item.lat;
            shoppingobj["lon"]=ui.item.lng;
            //shoppingobj.code=$(event.target).attr("id");
            //$.each(shoppingMoreList, function(i, item){
            //            //if(eventid&&item.code==eventid||item.id&&shoppingId&&item.id==shoppingId) {
            //    if(item.id&&shoppingId&&item.id==shoppingId) {
            //                boolHas = true;
            //                shoppingMoreList[i]["scenicName"] = shoppingobj.shoppingName;
            //                shoppingMoreList[i]["scenicCity"] = shoppingobj.shoppingCity;
            //                shoppingMoreList[i]["cityId"] = shoppingobj.cityId;
            //                shoppingMoreList[i]["sellProducts"] = shoppingobj.sellProducts;
            //                shoppingMoreList[i]["residenceTime"] = shoppingobj.residenceTime;
            //                shoppingMoreList[i]["lat"] = shoppingobj.lat;
            //                shoppingMoreList[i]["lon"] = shoppingobj.lng;
            //                shoppingMoreList[i]["id"] = shoppingobj.id;
            //                return false;
            //            }
            //});
            //if(!boolHas){
            //
            //    shoppingMoreList.push(shoppingobj);
            //}
            //shoppingTable(shoppingScheduleList,$("#shoppingTable"));
            $("#addShopping").parents(".form-group").show();
            $("#addShopping").parents(".form-group").prev().hide();
            $('#mapshopping').attr("src",CONTEXTPATH+"/mapsearch.html");
            autosearchsearSpot($("#shoppingCity"));
            shoppingremove();
            blockUIOpenShareNo("dialogModalshopping");
            if($("#shoppingForm").data('bootstrapValidator').getFieldElements('shoppingname')) {
                $("#shoppingForm").data('bootstrapValidator').removeField("shoppingname");
            }
            $("#shoppingForm").data('bootstrapValidator').resetField("shoppingCity");
            $(".returnCityShopping").off().on("click",function(){
                //that.parents(".form-group:eq(0)").find("input[type=text]").val("").attr("ownexpenseid","").show();
                //that.parents(".form-group:eq(0)").find(".form-control-static").remove();
                blockUIClose();
            });
        }
    }).focus(function (event) {
        sourceObjshopping=event.target;
        $(this).autocomplete("search");
    });
    obj.on("input",function(event){
        sourceObjshopping=event.target;
        $(this).next().val("").change();
    });
    if( ms.data("ui-autocomplete")) {
        ms.data("ui-autocomplete")._renderItem = function (ul, item) {
            if (item.value.indexOf("创建自定义购物场所") < 0) {
                return $("<li class='ui-menu-item' style='line-height:26px;padding:0 10px' lng="+item.lng+" lat="+item.lat+"></li>")
                    .attr("data-value", item.value)
                    .append("<div  style='display:inline-block;'>" + item.label +"</div>")
                    .append("<div  style='display:inline-block;padding-left:10px;color:#999;font-size:12px;'>（" + item.shoppingCity + "）</div>")
                    .appendTo(ul);
            } else {
                return $("<li style='line-height:26px;padding:0 10px;color:#1fbad1;'onclick='shoppingLast(\"" + obj[0].id + "\")'></li>")
                    .attr("data-value", item.value)
                    .append("<div  style='display:inline-block;'>" + item.label + "</div>")
                    .append("<div  style='display:inline-block;padding-left:10px;color:#999;font-size:12px;'>" + item.shoppingCity + "</div>")
                    .appendTo(ul);
            }
        }
    }
}
function  autosearchownMoreExpense(obj) {

    var ms=obj.autocomplete({
        delay: 500,
        scrollHeight: 200,
        source: function(request, response ) {
            var term = request.term;
            if($.trim(request.term).length>=1) {
                $.ajax({
                    url: CONTEXTPATH + '/ownexpenseagency/getOwnExpenseAgencyByName',
                    dataType: "json",
                    type:"POST",
                    data: {
                        itemName: request.term,
                        //cities: destinationArr.join(",")
                    },
                    success: function (data) {
                        var data=data.ownExpenseAgencyList;
                        data.push({
                            itemName:" 创建自定义自费项目“"+obj.val()+"”",
                            itemCity: "",
                            lat:"",
                            lng:"",
                            briefintroduction:""
                        });
                        cache[term] = data;
                        response($.map(data, function (item) {
                            return {
                                value: item.itemName,
                                itemCity:item.itemCity,
                                lat:item.lat,
                                lon:item.lon,
                                briefintroduction:item.briefintroduction,
                                photoUrls:item.photoUrls,
                                id:item.id,
                                limitNumber:item.limitNumber,
                                serviceItem:item.serviceItem,
                                referencePrice:item.referencePrice,
                            }
                        }));
                    }
                });
            }
        },
        minLength: 1,
        select: function( event, ui ) {

            //$(event.target).next().val(ui.item.mafengId).change();
            if(ui.item.lon&&ui.item.lat){
                _lngnow=Number(ui.item.lon);
                _latnow=Number(ui.item.lat);
            }else{
                _lngnow=Number(116);
                _latnow=Number(39);
            }
            $(event.target).blur();
            var eventid=$(event.target).attr("id");
            var brief=ui.item.briefintroduction;
            var city=ui.item.itemCity;
            var label=ui.item.label;
            var limitNumber=ui.item.limitNumber;
            var serviceItem=ui.item.serviceItem;
            var referencePrice=ui.item.referencePrice;
            var photoUrls=ui.item.photoUrls||[];
            var daynum=$(event.target).parents("form").attr("id").split("form2")[1];
            var ownExpenseId=ui.item.id;
            var boolHas=false;
            var imgHtml="";
            $("#ownExpenseName").html('<label class="control-label col-md-6"  id="ownExpensename">'+label+'</label>').removeClass("has-success").removeClass("has-error");
            $("#ownbrief").val(brief);
            $("#ownExpenseCity").val(city);
            //$("#ownExpenseCity").attr("areaid",destionationId);
            $("#ownExpenseName").attr("daynum",daynum);
            $("#ownExpenseName").attr("eventid",eventid);
            $("#ownExpenseName").attr("editOwnId",ui.item.id);
            $("#limitNumber").val(limitNumber);
            $("#serviceItem").val(serviceItem);
            $("#referencePrice").val(referencePrice);
            $("#ownExpensename").text(label);
            $("#ownExpensePhotosUrl1").show();
            photoScenic($('#ownExpensePhotosUrl1'),photoUrls);
            $.each(photoUrls, function(i, item){
                imgHtml+=' <li class="schedulePhotos2">\
                        <div class="viewThumb">\
                      <img src="'+item+'"/>\
                   </div>\
                   <div class="diyCancel"></div>\
                   <div title="删除" class="diySuccess" srcimg="'+item+'" style="display: block;"></div>\
                    <div class="diyBar" style="display: none;">\
                       <div class="diyProgress" style="width: 100%;"></div>\
                       <div class="diyProgressText">上传完成</div>\
                  </div>\
               </li>';
            });
            $("#ownExpenseListImg").html(imgHtml);
            if(ownExpenseId) {
                $("#ownExpenseName").attr("ownExpenseId", ownExpenseId);
            }else{
                $("#ownExpenseName").attr("ownExpenseId", "");
            }
            var ownExpense={};
            if(ownExpenseId) {
                ownExpense["id"] = ownExpenseId;
            }
            ownExpense["itemName"]=label;
            ownExpense["itemCity"]=city;
            // ownExpense["cityId"]=destionationId;
            ownExpense["limitNumber"]=limitNumber;
            ownExpense["serviceItem"]=serviceItem;
            ownExpense["referencePrice"]=referencePrice;
            ownExpense["briefintroduction"]=brief;
            ownExpense["lat"]=ui.item.lat;
            ownExpense["lon"]=ui.item.lon;
            ownExpense["photoUrls"]=ui.item.photoUrls;
            ownExpense.code=$(event.target).attr("id");
            //$.each(ownExpenseMoreList, function(i, item){
            //            if(item.id&&ownExpenseId&&item.id==ownExpenseId) {
            //                boolHas = true;
            //                ownExpenseMoreList[i]["itemName"] = ownExpense.itemName;
            //                //spot["enName"]=$("#spotenname").text();
            //                ownExpenseMoreList[i]["itemCity"] = ownExpense.itemCity;
            //                ownExpenseMoreList[i]["cityId"] = ownExpense.cityId;
            //                ownExpenseMoreList[i]["briefintroduction"] = ownExpense.briefintroduction;
            //                ownExpenseMoreList[i]["lat"] = ownExpense.lat;
            //                ownExpenseMoreList[i]["lon"] = ownExpense.lon;
            //                ownExpenseMoreList[i]["limitNumber"] = ownExpense.limitNumber;
            //                ownExpenseMoreList[i]["serviceItem"] = ownExpense.serviceItem;
            //                ownExpenseMoreList[i]["referencePrice"] = ownExpense.referencePrice;
            //                ownExpenseMoreList[i]["photoUrls"] = ownExpense["photoUrls"];
            //                ownExpenseMoreList[i]["id"] = ownExpense["id"];
            //                return false;
            //            }
            //});
            //if(!boolHas){
            //    ownExpenseMoreList.push(ownExpense);
            //}
            //ownTable(ownExpenseScheduleList,$("#freeTable"));
            $('#mapOwn').attr("src",CONTEXTPATH+"/mapsearch.html");
            autosearchsearSpot($("#ownExpenseCity"));
            $("#addFree").parents(".form-group").show();
            $("#addFree").parents(".form-group").prev().hide();
            ownExpenseremove();
            blockUIOpenShareNo("dialogModalownExpense");
            if($("#ownExpenseForm").data('bootstrapValidator').getFieldElements('ownExpensename')) {
                $("#ownExpenseForm").data('bootstrapValidator').removeField("ownExpensename");
            }
            $("#ownExpenseForm").data('bootstrapValidator').resetField("ownExpenseCity");
            $(".returnCityOwn").off().on("click",function(){
                //that.parents(".form-group:eq(0)").find("input[type=text]").val("").attr("ownexpenseid","").show();
                //that.parents(".form-group:eq(0)").find(".form-control-static").remove();
                blockUIClose();
            });
        }
    }).focus(function (event) {
        sourceObjownExpense=event.target;
        $(this).autocomplete("search");
    });
    obj.on("input",function(event){
        sourceObjownExpense=event.target;
        $(this).next().val("").change();
    });
    if( ms.data("ui-autocomplete")) {
        ms.data("ui-autocomplete")._renderItem = function (ul, item) {
            if (item.value.indexOf("创建自定义自费项目") < 0) {
                return $("<li class='ui-menu-item' style='line-height:26px;padding:0 10px' lng="+item.lng+" lat="+item.lat+"></li>")
                    .attr("data-value", item.value)
                    .append("<div  style='display:inline-block;'>" + item.label +"</div>")
                    .append("<div  style='display:inline-block;padding-left:10px;color:#999;font-size:12px;'>（" + item.itemCity + "）</div>")
                    .appendTo(ul);
            } else {
                return $("<li style='line-height:26px;padding:0 10px;color:#1fbad1;'onclick='ownExpenseLast(\"" + obj[0].id + "\")'></li>")
                    .attr("data-value", item.value)
                    .append("<div  style='display:inline-block;'>" + item.label + "</div>")
                    .append("<div  style='display:inline-block;padding-left:10px;color:#999;font-size:12px;'>" + item.itemCity + "</div>")
                    .appendTo(ul);
            }
        }
    }
}