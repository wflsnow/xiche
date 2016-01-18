$(document).ready(function() {
    // $('.city').remove();
    // $.get('/xiche/cities', function(data) {
    // $.each(data, function() {
    // $('#city').append('<option class="city form-control" id="' + this + '"
    // value="' + this + '">' + this + '</option>');
    // })
    // });

    $('#city').change(function() {
        $('.district').remove();
        var city = $(this).val();
        if (!!city) {
            $.get('/xiche/districts/' + city, function(data) {
                $.each(data, function() {
                    $('#district').append('<option class="district form-control" id="' + this + '" value="' + this + '">' + this + '</option>');
                })
            });
        }
    });
    
    $('#resetBtn').click(function(){
        $('.district').remove();
    });

    $('#popupOk').click(function() {
        $('#empForm').validate();
        if ($('#empForm').valid()) {
            $.ajax({
                url : '/xiche/emp',
                type : 'post',
                dataType : 'json',
                data : $('#empForm').serialize(),
                success : function(data) {
                    var res;
                    if (data) {
                        res = $('#success');
                    } else {
                        res = $('#fail');
                    }
                    res.show(500);
                    setTimeout(function(){
                        res.hide(500);
                    }, 2000);
                }
            });
        }
    });

    $('#btnAdd').click(function() {
        $(this).attr('href', '/xiche/emp');
    });

    $("#staff").DataTable({
        "columnDefs" : [ {
            orderable : false,
            targets : [ 9 ]
        } ],
        "fnDrawCallback" : function(settings) {
            $('.del').click(function() {
                if (!confirm('Do you really want to delete this employee?')) {
                    return;
                }
                var btn = $(this);
                $.get('/xiche/delemp/' + $(this).closest('tr').find('td:eq(2)').html()).done(function(data) {
                    btn.closest('tr').remove();
                });
            });
            $('.edit').click(function() {
                $(this).attr('href', '/xiche/emp/' + $(this).closest('tr').find('td:eq(2)').html());
            });
        },
        "oLanguage" : {
            "oPaginate" : {
                "sNext" : ">",
                "sPrevious" : "<",
                "sLast" : ">|",
                "sFirst" : "|<"
            },
        },
        "bDestroy" : true,
        "iDisplayLength" : 10
    });

    // $('#searchBtn').click(function() {
    // var path = 'staff/';
    // if ($('#empno').val()) {
    // path += $('#empno').val();
    // } else {
    // path += $('#city').val() + "/" + $('#district').val();
    // }
    // path += "?r=" + Math.random();
    // $("#staff").DataTable({
    // "bProcessing" : true,
    // "sAjaxSource" : path,
    // "aoColumns" : aoColumns,
    // "columnDefs" : [ {
    // orderable : false,
    // targets : [ 9 ]
    // } ],
    // "aLengthMenu" : [ 10, 25, 50, 100 ],
    // "sPaginationType" : "full_numbers",
    // "oLanguage" : {
    // "oPaginate" : {
    // "sNext" : ">",
    // "sPrevious" : "<",
    // "sLast" : ">|",
    // "sFirst" : "|<"
    // },
    // },
    // "bDestroy" : true,
    // "iDisplayLength" : 10,
    // "fnDrawCallback": function(settings) {
    // $('.del').click(function(){
    // if (!confirm('Do you really want to delete this employee?')) {
    // return;
    // }
    // var btn = $(this);
    // $.get('/xiche/delemp/' + $(this).closest('tr').find('td:eq(2)').html())
    // .done(function(data) {
    // btn.closest('tr').remove();
    // });
    // });
    // $('.edit').click(function(){
    // $(this).attr('href', '/xiche/emp/' +
    // $(this).closest('tr').find('td:eq(2)').html());
    // });
    // }
    // });
    // });
    //
    // var aoColumns = [ {
    // "sTitle" : "Name",
    // "mDataProp" : "name",
    // "bVisible" : true
    // }, {
    // "sTitle" : "ID No.",
    // "mDataProp" : "idno",
    // "bVisible" : true
    // }, {
    // "sTitle" : "Emp No.",
    // "mDataProp" : "empno",
    // "bVisible" : true
    // }, {
    // "sTitle" : "Gender",
    // "mDataProp" : "gender",
    // "bVisible" : true
    // }, {
    // "sTitle" : "Age",
    // "mDataProp" : "age",
    // "bVisible" : true
    // }, {
    // "sTitle" : "Address",
    // "mDataProp" : "address",
    // "bVisible" : true
    // }, {
    // "sTitle" : "Phone",
    // "mDataProp" : "phone",
    // "bVisible" : true
    // }, {
    // "sTitle" : "City",
    // "mDataProp" : "city",
    // "bVisible" : true
    // }, {
    // "sTitle" : "District",
    // "mDataProp" : "district",
    // "bVisible" : true
    // }, {
    // "sTitle" : '<span class="glyphicon glyphicon-cog"></span> <span
    // class="glyphicon glyphicon-minus"></span>',
    // "sDefaultContent" : '<a class="btn btn-info btn-xs edit"
    // data-toggle="modal" data-target="#popup"><span class="glyphicon
    // glyphicon-cog"></span></a>' +
    // ' <button class="btn btn-danger btn-xs del"><span class="glyphicon
    // glyphicon-minus"></span></button>',
    // "bVisible" : true
    // } ];
});