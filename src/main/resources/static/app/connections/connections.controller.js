'use strict';

angular.module('ssa').controller('ConnectionsController',
		function($scope, $state, Connections) {

			$scope.socialConnections = [];
			$scope.redirectToSocialConnectPage = function(provider) {
				console.log(provider);
				$state.go('connections.' + provider);
			}
			$scope.manageConnection = function(provider) {
				console.log(provider);
				$state.go('connections.' + provider + ".manage");
			}
			
			var init = function() {
				var fetchRequest = Connections.query();
				fetchRequest.$promise.then(function success(response) {
					$scope.socialConnections = response;
				}, function error(error) {
					console.log(error);
				});
			}
			init();
		});
