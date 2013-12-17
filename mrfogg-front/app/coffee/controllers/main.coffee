MainController = ($scope) ->
    return

ContainerController = ($scope) ->
    $scope.tripName = "London Trip"
    return

UsersController = ($scope, $rootScope, resource) ->
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

    resource.getUser($scope.userid, params).then (result) ->
            $scope.trips = result.trips
            $scope.tripcount = result.tripcount
            $scope.paginatedBy = result.paginatedBy

    return

TripsController = ($scope, $rootScope, resource) ->
    $rootScope.pageTitle = 'Trips'
    $scope.tripList = resource.getTrips()
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
module.controller("UsersController", ["$scope", "$rootScope", "resource", UsersController])
module.controller("TripsController", ["$scope", "$rootScope", "resource", TripsController])
module.controller("MrLoginController", ["$scope","$rootScope", "$location",
                  "$routeParams", "resource", "$gmAuth",
                  MrLoginController])
