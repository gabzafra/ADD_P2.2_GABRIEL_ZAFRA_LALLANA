<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@page import="java.util.ResourceBundle"%>
    <%@page import="java.util.Locale"%>
        <% 
        Locale locale;
        if(session.getAttribute("idioma") == null){
          locale = request.getLocale();
        } else {
          locale = new Locale((String)session.getAttribute("idioma"));
        }
        ResourceBundle rb = ResourceBundle.getBundle("idioma", locale);
        %>
    <div class="contact container mx-auto m-3">
        <a href="./profile?upd=${requestScope.detail.id}" class="btn btn-primary"><%=rb.getString("update_btn")%></a>
        <a href="./change-pass.jsp" class="btn btn-primary"><%=rb.getString("change_pass")%></a>
        <article>
            <h1>${requestScope.detail.name} ${requestScope.detail.surnames}</h1>
            <p><span><%=rb.getString("email")%>: </span><a href="mailto:${requestScope.detail.email}">${requestScope.detail.email}</a></p>
            <p><span><%=rb.getString("phone")%>: </span>${requestScope.detail.phone}</p>
        </article>
        <c:if test="${!empty requestScope.error}">
            <jsp:include page="/components/error.jsp">
                <jsp:param name="msj" value="${requestScope.error}" />
            </jsp:include>
        </c:if>
    </div>