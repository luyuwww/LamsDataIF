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
    <h2>中间表列表</h2>
    <br/>
    <c:forEach var="tb" items="${listMidTables}" varStatus="count">
        <HR/>
        序号: <strong>${count.index+1}</strong><br>
        DID: <strong>${tb.did}</strong><br>
        PID: <strong>${tb.pid}</strong><br>
        源表名: <strong>${tb.stbname}</strong><br>
        目标表名: <strong>${tb.ttbname}</strong><br>
        字段映射表: <strong>${tb.mtbname}</strong><br>
        源表查询sql: <strong>${tb.ssql}</strong><br>
        pid获取sql: <strong>${tb.pidsql}</strong><br>
        完成回调sql(更新目标表父级字段用): <strong>${tb.callbacksql}</strong><br>
        更新sql(更新源表标志位): <strong>${tb.updatesql}</strong><br>
        默认字段: <strong>${tb.defaultfield}</strong><br>
        默认值: <strong>${tb.defaultvalue}</strong><br>
        备注: <strong>${tb.bz}</strong><br>
        操作 <a href="${pageContext.request.contextPath}/catcheOneTB?midTbDid=${tb.did}&rand=<%=Math.random()%>">  抓取</a>
    </c:forEach>
    <br/>
    <HR/>
    <a href="${pageContext.request.contextPath}/">返回</a>
    <HR/>
</div>
</body>
</html>
