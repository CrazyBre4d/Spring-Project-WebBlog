$(document).ready(function() {
	
	$(window).scroll(function() {
		if ($(this).scrollTop() > 1){  
			$('.page-title').addClass("sticky");
		}
		else{
			$('.page-title').removeClass("sticky");
		}
	});

});

function likePost(button) {
	var postId = button.getAttribute('post-id');
	$.ajax({
		url: '/likePost/' + postId,
		type: 'POST',
		success: function (response) {
			// Обновите отображение количества лайков на странице
			var likesElement = button.nextElementSibling;
			likesElement.textContent = response.likes;
		}
	});
}
