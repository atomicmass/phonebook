function PhonebookVM() {
    var self = this;
    self.contactName = ko.observable();
    self.contactEmail = ko.observable();
    self.contactPhone = ko.observable();
    self.initials = ko.observableArray();
    self.contacts = ko.observableArray();
    self.currentInitial = ko.observable();

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
        self.initials.removeAll();
        for (var i = 0; i < data.length; i++) {
            self.initials.push({
                initial: ko.observable(data[i]),
                className: ko.observable("")
            });
        }
        self.navInitial();
    }

    self.bindContacts = function (data) {
        self.contacts.removeAll();
        for (var i = 0; i < data.length; i++) {
            self.contacts.push({
                id: ko.observable(data[i].id),
                name: ko.observable(data[i].name),
                initial: ko.observable(data[i].initial),
                email: ko.observable(data[i].email),
                ownerEmail: ko.observable(data[i].ownerEmail),
                phone: ko.observable(data[i].phone)
            });
        }

    }


    // todo refactor
    self.navInitial = function (data) {
        if (data) {
            if (self.currentInitial()) {
                self.currentInitial().className("");
            }
            data.className("active");
            self.currentInitial(data);
        }
        if (self.currentInitial()) {
            serviceVM.listContacts(self.currentInitial().initial());
        }
    }
}