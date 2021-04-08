<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../include/inc_header.jsp" %>

<table border="1" align="center" width="80%">
	<tr>
		<td colspan="2"><h2>회원상세정보</h2></td>
	</tr>
<tr>
		<td width="150">아이디</td>
		<td>
			ID : ${dto.id } / 세션ID : ${sessionScope.cookId }
		</td>
	</tr>
	<tr>
		<td>이름</td>
		<td>${dto.name }</td>
	</tr>
	<tr>
		<td>성별</td>
		<td>
			<c:if test="${dto.gender == 'M'}">
				남자
			</c:if>
			<c:if test="${dto.gender != 'M'}">
				여자
			</c:if>
		</td>
	</tr>
	<tr>
		<td>태어난년도</td>
		<td>${dto.bornYear }</td>
	</tr>
	<tr>
	     <td colspan="2">
	     	<button type="button" onclick="suntaek_proc('sujung', '0', '${dto.no}');">수정하기</button>
	     	&nbsp;&nbsp;&nbsp;
	     	<button type="button" onclick="suntaek_proc('sakje', '1', '${dto.no}');">삭제하기</button>
			&nbsp;&nbsp;&nbsp;
            <button type="button" onclick="suntaek_proc('list', '1', '');">목록으로</button>
	     </td>
	</tr>	
	<tr>
	    <td colspan="2" height="50px">
	 
			<table border="1" width="100%" align="center">
				<tr>
				     <td width="100px">이전글 : </td>
				     <td>
				     	<c:if test="${dto.preId == null}">
				     		이전글이 없습니다.
				     	</c:if>
				     	<c:if test="${dto.preId != null}">
				     		<a href="#" onclick="suntaek_proc('view', '0', '${dto.preNo}');">${dto.preId}</a>
				     	</c:if>
				     </td>
				</tr>
				<tr>
				     <td width="100px">다음글 : </td>
				     <td>
				     	<c:if test="${dto.nxtId == null}">
				     		다음글이 없습니다.
				     	</c:if>
				     	<c:if test="${dto.nxtId != null}">
				     		<a href="#" onclick="suntaek_proc('view', '0', '${dto.nxtNo}');">${dto.nxtId}</a>
				     	</c:if>
				     </td>
				</tr>	
			</table>
	 
	    </td>
	</tr>		
</table>

<script>
	$(document).ready(function(){
		//var content = $("#content").text().replace(/(?:\r\n|\r|\n)/g,'<br/>');
		//$("#content").html(content);
	});
</script>