require(["../require-config"], function() {
    require(['jquery', 'validate'], function() {
        $('.delete-stall').on('click', function() {
            var stallId = $(this).attr('data-stall');
            if (confirm('确定要删除该摊位？')) {
                $.ajax({
                    url: 'stall/delete',
                    type: 'GET',
                    data: {
                        'stallId': stallId
                    },
                    dataType: 'json',
                    success: function(response) {
                        if ('succeed' == response.status) {
                            alert('删除成功');
                            window.location.href = 'stall/list';
                        } else {
                            if ('-1' == response.error_code) {
                                alert('删除失败，摊位不存在');
                            } else if ('-2' == response.error_code){
                                alert('删除失败，该摊位已被租赁，清先解除租赁关系');
                            } else {
                                alert('删除失败');
                            }
                        }
                    },
                    error: function() {
                        console.log('fail to delete stall');
                    }
                });
            }
        });

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
                            window.location.href = 'stall/list';
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