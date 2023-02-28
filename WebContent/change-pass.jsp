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
			<div id="contact-form" class="contact container mx-auto m-3">
				<form action="<%=request.getContextPath()%>/profile?pass" method="POST">
					<fieldset>
						<legend><%=rb.getString("change_pass")%></legend>
						<div class="form-floating mb-3">
							<input type="password" class="form-control" name="old-pass" placeholder="<%=rb.getString("old_pass")%>"
								value=""> <label for="old-pass"><%=rb.getString("old_pass")%></label>
						</div>
						<div class="form-floating mb-3">
							<input type="password" class="form-control" name="new-pass1" placeholder="<%=rb.getString("new_pass")%>"
								value=""> <label for="new-pass1"><%=rb.getString("new_pass")%></label>
						</div>
						<div class="form-floating mb-3">
							<input type="password" class="form-control" name="new-pass2"
								placeholder="<%=rb.getString("repeat_pass")%>" value=""> <label for="new-pass2"><%=rb.getString("repeat_pass")%></label>
						</div>
						<button type="submit" class="btn btn-primary"><%=rb.getString("change")%></button>
						<a href="<%=request.getContextPath()%>/profile" class="btn btn-danger"><%=rb.getString("go_back")%></a>
					</fieldset>
				</form>
				<p>${param.error}</p>
				<c:if test="${!empty requestScope.error}">
					<jsp:include page="/components/error.jsp">
						<jsp:param name="msj" value="${requestScope.error}" />
					</jsp:include>
				</c:if>
			</div>
			<jsp:include page="components/footer.jsp" />
		</body>

		</html>