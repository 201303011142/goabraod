//indexJs

	$(function(){
		//姓名
//		var regIdcar = /^([a-zA-Z0-9\u4e00-\u9fa5\·]{1,20})$/;
//		$('.firstInput').blur(function(){
//			var firstInputVal = $(this).val();
//			if(!firstInputVal){
//				$(this).css({'border-bottom':'2px solid red'});
//				alert('姓名不能为空!');
////				$(this).focus();
//			}
//			else if(!regIdcar.test(firstInputVal)){
//				$(this).css({'border-bottom':'2px solid red'});
//				alert('输入的姓名有误!');
////				$(this).focus();
//			}
//			else{
//				$(this).css({'border-bottom':'2px solid #ccc'});
//			}
//		});
		//验证手机号码
//		var regIphone = /^1[34578]\d{9}$/;
//		$('.secondInput').blur(function(){
//			var firstInputVal = $(this).val();
//			if(!firstInputVal){
//				$(this).css({'border-bottom':'2px solid red'});
//				alert('手机号码不能为空!');
////				$(this).focus();
//			}
//			else if(!regIphone.test(firstInputVal)){
//				$(this).css({'border-bottom':'2px solid red'});
//				alert('输入的手机号码有误!');
////				$(this).focus();
//			}
//			else{
//				$(this).css({'border-bottom':'2px solid #ccc'});
//			}
//		});
		
		$('.upImg img').click(function(){
			$('html').animate({scrollTop:$('.upImg img').offset().top-5000},1200);
		});	
				
		
		//时间
		//laydate.skin('dahong')
		laydate({
			elem :'#yTime',
			format: 'YYYY-MM-DD',
			event : 'focus',
			festival: true,
			//min: laydate.now(+0),
			min: laydate.now(+0.5, 'YYYY-MM-DD'),
			istoday:true,
  			max: '2020-10-01', //最大日期	
		});
	});