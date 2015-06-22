<%@ page language="java" pageEncoding="UTF-8"
    contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="form_type" value="${subarea.subareaId > 0 ? '编辑区号' : '新建区号'}"/>
<head>
    <title>小李庄农贸管理系统</title>

    <script src="resources/subarea/form.js"></script>
</head>

<!-- Bread line. -->
<section class="content-header">
    <h1>区号管理<small>${form_type}</small></h1>
    <ol class="breadcrumb">
        <li><a href="homePage"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li>区号管理</li>
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
            <form role="form" autocomplete="off" action="${subarea.subareaId > 0 ? 'subarea/edit' : 'subarea/add'}" method="POST">
                <input type="hidden" name="subareaId" value="${subarea.subareaId}" />
                <div class="box-body">
                    <div class="form-group">
                        <label for="name">区号<span class="required-flag">*</span></label>
                        <div class="input-group">
                            <div class="input-group-addon">
                                <i class="fa fa-edit"></i>
                            </div>
                            <input name="name" type="text" class="form-control" id="name" value="${subarea.name}" placeholder="区号">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="comment">备注</label>
                        <div class="textarea">
                            <textarea class="form-control" id="description" name="comment" rows="6">${subarea.comment}</textarea>
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