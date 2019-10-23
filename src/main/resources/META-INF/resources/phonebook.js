function PhonebookVM() {
    var self = this;
    self.contactName = ko.observable();
    self.contactEmail = ko.observable();
    self.contactPhone = ko.observable();
    self.initials = ko.observableArray();

    self.saveContact = function () {
        $('#addContact').modal('hide');
        serviceVM.saveContact({
            name: self.contactName(),
            email: self.contactEmail(),
            phone: self.contactPhone()
        });
        self.contactName("");
        self.contactEmail("");
        self.contactPhone("");
    }

    self.showAddContact = function (data) {
        $('#addContact').modal('show');
    }

    self.bindInitials = function (data) {
        for (var i = 0; i < data.length; i++) {
            self.initials.push({
                initial: ko.observable(data[i])
            });
        }
    }
}