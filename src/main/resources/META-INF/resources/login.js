function LoginVM() {
    var self = this;
    self.email = ko.observable();
    self.password = ko.observable();

    self.login = function () {
        $('#login').modal('hide');
        serviceVM.login({
            email: self.email(),
            password: self.password()
        });
        self.email("");
        self.password("");
    }
}