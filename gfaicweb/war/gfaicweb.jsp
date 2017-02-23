<%@page import="org.apache.catalina.HttpResponse"%>
<%@page import="com.ihsinformatics.gfaicweb.server.WebService"%>
<%@page import="org.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	WebService web = new WebService();
	web.handleRequest(request, response);
	out.print(response);
%>
