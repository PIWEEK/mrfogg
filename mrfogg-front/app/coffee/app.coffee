@mrfogg = {}
mrfogg = @mrfogg

modules = [
    # Angular addons
    "ngRoute",
    "ngAnimate",
    "ngSanitize",

    # Mr Fogg controllers
    "mrfogg.controllers.main",

    # Services
    "mrfogg.services.resource",
    "mrfogg.services.common",
    "mrfogg.services.model",

    # Greenmine Plugins
    "gmUrls"
    "gmFlash",
    "gmModal",
    "gmStorage",
    "gmConfirm",
    "gmOverlay",
    "i18next"
]

configCallback = ($routeProvider, $locationProvider, $httpProvider, $provide,
                  $compileProvider, $gmUrlsProvider)->
    $routeProvider.when('/',
        {templateUrl: '/views/container.html', controller: "ContainerController"})
    $routeProvider.when('/login',
        {templateUrl: '/views/login.html', controller:"MrLoginController"})
    $routeProvider.when('/users',
        {templateUrl: '/views/userlist.html', controller:"UsersController"})
    $routeProvider.when('/users/:uid',
        {templateUrl: '/views/user.html', controller:"UserController"})

    apiUrls = {
        "login": "/login"
        "logout": "/logout"
        "users": "/users"
        "trips": "/trips"
        }
    $gmUrlsProvider.setUrls("api", apiUrls)
    console.log ("Config...")
    return

init = ($rootScope, $gmStorage, $gmAuth, $gmUrls, config)->
    console.log ("Initializing...")
    $rootScope.auth = $gmAuth.getUser()
    $gmUrls.setHost("api", config.host,config.scheme)
    return

angular.module('mrfogg', modules)
       .config(['$routeProvider', '$locationProvider', '$httpProvider', '$provide', '$compileProvider', '$gmUrlsProvider', configCallback])
       .run(["$rootScope","$gmStorage", "$gmAuth", "$gmUrls", 'config', init])

angular.module('mrfogg.config', []).value('config', {
#    host: "144.76.249.158:8080"
    host: "mrfogg.apiary.io"
    scheme: "http"
    defaultLanguage: "en"
    debug: false
})
