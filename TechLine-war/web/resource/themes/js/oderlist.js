function  searchOMbyDate($date) {
    window.console.log($date);
    $.post('searchOrderList', {dateO: $date}, function($oDMList) {
        window.console.log($oDMList);
        var tableOM = $('.oMView');
        $(tableOM).empty();
        $.each($oDMList, function(key, oderMaster) {
            var tr = $('<tr><td></td><td></td><td></td><td></td><td></td></tr>');
            $(tr).children().eq(0).children().addClass('order-number').append('<a href="OrderInfor?id=' + oderMaster[0] + '">' + oderMaster[0] + '</a>');
            $date = null;
            $date = new Date(oderMaster[1]);
            $(tr).children().eq(1).children().append(('0' + $date.getDate()).slice(-2) + '/' + ('0' + ($date.getMonth() + 1)).slice(-2) + '/' + $date.getFullYear() + " " + ('0' + $date.getHours()).slice(-2) + ':' + ('0' + $date.getMinutes()).slice(-2));
            $(tr).children().eq(2).children().append(oderMaster[2]);
            $(tr).children().eq(4).children().append('$' + oderMaster[3].toFixed(1));
            tr.appendTo(tableOM);
        });
    });

}