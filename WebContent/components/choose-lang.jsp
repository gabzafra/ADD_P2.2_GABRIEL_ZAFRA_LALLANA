<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
        <h1><%=rb.getString("select_lang")%></h1>
        <a href="./lang?set=es" class="btn btn-primary"><span class="fi fi-es"></span> <%=rb.getString("lang_es")%></a>
        <a href="./lang?set=en" class="btn btn-primary"><span class="fi fi-gb"></span> <%=rb.getString("lang_en")%></a>
        <c:if test="${!empty requestScope.error}">
            <jsp:include page="/components/error.jsp">
                <jsp:param name="msj" value="${requestScope.error}" />
            </jsp:include>
        </c:if>
    </div>