<%@ page language="java" pageEncoding="UTF-8"
    contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
    <title>小李庄农贸管理系统</title>

    <script src="resources/stall/statistic.js"></script>
</head>

<!-- Bread line. -->
<section class="content-header">
    <h1>统计信息<small>摊位统计</small></h1>
    <ol class="breadcrumb">
        <li><a href="homePage"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li>统计信息</li>
        <li class="active">摊位统计</li>
    </ol>
</section>
<!-- Content. -->
<section class="content">
    <div class="row">
        <div class="box">
            <div class="box-header">
                <form role="form" action="stall/statistic" method="get">
                    <div class="box-search container">
                        <div class="col-lg-2">
                            <div class="input-group">
                                <span class="input-group-addon">类别</span>
                                <select name="type" class="form-control">
                                    <optgroup label="请选择状态">
                                        <option value="all" ${ 'all' == request.type ? 'selected' : ''}>全部款项</option>
                                        <option value="rent" ${ 'rent' == request.type ? 'selected' : ''}>租金</option>
                                        <option value="meter" ${ 'meter' == request.type ? 'selected' : ''}>电费</option>
                                        <option value="meter" ${ 'water' == request.type ? 'selected' : ''}>电费</option>
                                    </optgroup>
                                </select>
                            </div>
                        </div>
                        <div class="col-lg-2">
                            <div class="input-group">
                                <span class="input-group-addon">状态</span>
                                <select name="isPaid" class="form-control">
                                    <optgroup label="请选择状态">
                                        <option value="true" ${ 'true' == request.isPaid ? 'selected' : ''}>已缴费</option>
                                        <option value="false" ${ 'false' == request.isPaid ? 'selected' : ''}>未缴费</option>
                                    </optgroup>
                                </select>
                            </div>
                        </div>
                        <div class="col-lg-2">
                            <div class="input-group">
                                <span class="input-group-addon">年份</span>
                                <select name="year" class="form-control">
                                    <optgroup label="请选择状态">
                                        <option value="">全部</option>
                                        <option value="2015" ${ '2015' == request.year ? 'selected' : ''}>2015</option>
                                        <option value="2016" ${ '2016' == request.year ? 'selected' : ''}>2016</option>
                                        <option value="2017" ${ '2017' == request.year ? 'selected' : ''}>2017</option>
                                        <option value="2018" ${ '2018' == request.year ? 'selected' : ''}>2018</option>
                                        <option value="2019" ${ '2019' == request.year ? 'selected' : ''}>2019</option>
                                        <option value="2020" ${ '2020' == request.year ? 'selected' : ''}>2020</option>
                                    </optgroup>
                                </select>
                            </div>
                        </div>
                        <div class="col-lg-2">
                            <div class="input-group">
                                <span class="input-group-addon">季度</span>
                                <select name="quarter" class="form-control">
                                    <optgroup label="请选择状态">
                                        <option value="0">全部</option>
                                        <option value="1" ${ 1 == request.quarter ? 'selected' : ''}>第一季度</option>
                                        <option value="2" ${ 2 == request.quarter ? 'selected' : ''}>第二季度</option>
                                        <option value="3" ${ 3 == request.quarter ? 'selected' : ''}>第三季度</option>
                                        <option value="4" ${ 4 == request.quarter ? 'selected' : ''}>第四季度</option>
                                    </optgroup>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="box-search container">
                        <div class="col-lg-4">
                            <div class="input-group">
                                <span class="input-group-addon">摊位名称</span>
                                <input class="form-control" type="text" name="stallName" value="${request.stallName}" placeholder="输入搜索的位名称">
                            </div>
                        </div>
                        <div class="col-lg-4">
                            <div class="input-group">
                                <span class="input-group-addon">租赁人</span>
                                <input class="form-control" type="text" name="rentor" value="${request.rentor}" placeholder="输入搜索的租赁人">
                            </div>
                        </div>
                        <div class="col-lg-2">
                            <div class="input-group">
                                <button type="submit" class="btn btn-primary">查询</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="box-body">
                <table class="table table-bordered table-hover">
                    <tbody>
                        <tr>
                            <th>#</th>
                            <th>季度</th>
                            <th>名称</th>
                            <th>租赁人</th>
                            <th>租金</th>
                            <th>电费</th>
                            <th>水费</th>
                            <th>总金额</th>
                        </tr>
                    <c:forEach items="${items}" var="item" varStatus="itemStatus">
                        <tr>
                            <td>${itemStatus.count}</td>
                            <td>
                                <c:if test="${!empty item.year}">
                                    ${item.year}年 第${item.quarter}季度
                                </c:if>
                            </td>
                            <td>${item.stallName}</td>
                            <td>${item.renterName}</td>
                            <td>${item.rentFee}
                                <span class="label ${item.isPayRent == 2 ? 'label-success' : 'label-danger'}">
                                    ${item.isPayRent == 2 ? '已缴纳' : '未缴纳'}
                                </span>
                                <c:if test="${item.renterId >0 }">
                                    <c:if test="${item.isPayRent == 0 }">
	                                    &nbsp;&nbsp;
	                                    <a title="租金单" href="stall/payRentPage?stallId=${item.stallId}">填写租金单</a>
                                    </c:if>
                                    <c:if test="${item.isPayRent == 1 }">
                                        &nbsp;&nbsp;
                                        <a title="确认已缴费" class="confirmPay" data-statistic="${item.statisticId}" data-type="rent" href="javascript:void(0)">确认已缴费</a>
                                    </c:if>
                                </c:if>
                            </td>
                            <td>${item.meterFee}
                                <span class="label ${item.isPayMeter == 2 ? 'label-success' : 'label-danger'}">
                                    ${item.isPayMeter == 2 ? '已缴纳' : '未缴纳'}
                                </span>
                                <c:if test="${item.renterId >0 }">
                                    <c:if test="${item.isPayMeter == 0 }">
                                        &nbsp;&nbsp;
                                        <a title="电费单" href="stall/payMeterPage?stallId=${item.stallId}">填写电费单</a>
                                    </c:if>
                                    <c:if test="${item.isPayMeter == 1 }">
                                        &nbsp;&nbsp;
                                        <a title="确认已缴费" class="confirmPay" data-statistic="${item.statisticId}" data-type="meter" href="javascript:void(0)">确认已缴费</a>
                                    </c:if>
                                </c:if>
                            </td>
                            <td>${item.waterFee}
                                <span class="label ${item.isPayWater == 2 ? 'label-success' : 'label-danger'}">
                                    ${item.isPayMeter == 2 ? '已缴纳' : '未缴纳'}
                                </span>
                                <c:if test="${item.renterId >0 }">
                                    <c:if test="${item.isPayWater == 0 }">
                                        &nbsp;&nbsp;
                                        <a title="水费单" href="stall/payWatermeterPage?stallId=${item.stallId}">填写水费单</a>
                                    </c:if>
                                    <c:if test="${item.isPayWater == 1 }">
                                        &nbsp;&nbsp;
                                        <a title="确认已缴费" class="confirmPay" data-statistic="${item.statisticId}" data-type="water" href="javascript:void(0)">确认已缴费</a>
                                    </c:if>
                                </c:if>
                            </td>
                            <td>${item.totalFee}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="box-footer clearfix">
                <c:set var="url" value="flat/statistic" />
                <c:set var="condition" value="" />
                <%@ include file="../layout/pagination.jsp"%>
            </div>
        </div>
    </div>
</section>