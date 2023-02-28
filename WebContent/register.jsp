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
				<form action="<%=request.getContextPath()%>/register" method="POST">
					<fieldset>
						<legend><%=rb.getString("register")%></legend>
						<div class="form-floating mb-3">
							<input type="text" class="form-control" name="name" placeholder="<%=rb.getString("name")%>"
								value="${requestScope.user.name}"> <label for="name"><%=rb.getString("name")%></label>
						</div>
						<div class="form-floating mb-3">
							<input type="text" class="form-control" name="surnames" placeholder="<%=rb.getString("surnames")%>"
								value="${requestScope.user.surnames}"> <label for="surnames"><%=rb.getString("surnames")%></label>
						</div>
						<div class="form-floating mb-3">
							<input type="email" class="form-control" name="mail" placeholder="<%=rb.getString("email")%>"
								value="${requestScope.user.email}"> <label for="mail"><%=rb.getString("email")%></label>
						</div>
						<div class="form-floating mb-3">
							<input type="number" class="form-control" name="phone" placeholder="<%=rb.getString("phone")%>"
								value="${requestScope.user.phone}">
							<label for="phone"><%=rb.getString("phone")%></label>
						</div>
						<div class="form-floating mb-3">
							<input type="password" class="form-control" name="pass1" placeholder="<%=rb.getString("pass")%>"
								value="${requestScope.user.password}"> <label for="pass1"><%=rb.getString("pass")%></label>
						</div>
						<div class="form-floating mb-3">
							<input type="password" class="form-control" name="pass2" placeholder="<%=rb.getString("repeat_pass")%>"
								value="${requestScope.pass2}"> <label for="pass2"><%=rb.getString("repeat_pass")%></label>
						</div>
						<button type="submit" class="btn btn-primary"><%=rb.getString("continue")%></button>
						<a href="index.jsp" class="btn btn-danger"><%=rb.getString("go_back")%></a>
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