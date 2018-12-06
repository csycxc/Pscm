//多个页面加载事件
function addLoadEvent(func){
	var oldonload = window.onload;
	if(typeof window.onload != 'function'){
		window.onload = func;
	}else{
		window.onload = function(){
			oldonload();
			func();
		}
	}
}
//左右拖动
function zuoyoutuodong() {
	var doc = $("#box"), dl = $("#leftdiv"), dc = $("#rightdiv");
    var sum = dl.width() + dc.width() + 
    $("#line").mousedown (function (e) {
        var me = $(this);
        var deltaX = e.clientX 
                - 
                (parseFloat(me.css("left")) || parseFloat(me.prop("clientLeft")));
        doc.mousemove(function (e){
            var lt = e.clientX - deltaX; 
            lt = lt < 0 ? 0 : lt;
            lt = lt > sum - me.width() ? sum - me.width() : lt;
            me.css ("left", lt + "px");
            dl.width(lt);
            dc.width(sum-lt-me.width());
        });
    }).width();

    doc.mouseup (function(){ doc.unbind("mousemove");});
    doc[0].ondragstart = doc[0].onselectstart = function (){ return false; };
};