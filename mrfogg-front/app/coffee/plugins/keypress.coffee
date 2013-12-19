CreateCallback = (keypress)->
    doDirective = (scope, element, attrs)->
        onKeyPress = (event)->
            if event.which == keypress
                scope.$apply ()-> scope.$eval(attrs.ngEnter)
                event.preventDefault()
        element.bind("keydown keypress", onKeyPress)
    return doDirective

NgEnterDirective = ()-> return CreateCallback 13
NgSpaceDirective = ()-> return CreateCallback 32
NgComaDirective = ()-> return CreateCallback 188

module = angular.module('mrKeypress', [])
module.directive('ngEnter', NgEnterDirective)
module.directive('ngSpace', NgSpaceDirective)
module.directive('ngComa', NgComaDirective)
