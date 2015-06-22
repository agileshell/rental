<%@ page language="java" pageEncoding="UTF-8"
    contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="form_type" value="${stall.stallId > 0 ? '编辑摊位' : '新建摊位'}"/>
<head>
    <title>小李庄农贸管理系统</title>
    <script src="resources/stall/form.js"></script>
    <script src="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/"%>resources/libs/My97DatePicker/WdatePicker.js"></script>
    <link rel="stylesheet" href="resources/libs/jquery/datatables/css/jquery.dataTables.css">
    <script src="resources/renter/datatables.js"></script>
</head>

<!-- Bread line. -->
<section class="content-header">
    <h1>摊位管理<small>${form_type}</small></h1>
    <ol class="breadcrumb">
        <li><a href="homePage"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li>摊位管理</li>
        <li class="active">${form_type}</li>
    </ol>
</section>
<!-- Content. -->
<section class="content">
    <div class="row">
        <div id="error" style="display: none;">
            <div class="alert alert-danger">
                <h4>Warning!</h4>
                <span id="error_info"></span>
            </div>
        </div>
        <div class="box">
            <div class="box-header">
                <h3 class="box-title">${form_type}</h3>
            </div>
            <form role="form" autocomplete="off" action="${stall.stallId > 0 ? 'stall/edit' : 'stall/add'}" method="POST">
                <input type="hidden" name="stallId" value="${stall.stallId}" />
                <div class="box-body">
                    <div class="form-group">
                        <label for="name">区号<span class="required-flag">*</span></label>
                        <div class="input-group">
                            <div class="input-group-addon">
                                <i class="fa fa-bookmark"></i>
                            </div>
                            <select name="subareaId" class="form-control">
                                <optgroup label="请选择区号">
                                    <c:forEach items="${subareas}" var="subarea">
                                        <option value="${subarea.subareaId}" ${ stall.subarea.subareaId == subarea.subareaId ? 'selected' : ''}>${subarea.name}</option>
                                    </c:forEach>
                                </optgroup>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="name">名称<span class="required-flag">*</span></label>
                        <div class="input-group">
                            <div class="input-group-addon">
                                <i class="fa fa-edit"></i>
                            </div>
                            <input name="name" type="text" class="form-control" id="name" value="${stall.name}" placeholder="名称">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="monthPrice">租金/月</label>
                        <div class="input-group">
                            <div class="input-group-addon">
                                <i class="fa fa-money"></i>
                            </div>
                            <input name="monthPrice" type="text" class="form-control" id="monthPrice" value="${stall.monthPrice}" placeholder="租金/月">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="comment">摊位备注</label>
                        <div class="textarea">
                            <textarea class="form-control" id="description" name="comment" rows="6">${stall.comment}</textarea>
                        </div>
                    </div>
                    <input name="renterId" type="hidden" id="renterId" value="${stall.renterId}">
                    <div class="form-group" id="renterCreateMsg">
                        <label for="renterId">租赁人</label>
                        <div class="input-group">
                            <div class="input-group-addon">
                                <i class="fa fa-user"></i>
                            </div>
                            <input type="text" class="form-control" id="renterName" value="${stall.stallRenter.renterName}" readonly="readonly">
                            <a href="#renterInfo" role="button" class="btn btn-primary" data-toggle="modal">选择租赁人</a>
                          <c:if test="${stall.renterId == 0}">
                            <a href="javascript:void();" id="createRenterBtn" role="button" class="btn btn-primary">创建租赁人</a>
                          </c:if>
                        </div>
                    </div>
                    <input type="hidden" id="isCreateRenter" value="false">
                    <div id="renterMsg" style="display:none">
                        <div class="form-group">
                            <label for="renterName">租赁人<span class="required-flag">*</span></label>
                            <div class="input-group">
                                <div class="input-group-addon">
                                    <i class="fa fa-user"></i>
                                </div>
                                <input name="renter.name" type="text" class="form-control" id="renterName" placeholder="姓名">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="renterMobile">手机号码<span class="required-flag">*</span></label>
                            <div class="input-group">
                                <div class="input-group-addon">
                                    <i class="fa fa-mobile"></i>
                                </div>
                                <input name="renter.mobile" type="text" class="form-control" id="renterMobile" placeholder="手机号码">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="renterIdCard">身份证号码</label>
                            <div class="input-group">
                                <div class="input-group-addon">
                                    <i class="fa fa-credit-card"></i>
                                </div>
                                <input name="renter.idCard" type="text" class="form-control" id="renterIdCard" placeholder="身份证号码">
                            </div>
                        </div>
                    </div>
                    <div id="rentInfo" style="${stall.renterId > 0 ? '' : 'display:none'}">
                        <div class="form-group">
                            <label for="rentDate">起始时间</label>
                            <div class="input-group">
                                <div class="input-group-addon">
                                    <i class="fa fa-long-arrow-up"></i>
                                </div>
                                <input name="rentDate" type="text" class="form-control" id="rentDate" value="${stall.stallRenter.rentDate}" onFocus="WdatePicker({startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'unrentDate\');}'})">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="unrentDate">终止时间</label>
                            <div class="input-group">
                                <div class="input-group-addon">
                                    <i class="fa fa-long-arrow-down"></i>
                                </div>
                                <input name="unrentDate" type="text" class="form-control" id="unrentDate" value="${stall.stallRenter.unrentDate}" onFocus="WdatePicker({startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'rentDate\');}'})">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="initWatermeter">水费起始值</label>
                            <div class="input-group">
                                <div class="input-group-addon">
                                    <i class="fa fa-tint"></i>
                                </div>
                                <input name="initWatermeter" type="text" class="form-control" id="initWatermeter" value="${stall.stallRenter.initWatermeter}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="initMeter">电费起始值</label>
                            <div class="input-group">
                                <div class="input-group-addon">
                                    <i class="fa fa-plug"></i>
                                </div>
                                <input name="initMeter" type="text" class="form-control" id="initMeter" value="${stall.stallRenter.initMeter}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="rentComment">租赁备注</label>
                            <div class="textarea">
                                <textarea class="form-control" id="rentComment" name="rentComment" rows="6">${stall.stallRenter.comment}</textarea>
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