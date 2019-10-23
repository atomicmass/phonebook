function LoginVM() {
    var self = this;
    self.email = ko.observable("sceoan@gmail.com");
    self.password = ko.observable("bleh");

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