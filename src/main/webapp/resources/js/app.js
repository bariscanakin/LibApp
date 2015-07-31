var app = angular.module('LibApp', ['ngRoute', 'ui.bootstrap', 'ngLoadingSpinner']);

app.factory("services", [ "$http", function($http) {
	var serviceBase = '';
	var obj = {};
	obj.getBooks = function() {
		return $http.get(serviceBase + 'books');
	}
	obj.getBook = function(bookId) {
		return $http.get(serviceBase + 'book/' + bookId);
	}
	obj.insertBook = function(book) {
		return $http.post(serviceBase + 'books', book).then(function(result) {
			return result;
		});
	}
	obj.updateBook = function(book) {
		return $http.put(serviceBase + 'books', book).then(function(result) {
			return result;
		});
	}
	
	obj.deleteBook = function(bookId) {
		return $http.delete(serviceBase + 'books/' + bookId).then(function(result) {
			return result;
		});
	}
	
	obj.generateCaptcha = function() {
		return $http.get('captcha');
	}

	return obj;
} ])

app.controller('listCtrl', function($scope, $modal, services) {
	services.getBooks().then(function(data) {
		var result = data.data;
		if (result.success) {
			$scope.books = result.data;
		} else {
			// show error msg
		}
	});

	$scope.showDeleteModal = function(book) {
		$scope.book = book;
		var modalInstance = $modal.open({
            templateUrl: 'delete_book_modal',
            controller: $scope.deleteModel,
            resolve: {
                book: function() {
                    return $scope.book;
                },
                books: function() {
                	return $scope.books;
                }
            }
        });
	};
	
	$scope.deleteModel = function($scope, $modalInstance, services, book, books) {
		$scope.book = book;
		$scope.books = books;
		$scope.alerts = [];
		
		$scope.closeAlert = function(index) {
			$scope.alerts.splice(index, 1);
		};
		
        $scope.cancelDelete = function() {
        	$modalInstance.dismiss('cancel');
        };

        $scope.deleteBook = function() {
        	services.deleteBook($scope.book.id).then(function(result) {
        		if (result.data.success) {
        			$scope.books.splice($scope.books.indexOf($scope.book), 1);
        			$modalInstance.dismiss('cancel');
				} else {
					$scope.alerts.splice(0, 1);
			        $scope.alerts.push({
			            type: 'danger',
			            msg: result.data.message
			        });
				}
        	});
        };
	};
	
	$scope.open = function(book) {
		services.generateCaptcha().then(function(result) {
			if (result.data.success) {
				$scope.captchaImg = result.data.data;
				var modalInstance = $modal.open({
					templateUrl: 'add_update_modal',
					controller: $scope.model,
					resolve: {
						book: function() {
							return book;
						},
						books: function() {
							return $scope.books;
						},
						captchaImg: function() {
							return $scope.captchaImg;
						}
					}
				});
			}
		});
    };
    
    $scope.model = function($scope, $modalInstance, services, book, books, captchaImg) {
		$scope.book = book || {};
		$scope.books = books;
		$scope.captchaImg = captchaImg;
		$scope.alerts = [];
		
		$scope.closeAlert = function(index) {
			$scope.alerts.splice(index, 1);
		};
		
        $scope.cancel = function() {
        	$modalInstance.dismiss('cancel');
        };

        $scope.saveBook = function() {
			if ($scope.book.id) {
				services.updateBook($scope.book).then(function(result) {
	        		if (result.data.success) {
	            		$scope.book.captchaValue = "";
	            		$modalInstance.dismiss('cancel');
					} else {
						$scope.alerts.splice(0, 1);
				        $scope.alerts.push({
				            type: 'danger',
				            msg: result.data.message
				        });
					}
	        	});
			} else {
				services.insertBook($scope.book).then(function(result) {
	        		if (result.data.success) {
	        			$scope.book.captchaValue = "";
	        			$scope.books.push($scope.book);
	        			$modalInstance.dismiss('cancel');
					} else {
						$scope.alerts.splice(0, 1);
				        $scope.alerts.push({
				            type: 'danger',
				            msg: result.data.message
				        });
					}
	        	});
			}
		};
	};
	
});

app.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/', {
		title : 'LibApp',
		templateUrl : 'books.html',
		controller : 'listCtrl'
	}).otherwise({
		redirectTo : '/'
	});
} ]);