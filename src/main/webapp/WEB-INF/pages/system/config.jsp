<%@ page language="java" pageEncoding="UTF-8"
    contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
    <title>小李庄农贸管理系统</title>

    <script src="resources/system/config.js"></script>
     <script src="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/"%>resources/libs/My97DatePicker/WdatePicker.js"></script>
</head>

<!-- Bread line. -->
<section class="content-header">
    <h1>系统设置<small>系统配置</small></h1>
    <ol class="breadcrumb">
        <li><a href="homePage"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li>系统设置</li>
        <li class="active">系统配置</li>
    </ol>
</section>
<!-- Content. -->
<section class="content">
    <div class="row">
        <div class="box">
            <div class="box-header">
                <h3 class="box-title">系统配置</h3>
            </div>
            <form role="form" autocomplete="off" action="system/config" method="POST">
                <div class="box-body">
                    <div class="form-group">
                        <label for="watermeter">水费/度<span class="required-flag">*</span></label>
                        <div class="input-group">
                            <div class="input-group-addon">
                                <i class="fa fa-tint"></i>
                            </div>
                            <input name="watermeter" type="text" class="form-control" id="watermeter" value="${watermeter}" placeholder="水费/度">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="watermeter">电费/度<span class="required-flag">*</span></label>
                        <div class="input-group">
                            <div class="input-group-addon">
                                <i class="fa fa-plug"></i>
                            </div>
                            <input name="meter" type="text" class="form-control" id="meter" value="${meter}" placeholder="电费/度">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="first_quarter_start">第一季度<span class="required-flag">*</span></label>
                        <div class="input-group" style="width: 60%;">
                            <div class="input-group-addon">
                                <i class="fa fa-long-arrow-up"></i>
                            </div>
                            <input name="first_quarter_start" type="text" class="form-control" id="first_quarter_start" value="${first_quarter_start}" placeholder="起始" onFocus="WdatePicker({startDate:'%y-%M-%d',dateFmt:'MM-dd'})">
                             <div class="input-group-addon">
                                <i class="fa fa-long-arrow-down"></i>
                            </div>
                            <input name="first_quarter_end" type="text" class="form-control" id="first_quarter_end" value="${first_quarter_end}" placeholder="终止" onFocus="WdatePicker({startDate:'%y-%M-%d',dateFmt:'MM-dd'})">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="second_quarter_start">第二季度<span class="required-flag">*</span></label>
                        <div class="input-group" style="width: 60%;">
                            <div class="input-group-addon">
                                <i class="fa fa-long-arrow-up"></i>
                            </div>
                            <input name="second_quarter_start" type="text" class="form-control" id="second_quarter_start" value="${second_quarter_start}" placeholder="起始" onFocus="WdatePicker({startDate:'%y-%M-%d',dateFmt:'MM-dd'})">
                             <div class="input-group-addon">
                                <i class="fa fa-long-arrow-down"></i>
                            </div>
                            <input name="second_quarter_end" type="text" class="form-control" id="second_quarter_end" value="${second_quarter_end}" placeholder="终止" onFocus="WdatePicker({startDate:'%y-%M-%d',dateFmt:'MM-dd'})">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="third_quarter_start">第三季度<span class="required-flag">*</span></label>
                        <div class="input-group" style="width: 60%;">
                            <div class="input-group-addon">
                                <i class="fa fa-long-arrow-up"></i>
                            </div>
                            <input name="third_quarter_start" type="text" class="form-control" id="third_quarter_start" value="${third_quarter_start}" placeholder="起始" onFocus="WdatePicker({startDate:'%y-%M-%d',dateFmt:'MM-dd'})">
                             <div class="input-group-addon">
                                <i class="fa fa-long-arrow-down"></i>
                            </div>
                            <input name="third_quarter_end" type="text" class="form-control" id="third_quarter_end" value="${third_quarter_end}" placeholder="终止" onFocus="WdatePicker({startDate:'%y-%M-%d',dateFmt:'MM-dd'})">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="fourth_quarter_start">第四季度<span class="required-flag">*</span></label>
                        <div class="input-group" style="width: 60%;">
                            <div class="input-group-addon">
                                <i class="fa fa-long-arrow-up"></i>
                            </div>
                            <input name="fourth_quarter_start" type="text" class="form-control" id="fourth_quarter_start" value="${fourth_quarter_start}" placeholder="起始" onFocus="WdatePicker({startDate:'%y-%M-%d',dateFmt:'MM-dd'})">
                             <div class="input-group-addon">
                                <i class="fa fa-long-arrow-down"></i>
                            </div>
                            <input name="fourth_quarter_end" type="text" class="form-control" id="fourth_quarter_end" value="${fourth_quarter_end}" placeholder="终止" onFocus="WdatePicker({startDate:'%y-%M-%d',dateFmt:'MM-dd'})">
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