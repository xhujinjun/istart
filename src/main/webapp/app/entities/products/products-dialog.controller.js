(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('ProductsDialogController', ProductsDialogController);

    ProductsDialogController.$inject = ['$timeout','ProductsUpdate','Upload', 'DicDList','DicList','$scope', '$stateParams', '$uibModalInstance', 'entity', 'Products', 'Trip', 'Pictrues'];

    function ProductsDialogController ($timeout,ProductsUpdate,Upload,DicDList,DicList, $scope, $stateParams, $uibModalInstance, entity, Products, Trip, Pictrues) {
        var vm = this;
        var allfiles;
        vm.products = entity;
        //取出大类的选项
        $scope.mySelects=getdata();
        function getdata(){
//        	产品id $stateParams.id
        	console.debug("取出数据");
            return DicList.query({
            	dicTypeCode:'DIC.006.0001'
             },function(data){
            	// productsId  detailTypeId
            	 var field_tourismTypesId = $stateParams.tourismTypesId;
            	 var len =data.length;
            	 if(field_tourismTypesId!=null&&""!=field_tourismTypesId){
            		 for(var i=0;i<len;i++){
                		 if(field_tourismTypesId=data[i].id){
                			 $scope.dic=data[i];
                			 //field_detailTypeId
                			 var dicTypeCode = data[i].dicCode;
                			 if(1==dicTypeCode){
                	            	//跟团游
                	            	dicTypeCode='DIC.006.0002';
                	            }else{
                	            	//主题游
                	            	dicTypeCode='DIC.006.0003';
                	            }
                			 $scope.detaileSelects= getDetaile();
                	            function getDetaile(){
                	                return DicDList.query({
                	                	dicTypeCode:dicTypeCode
                	                 },function(data){
                	                	 var field_detailTypeId=$stateParams.detailTypeId;
                	                	 var len2=data.length;
                	                	 if(null!=field_detailTypeId&&""!=field_detailTypeId){
                	                		 for(var j=0;j<len2;j++){
                    	                		 if(data[j].id==field_detailTypeId){
                    	                			 $scope.dicDTO=data[j];
                    	                			 break;
                    	                		 }
                    	                	 } 
                	                	 }
                	                 });
                	               }
                			 break;
                		 }
                	 }
            	 }
             });
           }
        $scope.change = function(dic){
        	var dicTypeCode;
            if(1==dic.dicCode){
            	//跟团游
            	dicTypeCode='DIC.006.0002';
            }else{
            	//主题游
            	dicTypeCode='DIC.006.0003';
            }
            $scope.detaileSelects= getDetaile();
            function getDetaile(){
                return DicDList.query({
                	dicTypeCode:dicTypeCode
                 });
               }
            
          }
        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('istartApp:productsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
        	var id=$("#id").val();
        	var field_pno=$("#field_pno").val();
        	var field_travelAgentId=$("#field_travelAgentId").val();
        	var field_phone=$("#field_phone").val();
        	var field_title=$("#field_title").val();
        	var field_price=$("#field_price").val();
        	var field_pricedesc=$("#field_pricedesc").val();
        	var field_preferential=$("#field_preferential").val();
        	var field_startdate=$("#field_startdate").val();
        	var field_startadderss=$("#field_startadderss").val();
        	var field_endadderss=$("#field_endadderss").val();
        	var field_days=$("#field_days").val();
        	var field_costdesc=$("#field_costdesc").val();
        	var field_route=$("#field_route").val();
        	var field_detaildesc=$("#field_detaildesc").val();
        	var field_bookNotice=$("#field_bookNotice").val();
        	var field_detailTrip=$("#detailTrip").val();
        	
        	var dicId=$("#dicId").val();
        	var detaile=$("#detaile").val();
        	if(null !=id&&"" !=id){
        		//有id，更新数据
        		ProductsUpdate.update({
        			id:id,
        			pno: field_pno,
        			travelAgentId: field_travelAgentId,
                    phone: field_phone,
                    title: field_title,
                    price: field_price,
                    pricedesc: field_pricedesc,
                    preferential: field_preferential,
                    startdate: field_startdate,
                    startadderss: field_startadderss,
                    endadderss: field_endadderss,
                    days: field_days,
                    costdesc: field_costdesc,
                    route: field_route,
                    detaildesc: field_detaildesc,
                    bookNotice:field_bookNotice,
                    tourismTypesId: dicId,
                    detailTypeId: detaile,
                    detailTrip:field_detailTrip
        		},function(data){
        			$uibModalInstance.dismiss('cancel');
        			//window.location.href="#/products";
                	//
                	//parent.location.reload();
        		})
        	}else{
        		//新增
        		if(allfiles.length==0){
            		alert("还没上传图片");
            		return ;
            	}
        		if (allfiles && allfiles.length) {
            		Upload.upload({
              		  url: 'api/saveproduct',
                        data: {
                            files: allfiles,
                            field_pno: field_pno,
                            field_travelAgentId: field_travelAgentId,
                            field_phone: field_phone,
                            field_title: field_title,
                            field_price: field_price,
                            field_pricedesc: field_pricedesc,
                            field_preferential: field_preferential,
                            field_startdate: field_startdate,
                            field_startadderss: field_startadderss,
                            field_endadderss: field_endadderss,
                            field_days: field_days,
                            field_costdesc: field_costdesc,
                            field_route: field_route,
                            field_detaildesc: field_detaildesc,
                            field_bookNotice:field_bookNotice,
                            dicId: dicId,
                            detaile: detaile,
                            detailTrip:field_detailTrip
                        }
                    }).then(function (response) {
                    	//app/entities/product/products.html
                    	window.location.href="#/products";
                    	//
                    	parent.location.reload();
                    })
                    }
        	}
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.startdate = false;
        vm.datePickerOpenStatus.dataCreateDatetime = false;
        vm.datePickerOpenStatus.dataUpdateDatetime = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
//        vm.uploadFiles = function (files) {
//        	alert("xx")
//            $scope.files = files;
//        	var ss = files;
//        	alert(ss.length)
//            if (ss && ss.length) {
//            	  Upload.upload({
//            		  url: 'api/mytest',
//                      data: {
//                          files: ss,username: 'muy',
//                      }
//                  })
//            }}
        
        vm.uploadFiles = function (files) {
        	$scope.files = files;
        	allfiles=files;
            return allfiles;
             }
    }
})();
