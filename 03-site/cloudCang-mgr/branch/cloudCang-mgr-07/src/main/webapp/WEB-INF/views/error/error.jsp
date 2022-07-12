<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!doctype html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0" />
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no"/>
    <title>业务处理错误</title>
    <link rel="icon" href="${staticSource}/resources/images/favicon.ico" type="image/x-icon" />
    <link rel="shortcut icon" href="${staticSource}/resources/images/favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" type="text/css" href="${staticSource}/resources/css/comm.css" />
    <link rel="stylesheet" type="text/css" href="${staticSource}/resources/css/error_page.css" />
    <script type="text/javascript" src="${staticSource}/resources/js/jquery.min.js"></script>
    <style>
        html,body{
            width: 100%;
            height: 100%;
        }
        .main{
            width:80%;
            height: 100%;
            background-image: url(${staticSource}/resources/images/error-bg.png);
            background-repeat:no-repeat;
            background-size: 100% 100%;
            text-align: center;
            margin: 0 auto;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        p{margin-top: 1.5rem}
        .p1{
            font-size: 1.75rem;
        }
        .p2{
            color: #acadaa;
            font-size: 1.25rem
        }
    </style>
</head>

<body>
<div class="main">
    <div>
        <img src="${staticSource}/resources/images/error-express.png">
        <p class="p1">页面出错了，请检查后重试...</p>
    </div>

</div>
</body>
</html>
