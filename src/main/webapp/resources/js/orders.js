$(document).ready(function() {

    $('#startDate, #endDate, #start, #end').datetimepicker({
        format : "yyyy-mm-dd hh:ii",
        autoclose : true,
        todayBtn : true,
        minuteStep : 15,
        pickerPosition : "bottom-left"
    });

    $("#orders").DataTable({
        "oLanguage" : {
            "oPaginate" : {
                "sNext" : ">",
                "sPrevious" : "<",
                "sLast" : ">|",
                "sFirst" : "|<"
            },
        },
        "fnDrawCallback" : function(settings) {
            $('.del').click(function() {
                if (!confirm('Do you really want to delete this order?')) {
                    return;
                }
                var btn = $(this);
                $.get('/xiche/orders/del/' + $(this).closest('tr').find('td:eq(0)').html()).done(function(data) {
                    btn.closest('tr').remove();
                });
            });
            $('.edit').click(function() {
                $(this).attr('href', '/xiche/orders/order/' + $(this).closest('tr').find('td:eq(0)').html());
            });
        },
        "columnDefs" : [ {
            orderable : false,
            targets : [ 11, 12 ]
        } ],
        "bDestroy" : true,
        "autoWidth" : true,
        "iDisplayLength" : 10
    });

    $('#popupOk').click(function() {
        $('#orderForm').validate();
        if ($('#orderForm').valid()) {
            $.ajax({
                url : '/xiche/emp',
                type : 'post',
                dataType : 'json',
                data : $('#orderForm').serialize(),
                success : function(data) {
                    var res;
                    if (data) {
                        res = $('#success');
                    } else {
                        res = $('#fail');
                    }
                    res.show(500);
                    setTimeout(function() {
                        res.hide(500);
                    }, 2000);
                }
            });
        }
    });

    $('#btnAdd').click(function() {
        $(this).attr('href', '/xiche/orders/order');
    });
});