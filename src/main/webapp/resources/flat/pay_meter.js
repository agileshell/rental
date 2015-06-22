require(["../require-config"], function() {
    require(['jquery', 'validate'], function() {
        $.validator.addMethod("isNumber", function(value, element) {
            return this.optional(element) || /^[-\+]?\d+$/.test(value) || /^[-\+]?\d+(\.\d+)?$/.test(value);
        }, "匹配数值类型，包括整数和浮点数");

        $('form').validate({
            rules: {
                firstRecord: {
                    required: true
                },
                lastRecord: {
                    required: true
                },
                price: {
                    required: true,
                    isNumber: true,
                    min: 0
                },
                totalPrice: {
                    required: true,
                    number: true,
                    min: 0
                },
                recordDate: {
                    required: true
                }
            },
            messages: {
                firstRecord: {
                    required: '不能为空'
                },
                lastRecord: {
                    required: '不能为空'
                },
                price: {
                    required: '不能为空',
                    isNumber: "格式不正确",
                    min: '必须大于0'
                },
                totalPrice: {
                    required: '不能为空',
                    number: "只能输入数字",
                    min: '必须大于0'
                },
                recordDate: {
                    required: '不能为空'
                }
            },
            submitHandler: function(form) {
                form.submit();
            }
        });

        $('#firstRecord').on('change', function() {
            _calculate();
        });
        $('#lastRecord').on('change', function() {
            _calculate();
        });
        $('#price').on('change', function() {
            _calculate();
        });
        _calculate = function () {
            var firstRecord = $('#firstRecord').val(),
                lastRecord = $('#lastRecord').val(),
                price = $('#price').val();
            if ('' == firstRecord) {
            	firstRecord = 0;
                $('#firstRecord').val(0);
            }
            if ('' == lastRecord) {
            	lastRecord = 0;
                $('#lastRecord').val(0);
            }
            if ('' == price) {
            	price = 1.0;
                $('#price').val(1.0);
            }

            var diff = lastRecord - firstRecord;
            $('#totalPrice').val((diff * price).toFixed(0));
        }
    });
});