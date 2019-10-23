function NavVM() {
    var self = this;
    self.authenticated = ko.observable(false);
    self.showRegister = function (data) {
        $('#register').modal('show');
    }

    self.showLogin = function (data) {
        $('#login').modal('show');
    }

    self.loggedIn = function (bl) {
        self.authenticated(bl);
    }

    self.logout = function () {
        self.authenticated(false);
        serviceVM.logout();
    }
}