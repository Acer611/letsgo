
var _lngnow=126.977791;
var _latnow=37.573925;
var map;
var marker;
var projection;
var defaultZoom = 16;
var placenow;
var placenowloc="";
function initialize() {
    var myLatlng = new google.maps.LatLng(_latnow,_lngnow);
    placenow=myLatlng;
    map = new google.maps.Map(document.getElementById('gMap'), {
        zoom: defaultZoom,
        center: myLatlng,
        disableDefaultUI: true,
        mapTypeId: google.maps.MapTypeId.ROADMAP,
        draggable: true,
        scrollwheel: true,
        disableDoubleClickZoom: true,
        maxZoom:18,
        minZoom:1
    });
    marker = new google.maps.Marker({
        position: myLatlng,
        optimized: false,
        map: map,
        icon: {
            path: google.maps.SymbolPath.CIRCLE,
            scale: 8,
            strokeColor: '#fff',
            strokeWeight: 3,
            fillColor: '#1484ff',
            fillOpacity: 1
        },
    });
    var trafficLayer = new google.maps.TrafficLayer();
    trafficLayer.setMap(map);
    //var transitLayer = new google.maps.TransitLayer();
    //transitLayer.setMap(map);
    //var bikeLayer = new google.maps.BicyclingLayer();
    //bikeLayer.setMap(map);
    var myCity = new google.maps.Circle({
        strokeColor: '#1484ff',
        strokeOpacity: 0.15,
        strokeWeight: 0,
        fillColor: '#1484ff',
        fillOpacity:0.15,
        map: map,
        center: myLatlng,
        radius: 50
    });
    //map.setMyLocationEnabled();
    myCity.setMap(map);
    pixelsnew(marker.getPosition());
    pixels(marker.getPosition());
    //map.addListener('dragend', function() {
    //    //pixels();
    //});
    //var curZoom=map.getZoom();
    /*map.addListener('dblclick', function() {
     curZoom=map.getZoom();
     if(curZoom>defaultZoom||curZoom<5){
     //return;
     }
     map.setZoom(curZoom+1);
     });*/
    //zoomchange3();
   // addMarkerWithTimeout(myLatlng, 200);
}
var imageinco="resources/dist/images/poi-marker_05.png"
var latlng_new;
var pixel,pixelold;
var markers = [];
function pixels(){
    var overlay = new google.maps.OverlayView();
    overlay.draw = function() {};
    overlay.onAdd = function() {
        projection = this.getProjection();
        latlng_new=projection.fromContainerPixelToLatLng(new google.maps.Point((pixelold.x),(pixelold.y)) ) ;
        pixel_new=projection.fromLatLngToContainerPixel(latlng_new)
        //https://maps.googleapis.com/maps/api/geocode/json?latlng=39.8974446,116.4038432&key=
        /*$.getJSON('https://maps.googleapis.com/maps/api/geocode/json?latlng='+latlng_new.lat() +','+latlng_new.lng() +'&key=AIzaSyBok03gYzNXxJPWYkfnlaZi7_XVdkc1MVo', function(data){
         console.log(data.results[0].formatted_address)
         })*/
        var geocoder = new google.maps.Geocoder();
        geocoder.geocode( { 'location': latlng_new}, function(results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                //map.setCenter(myLatlng);
                //console.log( results[0].formatted_address.split(" ")[0]);
                placenowloc=results[0].formatted_address;
                $(".positionSpot", window.parent.document).text(placenowloc);
            } else {
                $(".positionSpot", window.parent.document).text("暂时无法获取位置");
                console.log('Geocode was not successful for the following reason: ' + status);
            }
        });
        placenow=latlng_new;
    };
    overlay.setMap(map);
    return latlng_new;

}
function pixelsnew(p2){
    //console.log(p2)
    var overlay2 = new google.maps.OverlayView();
    overlay2.draw = function() {};
    overlay2.onAdd = function() {
        projection2 = this.getProjection();
        pixelold =projection2.fromLatLngToContainerPixel(p2);
        $("#pin").css({left:(pixelold.x-$("#pin").width()/2),top:(pixelold.y-$("#pin").height()),visibility:'visible'});
    };
    overlay2.setMap(map);
}
function showToast(lat,lng,loc) {
    javascript:control.showToast(lat,lng,loc);
}

function addMarkerWithTimeout(position, timeout,callback) {
    window.setTimeout(function() {
        markers.push(new google.maps.Marker({
            position: position,
            map: map,
            icon:imageinco,
            animation: google.maps.Animation.DROP
        }));
        // callback();
    }, timeout);

}

function clearMarkers() {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
    markers = [];
}
//markers[i].setMap(null);