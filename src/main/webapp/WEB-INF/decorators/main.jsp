<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<c:url value="<%=basePath%>" var="basePath" />

<!DOCTYPE html>
<html class="no-js">
    <head>
        <base href="<%=basePath%>" />
        <title><decorator:title /></title>
        <meta charset="UTF-8" />
        <meta http-equiv="cleartype" content="on" />
        <meta http-equiv="cache-control" content="no-cache" />

        <script data-main="resources/index/main" src="resources/require.js"></script>

        <link rel="stylesheet" type="text/css" href="resources/libs/bootstrap/css/bootstrap.min.css" />
        <link rel="stylesheet" type="text/css" href="resources/libs/bootstrap/css/bootstrap-theme.min.css">
        <link rel="stylesheet" type="text/css" href="resources/libs/font-awesome/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="resources/layout.css" />
        <link rel="stylesheet" type="text/css" href="resources/common.css" />

        <link rel="Shortcut Icon" href="<%=basePath%>favicon.ico" />

        <decorator:head />
    </head>
    <body class="skin-blue">
        <input type="hidden" id="basePath" value="<%=basePath%>" />
        <header id="header">
            <a href="homePage" class="logo">小李庄农贸</a>
            <!--header-->
            <nav class="navbar navbar-static-top" role="navigation">
                <a href="javascript:void(0)" class="navbar-btn sidebar-toggle" data-toggle="offcanvas" role="button">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </a>
                <div class="navbar-right">
                    <ul class="nav navbar-nav">
                        <li class="dropdown user user-menu">
                            <a href="logout">注销</a>
                        </li>
                    </ul>
                </div>
            </nav>
        </header>
        <div class="wrapper row-offcanvas row-offcanvas-left">
            <!-- Left side column. -->
            <aside class="left-side sidebar-offcanvas">
                <section class="sidebar">
                    <!-- Sidebar user panel. -->
                    <div class="user-panel">
                        <div class="pull-left image">
                            <img src="resources/images/avatar.png" class="img-circle" alt="User Image">
                        </div>
                        <div class="pull-left info">
                            <p>Hello, Admin</p>
                            <a href="homePage"><i class="fa fa-circle text-success"></i> Online</a>
                        </div>
                    </div>
                    <!-- Sidebar menu. -->
                    <ul class="sidebar-menu">
                        <li class="active">
                            <a href="homePage">
                                <i class="fa fa-dashboard"></i> <span>首页</span>
                            </a>
                        </li>
                        <li class="treeview active">
                            <a href="#">
                                <i class="fa fa-cog"></i> <span>租赁信息</span>
                                <i class="fa fa-angle-left pull-right"></i>
                            </a>
                            <ul class="treeview-menu">
                                <li><a href="stall/list"><i class="fa fa-angle-double-right"></i> 摊位管理</a></li>
                                <li><a href="flat/list"><i class="fa fa-angle-double-right"></i> 公寓管理</a></li>
                                <li><a href="renter/list"><i class="fa fa-angle-double-right"></i> 租客管理</a></li>
                            </ul>
                        </li>
                        <li class="treeview active">
                            <a href="#">
                                <i class="fa fa-cog"></i> <span>系统设置</span>
                                <i class="fa fa-angle-left pull-right"></i>
                            </a>
                            <ul class="treeview-menu">
                                <li><a href="system/configPage"><i class="fa fa-angle-double-right"></i> 系统配置</a></li>
                                <li><a href="subarea/list"><i class="fa fa-angle-double-right"></i> 区号管理</a></li>
                            </ul>
                        </li>
                    </ul>
                </section>
            </aside>
            <!-- Right side column. -->
            <aside class="right-side">
                <!-- Body, Bread line and content. -->
                <decorator:body />
            </aside>
        </div>
    </body>
</html>
