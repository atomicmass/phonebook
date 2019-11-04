function LoginVM() {
    var self = this;
    self.email = ko.observable("");//sceoan@gmail.com");
    self.password = ko.observable("");//bleh");

    self.login = function () {

        var valid = true;
        valid = required(self.email(), $("#cgLoginEmail")) && valid;
        valid = required(self.password(), $("#cgLoginPassword")) && valid;

        if(!valid) {
            return;
        }
        
        $('#login').modal('hide');
        serviceVM.login({
            email: self.email(),
            password: self.password()
        });
        self.email("");
        self.password("");
    }
}