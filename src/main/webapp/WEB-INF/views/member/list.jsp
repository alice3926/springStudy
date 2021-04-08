<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../include/inc_header.jsp" %>   

<table border="0" align="center" width="80%">
	<tr>
		<td colspan="7">
			<h2>회원목록</h2>
		</td>
	</tr>
	<tr>
		<td colspan="7">

<select name="search_option" id="search_option">
<c:choose>
	<c:when test="${search_option == 'id'}">
		<option value="">- 선택 -</option>	
		<option value="id" selected>아이디</option>
		<option value="name">이름</option>
		<option value="gender">성별</option>
		<option value="id_name_gender">아이디+이름+성별</option>
	</c:when>
	<c:when test="${search_option == 'name'}">
		<option value="">- 선택 -</option>	
		<option value="id">아이디</option>
		<option value="name" selected>이름</option>
		<option value="gender">성별</option>
		<option value="id_name_gender">아이디+이름+성별</option>
	</c:when>
	<c:when test="${search_option == 'gender'}">
		<option value="">- 선택 -</option>	
		<option value="id">아이디</option>
		<option value="name">이름</option>
		<option value="gender" selected>성별</option>
		<option value="id_name_gender">아이디+이름+성별</option>
	</c:when>
	<c:when test="${search_option == 'id_name_gender'}">
		<option value="">- 선택 -</option>	
		<option value="id">아이디</option>
		<option value="name">이름</option>
		<option value="gender">성별</option>
		<option value="id_name_gender" selected>아이디+이름+성별</option>
	</c:when>
	<c:otherwise>
		<option value="" selected>- 선택 -</option>	
		<option value="id">아이디</option>
		<option value="name">이름</option>
		<option value="gender">성별</option>
		<option value="id_name_gender">아이디+이름+성별</option>
	</c:otherwise>
</c:choose>	
</select>

<input type="text" name="search_data" id="search_data" value="${search_data }" style="width: 150px;">
&nbsp;
<input type="button" value="검색" onclick="search();">

<script>
function search() {
	if (confirm('검색 OK?')) {
		$("#span_search_option").text($("#search_option").val());
		$("#span_search_data").text($("#search_data").val());
		suntaek_proc('list', '1', '');
	}
}
</script>

		</td>
	</tr>
	<tr>
		<td style="padding: 10px 0px 5px;">전체 ${totalRecord }건입니다.</td>
	</tr>
		
<c:if test="${totalRecord == 0}">
	<tr>
		<td height="200">
		
			<table border="1" align="center" style="width:100%; height:200px;">
				<tr>
					<td align="center">등록된 내용이 없습니다.</td>
				</tr>
			</table>
			
		</td>
	</tr>	
</c:if>
<c:if test="${totalRecord > 0}">
	<tr>
		<td style="padding: 0 0 20px 0;">
		
<table border="1" align="center" style="width:100%;">
	<tr>
		<th>순번</th>
		<th>아이디</th>
		<th>비밀번호</th>
		<th>이름</th>
		<th>성별</th>
		<th>출생년도</th>
		<th>가입일</th>
	</tr>
	
	<c:forEach var="dto" items="${list }">
		<tr>
			<td>${dto.no}</td>
			<td>
				<a href="#" onclick="suntaek_proc('view', '0', '${dto.no}');">${dto.id}</a>
			</td>
			<td>${dto.passwd}</td>
			<td>${dto.name}</td>
			<td>${dto.gender}</td>
			<td>${dto.bornYear}</td>
			<td>${dto.regiDate}</td>
		</tr>
		<c:set var="jj" value="${jj = jj - 1}"></c:set>
	</c:forEach>					
</table>

		</td>
	</tr>
	<tr>
		<td colspan="7" height="50" align="center">

			<c:set var="value1" value="list"></c:set>
			<a href="#" onclick="suntaek_proc('${value1 }', '1', '');">[첫페이지]</a>
			&nbsp;&nbsp;
				
			<c:if test="${startPage > blockSize}">
				<a href="#" onclick="suntaek_proc('${value1 }', '${startPage - blockSize }', '');">[이전10개]</a>
			</c:if>
			<c:if test="${startPage <= blockSize}">
				[이전10개]
			</c:if>
			&nbsp;	
				
			<c:forEach var="i" begin="${startPage }" end="${lastPage }" step="1">
				<c:if test="${i == pageNumber}">
					[${i }]
				</c:if>
				<c:if test="${i != pageNumber}">
					<a href="#" onclick="suntaek_proc('${value1 }', '${i }', '');">${i }</a>
				</c:if>			    
			</c:forEach>
			
			&nbsp;
			<c:if test="${lastPage < totalPage}">
				<a href="#" onclick="suntaek_proc('${value1 }', '${startPage + blockSize }', '');">[다음10개]</a>
			</c:if>
			<c:if test="${lastPage >= totalPage}">
				[다음10개]
			</c:if>
			
			&nbsp;&nbsp;
			<a href="#" onclick="suntaek_proc('${value1 }', '${totalPage }', '');">[끝페이지]</a>
			
		</td>
	</tr>
</c:if>

	<tr>
		<td colspan="7" height="50" align="right">
			<button type="button" onclick="suntaek_all();">전체목록</button>
			<button type="button" onclick="suntaek_proc('chuga', '1', '');">가입하기</button>
		</td>
	</tr>
</table>
