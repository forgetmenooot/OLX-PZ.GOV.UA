$(document).ready(function () {

    $('#date').datepicker({dateFormat: "dd-mm-yy", maxDate: "+5w"});

    $('#find-btn').click(function () {
        this.blur();
        $('#trips').val('').addClass('hide');

        var arrStation = $.trim($('#arr-station').val());
        var depStation = $.trim($('#dep-station').val());
        var contextPath = $('#context-path').val();

        var date = $.trim($('#date').val());
        if (!validate(depStation, arrStation, date)) {
            return false;
        }

        $.ajax({
            url: contextPath + '/trip?dep_station=' + depStation + '&arr_station=' + arrStation + '&date=' + date,
            success: function (data) {
                $('#error').addClass('hide');
                $('#trips').removeClass('hide').empty();
                var markup =
                    '<div class="list-group-item">' +
                    '<h5 class="list-group-item-heading">№No ${train[0]} (${from[0]} - ${to[0]})</h5>' +
                    '<p class="list-group-item-text">' +
                    'Дата отправки:  ${otpr} </br>' +
                    'Дата прибытия: ${prib} </br>' +
                    'В пути:  ${vputi} </br>' +
                    'Люкс: ${l} мест(о)</br>' +
                    'Купе:  ${k} мест(о)</br>' +
                    'Платскарт: ${p} мест(о)</br>' +
                    'Общие: ${o} мест(о)</br>' +
                    'Сидячие: ${c} мест(о)</br>' +
                    '</p>' +
                    '</div>';
                $.template("trips", markup);
                $.tmpl("trips", data).appendTo('#trips');
            },
            error: function (data) {
                var errorText = data.responseJSON;
                $('#error').removeClass('hide').text(errorText);
            }
        });
        return false;
    });

    function validate(depStation, arrStation, date) {
        if (date == "") {
            $('#error').removeClass('hide').text("Дата не задана!");
            return false;
        }
        if (depStation.length < 3) {
            $('#error').removeClass('hide').text("Введите не меньше трех символов для станции отправления!");
            return false;
        }
        if (arrStation.length < 3) {
            $('#error').removeClass('hide').text("Введите не меньше трех символов для станции прибытия!");
            return false;
        }
        return true;
    }

});
