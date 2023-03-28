<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="content" prefix="footer.">
  <fmt:message key="message" var="message"/>
</fmt:bundle>

<footer class="footer mt-auto py-3 bg-dark">
    <div class="container">
        <span class="text-muted">${message}</span>
    </div>
</footer>
