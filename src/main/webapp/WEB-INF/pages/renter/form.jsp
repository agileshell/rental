<%@ page language="java" pageEncoding="UTF-8"
    contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="form_type" value="${renter.renterId > 0 ? '编辑租客' : '新建租客'}"/>
<head>
    <title>小李庄农贸管理系统</title>

    <script src="resources/renter/form.js"></script>
</head>

<!-- Bread line. -->
<section class="content-header">
    <h1>租客管理<small>${form_type}</small></h1>
    <ol class="breadcrumb">
        <li><a href="homePage"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li>租客管理</li>
        <li class="active">${form_type}</li>
    </ol>
</section>
<!-- Content. -->
<section class="content">
    <div class="row">
        <div class="box">
            <div class="box-header">
                <h3 class="box-title">${form_type}</h3>
            </div>
            <form role="form" autocomplete="off" action="${renter.renterId > 0 ? 'renter/edit' : 'renter/add'}" method="POST">
                <input type="hidden" name="renterId" value="${renter.renterId}" />
                <div class="box-body">
                    <div class="form-group">
                        <label for="name">姓名<span class="required-flag">*</span></label>
                        <div class="input-group">
                            <div class="input-group-addon">
                                <i class="fa fa-user"></i>
                            </div>
                            <input name="name" type="text" class="form-control" id="name" value="${renter.name}" placeholder="姓名">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="mobile">手机号码<span class="required-flag">*</span></label>
                        <div class="input-group">
                            <div class="input-group-addon">
                                <i class="fa fa-mobile"></i>
                            </div>
                            <input name="mobile" type="text" class="form-control" id="mobile" value="${renter.mobile}" placeholder="手机号码">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="idCard">身份证号码</label>
                        <div class="input-group">
                            <div class="input-group-addon">
                                <i class="fa fa-credit-card"></i>
                            </div>
                            <input name="idCard" type="text" class="form-control" id="idCard" value="${renter.idCard}" placeholder="身份证号码">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="comment">备注</label>
                        <div class="textarea">
                            <textarea class="form-control" id="description" name="comment" rows="6">${renter.comment}</textarea>
                        </div>
                    </div>
                </div>
                <div class="box-footer">
                    <div class="form-group">
                        <button type="submit" id="frm_submit" class="btn btn-primary">提交</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</section>