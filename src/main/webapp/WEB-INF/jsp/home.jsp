<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 06.01.2023
  Time: 12:28
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<!DOCTYPE html>
<html>

<head>
    <c:set var="contextPath" value="${pageContext.request.contextPath}"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${title}</title>
    <link href="<c:url value="/resources/css/bootstrap/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/custom/home.css"/> " rel="stylesheet">
</head>

<body>
<jsp:include page="header.jsp"/>
<img class="bg-img" src="<c:url value="/resources/img/background-1.jpg"/>" alt="">
<jsp:include page="footer.jsp"/>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
</body>

</html>
