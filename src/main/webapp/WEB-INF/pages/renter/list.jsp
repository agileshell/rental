<%@ page language="java" pageEncoding="UTF-8"
    contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<head>
    <title>小李庄农贸管理系统</title>

    <script src="resources/renter/list.js"></script>
</head>

<!-- Bread line. -->
<section class="content-header">
    <h1>租客管理<small>租客列表</small></h1>
    <ol class="breadcrumb">
        <li><a href="homePage"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li>租客管理</li>
        <li class="active">租客列表</li>
    </ol>
</section>
<!-- Content. -->
<section class="content">
    <div class="row">
        <div class="box">
            <div class="box-header">
                <form role="form" action="renter/list" method="get">
                    <div class="box-search container">
                        <div class="col-lg-6">
                            <div class="input-group">
                                <span class="input-group-addon">租客姓名</span>
                                <input class="form-control" type="text" name="name" value="${request.name}" placeholder="输入搜索的租客姓名">
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
                    <a href="renter/addPage" class="btn btn-primary pull-right">新建租客</a>
                </div>
            </div>
            <div class="box-body">
                <table class="table table-bordered table-hover">
                    <tbody>
                        <tr>
                            <th>#</th>
                            <th>姓名</th>
                            <th>手机号码</th>
                            <th>身份证号码</th>
                            <th>创建时间</th>
                            <th class="center">操作</th>
                        </tr>
                    <c:forEach items="${renters}" var="renter" varStatus="itemStatus">
                        <tr>
                            <td>${itemStatus.count}</td>
                            <td>${renter.name}</td>
                            <td>${renter.mobile}</td>
                            <td>${renter.idCard}</td>
                            <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${renter.created}"/></td>
                            <td class="operation-part center">
                                <a title="编辑" href="renter/editPage?renterId=${renter.renterId}"><span class="glyphicon glyphicon-pencil"></span></a>
                                <a title="删除" class="delete-renter" data-renter="${renter.renterId}" href="javascript:void(0)"><span class="glyphicon glyphicon-trash"></span></a>
                                &nbsp;&nbsp;
                                <a href="stall/list?renterId=${renter.renterId}">租赁的摊位</a>&nbsp;&nbsp;
                                <a href="flat/list?renterId=${renter.renterId}">租赁的公寓</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="box-footer clearfix">
                <c:set var="url" value="renter/list" />
                <c:set var="condition" value="${condition}" />
                <%@ include file="../layout/pagination.jsp"%>
            </div>
        </div>
    </div>
</section>
