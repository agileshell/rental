require(["../require-config"], function() {
    require(['jquery', 'validate'], function() {
        $.validator.addMethod("isMobile", function(value, element) {
            var length = value.length;
            var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/;
            return this.optional(element) || (length == 11 && mobile.test(value));
        }, "请正确填写您的手机号码");

        $('#name').focus();
        $('form').validate({
            rules: {
                name: {
                    required: true,
                    minlength: 2,
                    maxlength: 64
                },
                mobile: {
                    required: false,
                    isMobile : true
                },
            },
            messages: {
                name: {
                    required: '姓名不能为空',
                    minlength: "字符长度不能小于2个字符",
                    maxlength: "字符长度不能大于64个字符"
                },
                mobile: {
                	isMobile: "手机号码格式不正确"
                }
            },
            submitHandler: function(form) {
                form.submit();
            }
        });
    });
});