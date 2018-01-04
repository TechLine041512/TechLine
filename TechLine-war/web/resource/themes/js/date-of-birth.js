// Khai bao 3 bien toan cuc, de co the dung trong cac ham con
var Year = document.getElementById('ddlYear');;
var Month = document.getElementById('ddlMonth');;
var Day;
//Called when the page is loaded
function loadDoB() {
    loadYear();
    loadMonth();
    loadDay();
}
// Do du lieu vao ddl Year
function loadYear() {
    // Gan bien Year thanh tag <select> co id ddlYear
    Year = document.getElementById('ddlYear');
    // Reset lai kich thuoc cua ddlYear
    // Tranh truong hop bi du lieu rac moi khi goi ham
    Year.length = 0;
    // Khai bao bien cuc bo dung de chay vong for
    var iYear = 0;
    // Khai bao bien today kieu Date
    var today = new Date();
    // Cho vong for lap tu 1950 cho den nam hien hanh
    for (iYear = 1950; iYear <= today.getFullYear() - 1; iYear++) {
        // Khai bao va gan bien optYear la kieu du lieu tag <option>
        var optYear = document.createElement('option');
        // Gan thuoc tinh cho tag <option> ten optYear
        // Text: Du lieu hien thi tren ddl
        optYear.text = iYear;
        // Value: Gia tri cua du lieu tren
        optYear.value = iYear;
        // Them tag <option> ten optYear vao tag <select> ten Year
        Year.options.add(optYear);
    }
}
// Load Month
function loadMonth() {
    Month = document.getElementById('ddlMonth');
    Month.length = 0;
    var iMonth = 0;
    for (iMonth = 1; iMonth <= 12; iMonth++) {
        var optMonth = document.createElement('option');
        optMonth.text = iMonth;
        optMonth.value = iMonth;
        Month.options.add(optMonth);
    }
}
// Load Day
function loadDay() {
    Day = document.getElementById('ddlDay');
    Day.length = 0;
    // parseInt: chuyen kieu Month.value tu String sang Int
    var value = parseInt(Month.value);
    // Dat bien SoDay de lam gia tri cuoi cho dong lap phat sinh ngay
    var SoDay = 0;
    // Thuc hien cac dong lenh sau dua tren viec so sanh gia tri Month
    switch (value) {
        // Truong hop thang 2
        case 2:
            // Lay gia tri Year dang duoc chon trong ddlYear
            var gtYear = Year.selectedIndex;
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
    var iDay = 0;
    // Cho vong lap chay tu 1 den SoDay o tren
    for (iDay = 1; iDay <= SoDay; iDay++) {
        var optDay = document.createElement('option');
        optDay.text = iDay;
        optDay.value = iDay;
        Day.options.add(optDay);
    }
}
function validateEmail() {
    var emailV = document.getElementById('cusEmailEdit');
    var regexE = /^[0-9A-Za-z]+[0-9A-Za-z_]*@[\w\d.]+.\w{2,4}$/;
    if (!regexE.test(emailV.value)) {
        alert('Please enter a valid email!\nExample@gmail.com');
        emailV.focus;
        return false;
    } else {
        return true;
    }
}