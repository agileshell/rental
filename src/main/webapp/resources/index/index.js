require(["../require-config"], function() {
    require(['jquery', 'validate'], function() {
        $('#account').focus();
        $('form').validate({
            rules: {
                account: {
                    required: true
                },
                password: {
                    required: true
                }
            },
            messages: {
                account: {
                    required: '账户不能为空'
                },
                password: {
                    required: '密码不能为空'
                }
            }
        });
        var signin = function() {
            if ($('form').valid()) {
                $('form').submit();
            }
        };
        $('#signIn').on('click', function () {
            signin();
        });
        $(document).keydown(function(e) {
            if (e.keyCode == 13) {
                signin();
            }
        });
    });
});