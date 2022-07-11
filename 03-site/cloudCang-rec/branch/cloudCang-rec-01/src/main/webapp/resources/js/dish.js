
   $(window).load(function(){
      var wght=document.documentElement.clientHeight;/* 屏幕 */
      var toph=$(".dish-top").height();/*  头部 */
      var bottomh=$(".dish-bottom").height()+10;/*  底部 */
      $(".wfiframe").css("height",wght-toph-bottomh);

      $("#wfiframe").contents().find(".dish").css("height",wght-toph-bottomh-1);/*  计算中间的高度 */
      var allh=$("#wfiframe").contents().find(".zq-ul").height();/* 全部食谱的高度  */
       $("#wfiframe").contents().find(".dish-content").css("height",wght-toph-bottomh-1-allh);
      $("#wfiframe").contents().find(".designated-dishes").css("height",wght-toph-bottomh-52-75);
       $("#wfiframe").contents().find(".zuonoe").css("height",wght-toph-bottomh-52-89-75);
      
     
     /* 菜单导航  */ 
      var bheight= $("#wfiframe").contents().find(".dish-title-scoll").height();

      var sheight= $("#wfiframe").contents().find(".dish-title-scoll li").height();
      var num=Math.ceil(bheight/sheight);
      var nt=0;
       $("#wfiframe").contents().find(".dish-title .down").click(function(){
               nt++;
        if(nt<num){
           $("#wfiframe").contents().find(".dish-title-scoll").animate({marginTop:-nt*sheight},300);
        }
        else{
          nt=num-1;
        }
      });
       $("#wfiframe").contents().find(".dish-title .up").click(function(){
         nt--;
        if(nt<num && nt>=0){
           $("#wfiframe").contents().find(".dish-title-scoll").animate({marginTop:-nt*sheight},300);
        }
        else{
          nt=0;
        }
      });
      /*  菜肴类别   */
      var bheight1= $("#wfiframe").contents().find(".dish-content ul").height();
      var sheight1=270;
      var num1= Math.ceil(bheight1/sheight1);
      var nt1=0;
      $(".dish-bottom .down").click(function(){
        nt1++;
        if(nt1<num1){
          $(".wfiframe").contents().find(".dish-content ul").animate({marginTop:-nt1*270},300);
        }
        else{
          nt1=num1-1;
        }

      });
      $(".dish-bottom .up").click(function(){
         nt1--;
        if(nt1<num1 && nt1>=0){
         $("#wfiframe").contents().find(".dish-content ul").animate({marginTop:-nt1*sheight1},300);
        }
        else{
          nt1=0;
        }
      });
      /*   右边  */
    var bheight2= $("#wfiframe").contents().find(".designated-scoll").height();
      var sheight2=225;
      var num2= Math.ceil(bheight2/sheight2);
      var nt2=0;
       $("#wfiframe").contents().find(".dish-rightbottom .down").click(function(){
        nt2++;
        if(nt2<num2){
          $("#wfiframe").contents().find(".designated-scoll").animate({marginTop:-nt2*sheight2},300);
        }
        else{
          nt2=num2-1;
        }

      });
       $("#wfiframe").contents().find(".dish-rightbottom .up").click(function(){
         nt2--;

        if(nt2<num2 && nt2>=0){
           $("#wfiframe").contents().find(".designated-scoll").animate({marginTop:-nt2*sheight2},300);
        }
        else{
          nt2=0;
        }
      });
      var bheight4= $("#wfiframe").contents().find(".zuonoe-scoll").height();
      var sheight4=162;
      var num4= Math.ceil(bheight4/sheight4);
      var nt4=0;
       $("#wfiframe").contents().find(".dish-rightbottom1 .down").click(function(){
        nt4++;
        if(nt4<num4){
          $("#wfiframe").contents().find(".zuonoe-scoll").animate({marginTop:-nt4*sheight4},300);
        }
        else{
          nt4=num4-1;
        }

      });
       $("#wfiframe").contents().find(".dish-rightbottom1 .up").click(function(){
         nt4--;

        if(nt4<num4 && nt4>=0){
           $("#wfiframe").contents().find(".zuonoe-scoll").animate({marginTop:-nt4*sheight4},300);
        }
        else{
          nt4=0;
        }
      });
       $("#wfiframe").contents().find(".designated-scoll ul,.dish-look ul").click(function(){
        $(this).addClass("hover");
        $(this).siblings().removeClass("hover");
        $(this).find(".mark").show();
         $(this).find(".mark").siblings().hide();
         $(this).siblings().find(".mark").hide();
         $(this).siblings().find(".mark").siblings().show();
      });
       $("#wfiframe").contents().find(".dish-rightitle em").click(function(){
         $("#wfiframe").contents().find(".dish-look").css("visibility","visible");
         $("#wfiframe").contents().find(".dish-rightbottom1").show();
         $("#wfiframe").contents().find(".norecord,.designated-dishes,.dish-rightbottom").hide();
      });
   });


