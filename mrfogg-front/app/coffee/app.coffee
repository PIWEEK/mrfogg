@mrfogg = {}
mrfogg = @mrfogg

modules = [
    # Angular addons
    "ngRoute",
    "ngAnimate",
    "ngSanitize",

    # Controller
    "mrfogg.controllers.main",

    # Widgets
    # "mrfogg.widgets",

    # Services
    "mrfogg.services.resource",
    "mrfogg.services.common",
    "mrfogg.services.model",

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
        }
    $gmUrlsProvider.setUrls("api", apiUrls)

    $sceDelegateProvider.resourceUrlWhitelist(['self', 'http://localhost:8080/**'])

    return

init = ($rootScope, $gmStorage, $gmAuth, $gmUrls, config)->
    console.log ("Initializing...")
    $rootScope.auth = $gmAuth.getUser()
    $gmUrls.setHost("api", config.host,config.scheme)


    $rootScope.loadedCards = [
        {
            "id": 1,
            "title": "Encuesta de bares",
            "description": "LoremBacon ipsum dolor sit amet ground round filet mignon pig pork chop, short loin frankfurter venison.",
            "owner": {
                "email": "alonso.torres@kaleidos.net"
            },
            "widget": "/widget/images/10001",
            "widgetTemplate": "http://localhost:8080/assets/client/mrfogg-widget-images.html",
            "comments": [
                { "userId": 1, "Lorem ipsum" },
                { "userId": 2, "Lorem ipsum" }
            ]
        },
        {
            "id": 2,
            "title": "Mapa de la zona centro",
            "description": "Mapa de la zona centro",
            "owner": {
                "email": "ramiro.sanchez@kaleidos.net"
            },
            "widget": "/widget/map/10002",
            "comments": []
        }
    ]

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

