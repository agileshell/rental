require(["../require-config"], function() {
    require(['jquery', 'datatables'], function() {
        var _checkedObject,
        oTable,
        _autoLoad = function () {
            oTable = $('#renterTable').dataTable({
                    "bSort" : true, // 排序功能
                    "bFilter" : true, // 过滤功能
                    "bAutoWidth" : false, // 自动宽度
                    'bLengthChange' : true, // 改变每页显示数据数量
                    "bInfo" : true, // 页脚信息
                    "bJQueryUI" : false, // 是否开启主题
                    "bPaginate" : true, // 翻页功能
                    "iDisplayLength" : 10, // 每页显示10条数据
                    "sPaginationType" : "full_numbers", // 翻页样式设置
                    "oLanguage" : {
                        "sSearch" : "搜索:",
                        "sLengthMenu" : "每页显示 _MENU_ 条记录",
                        "sZeroRecords" : "没有检索到符合条件的数据",
                        "sInfo" : "当前第 _START_ - _END_ 条　共计 _TOTAL_ 条",
                        "sInfoEmpty" : "没有数据",
                        "sInfoFiltered" : "(从 _MAX_ 条记录中过滤)",
                        "sProcessing" : "正在加载数据...",
                        "oPaginate" : {
                            "sFirst" : "首页",
                            "sPrevious" : "上一页",
                            "sNext" : "下一页",
                            "sLast" : "尾页"
                        }
                    },
                    "aoColumnDefs" : [ {
                        "bSortable" : false,
                        "aTargets" : [ 0 ]
                    } ],
                    "fnDrawCallback": function () {
                        $("input[name='renter_id']").each(function () {
                            if (_checkedObject && (_checkedObject.val() != $(this).val())) {
                                $(this).prop("checked", false);
                            }
                        });
                    }
                });
        },
        _checked = function () {
            $("#renterTable").on("click", "input[name='renter_id']", function () {
                _checkedObject = $(this);
            });
        },
        _selectRenter = function () {
            $("#selectRenter").on("click", function() {
                var val = "";
                if (_checkedObject) {
                    val = _checkedObject.val();
                    var data = _checkedObject.attr("data");
                    $("#renterId").val(val);
                    $("#renterName").val(data);

                    $("#rentInfo").show();
                }
                $("#renterInfo").modal('hide');
            });
        },
       _clear = function () {
           $('#clearRenter').click(function (){
               if (_checkedObject) {
                   _checkedObject.prop("checked", false);
               }
               $("#renterId").val(0);
               $("#renterName").val("");

               $("#rentDate").val("");
               $("#unrentDate").val("");
               $("#initWatermeter").val("");
               $("#initMeter").val("");
               $("#rentComment").val("");

               $("#rentInfo").hide();

               _checkedObject = "";
               oTable.fnDraw();
           });
       };
       _clear();
       _checked();
       _autoLoad();
       _selectRenter();
    });
});
