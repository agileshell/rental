<%@ page language="java" pageEncoding="UTF-8"
    contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
    <title>小李庄农贸管理系统</title>

    <script src="resources/flat/pay_rent.js"></script>
    <script src="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/"%>resources/libs/My97DatePicker/WdatePicker.js"></script>
</head>

<!-- Bread line. -->
<section class="content-header">
    <h1>公寓管理<small>缴纳租金</small></h1>
    <ol class="breadcrumb">
        <li><a href="homePage"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li>公寓管理</li>
        <li class="active">缴纳租金</li>
    </ol>
</section>
<!-- Content. -->
<section class="content">
    <div class="row">
        <div class="box">
            <div class="box-header">
                <h3 class="box-title">缴纳租金</h3>
            </div>
            <form role="form" autocomplete="off" action="flat/payRent" method="POST">
                <input type="hidden" name="flatId" value="${flatId}" />
                <div class="box-body">
                    <div class="form-group">
                        <label for="name">公寓名称</label>
                        <div class="input-group">
                            <div class="input-group-addon">
                                <i class="fa fa-edit"></i>
                            </div>
                            <input type="text" class="form-control" id="flatName" value="${flatName}" readonly="readonly">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="startDate">起始时间<span class="required-flag">*</span></label>
                        <div class="input-group">
                            <div class="input-group-addon">
                                <i class="fa fa-long-arrow-up"></i>
                            </div>
                            <input name="startDate" type="text" class="form-control" id="startDate" value="" onFocus="WdatePicker({startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endDate\');}'})">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="endDate">终止时间<span class="required-flag">*</span></label>
                        <div class="input-group">
                            <div class="input-group-addon">
                                <i class="fa fa-long-arrow-down"></i>
                            </div>
                            <input name="endDate" type="text" class="form-control" id="endDate" value="" onFocus="WdatePicker({startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startDate\');}'})">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="totalPrice">总金额<span class="required-flag">*</span></label>
                        <div class="input-group">
                            <div class="input-group-addon">
                                <i class="fa fa-money"></i>
                            </div>
                            <input name="totalPrice" type="text" class="form-control" id="totalPrice" value="${monthPrice}" placeholder="总金额">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="comment">备注</label>
                        <div class="textarea">
                            <textarea class="form-control" id="description" name="comment" rows="6"></textarea>
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