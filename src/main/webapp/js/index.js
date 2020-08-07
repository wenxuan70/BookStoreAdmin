// 切换侧边栏激活状态
function switchActive(node) {
    $('.active').removeClass('active');
    switch (node) {
        case 'user':
            $('#user-btn').parent().addClass('active');
            break;
        case 'book':
            $('#book-btn').parent().addClass('active');
            break;
        case 'order':
            $('#order-btn').parent().addClass('active');
            break;
    };
}

$('#user-btn').click(function () {
    switchActive('user');
    $('#show-data').attr('src', '/user');
});

$('#book-btn').click(function () {
    switchActive('book');
    $('#show-data').attr('src', '/book');
});

$('#order-btn').click(function (e) {
    switchActive('order');
    $('#show-data').attr('src', '/order');
});

$('#user-info-btn').click(function () {
    $('#show-data').attr('src', '/info');
});