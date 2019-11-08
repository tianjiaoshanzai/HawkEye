

function to_Gister() {
    $(".login").animate({opacity: '0'}, "slow");
    $(".login").css("display", "none");
    $(".register").css("display", "block");
    $(".register").animate({opacity: '1'}, "slow")
}

function to_Login() {
    $(".register").animate({opacity: '0'}, "slow");
    $(".register").css("display", "none");
    $(".login").css("display", "block");
    $(".login").animate({opacity: '1'}, "slow")
}

function check() {
    if ($("#r_Username").val() == "") {
        $(".erro_3").text("�û�������Ϊ��");
        return false;
    }//�û����п�

    if ($("#r_Psw").val().length > 18 || $("#r_Psw").val().length < 6) {
        $(".erro_4").text("������6~18λ����");
        return false;
    }//6~18λ����

    if ($("#r_Psw").val() == "") {
        $(".erro_4").text("���벻��Ϊ��");
        return false;
    }//����Ϊ��


    else if ($("#r_Psw").val() != $("#r_Psw2").val()) {
        $(".erro_4").text("��������������벻һ��");
    }//���벻Ϊ�ա������������벻��ͬ

    return true;
}

function login() {
	var userName = $('#username').val()
	var password = $("#psw").val()

    var user = {
        "id": 0,
        "password": password,
        "userName": userName
    }

    $.ajax({
        type: "POST",
        url: "/users/login",
        contentType: "application/json",
        data : JSON.stringify(user),
        success:function(data) {
            if (data.state == 0) {
                window.location.href = "/html/index.html"
            } else {
                alert(data.message)
            }
        }
    });
}


function register() {
    console.log("register")
    if (!check()) {
        return
    }

    var userName = $('#r_Username').val()
    var password = $("#r_Psw").val()

    var user = {
        "id": 0,
        "password": password,
        "userName": userName
    }

    console.log(user)

    $.ajax({
        type: "POST",
        url: "/users/",
        contentType: "application/json",
        data : JSON.stringify(user),
        dataType : 'json',
        success:function(data) {
            console.log(data)
            if (data.state == 0) {
                location.reload();

            }else {
                alert(data.message)
            }
        }
    });
}
