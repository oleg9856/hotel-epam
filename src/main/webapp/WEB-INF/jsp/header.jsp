<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ page import="org.hotel.entity.user.Role" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="content" prefix="header.">
    <fmt:message key="home" var="home"/>
    <fmt:message key="log_in" var="log_in"/>
    <fmt:message key="sign_in" var="sign_in"/>
    <fmt:message key="log_out" var="sign_out"/>
</fmt:bundle>

<header class="p-3 bg-dark text-white">
    <div class="container">
        <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
            <a href="${contextPath}/controller/home" class="d-flex align-items-center mb-2 mb-lg-0 text-white text-decoration-none">
                <svg xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Bootstrap" width="40" height="32" fill="currentColor" class="bi bi-stickies-fill" viewBox="0 0 16 16">
                    <path d="M0 1.5V13a1 1 0 0 0 1 1V1.5a.5.5 0 0 1 .5-.5H14a1 1 0 0 0-1-1H1.5A1.5 1.5 0 0 0 0 1.5z"></path>
                    <path d="M3.5 2A1.5 1.5 0 0 0 2 3.5v11A1.5 1.5 0 0 0 3.5 16h6.086a1.5 1.5 0 0 0 1.06-.44l4.915-4.914A1.5 1.5 0 0 0 16 9.586V3.5A1.5 1.5 0 0 0 14.5 2h-11zm6 8.5a1 1 0 0 1 1-1h4.396a.25.25 0 0 1 .177.427l-5.146 5.146a.25.25 0 0 1-.427-.177V10.5z"></path>
                </svg>
            </a>

            <c:set var="user" scope="session" value="${sessionScope.user}"/>
            <c:set var="role" scope="session" value="${sessionScope.user.role}"/>

            <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                <li><a href="${contextPath}/controller?command=home" class="nav-link px-2 text-white">${home}</a></li>
                <c:if test="${user != null}">
                    <li>
                        <c:if test="${role == Role.CUSTOMER}">
                            <a class="nav-link px-2 text-white" href="${contextPath}/controller?command=book">Book</a>
                            <a class="nav-link px-2 text-white" href="${contextPath}/controller?command=room">${room}</a>
                        </c:if>
                        <c:if test="${role == Role.MANAGER}">
                            <a class="nav-link px-2 text-white" href="${contextPath}/controller?command=book">Book</a>
                            <a class="nav-link px-2 text-white" href="${contextPath}/controller?command=room">${room}</a>
                        </c:if>
                        <c:if test="${role == Role.BLOCK}">
                            <a class="nav-link px-2 disabled" href="#" tabindex="-1" aria-disabled="true">${role}</a>
                        </c:if>
                    </li>
                </c:if>
                <li class="nav-item active">
                    <a class="px-2 nav-link" style="color: white"
                       href="${contextPath}/controller/change-locale?locale=uk_UA">UA</a>
                </li>
                <li class="nav-item active">
                    <a class="px-2 nav-link" style="color: white"
                       href="${contextPath}/controller?locale=en_US">EN</a>
                </li>
            </ul>

            <div class="text-end">
                <c:if test="${role != null}">
                    <button type="button" class="btn btn-outline-light me-2">
                        <a class="text-white" href="${contextPath}/controller?command=sign_out">${sign_out}</a>
                    </button>
                </c:if>
                <c:if test="${role == null}">
                    <button  type="button" class="btn btn-outline-light me-2">
                        <a class="text-white" href="${pageContext.request.contextPath}/controller?command=login">${log_in}</a>
                    </button>
                    <button type="button" class="btn btn-warning">
                        <a class="text-dark" href="${pageContext.request.contextPath}/controller?command=sign_in">${sign_out}</a>
                    </button>
                </c:if>
            </div>
        </div>
    </div>
</header>