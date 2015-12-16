<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE>
<html>
<head>
    <title>Shop</title>
    <link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet"/>
</head>
<body>
<div class="row">
    <div class="col-md-4 col-md-offset-4">
        <div class="alert alert-danger hide" id="error" style="position: absolute; text-align: center; width: 94.5%;margin-top: 5%"
             role="alert"></div>
        <div class="panel panel-default" style="margin-top: 25%">
            <div class="panel-heading">
                <h3 class="panel-title">OLX</h3>
            </div>
            <div class="panel-body">
                <form role="form">
                    <fieldset>
                        <input type="hidden" id="context-path" value="${pageContext.request.contextPath}">
                        <div class="form-group">
                            <label class="control-label" for="good-name">Название товара:</label>
                            <input class="form-control" placeholder="Название товара" id="good-name" type="text"
                                   required
                                   autofocus>
                        </div>
                        <div class="form-group">
                            <label class="control-label" for="city">Город (Пустая строка - поиск по Украине):</label>
                            <input class="form-control" placeholder="Город" id="city" type="text"
                                   required>
                        </div>
                        <button type=submit class="btn btn-lg btn-primary btn-block" id="find-btn">Найти</button>
                    </fieldset>
                </form>
            </div>
        </div>
        <hr/>
        <div class="list-group hide" id="goods">
            Найденные товары:<br/>
        </div>
    </div>
</div>
</body>
<script src="<c:url value="/resources/js/jquery-2.1.4.min.js"/>"></script>
<script src="<c:url value="/resources/js/jquery.tmpl.js"/>"></script>
<script src="<c:url value="/resources/js/jquery.tmpl.min.js"/>"></script>
<script src="<c:url value="/resources/custom_js/shop.js"/>"></script>
</html>
