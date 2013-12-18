MainController = ($scope) ->
    return

ContainerController = ($scope) ->
    $scope.tripName = "London Trip"
    return

TaskListController = ($scope, $rootScope, $routeParams, $gmStorage, resource) ->
    $rootScope.pageTitle = 'Tasks'
    $rootScope.tripid = parseInt($routeParams.tripid, 10)
    $scope.$on('tripid', (event, data) ->
        console.log data
        resource.getTasks($scope.tripid).then (result) ->
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

TripListController = ($scope, $rootScope, $routeParams, $gmStorage, resource) ->
    $rootScope.pageTitle = 'Trips'
    $rootScope.tripid = parseInt($routeParams.tripid, 10)
    $rootScope.tripid = 1 if not $rootScope.tripid
    $scope.$emit('tripid', $rootScope.tripid);
    resource.getTrips($rootScope.userid).then (result) ->
        $scope.triplist = result
        tripId = 1
        $scope.mytrips = _.remove($scope.triplist, (trip) -> 
            return trip.id == $rootScope.tripid
        ) 
        $scope.mytrip = $scope.mytrips[0]
        #$gmStorage.set("tripid", $scope.mytrip)
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

module = angular.module("mrfogg.controllers.main", [])
module.controller("MainController", ["$scope", MainController])
module.controller("ContainerController", ["$scope", ContainerController])
module.controller("UserListController", ["$scope", "$rootScope", "resource", UserListController])
module.controller("MrLoginController", ["$scope","$rootScope", "$location", "$routeParams", "resource", "$gmAuth", MrLoginController])
module.controller("TripListController", ["$scope", "$rootScope", "resource", TripListController])
module.controller("TaskListController", ["$scope", "$rootScope", "$routeParams", "resource", TaskListController])
module.controller("UserController", ["$scope", "$rootScope", "$routeParams", "resource", UserController])
