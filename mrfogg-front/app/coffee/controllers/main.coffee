MainController = ($scope) ->
    $scope.addTaskButton = ()->
        $scope.showTaskDialog = true

    $scope.closeTaskButton = ()->
        $scope.showTaskDialog = false

    $scope.addTripButton = ()->
        $scope.showTripDialog = true

    $scope.closeTripButton = ()->
        $scope.showTripDialog = false

    $scope.addCardButton = ()->
        $scope.showCardForm = true

    $scope.closeCardButton = ()->
        $scope.showCardForm = false

    $scope.$on("new-card", (data)->
        $scope.showCardForm = false
    )
    return

ContainerController = ($scope) ->
    $scope.tripName = "London Trip"
    return

UserListController = ($scope, $rootScope, resource) ->
    $rootScope.pageTitle = 'Users'

    onSuccess = (data) ->
        $scope.userlist = data

    onError = (data) ->
        $scope.error = true
        $scope.errorMessage = data.detail

    promise = resource.getUsers()
    promise = promise.then(onSuccess, onError)

    return

UserController = ($scope, $rootScope, $routeParams, resource) ->
    $rootScope.pageTitle = 'My profile'
    $rootScope.userid = parseInt($routeParams.uid, 10)
    $rootScope.userid = "me" if not $rootScope.userid

    resource.getUser($rootScope.userid).then (result) ->
        $scope.user = result

    return

TripListController = ($scope, $rootScope, $routeParams, $gmStorage, resource) ->
    $rootScope.pageTitle = 'Trips'
    tripIdFromUrl = parseInt($routeParams.tripId, 10)
    console.log "tripid "+$rootScope.tripId
    changed = false
    trip_unset = false
    if not isNaN(tripIdFromUrl) and (tripIdFromUrl != $rootScope.tripId)
        $rootScope.tripId = tripIdFromUrl
        changed = true
    if isNaN(tripIdFromUrl)
        trip_unset = true
    if trip_unset
        $rootScope.tripId = 1
    if trip_unset or changed    
        resource.getTrips($rootScope.userid).then (result) ->
            $scope.triplist = result
        $scope.mytrips = _.remove($scope.triplist, (trip) ->
                return trip.id == $rootScope.tripId
            ) 
            $rootScope.mytrip = $scope.mytrips[0]
            resource.getTasks($rootScope.tripId).then (result) ->
                $rootScope.tasklist = result._attrs

    return

MrLoginController = ($scope, $rootScope, $location, $routeParams, resource, $gmAuth) ->
    $rootScope.pageTitle = 'Login'
    $rootScope.pageSection = 'login'

    $scope.form = {}
    $scope.submit = ->
        email = $scope.form.email
        password = $scope.form.password

        $scope.loading = true

        onSuccess = (user) ->
            $gmAuth.setUser(user)
            $rootScope.auth = user
            $location.url("/")

        onError = (data) ->
            $scope.error = true
            $scope.errorMessage = data.detail

        promise = resource.login(email, password)
        promise = promise.then(onSuccess, onError)
        promise.then ->
            $scope.loading = false

    return

TooltipController = ($scope)->
    $scope.showTooltip = ()=>
       $(".icon-menu").siblings(".tooltip").toggle();

CardsController = ($scope, $rootScope, resource, $routeParams)->
    $scope.widgetHost = "http://localhost:8080"

    onSuccess = (data) ->
        console.log("SUCCESS", data)
        $scope.loadedCards = data._attrs

        for card in data._attrs
            console.debug("card", card)

    onError = (data) ->
        console.log("error " + data)
        $rootScope.error = true
        $rootScope.errorMessage = data.detail

    tripId = $routeParams.tripId
    taskId = $routeParams.taskId

    $scope.$on("new-card", (data)->
        console.log("NEW CardsController", data)
    )

    resource.getTaskCards(tripId, taskId).then(onSuccess, onError) if taskId and tripId
    return

CommentController = ($scope, resource, $routeParams)->
    console.log($scope.card)
    $scope.inputComment = ""
    $scope.comments = $scope.card.comments

    tripId = $routeParams.tripId
    taskId = $routeParams.taskId
    cardId = $scope.card.id

    $scope.addComment = ()->
        user = { "id": 1, "email": "alotor@gmail.com", "avatar": "http://gravatar.com/alotor" }
        comment = { "user": user, text: $scope.inputComment }
        $scope.comments.push comment

        commentData =
            text: $scope.inputComment

        $scope.inputComment = ""
        resource.postComment(tripId, taskId, cardId, commentData)

    return

NewCardController = ($scope)->
    $scope.publishCard = ()->
        $scope.$emit("new-card", $scope.card)
        $scope.card = {}

    $scope.card = {}

    return

module = angular.module("mrfogg.controllers.main", [])
module.controller("MainController", ["$scope", MainController])
module.controller("ContainerController", ["$scope", ContainerController])
module.controller("UserController", ["$scope", "$rootScope", "$routeParams", "resource", UserController])
module.controller("UserListController", ["$scope", "$rootScope", "resource", UserListController])
module.controller("TooltipController", ["$scope", TooltipController])
module.controller("MrLoginController", ["$scope","$rootScope", "$location", "$routeParams", "resource", "$gmAuth", MrLoginController])
module.controller("TripListController", ["$scope", "$rootScope", "$routeParams", "$gmStorage", "resource", TripListController])
module.controller("CardsController", ["$scope", "$rootScope", "resource", "$routeParams", CardsController])
module.controller("CommentController", ["$scope", "resource", "$routeParams", CommentController])
module.controller("NewCardController", ["$scope", NewCardController])

