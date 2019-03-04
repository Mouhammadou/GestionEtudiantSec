var myApp=angular.module("myApp", []);
myApp.controller("InscriptionController", function ($scope, $http) {
    $scope.etudiant={};

    $scope.saveEtudiant=function () {
        $http.post("etudiants", $scope.etudiant)
            .success(function (data) {
                $scope.etudiant=data;
            })
    }
});