<%@ page language="java" pageEncoding="UTF-8"
    contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<head>
    <title>小李庄农贸管理系统</title>

    <script src="resources/stall/list.js"></script>
</head>

<!-- Bread line. -->
<section class="content-header">
    <h1>摊位管理<small>摊位列表</small></h1>
    <ol class="breadcrumb">
        <li><a href="homePage"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li>摊位管理</li>
        <li class="active">摊位列表</li>
    </ol>
</section>
<!-- Content. -->
<section class="content">
    <div class="row">
        <div class="box">
            <div class="box-header">
                <form role="form" action="stall/list" method="get">
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
                        <div class="col-lg-2">
                            <div class="input-group">
                                <span class="input-group-addon">区号</span>
                                <select name="subareaId" class="form-control">
                                    <optgroup label="请选择区号">
                                        <option value="">全部</option>
                                      <c:forEach items="${subareas}" var="subarea">
                                        <option value="${subarea.subareaId}" ${ subarea.subareaId == request.subareaId ? 'selected' : ''}>${subarea.name}</option>
                                      </c:forEach>
                                    </optgroup>
                                </select>
                            </div>
                        </div>
                        <div class="col-lg-3">
                            <div class="input-group">
                                <span class="input-group-addon">摊位名称</span>
                                <input class="form-control" type="text" name="name" value="${request.name}" placeholder="输入搜索的摊位名称">
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
                    <a href="stall/addPage" class="btn btn-primary pull-right">新建摊位</a>
                </div>
            </div>
            <div class="box-body">
                <table class="table table-bordered table-hover">
                    <tbody>
                        <tr>
                            <th>#</th>
                            <th>摊位号</th>
                            <th>租金/月</th>
                            <th>租赁人</th>
                            <th>租金缴纳</th>
                            <th>水费缴纳</th>
                            <th>电费缴纳</th>
                            <th>创建时间</th>
                            <th class="center">操作</th>
                        </tr>
                    <c:forEach items="${stalls}" var="stall" varStatus="itemStatus">
                        <tr>
                            <td>${itemStatus.count}</td>
                            <td>${stall.subarea} ${stall.name}</td>
                            <td>${stall.monthPrice}</td>
                            <td>${stall.renterName}</td>
                            <td>
                                <span class="label ${stall.hasPaidRent == true ? 'label-success' : 'label-danger'}">
                                    ${stall.hasPaidRent == true ? '已缴纳' : '未缴纳'}
                                </span>
                                <c:if test="${stall.renterId >0 }">
                                    &nbsp;&nbsp;
                                    <a title="缴纳租金" href="stall/payRentPage?stallId=${stall.stallId}">缴纳租金</a>
                                </c:if>
                                    &nbsp;&nbsp;
                                    <a title="缴纳历史" href="stall/listRentpaymentHistory?stallId=${stall.stallId}">缴纳历史</a>
                            </td>
                            <td>
                                <span class="label ${stall.hasPaidWatermeter == true ? 'label-success' : 'label-danger'}">
                                    ${stall.hasPaidWatermeter == true ? '已缴纳' : '未缴纳'}
                                </span>
                                <c:if test="${stall.renterId >0 }">
                                    &nbsp;&nbsp;
                                    <a title="缴纳水费" href="stall/payWatermeterPage?stallId=${stall.stallId}">缴纳水费</a>
                                </c:if>
                                    &nbsp;&nbsp;
                                    <a title="缴纳历史" href="stall/listWaterpaymentHistory?stallId=${stall.stallId}">缴纳历史</a>
                            </td>
                            <td>
                                <span class="label ${stall.hasPaidMeter == true ? 'label-success' : 'label-danger'}">
                                    ${stall.hasPaidMeter == true ? '已缴纳' : '未缴纳'}
                                </span>
                                <c:if test="${stall.renterId >0 }">
                                    &nbsp;&nbsp;
                                    <a title="缴纳电费" href="stall/payMeterPage?stallId=${stall.stallId}">缴纳电费</a>
                                </c:if>
                                    &nbsp;&nbsp;
                                    <a title="缴纳历史" href="stall/listMeterpaymentHistory?stallId=${stall.stallId}">缴纳历史</a>
                            </td>
                            <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${stall.created}"/></td>
                            <td class="operation-part center">
                                <a title="编辑" href="stall/editPage?stallId=${stall.stallId}"><span class="glyphicon glyphicon-pencil"></span></a>
                                <a title="删除" class="delete-stall" data-stall="${stall.stallId}" href="javascript:void(0)"><span class="glyphicon glyphicon-trash"></span></a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="box-footer clearfix">
                <c:set var="url" value="stall/list" />
                <c:set var="condition" value="${condition}" />
                <%@ include file="../layout/pagination.jsp"%>
            </div>
        </div>
    </div>
</section>