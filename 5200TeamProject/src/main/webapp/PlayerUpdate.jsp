<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update a Player</title>
</head>
<body>
  <h1>Update Player</h1>
  <form action="playerupdate" method="post">
    <p>
      <label for="username">UserName</label>
      <input id="username" name="username" value="">
      <input id="accoundID" name="id" type="hidden" value="${fn:escapeXml(param.id)}">
    </p>
    <p>
      <input type="submit">
    </p>
  </form>
  <br/><br/>
  <p>
    <span id="successMessage"><b>${messages.success}</b></span>
  </p>
</body>
</html>
