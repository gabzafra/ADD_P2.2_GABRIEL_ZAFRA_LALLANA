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
        <!DOCTYPE html>
        <html lang="es">
        <jsp:include page="components/head.jsp" />
        <body>
            <jsp:include page="components/header.jsp" />
            <jsp:include page="components/profile-card.jsp" />
            <div class="contact-list container">
                <form action="<%=request.getContextPath()%>/admin?defaults=true" method="POST">
                    <fieldset>
                        <legend class="h1"><%=rb.getString("update_defaults")%></legend>
                        <div class="container row">                        
	                        <fieldset class="col">
	                            <legend class="w-auto px-3"><%=rb.getString("storage_mode")%></legend>
	                            <div class="btn-group btn-group-sm">
	                                <input type="radio" class="btn-check" name="storemode" id="r11" value="mem" ${requestScope.storemode == 'mem' ? 'checked' : ''}>
	                                <label class="btn btn-outline-secondary" for="r11"><%=rb.getString("memory")%></label>
	                                <input type="radio" class="btn-check" name="storemode" id="r12" value="db" ${requestScope.storemode == 'db' ? 'checked' : ''}>
	                                <label class="btn btn-outline-secondary" for="r12"><%=rb.getString("jdbc")%></label>
	                                <input type="radio" class="btn-check" name="storemode" id="r13" value="hb" ${requestScope.storemode == 'hb' ? 'checked' : ''}>
                                    <label class="btn btn-outline-secondary" for="r13"><%=rb.getString("hibernate")%></label>
	                            </div>
	                        </fieldset>
	                        <fieldset class="col">
	                            <legend class="w-auto px-3"><%=rb.getString("default_lang")%></legend>
	                            <div class="btn-group btn-group-sm">
	                                <input type="radio" class="btn-check" name="deflang" id="r21" value="es" ${requestScope.deflang == 'es' ? 'checked' : ''}>
	                                <label class="btn btn-outline-secondary" for="r21"><%=rb.getString("lang_es")%></label>
	                                <input type="radio" class="btn-check" name="deflang" id="r22" value="en" ${requestScope.deflang == 'en' ? 'checked' : ''}>
	                                <label class="btn btn-outline-secondary" for="r22"><%=rb.getString("lang_en")%></label>
	                            </div>
	                        </fieldset>
                        </div>
                        <p class="text-danger small"><%=rb.getString("upd_def_info")%></p>
                        <button type="submit" class="btn btn-success"><%=rb.getString("update_btn")%></button>                 
                    </fieldset>
                </form>
                <hr>
                <form action="<%=request.getContextPath()%>/admin" method="POST">
                    <fieldset>
                        <legend class="h1"><%=rb.getString("search_user")%></legend>
                        <div class="form-floating mb-3">
                            <input type="text" class="form-control" name="filter" placeholder="<%=rb.getString("name")%>"
                                value="${requestScope.filter}">
                            <label for="filter"><%=rb.getString("name")%></label>
                        </div>
                        <button type="submit" class="btn btn-success"><%=rb.getString("search")%></button>
                        <a href="./admin" class="btn btn-primary"><%=rb.getString("reset")%></a>                    
                    </fieldset>
                </form>
                <hr>
                <h1><%=rb.getString("users")%></h1>
                <c:forEach items="${requestScope.list}" var="user">
                    <div class="row align-items-center border p-2">
                    <c:if test="${user.lang == 'es'}" >
                        <div class="col-1">
                            <p><span class="fi fi-es"></span> ES</p>
                        </div>
                    </c:if>
                    <c:if test="${user.lang == 'en'}" >
                        <div class="col-1">
                            <p><span class="fi fi-gb"></span> UK</p>
                        </div>
                    </c:if>	                    
                        <div class="col">
                            <p>${user.name}</p>
                        </div>
                        <div class="col-1">
                            <a href="./admin?upd=${user.id}" class="btn btn-primary"><%=rb.getString("update_btn")%></a>
                        </div>
                        <div class="col-1">
                            <a href="./admin?del=${user.id}" class="btn btn-danger"><%=rb.getString("delete")%></a>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <jsp:include page="components/footer.jsp" />
        </body>

        </html>