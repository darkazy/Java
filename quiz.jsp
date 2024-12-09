<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Random"%>
<!DOCTYPE html>
<html>
<head>
    <title>Addition Quiz</title>
</head>
<body>
    <h2>Addition Quiz</h2>
    <form action="result.jsp" method="post">
        <%
            Random random = new Random();
            for (int i = 1; i <= 5; i++) {
                int num1 = random.nextInt(50) + 1; // Random number between 1 and 50
                int num2 = random.nextInt(50) + 1; // Random number between 1 and 50

                // Store question parameters in hidden fields for later use
        %>
            <p>
                Question <%= i %>: 
                <%= num1 %> + <%= num2 %> = 
                <input type="text" name="answer<%= i %>" size="5" required>
                <!-- Hidden fields -->
                <input type="hidden" name="num1<%= i %>" value="<%= num1 %>">
                <input type="hidden" name="num2<%= i %>" value="<%= num2 %>">
            </p>
        <%
            }
        %>
        <br>
        <button type="submit">Submit</button>
    </form>
    <p>Click the browser's Refresh button to get a new quiz.</p>
</body>
</html>
