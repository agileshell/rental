require(["../require-config"], function() {
    require(['jquery', 'validate'], function() {
        $('form').validate({
            rules: {
                name: {
                    required: true,
                    minlength: 2,
                },
                price: {
                    number: true
                },
                subareaId: {
                	required: true
                }
            },
            messages: {
                name: {
                    required: '名称不能为空',
                    minlength: "字符长度不能小于2个字符"
                },
                price: {
                    number: "只能输入数字"
                },
                subareaId: {
                    required: '区号不能为空'
                }
            },
            submitHandler: function(form) {
                $("#error").hide();
                $flag = true;
                if ($('#isCreateRenter').val() == true) {
                    if($("#renterName").val().trim() == "") {
                        $flag = false;
                        $("#error_info").html("租赁人姓名不能为空");
                        $("#error").show();
                        return;
                    }
                    if($("#renterMobile").val().trim() == "") {
                        $flag = false;
                        $("#error_info").html("租赁人手机号码不能为空");
                        $("#error").show();
                        return;
                    }
                }
                if($flag) {
                    $("#frm_submit").attr("disabled", true); 
                    form.submit();
                }
            }
        });
        $('#createRenterBtn').on('click', function() {
            $('#renterCreateMsg').remove();
            $('#renterMsg').show();
            $("#rentInfo").show();
            $('#isCreateRenter').val(true);
        });
    });
});