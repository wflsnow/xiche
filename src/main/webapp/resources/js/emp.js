$(document).ready(function() {
    $('#popcity').change(function() {
        $('#popdistrict .district').remove();
        var city = $('#popcity').val();
        if (!!city) {
            $.get('/xiche/districts/' + city, function(data) {
                $.each(data, function() {
                    $('#popdistrict').append('<option class="district form-control" id="' + this + '" value="' + this + '">' + this + '</option>');
                })
            });
        }
    });
    $.validator.setDefaults({
        highlight: function(element) {
            $(element).closest('.form-group').addClass('has-error');
        },
        unhighlight: function(element) {
            $(element).closest('.form-group').removeClass('has-error');
        },
        errorElement: 'span',
        errorClass: 'help-block',
        errorPlacement: function(error, element) {
            if(element.parent('.input-group').length) {
                error.insertAfter(element.parent());
            } else {
                error.insertAfter(element);
            }
        }
    });
});