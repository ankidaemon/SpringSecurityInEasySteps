<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Simple Spring Security</title>
</head>
<body>

	<c:if test="${pageContext.request.userPrincipal.name != null}">
		<h2>
			Welcome : ${pageContext.request.userPrincipal.name} | <a
				href="${pageContext.request.contextPath}/logout"> Logout</a>			
			<%-- 
			Section 2 - Video 2.4 - CSRF and form login
			<form name="form" method="get"
				action="${pageContext.request.contextPath}/logout">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>	
				<input type="submit" value="Log Out" />
				</form>
 			--%>		
 		</h2>
		<p>Your Session id is: "${pageContext.request.session.id}"</p>
	</c:if>

</body>
</html>