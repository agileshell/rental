require(["../require-config"], function() {
    require(['jquery', 'validate'], function() {
        $('form').validate({
            rules: {
                startDate: {
                    required: true
                },
                 endDate: {
                    required: true
                },
                totalPrice: {
                    required: true,
                    number: true,
                    min: 0
                }
            },
            messages: {
                startDate: {
                    required: '不能为空'
                },
                endDate: {
                    required: '不能为空'
                },
                totalPrice: {
                    required: '不能为空',
                    number: "只能输入数字",
                    min: '必须大于0'
                }
            },
            submitHandler: function(form) {
                form.submit();
            }
        });
    });
});