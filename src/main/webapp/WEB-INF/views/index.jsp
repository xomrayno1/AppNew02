<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"%>

  
      <div class="right_col" role="main">
          <!-- top tiles -->
          <div class="row" style="display: inline-block;" >
          <div class="tile_count">
            
          </div>
        </div>
          <!-- /top tiles -->
  
 		</div>
 		 
  
 	
<script>
$(document).ready(function(){
	$('#home').parents("li").addClass('active').siblings().removeClass("active");
	$('#home').addClass('current-page').siblings().removeClass("current-page");
	$('#home').parents().show();
});
</script>