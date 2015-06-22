<%@ page language="java" pageEncoding="UTF-8"
    contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
    <title>小李庄农贸管理系统</title>
</head>

<!-- Bread line. -->
<section class="content-header">
    <h1>区号管理<small>区号列表</small></h1>
    <ol class="breadcrumb">
        <li><a href="homePage"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li>区号管理</li>
        <li class="active">区号列表</li>
    </ol>
</section>
<!-- Content. -->
<section class="content">
    <div class="row">
        <div class="box">
            <div class="box-header">
                <h3 class="box-title">区号列表</h3>
                <div class="clearfix ">&nbsp;</div>
                <div class="box-operation">
                    <a href="subarea/addPage" class="btn btn-primary pull-right">新建区号</a>
                </div>
            </div>
            <div class="box-body">
                <table class="table table-bordered table-hover">
                    <tbody>
                        <tr>
                            <th>#</th>
                            <th>名称</th>
                            <th class="center">操作</th>
                        </tr>
                    <c:forEach items="${subareas}" var="subarea" varStatus="itemStatus">
                        <tr>
                            <td>${itemStatus.count}</td>
                            <td>${subarea.name}</td>
                            <td class="operation-part center">
                                <a title="编辑" href="subarea/editPage?subareaId=${subarea.subareaId}"><span class="glyphicon glyphicon-pencil"></span></a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</section>