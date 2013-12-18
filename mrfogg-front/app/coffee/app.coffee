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
    console.log ("Config...")

    $routeProvider.when('/',
        {templateUrl: '/views/container.html', controller: "ContainerController"})
    $routeProvider.when('/login',
        {templateUrl: '/views/login.html', controller:"MrLoginController"})
    $routeProvider.when('/users',
        {templateUrl: '/views/userlist.html', controller:"UserListController"})

    apiUrls = {
        "login": "/login"
        "logout": "/logout"
        "users": "/users"
        "trips": "/trips"
        "cards": "/trips/%s/tasks/%s/cards"
    }

    $gmUrlsProvider.setUrls("api", apiUrls)

    $sceDelegateProvider.resourceUrlWhitelist(['self', 'http://localhost:8080/**'])

    return

init = ($rootScope, $gmStorage, $gmAuth, $gmUrls, config)->
    console.log ("Initializing...")
    $rootScope.auth = $gmAuth.getUser()
    $gmUrls.setHost("api", config.host,config.scheme)

    return

module = angular.module('mrfogg', modules)
module.config(['$routeProvider', '$locationProvider', '$httpProvider', '$provide', '$compileProvider', '$gmUrlsProvider', '$sceDelegateProvider', configCallback])
module.run(["$rootScope","$gmStorage", "$gmAuth", "$gmUrls", 'config', init])

angular.module('mrfogg.config', []).value('config', {
#    host: "144.76.249.158:8080"
    host: "mrfogg.apiary.io"
    scheme: "http"
    defaultLanguage: "en"
    debug: false
})

angular.module("mrfogg.widgets", [])
