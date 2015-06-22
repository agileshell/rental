<%@ page language="java" pageEncoding="UTF-8"
    contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="form_type" value="${flat.flatId > 0 ? '编辑公寓' : '新建公寓'}"/>
<head>
    <title>小李庄农贸管理系统</title>
    <script src="resources/flat/form.js"></script>
    <script src="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/"%>resources/libs/My97DatePicker/WdatePicker.js"></script>
    <link rel="stylesheet" href="resources/libs/jquery/datatables/css/jquery.dataTables.css">
    <script src="resources/renter/datatables.js"></script>
</head>

<!-- Bread line. -->
<section class="content-header">
    <h1>公寓管理<small>${form_type}</small></h1>
    <ol class="breadcrumb">
        <li><a href="homePage"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li>公寓管理</li>
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
            <form role="form" autocomplete="off" action="${flat.flatId > 0 ? 'flat/edit' : 'flat/add'}" method="POST">
                <input type="hidden" name="flatId" value="${flat.flatId}" />
                <div class="box-body">
                    <div class="form-group">
                        <label for="name">名称<span class="required-flag">*</span></label>
                        <div class="input-group">
                            <div class="input-group-addon">
                                <i class="fa fa-edit"></i>
                            </div>
                            <input name="name" type="text" class="form-control" id="name" value="${flat.name}" placeholder="名称">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="monthPrice">租金/月</label>
                        <div class="input-group">
                            <div class="input-group-addon">
                                <i class="fa fa-money"></i>
                            </div>
                            <input name="monthPrice" type="text" class="form-control" id="monthPrice" value="${flat.monthPrice}" placeholder="租金/月">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="comment">备注</label>
                        <div class="textarea">
                            <textarea class="form-control" id="description" name="comment" rows="6">${flat.comment}</textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="renterId">租赁人</label>
                        <div class="input-group">
                            <div class="input-group-addon">
                                <i class="fa fa-user"></i>
                            </div>
                            <input name="renterId" type="hidden" id="renterId" value="${flat.renterId}">
                            <input type="text" class="form-control" id="renterName" value="${flat.flatRenter.renterName}" readonly="readonly">
                            <a href="#renterInfo" role="button" class="btn btn-primary" data-toggle="modal">选择租赁人</a>
                        </div>
                    </div>
                    <div id="rentInfo" style="${flat.renterId > 0 ? '' : 'display:none'}">
	                    <div class="form-group">
	                        <label for="rentDate">起始时间</label>
	                        <div class="input-group">
	                            <div class="input-group-addon">
	                                <i class="fa fa-long-arrow-up"></i>
	                            </div>
	                            <input name="rentDate" type="text" class="form-control" id="rentDate" value="${flat.flatRenter.rentDate}" onFocus="WdatePicker({startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'unrentDate\');}'})">
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="unrentDate">终止时间</label>
	                        <div class="input-group">
	                            <div class="input-group-addon">
	                                <i class="fa fa-long-arrow-down"></i>
	                            </div>
	                            <input name="unrentDate" type="text" class="form-control" id="unrentDate" value="${flat.flatRenter.unrentDate}" onFocus="WdatePicker({startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'rentDate\');}'})">
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="initMeter">电费起始值</label>
	                        <div class="input-group">
	                            <div class="input-group-addon">
	                                <i class="fa fa-plug"></i>
	                            </div>
	                            <input name="initMeter" type="text" class="form-control" id="initMeter" value="${flat.flatRenter.initMeter}">
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="rentComment">备注</label>
	                        <div class="textarea">
	                            <textarea class="form-control" id="rentComment" name="rentComment" rows="6">${flat.flatRenter.comment}</textarea>
	                        </div>
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
<div class="modal fade" id="renterInfo" tabindex="-1" role="dialog" aria-labelledby="renterInfoLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="renterInfoLabel">选择租赁人</h4>
            </div>
            <div class="modal-body">
                <table cellpadding="0" cellspacing="0" border="0" class="display" id="renterTable">
                    <thead>
                        <tr>
                            <th></th>
                            <th>姓名</th>
                            <th>手机号码</th>
                        </tr>
                    </thead>
                    <tbody>
                         <c:forEach items="${renters}" var="renter" varStatus="itemStatus">
                         <tr class="gradeA">
                            <td>
                                 <input id="renter_${renter.renterId}" type="radio" name="renter_id" value="${renter.renterId}" data="${renter.name}" ${renter.renterId == stall.renterId ? 'checked' : ''}/>
                            </td>
                            <td><label for="renter_${renter.renterId}">${renter.name}</label></td>
                            <td><label for="renter_${renter.renterId}">${renter.mobile}</label></td>
                        </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="clearRenter">清除</button>
                <button type="button" class="btn btn-primary" id="selectRenter">选择</button>
            </div>
        </div>
    </div>
</div>