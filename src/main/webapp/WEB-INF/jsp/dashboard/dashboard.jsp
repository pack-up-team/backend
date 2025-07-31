<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>대시보드</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .header {
            background-color: #007bff;
            color: white;
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 20px;
        }
        .welcome-message {
            font-size: 24px;
            margin-bottom: 10px;
        }
        .btn {
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            margin-right: 10px;
        }
        .logout-btn {
            background-color: #dc3545;
            color: white;
        }
        .logout-btn:hover {
            background-color: #c82333;
        }
        .mypage-btn {
            background-color: #28a745;
            color: white;
        }
        .mypage-btn:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>
    <div class="header">
        <div class="welcome-message">환영합니다, ${username}님!</div>
        <div>
            <a href="/mypage/mypage" class="btn mypage-btn">마이페이지</a>
            <form action="/logout" method="post" style="display: inline;">
                <button type="submit" class="btn logout-btn">로그아웃</button>
            </form>
        </div>
        <div>
            <img src="/files/image/${imageRefNo}?fileCate1=${fileCate1}&fileCate2=${fileCate2}" alt="이미지 미리보기" style="max-width: 200px; max-height: 200px;">
        </div>
    </div>

    <script>
        // 사용자 정보 체크
        <c:if test="${empty username}">
            window.location.href = '/';
        </c:if>
        
        console.log("imageRefNo : "+${imageRefNo});
        console.log("fileCate1 : "+"${fileCate1}");
        console.log("fileCate2 : "+"${fileCate2}");
    </script>
    
    <div>
        <h2>대시보드</h2>
        <p>로그인에 성공하셨습니다.</p>
        <p>현재 시간: <c:out value="${currentTime}" /></p>
    </div>
</body>
</html>