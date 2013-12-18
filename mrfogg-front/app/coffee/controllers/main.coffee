MainController = ($scope) ->
    return

ContainerController = ($scope) ->
    $scope.tripName = "London Trip"
    return

TaskListController = ($scope, $rootScope, $routeParams, resource) ->
    $rootScope.pageTitle = 'Tasks'
    $rootScope.tripid = parseInt($routeParams.tripid, 10)
    #localstorage
    resource.getTasks($rootScope.tripid).then (result) ->
        $scope.tasklist = result
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

TripListController = ($scope, $rootScope, resource) ->
    $rootScope.pageTitle = 'Trips'
    resource.getTrips($rootScope.userid).then (result) ->
        $scope.triplist = result
        tripId = 1
        $scope.mytrips = _.remove($scope.triplist, (trip) -> 
            return trip.id == tripId
        ) 
        $scope.mytrip = $scope.mytrips[0]
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

module = angular.module("mrfogg.controllers.main", [])
module.controller("MainController", ["$scope", MainController])
module.controller("ContainerController", ["$scope", ContainerController])
module.controller("UserController", ["$scope", "$rootScope", "$routeParams", "resource", UserController])
module.controller("UserListController", ["$scope", "$rootScope", "resource", UserListController])
module.controller("MrLoginController", ["$scope","$rootScope", "$location", "$routeParams", "resource", "$gmAuth", MrLoginController])
module.controller("TripListController", ["$scope", "$rootScope", "resource", TripListController])
module.controller("TaskListController", ["$scope", "$rootScope", "$routeParams", "resource", TaskListController])
module.controller("CardsController", ["$scope", "$rootScope", "resource", "$routeParams", CardsController])
module.controller("CommentController", ["$scope", "resource", "$routeParams", CommentController])

