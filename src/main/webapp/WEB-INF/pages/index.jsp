<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
    <head>
        <base href="<%=basePath%>" />
        <title>小李庄农贸管理系统</title>
        <meta charset="UTF-8" />
        <meta http-equiv="cleartype" content="on" />
        <meta http-equiv="cache-control" content="no-cache" />

        <link rel="Shortcut Icon" href="<%=basePath%>favicon.ico" />

        <link rel="stylesheet" type="text/css" href="resources/libs/bootstrap/css/bootstrap.min.css" />
        <link rel="stylesheet" type="text/css" href="resources/libs/font-awesome/css/font-awesome.min.css">
        <link rel="stylesheet" href="resources/index/index.css">

        <script data-main="resources/index/index" src="resources/require.js"></script>
    </head>
    <body class="login-bg">
        <div class="login-wrapper">
            <div class="logo">
                <p class="logo-img"></p>
                <h1>小李庄农贸管理系统</h1>
            </div>
            <div class="box">
                <div class="content-wrap">
                    <p class="error-message">${login_error}</p>
                    <form autocomplete="off" action="login" method="post">
                        <div class="form-group">
                            <input id="account" name="account" type="text" class="form-control" placeholder="用户名" value="admin">
                        </div>
                        <div class="form-group">
                            <input id="password" name="password" type="password" class="form-control" value="123456" placeholder="密码">
                        </div>
                        <button type="button" class="btn btn-success" id="signIn">登录</button>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>