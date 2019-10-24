function PhonebookVM() {
    var self = this;
    self.contactName = ko.observable();
    self.contactEmail = ko.observable();
    self.contactPhone = ko.observable();
    self.initials = ko.observableArray();
    self.contacts = ko.observableArray();
    self.currentInitial = ko.observable();
    self.prevDeleted = [];
    self.searchString = ko.observable();
    self.searchResults = ko.observableArray();

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
        self.navInitial(self.currentInitial());
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

    self.navInitial = function (data) {
        if (self.currentInitial()) {
            self.currentInitial().className("");
        }
        self.currentInitial(data);
        if (data) {    
            data.className("active");
            serviceVM.listContacts(self.currentInitial().initial());
        }
    }

    self.delete = function(data) {
        self.prevDeleted.push(data);
        serviceVM.deleteContact(data.id())
            .done(function (data) {
                serviceVM.listInitials();
            })
            .done(function (data) {
                $('#undoBtn').fadeIn(400);
            });
    }

    self.undo = function(data) {
        var last = self.prevDeleted.pop();
        serviceVM.saveContact({
            name: last.name(),
            email: last.email(),
            phone: last.phone()
        });
        if(self.prevDeleted.length == 0) {
            $('#undoBtn').fadeOut(400);
        }
    }

    self.search = function(data) {
        serviceVM.search(self.searchString())
            .done(function (data) {
                self.searchResults.removeAll();
                for (var i = 0; i < data.length; i++) {
                self.searchResults.push({
                        id: ko.observable(data[i].id),
                        name: ko.observable(data[i].name),
                        initial: ko.observable(data[i].initial),
                        email: ko.observable(data[i].email),
                        ownerEmail: ko.observable(data[i].ownerEmail),
                        phone: ko.observable(data[i].phone)
                    });
                }
            });
    }

    self.showSearch = function(data) {
        $('#listNav').toggleClass("active");
        $('#searchNav').toggleClass("active");
        $('#phonebookList').hide();
        $('#phonebookSearch').show();
    }

    self.showList = function(data) {
        $('#listNav').toggleClass("active");
        $('#searchNav').toggleClass("active");
        $('#phonebookList').show();
        $('#phonebookSearch').hide();
    }
}