var hlist=(list)=>{
      var li='<div class="dym-card">'
         +'   <h2>추천 호텔</h2>'
         +'   <ol name="dym-chs" class="dym-group">';
      $.each(list,(i,val)=>{
         li+='      <li class="dym-item ">'
            +'   <a id ="choosedCity" data-icon="forward after" data-track-dym-result="true" data-name="<strong>'+val.destination+'</strong>" data-class="" data-eid="27538638">'
            +'            <h3 class="dym-item-title"><strong>검색어에 가까운 지역 =>'+val.destination+'</strong></h3>'
            +'            <p class="dym-item-geo KR flag">'
            +'               <img src="//images.skyscnr.com/sttc/hotels-front-end/common/img/country_flags/KR.gif">'
            +'               <strong class="dym-poi-type">'+val.hotelName+'</strong> --- <strong>'+val.price+' 원</strong>'
            +'            </p>'
            +'         </a>'
            +'      </li>'
         });
         li+='</ol>'
         +'</div>'
      $('#chooseHt').append(li);
   };
   $.get(hlist(),()=>{
	      $('#choosedCity').click(()=>{
	         alert('클릭됨');
	      });
	      $('<button/>')
	      .insertAfter($("ol"))
	      .addClass('more')
	      .text('더 보기')
	      .click()
	      ;
	   });