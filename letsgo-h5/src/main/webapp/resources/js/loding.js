var myScroll;
function loaded () {

        upIcon = $("#continue_loding"),
        downIcon = $("#continue_loding"),
        distance = 30; //滑动距离

    myScroll = new IScroll('#wrapper', { probeType: 3, mouseWheel: true });

    myScroll.on("scroll",function(){
        var y = this.y,
            maxY = this.maxScrollY - y;
            downHasClass = downIcon.hasClass("reverse_icon"),
            upHasClass = upIcon.hasClass("reverse_icon");

        if(y >= distance){
            !downHasClass && downIcon.addClass("reverse_icon");
            return "";
        }else if(y < distance && y > 0){
            downHasClass && downIcon.removeClass("reverse_icon");
            return "";
        }

        if(maxY >= distance){
           !upHasClass && upIcon.addClass("reverse_icon");
            return "";
        }else if(maxY < distance && maxY >=0){
           upHasClass && upIcon.removeClass("reverse_icon");
            return "";
        }
    });

    // 下拉刷新事件
    myScroll.on("slideDown",function(){

        if(this.y > 50) {
            if (parseInt(dum) <= 1) {
                return false;
            }
            if (ctHref) {
                window.location.href = ctHref;
            } else {

            }
        }
     });
    // 上拉滑动事件
    myScroll.on("slideUp",function(){
       // if(this.maxScrollY - this.y > distance){

            if(parseInt(dum)+1>totallen){
                $("#continue_loding").find("span").text("已经到底,点击左侧查看更多!");
                return false;
            }
            if(cdHref) {
                window.location.href = cdHref;
            }else{

            }
            //upIcon.removeClass("reverse_icon")
       // }
    });
}
var cdHref="";
var ctHref="";
var totallen=$(".cd-time-lay").find(".cd-child_list").find("li").length;
