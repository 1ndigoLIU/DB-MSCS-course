<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Find a Player</title>
</head>
<body>
    <form action="findplayers" method="post">
      <h1>Search for a Player by Name</h1>
      <p>
        <label for="username">Username</label>
        <input id="username" name="username" value="${fn:escapeXml(param.username)}">
      </p>
      <p>
        <input type="submit">
        <br/><br/><br/>
        <span id="scuuessMessage"><b>${messages.success}</b></span>
      </p>
    </form>
    <br/>
    <h1>Matching Players</h1>
    <table border="1">
      <tr>
        <th>UserName</th>
        <th>Email</th>
        <th>Status</th>
        <th>Characters</th>
      </tr>
      <c:forEach items="${players}" var="player">
          <tr>
              <td><c:out value="${player.getName()}"/></td>
              <td><c:out value="${player.getEmail()}"/></td>
              <td><c:out value="${player.getStatus()}"/></td>
              <td><a href="characters?id=<c:out value="${player.getAccountId()}"/>">Characters</a></td>
              <td><a href="playerupdate?id=<c:out value="${player.getAccountId()}"/>">Change Username</a></td>
          </tr>
      </c:forEach>
    </table>
</body>
</html>
