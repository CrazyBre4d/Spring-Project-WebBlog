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

$('.like-button').click(function() {
	var postId = $(this).data('post-id');
	var button = $(this); // Сохраняем ссылку на кнопку

	$.ajax({
		type: 'POST',
		url: '/like/' + postId,
		success: function() {
			// Успешный ответ от сервера
			var likes = button.data('likes'); // Получаем текущее количество лайков
			likes++; // Увеличиваем количество лайков на 1
			button.data('likes', likes); // Обновляем количество лайков
			button.find('.like-count').text(likes); // Обновляем текст на кнопке
			button.addClass('liked'); // Изменяем состояние кнопки
		},
		error: function() {
			alert('Ошибка при отправке лайка.');
		}
	});
});