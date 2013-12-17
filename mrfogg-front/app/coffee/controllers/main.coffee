MainController = ($scope) ->
    return

ContainerController = ($scope) ->
    $scope.tripName = "London Trip"
    return

UsersController = ($scope, $rootScope) ->
    $rootScope.pageTitle = 'Users'
    $scope.userList = resource.getUsers()
    return

TripsController = ($scope, $rootScope) ->
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
module.controller("UsersController", ["$scope", "$rootScope", UsersController])
module.controller("TripsController", ["$scope", "$rootScope", TripsController])
module.controller("MrLoginController", ["$scope","$rootScope", "$location",
                  "$routeParams", "resource", "$gmAuth",
                  MrLoginController])
