require(["../require-config"], function() {
    require(['jquery', 'validate'], function() {
        $('.confirmPay').on('click', function() {
            var statisticId = $(this).attr('data-statistic');
            var type = $(this).attr('data-type');
            var msg = '';
            if (type == 'rent') {
            	msg = '确定要已缴纳租金？';
            } else if (type == 'meter') {
            	msg = '确定要已缴纳电费？';
            } else if (type == 'water') {
            	msg = '确定要已缴纳水费？';
            }
            if (confirm(msg)) {
                $.ajax({
                    url: 'stall/confirmPay',
                    type: 'GET',
                    data: {
                        'statisticId': statisticId,
                        'type': type
                    },
                    dataType: 'json',
                    success: function(response) {
                        if ('succeed' == response.status) {
                            alert('确定缴纳成功');
                            window.location.href = 'stall/statistic';
                        } else {
                        	alert('确定缴纳失败');
                        }
                    },
                    error: function() {
                        console.log('fail to delete stall');
                    }
                });
            }
        });
    });
});