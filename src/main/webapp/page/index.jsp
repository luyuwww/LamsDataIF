<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lams接口控制台</title>
    <link href="${pageContext.request.contextPath}/res/js/bootstrap-3.0.3-dist/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/res/js/bootstrap-3.0.3-dist/css/bootstrap-theme.min.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/res/js/self/theme.css" rel="stylesheet">
</head>
<script type="text/javascript">
    function submitSyncUserGroup() {
        var r = confirm("慎重: 确定进行用户同步吗?");
        if (r == true) {
            window.location.assign("/LamsIFML/syncUserGroup");
        } else {
            alert("取消!");
        }
    }
</script>
<body>
<div class="container theme-showcase">
    <p>

        <br>
        <a class="btn btn-lg btn-success"
           href="${pageContext.request.contextPath}/keyanPushXyPrj">科研推送->档案抓取-纵向/横向项目</a>
        <a class="btn btn-lg btn-success"
           href="${pageContext.request.contextPath}/keyanPushWxPrj">科研推送->档案抓取-外协项目</a>
        <br>
        <a class="btn btn-lg btn-warning"
           href="${pageContext.request.contextPath}/daPushXyPrj">档案推送->科研抓取-纵向/横向项目</a>
        <a class="btn btn-lg btn-warning"
           href="${pageContext.request.contextPath}/daPushWxPrj">档案推送->科研抓取-外协项目</a>
        <br>
        <a class="btn btn-lg btn-primary"
           href="${pageContext.request.contextPath}/viewLogList">查看日志</a>
    </p>
</div>
</body>
</html>
