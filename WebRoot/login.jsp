<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp" %>
	<br/>
	<hr/>
	<form action="${pageContext.request.contextPath}/servlet/ClientServlet?op=login" method="post">
		<table border="1" width="338" align="center">
			<tr>
				<td>用户名：</td>
				<td>
					<input type="text" name="name">
				</td>
			</tr>
			<tr>
				<td>密码：</td>
				<td>
					<input type="password" name="password">
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" value="登录">
				</td>
			</tr>
		</table>
	</form>
  </body>
</html>
