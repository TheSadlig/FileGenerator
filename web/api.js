var app = angular.module('filegenerator', []);
app.controller('main', function($scope, $http) {
  $scope.link = 'filegenerator.html';
});

app.controller('generator', function($scope, $http, $window) {
  $scope.openedTab = null;

  $http.get('FileGenerator%2FFileGenerator%2Fsrc%2Ftest%2Fresources/list').
  then(function(response) {
    $scope.data = response.data;
  });

  $scope.updateResult = function() {
    var path = window.encodeURIComponent($scope.templatePath);
    $http.get('http://localhost:4567/templates/' + path + '/generate').
    then(function(response) {
      $scope.resultingFile = response.data;
        if ($scope.openedTab == null) {
          // By default, we set the first chunk as displayed
          $scope.select(Object.keys($scope.resultingFile.chunks)[0]);
        }
    }, function(response) {
      console.log(response);
    });
  }

  $scope.select = function(chunkName) {
    $scope.openedTab = chunkName;
  }

  $scope.debug = function() {
    console.log($scope);
  }

  $scope.renderAST = function() {
    console.log("Rendering dotfile");
    $window.render($scope.resultingFile["resultingDotFile"]);
  }
});


app.controller('creation', function($scope, $http, $timeout, $window) {
  $scope.openedTab = null;
  $scope.newTemplate = "===filename.xml,filepath/===\n===END===";

  var updateInterval = 100;
  var lastUpdate = 0;
  var timeouted = false;
  $scope.preview = function() {
    var d = new Date();
    if (d.getTime() - lastUpdate > updateInterval) {
      $http({
          url: 'http://localhost:4567/templates/preview',
          method: "POST",
          data: {
            'inputTemplate': $scope.newTemplate
          },
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
          }
        })
        .then(function(response) {
            $scope.resultingFile = response.data;

            if ($scope.openedTab == null) {
              // By default, we set the first chunk as displayed
              $scope.select(Object.keys($scope.resultingFile.chunks)[0]);
            }
          },
          function(response) {
            console.log(response);
          });
      lastUpdate = d.getTime();
    } else {
      if (!timeouted) {
        $timeout($scope.preview(), 1);
        timeouted = true;
      }
    }

  };

  $scope.debug = function() {
    console.log($scope);
  };

  $scope.select = function(chunkName) {
    $scope.openedTab = chunkName;
  }

  $scope.renderAST = function() {
    console.log("Rendering dotfile");
    $window.render($scope.resultingFile["resultingDotFile"]);
  }
});
