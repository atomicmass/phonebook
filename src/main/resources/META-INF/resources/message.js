function MessageVM() {
    var self = this;
    self.error = ko.observable();
    self.warning = ko.observable();

    self.showWarning = function (warn) {
        self.warning(warn);
        $('#message').fadeIn(400).delay(5000).fadeOut(400);
    }
}