<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>
<div style="display: ;">
proc : <span id="span_proc"></span><br>
pageNumber : <span id="span_pageNumber">${pageNumber }</span><br>
no :  <span id="span_no">${no }</span><br>
path : <span id="span_path">${path }</span><br>
arg01 : <span id="span_arg01">${arg01 }</span><br>
cookNo :  <span id="span_cookNo">${cookNo }</span><br>
결과 : <span id="span_passwd"></span><br>
</div>

<input type="text" name="a" style="display: ;"><br><!-- ajax 테스트 위한 것  -->

<div id="result" style="border:1px solid red; height:500px; position : relative;" ></div>

<%-- <script type="text/javascript" src="${path }/memo/_memo.js"></script> --%>
<script src='<c:url value="/resources/js/_memo.js"/>'>
</script>