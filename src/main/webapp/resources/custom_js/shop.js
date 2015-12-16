$(document).ready(function () {

    $('#find-btn').click(function () {
        this.blur();
        $('#goods').val('').addClass('hide');

        var city = $.trim($('#city').val());
        var goodName = $.trim($('#good-name').val());
        var contextPath = $('#context-path').val();

        if (!validate(city, goodName)) {
            return false;
        }

        $.ajax({
            url: contextPath+'/shop?city=' + city + '&query=' + goodName,
            success: function (data) {
                $('#error').addClass('hide');
                $('#goods').removeClass('hide');
                $('#goods').empty();
                var markup =
                    '<div class="list-group-item">' +
                    '<h5 class="list-group-item-heading">Заголовок: ${name}</h5>' +
                    '<p class="list-group-item-text">' +
                    'Стоимость:  ${price} </br>' +
                    'Url: <a href="${url}">${url}</a> </br>' +
                    '</p>' +
                    '</div>';
                $.template("goods", markup);
                $.tmpl("goods", data).appendTo('#goods');
            },
            error: function (data) {
                var errorText = data.responseText;
                $('#error').removeClass('hide').text(errorText);
            }
        });
        return false;
    });

    function validate(city, query) {
        if (query.length < 3) {
            $('#error').removeClass('hide').text("Введите не меньше трех символов для поиска!");
            return false;
        }
        return true;
    }

});

