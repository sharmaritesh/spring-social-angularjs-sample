angular
	.module('ssa', [ 'ui.router', 'ngResource' ])
	.config(function($stateProvider, $urlRouterProvider, $httpProvider) {
		$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
		$httpProvider.defaults.headers.common["Accept"] = "application/json";
		$httpProvider.defaults.headers.common["Content-Type"] = "application/json";
		
		$stateProvider.state('site', {
            'abstract': true,
            views: {
                'navbar@': {
                    templateUrl: 'app/navbar/navbar.html',
                    controller: 'NavbarController'
                }
            }
        });
	})
	.run(function ($rootScope) {
		$rootScope.$on('$stateChangeStart', function (event, toState, toStateParams) {
        });

        $rootScope.$on('$stateChangeSuccess',  function(event, toState, toParams, fromState, fromParams) {
            $rootScope.previousStateName = fromState.name;
            $rootScope.previousStateParams = fromParams;
            
            console.log(fromState.name + ' => ' + toState.name);
        });

	})
	.filter('trusted', ['$sce', function ($sce) {
	    return function(url) {
	        return $sce.trustAsResourceUrl(url);
	    };
	}]);