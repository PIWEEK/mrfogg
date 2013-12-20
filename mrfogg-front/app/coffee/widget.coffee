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
        $scope.$on("card-saved",(event,data)->
            console.log("Image", data)
            resource.postWidgetData("widgets/images", {
                id: data.id,
                url: $scope.inputImageUrl
            })
        )

VideoWidgetController = ($scope, resource,$sce) ->
    $scope.test = "...wait..."

    $scope.isEditing = false
    $scope.isDisplaying = false

    if $scope.card.id
        $scope.isDisplaying = true
        model = $scope.card.widget.model
        resource.getWidgetData(model).then((response)->
            console.log("response", response)
            $scope.videoUrl = $sce.trustAsResourceUrl(""+response.data)
            # $scope.videoUrl = "" + response.data
            $scope.test = "" + response.data
        )
    else
        $scope.isEditing = true
        $scope.$on("card-saved",(event,data)->
            console.log("Video", data)
            resource.postWidgetData("widgets/videos", {
                id: data.id,
                url: $scope.inputVideoUrl
            })
        )

module = angular.module("mrfogg.widgets", [])
module.controller("ImageWidgetController", ["$scope", "resource", ImageWidgetController])
module.controller("VideoWidgetController", ["$scope", "resource", "$sce", VideoWidgetController])
