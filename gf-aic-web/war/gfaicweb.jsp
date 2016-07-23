<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.ihsinformatics.gfaicweb.*"%>
<%
	WebService web = new WebService();
	String resp = web.handleRequest(request);
	System.out.println(resp);
	out.println(resp);
%>
