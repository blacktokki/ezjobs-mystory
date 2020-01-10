<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<div class="modal fade review-modal" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
	    <div class="modal-content">
	    	<div class="modal-body row">
				<div class="card col" id="wordChange">
					<div class="card-header card-title">단어 교체</div>
					<div class="card-body">
						<ul class="card-text list-group form-inline"></ul>
					</div>
				</div>
				<div class="col d-flex align-items-center justify-content-center">
					<div class="d-flex flex-column">
						<a href="#" class="btn btn-primary btn-load">유사도검사</a>
						<a href="#" class="btn btn-primary btn-check">맞춤법검사</a>
					</div>
				</div>
				<div class="card col" id="compare">
					<div class="card-header card-title">비교 하기</div>
					<div class="card-body">
						<ul class="card-text list-group list-group-flush"></ul>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<a href="#" class="btn btn-primary btn-apply">내용 적용하기</a>
			</div>
		</div>
	</div>
</div>
