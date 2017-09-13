<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title><tiles:getAsString name="title" /></title>

    </head>
    <body>
        <header id="header">
            <tiles:insertAttribute name="header" />
        </header>

        <section id="sidemenu">
            <tiles:insertAttribute name="menu" />
        </section>

        <section id="site-content">
            <tiles:insertAttribute name="body" />
        </section>

        <footer id="footer">
            <tiles:insertAttribute name="footer" />
        </footer>

        <spring:url value="/resources/core/js/jquery.js" var="jquery" />
        <spring:url value="/resources/core/js/jquery-ui.js" var="jqueryui" />
        <spring:url value="/resources/core/js/jquery-ui.min.js" var="jqueryuimin" />

        <script src="${jquery}"></script>
        <script src="${jqueryui}"></script>

    </body>
</html>