MainController = ($scope) ->
    return

ContainerController = ($scope) ->
    $scope.tripName = "London Trip"
    return

module = angular.module("mrfogg.controllers.main", [])
module.controller("MainController", ["$scope", MainController])
module.controller("ContainerController", ["$scope", ContainerController])
