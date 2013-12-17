MainController = ($scope) ->
    return

ContainerController = ($scope) ->
    $scope.tripName = "London Trip"
    return

MrLoginController = ($scope, $location) ->
    $scope.username = "user"
    $scope.password = "pass"
    $scope.auth = ->
        $location.url('/');
    return

module = angular.module("mrfogg.controllers.main", [])
module.controller("MainController", ["$scope", MainController])
module.controller("ContainerController", ["$scope", ContainerController])
module.controller("MrLoginController", ["$scope","$location", MrLoginController])
