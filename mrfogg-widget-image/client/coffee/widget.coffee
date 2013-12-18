ImageWidgetController = ($scope) ->
    $scope.test = "TEST"
    return

module = angular.module("mrfogg.widgets", [])
module.controller("ImageWidgetController", ["$scope", ImageWidgetController])
