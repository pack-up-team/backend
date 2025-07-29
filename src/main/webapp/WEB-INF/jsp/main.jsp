<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PackUp - 메인 페이지</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 0;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }
        
        .header {
            background: rgba(255, 255, 255, 0.1);
            backdrop-filter: blur(10px);
            padding: 1rem 2rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        
        .logo {
            color: white;
            font-size: 2rem;
            font-weight: bold;
        }
        
        .nav-links {
            display: flex;
            gap: 2rem;
        }
        
        .nav-links a {
            color: white;
            text-decoration: none;
            padding: 0.5rem 1rem;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        
        .nav-links a:hover {
            background: rgba(255, 255, 255, 0.2);
        }
        
        .container {
            flex: 1;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            text-align: center;
            padding: 2rem;
        }
        
        .welcome {
            color: white;
            margin-bottom: 2rem;
        }
        
        .welcome h1 {
            font-size: 3rem;
            margin-bottom: 1rem;
        }
        
        .welcome p {
            font-size: 1.2rem;
            margin-bottom: 2rem;
        }
        
        .action-buttons {
            display: flex;
            gap: 1rem;
            flex-wrap: wrap;
            justify-content: center;
        }
        
        .btn {
            padding: 1rem 2rem;
            font-size: 1rem;
            border: none;
            border-radius: 25px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            transition: all 0.3s;
        }
        
        .btn-primary {
            background: #4CAF50;
            color: white;
        }
        
        .btn-primary:hover {
            background: #45a049;
            transform: translateY(-2px);
        }
        
        .btn-secondary {
            background: rgba(255, 255, 255, 0.2);
            color: white;
            border: 2px solid white;
        }
        
        .btn-secondary:hover {
            background: white;
            color: #667eea;
            transform: translateY(-2px);
        }
    </style>
</head>
<body>
    <header class="header">
        <div class="logo">PackUp</div>
        <nav class="nav-links">
            <a href="/dashboard">대시보드</a>
            <a href="/mypage">마이페이지</a>
            <a href="/lgn/login">로그인</a>
        </nav>
    </header>
    
    <main class="container">
        <div class="welcome">
            <h1>PackUp에 오신 것을 환영합니다!</h1>
            <p>효율적인 패키징 관리 시스템으로 더 나은 비즈니스를 시작하세요.</p>
        </div>
        
        <div class="action-buttons">
            <a href="/dashboard" class="btn btn-primary">시작하기</a>
            <a href="/lgn/login" class="btn btn-secondary">로그인</a>
        </div>
    </main>
</body>
</html>