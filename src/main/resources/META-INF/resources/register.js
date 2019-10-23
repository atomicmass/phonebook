function RegisterVM() {
    var self = this;
    self.email = ko.observable();
    self.password = ko.observable();
    self.name = ko.observable();

    self.register = function () {
        $('#register').modal('hide');
        serviceVM.register({
            email: self.email(),
            password: self.password(),
            name: self.name()
        });
        self.email("");
        self.password("");
        self.name("");
    }
}