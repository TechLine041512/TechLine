function loadDay() {
    Year = document.getElementById('ddlYear');
    Month = document.getElementById('ddlMonth');
    Day = document.getElementById('ddlDay');
    Day.length = 0;
    // parseInt: chuyen kieu Month.value tu String sang Int
    var mon = parseInt(Month.options[Month.selectedIndex].value);
    // Dat bien SoDay de lam gia tri cuoi cho dong lap phat sinh ngay
    var SoDay = 30;
    // Thuc hien cac dong lenh sau dua tren viec so sanh gia tri Month
    switch (mon) {
        // Truong hop thang 2
        case 2:
            // Lay gia tri Year dang duoc chon trong ddlYear
            var gtYear = parseInt(Year.options[Year.selectedIndex].value);
            // Thuat toan tinh nam nhuan
            if ((gtYear % 4 == 0) && ((gtYear % 100 != 0) || (gtYear % 400 == 0))) {
                // La nam nhuan
                SoDay = 28;
            }
            else {
                // Khong la nam nhuan
                SoDay = 29;
            }
            break;
            // Truong hop cac thang 1, 3, 5, 7, 8, 10, 12
        case 1:
        case 3:
        case 5:
        case 7:
        case 8:
        case 10:
        case 12:
            SoDay = 31;
            break;
            // Truong hop cac thang 4, 6, 9, 11
        case 4:
        case 6:
        case 9:
        case 11:
            SoDay = 30;
            break;
        default :
            SoDay=30;
    }
    // Cho vong lap chay tu 1 den SoDay o tren
    for (var iDay = 1; iDay <= SoDay; iDay++) {
        var optDay = document.createElement('option');
        optDay.text = iDay;
        optDay.value = iDay;
        Day.options.add(optDay);
    }
}

function validatePass() {
    var newPass = document.getElementById('newPass').value;
    var confirmPass = document.getElementById('confirmPass').value;
    if (newPass != confirmPass) {
        alert('Confirm Password does not match New Password');
        return false;
    }
    return true;
}