	var goodsReceipt = [];
 	var goodsIssued = [];
 	
 	var receiptLength = objectReceipt.length;
 	var issuedLength = objectIssued.length;
 	
 	for(var i = 0 ; i < receiptLength ; i++){
 		goodsReceipt[i] = objectReceipt[i].value;
 		goodsIssued[i] = objectIssued[i].value
 	}
 	
	
	$(document).ready(function(){
 		getChartData();
 	})
	function getChartData(){
		 var ctx = document.getElementById('myChart').getContext('2d');
		 var myChart = new Chart(ctx, {
		     type: 'bar',
		     data: {
		         labels: ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"],
		         datasets: [{
			        	 label: 'Tong Gia Tri Nhap ', 
			             data: goodsReceipt,
			             backgroundColor: [
			                 'rgba(0, 28, 255, 0.7)',   
			            	 'rgba(0, 28, 255, 0.7)',
			            	 'rgba(0, 28, 255, 0.7)',   
			            	 'rgba(0, 28, 255, 0.7)',   
			            	 'rgba(0, 28, 255, 0.7)',
			            	 'rgba(0, 28, 255, 0.7)',  
			            	 'rgba(0, 28, 255, 0.7)',   
			            	 'rgba(0, 28, 255, 0.7)',
			            	 'rgba(0, 28, 255, 0.7)',   
			            	 'rgba(0, 28, 255, 0.7)',   
			            	 'rgba(0, 28, 255, 0.7)',
			            	 'rgba(0, 28, 255, 0.7)' 
			             ],
			             borderColor: [
			                 'rgba(255, 99, 132, 1)',
			            	 'rgba(255, 99, 132, 1)',
			            	 'rgba(255, 99, 132, 1)',
			            	 'rgba(255, 99, 132, 1)',
			            	 'rgba(255, 99, 132, 1)',
			            	 'rgba(255, 99, 132, 1)',
			            	 'rgba(255, 99, 132, 1)',
			            	 'rgba(255, 99, 132, 1)',
			            	 'rgba(255, 99, 132, 1)',
			            	 'rgba(255, 99, 132, 1)',
			            	 'rgba(255, 99, 132, 1)',
			            	 'rgba(255, 99, 132, 1)'
			             ],
			             borderWidth: 1
			         },
		         	{
			        	 label: 'Tong Gia Tri Xuat', 
			             data: goodsIssued,
			             backgroundColor: [
			            	 'rgba(255, 208, 138, 1)',
			            	  'rgba(255, 208, 138, 1)',
			            	 'rgba(255, 208, 138, 1)',
			            	 'rgba(255, 208, 138, 1)',
			            	 'rgba(255, 208, 138, 1)',
			            	 'rgba(255, 208, 138, 1)',
			            	 'rgba(255, 208, 138, 1)',
			            	  'rgba(255, 208, 138, 1)',
			            	 'rgba(255, 208, 138, 1)',
			            	 'rgba(255, 208, 138, 1)',
			            	  'rgba(255, 208, 138, 1)',
			            	  'rgba(255, 208, 138, 1)'
			             ],
			             borderColor: [
			                 'rgba(255, 99, 132, 1)',
			                 'rgba(255, 99, 132, 1)',
			                 'rgba(255, 99, 132, 1)',
			                 'rgba(255, 99, 132, 1)',
			                 'rgba(255, 99, 132, 1)',
			                 'rgba(255, 99, 132, 1)',
			                 'rgba(255, 99, 132, 1)',
			                 'rgba(255, 99, 132, 1)',
			                 'rgba(255, 99, 132, 1)',
			                 'rgba(255, 99, 132, 1)',
			                 'rgba(255, 99, 132, 1)',
			                 'rgba(255, 99, 132, 1)'
			             ],
			             borderWidth: 1
			        } 
		         ] 
		     },
		     options: {
		         scales: {
		             yAxes: [{
		                 ticks: {
		                     beginAtZero: true
		                 }
		             	 
		             }]
		         }
		     }
		 });
	}
 	
 	function float2vnd(value){
 		  return value.toLocaleString('it-IT', {style : 'currency', currency : 'VND'});
 		}