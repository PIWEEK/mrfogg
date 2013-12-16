@mrfogg = {}
mrfogg = @mrfogg

modules = [
    "mrfogg.controllers.common"
]
configCallback = ()->
    console.log ("Config...")
    return

init = ()->
    console.log ("Initializing...")
    return

angular.module('mrfogg', modules)
       .config([configCallback])
       .run([init])
