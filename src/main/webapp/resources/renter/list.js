require(["../require-config"], function() {
    require(['jquery', 'validate'], function() {
        $('.delete-renter').on('click', function() {
            var renterId = $(this).attr('data-renter');
            if (confirm('确定要删除该租客？')) {
                $.ajax({
                    url: 'renter/delete',
                    type: 'GET',
                    data: {
                        'renterId': renterId
                    },
                    dataType: 'json',
                    success: function(response) {
                        if ('succeed' == response.status) {
                            alert('删除成功');
                            window.location.href = 'renter/list';
                        } else {
                            if ('-1' == response.error_code) {
                                alert('删除失败，租客不存在');
                            } else if ('-2' == response.error_code){
                                alert('删除失败，该租客已经租赁摊位，清先解除租赁关系');
                            } else if ('-3' == response.error_code){
                                alert('删除失败，该租客已经租赁公寓，清先解除租赁关系');
                            } else {
                                alert('删除失败');
                            }
                        }
                    },
                    error: function() {
                        console.log('fail to delete renter');
                    }
                });
            }
        });
    });
});