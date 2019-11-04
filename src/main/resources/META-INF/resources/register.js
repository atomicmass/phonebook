function RegisterVM() {
    var self = this;
    self.email = ko.observable();
    self.password = ko.observable();
    self.name = ko.observable();

    self.register = function () {

        var valid = true;
        valid = required(self.name(), $("#cgRegisterName")) && valid;
        valid = required(self.email(), $("#cgRegisterEmail")) && valid;
        valid = required(self.password(), $("#cgRegisterPassword")) && valid;

        if(!valid) {
            return;
        }

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