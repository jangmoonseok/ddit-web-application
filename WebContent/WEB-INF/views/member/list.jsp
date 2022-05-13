<%@page import="com.jsp.command.Criteria"%>
<%@page import="com.jsp.command.PageMaker"%>
<%@page import="java.util.Map"%>
<%@page import="com.jsp.dto.MemberVO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<%-- <%
	Map<String, Object> dataMap = (Map<String, Object>)request.getAttribute("dataMap");
	List<MemberVO> memberList = (List<MemberVO>)dataMap.get("memberList");
	PageMaker pageMaker = (PageMaker)dataMap.get("pageMaker");
	Criteria cri = pageMaker.getCri();
	int startPage = pageMaker.getStartPage();
	int endPage = pageMaker.getEndPage();
%> --%>
<c:set var="dataMap" value="${dataMap }"/>
<c:set var="memberList" value="${dataMap.get('memberList') }"/>
<c:set var="pageMaker" value="${dataMap.get('pageMaker') }"/>
<c:set var="cri" value="${pageMaker.cri }"/>
<c:set var="startPage" value="${pageMaker.startPage }"/>
<c:set var="endPage" value="${pageMaker.endPage }"/>
<%-- ${dataMap }<br>
${memberList }<br>
${pageMaker }<br>
${cri }<br>
${startPage }<br>
${endPage }<br> --%>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>AdminLTE 3 | Starter</title>

  <!-- Google Font: Source Sans Pro -->
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
  <!-- Font Awesome Icons -->
  <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/bootstrap/plugins/fontawesome-free/css/all.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/bootstrap/dist/css/adminlte.min.css">
</head>
<body class="hold-transition sidebar-mini">
<div class="wrapper">
  <div >
	 <!-- Main content -->
	<section class="content-header">
	  	<div class="container-fluid">
	  		<div class="row md-2">
	  			<div class="col-sm-6">
	  				<h1>회원목록</h1>  				
	  			</div>
	  			<div class="col-sm-6">
	  				<ol class="breadcrumb float-sm-right">
			        <li class="breadcrumb-item">
			        	<a href="list.do">
				        	<i class="fa fa-dashboard"></i>회원관리
				        </a>
			        </li>
			        <li class="breadcrumb-item active">
			        	목록
			        </li>		        
	    	  </ol>
	  			</div>
	  		</div>
	  	</div>
	</section>
	 
	 
   	<section class="content">
   		<div class="card">
   			<div class="card-header with-border">
   				<button type="button" class="btn btn-primary" onclick="" >회원등록</button>
   				<div id="keyword" class="card-tools" style="width:550px;">
   					 <div class="input-group row">
   					 	<!-- search bar -->
   					 	<!-- sort num -->
					  	<select class="form-control col-md-3" name="perPageNum" 
					  			id="perPageNum" onchange="list_go(1)">					  		  		
					  		<option value="10" ${cri.perPageNum eq 10 ? 'selected' : '' }>정렬개수</option>
					  		<option value="2" ${cri.perPageNum eq 2 ? 'selected' : '' }>2개씩</option>
					  		<option value="3" ${cri.perPageNum eq 3 ? 'selected' : '' }>3개씩</option>
					  		<option value="5" ${cri.perPageNum eq 5 ? 'selected' : '' }>5개씩</option>
					  	</select>
					  	
					  	<!-- search bar -->
					 	<select class="form-control col-md-3" name="searchType" id="searchType">
					 		<option value=""  >검색구분</option>
							<option value="i" ${param.searchType == 'i' ? "selected" : ""}>아이디</option>
							<option value="n" ${param.searchType == 'n' ? "selected" : ""}>이 름</option>
							<option value="p" ${param.searchType == 'p' ? "selected" : ""}>전화번호</option>
							<option value="e" ${param.searchType == 'e' ? "selected" : ""}>이메일</option>				 									
						</select>
						<!-- keyword -->
   					 	<input  class="form-control" type="text" name="keyword" 
										placeholder="검색어를 입력하세요." value="${param.keyword }"/>
						<span class="input-group-append">
							<button class="btn btn-primary" type="button" 
									id="searchBtn" data-card-widget="search" onclick="list_go(1);">
								<i class="fa fa-fw fa-search"></i>
							</button>
						</span>
					<!-- end : search bar -->		
   					 </div>
   				</div>   			
   			</div>
   			<div class="card-body" style="text-align:center;">
    		  <div class="row">
	             <div class="col-sm-12">	
		    		<table class="table table-bordered">
		    			<tr>
		    				<th>사진</th>
		                	<th>아이디</th>
		                	<th>패스워드</th>
		                	<th>이 름</th>
		                	<th>이메일</th>
		                	<th>전화번호</th>
		                	<th>등록날짜</th> <!-- yyyy-MM-dd  -->
		               	</tr>
		               	<c:forEach items="${memberList }" var="member">
		               		<tr onclick="" style="cursor:pointer;">
			     				<td>사진</td>
			     				<td>${member.id }</td>
			     				<td>${member.pwd }</td>
			     				<td>${member.name }</td>
			     				<td>${member.email }</td>
			     				<td>${member.phone.replace('-','') }</td>
			     				<td>${member.regdate }</td>
		     				</tr>
		               	</c:forEach>
		               	<c:if test="${empty memberList }">
			               	<tr>
			     				<td colspan="7" class="text-center">해당 내용이 존재하지 않습니다.</td>
			     			</tr>
		               	</c:if> 
		     		<%-- <%
		     			if(memberList != null){
		     				for(MemberVO member : memberList){		     					
			     				pageContext.setAttribute("member", member);
		     		%>
		     			<tr onclick="" style="cursor:pointer;">
		     				<td>사진</td>
		     				<td>${member.id }</td>
		     				<td>${member.pwd }</td>
		     				<td>${member.name }</td>
		     				<td>${member.email }</td>
		     				<td>${member.phone.replace('-','') }</td>
		     				<td>${member.regdate }</td>
		     			</tr>
		     		<%		
		     				}
		     			}else{
		     		%>
		     			<tr>
		     				<td colspan="7" class="text-center">해당 내용이 존재하지 않습니다.</td>
		     			</tr>
		     		<%
		     			}
		     		%> --%>
		     		
	
		            </table>
    		     </div> <!-- col-sm-12 -->
    	       </div> <!-- row -->
    		</div> <!-- card-body -->
    		<div class="card-footer">
    			<!-- pagination -->
    			<nav aria-label="Navigation">
					<ul class="pagination justify-content-center m-0">
						<li class="page-item">
							<a class="page-link" href="javascript:list_go(1)">
								<i class="fas fa-angle-double-left"></i>
							</a>
						</li>
						<li class="page-item">
							<a class="page-link" href="">
								<i class="fas fa-angle-left"></i>
							</a>						
						</li>
						<c:forEach begin="${startPage }" end="${endPage }" var="page">
							<li class="page-item ${page eq cri.page ? 'active' : ''}">
								<a class="page-link" href="javascript:list_go(${page })">
									${page }
								</a>
							</li>
						</c:forEach>
						<%-- <% 
							for(int i = startPage; i <= endPage; i++){
								pageContext.setAttribute("page", i);
						%>
						<li class="page-item <%= (i == cri.getPage()) ? "active" : "" %>">
							<a class="page-link" href="javascript:list_go(<%= i%>)">
								${page }
							</a>
						</li>
						<%			
							}
						%> --%>
						<li class="page-item">
							<a class="page-link" href="">
								<i class="fas fa-angle-right"></i>
							</a>						
						</li>
						<li class="page-item">
							<a class="page-link" href="">
								<i class="fas fa-angle-double-right"></i>
							</a>						
						</li>
					</ul>
				</nav>
    		</div>
	     </div>
   	</section>
  </div>
  
  <form id="jobForm">
  	<input type="hidden" name="page" value=""/>
  	<input type="hidden" name="perPageNum" value=""/>
  	<input type="hidden" name="searchType" value=""/>
  	<input type="hidden" name="keyword" value=""/>
  </form>
  
  <script>
	  function list_go(page,url){
			//alert(page);
			if(!url) url="list";
			
			var jobForm=$('#jobForm');
			jobForm.find("[name='page']").val(page);
			jobForm.find("[name='perPageNum']").val($('select[name="perPageNum"]').val());
			jobForm.find("[name='searchType']").val($('select[name="searchType"]').val());
			jobForm.find("[name='keyword']").val($('div.input-group>input[name="keyword"]').val())
			
			
			jobForm.attr({
				action:url,
				method:'get'
			}).submit();
			
	}
  </script>
 <!-- Control Sidebar -->
  <aside class="control-sidebar control-sidebar-dark">
    <!-- Control sidebar content goes here -->
    <div class="p-3">
      <h5>Title</h5>
      <p>Sidebar content</p>
    </div>
  </aside>
  <!-- /.control-sidebar -->

  <!-- Main Footer -->
  <footer class="main-footer">
    <!-- To the right -->
    <div class="float-right d-none d-sm-inline">
      Anything you want
    </div>
    <!-- Default to the left -->
    <strong>Copyright &copy; 2014-2021 <a href="https://adminlte.io">AdminLTE.io</a>.</strong> All rights reserved.
  </footer>
</div>
<!-- ./wrapper -->

<!-- REQUIRED SCRIPTS -->

<!-- jQuery -->
<script src="<%=request.getContextPath() %>/resources/bootstrap/plugins/jquery/jquery.min.js"></script>
<!-- Bootstrap 4 -->
<script src="<%=request.getContextPath() %>/resources/bootstrap/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- AdminLTE App -->
<script src="<%=request.getContextPath() %>/resources/bootstrap/dist/js/adminlte.min.js"></script>
</body>
</html>