var emailRegex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/igm;
var phoneRegex = /^((\+\d\d)|0)\d{9}$/;

function required(value, controlGroup) {
    if(value == "" || value == null) {
        console.log("required failed " + value + " " + controlGroup);
        controlGroup.addClass("error").delay(2000).queue(function(next){
            controlGroup.removeClass("error");
            next();
        });
        return false;
    }
    return true;
}

function regex(value, regex, controlGroup) {
    if(!regex.test(value)) {
        console.log("regex failed " + value + " " + controlGroup);
        controlGroup.addClass("error").delay(2000).queue(function(next){
            controlGroup.removeClass("error");
            next();
        });
        return false;
    }
    return true;
}

function removeSpaces(value) {
    while(/\s/.test(value)) {
        self.contactPhone(value.replace(" ", ""));
    }
    return value;
}