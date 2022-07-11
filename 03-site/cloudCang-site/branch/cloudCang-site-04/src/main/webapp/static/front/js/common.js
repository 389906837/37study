
   $(window).load(function(){
        /*   导航  */
	   
	   $(".nav-sun").each(function(){
		  var wdt=allwdt=0;
		  var linum=$(this).find("li").length;
		  for(var t=0;t<linum;t++){
			  wdt=$(this).find("li").eq(t).width();
			  allwdt=allwdt+wdt; 
		  }
		  $(this).width(allwdt+46);
	   });
        var num;
        $(".nav-sun").mouseenter(function(){
            $(".nav-parent li").find("em").eq(num).removeClass("hide");
            $(this).addClass("nav-sunhover");
        });
        $(".nav-sun").mouseleave(function(){
            $(this).removeClass("nav-sunhover");
             $(".nav-parent li").find("em").eq(num).addClass("hide").stop();
        });
        $(".nav-parent li").mouseenter(function(){
            num=$(".nav-parent li").find("em").index($(this).find("em"));
            if(num>=0)
            {
               $(this).find("em").removeClass("hide");
                $(".nav-sun").eq(num).addClass("nav-sunhover").stop(true,true); 
            }
            //子导航位置
            var lefof=$(this).position().left+40+$(this).width()/2;
            var leftoff=$(".nav-sun").eq(num).width();
            if(leftoff>=700 || 700-$(this).position().left+40<=leftoff/2){
            	$(".nav-sun").eq(num).css("right",0);
            }
            else{
            	$(".nav-sun").eq(num).css("left",lefof-leftoff/2);
            }
        });
         $(".nav-parent li").mouseleave(function(){
            $(".nav-sun").eq(num).removeClass("nav-sunhover");
             $(this).find("em").addClass("hide").stop();
        });
        $(".nav-childrenmid ul").each(function(){
            $(this).find("li:last").addClass("last");
        });
        
   });


