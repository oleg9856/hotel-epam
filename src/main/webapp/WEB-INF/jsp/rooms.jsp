<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ taglib prefix="pag" uri="http: //corporation.com/custom-tag/paginator" %>
<%@ page import="org.hotel.entity.room.sorting.OrderBy" %>
<%@ page import="org.hotel.entity.room.StatusRoom" %>
<%@ page import="org.hotel.entity.PageLimit" %>

<fmt:setLocale value="${sessionScope.locale}"/>

<html>
<head>
    <c:set var="contextPath" value="${pageContext.request.contextPath}"/>
    <c:set var="room_search" scope="session" value="${sessionScope.room_search}"/>
    <c:set var="order_by" scope="session" value="${sessionScope.room_search.orderBy}"/>
    <c:set var="current_page" scope="session" value="${sessionScope.room_search.currentPage}"/>
    <c:set var="page_count" scope="session" value="${sessionScope.room_search.pageCount}"/>
    <c:set var="items_per_page" scope="session" value="${sessionScope.room_search.itemsPerPage}"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${title}</title>
    <link href="<c:url value="/resources/css/bootstrap/bootstrap.min.css" />" rel="stylesheet">
    <link href="https://cdn.datatables.net/1.13.1/css/dataTables.bootstrap5.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="left" style="margin-top: 50px">
<div class="right">
    <h2>${main_header}</h2>
    <ul class="list-group">
        <li class="list-group-item">
            <div class="row">
                <div class=col-md-1>
                    ${index_header}
                </div>
                <div class=col-md-4>
                    ${publication_header}
                </div>
                <div class=col-md-2>
                    ${type_header}
                </div>
                <div class=col-md-1>
                    ${price_header}
                </div>
            </div>
        </li>
        <c:forEach items="${rooms}" var="room">
            <a href="${contextPath}/controller?command=?id=${room.id}">
                <li class="list-group-item">
                    <div class="row">
                        <div class=col-md-1>
                                ${room.id}
                        </div>
                        <div class=col-md-4>
                                ${room.name}
                        </div>
                        <div class=col-md-2>
                                ${typeNames.get(room.typeId)}
                        </div>
                        <div class=col-md-1>
                                ${room.price}
                        </div>
                    </div>
                </li>
            </a>
        </c:forEach>
    </ul>
<div class="row">
    <nav aria-label="...">
        <ul class="pagination pagination-sm">
            <li class="page-item"><a class="page-link"
                                     href="${contextPath}/controller?command=rooms?order_by=${order_by}&currentPage=1&itemsPerPage=3">3</a>
            </li>
            <li class="page-item"><a class="page-link"
                                     href="${contextPath}/controller?command=rooms?order_by=${order_by}&currentPage=1&itemsPerPage=5">5</a>
            </li>
            <li class="page-item"><a class="page-link"
                                     href="${contextPath}/controller?command=rooms?order_by=${order_by}&currentPage=1&itemsPerPage=30">30</a>
            </li>
        </ul>
    </nav>
    <nav aria-label="...">
        <ul class="pagination justify-content-center">
            <pag:display currentPage="${current_page}"
                         totalPageCount="${page_count}" viewPageCount="3"
                         urlPattern="${contextPath}/controller?command=rooms&order_by=${order_by}&itemsPerPage=${items_per_page}&"/>

        </ul>
    </nav>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>
