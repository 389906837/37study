$.fn.threeSlide = function(ulSelector, liSelector, speed, delay, prevBtn, nextBtn) {
	var container = $(this);
	var ul = container.find(ulSelector);
	var items = ul.find(liSelector);
	if (items.length) {
		var count = items.length;
		while(items.length < 5) {
			ul.append(items.clone());
			items = ul.find(liSelector);
		}
		var index = 0;
		var width = items.width();
		var inslide = false;

		items.removeClass('active');
		ul.width(width * items.length);

		function moveTo(to) {
			if (inslide) return false;
			items.removeClass('active');
			var current = items.filter(function(i) { return i % count === to }).addClass('active').eq(0);
			while (current.prevAll().length < 2) {
				ul.prepend(ul.find('li:last'));
				ul.css('marginLeft', parseInt(ul.css('marginLeft')) - width);
			}
			while (current.nextAll().length < 2) {
				ul.append(ul.find('li:first'));
				ul.css('marginLeft', parseInt(ul.css('marginLeft')) + width);
			}
			inslide = true;
			var currentIndex = current.prevAll().length;
			var target = width - currentIndex * width;
			var from = parseInt(ul.css('marginLeft'));
			if (Math.abs(from - count * width - target) < Math.abs(from - target)) {
				ul.css('marginLeft', from - count * width);
			} else if (Math.abs(from + count * width - target) < Math.abs(from - target)) {
				ul.css('marginLeft', from + count * width);

			}
			ul.animate({ marginLeft: target }, speed, function() {
				inslide = false;
			});
			container.trigger('slide', to);
			return true;
		}
		function next() {
			var n = (index + 1) % count;
			if (moveTo(n)) {
				index = n;
				autoNext();
			}
		}
		function prev() {
			var p = (index + count - 1) % count;
			if (moveTo(p)) {
				index = p;
				autoNext();
			}
		}
		var auto = false;
		function autoNext() {
			clearTimeout(auto);
			auto = setTimeout(function() {
				next();
				autoNext();
			}, delay);
		}
		if (prevBtn) {
        		$(prevBtn).on('click', function() {
        			prev();
        		});
		}
		if (nextBtn) {
        		$(nextBtn).on('click', function() {
        			next();
        		});
		}
		setTimeout(function() {
        		moveTo(0);
        		autoNext();
		});
	}
	return this;
};