# Copyright 2013 Andrey Antukh <niwi@niwi.be>
#
# Licensed under the Apache License, Version 2.0 (the "License")
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

LoginController = ($scope, $rootScope, $location, $routeParams, rs, $gmAuth, $i18next) ->
    $rootScope.pageTitle = $i18next.t('login.login-title')
    $rootScope.pageSection = 'login'

    $scope.form = {}
    $scope.submit = ->
        email = $scope.form.email
        password = $scope.form.password

        $scope.loading = true

        onSuccess = (user) ->
            $gmAuth.setUser(user)
            $rootScope.auth = user
            if $routeParams['next']
                $location.url($routeParams['next'])
            else
                $location.url("/")

        onError = (data) ->
            $scope.error = true
            $scope.errorMessage = data.detail

        promise = rs.login(email, password)
        promise = promise.then(onSuccess, onError)
        promise.then ->
            $scope.loading = false

    return



PublicRegisterController = ($scope, $rootScope, $location, rs, $data, $gmAuth, $i18next) ->
    $rootScope.pageTitle = $i18next.t('register.register')
    $rootScope.pageSection = 'login'
    $scope.form = {"type": "public"}

    $scope.$watch "site.data.public_register", (value) ->
        if value == false
            $location.url("/login")

    $scope.submit = ->
        form = _.clone($scope.form)

        promise = rs.register(form)
        promise.then (user) ->
            $gmAuth.setUser(user)
            $rootScope.auth = user
            $location.url("/")

        promise.then null, (data) ->
            $scope.checksleyErrors = data

    return


module = angular.module("mrfogg.controllers.auth", [])
module.controller("LoginController", ['$scope', '$rootScope', '$location', '$routeParams', 'resource', '$gmAuth', '$i18next', LoginController])
module.controller("PublicRegisterController", ["$scope", "$rootScope", "$location", "resource", "$data", "$gmAuth", "$i18next", PublicRegisterController])
