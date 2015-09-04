require(["../require-config"], function() {
    require(['jquery', 'validate'], function() {
        $('#name').focus();
        $('form').validate({
            rules: {
                name: {
                    required: true,
                    minlength: 2,
                    maxlength: 64
                }
            },
            messages: {
                name: {
                    required: '区号不能为空',
                    minlength: "字符长度不能小于2个字符",
                    maxlength: "字符长度不能大于64个字符"
                }
            },
            submitHandler: function(form) {
                form.submit();
            }
        });
    });
});