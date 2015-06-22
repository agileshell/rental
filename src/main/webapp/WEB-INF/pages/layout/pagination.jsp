<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div>
    <select id="pagination-ps" class="form-control pull-left" style="width: 115px" onchange="return changePs();">
        <option ${ps == 20 ? 'selected' : ''} value="20">每页20条</option>
        <option ${ps == 50 ? 'selected' : ''} value="50">每页50条</option>
        <option ${ps == 100 ? 'selected' : ''} value="100">每页100条</option>
    </select>
    <ul id="pagination-pn" class="pagination pagination-sm no-margin pull-right">
    <c:choose>
      <c:when test="${curn <= 1}">
        <li class="disabled"><a href="javascript:void(0)">首页</a></li>
      </c:when>
      <c:otherwise>
        <li><a href="${url}?curn=1&ps=${ps}${condition}">首页</a></li>
      </c:otherwise>
    </c:choose>
    <c:choose>
      <c:when test="${curn > 1}">
        <li><a href="${url}?curn=${curn-1}&ps=${ps}${condition}">«上一页</a></li>
      </c:when>
      <c:otherwise>
        <li class="disabled"><a href="javascript:void(0)">«上一页</a></li>
      </c:otherwise>
    </c:choose>
    <c:forEach var="i" begin="${curn-9 <= 0 ? 1 : (curn+9 <= totaln ? curn-9 : (totaln-20 > 0 ? totaln-20 : 1))}" end="${curn+19 <= totaln ? curn+19 : totaln}" step="1">
        <li ${curn == i ? "class='active'" : ""}><a href="${url}?curn=${i}&ps=${ps}${condition}">${i}</a></li>
    </c:forEach>
    <c:choose>
      <c:when test="${curn < totaln}">
        <li><a href="${url}?curn=${curn+1}&ps=${ps}${condition}">»下一页</a></li>
      </c:when>
      <c:otherwise>
        <li class="disabled"><a href="javascript:void(0)">»下一页</a></li>
      </c:otherwise>
    </c:choose>
    <c:choose>
      <c:when test="${curn == totaln}">
        <li class="disabled"><a href="javascript:void(0)">末页</a></li>
      </c:when>
      <c:otherwise>
        <li><a href="${url}?curn=${totaln}&ps=${ps}${condition}">末页</a></li>
      </c:otherwise>
    </c:choose>
    </ul>
</div>
<script>
function changePs() {
    var ps = document.getElementById("pagination-ps").value;
    location.href = "${url}?curn=${curn}&ps=" + ps + "${condition}";
    return;
}
</script>