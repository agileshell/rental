<%@ page language="java" pageEncoding="UTF-8"
    contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
    <title>小李庄农贸管理系统</title>
</head>

<!-- Bread line. -->
<section class="content-header">
    <h1>统计信息<small>季度统计</small></h1>
    <ol class="breadcrumb">
        <li><a href="homePage"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li>统计信息</li>
        <li class="active">季度统计</li>
    </ol>
</section>
<!-- Content. -->
<section class="content">
    <div class="row">
        <div class="box">
            <div class="box-header">
                <h3 class="box-title">季度统计</h3>
                <div class="clearfix ">&nbsp;</div>
            </div>
            <div class="box-body">
                <table class="table table-bordered table-hover">
                    <tbody>
                        <tr>
                            <th>#</th>
                            <th>季度</th>
                            <th>摊位租金</th>
                            <th>摊位电费</th>
                            <th>摊位水费</th>
                            <th>公寓租金</th>
                            <th>公寓电费</th>
                            <th>总金额</th>
                        </tr>
                    <c:forEach items="${items}" var="item" varStatus="itemStatus">
                        <tr>
                            <td>${itemStatus.count}</td>
                            <td>${item.title}</td>
                            <td>${item.stallRentFee}</td>
                            <td>${item.stallMeterFee}</td>
                            <td>${item.stallWaterFee}</td>
                            <td>${item.flatRentFee}</td>
                            <td>${item.flatMeterFee}</td>
                            <td>${item.totalFee}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="box-footer clearfix">
                <c:set var="url" value="fee/quarter" />
                <c:set var="condition" value="" />
                <%@ include file="../layout/pagination.jsp"%>
            </div>
        </div>
    </div>
</section>