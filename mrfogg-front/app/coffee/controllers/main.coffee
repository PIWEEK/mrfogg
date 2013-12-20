MainController = ($scope, resource, $timeout, $routeParams, $location) ->
    onUserSuccess = (result) ->
        $scope.user = result

    onUserError = (result)->
        $location.url("/login")

    resource.getUser("me").then(onUserSuccess, onUserError)

    $scope.addTripButton = ()->
        $scope.showTripDialog = true

    $scope.closeTripButton = ()->
        $scope.showTripDialog = false

    $scope.addTaskButton = ()->
        $scope.showTaskDialog = true

    $scope.closeTaskButton = ()->
        $scope.showTaskDialog = false

    $scope.addCardButton = ()->
        if $routeParams.taskId
            $scope.showCardForm = true
        else
            $scope.$emit("flash", {"type":"error", "message": "No tiene seleccionada ninguna tarea"})

    $scope.closeCardButton = ()->
        $scope.showCardForm = false

    $scope.$on("new-card", (data)->
        $scope.showCardForm = false
    )

    $scope.$on("new-trip", (data)->
        $scope.showTripDialog = false
    )

    $scope.isFlashWarnVisible = false
    $scope.isFlashErrorVisible = false
    $scope.isFlashSuccessVisible = false

    $scope.$on("flash", (event, data)->
        $scope.isFlashWarnVisible = true if data.type == "warn"
        $scope.isFlashErrorVisible = true if data.type == "error"
        $scope.isFlashSuccessVisible = true if data.type == "success"
        $scope.flashMessage = data.message

        hideFlash = ()->
            $scope.isFlashWarnVisible = false
            $scope.isFlashErrorVisible = false
            $scope.isFlashSuccessVisible = false

        $timeout(hideFlash, 2000)
    )

    $scope.isTaskPlaceHolderVisible = false
    $scope.isCardPlaceHolderVisible = false
    $scope.isWelcomeVisible = false
    return

UserListController = ($scope, $rootScope, resource) ->
    $scope.addUser = ()->
        users = [$scope.user.email]
        cb = resource.putTripUsers($rootScope.mytrip.id, users)
        cb.then (response)->
            $rootScope.userList = response.data
        $scope.user = {}
        return
    $scope.user = {}

    return


TaskListController = ($scope, $rootScope, resource) ->

    $scope.getTaskClasses = (task) ->
        classes = task.status
        if task.current 
            classes = classes+" current"
        return classes

    $scope.taskToggleStatus = (task) ->
        if task.status == "done"
            task.status = "pending"
        else
            task.status = "done"
        p = resource.updateTask($rootScope.mytrip.id, task.id, task)
        p.then ()->
            console.log("guardado OK, pidendo lista")

    $scope.addTask = ()->
        cb = resource.postTask($rootScope.mytrip.id, $scope.task)
        cb.then (response)->
            $rootScope.tasklist.push(response.data)
        $scope.task = {}
        return

    $scope.task = {}

    return

ContainerController = ($scope) ->
    $scope.tripName = "London Trip"
    return


TripListController = ($scope, $rootScope, $routeParams, $gmStorage, resource) ->
    $rootScope.pageTitle = 'Trips'
    tripIdFromUrl = parseInt($routeParams.tripId, 10)
    taskIdFromUrl = parseInt($routeParams.taskId, 10)
    changed = false
    trip_unset = false
    if not isNaN(tripIdFromUrl) and (tripIdFromUrl != $rootScope.tripId)
        $rootScope.tripId = tripIdFromUrl
        changed = true
    if isNaN(tripIdFromUrl)
        trip_unset = true
    if trip_unset
        $rootScope.tripId = 1
    resource.getTrips($rootScope.userid).then (result) ->
        $scope.triplist = result
        $scope.mytrips = _.remove($scope.triplist, (trip) ->
            return trip.id == $rootScope.tripId
        )
        $rootScope.mytrip = $scope.mytrips[0]
        resource.getTrip($rootScope.tripId).then (result) ->
            $rootScope.userList = result._attrs.members
        resource.getTasks($rootScope.tripId).then (result) ->
            $rootScope.tasklist = result._attrs
            for task in $rootScope.tasklist
                if task.id == taskIdFromUrl
                    task.current = "current"

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

TooltipController = ($scope, $document)->
    $scope.isTooltipVisible = false
    $scope.showTooltip = ()->
        $scope.isTooltipVisible = !$scope.isTooltipVisible

    $scope.hideTooltip = ()->
        $scope.isTooltipVisible = false



CardsController = ($scope, $rootScope, resource, $routeParams)->
    #$scope.widgetHost = "http://localhost:8080"
    $scope.widgetHost = ""

    onSuccess = (data) ->
        $scope.loadedCards = data._attrs
        $scope.isCardPlaceHolderVisible = $scope.loadedCards.length == 0

    onError = (data) ->
        $rootScope.error = true
        $rootScope.errorMessage = data.detail

    tripId = $routeParams.tripId
    taskId = $routeParams.taskId

    $scope.$on("new-card", (event, data)->
        console.log("guardando", data)
        p = resource.postCard(tripId, taskId, data)
        p.then (response)->
            console.log("guardado OK, pidendo lista", response)
            $scope.$broadcast("card-saved", response.data)
            resource.getTaskCards(tripId, taskId).then(onSuccess, onError)
    )

    resource.getTaskCards(tripId, taskId).then(onSuccess, onError) if taskId and tripId
    return

CommentController = ($scope, resource, $routeParams)->
    $scope.inputComment = ""
    $scope.comments = $scope.card.comments

    tripId = $routeParams.tripId
    taskId = $routeParams.taskId
    cardId = $scope.card.id

    $scope.addComment = ()->
        comment = { "user": $scope.user, text: $scope.inputComment }
        $scope.comments.push comment

        commentData =
            text: $scope.inputComment

        $scope.inputComment = ""
        resource.postComment(tripId, taskId, cardId, commentData)

    return

NewCardController = ($scope)->
    $scope.publishCard = ()->
        if $scope.card.title and $scope.card.description
            $scope.$emit("new-card", $scope.card)
            $scope.card = {
                widget: "/assets/client/mrfogg-widget-images.html"
            }
        else
            $scope.$emit("flash", { "type": "error", "message": "Error de validación"})

    $scope.card = {
        widget: "/assets/client/mrfogg-widget-images.html"
    }
    return

NewTripController = ($scope, resource, $location)->
    $scope.publishTrip = ()->
        if $scope.trip.name and $scope.trip.description
            cb = resource.postTrip($scope.trip)
            cb.then(
                (result)->
                    $scope.$emit("new-trip", $scope.trip)
                    $scope.trip = { members : [] }
                    $location.url("/trips/#{result.data.id}")
                    console.log("New trip", result)
                (error)-> $scope.$emit("flash", { "type": "error", "message": error})
            )
        else
            $scope.$emit("flash", { "type": "error", "message": "No se puede crear el viaje, revise los campos"})
        return

    $scope.enterEmail = ()->
        found = false
        for curr in $scope.trip.members
            found = true if $scope.emailInput is curr
        $scope.trip.members.push($scope.emailInput) unless found
        $scope.emailInput = ""
        return

    $scope.trip = { members : [] }
    $scope.emailInput = ""
    return

module = angular.module("mrfogg.controllers.main", [])
module.controller("MainController", ["$scope","resource", "$timeout", "$routeParams", "$location", MainController])
module.controller("ContainerController", ["$scope", ContainerController])
module.controller("UserListController", ["$scope", "$rootScope", "resource", UserListController])
module.controller("TooltipController", ["$scope", "$document", TooltipController])
module.controller("MrLoginController", ["$scope","$rootScope", "$location", "$routeParams", "resource", "$gmAuth", MrLoginController])
module.controller("TripListController", ["$scope", "$rootScope", "$routeParams", "$gmStorage", "resource", TripListController])
module.controller("CardsController", ["$scope", "$rootScope", "resource", "$routeParams", CardsController])
module.controller("CommentController", ["$scope", "resource", "$routeParams", CommentController])
module.controller("NewCardController", ["$scope", NewCardController])
module.controller("TaskListController", ["$scope", "$rootScope", "resource", TaskListController])
module.controller("NewTripController", ["$scope", "resource", "$location", NewTripController])
