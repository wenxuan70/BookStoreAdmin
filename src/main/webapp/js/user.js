const url = "http://localhost:8081";

// 加载
// $.ajax({
//   url: url + "/user",
//   type: 'get',
//   dataType: 'json',
//   success: function (e) {
//       let data = e.data, count = e.count, str = "";
//       console.log(e);
//       for (let i = 0; i < data.length; i++) {
//         str += "<tr data-id=\"" + data[i].id + "\">";
//         str += "<td><input type=\"checkbox\"/></td>";
//         str += "<td>" + data[i].id + "</td>";
//         str += "<td>" + data[i].username + "</td>";
//         str += "<td>" + data[i].password + "</td>";
//         str += "<td>" + data[i].email + "</td>";
//         str += "<td>" + data[i].createTime + "</td>";
//         str += "<td>";
//         str += "<button class=\"btn btn-primary btn-sm modify\" data-id=\""
// 		        + data[i].id
// 		        + "\" data-username=\""
// 		        + data[i].username
// 		        + "\" data-toggle=\"modal\" data-target=\"#modify-user\">修改</button>";
//         str += "<button class=\"btn btn-danger btn-sm deleteUserBtn\" data-id=\""+data[i].id+"\">删除</button>";
//         str += "</td>";
//         str += "</tr>";
//       }
//       $("tbody").html(str);
//   }
// });

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
	console.log(username,email,password,);
	if (!/^\w{6,16}$/.test(username)) {
		alert('用户名格式:由字母,数字,下划线组成;长度为6-16个字符');
	} else if (!/^\w+@\w+\.\w+$/.test(email)) {
		alert('邮箱格式不正确');
	} else if (!/^\w{6,16}$/.test(password)) {
		alert('密码格式:由字母,数字,下划线组成;长度为6-16个字符');
	} else {
		console.log('添加成功');
		// ...
		// 关闭模态框
		$('#add-user').modal('hide');
	}
});

// 获取用户id和用户名，用于修改用户资料
$('#modify-user').on('show.bs.modal	', function (e) {
	// 找到点击的按钮
	let clickBtn = e.relatedTarget;
	// 获取要修改的用户id和用户名
	let id = clickBtn.attributes['data-id'].value;
	let username = clickBtn.attributes['data-username'].value;
	// 添加到id框上
	$('#modify-user-user-id').val(id);
	// 添加用户名
	$('#modify-user-username').val(username);
});

// 清除数据
$('#modify-user').on('hidden.bs.modal', function () {
	$('#modify-user-email').val('');
	$('#modify-user-password').val('');
});

// 修改用户资料
$('#modify-user-btn').click(function () {
	let email = $('#modify-user-email').val(), 
		password = $('#modify-user-password').val();
	if (!/^\w+@\w+\.\w+$/.test(email)) {
		alert('邮箱格式不正确');
	} else if (!/^\w{6,16}$/.test(password)) {
		alert('密码格式:由字母,数字,下划线组成;长度为6-16个字符');
	} else {
		// ...
		console.log('修改成功');
		// 关闭模态框
		$('#modify-user').modal('hide');
	}
});

$(document).ready(function () {
	// 删除用户
	$('.deleteUserBtn').click(function () {
		let del = confirm('你确定删除该用户吗?删除后无法恢复!'),
			id = $(this).attr('data-id');
		if (del) {
			// 删除行
			$('tr[data-id=\"'+ id + '\"]').remove();
			console.log('删除成功');
		}
	});
})