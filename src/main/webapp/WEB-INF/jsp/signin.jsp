<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="content" prefix="sign_in.">
    <fmt:message key="fail_message" var="fail_message"/>
    <fmt:message key="password" var="password"/>
    <fmt:message key="email" var="email"/>
    <fmt:message key="name" var="name"/>
    <fmt:message key="surname" var="surname"/>
    <fmt:message key="phone_number" var="phone_number"/>
    <fmt:message key="title" var="title"/>
    <fmt:message key="login" var="login"/>
</fmt:bundle>

<html>
<head>
    <c:set var="contextPath" value="${pageContext.request.contextPath}"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${title}</title>
    <link href="<c:url value="/resources/css/bootstrap/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/custom/user_system.css"/> " rel="stylesheet">
</head>
<body class="text-center">
<c:set var="user" scope="session" value="${sessionScope.user}"/>
<main class="form-sign_in">
    <form action="${contextPath}/controller" id="sign_in" method="post">
        <input type="hidden" name="command" value="sign_in">
        <img class="mb-4" src="<c:url value="/resources/img/user_system_image.png"/>" alt="" width="72" height="57">
        <h1 class="h3 mb-3 fw-normal">${title}</h1> //dd

        <div class="form-floating">
            <input type="text" class="form-control" id="name" name="name" placeholder="John"
                   required>
            <label for="name">${name}</label>
        </div>
        <div class="form-floating">
            <input type="text" class="form-control" id="surname" name="surname" placeholder="Simpson"
                   required>
            <label for="surname">${surname}</label>
        </div>
        <div class="form-floating">
            <input type="password" class="form-control" id="password" name="password" placeholder="Password"
                   required>
            <label for="password">${password}</label>
        </div>
        <div class="form-floating">
            <input type="email" class="form-control" id="email" name="email" placeholder="name@example.com"
                   required>
            <label for="email">${email}</label>
        </div>
        <div class="form-floating">
            <input type="tel" class="form-control" id="phone_number" name="phone_number" placeholder="Password"
                   required>
            <label for="phone_number">${phone_number}</label>
        </div>
        <hr/>
        <input class="w-100 btn btn-lg btn-primary" type="submit" id="submit" value="Sin">
        <c:set var="is_sign_in_failed" scope="request" value="${requestScope.is_sign_in_failed}"/>
        <c:if test="${is_sign_in_failed}">
            <div id="fail_message">
                    ${fail_message}
            </div>
            <c:set var="is_login_failed" scope="session" value=""/>
        </c:if>
    </form>
    <hr/>
    <button  class="w-100 btn btn-lg btn-light" href="${contextPath}/controller?command">${login}</button>
    <p class="mt-5 mb-3 text-muted">©2023</p>
</main>
</body>
</html>
