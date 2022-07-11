window.onload = function() {
    var div1 = document.querySelector('#dragCircle');
   /* var head = document.querySelector('.headtop-bg');*/

//限制最大宽高，不让滑块出去
    var maxW = document.body.clientWidth - div1.offsetWidth;
    var maxH = document.body.clientHeight - div1.offsetHeight;
//手指触摸开始，记录div的初始位置
    div1.addEventListener('touchstart', function(e) {
        var ev = e || window.event;
        var touch = ev.targetTouches[0];
        oL = touch.clientX - div1.offsetLeft;
        oT = touch.clientY - div1.offsetTop;
        e.stopPropagation();
        $('.index_container').css('overflow','hidden');
    });
//触摸中的，位置记录
    div1.addEventListener('touchmove', function(e) {
        var ev = e || window.event;
        var touch = ev.targetTouches[0];
        var oLeft = touch.clientX - oL;
        var oTop = touch.clientY - oT;
        if(oLeft < 0) {
            oLeft = 0;
        } else if(oLeft >= maxW) {
            oLeft = maxW;
        }
        if(oTop < 0) {
            oTop = 0;
        } else if(oTop >= maxH) {
            oTop = maxH;
        }
        div1.style.left = oLeft + 'px';
        div1.style.top = oTop + 'px';
        e.stopPropagation();
        $('.index_container').css('overflow','hidden');
    });
//触摸结束时的处理
    div1.addEventListener('touchend', function() {
        document.removeEventListener("touchmove", defaultEvent);
        $('.index_container').css('overflow','auto');
    });
//阻止默认事件
    function defaultEvent(e) {
        e.stopPropagation();
    }
}