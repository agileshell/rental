require(["../require-config"], function() {
    require(['jquery', 'validate'], function() {
        $('form').validate({
            rules: {
                name: {
                    required: true,
                    minlength: 2,
                },
                price: {
                    number: true,
                }
            },
            messages: {
                name: {
                    required: '名称不能为空',
                    minlength: "字符长度不能小于2个字符"
                },
                price: {
                    number: "只能输入数字"
                }
            },
            submitHandler: function(form) {
                form.submit();
            }
        });
    });
});