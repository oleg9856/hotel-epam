<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="content" prefix="login.">
    <fmt:message key="fail_message" var="fail_message"/>
    <fmt:message key="password" var="password"/>
    <fmt:message key="email" var="email"/>
    <fmt:message key="log_in" var="log_in"/>
    <fmt:message key="title" var="title"/>
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
        <form action="${contextPath}/controller" id="login" method="post">
            <input type="hidden" name="command" value="login">
            <img class="mb-4" src="<c:url value="/resources/img/user_system_image.png"/>" alt="" width="72" height="57">
            <h1 class="h3 mb-3 fw-normal">Please login</h1>
            <div class="form-floating">
                <input type="email" class="form-control" id="email" name="email" placeholder="name@example.com"
                       required>
                <label for="email">Email address</label>
            </div>
            <div class="form-floating">
                <input type="password" class="form-control" id="password" name="password" placeholder="Password"
                       required>
                <label for="password">Password</label>
            </div>
            <hr/>
            <input class="w-100 btn btn-lg btn-primary" type="submit" id="submit" value="Sin">
            <c:set var="is_login_failed" scope="request" value="${requestScope.is_login_failed}"/>
            <c:if test="${is_login_failed}">
                <div id="fail_message">
                        ${fail_message}
                </div>
            </c:if>
        </form>
        <hr/>
        <button  class="w-100 btn btn-lg btn-light" href="${contextPath}/controller?command=sing_in">${log_in}</button>
        <p class="mt-5 mb-3 text-muted">©2023</p>
    </main>
</body>
</html>
