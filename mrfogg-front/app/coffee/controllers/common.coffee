ContainerController = ($scope) ->
    $scope.tripName = "London Trip"
    return

module = angular.module("mrfogg.controllers.common", [])
module.controller("ContainerController", ["$scope", ContainerController])
