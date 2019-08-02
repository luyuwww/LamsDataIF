<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>日志列表</title>
    <link href="${pageContext.request.contextPath}/res/js/bootstrap-3.0.3-dist/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/res/js/bootstrap-3.0.3-dist/css/bootstrap-theme.min.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/res/js/self/theme.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/res/js/self/grid.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h2>中间库列表</h2>

    <br/>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>序号</th>
            <th>门类</th>
            <th>数据库名</th>
            <th>数据库类型</th>
            <th>数据库URL</th>
            <th colspan="3" style="text-align: center">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="middbs" items="${listMidDbs}" varStatus="count">
            <tr>
                <td>${count.index+1}</td>
                <td>${middbs.dalxchname}【${middbs.libcode}】</td>
                <td>${middbs.dbname}</td>
                <td>${middbs.dbtype}</td>
                <td>${middbs.dburl}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/testConn?midDbsDid=${middbs.did}&rand=<%=Math.random()%>">
                        测试连接</a>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/viewMidTableByDbID?midDbsDid=${middbs.did}&rand=<%=Math.random()%>">
                        详情</a>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/catcheOneDB?midDbsDid=${middbs.did}&rand=<%=Math.random()%>">
                        抓取</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a href="${pageContext.request.contextPath}/">
        返回</a>
</div>
</body>
</html>
