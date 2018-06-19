;$(function(){
	var init = function (){
		initBuyBtn();
		$('#addToCart').click(addProductToCart);
		$('#addProductPopup .count').change(calculateCost);
		$('#loadMore').click(loadMoreProducts);
		initSearchForm();
	};

	var showAddProductPopup = function (){
		var idProduct = $(this).attr('data-id-product');
		var product = $('#product'+idProduct);
		$('#addProductPopup').attr('data-id-product', idProduct);
		$('#addProductPopup .product-image').attr('src', product.find('.thumbnail img').attr('src'));
		$('#addProductPopup .name').text(product.find('.name').text());
		var price = product.find('.price').text();
		$('#addProductPopup .price').text(price);
		$('#addProductPopup .category').text(product.find('.category').text());
		$('#addProductPopup .producer').text(product.find('.producer').text());
		$('#addProductPopup .count').val(1);
		$('#addProductPopup .cost').text(price);
		$('#addToCart').removeClass('hidden');
		$('#addToCartIndicator').addClass('hidden');
		$('#addProductPopup').modal({
			show:true
		});
	};
	var initBuyBtn = function(){
		$('.buy-btn').click(showAddProductPopup);
	};
	var addProductToCart = function (){
		var idProduct = $('#addProductPopup').attr('data-id-product');
		var count = $('#addProductPopup .count').val();
		$('#addToCart').addClass('hidden');
		$('#addToCartIndicator').removeClass('hidden');
		setTimeout(function(){
			var data = {
				totalCount : count,
				totalCost : 2000
			};
			$('#currentShoppingCart .total-count').text(data.totalCount);
			$('#currentShoppingCart .total-cost').text(data.totalCost);
			$('#currentShoppingCart').removeClass('hidden');
			$('#addProductPopup').modal('hide');
		}, 800);
	};
	var calculateCost = function(){
		var priceStr = $('#addProductPopup .price').text();
		var price = parseFloat(priceStr.replace('$',' '));
		var count = parseInt($('#addProductPopup .count').val());
		var min = parseInt($('#addProductPopup .count').attr('min'));
		var max = parseInt($('#addProductPopup .count').attr('max'));
		if(count >= min && count <= max) {
			var cost = price * count;
			$('#addProductPopup .cost').text('$ '+cost);
		} else {
			$('#addProductPopup .count').val(1);
			$('#addProductPopup .cost').text(priceStr);
		}
	};
	var loadMoreProducts = function (){
		$('#loadMore').addClass('hidden');
		$('#loadMoreIndicator').removeClass('hidden');
		setTimeout(function(){
			$('#loadMoreIndicator').addClass('hidden');
			$('#loadMore').removeClass('hidden');
		}, 800);
	};
	var initSearchForm = function (){
		$('#allCategories').click(function (){
			$('.categories .search-option').prop('checked', $(this).is(':checked'));
		});
		$('.categories .search-option').click(function (){
			$('#allCategories').prop('checked', false);
		});
		$('#allProducers').click(function (){
			$('.producers .search-option').prop('checked', $(this).is(':checked'));
		});
		$('.producers .search-option').click(function (){
			$('#allProducers').prop('checked', false);
		});
	};

	init();
});