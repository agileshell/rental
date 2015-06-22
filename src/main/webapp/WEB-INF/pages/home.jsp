<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<head>
    <title>小李庄农贸管理系统</title>
	<style type="text/css">
	.statistic-block {
		margin: 20px 0 0;
	}
	.statistic-block {
		padding: 0 75px;
	}
	#welcome-index-welcome .statistic {
		margin: 20px;
		padding: 15px;
		text-align: center;
		min-width: 200px;
	}
	#welcome-index-welcome h3 {
		margin: 0;
		height: 38px;
		color: #3c8dbc;
		line-height: 38px;
	}
	#welcome-index-welcome .value {
		font-size: 32px;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
		margin: 0;
		padding: 5px 0;
		line-height: 1;
		color: #3c8dbc;
	}
	</style>
</head>

<!-- Bread line. -->
<section class="content-header">
    <h1>控制面板<small>Control panel</small></h1>
    <ol class="breadcrumb">
        <li><a href="homePage"><i class="fa fa-dashboard"></i> Home</a></li>
        <li class="active">控制面板</li>
    </ol>
</section>
<!-- Content. -->
<section class="content">
    <div id="welcome-index-welcome">
        <div class="row statistic-block">
            <div class="col-xs-3">
                <div class="statistic well">
                    <h3 class="title">摊位总数</h3>
                    <p class="value" title="总数${stallTotal}/已租${stallHasRent}">
                        <a href="stall/list" >${stallTotal}-${stallHasRent}</a>
                    </p>
                </div>
            </div>
            <div class="col-xs-3">
                <div class="statistic well">
                    <h3 class="title">公寓总数</h3>
                    <p class="value" title="总数${flatTotal}/已租${flatHasRent}">
                        <a href="flat/list" >${flatTotal}-${flatHasRent}</a>
                    </p>
                </div>
            </div>
            <div class="col-xs-3">
                <div class="statistic well">
                    <h3 class="title">租客总数</h3>
                    <p class="value" title="${renterTotal}">
                        <a href="renter/list" title="租客管理">${renterTotal}</a>
                    </p>
                </div>
            </div>
        </div>
    </div>
</section>
