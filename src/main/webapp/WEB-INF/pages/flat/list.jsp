<%@ page language="java" pageEncoding="UTF-8"
    contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<head>
    <title>小李庄农贸管理系统</title>

    <script src="resources/flat/list.js"></script>
</head>

<!-- Bread line. -->
<section class="content-header">
    <h1>公寓管理<small>公寓列表</small></h1>
    <ol class="breadcrumb">
        <li><a href="homePage"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li>公寓管理</li>
        <li class="active">公寓列表</li>
    </ol>
</section>
<!-- Content. -->
<section class="content">
    <div class="row">
        <div class="box">
            <div class="box-header">
                <form role="form" action="flat/list" method="get">
                    <div class="box-search container">
                        <div class="col-lg-2">
                            <div class="input-group">
                                <span class="input-group-addon">状态</span>
                                <select name="isRented" class="form-control">
                                    <optgroup label="请选择状态">
                                        <option value="">全部</option>
                                        <option value="true" ${ 'true' == request.isRented ? 'selected' : ''}>已被租赁</option>
                                        <option value="false" ${ 'false' == request.isRented ? 'selected' : ''}>未被租赁</option>
                                    </optgroup>
                                </select>
                            </div>
                        </div>
                        <div class="col-lg-4">
                            <div class="input-group">
                                <span class="input-group-addon">公寓名称</span>
                                <input class="form-control" type="text" name="name" value="${request.name}" placeholder="输入搜索的公寓名称">
                            </div>
                        </div>
                        <div class="col-lg-2">
                            <div class="input-group">
                                <button type="submit" class="btn btn-primary">查询</button>
                            </div>
                        </div>
                    </div>
                </form>
                <div class="box-operation">
                    <a href="flat/addPage" class="btn btn-primary pull-right">新建公寓</a>
                </div>
            </div>
            <div class="box-body">
                <table class="table table-bordered table-hover">
                    <tbody>
                        <tr>
                            <th>#</th>
                            <th>名称</th>
                            <th>租金/月</th>
                            <th>租赁人</th>
                            <th>租金缴纳</th>
                            <th>电费缴纳</th>
                            <th>创建时间</th>
                            <th class="center">操作</th>
                        </tr>
                    <c:forEach items="${flats}" var="flat" varStatus="itemStatus">
                        <tr>
                            <td>${itemStatus.count}</td>
                            <td>${flat.name}</td>
                            <td>${flat.monthPrice}</td>
                            <td>${flat.renterName}</td>
                            <td>
                                <span class="label ${flat.hasPaidRent == true ? 'label-success' : 'label-danger'}">
                                    ${flat.hasPaidRent == true ? '已缴纳' : '未缴纳'}
                                </span>
                                <c:if test="${flat.renterId >0 }">
                                    &nbsp;&nbsp;
                                    <a title="缴纳租金" href="flat/payRentPage?flatId=${flat.flatId}">缴纳租金</a>
                                </c:if>
                                    &nbsp;&nbsp;
                                    <a title="缴纳历史" href="flat/listRentpaymentHistory?flatId=${flat.flatId}">缴纳历史</a>
                            </td>
                            <td>
                                <span class="label ${flat.hasPaidMeter == true ? 'label-success' : 'label-danger'}">
                                    ${flat.hasPaidMeter == true ? '已缴纳' : '未缴纳'}
                                </span>
                                <c:if test="${flat.renterId >0 }">
                                    &nbsp;&nbsp;
                                    <a title="缴纳电费" href="flat/payMeterPage?flatId=${flat.flatId}">缴纳电费</a>
                                </c:if>
                                    &nbsp;&nbsp;
                                    <a title="缴纳历史" href="flat/listMeterpaymentHistory?flatId=${flat.flatId}">缴纳历史</a>
                            </td>
                            <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${flat.created}"/></td>
                            <td class="operation-part center">
                                <a title="编辑" href="flat/editPage?flatId=${flat.flatId}"><span class="glyphicon glyphicon-pencil"></span></a>
                                <a title="删除" class="delete-flat" data-flat="${flat.flatId}" href="javascript:void(0)"><span class="glyphicon glyphicon-trash"></span></a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="box-footer clearfix">
                <c:set var="url" value="flat/list" />
                <c:set var="condition" value="${condition}" />
                <%@ include file="../layout/pagination.jsp"%>
            </div>
        </div>
    </div>
</section>