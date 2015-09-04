require(["../require-config"], function() {
    require(['jquery', 'validate'], function() {
        $('form').validate({
            rules: {
                watermeter: {
                    required: true,
                    number: true,
                    min: 0
                },
                meter: {
                    required: true,
                    number: true,
                    min: 0
                }
            },
            messages: {
            	watermeter: {
                    required: '不能为空',
                    number: '格式不正确',
                    min: '必须大于0'
                },
                meter: {
                    required: '不能为空',
                    number: '格式不正确',
                    min: '必须大于0'
                }
            },
            submitHandler: function(form) {
                form.submit();
            }
        });
    });
});