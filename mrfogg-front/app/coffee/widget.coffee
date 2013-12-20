ImageWidgetController = ($scope, resource) ->
    $scope.test = "...wait..."

    $scope.isEditing = false
    $scope.isDisplaying = false

    if $scope.card.id
        $scope.isDisplaying = true
        model = $scope.card.widget.model
        resource.getWidgetData(model).then((response)->
            $scope.imageUrl = "" + response.data[0]
            $scope.test = "" + response.data[0]
        )
    else
        $scope.isEditing = true

VideoWidgetController = ($scope, resource) ->
    $scope.test = "...wait..."

    $scope.isEditing = false
    $scope.isDisplaying = false

    if $scope.card.id
        $scope.isDisplaying = true
        model = $scope.card.widget.model
        resource.getWidgetData(model).then((response)->
            $scope.videoUrl = "" + response.data
            $scope.test = "" + response.data
        )
    else
        $scope.isEditing = true

module = angular.module("mrfogg.widgets", [])
module.controller("ImageWidgetController", ["$scope", "resource", ImageWidgetController])
module.controller("VideoWidgetController", ["$scope", "resource", VideoWidgetController])
