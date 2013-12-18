(function() {
  var ImageWidgetController, module;

  ImageWidgetController = function($scope) {
    $scope.test = "TEST";
  };

  module = angular.module("mrfogg.widgets", []);

  module.controller("ImageWidgetController", ["$scope", ImageWidgetController]);

}).call(this);
