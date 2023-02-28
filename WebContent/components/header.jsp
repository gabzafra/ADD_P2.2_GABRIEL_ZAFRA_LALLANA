<%@page import="dam2.add.p22.config.ConfigService"%>
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
    <header>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark p-1">
            <a class="navbar-brand" href="index.jsp"><%=rb.getString("home")%></a>
            <ul class="navbar-nav">
                <c:if test="${sessionScope.idioma != null}">
                    <li class="nav-item nav-link"><span class="fi <%=rb.getString("flag")%>"></span></li>   
                </c:if>    
                <c:if test="${sessionScope.name == null}">
                    <li class="nav-item"><a class="nav-link" href="login.jsp"><%=rb.getString("login")%></a></li>
                    <li class="nav-item"><a class="nav-link" href="register.jsp"><%=rb.getString("register")%></a></li>
                </c:if>
                <c:if test="${sessionScope.name != null}">
                    <li class="nav-item"><a class="nav-link"
                            href="<%=request.getContextPath()%>/profile">${sessionScope.name}</a></li>
                    <li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/login"><%=rb.getString("logout")%></a></li>
                </c:if>
            </ul>
        </nav>
    </header>