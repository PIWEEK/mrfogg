@mrfogg = {}
mrfogg = @mrfogg

modules = [
    # Angular addons
    "ngRoute",
    "ngAnimate",
    "ngSanitize",

    # Controller
    "mrfogg.controllers.main",

    # Services
    "mrfogg.services.resource",
    "mrfogg.services.common",
    "mrfogg.services.model",

    # Modules
    "mrKeypress",

    # Widgets
    "mrfogg.widgets",

    # Greenmine Plugins
    "gmUrls",
    "gmFlash",
    "gmModal",
    "gmStorage",
    "gmConfirm",
    "gmOverlay",
    "i18next"
]

configCallback = ($routeProvider, $locationProvider, $httpProvider, $provide, $compileProvider, $gmUrlsProvider, $sceDelegateProvider)->
    $routeProvider.when('/',
        {templateUrl: '/views/container.html', controller: "ContainerController"})

    $routeProvider.when('/trips/:tripId',
        {templateUrl: '/views/container.html', controller: "ContainerController"})

    $routeProvider.when('/trips/:tripId/tasks/:taskId',
        {templateUrl: '/views/container.html', controller: "ContainerController"})

    $routeProvider.when('/login',
        {templateUrl: '/views/login.html', controller:"MrLoginController"})
    $routeProvider.when('/users',
        {templateUrl: '/views/userlist.html', controller:"UserListController"})

    apiUrls = {
        "root": "/"
        "login": "/auth/login"
        "logout": "/auth/logout"
        "users": "/users"
        "trips": "/trips"
        "cards": "/trips/%s/tasks/%s/cards"
    }

    $gmUrlsProvider.setUrls("api", apiUrls)

    $sceDelegateProvider.resourceUrlWhitelist(['self', 'http://localhost:8080/**'])

    return

init = ($rootScope, $gmStorage, $gmAuth, $gmUrls, config)->
    $rootScope.auth = $gmAuth.getUser()
    $gmUrls.setHost("api", config.host,config.scheme)

    return

module = angular.module('mrfogg', modules)
module.config(['$routeProvider', '$locationProvider', '$httpProvider', '$provide', '$compileProvider', '$gmUrlsProvider', '$sceDelegateProvider', configCallback])
module.run(["$rootScope","$gmStorage", "$gmAuth", "$gmUrls", 'config', init])

angular.module('mrfogg.config', []).value('config', {
#    host: "144.76.249.158:8080"
    host: "mrfogg.apiary.io"
#    host: "localhost:8080"
    scheme: "http"
    defaultLanguage: "en"
    debug: false
})

angular.module("mrfogg.widgets", [])
