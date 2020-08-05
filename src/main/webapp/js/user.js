const url = "http://localhost:8081/user";

$('#add-user').on('hidden.bs.modal', function () {
	// 关闭后清除数据
	$('#add-user-username').val('');
	$('#add-user-email').val('');
	$('#add-user-password').val('');
})

// 添加用户
$('#add-user-btn').click(function () {
	let username = $('#add-user-username').val(), 
		email = $('#add-user-email').val(), 
		password = $('#add-user-password').val();
	if (!/^([\u4E00-\u9FA5]|\w){2,16}$/.test(username)) {
		alert('用户名格式:由中文,字母,数字,下划线组成;长度为2-16个字符');
	} else if (!/^\w+@\w+\.\w+$/.test(email)) {
		alert('邮箱格式不正确');
	} else if (!/^\w{6,16}$/.test(password)) {
		alert('密码格式:由字母,数字,下划线组成;长度为6-16个字符');
	} else {
		// 发送请求
		$.ajax({
			url: url,
			type: 'post',
			data: {
				username: username,
				password: password,
				email: email
			},
			dataType: 'json',
			success: function (e) {
			},
			error: function (e) {
				alert('找不到服务器T_T');
			},
			complete: function () {
				// 关闭模态框
				$('#add-user').modal('hide');
			}
		});

	}
});

// 获取用户id和用户名，用于修改用户资料
$('#modify-user').on('show.bs.modal	', function (e) {
	// 找到点击的按钮
	let clickBtn = e.relatedTarget;
	// 获取要修改的用户资料
	let id = clickBtn.attributes['data-id'].value;
	let username = clickBtn.attributes['data-username'].value;
	let email = clickBtn.attributes['data-email'].value;
	let password = clickBtn.attributes['data-password'].value;
	// 添加到输入框
	$('#modify-user-user-id').val(id);
	$('#modify-user-username').val(username);
	$('#modify-user-email').val(email);
	$('#modify-user-password').val(password);
});

// 清除数据
$('#modify-user').on('hidden.bs.modal', function () {
	$('#modify-user-email').val('');
	$('#modify-user-password').val('');
});

// 修改用户资料
$('#modify-user-btn').click(function () {
	let id = $('#modify-user-user-id').val(),
		email = $('#modify-user-email').val(),
		password = $('#modify-user-password').val();
	if (!/^\w+@\w+\.\w+$/.test(email)) {
		alert('邮箱格式不正确');
	} else if (!/^\w{6,16}$/.test(password)) {
		alert('密码格式:由字母,数字,下划线组成;长度为6-16个字符');
	} else {
		$.ajax({
			url: url + '?id=' + id + '&email=' + email + '&password=' + password,
			type: 'put',
			datatype: 'json',
			success: function (e) {
				if (e.code === 'ok') {
					alert("修改成功");
				} else {
					alert("修改失败,请稍后重试");
				}
			},
			error: function (e) {
				alert('找不到服务器T_T');
			},
			complete: function (e) {
				// 关闭模态框
				$('#modify-user').modal('hide');
			}
		});
	}
});

// 搜索用户
$('#search-user-btn').click(function (e) {
	let username = $('#search-user-username').val();
	if (!/^([\u4E00-\u9FA5]|\w){2,16}$/.test(username)) {
		alert('用户名格式:由中文,字母,数字,下划线组成;长度为2-16个字符');
	} else {
		// 跳转
		$(location).attr('href', url + '?q=' + username);
	}
});

$(document).ready(function () {
	// 删除用户
	$('.deleteUserBtn').click(function () {
		let del = confirm('你确定删除该用户吗?删除后无法恢复!'),
			id = $(this).attr('data-id');
		if (del) {
			$.ajax({
				url: url + '?id=' + id,
				type: 'delete',
				datatype: 'json',
				success: function (e) {
					if (e.code === 'ok') {
						alert('删除成功');
						// 删除行
						$('tr[data-id=\"'+ id + '\"]').remove();
					} else {
						alert('删除失败,请稍后重试');
					}
				},
				error: function (e) {
					alert('找不到服务器T_T');
				}
			});
		}
	});
})