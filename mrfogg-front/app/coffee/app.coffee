@mrfogg = {}
mrfogg = @mrfogg

modules = [
    # Angular addons
    "ngRoute",
    "ngAnimate",
    "ngSanitize",

    # Mr Fogg controllers
    "mrfogg.controllers.main",

    # Greenmine Plugins
    "gmUrls"
    "gmFlash",
    "gmModal",
    "gmStorage",
    "gmConfirm",
    "gmOverlay",
    "i18next",
]

configCallback = ($routeProvider)->
    $routeProvider.when('/',
        {templateUrl: '/views/container.html', controller: "ContainerController"})

    console.log ("Config...")
    return

init = ()->
    console.log ("Initializing...")
    return

angular.module('mrfogg', modules)
       .config(["$routeProvider", configCallback])
       .run([init])
