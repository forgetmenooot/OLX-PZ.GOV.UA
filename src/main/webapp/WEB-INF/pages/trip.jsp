<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE>
<html>
<head>
    <title>Trip</title>
    <link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/resources/css/jquery-ui.css"/>" rel="stylesheet"/>
</head>
<body>
<div class="row">
    <div class="col-md-4 col-md-offset-4">
        <div class="alert alert-danger hide" id="error" style="position: absolute; text-align: center; width: 94.5%;margin-top: 5%"
             role="alert"></div>
        <div class="panel panel-default" style="margin-top: 25%">
            <div class="panel-heading">
                <h3 class="panel-title">Железная дорога</h3>
            </div>
            <div class="panel-body">
                <form role="form">
                    <fieldset>
                        <input type="hidden" id="context-path" value="${pageContext.request.contextPath}">
                        <div class="form-group">
                            <label class="control-label" for="dep-station">Станция отправки:</label>
                            <input class="form-control" placeholder="Станция отправки" id="dep-station" type="text"
                                   required
                                   autofocus>
                        </div>
                        <div class="form-group">
                            <label class="control-label" for="arr-station">Станция прибытия:</label>
                            <input class="form-control" placeholder="Станция прибытия" id="arr-station" type="text"
                                   required>
                        </div>
                        <div class="form-group">
                            <label class="control-label" for="date">Дата:</label>
                            <input class="form-control" placeholder="Дата" id="date" type="text" readonly required>
                        </div>
                        <button type=submit class="btn btn-lg btn-primary btn-block" id="find-btn">Найти</button>
                    </fieldset>
                </form>
            </div>
        </div>
        <hr/>
        <div class="list-group hide" id="trips">
            Поезда в выбранную дату:<br/>
        </div>
    </div>
</div>
</body>
<script src="<c:url value="/resources/js/jquery-2.1.4.min.js"/>"></script>
<script src="<c:url value="/resources/js/jquery-ui.js"/>"></script>
<script src="<c:url value="/resources/js/jquery.tmpl.js"/>"></script>
<script src="<c:url value="/resources/js/jquery.tmpl.min.js"/>"></script>
<script src="<c:url value="/resources/custom_js/trip.js"/>"></script>
</html>
