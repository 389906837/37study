<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/include/taglibs.jsp"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="公司简介，企业简介，37号仓，37号仓简介，江苏叁拾柒号仓" />
    <meta name="description" content="37号仓是一家人工智能领域机器视觉方面的研发型公司，企业拥有一支高水平的研发和管理团队，致力于人工智能视觉识别系统的软硬件研发、生产和推广。" />
    <title>关于我们-37号仓-计算机视觉识别核心算法供应商-江苏叁拾柒号仓智能科技有限公司</title>
    <link rel="stylesheet" href="${staticSource}/static/css/index.css">
    <link rel="shortcut icon" href="${staticSource}/static/favicon.ico" type="image/x-icon">
</head>
<body>

<div class="main">

    <!--头-->
    <jsp:include page="/common/include/noIndexFrontTop.jsp">
        <jsp:param name="selected" value="1"/>
    </jsp:include>

    <div class="aboutus">
        <div class="aboutus-img"></div>
        <div class="aboutus-intro" id="qyjj">
            <div class="aboutus-intro-w">
                <h1>企业简介</h1>
                <div class="aboutus-intro-bg">
                    <div class="aboutus-introduction">
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;江苏叁拾柒号仓智能科技有限公司是江苏子雨集团的人工智能板块。
                        企业理念基于万物互联、万物识别；公司定位于人工智能领域机器视觉
                        方面的研发型公司，企业核心是视觉识别方面算法研发、AI芯片适配及
                        机器视觉软硬件集成和开发。</br></br>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;产品可广泛应用于无人零售、金融资产监管、珍稀动植物保护以及精准农业、养老、医疗等领域，在产生经济价值的同时实现巨大的社会价值。
                    </div>
                </div>
            </div>
        </div>
        <div class="aboutus-vision" id="qyyj">
            <h1>企业愿景</h1>
            <p>
                通过技术输出及合作，逐步为前端智能化产品装配上智慧的“眼睛”，</br>
                在这些“眼睛”观察世界的同时，我们又建立了海量的物品库，</br>
                为将来机器人辅助人类从事各方面替代性工作打下基础。
            </p>
            <p>通过万物互联万物识别最终实现用人工智能科技服务人类</p>
        </div>
    </div>
    <!--尾-->
    <jsp:include page="/common/include/indexFrontBottom.jsp" />
</div>

<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript">
    <%@ include file="/common/include/baidu.jsp"%>
</script>

</body>
</html>
