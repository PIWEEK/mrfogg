ImageWidgetController = ($scope, resource) ->
    $scope.test = "...wait..."
    model = $scope.card.widget.model
    resource.getWidgetData(model).then((response)->
        console.log("Image: ", response.data)
        $scope.imageUrl = "" + response.data[0]
        $scope.test = "" + response.data[0]
    )

module = angular.module("mrfogg.widgets", [])
module.controller("ImageWidgetController", ["$scope", "resource", ImageWidgetController])
