ImageWidgetController = ($scope) ->
    $scope.test = "TEST"
    return

console.log(">> ImageWidget")
module = angular.module("mrfogg.widgets", [])
module.controller("ImageWidgetController", ["$scope", ImageWidgetController])
