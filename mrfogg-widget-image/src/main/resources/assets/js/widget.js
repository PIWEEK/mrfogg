(function() {
  var ImageWidgetController, module;

  ImageWidgetController = function($scope) {
    $scope.test = "TEST";
  };

  console.log(">> ImageWidget");

  module = angular.module("mrfogg.widgets", []);

  module.controller("ImageWidgetController", ["$scope", ImageWidgetController]);

}).call(this);
