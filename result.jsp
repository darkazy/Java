<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Quiz Results</title>
</head>
<body>
    <h2>Addition Quiz Results</h2>
    <%
        int totalQuestions = 5;
        int correctAnswers = 0;

        for (int i = 1; i <= totalQuestions; i++) {
            // Retrieve question details and user's answer
            int num1 = Integer.parseInt(request.getParameter("num1" + i));
            int num2 = Integer.parseInt(request.getParameter("num2" + i));
            int userAnswer = Integer.parseInt(request.getParameter("answer" + i));
            int correctAnswer = num1 + num2;

            // Check if the answer is correct
            if (userAnswer == correctAnswer) {
                correctAnswers++;
            }
    %>
            <p>
                Question <%= i %>: <%= num1 %> + <%= num2 %> = <%= correctAnswer %> 
                | Your Answer: <%= userAnswer %> 
                <strong><%= (userAnswer == correctAnswer) ? "✔ Correct" : "✘ Wrong" %></strong>
            </p>
    <%
        }
    %>
    <h3>Total Correct: <%= correctAnswers %> / <%= totalQuestions %></h3>
</body>
</html>
