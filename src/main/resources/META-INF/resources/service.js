function ServiceVM() {
    var self = this;
    self.baseUri = 'http://localhost:9001/phonebook/api/v1';
    self.userUri = self.baseUri + "/user"
    self.loginUri = self.userUri + "/login";
    self.phonebookUri = self.baseUri +"/phonebook";
    self.initialUri = self.phonebookUri + "/initial";
    self.serviceUsername = "";
    self.servicePassword = "";
    self.tasks = ko.observableArray();

    

    self.ajax = function (uri, method, data) {
        var auth = null;
        if (self.serviceUsername != "") {
            auth = function (xhr) {
                xhr.setRequestHeader("Authorization",
                    "Basic " + btoa(self.serviceUsername + ":" + self.servicePassword));
            }
        }


        var request = {
            url: uri,
            type: method,
            contentType: "application/json",
            cache: false,
            dataType: 'json',
            data: JSON.stringify(data),
            beforeSend: auth,
            error: function (err) {
                console.log("ajax error " + err.status + " " + err.statusText);
                messageVM.showWarning(err.responseText);
            }
        };
        return $.ajax(request);
    }

    self.register = function (user) {
        self.ajax(self.userUri, "POST", user).done(function (data) {
            self.login(user);
        });
    }

    self.logout = function () {
        self.serviceUsername = "";
        self.servicePassword = "";
        $('#phonebook').hide();
        $('#main').show();
    }

    self.login = function (user) {
        self.ajax(self.loginUri, "POST", user).done(function (data) {
            navVM.loggedIn(true);
            self.serviceUsername = user.email;
            self.servicePassword = user.password;
            $('#main').hide();
            $('#phonebook').show();
            self.listInitials();
        });
    }

    self.listInitials = function() {
        self.ajax(self.initialUri, "GET").done(function (data) {
            phonebookVM.bindInitials(data);
        });
    }

    self.saveContact = function (contact) {
        self.ajax(self.phonebookUri, "POST", contact).done(function (data) {self.listInitials();});
    }
}